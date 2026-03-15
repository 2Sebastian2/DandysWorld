package net.seb234.dandysworld;

import com.badlogic.gdx.ApplicationAdapter;


public class MainGame extends ApplicationAdapter {

    @Override
    public void create() {
        // Initialization code here
        System.out.println("Welcome to Dandy's World!");
    }

    @Override
    public void render() {
        // Game rendering code here
        System.out.println("Rendering the game...");
    }

    @Override
    public void dispose() {
        // Cleanup resources here
        System.out.println("Cleaning up & quit");
    }
}