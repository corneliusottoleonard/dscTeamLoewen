package grpc_server;

import grpc_generated.Grpc;
import grpc_generated.PredictionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import mock.*;

public class grpcClientTest {

    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext() // This is just for the sake of simplicity. In production, you should use encryption.
                .build();

        // Example of using the generated gRPC client (assuming a running gRPC server)
        PredictionServiceGrpc.PredictionServiceBlockingStub blockingStub = PredictionServiceGrpc.newBlockingStub(channel);

        Field field1 = new Field("path");
        FieldSequence fieldSequence = new FieldSequence();

        Grpc.FitRequest fitRequest = Grpc.FieldSequence.newBuilder().setFields(field1).build();
        Grpc.PredictRequest predictRequest = Grpc.PredictRequest.newBuilder().setField(fieldSequence).setSteps(10).build();

        // Call the fit RPC
        Grpc.FitResponse fitResponse = blockingStub.fit(fitRequest);
        System.out.println("Fit response: " + fitResponse);

        // Call the predict RPC
        Grpc.PredictionResponse predictionResponse = blockingStub.predict(predictRequest);
        System.out.println("Prediction response: " + predictionResponse);



        // Shutdown the channel
        channel.shutdown();
    }
}


