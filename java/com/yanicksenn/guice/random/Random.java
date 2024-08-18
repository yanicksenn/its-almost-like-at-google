package com.yanicksenn.guice.random;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import com.google.inject.multibindings.OptionalBinder;
import com.yanicksenn.libraries.ranges.IntRange;
import com.yanicksenn.libraries.ranges.LongRange;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public interface Random {
    void setSeed(long seed);

    <T> T from(List<T> list);

    int between(int min, int max);

    int inRange(IntRange range);

    long inRange(LongRange range);



    final class Module extends AbstractModule {
        @Override
        protected void configure() {
            OptionalBinder.newOptionalBinder(binder(), Key.get(Long.class, Annotations.InitialSeed.class));
            bind(Random.class).to(RandomImpl.class);
        }
    }

    final class Annotations {
        @Target({ FIELD, PARAMETER, METHOD })
        @Retention(RUNTIME)
        @BindingAnnotation
        public @interface InitialSeed {}

        private Annotations() {
            throw new AssertionError();
        }
    }
}
