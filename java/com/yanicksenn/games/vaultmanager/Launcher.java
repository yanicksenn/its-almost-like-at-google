package com.yanicksenn.games.vaultmanager;

import com.google.inject.Guice;

import java.io.IOException;
import java.lang.AssertionError;

public final class Launcher {
    public static void main(String[] args) throws IOException {
        Guice.createInjector(new Game.Module())
            .getInstance(Game.class)
            .run();
    }

    private Launcher() {
        throw new AssertionError();
    }
}