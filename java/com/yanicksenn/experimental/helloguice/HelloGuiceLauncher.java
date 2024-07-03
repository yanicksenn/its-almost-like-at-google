package com.yanicksenn.experimental.helloguice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

public class HelloGuiceLauncher {
  public static void main(String[] args) {
    Guice.createInjector(new Module()).getInstance(HelloGuice.class).run();
  }

  public static class Module extends AbstractModule {
    @Override
    protected void configure() {
      bind(Storage.class).to(ProductionStorage.class);
    }
  }
}
