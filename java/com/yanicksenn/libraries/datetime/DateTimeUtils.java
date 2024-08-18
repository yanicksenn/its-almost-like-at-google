package com.yanicksenn.libraries.datetime;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.yanicksenn.libraries.ranges.IntRange;
import com.yanicksenn.protos.*;

import java.util.Comparator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class DateTimeUtils {

    public static Comparator<Timestamp> TIMESTAMP_COMPARATOR = Comparator
            .comparingLong(Timestamp::getSeconds)
            .thenComparingLong(Timestamp::getNanos);

    public static Comparator<Date> DATE_COMPARATOR = Comparator
            .comparingLong(Date::getYear)
            .thenComparingInt(Date::getMonth)
            .thenComparingInt(Date::getDay);

    public static final IntRange MONTH_RANGE = IntRange.between(1, 12);
    public static final IntRange HOURS_RANGE = IntRange.until(24);
    public static final IntRange MINUTES_RANGE = IntRange.until(60);
    public static final IntRange SECONDS_RANGE = IntRange.until(60);
    public static final IntRange NANOS_RANGE = IntRange.until(1_000_000_000);

    private static final ImmutableSet<Integer> MONTHS_30 = ImmutableSet.of(4, 6, 9, 11);
    private static final long SECONDS_IN_DAY = 60 * 60 * 24;
    private static final ImmutableMap<TimeFormattingOptions.Precision, Integer> TIME_FORMATTING_PRECISION =
            ImmutableMap.of(
                    TimeFormattingOptions.Precision.NANOS, 9,
                    TimeFormattingOptions.Precision.MICROS, 6,
                    TimeFormattingOptions.Precision.MILLIS, 3
            );

    public static Timestamp toTimestamp(Instant instant) {
        checkNotNull(instant);
        Time time = instant.getTime();
        long daysInSeconds = (getDaysFromCivil(instant.getDate()) * SECONDS_IN_DAY);
        long hoursInSeconds = (long) time.getHours() * 60 * 60;
        long minutesInSeconds = (long) time.getMinutes() * 60;
        long seconds = daysInSeconds
                + hoursInSeconds + minutesInSeconds + time.getSeconds();
        int nanos = instant.getTime().getNanos();
        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }

    public static Instant toInstant(Timestamp timestamp) {
        checkNotNull(timestamp);
        return Instant.newBuilder()
                .setDate(getDate(timestamp))
                .setTime(getTime(timestamp))
                .build();
    }

    public static Date getDate(Timestamp timestamp) {
        checkNotNull(timestamp);
        long days = timestamp.getSeconds() / SECONDS_IN_DAY;
        return getCivilFromDays(days);
    }

    public static Time getTime(Timestamp timestamp) {
        checkNotNull(timestamp);
        long time = timestamp.getSeconds() % SECONDS_IN_DAY;
        int seconds = (int) (time % 60);
        int minutes = (int) (time / 60 % 60);
        int hours = (int) (time / 60 / 60 % 24);
        return Time.newBuilder()
                .setHours(hours)
                .setMinutes(minutes)
                .setSeconds(seconds)
                .setNanos(timestamp.getNanos())
                .build();
    }

    public static String formatInstant(Instant instant) {
        validateInstant(instant);
        return formatInstant(instant, FormattingOptions.getDefaultInstance());
    }

    public static String formatInstant(Instant instant, FormattingOptions formattingOptions) {
        checkNotNull(instant);
        checkNotNull(formattingOptions);
        return String.format("%s %s",
                formatDate(instant.getDate()),
                formatTime(instant.getTime(), formattingOptions.getTime()));
    }

    public static String formatDate(Date date) {
        checkNotNull(date);
        return String.format("%04d-%02d-%02d",
                date.getYear(),
                date.getMonth(),
                date.getDay());
    }

    public static String formatTime(Time time, TimeFormattingOptions timeFormattingOptions) {
        validateTime(time);
        checkNotNull(timeFormattingOptions);
        TimeFormattingOptions.Precision precision = timeFormattingOptions.getPrecision();
        return switch (precision) {
            case NANOS -> formatTimeWithNanosPrecision(time);
            case MICROS -> formatTimeWithMicrosPrecision(time);
            case MILLIS -> formatTimeWithMillisPrecision(time);
            case SECONDS -> formatTimeWithSecondsPrecision(time);
            case MINUTES -> formatTimeWithMinutesPrecision(time);
            case UNRECOGNIZED -> throw new IllegalArgumentException("precision is unrecognized");
        };
    }

    public static String formatTimeWithNanosPrecision(Time time) {
        validateTime(time);
        return String.format("%02d:%02d:%02d.%09d",
                time.getHours(),
                time.getMinutes(),
                time.getSeconds(),
                time.getNanos());
    }

    public static String formatTimeWithMicrosPrecision(Time time) {
        validateTime(time);
        return String.format("%02d:%02d:%02d.%06d",
                time.getHours(),
                time.getMinutes(),
                time.getSeconds(),
                time.getNanos() / 1_000);
    }

    public static String formatTimeWithMillisPrecision(Time time) {
        validateTime(time);
        return String.format("%02d:%02d:%02d.%03d",
                time.getHours(),
                time.getMinutes(),
                time.getSeconds(),
                time.getNanos() / 1_000_000);
    }

    public static String formatTimeWithSecondsPrecision(Time time) {
        validateTime(time);
        return String.format("%02d:%02d:%02d",
                time.getHours(),
                time.getMinutes(),
                time.getSeconds());
    }

    public static String formatTimeWithMinutesPrecision(Time time) {
        validateTime(time);
        return String.format("%02d:%02d",
                time.getHours(),
                time.getMinutes());
    }

    public static void validateInstant(Instant instant) {
        checkNotNull(instant);
        validateDate(instant.getDate());
        validateTime(instant.getTime());
    }

    public static void validateDate(Date date) {
        checkNotNull(date);
        checkArgument(MONTH_RANGE.contains(date.getMonth()));
        IntRange DAY_RANGE = IntRange.to(getDaysOfMonth(date.getYear(), date.getMonth()));
        checkArgument(DAY_RANGE.contains(date.getDay()));
    }

    public static void validateTime(Time time) {
        checkNotNull(time);
        checkArgument(HOURS_RANGE.contains(time.getHours()));
        checkArgument(MINUTES_RANGE.contains(time.getMinutes()));
        checkArgument(SECONDS_RANGE.contains(time.getSeconds()));
        checkArgument(NANOS_RANGE.contains(time.getNanos()));
    }

    public static void validateTimestamp(Timestamp timestamp) {
        checkNotNull(timestamp);
        checkArgument(NANOS_RANGE.contains(timestamp.getNanos()));
    }

    private static Date getCivilFromDays(long days) {
        // See https://howardhinnant.github.io/date_algorithms.html
        long z = days + 719468;
        long era = (z >= 0 ? z : z - 146096) / 146097;
        long doe = (z - era * 146097);
        long yoe = (doe - doe / 1460 + doe / 36524 - doe / 146096) / 365;
        long y = yoe + era * 400;
        long doy = doe - (365 * yoe + yoe / 4 - yoe / 100);
        long mp = (5 * doy + 2) / 153;
        int d = (int) (doy - (153 * mp + 2) / 5 + 1);
        int m = (int) (mp < 10 ? mp + 3 : mp - 9);
        long year = y + (m <= 2 ? 1 : 0);
        return Date.newBuilder()
                .setYear(year)
                .setMonth(m)
                .setDay(d)
                .build();
    }

    private static long getDaysFromCivil(Date date) {
        // See https://howardhinnant.github.io/date_algorithms.html

        checkNotNull(date);
        int m = date.getMonth();
        checkArgument(MONTH_RANGE.contains(m));

        long year = date.getYear();
        int d = date.getDay();
        checkArgument(d >= 1 && d <= getDaysOfMonth(year, m));

        long y = year - (m <= 2 ? 1 : 0);
        long era = (y >= 0 ? y : y - 399) / 400;
        long yoe = y - (era * 400);
        long doy = (153L * (m > 2 ? m - 3 : m + 9) + 2) / 5 + d - 1;
        long doe = yoe * 365 + yoe / 4 - yoe / 100 + doy;
        return era * 146097 + doe - 719468;
    }

    public static int getDaysOfMonth(long year, int month) {
        checkArgument(MONTH_RANGE.contains(month));
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        } else {
            return MONTHS_30.contains(month) ? 30 : 31;
        }
    }

    private static boolean isLeapYear(long year) {
        if (year % 4 != 0) {
            return false;
        }

        if (year % 100 != 0) {
            return true;
        }

        return year % 400 == 0;
    }

    private DateTimeUtils() {
        throw new AssertionError();
    }
}
