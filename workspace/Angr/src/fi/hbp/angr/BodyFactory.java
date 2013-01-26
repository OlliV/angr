package fi.hbp.angr;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.models.Hans;
import fi.hbp.angr.models.items.Box;
import fi.hbp.angr.models.items.Grenade;

public class BodyFactory {
    private Stage stage;
    private World world;
    private InputMultiplexer inputMultiplexer;
    private BodyEditorLoader bel;
    private ItemDestruction itdes;
    protected AssetContainer asGrenade = new AssetContainer();
    protected AssetContainer asBox = new AssetContainer();
    protected Hans.HansAssetContainer hac = new Hans.HansAssetContainer();

    /**
     * Add assets of this class to a preload list
     * TODO Should the map/level tell what assets it will need?
     */
    public static void preload() {
        Grenade.preload();
        Box.preload();
        Hans.preload();
    }

    /* TODO Add a function to unload unneeded assets */

    /**
     * TODO
     * @param stage
     * @param world
     * @param inputMultiplexer
     * @param ides
     */
    public BodyFactory(Stage stage, World world, ItemDestruction itdes, InputMultiplexer inputMultiplexer) {
        this.stage = stage;
        this.world = world;
        this.itdes = itdes;
        this.inputMultiplexer = inputMultiplexer;

        bel = new BodyEditorLoader(Gdx.files.internal("models.json"));

        /* Initialize assets */
        Grenade.initAssets(asGrenade, bel);
        Box.initAssets(asBox, bel);
        Hans.initAssets(hac, bel);
    }

    /**
     * TODO
     * @return
     */
    public World getWorld() {
        return world;
    }

    /**
     * TODO
     * @param x
     * @param y
     * @param angle
     * @return
     */
    public Actor spawnGrenade(float x, float y, float angle) {
        Grenade actor = new Grenade(stage, world, itdes, bel, asGrenade, x, y, angle);
        inputMultiplexer.addProcessor(actor);
        stage.addActor(actor);
        return actor;
    }

    /**
     * TODO
     * @param x
     * @param y
     * @param angle
     * @return
     */
    public Actor spawnBox(float x, float y, float angle) {
        Box actor = new Box(world, itdes, bel, asBox, x, y, angle);
        stage.addActor(actor);
        return actor;
    }

    public Actor spawnHans(float x, float y, float angle) {
        Hans actor = new Hans(stage, world, bel, hac, x, y, angle);
        inputMultiplexer.addProcessor(actor);
        stage.addActor(actor);
        return actor;
    }
}
