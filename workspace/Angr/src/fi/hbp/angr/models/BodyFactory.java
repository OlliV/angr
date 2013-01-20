package fi.hbp.angr.models;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BodyFactory {
    private InputMultiplexer inputMultiplexer;
    private BodyEditorLoader bel;
    protected AssetContainer asGrenade = new AssetContainer();

    /**
     * Add assets of this class to a preload list
     * TODO Should the map/level tell what assets it will need?
     */
    public static void preload() {
        Grenade.preload();
    }

    /* TODO We need a function to unload unneeded assets */

    public BodyFactory(InputMultiplexer inputMultiplexer) {
        this.inputMultiplexer = inputMultiplexer;

        bel = new BodyEditorLoader(Gdx.files.internal("models.json"));
        Grenade.initAssets(asGrenade, bel);
    }

    public Actor createGrenade(Stage stage, World world, float x, float y, float angle) {
        Grenade act = new Grenade(stage, world, bel, asGrenade, x, y, angle);
        inputMultiplexer.addProcessor(act);
        return act;
    }
}
