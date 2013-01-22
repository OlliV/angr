package fi.hbp.angr;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.models.items.Box;
import fi.hbp.angr.models.items.Grenade;

public class BodyFactory {
    private InputMultiplexer inputMultiplexer;
    private ItemDestruction ides;
    private BodyEditorLoader bel;
    protected AssetContainer asGrenade = new AssetContainer();
    protected AssetContainer asBox = new AssetContainer();

    /**
     * Add assets of this class to a preload list
     * TODO Should the map/level tell what assets it will need?
     */
    public static void preload() {
        Grenade.preload();
        Box.preload();
    }

    /* TODO We need a function to unload unneeded assets */

    public BodyFactory(InputMultiplexer inputMultiplexer, ItemDestruction ides) {
        this.inputMultiplexer = inputMultiplexer;
        this.ides = ides;

        bel = new BodyEditorLoader(Gdx.files.internal("models.json"));
        Grenade.initAssets(asGrenade, bel);
        Box.initAssets(asBox, bel);
    }

    public Actor createGrenade(Stage stage, World world, float x, float y, float angle) {
        Grenade act = new Grenade(stage, world, bel, asGrenade, x, y, angle);
        inputMultiplexer.addProcessor(act);
        return act;
    }

    public Actor createBox(World world, float x, float y, float angle) {
        Box act = new Box(world, ides, bel, asBox, x, y, angle);
        return act;
    }
}
