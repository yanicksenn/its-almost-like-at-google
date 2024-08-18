package com.yanicksenn.guice.timesource;

import com.google.inject.AbstractModule;
import com.yanicksenn.protos.Timestamp;

public interface TimeSource {
    Timestamp now();

    final class Module extends AbstractModule {
        @Override
        protected void configure() {
            bind(TimeSource.class).to(SystemTimeSource.class);
        }
    }
}
