package com.yanicksenn.experimental.helloproto;

import com.google.common.collect.ImmutableList;
import com.yanicksenn.experimental.helloproto.proto.Payload;
import com.yanicksenn.experimental.helloproto.proto.Payload.Flag;

public final class HelloProto {
  public static void main(String[] args) {
    System.out.println(
        Payload.newBuilder()
            .setName("Payload")
            .setTimestamp(System.currentTimeMillis())
            .setData("Lorem ipsum dolor sit amet")
            .addAllFlags(
                ImmutableList.of(
                    Flag.newBuilder().setKey("ABC").setValue("DEF").build(),
                    Flag.newBuilder().setKey("123").setValue("456").build()))
            .build());
  }

  private HelloProto() {
    throw new AssertionError();
  }
}
