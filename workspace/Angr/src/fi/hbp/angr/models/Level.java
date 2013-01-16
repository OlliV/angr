package fi.hbp.angr.models;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fi.hbp.angr.Assets;
import fi.hbp.angr.G;

public class Level extends Actor {

    private Body body;

    private Sprite sprite;

    public Level(String levelName, World world) {
        BodyEditorLoader bel = new BodyEditorLoader(
                Gdx.files.internal("data/levels.json"));

        Texture texture = Assets.getAssetManager().get(
                "data/" + levelName + ".png",
                Texture.class);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.StaticBody;
        bd.position.set(0, 0);

        FixtureDef fd = new FixtureDef();
        fd.density = 0.6f;
        fd.friction = 0.7f;
        fd.restitution = 0.3f;
        fd.filter.categoryBits = CollisionFilterMasks.WALL;

        sprite = new Sprite(texture);
        sprite.setPosition(0, 0);

        body = world.createBody(bd);
        bel.attachFixture(body, levelName, fd, sprite.getWidth() / G.BOX_TO_WORLD);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}
