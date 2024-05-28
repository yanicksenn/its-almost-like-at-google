package com.yanicksenn.hellogrpc;

import com.yanicksenn.hellogrpc.proto.HelloRequest;
import com.yanicksenn.hellogrpc.proto.HelloResponse;
import com.yanicksenn.hellogrpc.proto.HelloServiceGrpc;
import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.ChannelCredentials;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;

public final class HelloClient {
  private final String target;
  private final ChannelCredentials channelCredentials;
  private final ManagedChannel channel;
  private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

  public static void main(String[] args) throws InterruptedException {
    HelloClient client = new HelloClient("localhost:8383", InsecureChannelCredentials.create());
    client.sayHello();
    client.stop();
  }

  private HelloClient(String target, ChannelCredentials channelCredentials) {
    this.target = target;
    this.channelCredentials = channelCredentials;
    channel = Grpc.newChannelBuilder(target, channelCredentials).build();
    blockingStub = HelloServiceGrpc.newBlockingStub(channel);
  }

  private void sayHello() {
    try {
      HelloRequest request = HelloRequest.newBuilder().setName("Yanick").build();
      HelloResponse response = blockingStub.sayHello(request);
      System.out.println(System.currentTimeMillis() + " - " + response.getMessage());
    } catch (StatusRuntimeException e) {
      e.printStackTrace(System.err);
    }
  }

  private void stop() throws InterruptedException {
    channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
  }
}
