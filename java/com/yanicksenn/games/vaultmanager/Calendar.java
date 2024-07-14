package com.yanicksenn.games.vaultmanager;

import com.google.auto.value.AutoValue;
import com.yanicksenn.libraries.ranges.IntRange;
import com.google.common.collect.ImmutableMap;
import com.yanicksenn.games.vaultmanager.Game.Module.Annotations.StartDate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.protos.Date;

import java.util.Optional;

/**
 * Represents a calendar with its own date instance.
 */
@Singleton
public final class Calendar {
    public static IntRange MONTH_RANGE = IntRange.of(1, 13);
    private static final ImmutableMap<Integer, DaysPerMonth> DAYS_PER_MONTH =
            new ImmutableMap.Builder<Integer, DaysPerMonth>()
                    .put(1, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(31)
                            .build())
                    .put(2, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(28)
                            .setDaysDuringLeapYear(29)
                            .build())
                    .put(3, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(31)
                            .build())
                    .put(4, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(30)
                            .build())
                    .put(5, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(31)
                            .build())
                    .put(6, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(30)
                            .build())
                    .put(7, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(31)
                            .build())
                    .put(8, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(31)
                            .build())
                    .put(9, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(30)
                            .build())
                    .put(10, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(31)
                            .build())
                    .put(11, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(30)
                            .build())
                    .put(12, DaysPerMonth.builder()
                            .setDaysDuringNormalYear(31)
                            .build())
                    .build();

    private Date currentDate;

    public static IntRange getDaysPerMonthRange(int year, int month) {
        if (isInvalidYear(year)) {
            throw new IllegalArgumentException("year must be greater or equal to 0");
        }

        if (isInvalidMonth(month)) {
            throw new IllegalArgumentException("month must be between 1 and 12");
        }

        DaysPerMonth daysPerMonth = DAYS_PER_MONTH.get(month);
        assert daysPerMonth != null;

        int days = isLeapYear(year)
                ? daysPerMonth.getDaysDuringNormalYear()
                : daysPerMonth.getDaysDuringLeapYear()
                .orElse(daysPerMonth.getDaysDuringNormalYear());
        return IntRange.of(1, days + 1);
    }

    @Inject
    Calendar(@StartDate Date startDate) {
        if (!isValidDate(startDate)) {
            throw new IllegalArgumentException("startDate must be valid");
        }
        this.currentDate = startDate;
    }

    public Date today() {
        return currentDate;
    }

    public Date tomorrow() {
        if (isOnLastDayOfMonth(currentDate)) {
            if (isOnLastMonthOfYear(currentDate)) {
                return Date.newBuilder()
                        .setYear(currentDate.getYear() + 1)
                        .setMonth(1)
                        .setDay(1)
                        .build();
            } else {
                return currentDate.toBuilder()
                        .setMonth(currentDate.getMonth() + 1)
                        .setDay(1)
                        .build();
            }
        } else {
            return currentDate.toBuilder()
                    .setDay(currentDate.getDay() + 1)
                    .build();
        }
    }

    public void advance() {
        currentDate = tomorrow();
    }

    private static boolean isValidDate(Date date) {
        if (isInvalidYear(date.getYear()) ||
                isInvalidMonth(date.getMonth())) {
            return false;
        }

        return getDaysPerMonthRange(date.getYear(), date.getMonth())
                .contains(date.getDay());
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private static boolean isOnLastDayOfMonth(Date date) {
        return date.getDay() == getDaysPerMonthRange(date.getYear(), date.getMonth()).size();
    }

    private static boolean isOnLastMonthOfYear(Date date) {
        return date.getMonth() == 12;
    }

    private static boolean isInvalidYear(int year) {
        return year < 0;
    }

    private static boolean isInvalidMonth(int month) {
        return month < 1 || month > 12;
    }


    @AutoValue
    static abstract class DaysPerMonth {
        abstract int getDaysDuringNormalYear();

        abstract Optional<Integer> getDaysDuringLeapYear();

        static Builder builder() {
            return new AutoValue_Calendar_DaysPerMonth.Builder();
        }

        @AutoValue.Builder
        static abstract class Builder {
            abstract Builder setDaysDuringNormalYear(int daysDuringNormalYear);

            abstract Builder setDaysDuringLeapYear(int daysDuringLeapYear);

            abstract DaysPerMonth build();
        }
    }
}