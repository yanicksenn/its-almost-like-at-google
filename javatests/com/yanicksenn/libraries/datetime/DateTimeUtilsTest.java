package com.yanicksenn.libraries.datetime;

import com.yanicksenn.protos.Time;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(JUnitParamsRunner.class)
public class DateTimeUtilsTest {

    @Test
    @Parameters({
        "0, 0, 0, 0",
        "0, 0, 0, 999999999",
        "0, 0, 59, 0",
        "0, 59, 0, 0",
        "23, 0, 0, 0",
    })
    public void validateTime_valid(int hours, int minutes, int seconds, int nanos) {
        DateTimeUtils.validateTime(Time.newBuilder()
                .setHours(hours)
                .setMinutes(minutes)
                .setSeconds(seconds)
                .setNanos(nanos)
                .build());
    }

    @Test
    @Parameters({
            "-1, -1, -1, -1",
            "0, 0, 0, 1000000000",
            "0, 0, 60, 0",
            "0, 60, 0, 0",
            "24, 0, 0, 0",
    })
    public void validateTime_invalid(int hours, int minutes, int seconds, int nanos) {
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
    public void validateTimestamp() {

    }
}