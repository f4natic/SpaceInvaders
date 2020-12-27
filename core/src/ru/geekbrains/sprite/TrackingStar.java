package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackingStar extends Star {

    private Vector2 trackingSpd;

    private Vector2 sumSpd = new Vector2();

    public TrackingStar(TextureAtlas atlas, Vector2 trackingSpd) {
        super(atlas);
        this.trackingSpd = trackingSpd;
    }

    @Override
    public void update(float delta) {
        sumSpd.setZero().mulAdd(trackingSpd, 0.2f).rotateDeg(180).add(v);
        pos.mulAdd(sumSpd, delta);
        checkBounds();
    }
}
