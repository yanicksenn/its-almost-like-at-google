package com.yanicksenn.libraries.datetime;

import com.google.errorprone.annotations.Keep;
import com.yanicksenn.protos.Time;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Random;
import java.util.stream.IntStream;

@RunWith(JUnitParamsRunner.class)
public class DateTimeUtilsValidateTimeTest {

    @Test
    public void validateTime_whenPassingNull_throws() {
        Assert.assertThrows(NullPointerException.class, () -> {
            DateTimeUtils.validateTime(null);
        });
    }

    @Test
    @Parameters({
            "-1, -1, -1, -1",
            "0, 0, 0, 1000000000",
            "0, 0, 60, 0",
            "0, 60, 0, 0",
            "24, 0, 0, 0",
    })
    public void validateTime_whenPassingInvalidTime_throws(int hours, int minutes, int seconds, int nanos) {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            DateTimeUtils.validateTime(Time.newBuilder()
                    .setHours(hours)
                    .setMinutes(minutes)
                    .setSeconds(seconds)
                    .setNanos(nanos)
                    .build());
        });
    }

    @Test
    @Parameters({
            "0, 0, 0, 0",
            "0, 0, 0, 999999999",
            "0, 0, 59, 0",
            "0, 59, 0, 0",
            "23, 0, 0, 0",
    })
    public void validateTime_whenPassingValidTime_doesNotThrow(int hours, int minutes, int seconds, int nanos) {
        DateTimeUtils.validateTime(Time.newBuilder()
                .setHours(hours)
                .setMinutes(minutes)
                .setSeconds(seconds)
                .setNanos(nanos)
                .build());
    }

    @Test
    @Parameters(method = "fuzzyValid")
    public void validateTime_whenFuzzyValid_doesNotThrow(Time time) {
        try {
            DateTimeUtils.validateTime(time);
        } catch (Throwable e) {
            Assert.fail(String.format("fuzzy time %s is not supposed invalid", time));
        }
    }

    @Test
    @Parameters(method = "fuzzyInvalid")
    public void validateTime_whenFuzzyInvalid_throws(Time time) {
        Assert.assertThrows(
                String.format("fuzzy time %s is not supposed valid", time),
                IllegalArgumentException.class, () -> {
            DateTimeUtils.validateTime(time);
        });
    }

    @Keep
    private static Collection<Time> fuzzyValid() {
        FuzzyTestData testData = FuzzyTestData.of(1337);
        return IntStream.range(0, 50)
                .mapToObj(i -> testData.generateValid())
                .toList();
    }

    @Keep
    private static Collection<Time> fuzzyInvalid() {
        FuzzyTestData testData = FuzzyTestData.of(1337);
        return IntStream.range(0, 50)
                .mapToObj(i -> testData.generateInvalid())
                .toList();
    }

    static final class FuzzyTestData {
        private final long seed;
        private final Random random;

        static FuzzyTestData of(long seed) {
            return new FuzzyTestData(seed);
        }

        private FuzzyTestData(long seed) {
            this.seed = seed;
            this.random = new Random(seed);
        }

        Time generateValid() {
            int hours = random.nextInt(0, 24);
            int minutes = random.nextInt(0, 60);
            int seconds = random.nextInt(0, 60);
            int nanos = random.nextInt(0, 1_000_000_000);
            return Time.newBuilder()
                    .setHours(hours)
                    .setMinutes(minutes)
                    .setSeconds(seconds)
                    .setNanos(nanos)
                    .build();
        }

        Time generateInvalid() {
            int hours = random.nextBoolean()
                    ? random.nextInt(Integer.MIN_VALUE, 0)
                    : random.nextInt(24, Integer.MAX_VALUE);
            int minutes = random.nextBoolean()
                    ? random.nextInt(Integer.MIN_VALUE, 0)
                    : random.nextInt(60, Integer.MAX_VALUE);
            int seconds = random.nextBoolean()
                    ? random.nextInt(Integer.MIN_VALUE, 0)
                    : random.nextInt(60, Integer.MAX_VALUE);
            int nanos =  random.nextBoolean()
                    ? random.nextInt(Integer.MIN_VALUE, 0)
                    : random.nextInt(1_000_000_000, Integer.MAX_VALUE);
            return Time.newBuilder()
                    .setHours(hours)
                    .setMinutes(minutes)
                    .setSeconds(seconds)
                    .setNanos(nanos)
                    .build();
        }

        void reset() {
            random.setSeed(seed);
        }
    }
}