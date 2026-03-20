package net.seb234.dandysworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Input.Keys;

public class Toon extends Actor {

    private String toonId;
    private String toonPath;

    private Texture idle;
    private Texture walk;
    private Texture run;

    private Animation<TextureRegion> idleN;
    private Animation<TextureRegion> idleS;
    private Animation<TextureRegion> idleW;
    private Animation<TextureRegion> idleE;

    private Animation<TextureRegion> walkN;
    private Animation<TextureRegion> walkS;
    private Animation<TextureRegion> walkW;
    private Animation<TextureRegion> walkE;

    private Animation<TextureRegion> runN;
    private Animation<TextureRegion> runS;
    private Animation<TextureRegion> runW;
    private Animation<TextureRegion> runE;

    private float elapsedTime;
    private int lastKeyPressed = Keys.W; // Default to facing north
    private Vector2 velocity;

    public Toon(String toonId, String name) {
        super();
        this.setName(name);
        this.setPosition(100, 100);
        this.setBounds(this.getX(), this.getY(), 64, 64);

        this.toonId = toonId;
        this.toonPath = "textures/toons/" + this.toonId + "/";

        this.velocity = new Vector2(0, 0);
        
        this.walk = new Texture(this.toonPath + this.toonId + "_walk.png");
        TextureRegion[][] walkTmp = TextureRegion.split(this.walk, 32, 32);

        this.walkS = new Animation<TextureRegion>(0.1f, walkTmp[0]);
        this.walkN = new Animation<TextureRegion>(0.1f, walkTmp[1]);
        this.walkW = new Animation<TextureRegion>(0.1f, walkTmp[2]);
        this.walkE = new Animation<TextureRegion>(0.1f, walkTmp[3]);

        this.walkN.setPlayMode(Animation.PlayMode.LOOP);
        this.walkS.setPlayMode(Animation.PlayMode.LOOP);
        this.walkW.setPlayMode(Animation.PlayMode.LOOP);
        this.walkE.setPlayMode(Animation.PlayMode.LOOP);

        this.idle = new Texture(this.toonPath + this.toonId + "_idle.png");
        TextureRegion[][] idleTmp = TextureRegion.split(this.idle, 32, 32);

        this.idleS = new Animation<TextureRegion>(0.1f, idleTmp[0]);
        this.idleN = new Animation<TextureRegion>(0.1f, idleTmp[1]);
        this.idleW = new Animation<TextureRegion>(0.1f, idleTmp[2]);
        this.idleE = new Animation<TextureRegion>(0.1f, idleTmp[3]);

        this.idleN.setPlayMode(Animation.PlayMode.LOOP);
        this.idleS.setPlayMode(Animation.PlayMode.LOOP);
        this.idleW.setPlayMode(Animation.PlayMode.LOOP);
        this.idleE.setPlayMode(Animation.PlayMode.LOOP);

        this.run = new Texture(this.toonPath + this.toonId + "_run.png");
        TextureRegion[][] runTmp = TextureRegion.split(this.run, 32, 32);

        this.runS = new Animation<TextureRegion>(0.1f, runTmp[0]);
        this.runN = new Animation<TextureRegion>(0.1f, runTmp[1]);
        this.runW = new Animation<TextureRegion>(0.1f, runTmp[3]);
        this.runE = new Animation<TextureRegion>(0.1f, runTmp[2]);

        this.runN.setPlayMode(Animation.PlayMode.LOOP);
        this.runS.setPlayMode(Animation.PlayMode.LOOP);
        this.runW.setPlayMode(Animation.PlayMode.LOOP);
        this.runE.setPlayMode(Animation.PlayMode.LOOP);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;
        if (this.velocity.equals()) {

            if (this.velocity.x == )) {
                currentFrame = this.runN.getKeyFrame(elapsedTime);
            } else {
                currentFrame = this.walkN.getKeyFrame(elapsedTime);
            }

        } else if (Gdx.input.isKeyPressed(Keys.S)) {

            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                currentFrame = this.runS.getKeyFrame(elapsedTime);
            } else {
                currentFrame = this.walkS.getKeyFrame(elapsedTime);
            }

        } else if (Gdx.input.isKeyPressed(Keys.A)) {

            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                currentFrame = this.runW.getKeyFrame(elapsedTime);
            } else {
                currentFrame = this.walkW.getKeyFrame(elapsedTime);
            }

        } else if (Gdx.input.isKeyPressed(Keys.D)) {

            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                currentFrame = this.runE.getKeyFrame(elapsedTime);
            } else {
                currentFrame = this.walkE.getKeyFrame(elapsedTime);
            }

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

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }

    public void move(float dt) {
        this.moveBy(this.velocity.x * dt, this.velocity.y * dt);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                this.setVelocity(0, 200);
            } else {
                this.setVelocity(0, 100);
            }
            this.lastKeyPressed = Keys.W;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                this.setVelocity(0, -200);
            } else {
                this.setVelocity(0, -100);
            }
            this.lastKeyPressed = Keys.S;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                this.setVelocity(-200, 0);
            } else {
                this.setVelocity(-100, 0);
            }
            this.lastKeyPressed = Keys.A;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
                this.setVelocity(200, 0);
            } else {
                this.setVelocity(100, 0);
            }
            this.lastKeyPressed = Keys.D;

        }
        this.move(delta);
    }

    public void dispose() {
        this.idle.dispose();
        this.walk.dispose();
        this.run.dispose();

    }
    
}
