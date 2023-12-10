package grpc_server;

import grpc_generated.Grpc;
import grpc_generated.PredictionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import mock.*;

import java.io.File;
import java.util.List;

public class grpcClient {

    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .maxInboundMessageSize(100 * 1024 * 1024)// This is just for the sake of simplicity. In production, you should use encryption.
                .build();

        // Example of using the generated gRPC client (assuming a running gRPC server)
        PredictionServiceGrpc.PredictionServiceBlockingStub blockingStub = PredictionServiceGrpc.newBlockingStub(channel);

        String folderPath = "src/python/tests/assets/potFields_small";
        int csvFileCount = 0;

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                    csvFileCount++;
                }
            }
        } else {
            System.out.println("Invalid folder path.");
        }

        FieldSequenceI fieldSequence = new FieldSequence();
        for (int i = 0 ; i < csvFileCount ; i++) {
            fieldSequence.addField("src/python/tests/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-" + i + ".csv");
        }
        List<FieldI> fields = fieldSequence.getFields();

        Grpc.FieldSequence.Builder fieldSequenceRequest = Grpc.FieldSequence.newBuilder();
        for (int i = 0 ; i < fields.size() - 1; i++) {

            FieldI field = fields.get(i);
            List<CoordinateI> coordinates = field.getCoordinates();

            Grpc.Field.Builder fieldRequest = Grpc.Field.newBuilder();

            for (int j = 0 ; j < coordinates.size(); j ++) {
                Grpc.Coordinate coordinateRequest = Grpc.Coordinate.newBuilder()
                        .setX(coordinates.get(j).getxIndex())
                        .setY(coordinates.get(j).getyIndex())
                        .setValue(coordinates.get(j).getValue()).build();
                fieldRequest.addCoordinates(coordinateRequest);
            }
            fieldSequenceRequest.addFields(fieldRequest);
        }

        Grpc.FitRequest fitRequest = Grpc.FitRequest.newBuilder().setFitData(fieldSequenceRequest).build();
        // Call to predict RPC
        Grpc.FitResponse fitResponse = blockingStub.fit(fitRequest);

        Grpc.PredictRequest predictRequest = Grpc.PredictRequest.newBuilder().setField(fieldSequenceRequest)
                .setSteps(5).build();
        Grpc.PredictionResponse predictionResponse = blockingStub.predict(predictRequest);
        System.out.println("Prediction response: " + predictionResponse);


        // Shutdown the channel
        channel.shutdown();
    }
}


