package fi.hbp.angr.models.levels;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fi.hbp.angr.BodyFactory;
import fi.hbp.angr.G;
import fi.hbp.angr.Preloadable;
import fi.hbp.angr.logic.GameState;

/**
 * Abstract Level class.
 */
public abstract class Level extends Actor implements Preloadable {
    private final String levelName;
    private Sprite sprite;

    /**
     * Constructor for Level
     * @param levelName Texture filename without extension.
     */
    public Level(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void preload() {
        G.getAssetManager().load("data/" + levelName + ".png", Texture.class);
    }

    @Override
    public void unload() {
        G.getAssetManager().unload("data/" + levelName + ".png");
    }

    public abstract void show(BodyFactory bf, GameState gs);

    /**
     * Internal show method that should be called by public show method
     * @param bf Body factory for creating the map body and sprite.
     * @param mapFd
     */
    protected void show(BodyFactory bf, FixtureDef mapFd) {
        BodyEditorLoader bel = new BodyEditorLoader(
                Gdx.files.internal("levels.json"));

        Texture texture = G.getAssetManager().get(
                "data/" + levelName + ".png",
                Texture.class);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.StaticBody;
        bd.position.set(0, 0);

        sprite = new Sprite(texture);
        sprite.setPosition(0, 0);

        Body body = bf.getWorld().createBody(bd);
        bel.attachFixture(body,
                          levelName,
                          mapFd,
                          sprite.getWidth() / G.BOX_TO_WORLD);
    }

    /**
     * Get level highscore
     * @return higscore
     */
    public int getHighScore() {
        return 100;
        // TODO
    }

    /**
     * Set level highscore
     * @param score
     */
    public void setHighScore(int score) {
        // TODO
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public String toString() {
        return "Level: " + levelName;
    }
}
