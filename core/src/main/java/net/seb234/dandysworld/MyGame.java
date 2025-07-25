package net.seb234.dandysworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.seb234.dandysworld.screens.SelectScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGame extends Game {
    public SpriteBatch batch;
    public boolean isHost;
    public GameState state;
    public PlayerToon player;
    public OrthographicCamera cam;

    public StopWatch stopWatch;

    @Override
    public void create() {
        state = new GameState();

        player = new PlayerToon();
        player.setId("-1");

        stopWatch = new StopWatch();
        isHost = false;
        batch = new SpriteBatch();
        cam = new OrthographicCamera(800, 480);
        setScreen(new SelectScreen(this));
    }
    @Override
    public void dispose() {
        batch.dispose();
    }
}


