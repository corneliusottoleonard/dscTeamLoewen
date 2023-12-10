import numpy as np
cimport numpy as np

import PotField_pb2

def convert_fieldsequence_to_ndarray(data: PotField_pb2.FieldSequence):
    cdef int tsteps = len(data.fields)
    cdef int n_coords = len(data.fields[0].coordinates)
    coords = np.zeros((tsteps, n_coords, 2), dtype=np.int32)
    values = np.zeros((tsteps, n_coords), dtype=np.double)
    cdef int step, i
    for step in range(tsteps):
        for i in range(n_coords):
            coords[step][i][0] = data.fields[step].coordinates[i].x
            coords[step][i][1] = data.fields[step].coordinates[i].y
            values[step][i] = data.fields[step].coordinates[i].value
    return coords, values

def convert_ndarray_to_fieldsequence(np.ndarray[np.int32_t, ndim=3] coords, np.ndarray[np.double_t, ndim=2] values):
    tsteps, n_coord, _ = np.shape(coords)
    fields = [PotField_pb2.Field()] * tsteps
    cdef int step, i
    coordinates = [PotField_pb2.Coordinate()] * n_coord
    for step in range(tsteps):
        for i in range(n_coord):
            coordinates[i] = PotField_pb2.Coordinate(
                x=coords[step, i, 0],
                y=coords[step, i, 1],
                value=values[step, i]
            )
        f = PotField_pb2.Field(
            coordinates=coordinates
        )
        fields[step] = f
    return PotField_pb2.FieldSequence(fields=fields)
