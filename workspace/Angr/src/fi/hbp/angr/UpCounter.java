package fi.hbp.angr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Up counter class for score animations
 */
public class UpCounter {
    private int value;
    private int currentValue;
    private final float delayEnd;
    private float delayCounter = 0;
    private boolean stopped = false;
    private final boolean sounds;
    private static Sound clickSound;

    /**
     * Constructor for up counter.
     * @param value target value.
     * @param interval time between ticks.
     * @param sounds enable sounds.
     */
    public UpCounter(int value, float interval, boolean sounds) {
        this.value = value;
        this.delayEnd = interval;
        this.sounds = sounds;

        if (sounds && clickSound == null) {
            clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
        }
    }

    /**
     * Update counter state.
     * @param dt delta time.
     */
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

    /**
     * Get current value of the counter.
     * @return current value.
     */
    public int getValue() {
        return currentValue;
    }

    /**
     * Is this counter stopped.
     * @return true if counter is stopped; false otherwise.
     */
    public boolean isStopped() {
        return stopped;
    }
}
