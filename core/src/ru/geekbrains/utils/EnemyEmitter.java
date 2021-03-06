package ru.geekbrains.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Enemy;

public class EnemyEmitter {

    private static final float GENERATE_INTEVAL = 4.0f;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3.0f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MIDDLE_HEIGHT = 0.15f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final int ENEMY_MIDDLE_BULLET_DAMAGE = 5;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 4.0f;
    private static final int ENEMY_MIDDLE_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 2.0f;
    private static final int ENEMY_BIG_HP = 10;

    private final Vector2 enemySmallSPD = new Vector2(0.0f, -0.2f);
    private final Vector2 enemySmallBulletSPD = new Vector2(0.0f, -0.3f);

    private final Vector2 enemyMiddleSPD = new Vector2(0.0f, -0.03f);
    private final Vector2 enemyMiddleBulletSPD = new Vector2(0.0f, -0.3f);

    private final Vector2 enemyBigSPD = new Vector2(0.0f, -0.005f);
    private final Vector2 enemyBigBulletSPD = new Vector2(0.0f, -0.3f);

    private final Rect worldBounds;
    private final Sound bulletSound;
    private final TextureRegion bulletRegion;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMiddleRegions;
    private final TextureRegion[] enemyBigRegions;

    private final EnemyPool enemyPool;

    private float generateTimer;

    public EnemyEmitter(TextureAtlas atlas, Rect worldBounds, Sound bulletSound, EnemyPool enemyPool) {
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        TextureRegion enemySmallRegion = atlas.findRegion("enemy0");
        this.enemySmallRegions = Regions.split(enemySmallRegion, 1,2, 2);
        TextureRegion enemyMiddleRegion = atlas.findRegion("enemy1");
        this.enemyMiddleRegions = Regions.split(enemyMiddleRegion, 1,2, 2);
        TextureRegion enemyBigRegion = atlas.findRegion("enemy2");
        this.enemyBigRegions = Regions.split(enemyBigRegion, 1,2, 2);
        this.worldBounds = worldBounds;
        this.bulletSound = bulletSound;
        this.enemyPool = enemyPool;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if(generateTimer >= GENERATE_INTEVAL) {
            generateTimer = 0.0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if(type < 0.5f) {
                enemy.set(
                        enemySmallRegions,
                        bulletRegion,
                        bulletSound,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        enemySmallBulletSPD,
                        ENEMY_SMALL_BULLET_DAMAGE,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        enemySmallSPD,
                        ENEMY_SMALL_HEIGHT
                );
            } else if(type < 0.8f) {
                enemy.set(
                        enemyMiddleRegions,
                        bulletRegion,
                        bulletSound,
                        ENEMY_MIDDLE_BULLET_HEIGHT,
                        enemyMiddleBulletSPD,
                        ENEMY_MIDDLE_BULLET_DAMAGE,
                        ENEMY_MIDDLE_HP,
                        ENEMY_MIDDLE_RELOAD_INTERVAL,
                        enemyMiddleSPD,
                        ENEMY_MIDDLE_HEIGHT
                );
            } else {
                enemy.set(
                        enemyBigRegions,
                        bulletRegion,
                        bulletSound,
                        ENEMY_BIG_BULLET_HEIGHT,
                        enemyBigBulletSPD,
                        ENEMY_BIG_BULLET_DAMAGE,
                        ENEMY_BIG_HP,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        enemyBigSPD,
                        ENEMY_BIG_HEIGHT
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
