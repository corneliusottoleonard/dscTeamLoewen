package grpc_server;

import grpc_generated.Grpc;
import grpc_generated.PredictionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import mock.*;

import java.util.List;

public class grpcClientTest {

    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext() // This is just for the sake of simplicity. In production, you should use encryption.
                .build();

        // Example of using the generated gRPC client (assuming a running gRPC server)
        PredictionServiceGrpc.PredictionServiceBlockingStub blockingStub = PredictionServiceGrpc.newBlockingStub(channel);

        FieldSequenceI fieldSequence = new FieldSequence();
        fieldSequence.addField("src/python/tests/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-0.csv");
        List<FieldI> fields = fieldSequence.getFields();
        FieldI field1 = fields.get(0);


        Grpc.Coordinate coordinateRequest = Grpc.Coordinate.newBuilder()
                .setX(field1.getCoordinates().get(0).getxIndex())
                .setY(field1.getCoordinates().get(0).getyIndex())
                .setZ(field1.getCoordinates().get(0).getValue()).build();

        Grpc.Field fieldRequest = Grpc.Field.newBuilder().setCoordinates(0, coordinateRequest).build();

        Grpc.FieldSequence fieldSequenceRequest = Grpc.FieldSequence.newBuilder()
                .setFields(0,fieldRequest).build();


        Grpc.FitRequest fitRequest = Grpc.FitRequest.newBuilder().setFitData(fieldSequenceRequest).build();

        Grpc.PredictRequest predictRequest = Grpc.PredictRequest.newBuilder().setField(fieldSequenceRequest)
                .setSteps(10).build();


        // Call the predict RPC
        Grpc.PredictionResponse predictionResponse = blockingStub.predict(predictRequest);
        System.out.println("Prediction response: " + predictionResponse);


        // Shutdown the channel
        channel.shutdown();
    }
}


