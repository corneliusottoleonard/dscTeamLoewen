import pandas as pd
import numpy as np


from datafold import (
    EDMD,
    DMDStandard,
    TSCIdentity,
)

from datafold.pcfold import (
    TSCDataFrame,
)

import datafold.dynfold as dfold
import datafold.pcfold as pfold


class BaseModel:
    @staticmethod
    def create_defussion_maps(data: pd.DataFrame) -> dfold.DiffusionMaps:
        """
        this function reduce dimensionality of Features

        Args:
            data:
                Pandas.DataFrame
            n_components:
                int -> reduce faktor

        Returns:
            dfold.DiffusionMaps

        Raises:
            None

        Von idae Gruppe wednesday
        """
        #
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
        pass

    @staticmethod
    def predict(model: EDMD, data: TSCDataFrame, time_value: int = 30) -> np.array:
        """
        this function predicting Dmd Model and reshape Data

        Input:
            model: EDMD
            data: TSCDataFrame
            Time_value: int = Default(30) -> amount of predictions made

        Output:
            np.array

        """
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
