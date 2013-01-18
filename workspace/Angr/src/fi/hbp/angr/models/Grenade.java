package fi.hbp.angr.models;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fi.hbp.angr.G;

public class Grenade extends Actor {
    private static final String MODEL_NAME = "grenade";
    private static final String TEXTURE_PATH = "data/" + MODEL_NAME + ".png";
    public Body body;
    private Vector2 modelOrigin;
    private Sprite sprite;

    static void preload() {
        G.preloadTextures.add(TEXTURE_PATH);
    }

    static void initAssets(AssetContainer as, BodyEditorLoader bel) {
        as.texture = G.getAssetManager().get(
                bel.getImagePath(MODEL_NAME),
                Texture.class);

        as.bd = new BodyDef();
        as.bd.type = BodyType.DynamicBody;
        as.bd.active = true;
        as.bd.position.set(0, 0);

        as.fd = new FixtureDef();
        as.fd.density = 5.0f;
        as.fd.friction = 1.0f;
        as.fd.restitution = 0.5f;

        G.preloadTextures.remove(TEXTURE_PATH);

        //as.fd.filter.categoryBits = CollisionFilterMasks.GRENADE;
        //as.fd.filter.maskBits = CollisionFilterMasks.ENEMY | CollisionFilterMasks.WALL | CollisionFilterMasks.GRENADE;
    }

    public Grenade(World world, BodyEditorLoader bel, AssetContainer as, float x, float y, float angle) {
        as.bd.position.set(new Vector2(x * G.WORLD_TO_BOX, y * G.WORLD_TO_BOX));
        body = world.createBody(as.bd);
        sprite = new Sprite(as.texture);

        bel.attachFixture(body,
                          MODEL_NAME,
                          as.fd,
                          sprite.getWidth() * G.WORLD_TO_BOX);
        modelOrigin = bel.getOrigin(MODEL_NAME, sprite.getWidth()).cpy();
        //body.setTransform(x, y, angle);
        sprite.setOrigin(modelOrigin.x, modelOrigin.y);
        sprite.setPosition(x, y);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        Vector2 pos = body.getPosition(); // .sub(modelOrigin);
        sprite.setPosition(pos.x * G.BOX_TO_WORLD - modelOrigin.x,
                           pos.y * G.BOX_TO_WORLD - modelOrigin.y);
        sprite.setOrigin(modelOrigin.x, modelOrigin.y);
        sprite.setRotation((float)(body.getAngle() * MathUtils.radiansToDegrees));
        sprite.draw(batch, parentAlpha);
    }
}
