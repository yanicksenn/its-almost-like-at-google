package com.yanicksenn.hellogrpc;

import com.yanicksenn.hellogrpc.proto.HelloRequest;
import com.yanicksenn.hellogrpc.proto.HelloResponse;
import com.yanicksenn.hellogrpc.proto.HelloServiceGrpc;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerCredentials;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class HelloServer {
  private final int port;
  private final ServerCredentials serverCredentials;
  private final Server server;

  public static void main(String[] args) throws IOException, InterruptedException {
    new HelloServer(/* port= */ 8383, InsecureServerCredentials.create())
        .startAndAwaitTermination();
  }

  private HelloServer(int port, ServerCredentials serverCredentials) {
    this.port = port;
    this.serverCredentials = serverCredentials;
    this.server =
        Grpc.newServerBuilderForPort(port, serverCredentials)
            .addService(new HelloServiceImpl())
            .build();
  }

  public void startAndAwaitTermination() throws IOException, InterruptedException {
    Runtime.getRuntime().addShutdownHook(new ShutdownHook(/* helloServer= */ this));
    server.start();
    System.out.println("*** Server started, listening on port " + port + ".");
    server.awaitTermination();
  }

  public void stop() throws InterruptedException {
    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
  }

  private static final class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
      System.out.println(System.currentTimeMillis() + " - request: " + req.getName());
      responseObserver.onNext(
          HelloResponse.newBuilder().setMessage("Hello, " + req.getName() + "!").build());
      responseObserver.onCompleted();
    }
  }

  private static final class ShutdownHook extends Thread {
    private final HelloServer helloServer;

    ShutdownHook(HelloServer helloServer) {
      this.helloServer = helloServer;
    }

    @Override
    public void run() {
      System.err.println("\n*** shutting down gRPC server since JVM is shutting down.");
      try {
        helloServer.stop();
      } catch (InterruptedException e) {
        e.printStackTrace(System.err);
      }
      System.err.println("*** server shut down.");
    }
  }
}
