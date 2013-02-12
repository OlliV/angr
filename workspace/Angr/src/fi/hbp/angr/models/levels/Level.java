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
import fi.hbp.angr.models.SlingshotActor;
import fi.hbp.angr.models.actors.Hans;

/**
 * Abstract Level class.
 *
 * All game levels should inherit this class.
 */
public abstract class Level extends Actor implements Preloadable {
    /**
     * Level name.
     */
    private final String levelName;
    /**
     * Terrain sprite for of the level.
     */
    private Sprite sprite;
    /**
     * Game state object.
     */
    protected GameState gs;
    /**
     * Body factor for creating level actors.
     */
    protected BodyFactory bf;

    /**
     * Actor of player model Hans.
     */
    protected Hans hans;
    /* TODO Use timer instead of this implementation? */
    /**
     * Grenade spawn delay.
     */
    private final float grenadeSpawnDelay = 5.0f;
    /**
     * Counter used to count grenade spawn delay.
     */
    private float grenadeSpawnCounter = 0.0f;

    /**
     * Constructor for Level class.
     * @param levelName Texture filename without extension.
     */
    public Level(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Automatically called preload method.
     *
     * Prefer use of AssetManager.load().
     */
    protected abstract void doOnPreload();

    @Override
    public final void preload() {
        G.getAssetManager().load("data/" + levelName + ".png", Texture.class);
        doOnPreload();
    }

    /**
     * Automatically called unload method.
     *
     * Prefer use of AssetManager.unload().
     */
    protected abstract void doOnUnload();

    @Override
    public final void unload() {
        G.getAssetManager().unload("data/" + levelName + ".png");
        doOnUnload();
    }

    /**
     * This method is called when initiating this level.
     */
    protected abstract void doOnShow();

    /**
     * This method is called when the game screen is initialized.
     * @param bf a body factory.
     * @param gs a game state object.
     */
    public final void show(BodyFactory bf, GameState gs) {
        this.bf = bf;
        this.gs = gs;
        doOnShow();
    }

    /**
     * Internal show method that should be called by public show method
     * @param mapFd fixture definition for terrain body.
     * @param hansX x coordinate for Hans.
     * @param hansY y coordinate for Hans.
     */
    protected void showMap(FixtureDef mapFd, float hansX, float hansY) {
        if (bf == null || gs == null) {
            Gdx.app.error("Level", "Body factory or game state not set.");
            Gdx.app.exit();
            while (true);
        }

        /* Create map terrain */
        createTerrain(mapFd);

        /* Spawn player */
        hans = (Hans)bf.spawnHans(hansX, hansY, 0);
        spawnGrenade();
    }

    /**
     * Initialize terrain.
     * @param mapFd map fixture definition.
     */
    private void createTerrain(FixtureDef mapFd) {
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
     * Get level highscore.
     * @return higscore.
     */
    public int getHighScore() {
        return 100;
        // TODO get high score
    }

    /**
     * Set level highscore.
     * @param score
     */
    public void setHighScore(int score) {
        // TODO Set high score.
    }

    /**
     * Check if grenade has been used and spawn a new one if needed.
     */
    private void updateHans() {

        if (hans.isPalmJointActive() == false) {
            GameState.Grenades grenades = gs.getGrenades();
            if (grenades.getCount() > 0) {
                grenadeSpawnCounter += Gdx.graphics.getDeltaTime();
                if (grenadeSpawnCounter >= grenadeSpawnDelay) {
                    grenades.decrement();
                    if (grenades.getCount() > 0)
                        spawnGrenade();
                    grenadeSpawnCounter = 0.0f;
                }
            }
        }
    }

    /**
     * Spawn a new grenade.
     */
    private void spawnGrenade() {
        hans.setPalmJoint(((SlingshotActor)bf.spawnGrenade(
                hans.getX() + 50.0f,
                hans.getY() + 30.0f, 0)).getBody());
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        sprite.draw(batch);
        updateHans();
    }

    @Override
    public String toString() {
        return "Level: " + levelName;
    }
}
