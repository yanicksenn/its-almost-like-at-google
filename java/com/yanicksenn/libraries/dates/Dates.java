package com.yanicksenn.libraries.dates;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import com.yanicksenn.libraries.ranges.IntRange;
import com.yanicksenn.protos.Date;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public final class Dates {

    public static final class Months {
        public static final int JANUARY = 1;
        public static final int FEBRUARY = 2;
        public static final int MARCH = 3;
        public static final int APRIL = 4;
        public static final int MAY = 5;
        public static final int JUNE = 6;
        public static final int JULY = 7;
        public static final int AUGUST = 8;
        public static final int SEPTEMBER = 9;
        public static final int OCTOBER = 10;
        public static final int NOVEMBER = 11;
        public static final int DECEMBER = 12;

        public static final IntRange RANGE = IntRange.between(JANUARY, DECEMBER);

        private Months() {
            throw new AssertionError();
        }
    }

    public static final Comparator<Date> COMPARATOR = Comparator
            .comparingInt(Date::getYear)
            .thenComparingInt(Date::getMonth)
            .thenComparingInt(Date::getDay);

    private static final ImmutableMap<Integer, DaysPerMonth> DAYS_PER_MONTH =
            ImmutableMap.<Integer, DaysPerMonth>builder()
                .put(Months.JANUARY, DaysPerMonth.builder().setInNormalYear(31).build())
                .put(Months.FEBRUARY, DaysPerMonth.builder().setInNormalYear(28).setInLeapYear(29).build())
                .put(Months.MARCH, DaysPerMonth.builder().setInNormalYear(31).build())
                .put(Months.APRIL, DaysPerMonth.builder().setInNormalYear(30).build())
                .put(Months.MAY, DaysPerMonth.builder().setInNormalYear(31).build())
                .put(Months.JUNE, DaysPerMonth.builder().setInNormalYear(30).build())
                .put(Months.JULY, DaysPerMonth.builder().setInNormalYear(31).build())
                .put(Months.AUGUST, DaysPerMonth.builder().setInNormalYear(31).build())
                .put(Months.SEPTEMBER, DaysPerMonth.builder().setInNormalYear(30).build())
                .put(Months.OCTOBER, DaysPerMonth.builder().setInNormalYear(31).build())
                .put(Months.NOVEMBER, DaysPerMonth.builder().setInNormalYear(30).build())
                .put(Months.DECEMBER, DaysPerMonth.builder().setInNormalYear(31).build())
                .build();

    public static Date requireValid(Date date) {
        if (!isValid(date)) {
            throw new IllegalStateException(String.format("date %s is not valid", format(date)));
        }
        return date;
    }

    public static boolean isValid(Date date) {
        Objects.requireNonNull(date);

        if (!Months.RANGE.contains(date.getMonth())) {
            return false;
        }

        DaysPerMonth dpm = DAYS_PER_MONTH.get(date.getMonth());
        if (dpm == null) {
            // If this happens then there must've been a programming mistake.
            return false;
        }

        int days = getDaysOfMonth(date.getYear(), date.getMonth());
        return IntRange.between(1, days).contains(date.getDay());
    }

    public static int getDaysOfMonth(int year, int month) {
        if (!Months.RANGE.contains(month)) {
            throw new IllegalArgumentException(String.format("month %d is not valid", month));
        }

        DaysPerMonth dpm = DAYS_PER_MONTH.get(month);
        return isLeapYear(year)
                ? dpm.getInLeapYear().orElse(dpm.getInNormalYear())
                : dpm.getInNormalYear();
    }

    public static String format(Date date) {
        return String.format("%04d-%02d-%02d", date.getYear(), date.getMonth(), date.getDay());
    }

    public static Date getTomorrow(Date today) {
        Dates.requireValid(today);
        if (isLastDayOfMonth(today)) {
            if (isLastDayOfYear(today)) {
                return Date.newBuilder()
                        .setYear(today.getYear() + 1)
                        .setMonth(1)
                        .setDay(1)
                        .build();
            } else {
                return today.toBuilder()
                        .setMonth(today.getMonth() + 1)
                        .setDay(1)
                        .build();
            }
        } else {
            return today.toBuilder()
                    .setDay(today.getDay() + 1)
                    .build();
        }
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

    private static boolean isLastDayOfYear(Date date) {
        Dates.requireValid(date);
        return date.getMonth() == Months.DECEMBER
            && isLastDayOfMonth(date);
    }

    private static boolean isLastDayOfMonth(Date date) {
        Objects.requireNonNull(date);
        Dates.requireValid(date);
        int days = getDaysOfMonth(date.getYear(), date.getMonth());
        return date.getDay() == days;
    }

    private static boolean isFirstDayOfYear(Date date) {
        Objects.requireNonNull(date);
        Dates.requireValid(date);
        return date.getMonth() == Months.JANUARY
            && isFirstDayOfMonth(date);
    }

    private static boolean isFirstDayOfMonth(Date date) {
        Objects.requireNonNull(date);
        Dates.requireValid(date);
        return date.getDay() == 1;
    }


    private Dates() {
        throw new AssertionError();
    }

    @AutoValue
    protected static abstract class DaysPerMonth {
        abstract int getInNormalYear();

        abstract Optional<Integer> getInLeapYear();

        static DaysPerMonth.Builder builder() {
            return new AutoValue_Dates_DaysPerMonth.Builder();
        }

        @AutoValue.Builder
        protected static abstract class Builder {
            abstract DaysPerMonth.Builder setInNormalYear(int inNormalYear);

            abstract DaysPerMonth.Builder setInLeapYear(int inLeapYear);

            abstract DaysPerMonth build();
        }
    }
}
