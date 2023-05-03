package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.ArrayList;

public class PlayerPlugin implements IGamePluginService {

	private Entity player;

	public PlayerPlugin() {
	}

	@Override
	public void start(GameData gameData, World world) {

		// Add entities to the world
		player = createPlayerShip(gameData);
		world.addEntity(player);
	}

	@Override
	public void stop(GameData gameData, World world) {
		// Remove entities
		world.removeEntity(player);
	}

	private Entity createPlayerShip(GameData gameData) {

		float x = gameData.getDisplayWidth() / 2f;
		float y = gameData.getDisplayHeight() / 2f;

		float maxSpeed = 300;
		float acceleration = 200;
		float deceleration = 10;

		float radians = (float) Math.PI / 2;
		float rotationSpeed = 5;

		float radius = 4f;

		Entity playerShip = new Player();
		playerShip.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
		playerShip.add(new PositionPart(x, y, radians));
		playerShip.add(new LifePart(3));
		playerShip.setRadius(radius);

		return playerShip;
	}

}
