import pandas as pd
import numpy as np


import matplotlib.pyplot as plt

from PotFieldPrediction.src.python.models.edmd import EDMDModel


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
    path = "PotFieldPrediction/src/python/tests/assets/potFields_small/"
    df = load_data_pot_fields(path)
    df = df.T
    dmd = EDMDModel.fit_and_predict(df, n_components=14)

    assert int(np.mean(dmd)) == 45
    assert dmd.shape[1] == 106405
    assert dmd.shape[0] == 30
    dmd = np.array(dmd)
    assert np.shape(dmd)== (30,106405)
    
    add_dmd = np.array([[0]*30,[0]*30,[0]*30,[0]*30]).T
    assert [np.shape(dmd),np.shape(add_dmd)] == [(30, 106405), (30, 4)]

    dmd = np.hstack((dmd,add_dmd))
    assert np.shape(dmd)== (30,106409)
    dmd = np.reshape(dmd, (30,1097,97))
    assert np.shape(dmd)== (30,1097,97)
    # Create a figure with subplots
    fig, axes = plt.subplots(nrows=6, ncols=5, figsize=(15, 12))

    # Flatten the 2D array of subplots
    axes = axes.flatten()

    # Create a heatmap for each matrix and save it to a PNG file
    for i, ax in enumerate(axes):
        ax.imshow(dmd[i, :, :].T, cmap='viridis')
        ax.set_title(f'Time step {i+1}')
        ax.axis('off')  # Turn off axis labels for better visualization

    # Adjust layout for better spacing
    plt.tight_layout()

    # Save the figure to a PNG file
    plt.savefig("PotFieldPrediction\\src\\python\\tests\\assets\\test_edmd.png")
