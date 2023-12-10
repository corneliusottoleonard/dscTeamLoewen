from typing import Tuple

from datafold import EDMD
import datafold.dynfold as dfold
import datafold.pcfold as pfold
from datafold.pcfold import TSCDataFrame
import numpy as np


class BaseModel:

    @staticmethod
    def create_diffusion_maps(data: np.ndarray) -> dfold.DiffusionMaps:
        """Reduce dimensionality of features by using diffusion maps. 

        From group Wednesday.

        Args:
            data (np.ndarray): Two-dimensional point cloud of data to reduce.

        Returns:
            dfold.DiffusionMaps: Fitted diffusion map.
        """
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
    def fit(coords: np.ndarray, values: np.ndarray) -> EDMD:
        """Fit the model using the provided data as trainings data.

        Args:
            coords (np.ndarray): Coordinates of the `values`.
            values (np.ndarray): As many values of training value.

        Raises:
            NotImplementedError: Raised if function of base class is called.

        Returns:
            EDMD: Fitted model.
        """
        raise NotImplementedError('Can\'t call `fit` method of base class.')

    @staticmethod
    def predict(model: EDMD, coords: np.ndarray, values: np.ndarray, tsteps: int = 30)-> Tuple[np.ndarray, np.ndarray]:
        """Predict the next `tsteps` steps using the `model` instance.

        Args:
            model (EDMD): Model to use for prediction.
            coords (np.ndarray): Coordinates of the `values`.
            values (np.ndarray): Values of the last time steps.
            tsteps (int, optional): Steps to predict. Defaults to 30.

        Returns:
            Tuple[np.ndarray, np.ndarray]: Array with coordinates and array of shape
            (`tsteps`, `coords.shape[1]`) with predicted values.
        """
        num_coords = coords.shape[1]
        predict_data = TSCDataFrame.from_tensor(values.reshape(1, -1, num_coords))
        prediction = model.predict(
            predict_data.loc[[(0, 0)]],
            time_values=[i for i in range(tsteps)],
        )
        new_coords = np.concatenate([[coords[0, :, :]] for _ in range(tsteps)], axis=0)
        return new_coords, prediction.to_numpy(np.float64)
