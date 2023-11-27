from concurrent import futures

import grpc
from grpc_reflection.v1alpha import reflection

import PotField_pb2
import PotField_pb2_grpc
from .models import edmd


class PotFieldPredictionService(PotField_pb2_grpc.PredictionServiceServicer):
    def fit(self, request, context):
        pass

    def predict(self, request, context):
       pass


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
