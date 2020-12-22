package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Bullet;

public abstract class Ship extends Sprite {

    protected final BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected Vector2 bulletSpd;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected Vector2 spd;
    protected Vector2 spd0;

    protected Rect worldBounds;

    protected float reloadInterval;
    private float reloadTimer;

    public Ship(BulletPool bulletPool) {
        this.bulletPool = bulletPool;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
    }

    @Override
    public void update(float delta) {
        if(pos.y > worldBounds.getTop() - this.getHalfHeight()){
            pos.mulAdd(spd, delta*10);
        } else{
            pos.mulAdd(spd, delta);
        }
        reloadTimer += delta;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0.0f;
            shoot();
        }
    }

    private void shoot() {
        bulletSound.play(0.25f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletSpd, bulletHeight, worldBounds, damage);
    }
}
