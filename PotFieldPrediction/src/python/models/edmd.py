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

from PotFieldPrediction.src.python.models.base import BaseModel


class EDMDModel(BaseModel):
    @staticmethod
    def fit(data: pd.DataFrame, n_components: int = 10) -> EDMD:
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
