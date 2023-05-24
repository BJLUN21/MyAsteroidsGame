package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;

public class AsteroidPlugin implements IGamePluginService {

	private Entity asteroid;

	@Override
	public void start(GameData gameData, World world) {

		// Add entities to the world
		asteroid = createAsteroid(gameData);
		world.addEntity(asteroid);
	}

	@Override
	public void stop(GameData gameData, World world) {
		// Remove entities
		world.removeEntity(asteroid);
	}

	private Entity createAsteroid(GameData gameData) {

		float x = gameData.getDisplayWidth() * (float) Math.random();
		float y = gameData.getDisplayHeight() * (float) Math.random();

		float maxSpeed = 50;
		float acceleration = 50;
		float deceleration = 0;

		float radians = (float) Math.PI * 2 * (float) Math.random();
		float rotationSpeed = 0;

		float radius = 12f;

		Entity asteroid = new Asteroid(false);
		asteroid.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
		asteroid.add(new PositionPart(x, y, radians));
		asteroid.add(new LifePart(1));
		asteroid.setRadius(radius);

		return asteroid;
	}
}
