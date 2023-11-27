import os
import pandas as pd
import numpy as np



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
