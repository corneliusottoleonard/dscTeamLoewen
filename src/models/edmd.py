# created by Sebastian Schmid
import pandas as pd
import numpy as np
from datafold import (
    EDMD,
    DMDStandard,
    TSCFeaturePreprocess,
)
from sklearn.preprocessing import (
    Normalizer,
)
from datafold.pcfold import (
    TSCDataFrame,
)
from sklearn.decomposition import PCA

import datafold.dynfold as dfold
import datafold.pcfold as pfold


class EDMDModel:
    @staticmethod
    def pca(data: pd.DataFrame, n_components: int = 10) -> pd.DataFrame:
        pca = PCA(n_components=n_components, svd_solver="arpack")
        return pca.fit_transform(data)

    @staticmethod
    def create_defussion_maps(data: pd.DataFrame):
        # Von Gruppe wednesday
        pcm = pfold.PCManifold(data)
        pcm.optimize_parameters(
            n_subsample=int(np.round(data.shape[1]) * 0.50),
            k=int(np.round(data.shape[1] * 0.50)),
        )
        parameterized_kernel = pfold.GaussianKernel(
            epsilon=pcm.kernel.epsilon, distance=dict(cut_off=pcm.cut_off)
        )
        dmap = dfold.DiffusionMaps(
            kernel=parameterized_kernel,
            n_eigenpairs=2,
        )
        return dmap

    @staticmethod
    def fit(data: pd.DataFrame, n_components: int = 10) -> EDMD:
        model = EDMD(
            dict_steps=[
                (
                    "_id",
                    TSCFeaturePreprocess(Normalizer()),
                ),
                ("Diffusion Map", EDMDModel.create_defussion_maps(data)),
            ],
            dmd_model=DMDStandard(),
            include_id_state=False,
            sort_koopman_triplets=True,
            verbose=True,
        )

        train = TSCDataFrame.from_tensor(
            np.float32(
                np.reshape(
                    data,
                    (1, data.shape[0], -1),
                )
            )
        )
        try:
            return model.fit(train)
        except:
            raise Exception("Split data in train and test first")

    @staticmethod
    def predict(model: EDMD, data: TSCDataFrame, time_value: int = 30) -> np.array:
        predict_data = TSCDataFrame.from_tensor(
            np.float32(
                np.reshape(
                    data,
                    (1, data.shape[0], -1),
                )
            )
        )
        return model.predict(
            predict_data.loc[[(0, 0)]],
            time_values=[i for i in range(time_value)],
        )

    @staticmethod
    def fit_and_predict(
        data: pd.DataFrame, n_components: int = 10, time_value: int = 30
    ) -> np.array:
        fitted_dmd_model = EDMDModel.fit(data, n_components)
        return EDMDModel.predict(fitted_dmd_model, data, time_value)
