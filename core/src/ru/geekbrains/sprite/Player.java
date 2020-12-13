package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Player extends Sprite {
    private static final float HEIGHT = 0.155f;
    private static final float V_LEN = 0.015f;

    private boolean isKeyBoard = false;
    private Vector2 keyBoardSpeed;

    private Rect worldBounds;

    private Vector2 touch;
    private Vector2 spd;
    private Vector2 tmp;

    public Player(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship"), 0, 0, 195, 287));
        touch = new Vector2();
        spd = new Vector2();
        tmp = new Vector2();
        keyBoardSpeed = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        this.pos.set(0.0f, -0.415f);
    }

    @Override
    public void update(float delta) {
        if(!isKeyBoard){
            tmp.set(touch);
            if(tmp.sub(this.pos).len() < V_LEN ){
                this.pos.set(touch);
            } else {
                pos.add(spd);
            }
        } else {
            pos.add(keyBoardSpeed);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        isKeyBoard = true;

        switch(keycode){
            case 19: {
                keyBoardSpeed.set(0.0f ,V_LEN);
                break;
            }
            case 20:{
                keyBoardSpeed.set(0.0f , -V_LEN);
                break;
            }
            case 21: {
                keyBoardSpeed.set(-V_LEN, 0.0f);
                break;
            }
            case 22:{
                keyBoardSpeed.set(V_LEN, 0.0f);
                break;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode){
            case 19: {
                keyBoardSpeed.set(keyBoardSpeed.x ,0.0f);
                break;
            }
            case 20:{
                keyBoardSpeed.set(keyBoardSpeed.x  , 0.0f);
                break;
            }
            case 21: {
                keyBoardSpeed.set(0.0f, keyBoardSpeed.y);
                break;
            }
            case 22:{
                keyBoardSpeed.set(0.0f, keyBoardSpeed.y);
                break;
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        isKeyBoard = false;
        this.touch.set(touch);
        spd.set(touch.cpy().sub(this.pos)).setLength(V_LEN);
        return false;
    }
}