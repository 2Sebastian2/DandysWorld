package net.seb234.dandysworld;

import com.badlogic.gdx.Game;

public abstract class BaseGame extends Game {
    
    private static BaseGame baseGame;

    public BaseGame() {
        baseGame = this;
    }

    public static void setActiveScreen(BaseScreen s) {
        baseGame.setScreen(s);
    } 
}
