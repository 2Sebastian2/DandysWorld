package net.seb234.dandysworld;

import net.seb234.dandysworld.screens.InitScreen;

public class MainGame extends BaseGame {

    @Override
    public void create() {
        setActiveScreen(new InitScreen());
    }
}