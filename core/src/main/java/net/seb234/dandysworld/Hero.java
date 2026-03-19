package net.seb234.dandysworld;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hero extends Actor {

    private Texture skin;

    public Hero() {
        super();
        this.setName("Dandy");
        this.setPosition(100, 100);
        this.skin = new Texture("dandy.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (skin != null) {
            batch.draw(skin, getX(), getY());
        }
    }   

    @Override
    public void act(float delta) {
        System.out.println(getName() + " is acting heroically!");
    }
    
}
