package com.yanicksenn.games.vaultmanager;

import com.google.common.collect.ImmutableList;
import com.yanicksenn.games.vaultmanager.proto.GameResources;
import com.yanicksenn.games.vaultmanager.proto.Human;
import com.yanicksenn.guice.random.Random;
import com.yanicksenn.libraries.dates.Dates;
import com.yanicksenn.libraries.ranges.IntRange;
import com.yanicksenn.games.vaultmanager.Game.Module.Annotations.Resources;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.protos.Date;

import java.util.Objects;

@Singleton
public class Generator {
    private static final ImmutableList<Human.Properties.Gender> GENDERS =
        ImmutableList.of(
            Human.Properties.Gender.MALE,
            Human.Properties.Gender.FEMALE);

    private final Random random;
    private final GameResources resources;

    @Inject
    Generator(
            Random random,
            @Resources GameResources resources) {
        this.random = random;
        this.resources = resources;
    }

    public Human.Properties getRandomHumanProperties(Date birthday) {
        Objects.requireNonNull(birthday);
        return switch (getRandomGender()) {
            case MALE -> getRandomMaleHumanProperties(birthday);
            case FEMALE -> getRandomFemaleHumanProperties(birthday);
            case null, default -> throw new IllegalStateException("Gender not recognized");
        };
    }

    public Human.Properties getRandomMaleHumanProperties(Date birthday) {
        Objects.requireNonNull(birthday);
        return Human.Properties.newBuilder()
            .setFirstname(getRandomMaleFirstname())
            .setLastname(getRandomLastname())
            .setBirthday(birthday)
            .setGender(Human.Properties.Gender.MALE)
            .build();
    }

    public Human.Properties getRandomFemaleHumanProperties(Date birthday) {
        Objects.requireNonNull(birthday);
        return Human.Properties.newBuilder()
            .setFirstname(getRandomFemaleFirstname())
            .setLastname(getRandomLastname())
            .setBirthday(birthday)
            .setGender(Human.Properties.Gender.FEMALE)
            .build();
    }

    public String getRandomMaleFirstname() {
        return random.from(resources.getMaleFirstnamesList());
    }

    public String getRandomFemaleFirstname() {
        return random.from(resources.getFemaleFirstnamesList());
    }

    public String getRandomLastname() {
        return random.from(resources.getLastnamesList());
    }

    public Human.Properties.Gender getRandomGender() {
        return random.from(GENDERS);
    }

    public Date getRandomDate(IntRange yearRange) {
        Objects.requireNonNull(yearRange);
        int year = random.inRange(yearRange);
        int month = random.inRange(Dates.Months.RANGE);
        int day = random.between(1, Dates.getDaysOfMonth(year, month));
        return Date.newBuilder()
                .setYear(year)
                .setMonth(month)
                .setDay(day)
                .build();
    }
}