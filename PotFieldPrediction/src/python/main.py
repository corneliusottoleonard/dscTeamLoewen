from concurrent import futures

import grpc
from grpc_reflection.v1alpha import reflection
import numpy as np

import PotField_pb2
import PotField_pb2_grpc
from models import edmd
import conversion


class PotFieldPredictionService(PotField_pb2_grpc.PredictionServiceServicer):
    def __init__(self) -> None:
        self.model = None
        super().__init__()

    def fit(self, request, context):
        c, v = conversion.convert_fieldsequence_to_ndarray(request.fitData)
        model = edmd.EDMDModel.fit(c, v)
        self.model = model
        return PotField_pb2.PredictionResponse()

    def predict(self, request, context):
        if not self.model: raise Exception('Fitting was not yet executed')
        c, v = conversion.convert_fieldsequence_to_ndarray(request.predictData)
        new_c, prediction = edmd.EDMDModel.predict(self.model, c, v)
        return conversion.convert_ndarray_to_fieldsequence(new_c, prediction)


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10), options = [
        ('grpc.max_send_message_length', 100*1024*1024),
        ('grpc.max_receive_message_length', 100*1024*1024)
    ])
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
