# created by Sebastian Schmid
from datafold.pcfold import TSCDataFrame
from datafold import (
    EDMD,
    DMDStandard,
    TSCIdentity,
)
import pandas as pd
import numpy as np

from models.base import BaseModel


class DMDModel(BaseModel):

    @staticmethod
    def fit(coords: np.ndarray, values: np.ndarray) -> EDMD:
        # FIXME: this is EDMD?!?
        num_coords = coords.shape[1]
        model = EDMD(
            dict_steps=[
                (
                    "_id",
                    TSCIdentity(),
                ),
                ("Diffusion Map", DMDModel.create_diffusion_maps(values.reshape(-1, num_coords))),
            ],
            dmd_model=DMDStandard(),
            include_id_state=False,
            sort_koopman_triplets=True,
            verbose=True,
        )

        train = TSCDataFrame.from_tensor(values.reshape(1, -1, num_coords))
        return model.fit(train)

