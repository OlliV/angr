package fi.hbp.angr.models;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Model extends Actor {
    public abstract Body getBody();
}
