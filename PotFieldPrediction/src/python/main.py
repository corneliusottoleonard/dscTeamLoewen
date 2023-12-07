from concurrent import futures

from datafold.pcfold import TSCDataFrame
import grpc
from grpc_reflection.v1alpha import reflection
import pandas as pd

import PotField_pb2
import PotField_pb2_grpc
from models import edmd


def convert_fieldsequence_to_dataframe(data: 'PotField_pb2.FieldSequence') -> TSCDataFrame:
    # TODO:  inefficient af, fix it
    return TSCDataFrame.from_frame_list((
        pd.DataFrame.from_records((
            {'x': coord.x, 'y': coord.y, 'z': coord.z} for coord in field.coordinates
        )) for field in data
    ))

def convert_dataframe_to_fieldsequence(data: TSCDataFrame) -> PotField_pb2.FieldSequence:
    # TODO:  inefficient af, fix it
    return PotField_pb2.FieldSequence(fields=(
        PotField_pb2.Field(coordinates=(
            PotField_pb2.Coordinate(x=coord['x'], y=coord['y'], z=coord['z'])
                for _, coord in f.iterrows()
        )) for _, f in data.itertimeseries()
    ))

class PotFieldPredictionService(PotField_pb2_grpc.PredictionServiceServicer):
    def __init__(self) -> None:
        self.model = None
        super().__init__()

    def fit(self, request, context):
        data = convert_fieldsequence_to_dataframe(request.fitData.fields)
        model = edmd.EDMDModel.fit(data)
        self.model = model
        return

    def predict(self, request, context):
        if not self.model: raise Exception('Fitting was not yet executed')
        data = convert_fieldsequence_to_dataframe(request.predictData.fields)
        prediction = self.model.predict(data)
        return convert_dataframe_to_fieldsequence(prediction)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    PotField_pb2_grpc.add_PredictionServiceServicer_to_server(PotFieldPredictionService(), server)

    # necessary to use gRPC web UI
    service_names = (
        PotField_pb2.DESCRIPTOR.services_by_name['PredictionService'].full_name,
        reflection.SERVICE_NAME,
    )
    reflection.enable_server_reflection(service_names, server)

    server.add_insecure_port('127.0.0.1:50051')
    server.start()
    server.wait_for_termination()


if __name__=='__main__':
    serve()
