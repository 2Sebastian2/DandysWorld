package net.seb234.dandysworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import net.seb234.dandysworld.client.ClientManager;
import net.seb234.dandysworld.server.ServerLauncher;

public class GameScreen implements Screen {
    final MyGame game;
    final boolean isHost;
    public static GameState state;
    public static PlayerToon player;

    public GameScreen(MyGame game, boolean isHost) {
        this.game = game;
        this.isHost = isHost;
        state = new GameState();

        if (isHost) {
            ServerLauncher.startServer();
            player = new PlayerToon(0);
        } else {
            ClientManager.startClient("192.168.1.4");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        game.cam.update();
        game.batch.setProjectionMatrix(game.cam.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        for (PlayerToon _player : state.playerList.values()) {
            _player.draw(game.batch);
        }

        player.draw(game.batch);

        if (isHost) {
            ServerLauncher.sendGameUpdate();
        } else {
            ClientManager.sendInput();
        }
        game.batch.end();
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

    }

    private void handleInput(float delta) {
        float move = player.speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.setX(player.getX() - move);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.setX(player.getX() + move);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.setY(player.getY() + move);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.setY(player.getY() - move);
    }
}
