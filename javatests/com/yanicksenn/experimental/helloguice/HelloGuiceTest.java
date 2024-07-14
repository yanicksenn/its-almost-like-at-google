package com.yanicksenn.experimental.helloguice;

import static com.google.common.truth.Truth.assertThat;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.util.Modules;
import com.yanicksenn.experimental.helloguice.HelloGuice;
import com.yanicksenn.experimental.helloguice.HelloGuiceLauncher;
import com.yanicksenn.experimental.helloguice.PersonStorage;
import com.yanicksenn.experimental.helloguice.Storage;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class HelloGuiceTest {

  @Inject private HelloGuice helloGuice;
  @Inject private PersonStorage personStorage;
  @Inject private Storage storage;

  @Before
  public void setUp() {
    Guice.createInjector(
            Modules.override(new HelloGuiceLauncher.Module()).with(new TestingModule()))
        .injectMembers(this);
  }

  @Test
  public void testRun() {
    assertThat(personStorage.readAll()).hasSize(0);
    helloGuice.run();
    assertThat(personStorage.readAll()).hasSize(1);
  }

  private static class TestingModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(Storage.class).to(InMemoryStorage.class);
    }
  }
}
