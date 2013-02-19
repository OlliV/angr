package fi.hbp.angr;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.swarmconnect.Swarm;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;

        initialize(new GdxGame(), cfg);
        Swarm.setActive(this);
        G.scoreboard = new AndroidScoreboard();
    }

    @Override
    public void onResume() {
        super.onResume();
        Swarm.setActive(this);

        Swarm.init(this, 4279, SwarmAppKey.key);
    }

    @Override
    public void onPause() {
        super.onPause();
        Swarm.setInactive(this);
    }
}