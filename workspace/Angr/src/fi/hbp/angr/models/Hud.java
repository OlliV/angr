package fi.hbp.angr.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hud {
    BitmapFont font = new BitmapFont();
    SpriteBatch hudBatch;

    public Hud() {
        hudBatch = new SpriteBatch();
        font.setColor(Color.RED);
        font.setScale(1.0f);
    }

    public void draw() {
        hudBatch.begin();
        font.draw(hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 30);
        hudBatch.end();
    }
}
