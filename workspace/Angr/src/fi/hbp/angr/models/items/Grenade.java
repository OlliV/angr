package fi.hbp.angr.models.items;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.AssetContainer;
import fi.hbp.angr.G;
import fi.hbp.angr.ItemDestruction;
import fi.hbp.angr.actors.SlingshotActor;
import fi.hbp.angr.models.DamageModel;
import fi.hbp.angr.models.Destructible;
import fi.hbp.angr.models.Explosion;

public class Grenade extends SlingshotActor implements Destructible {
    private static final String MODEL_NAME = "grenade";
    private static final String TEXTURE_PATH = "data/" + MODEL_NAME + ".png";
    private static final String SOUND_FX_PATH = "data/grenade.wav";
    private static final float EXPLOSION_DELAY = 3.0f;

    private ItemDestruction itdes;

    private Vector2 modelOrigin;
    private Sprite sprite;

    /* Explosion and destruction */
    private boolean bExplosionTimer = false;
    private float explosionTimer = 0;
    private boolean destroyed = false;
    private Explosion explosion;
    private static Sound explosionSound;
    private ParticleEffect particleEffect;

    /**
     * Preload static data
     */
    public static void preload() {
        G.getAssetManager().load(TEXTURE_PATH, Texture.class);
        G.getAssetManager().load(SOUND_FX_PATH, Sound.class);
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
        as.fd.density = 1.0f;
        as.fd.friction = 0.3f;
        as.fd.restitution = 0.3f;

        //as.fd.filter.categoryBits = CollisionFilterMasks.GRENADE;
        //as.fd.filter.maskBits = CollisionFilterMasks.ENEMY | CollisionFilterMasks.WALL | CollisionFilterMasks.GRENADE;

        explosionSound = G.getAssetManager().get(SOUND_FX_PATH);
    }

    /**
     * TODO
     * @param stage
     * @param world
     * @param bel
     * @param as
     * @param x
     * @param y
     * @param angle
     */
    public Grenade(Stage stage, World world, ItemDestruction itdes, BodyEditorLoader bel, AssetContainer as, float x, float y, float angle) {
        super(stage, world, 50.0f, 0.5f);
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

        /* Explosion timer and effects */
        explosion = new Explosion(body);
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("data/explosion.p"),
                            Gdx.files.internal("data"));
        particleEffect.setDuration(180);
    }

    @Override
    protected void slingshotRelease() {
        particleEffect.reset();
        bExplosionTimer = true;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        /* Calculate new position */
        Vector2 pos = body.getPosition();
        float x = pos.x * G.BOX_TO_WORLD - modelOrigin.x;
        float y = pos.y * G.BOX_TO_WORLD - modelOrigin.y;

        /* Explosion handler */
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (bExplosionTimer == true) {
            explosionTimer += deltaTime;
            if (explosionTimer >= EXPLOSION_DELAY) {
                explosionTimer = 0;
                bExplosionTimer = false;
                explosion.doExplosion();
                explosionSound.play();
                particleEffect.setPosition(x, y);
                particleEffect.start();
                destroyed = true;
            }
        }

        /* Draw sprite and particle effect */
        if (!destroyed) {
            sprite.setPosition(x, y);
            sprite.setOrigin(modelOrigin.x, modelOrigin.y);
            sprite.setRotation((float)(body.getAngle() * MathUtils.radiansToDegrees));
            sprite.draw(batch, parentAlpha);
        } else {
            if (particleEffect.isComplete()) {
                /* NOTE: Adding this body to a list of destroyed bodies
                 * doesn't remove it immediately from the world. */
                itdes.add(this);
            }
            particleEffect.draw(batch, deltaTime);
        }
    }

    @Override
    public DamageModel getDatamageModel() {
        return null;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void setDestroyed() {
        this.destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }
}
