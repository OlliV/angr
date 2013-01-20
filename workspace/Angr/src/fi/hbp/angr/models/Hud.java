package fi.hbp.angr.models;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hud {
    private BitmapFont font = new BitmapFont();
    private SpriteBatch hudBatch;
    private ArrayList<HudActor> actors = new ArrayList<HudActor>();

    public Hud() {
        hudBatch = new SpriteBatch();
        font.setColor(Color.RED);
        font.setScale(1.0f);
    }

    public void addActor(HudActor actor) {
        actors.add(actor);
    }

    public void draw() {
        hudBatch.begin();
        font.draw(hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 30);
        for (HudActor actor : actors) {
            actor.draw(hudBatch);
        }
        hudBatch.end();
    }
}
