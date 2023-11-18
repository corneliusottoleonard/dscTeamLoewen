# created by Sebastian Schmid
from matplotlib import pyplot as plt
import pandas as pd
import numpy as np
from datafold import EDMD, DMDStandard, TSCFeaturePreprocess, TSCIdentity
from sklearn.preprocessing import (
    Normalizer,
)
from datafold.pcfold import (
    TSCDataFrame,
)


class Dmd:
    train = None
    test = None
    test_size_in_frames = None
    err = None
    mse = None
    mae = None
    eigenvalues = None
    name = "DMD"

    def __init__(
        self,
        data: pd.DataFrame,
        dict_steps=[
            (
                "_id",
                TSCIdentity(),
            )
        ],
    ):
        self.data = normalize(data)
        self.dict_steps = dict_steps
        self.model = EDMD(
            self.dict_steps,
            dmd_model=DMDStandard(rank=10),
            include_id_state=False,
            sort_koopman_triplets=True,
            verbose=True,
        )

    def split_data_in_train_test(
        self,
        train_size_in_frames: int,
        test_size_in_frames: int,
        start_index: int = 0,
        reduce_faktor: int = 10,
    ) -> None:
        self.train_size_in_frames = train_size_in_frames
        self.test_size_in_frames = test_size_in_frames
        self.reduce_faktor = reduce_faktor
        self.train = TSCDataFrame.from_tensor(
            np.float32(
                reduce_data_points(
                    np.reshape(
                        (self.data.iloc[:, start_index:train_size_in_frames].values.T),
                        (1, train_size_in_frames, -1),
                    ),
                    reduce_faktor,
                ),
            )
        )

        reduce_data_points_test_for_pred = np.float32(
            reduce_data_points(
                np.reshape(
                    self.data.iloc[
                        :,
                        start_index
                        + train_size_in_frames : start_index
                        + train_size_in_frames
                        + 1,
                    ].values.T,
                    (1, 1, -1),
                ),
                reduce_faktor,
            ),
        )
        reduce_data_points_test = np.float32(
            reduce_data_points(
                np.reshape(
                    self.data.iloc[
                        :,
                        start_index
                        + train_size_in_frames : train_size_in_frames
                        + test_size_in_frames,
                    ].values.T,
                    (1, self.test_size_in_frames, -1),
                ),
                reduce_faktor,
            ),
        )
        self.test = TSCDataFrame.from_tensor(reduce_data_points_test)
        self.test_for_pred = TSCDataFrame.from_tensor(reduce_data_points_test_for_pred)

    def fit(self) -> None:
        try:
            self.model.fit(self.train)
        except:
            raise Exception("Split data in train and test first")

    def predict(self, time_value: int = 30) -> TSCDataFrame:
        self.time_value = time_value
        self.pred = self.model.predict(
            self.test_for_pred,
            time_values=[i for i in range(time_value)],
        )
        self.err = self.pred - self.test
        self.mse = np.array(self.err**2).mean()
        self.mae = np.abs(np.array(self.err)).mean()
        self.eigenvalues = self.model.koopman_eigenvalues
        return self.pred

    def visualize_eigenvalues(self) -> None:
        if self.eigenvalues is None:
            raise Exception("first fit data")
        theta = np.linspace(0, 2 * np.pi, 100)
        imaginary_eigenvalues = self.eigenvalues[np.imag(self.eigenvalues) != 0]

        plt.figure(figsize=(7, 7))
        plt.scatter(
            np.real(imaginary_eigenvalues),
            np.imag(imaginary_eigenvalues),
            marker="o",
            color="red",
            label="Imaginary Eigenvalues",
        )
        plt.plot(
            np.cos(theta),
            np.sin(theta),
            linestyle="--",
            color="blue",
            label="Unit Circle",
        )
        plt.title(f"{self.name} Eigenvalues")
        plt.xlabel("Real")
        plt.ylabel("Imaginary")
        plt.legend()
        plt.grid(True)
        plt.show()

    def visualize_time_series(self) -> None:
        steps = 5
        shape = np.shape(self.pred)
        preds = np.array(self.pred).reshape(self.time_value, shape[1], 1)
        orginal = np.array(self.test).reshape(self.time_value, shape[1], 1)
        err = np.array(self.err).reshape(self.time_value, shape[1], 1)

        fig, axs = plt.subplots(steps, 3, figsize=(15, 10))
        for i in range(5):
            axs[i, 0].imshow(list(preds[i * 5].T) * self.reduce_faktor, cmap="viridis")
            axs[i, 0].set_title(f"{self.name}: Prediction Time Step {i*steps}")

            axs[i, 1].imshow(
                list(orginal[i * 5].T) * self.reduce_faktor, cmap="viridis"
            )
            axs[i, 1].set_title(f"{self.name}: Actual Data Time Step {i*steps}")

            axs[i, 2].imshow(list(err[i * 5].T) * self.reduce_faktor, cmap="viridis")
            axs[i, 2].set_title(f"{self.name}: Error Time Step {i*steps}")

    def visualize_error_over_time(self) -> None:
        self.error_over_time = [
            abs(np.mean(self.err.iloc[i])) for i in range(self.time_value)
        ]
        plt.xlabel("Time")
        plt.ylabel("Error")
        plt.title(f"{self.name}: Error Over Time")
        plt.plot(self.error_over_time)
        plt.show()


class Edmd(Dmd):
    name = "EDMD"

    def __init__(
        self,
        data: pd.DataFrame,
        dict_steps=[
            (
                "_id",
                TSCFeaturePreprocess(Normalizer()),
            ),
        ],
    ):
        self.data = normalize(data)
        self.dict_steps = dict_steps
        self.model = EDMD(
            self.dict_steps,
            dmd_model=DMDStandard(rank=10),
            include_id_state=False,
            sort_koopman_triplets=True,
            verbose=True,
        )


def load_data_pot_fields(
    path: str,
    index: int = 0,
    last_value: int = 0,
    df: pd.DataFrame = pd.DataFrame(),
) -> pd.DataFrame:
    try:
        new_df = pd.read_csv(
            f"{path}rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-{index}.csv"
        )
        new_df_test_values = np.mean(new_df.values[:, 2])
    except Exception as e:
        if df.empty:
            raise e
        df.loc[-1] = df.loc[1]  # adding missing row
        df.index = df.index + 1
        df.loc[0] = df.loc[1]  # adding missing row
        df.index = df.index + 1
        df.loc[106407] = df.loc[106404]  # adding missing row
        df.loc[106408] = df.loc[106404]  # adding missing row
        df.loc[106409] = df.loc[106404]  # adding missing row
        df = df.sort_index()
        return df
    if new_df_test_values != last_value:
        df[f"{index}_id"] = new_df["value"]
    return load_data_pot_fields(path, index + 1, new_df_test_values, df.copy())


def normalize(data: pd.DataFrame) -> pd.DataFrame:
    return (data - np.min(data)) / (np.max(data) - np.min(data))


def reduce_data_points_old(data: np.ndarray, reduce_faktor: int) -> np.ndarray:
    shape = np.shape(data)
    data_reshape = np.reshape(data, (shape[1], 97, int(106409 / 97)))
    field = np.zeros((1, shape[1], 1097 * (97 // reduce_faktor)))
    for i in range(shape[1]):
        for l in range(97 // reduce_faktor):
            for j in range(1097):
                try:
                    field[0, i, j + l] = np.max(
                        data_reshape[
                            i, l * reduce_faktor : l * reduce_faktor + reduce_faktor, j
                        ]
                    )
                except:
                    pass

    return field


def reduce_data_points(data: np.ndarray, reduce_faktor: int = 100) -> np.ndarray:
    shape = np.shape(data)
    field = np.zeros((1, shape[1], shape[2] // reduce_faktor))
    for i in range(shape[1]):
        for j in range(shape[2] // reduce_faktor):
            field[0, i, j] = np.median(
                data[:, i, j * reduce_faktor : (j + 1) * reduce_faktor]
            )

    return field
