package fi.hbp.angr;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Angr";
		cfg.useGL20 = true;
		cfg.width = 1024;
		cfg.height = 768;

		G.scoreboard = new DesktopScoreboard();
		new LwjglApplication(new GdxGame(), cfg);
	}
}
