package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;

    private static final int INVALID_POINTER = -1;

    private final BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletSpd;

    private final Vector2 spd;
    private final Vector2 spd0;

    private Rect worldBounds;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private  int rightPointer = INVALID_POINTER;

    private int counter = 0;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletSpd = new Vector2(0, 0.25f);
        spd = new Vector2();
        spd0 = new Vector2(0.5f, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(spd, delta);
        if(getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
        if(getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
        shootCounter();
    }

    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = true;
                moveLeft();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch(keycode){
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight = false;
                if(pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = false;
                if(pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x){
            if(leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if(rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER){
                moveRight();
            } else {
                stop();
            }
        }

        if(pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if(leftPointer != INVALID_POINTER){
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    private void moveLeft() {
        spd.set(spd0).rotateDeg(180);
    }

    private void moveRight() {
        spd.set(spd0);
    }

    private void stop() {
        spd.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletSpd, 0.01f, worldBounds, 1);
    }

    private void shootCounter() {
        if(counter == 15) {
            shoot();
            counter = 0;
        }
        counter ++;
    }

}