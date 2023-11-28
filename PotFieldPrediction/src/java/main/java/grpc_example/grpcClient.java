package grpc_example;

import grpc.GreeterGrpc;
import grpc.Grpc;
import io.grpc.stub.StreamObserver;

public class grpcClient extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(Grpc.HelloRequest request, StreamObserver<Grpc.HelloReply> responseObserver) {
        Grpc.HelloReply reply = Grpc.HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
