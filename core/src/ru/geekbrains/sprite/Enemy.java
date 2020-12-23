package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class Enemy extends Ship {

    private Vector2 startSpd;

    public Enemy(BulletPool bulletPool, Rect worldBounds) {
        super(bulletPool);
        this.worldBounds = worldBounds;
        this.spd = new Vector2();
        this.spd0 = new Vector2();
        this.bulletPos = new Vector2();
        startSpd = new Vector2(0.0f, -0.3f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(getTop() < worldBounds.getTop()) {
            this.spd.set(spd0);
        } else {
            reloadTimer  = reloadInterval - delta * 2;
        }
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if(getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            TextureRegion bulletRegion,
            Sound bulletSound,
            float bulletHeight,
            Vector2 bulletSpd,
            int damage,
            int hp,
            float reloadInterval,
            Vector2 spd0,
            float height
    ) {
        this.regions = regions;
        this.bulletRegion = bulletRegion;
        this.bulletSound = bulletSound;
        this.bulletHeight = bulletHeight;
        this.bulletSpd = bulletSpd;
        this.damage = damage;
        this.hp = hp;
        this.reloadInterval = reloadInterval;
        this.spd0.set(spd0);
        this.spd.set(startSpd);
        setHeightProportion(height);
    }
}
