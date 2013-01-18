package fi.hbp.angr.models;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BodyFactory {
    private BodyEditorLoader bel;
    private AssetContainer asGrenade = new AssetContainer();

    public static void preload() {
        Grenade.preload();
    }

    public BodyFactory() {
        bel = new BodyEditorLoader(Gdx.files.internal("models.json"));
        Grenade.initAssets(asGrenade, bel);
    }

    public Actor createGrenade(World world, float x, float y, float angle) {
        return new Grenade(world, bel, asGrenade, x, y, angle);
    }
}
