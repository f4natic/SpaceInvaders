package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;

public abstract class Ship extends Sprite {

    protected final BulletPool bulletPool;
    private final ExplosionPool explosionPool;

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
    protected float reloadTimer;

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(spd, delta);
        reloadTimer += delta;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0.0f;
            shoot();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void shoot() {
        bulletSound.play(0.25f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletSpd, bulletHeight, worldBounds, damage);
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.pos, getHeight());
    }
}
