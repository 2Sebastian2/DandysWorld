package net.seb234.dandysworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Fox extends Actor {

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> walkS;
    private Animation<TextureRegion> walkN;
    private Animation<TextureRegion> walkW;
    private Animation<TextureRegion> walkE;
    private Texture walk;

    private float elapsedTime;

    public Fox() {
        super();
        this.setName("Fox");
        this.setPosition(0, 0);
        this.walk = new Texture("Fox_walk.png");
        TextureRegion[][] tmp = TextureRegion.split(walk, 32, 32);
        this.walkS = new Animation<TextureRegion>(0.1f, tmp[0]);
        this.walkN = new Animation<TextureRegion>(0.1f, tmp[1]);
        this.walkW = new Animation<TextureRegion>(0.1f, tmp[2]);
        this.walkE = new Animation<TextureRegion>(0.1f, tmp[3]);
        this.idle = walkS; // For simplicity, using the first frame as idle.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        
    }   

    @Override
    public void act(float delta) {
        if (!this.animationPaused) {
            elapsedTime += delta;
        }


        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            this.walkN.setPlayMode(Animation.PlayMode.LOOP);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            this.walkS.setPlayMode(Animation.PlayMode.LOOP);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            this.walkW.setPlayMode(Animation.PlayMode.LOOP);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            this.walkE.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            this.idle.setPlayMode(Animation.PlayMode.LOOP);
        }
    }
    
}
