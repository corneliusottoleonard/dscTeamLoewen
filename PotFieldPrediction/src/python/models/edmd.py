# created by Sebastian Schmid
import pandas as pd
import numpy as np

from sklearn.preprocessing import (
    Normalizer,
)

from datafold import (
    EDMD,
    DMDStandard,
    TSCFeaturePreprocess,
)
from datafold.pcfold import (
    TSCDataFrame,
)

from .base import BaseModel


class EDMDModel(BaseModel):
    @staticmethod
    def fit(data: TSCDataFrame, n_components: int = 10) -> EDMD:
        """
        this function fitting Dmd Model and reshape Data

        Input:
            data:
                Pandas.DataFrame
            n_components:
                int = Default(10)-> feature reduce faktor

        Output:
            EDMD Model

        """
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
        return model.fit(data)


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
        return model.predict(
            data,
            time_values=[i for i in range(time_value)],
        )
