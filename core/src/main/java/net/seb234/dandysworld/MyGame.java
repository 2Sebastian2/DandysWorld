package net.seb234.dandysworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGame extends Game {
    public SpriteBatch batch;
    public OrthographicCamera cam;

    @Override
    public void create() {
        batch = new SpriteBatch();
        cam = new OrthographicCamera(800, 480);
        setScreen(new SelectScreen(this));
    }
    @Override public void dispose() { batch.dispose(); }
}


