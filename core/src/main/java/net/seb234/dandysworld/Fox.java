package net.seb234.dandysworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Input.Keys;

public class Fox extends Actor {

    private Texture walk;
    private Texture idle;

    private Animation<TextureRegion> walkN;
    private Animation<TextureRegion> walkS;
    private Animation<TextureRegion> walkW;
    private Animation<TextureRegion> walkE;

    private Animation<TextureRegion> idleN;
    private Animation<TextureRegion> idleS;
    private Animation<TextureRegion> idleW;
    private Animation<TextureRegion> idleE;

    private float elapsedTime;
    private int lastKeyPressed = Keys.W; // Default to facing north

    public Fox() {
        super();
        this.setName("Fox");
        this.setPosition(100, 100);
        this.setBounds(this.getX(), this.getY(), 64, 64);

        this.walk = new Texture("Fox_walk.png");
        TextureRegion[][] walkTmp = TextureRegion.split(walk, 32, 32);

        this.walkS = new Animation<TextureRegion>(0.1f, walkTmp[0]);
        this.walkN = new Animation<TextureRegion>(0.1f, walkTmp[1]);
        this.walkW = new Animation<TextureRegion>(0.1f, walkTmp[2]);
        this.walkE = new Animation<TextureRegion>(0.1f, walkTmp[3]);

        this.walkN.setPlayMode(Animation.PlayMode.LOOP);
        this.walkS.setPlayMode(Animation.PlayMode.LOOP);
        this.walkW.setPlayMode(Animation.PlayMode.LOOP);
        this.walkE.setPlayMode(Animation.PlayMode.LOOP);

        this.idle = new Texture("Fox_idle.png");
        TextureRegion[][] idleTmp = TextureRegion.split(idle, 32, 32);

        this.idleS = new Animation<TextureRegion>(0.1f, idleTmp[0]);
        this.idleN = new Animation<TextureRegion>(0.1f, idleTmp[1]);
        this.idleW = new Animation<TextureRegion>(0.1f, idleTmp[2]);
        this.idleE = new Animation<TextureRegion>(0.1f, idleTmp[3]);

        this.idleS.setPlayMode(Animation.PlayMode.LOOP);
        this.idleN.setPlayMode(Animation.PlayMode.LOOP);
        this.idleW.setPlayMode(Animation.PlayMode.LOOP);
        this.idleE.setPlayMode(Animation.PlayMode.LOOP);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;
        if (Gdx.input.isKeyPressed(Keys.W)) {
            currentFrame = this.walkN.getKeyFrame(elapsedTime);
        } else if (Gdx.input.isKeyPressed(Keys.S)) {
            currentFrame = this.walkS.getKeyFrame(elapsedTime);
        } else if (Gdx.input.isKeyPressed(Keys.A)) {
            currentFrame = this.walkW.getKeyFrame(elapsedTime);
        } else if (Gdx.input.isKeyPressed(Keys.D)) {
            currentFrame = this.walkE.getKeyFrame(elapsedTime);
        } else {
            currentFrame = this.frameIdle();
        }

        batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private TextureRegion frameIdle() {
        if (this.lastKeyPressed == Keys.W) {
            return this.idleN.getKeyFrame(elapsedTime);
        } else if (this.lastKeyPressed == Keys.S) {
            return this.idleS.getKeyFrame(elapsedTime);
        } else if (this.lastKeyPressed == Keys.A) {
            return this.idleW.getKeyFrame(elapsedTime);
        } else if (this.lastKeyPressed == Keys.D) {
            return this.idleE.getKeyFrame(elapsedTime);
        } else {
            return this.idleN.getKeyFrame(elapsedTime);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            this.moveBy(0, 100*delta);
            this.lastKeyPressed = Keys.W;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            this.moveBy(0, -100*delta);
            this.lastKeyPressed = Keys.S;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            this.moveBy(-100*delta, 0);
            this.lastKeyPressed = Keys.A;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            this.moveBy(100*delta, 0);
            this.lastKeyPressed = Keys.D;
        }
    }

    public void dispose() {
        walk.dispose();
    }
    
}
