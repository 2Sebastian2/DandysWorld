package net.seb234.dandysworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MainGame extends ApplicationAdapter {

    private Toon fox;
    private Stage stage;

    @Override
    public void create() {
        // Initialization code here
        System.out.println("Welcome to Dandy's World!");
        fox = new Toon("fox", "Fox");
        stage = new Stage();
        stage.addActor(fox);
    }

    @Override
    public void render() {
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getDeltaTime());

        stage.draw();
    }

    @Override
    public void dispose() {
        // Cleanup resources here
        System.out.println("Cleaning up & quit");
        stage.dispose();
        fox.dispose();
    }
}