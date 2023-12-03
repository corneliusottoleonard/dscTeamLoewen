package grpc_server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class grpcClientTest {

    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext() // This is just for the sake of simplicity. In production, you should use encryption.
                .build();

        // Shutdown the channel
        channel.shutdown();
    }
}


