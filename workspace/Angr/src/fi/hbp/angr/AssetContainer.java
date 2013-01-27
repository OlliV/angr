package fi.hbp.angr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Generic asset container for game objects and body factory classes.
 */
public class AssetContainer {
    /**
     * Texture for the body.
     */
    public Texture texture;

    /**
     * BodyDef for the body.
     */
    public BodyDef bd;

    /**
     * FixtureDef for the body.
     */
    public FixtureDef fd;
}
