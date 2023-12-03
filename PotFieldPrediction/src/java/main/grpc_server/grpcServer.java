package grpc_server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class grpcServer {

    public static void main(String[] args) throws IOException {
        Server server = ServerBuilder.forPort(9090).addService(new grpcClient()).build();

        server.start();

        System.out.println("Server started at port: " + server.getPort());

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

