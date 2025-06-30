package net.seb234.dandysworld;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import net.seb234.dandysworld.dataholder.PlayerData;

public class PlayerToon extends Actor {

    public String texture_name;
    public Texture texture;
    public final float speed = 200f;

    public final int id;

    public PlayerToon(int id) {
        super();
        this.id = id;
        this.texture_name = Outfit.defaultTexture();
        setTexture();
        setScale(0.3f);
        setPosition(0, 0);
    }

    public void draw(SpriteBatch b) {
        b.draw(texture, getX(), getY());
    }

    public void setTexture() {
        this.texture = new Texture(this.texture_name);
    }

    public PlayerData getPlayerData() {
        PlayerData data = new PlayerData();

        data.x = getX();
        data.y = getY();
        data.playerId = this.id;
        data.texture_name = this.texture_name;

        return data;
    }

    public void updateData(PlayerData data) {
        setX(data.x);
        setY(data.y);
        this.texture_name = data.texture_name;
        setTexture();
    }
}
