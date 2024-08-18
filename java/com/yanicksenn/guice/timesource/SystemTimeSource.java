package com.yanicksenn.guice.timesource;

import com.yanicksenn.protos.Timestamp;


public class SystemTimeSource implements TimeSource {
    @Override
    public Timestamp now() {
        int nanos = (int) System.nanoTime() % 1_000_000_000;
        long seconds = System.currentTimeMillis() / 1000;
        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }
}
