package fi.hbp.angr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class UpCounter {
    private int value;
    private int currentValue;
    private final float delayEnd;
    private float delayCounter = 0;
    private boolean stopped = false;
    private final boolean sounds;
    private static Sound clickSound;

    public UpCounter(int value, float delay, boolean sounds) {
        this.value = value;
        this.delayEnd = delay;
        this.sounds = sounds;

        if (sounds && clickSound == null) {
            clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
        }
    }

    public void update(float dt) {
        if (stopped)
            return;

        if (sounds)
            clickSound.play();

        delayCounter += dt;
        if (delayCounter >= delayEnd) {
            delayCounter = 0;

            currentValue += 5;
            if (currentValue >= value) {
                currentValue = value;
                stopped = true;
            }
        }
    }

    public int getValue() {
        return currentValue;
    }

    public boolean isStopped() {
        return stopped;
    }
}
