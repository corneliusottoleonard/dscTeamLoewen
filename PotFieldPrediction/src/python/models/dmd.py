# created by Sebastian Schmid
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
from sklearn.decomposition import PCA

import datafold.dynfold as dfold
import datafold.pcfold as pfold


class DMDModel:

    @staticmethod
    def create_defussion_maps(data: pd.DataFrame) -> dfold.DiffusionMaps:
        '''
            this function reduce dimensionality of Features
                
            Input: 
                data: Pandas.DataFrame
                n_components: int -> reduce faktor
                
            Output: 
                dfold.DiffusionMaps
                

        Von idae Gruppe wednesday
        '''
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
        '''
            this function fitting Dmd Model and reshape Data
                
            Input: 
                data: Pandas.DataFrame
                n_components: int = Default(10)-> feature reduce faktor
                
            Output: 
                EDMD -> DMD Model
                
        '''
        model = EDMD(
            dict_steps=[
                (
                    "_id",
                    TSCIdentity(),
                ),
                ("Diffusion Map", DMDModel.create_defussion_maps(data)),
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
        '''
            this function predicting Dmd Model and reshape Data
                
            Input:
                model: EDMD
                data: TSCDataFrame
                Time_value: int = Default(30) -> amount of predictions made
                
            Output: 
                np.array
                
        '''
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
        '''
            this function combine fit and predict
                
            Input:
                model: EDMD
                data: pd.DataFrame
                n_components: int = Default(10)-> feature reduce faktor
                itme_value: int = Default(30) -> amount of predictions made
                
            Output: 
                np.array
                
        '''
        fitted_dmd_model = DMDModel.fit(data, n_components)
        return DMDModel.predict(fitted_dmd_model, data, time_value)
