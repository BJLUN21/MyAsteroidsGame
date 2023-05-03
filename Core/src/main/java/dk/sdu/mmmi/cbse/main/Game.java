package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
//import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
//import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
//import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;
//import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
//import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
//import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
//import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;

import java.util.ArrayList;
import java.util.List;

public class Game
		implements ApplicationListener {

	private static OrthographicCamera cam;
	private final GameData gameData = new GameData();
	private ShapeRenderer sr;
	private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
	private List<IGamePluginService> entityPlugins = new ArrayList<>();
	private List<IPostEntityProcessingService> postEntityProcessors = new ArrayList<>();
	private World world = new World();

	@Override
	public void create() {

		gameData.setDisplayWidth(Gdx.graphics.getWidth());
		gameData.setDisplayHeight(Gdx.graphics.getHeight());

		cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
		cam.translate(gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f);
		cam.update();

		sr = new ShapeRenderer();

		Gdx.input.setInputProcessor(
				new GameInputProcessor(gameData)
		);

		IGamePluginService IGamePluginService;

		for (IGamePluginService iGamePluginService : SPILocator.locateAll(IGamePluginService.class)) {
			iGamePluginService.start(gameData, world);
		}
/*
		IGamePluginService playerPlugin = new PlayerPlugin();
		IGamePluginService enemyPlugin = new EnemyPlugin();
		IGamePluginService asteroidPlugin = new AsteroidPlugin();
		//IGamePluginService bulletPlugin = new BulletPlugin();

		IEntityProcessingService playerProcess = new PlayerControlSystem();
		entityPlugins.add(playerPlugin);
		entityProcessors.add(playerProcess);

		IEntityProcessingService enemyProcess = new EnemyControlSystem();
		entityPlugins.add(enemyPlugin);
		entityProcessors.add(enemyProcess);

		IEntityProcessingService asteroidProcess = new AsteroidControlSystem();
		entityPlugins.add(asteroidPlugin);
		entityProcessors.add(asteroidProcess);

		IEntityProcessingService bulletProcess = new BulletControlSystem();
		//entityPlugins.add(bulletPlugin);
		entityProcessors.add(bulletProcess);

		IPostEntityProcessingService collision = new CollisionDetector();
		postEntityProcessors.add(collision);
		// Lookup all Game Plugins using ServiceLoader
		for (IGamePluginService iGamePlugin : entityPlugins) {
			iGamePlugin.start(gameData, world);
		}
*/
	}

	@Override
	public void render() {

		// clear screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameData.setDelta(Gdx.graphics.getDeltaTime());

		update();

		draw();

		gameData.getKeys().update();
	}

	private void update() {
		// Update
		for (IEntityProcessingService entityProcessorService : SPILocator.locateAll(IEntityProcessingService.class)) {
			entityProcessorService.process(gameData, world);
		}

		for (IPostEntityProcessingService postEntityProcessorService : SPILocator.locateAll(IPostEntityProcessingService.class)) {
			postEntityProcessorService.process(gameData, world);
		}
	}

	private void draw() {
		for (Entity entity : world.getEntities()) {

			sr.begin(ShapeRenderer.ShapeType.Line);

			float[] shapex = entity.getShapeX();
			float[] shapey = entity.getShapeY();

			for (int i = 0, j = shapex.length - 1;
				 i < shapex.length;
				 j = i++) {

				sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
			}

			sr.end();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
