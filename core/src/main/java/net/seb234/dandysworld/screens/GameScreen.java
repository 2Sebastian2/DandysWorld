package net.seb234.dandysworld.screens;

import net.seb234.dandysworld.BaseScreen;
import net.seb234.dandysworld.Toon;

public class GameScreen extends BaseScreen {

    private Toon fox;

    @Override
    public void initialize() {
        // Initialization code here
        System.out.println("Welcome to Dandy's World!");
        fox = new Toon("fox", "Fox");
        mainStage.addActor(fox);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void dispose() {
        // Cleanup resources here
        System.out.println("Cleaning up & quit");
        mainStage.dispose();
        fox.dispose();
    }
    
}
