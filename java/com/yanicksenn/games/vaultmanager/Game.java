package com.yanicksenn.games.vaultmanager;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.yanicksenn.games.vaultmanager.proto.DeathOperation;
import com.yanicksenn.games.vaultmanager.proto.GameResources;
import com.yanicksenn.games.vaultmanager.proto.Human;
import com.yanicksenn.games.vaultmanager.proto.Operation;
import com.yanicksenn.games.vaultmanager.proto.PlopOperation;
import com.yanicksenn.libraries.ranges.IntRange;
import com.yanicksenn.libraries.resources.Resource;
import com.yanicksenn.libraries.resources.Resources;
import com.yanicksenn.protos.Date;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Singleton
public class Game implements Runnable {

    private final Generator generator;
    private final Population population;
    private final Calendar calendar;
    private final OperationQueue operationQueue;

    @Inject
    Game(Generator generator, Population population, Calendar calendar, OperationQueue operationQueue) {
        this.generator = generator;
        this.population = population;
        this.calendar = calendar;
        this.operationQueue = operationQueue;
    }

    @Override
    public void run() {
        Date birthday = generator.getRandomDate(IntRange.between(1985, 1995));
        Human.Id humanId = population.nextId();
        Human.Properties humanProperties = generator.getRandomHumanProperties(birthday);

        operationQueue.enqueue(
            Operation.newBuilder()
                .setDate(calendar.today())
                .setPlopOperation(PlopOperation.newBuilder()
                    .setHumanId(humanId)
                    .setHumanProperties(humanProperties))
                .build());

        operationQueue.enqueue(
            Operation.newBuilder()
                .setDate(calendar.today())
                .setDeathOperation(DeathOperation.newBuilder()
                    .setHumanId(humanId))
                .build());

        operationQueue.enqueue(
            Operation.newBuilder()
                .setDate(calendar.today())
                .setDeathOperation(DeathOperation.newBuilder()
                    .setHumanId(humanId))
                .build());

        while (operationQueue.hasMoreOperations()) {
            operationQueue.handle();
        }
    }

    public static class Module extends AbstractModule {

        private static final Resource RESOURCE_FIRSTNAMES_MALE = Resource.builder()
            .setModule(Module.class)
            .setResourcePath("resources/firstnames_male.txt")
            .build();

        private static final Resource RESOURCE_FIRSTNAMES_FEMALE = Resource.builder()
            .setModule(Module.class)
            .setResourcePath("resources/firstnames_female.txt")
            .build();

        private static final Resource RESOURCE_LASTNAMES = Resource.builder()
            .setModule(Module.class)
            .setResourcePath("resources/lastnames.txt")
            .build();

        public static final class Annotations {
            @Target({ FIELD, PARAMETER, METHOD })
            @Retention(RUNTIME)
            public @interface Seed {}

            @Target({ FIELD, PARAMETER, METHOD })
            @Retention(RUNTIME)
            public @interface Resources {}

            @Target({ FIELD, PARAMETER, METHOD })
            @Retention(RUNTIME)
            public @interface StartDate {}

            private Annotations() {
                throw new AssertionError();
            }
        }

        @Provides
        @Annotations.Seed
        public long provideGameSeed() {
            return 1234L;
        }

        @Provides
        @Annotations.Resources
        public GameResources provideGameResources() throws IOException {
            return GameResources.newBuilder()
                .addAllMaleFirstnames(RESOURCE_FIRSTNAMES_MALE.parseList())
                .addAllFemaleFirstnames(RESOURCE_FIRSTNAMES_FEMALE.parseList())
                .addAllLastnames(RESOURCE_LASTNAMES.parseList())
                .build();
        }

        @Provides
        @Annotations.StartDate
        public Date providesGameStartDate() {
            return Date.newBuilder()
                .setYear(2024)
                .setMonth(7)
                .setDay(13)
                .build();
        }
    }
}
