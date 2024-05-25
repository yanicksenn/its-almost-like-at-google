package com.yanicksenn.helloguice;

import com.google.inject.Inject;
import com.yanicksenn.helloguice.proto.Date;
import com.yanicksenn.helloguice.proto.Person;
import com.yanicksenn.helloguice.proto.Person.Id;
import java.util.UUID;

public class HelloGuice {
    private final PersonStorage personStorage;

    @Inject
    HelloGuice(PersonStorage personStorage) {
        this.personStorage = personStorage;
    }

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
    }

    private static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
