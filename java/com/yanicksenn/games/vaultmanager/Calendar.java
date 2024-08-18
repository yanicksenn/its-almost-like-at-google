package com.yanicksenn.games.vaultmanager;

import com.yanicksenn.libraries.datetime.DateTimeUtils;
import com.yanicksenn.games.vaultmanager.Game.Module.Annotations.StartDate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.protos.Date;

/**
 * Represents a calendar with its own date instance.
 */
@Singleton
public final class Calendar {
    private final Date currentDate;

    @Inject
    Calendar(@StartDate Date startDate) {
        DateTimeUtils.validateDate(startDate);
        this.currentDate = startDate;
    }

    public Date today() {
        return currentDate;
    }

    // TODO: yanicksenn - Add possibility to advance the current date.
}