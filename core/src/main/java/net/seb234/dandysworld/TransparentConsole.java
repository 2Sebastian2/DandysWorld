package net.seb234.dandysworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class TransparentConsole {

    private static class Message {
        public Color color;

        public String msg;

        public Message(Color color, String msg) {
            this.color = color;
            this.msg = msg;
        }
    }
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final Queue<Message> logs = new LinkedList<>();

    public TransparentConsole() {
        font.getData().setScale(0.9f);
    }

    public void log(String tag, String text) {
        if (logs.size() > 30) logs.poll();

        logs.offer(new Message(Color.YELLOW, "LOG! [" + LocalTime.now().withNano(0) + "] (" + tag + ") " + text));

        Gdx.app.log(tag, text);
    }

    public void error(String tag, String text) {
        if (logs.size() > 30) logs.poll();

        logs.offer(new Message(Color.RED, "ERROR! [" + LocalTime.now().withNano(0) + "] (" + tag + ") " + text));

        Gdx.app.error(tag, text);
    }

    public void updateConsole() {
        batch.begin();

        int i = 0;
        for (Message message : logs) {
            font.setColor(message.color);

            font.draw(batch, message.msg, 10, Gdx.input.getY() - 10 - 15 * i);
            i++;
        }
        batch.end();
    }
}
