import os
import pandas as pd
import numpy as np


from PotFieldPrediction.src.python.models.dmd import DMDModel
from PotFieldPrediction.src.python.utils import load_data_pot_fields


def test_fit_train():
    path = os.path.join(
        "PotFieldPrediction\\src\\python\\tests\\assets\\potFields_small\\"
    )
    df = load_data_pot_fields(path)
    df = df.T
    dmd = DMDModel.fit(df, 14)
    dmd = DMDModel.predict(dmd, df)
    assert int(np.mean(dmd)) == 45
    assert dmd.shape[1] == 106405
    assert dmd.shape[0] == 30
