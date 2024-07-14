package com.yanicksenn.libraries.dates;

import com.yanicksenn.protos.Date;

import java.util.Comparator;

public final class Dates {

    public static final Comparator<Date> COMPARATOR = Comparator
            .comparingInt(Date::getYear)
            .thenComparingInt(Date::getMonth)
            .thenComparingInt(Date::getDay);

    private Dates() {
        throw new AssertionError();
    }
}
