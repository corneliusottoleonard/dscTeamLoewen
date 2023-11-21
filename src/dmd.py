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
from sklearn.decomposition import PCA


class DMD:
    @staticmethod
    def pca(data: pd.DataFrame, n_components: int = 10) -> pd.DataFrame:
        # 1. Zentrierung der Daten
        mean = np.mean(data, axis=0)
        centered = data - mean

        # 2. PCA durchführen
        pca = PCA(n_components=n_components)
        return pca.fit_transform(centered)

    @staticmethod
    def fit(data: pd.DataFrame, n_components: int = 10) -> EDMD:
        dict_steps = (
            [
                (
                    "_id",
                    TSCIdentity(),
                )
            ],
        )
        model = EDMD(
            dict_steps,
            dmd_model=DMDStandard(rank=10),
            include_id_state=False,
            sort_koopman_triplets=True,
            verbose=True,
        )
        data_pca = DMD.pca(data, n_components)
        train = TSCDataFrame.from_tensor(
            np.float32(
                np.reshape(
                    data_pca,
                    (1, np.size(data_pca)[0], -1),
                )
            )
        )
        try:
            return model.fit(train)
        except:
            raise Exception("Split data in train and test first")

    @staticmethod
    def predict(model: EDMD, data: TSCDataFrame, time_value: int = 30) -> np.array:
        return model.predict(
            data,
            time_values=[i for i in range(time_value)],
        )

    @staticmethod
    def fit_and_predict(
        data: pd.DataFrame, n_components: int = 10, time_value: int = 30
    ) -> np.array:
        fitted_dmd_model = DMD.fit(data, n_components)
        return DMD.predict(fitted_dmd_model, data, time_value)
