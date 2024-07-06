package com.yanicksenn.experimental.hellojmonkeyengine;

import com.jme3.system.AppSettings;
import com.yanicksenn.libraries.flags.Flags;
import java.lang.AssertionError;

public final class Launcher {
    public static void main(String[] args) {
        Flags.init(args);
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My Awesome Game");
        int width = 900, height = 600;
        settings.setResolution(width, height);
        settings.setWindowSize(width, height);
        Game game = new Game();
        game.setSettings(settings);
        game.setShowSettings(false);
        boolean isShowInfo = Flags.isToggled("show_info");
        game.setDisplayFps(isShowInfo);
        game.setDisplayStatView(isShowInfo);
        game.start();
    }
    
    private Launcher() {
        throw new AssertionError();
    }
}