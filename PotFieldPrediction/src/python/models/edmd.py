# created by Sebastian Schmid
import numpy as np
from datafold import (
    EDMD,
    DMDStandard,
    TSCFeaturePreprocess,
)
from datafold.pcfold import TSCDataFrame
from sklearn.preprocessing import (
    Normalizer,
)

from .base import BaseModel


class EDMDModel(BaseModel):

    @staticmethod
    def fit(coords: np.ndarray, values: np.ndarray) -> EDMD:
        num_coords = coords.shape[1]
        model = EDMD(
            dict_steps=[
                (
                    '_id',
                    TSCFeaturePreprocess(Normalizer()),
                ),
                ('Diffusion Map', EDMDModel.create_diffusion_maps(values.reshape(-1, num_coords))),
            ],
            dmd_model=DMDStandard(),
            include_id_state=False,
            sort_koopman_triplets=True,
            verbose=True,
        )

        train = TSCDataFrame.from_tensor(values.reshape(1, -1, num_coords))
        return model.fit(train)
