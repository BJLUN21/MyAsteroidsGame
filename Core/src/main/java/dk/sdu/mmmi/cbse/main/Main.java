package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {

	public static void main(String[] args) {
/*
		System.setProperty("org.lwjgl.util.Debug", "true");
		System.setProperty("org.lwjgl.util.DebugLoader", "true");
*/
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("Asteroids");
		int width = 1280;
		int height = 720;
		cfg.setWindowSizeLimits(width, height, width, height);
		cfg.setWindowedMode(width, height);
		cfg.setResizable(false);

		new Lwjgl3Application(new Game(), cfg);
	}
}
