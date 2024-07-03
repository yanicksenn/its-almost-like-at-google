package com.yanicksenn.experimental.helloguice;

import com.google.inject.Inject;
import com.yanicksenn.experimental.helloguice.proto.Date;
import com.yanicksenn.experimental.helloguice.proto.Person;
import com.yanicksenn.experimental.helloguice.proto.Person.Id;
import java.lang.Runnable;
import java.util.UUID;

public final class HelloGuice implements Runnable {
    private final PersonStorage personStorage;

    @Inject
    HelloGuice(PersonStorage personStorage) {
        this.personStorage = personStorage;
    }

    @Override
    public void run() {
        Person.Id personId = Person.Id.newBuilder().setId(createUUID()).build();
        personStorage.write(
            personId,
            Person.newBuilder()
                .setId(personId)
                .setFirstname("Yanick")
                .setLastname("Senn")
                .setBirthday(Date.newBuilder().setYear(1997).setMonth(4).setDay(11))
                .build());
        System.out.println("Execution complete.");
    }

    private static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
