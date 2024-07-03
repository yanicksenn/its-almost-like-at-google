package com.yanicksenn.experimental.helloguice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.experimental.helloguice.proto.Person;
import com.yanicksenn.experimental.helloguice.proto.Person.Id;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class PersonStorage {
  private final Storage storage;

  @Inject
  PersonStorage(Storage storage) {
    this.storage = storage;
  }

  public Person read(Person.Id key) {
    return (Person) storage.read(key);
  }

  public List<Person> readAll() {
    return storage.readAll().stream().map(e -> (Person) e).collect(Collectors.toList());
  }

  public void write(Person.Id key, Person value) {
    storage.write(key, value);
  }
}
