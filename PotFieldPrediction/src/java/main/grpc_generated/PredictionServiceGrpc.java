package grpc_generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: grpc.proto")
public final class PredictionServiceGrpc {

  private PredictionServiceGrpc() {}

  public static final String SERVICE_NAME = "PredictionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Grpc.FitRequest,
      Grpc.FitResponse> getFitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "fit",
      requestType = Grpc.FitRequest.class,
      responseType = Grpc.FitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Grpc.FitRequest,
      Grpc.FitResponse> getFitMethod() {
    io.grpc.MethodDescriptor<Grpc.FitRequest, Grpc.FitResponse> getFitMethod;
    if ((getFitMethod = PredictionServiceGrpc.getFitMethod) == null) {
      synchronized (PredictionServiceGrpc.class) {
        if ((getFitMethod = PredictionServiceGrpc.getFitMethod) == null) {
          PredictionServiceGrpc.getFitMethod = getFitMethod = 
              io.grpc.MethodDescriptor.<Grpc.FitRequest, Grpc.FitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "PredictionService", "fit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Grpc.FitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Grpc.FitResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PredictionServiceMethodDescriptorSupplier("fit"))
                  .build();
          }
        }
     }
     return getFitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Grpc.PredictRequest,
      Grpc.PredictionResponse> getPredictMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "predict",
      requestType = Grpc.PredictRequest.class,
      responseType = Grpc.PredictionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Grpc.PredictRequest,
      Grpc.PredictionResponse> getPredictMethod() {
    io.grpc.MethodDescriptor<Grpc.PredictRequest, Grpc.PredictionResponse> getPredictMethod;
    if ((getPredictMethod = PredictionServiceGrpc.getPredictMethod) == null) {
      synchronized (PredictionServiceGrpc.class) {
        if ((getPredictMethod = PredictionServiceGrpc.getPredictMethod) == null) {
          PredictionServiceGrpc.getPredictMethod = getPredictMethod = 
              io.grpc.MethodDescriptor.<Grpc.PredictRequest, Grpc.PredictionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "PredictionService", "predict"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Grpc.PredictRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Grpc.PredictionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PredictionServiceMethodDescriptorSupplier("predict"))
                  .build();
          }
        }
     }
     return getPredictMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PredictionServiceStub newStub(io.grpc.Channel channel) {
    return new PredictionServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PredictionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PredictionServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PredictionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PredictionServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PredictionServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void fit(Grpc.FitRequest request,
        io.grpc.stub.StreamObserver<Grpc.FitResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFitMethod(), responseObserver);
    }

    /**
     */
    public void predict(Grpc.PredictRequest request,
        io.grpc.stub.StreamObserver<Grpc.PredictionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPredictMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFitMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Grpc.FitRequest,
                Grpc.FitResponse>(
                  this, METHODID_FIT)))
          .addMethod(
            getPredictMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Grpc.PredictRequest,
                Grpc.PredictionResponse>(
                  this, METHODID_PREDICT)))
          .build();
    }
  }

  /**
   */
  public static final class PredictionServiceStub extends io.grpc.stub.AbstractStub<PredictionServiceStub> {
    private PredictionServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceStub(channel, callOptions);
    }

    /**
     */
    public void fit(Grpc.FitRequest request,
        io.grpc.stub.StreamObserver<Grpc.FitResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void predict(Grpc.PredictRequest request,
        io.grpc.stub.StreamObserver<Grpc.PredictionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPredictMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PredictionServiceBlockingStub extends io.grpc.stub.AbstractStub<PredictionServiceBlockingStub> {
    private PredictionServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Grpc.FitResponse fit(Grpc.FitRequest request) {
      return blockingUnaryCall(
          getChannel(), getFitMethod(), getCallOptions(), request);
    }

    /**
     */
    public Grpc.PredictionResponse predict(Grpc.PredictRequest request) {
      return blockingUnaryCall(
          getChannel(), getPredictMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PredictionServiceFutureStub extends io.grpc.stub.AbstractStub<PredictionServiceFutureStub> {
    private PredictionServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PredictionServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredictionServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PredictionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Grpc.FitResponse> fit(
        Grpc.FitRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getFitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Grpc.PredictionResponse> predict(
        Grpc.PredictRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPredictMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIT = 0;
  private static final int METHODID_PREDICT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PredictionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PredictionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIT:
          serviceImpl.fit((Grpc.FitRequest) request,
              (io.grpc.stub.StreamObserver<Grpc.FitResponse>) responseObserver);
          break;
        case METHODID_PREDICT:
          serviceImpl.predict((Grpc.PredictRequest) request,
              (io.grpc.stub.StreamObserver<Grpc.PredictionResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PredictionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PredictionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Grpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PredictionService");
    }
  }

  private static final class PredictionServiceFileDescriptorSupplier
      extends PredictionServiceBaseDescriptorSupplier {
    PredictionServiceFileDescriptorSupplier() {}
  }

  private static final class PredictionServiceMethodDescriptorSupplier
      extends PredictionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PredictionServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PredictionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PredictionServiceFileDescriptorSupplier())
              .addMethod(getFitMethod())
              .addMethod(getPredictMethod())
              .build();
        }
      }
    }
    return result;
  }
}
