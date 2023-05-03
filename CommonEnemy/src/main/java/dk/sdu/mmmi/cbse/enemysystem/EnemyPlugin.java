package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

	private Entity enemy;

	//Default constructor
	public EnemyPlugin() {
	}

	@Override
	public void start(GameData gameData, World world) {

		// Add entities to the world
		enemy = createEnemyShip(gameData);
		world.addEntity(enemy);
	}

	@Override
	public void stop(GameData gameData, World world) {
		// Remove entities
		world.removeEntity(enemy);
	}

	private Entity createEnemyShip(GameData gameData) {

		float x = gameData.getDisplayWidth() * (float) Math.random();
		float y = gameData.getDisplayHeight() * (float) Math.random();

		float maxSpeed = 150;
		float acceleration = 100;
		float deceleration = 10;

		float radians = (float) Math.PI * 2 * (float) Math.random();
		float rotationSpeed = 6;

		float radius = 4f;

		Entity enemyShip = new Enemy();
		enemyShip.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
		enemyShip.add(new PositionPart(x, y, radians));
		enemyShip.add(new LifePart(3));
		enemyShip.setRadius(radius);

		return enemyShip;
	}
}
