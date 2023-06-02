package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {

		// Initialize Spring container
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(GameConfiguration.class);
		context.refresh();

		// Obtain an instance of the Game class from the Spring container
		Game game = context.getBean(Game.class);

		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("Asteroids");
		int width = 1280;
		int height = 720;
		cfg.setWindowSizeLimits(width, height, width, height);
		cfg.setWindowedMode(width, height);
		cfg.setResizable(false);

		new Lwjgl3Application(game, cfg);
	}
}
