import pandas as pd
import numpy as np


from src.models.dmd import DMDModel


def load_data_pot_fields(
    path: str,
    index: int = 0,
    df: pd.DataFrame = pd.DataFrame(),
) -> pd.DataFrame:
    try:
        new_df = pd.read_csv(
            f"{path}rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-{index}.csv"
        )

    except Exception as e:
        if df.empty:
            raise e
        return df

    df[f"{index}_id"] = new_df["value"]
    return load_data_pot_fields(path, index + 1, df.copy())


def test_fit_train():
    path = "tests/assets/potFields_small/"
    df = load_data_pot_fields(path)
    df = df.T
    dmd = DMDModel.fit_and_predict(df, n_components=14)

    assert int(np.mean(dmd)) == 45
    assert dmd.shape[1] == 106405
    assert dmd.shape[0] == 30
