package com.yanicksenn.games.vaultmanager;

import com.yanicksenn.libraries.dates.Dates;
import com.yanicksenn.games.vaultmanager.Game.Module.Annotations.StartDate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.protos.Date;

/**
 * Represents a calendar with its own date instance.
 */
@Singleton
public final class Calendar {
    private Date currentDate;

    @Inject
    Calendar(@StartDate Date startDate) {
        this.currentDate = Dates.requireValid(startDate);
    }

    public Date today() {
        return currentDate;
    }

    public Date tomorrow() {
        return Dates.getTomorrow(currentDate);
    }

    public void advance() {
        currentDate = tomorrow();
    }
}