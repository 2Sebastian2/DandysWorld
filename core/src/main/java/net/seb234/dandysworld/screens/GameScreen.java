package net.seb234.dandysworld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import net.seb234.dandysworld.MyGame;
import net.seb234.dandysworld.PlayerToon;
import net.seb234.dandysworld.TransparentConsole;
import net.seb234.dandysworld.client.NettyClient;
import net.seb234.dandysworld.dataholder.NetUtils;
import net.seb234.dandysworld.server.NettyServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameScreen implements Screen {
    private final MyGame game;
    private Future<?> operatingTask;

    public static final TransparentConsole console = new TransparentConsole();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public GameScreen(MyGame game, boolean isHost) {
        this.game = game;
        game.isHost = isHost;
    }

    @Override
    public void show() {
        game.player.setTexture();


        if (game.isHost) {
            NettyServer server = new NettyServer(12345, this.game);
            console.log("Game", "Submitting Server to Thread...");
            operatingTask = executor.submit(() -> {
                try {
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    operatingTask.cancel(false);
                }
            });
        } else {
            NettyClient client = new NettyClient(12345, this.game);
            console.log("Game", "Submitting Client to Thread...");
            operatingTask = executor.submit(() -> {
                try {
                    client.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    operatingTask.cancel(false);
                }
            });
        }
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        game.cam.update();
        game.batch.setProjectionMatrix(game.cam.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        for (PlayerToon _player : game.state.playerList.values()) {
            _player.setTexture();
            _player.draw(game.batch);
        }
        game.player.draw(game.batch);

        game.batch.end();

        NetUtils.threadHandler();
        console.updateConsole();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        operatingTask.cancel(false);
        game.dispose();
    }

    private void handleInput(float delta) {
        float move = game.player.speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) game.player.setX(game.player.getX() - move);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) game.player.setX(game.player.getX() + move);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) game.player.setY(game.player.getY() + move);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) game.player.setY(game.player.getY() - move);
    }
}
