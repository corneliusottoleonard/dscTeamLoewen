package grpc_example;

import grpc.GreeterGrpc;
import grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class grpcClientTest {

    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext() // This is just for the sake of simplicity. In production, you should use encryption.
                .build();

        // Create a gRPC client
        GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);

        // Create a request
        Grpc.HelloRequest request = Grpc.HelloRequest.newBuilder().setName("Gregor").build();

        // Call the sayHello method
        Grpc.HelloReply response = blockingStub.sayHello(request);

        // Print the response
        System.out.println("Response from server: " + response.getMessage());

        // Shutdown the channel
        channel.shutdown();
    }
}
