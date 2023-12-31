package grpc_server;

import grpc_generated.Grpc;
import grpc_generated.PredictionServiceGrpc;
import io.grpc.stub.StreamObserver;

public class grpcService extends PredictionServiceGrpc.PredictionServiceImplBase {

    @Override
    public void fit(Grpc.FitRequest request, StreamObserver<Grpc.FitResponse> responseObserver) {
        // Implement your fit logic here
        Grpc.FitResponse response = Grpc.FitResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void predict(Grpc.PredictRequest request, StreamObserver<Grpc.PredictionResponse> responseObserver) {
        // Implement your predict logic here
        Grpc.PredictionResponse predictionResponse = Grpc.PredictionResponse.newBuilder()
                .setPredictedData(request.getField()).build();
        responseObserver.onNext(predictionResponse);
        responseObserver.onCompleted();
    }

}

