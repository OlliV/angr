package fi.hbp.angr.models.items;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fi.hbp.angr.AssetContainer;
import fi.hbp.angr.G;
import fi.hbp.angr.ItemDestruction;
import fi.hbp.angr.models.BoxDamageModel;
import fi.hbp.angr.models.CollisionFilterMasks;
import fi.hbp.angr.models.DamageModel;
import fi.hbp.angr.models.Destructible;

public class Box extends Actor implements Destructible {
    private static final String MODEL_NAME = "box";
    private static final String TEXTURE_PATH = "data/" + MODEL_NAME + ".png";
    private ItemDestruction itdes;
    private Body body;
    private Vector2 modelOrigin;
    private Sprite sprite;
    private DamageModel damageModel = new BoxDamageModel();
    private BitmapFont font = new BitmapFont();
    private ParticleEffect particleEffect;
    private boolean destroyed;

    /**
     * Preload static data
     */
    public static void preload() {
        G.getAssetManager().load(TEXTURE_PATH, Texture.class);
    }

    /**
     * Initialize assets of this object
     * @param as
     * @param bel
     */
    public static void initAssets(AssetContainer as, BodyEditorLoader bel) {
        as.texture = G.getAssetManager().get(
                bel.getImagePath(MODEL_NAME),
                Texture.class);

        as.bd = new BodyDef();
        as.bd.type = BodyType.DynamicBody;
        as.bd.active = true;
        as.bd.position.set(0, 0);

        as.fd = new FixtureDef();
        as.fd.density = 8.0f;
        as.fd.friction = 0.3f;
        as.fd.restitution = 0.3f;
        as.fd.filter.categoryBits = CollisionFilterMasks.OTHER;
        as.fd.filter.maskBits = CollisionFilterMasks.ALL;
    }

    /**
     * TODO
     * @param world
     * @param des
     * @param bel
     * @param as
     * @param x
     * @param y
     * @param angle
     */
    public Box(World world, ItemDestruction itdes, BodyEditorLoader bel, AssetContainer as, float x, float y, float angle) {
        this.itdes = itdes;

        as.bd.position.set(new Vector2(x * G.WORLD_TO_BOX, y * G.WORLD_TO_BOX));
        body = world.createBody(as.bd);
        body.setUserData(this);
        sprite = new Sprite(as.texture);

        bel.attachFixture(body,
                          MODEL_NAME,
                          as.fd,
                          sprite.getWidth() * G.WORLD_TO_BOX);
        modelOrigin = bel.getOrigin(MODEL_NAME, sprite.getWidth()).cpy();
        sprite.setOrigin(modelOrigin.x, modelOrigin.y);
        sprite.setPosition(x, y);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

        /* Debug */
        font.setScale(4);

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("data/boxdestruction.p"),
                            Gdx.files.internal("data"));
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (!destroyed) {
            Vector2 pos = body.getPosition();
            sprite.setPosition(pos.x * G.BOX_TO_WORLD - modelOrigin.x,
                               pos.y * G.BOX_TO_WORLD - modelOrigin.y);
            sprite.setOrigin(modelOrigin.x, modelOrigin.y);
            sprite.setRotation((float)(body.getAngle() * MathUtils.radiansToDegrees));
            sprite.draw(batch, parentAlpha);

            if (G.DEBUG) {
                /* Debug print health status */
                font.draw(batch, this.getDatamageModel().toString(),
                        pos.x * G.BOX_TO_WORLD,
                        pos.y * G.BOX_TO_WORLD + 100f);
            }
        } else {
            if (particleEffect.isComplete()) {
                /* NOTE: Adding this body to a list of destroyed bodies
                 * doesn't remove it immediately from the world. */
                body.setType(BodyType.StaticBody);
                itdes.add(this);
            }
            particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public DamageModel getDatamageModel() {
        return damageModel;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void setDestroyed() {
        this.destroyed = true;

        /* Box should not collide with other game objects anymore */
        Filter filter = body.getFixtureList().get(0).getFilterData();
        filter.maskBits = CollisionFilterMasks.GROUND; /* But with ground */
        body.getFixtureList().get(0).setFilterData(filter);

        particleEffect.reset();
        particleEffect.setPosition(sprite.getX(),
                                   sprite.getY());
        particleEffect.start();
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }
}
