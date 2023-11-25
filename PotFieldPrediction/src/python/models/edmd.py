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

from PotFieldPrediction.src.python.models.dmd import DMDModel


class EDMDModel(DMDModel):

    @staticmethod
    def fit(data: pd.DataFrame, n_components: int = 10) -> EDMD:
        '''
            this function fitting Dmd Model and reshape Data
                
            Input: 
                data: Pandas.DataFrame
                n_components: int = Default(10)-> feature reduce faktor
                
            Output: 
                EDMD Model
                
        '''
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

