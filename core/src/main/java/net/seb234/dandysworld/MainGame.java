package net.seb234.dandysworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MainGame extends ApplicationAdapter {

    private Fox fox;
    private Stage stage;

    @Override
    public void create() {
        // Initialization code here
        System.out.println("Welcome to Dandy's World!");
        fox = new Fox();
        stage = new Stage();
        stage.addActor(fox);
    }

    @Override
    public void render() {
        // Game rendering code here
        System.out.println("Rendering the game...");
        // New Batch

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        // Cleanup resources here
        System.out.println("Cleaning up & quit");
    }
}