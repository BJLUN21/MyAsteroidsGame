package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.bulletsystem.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;

/**
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

	@Override
	public void process(GameData gameData, World world) {

		for (Entity player : world.getEntities(Player.class)) {
			PositionPart positionPart = player.getPart(PositionPart.class);
			MovingPart movingPart = player.getPart(MovingPart.class);

			// turning
			movingPart.setLeft(gameData.getKeys().isDown(LEFT));
			movingPart.setRight(gameData.getKeys().isDown(RIGHT));

			// accelerating
			movingPart.setUp(gameData.getKeys().isDown(UP));

			// shooting
			if (gameData.getKeys().isPressed(SPACE)) {
				world.addEntity(shoot(gameData, positionPart.getX(), positionPart.getY(), positionPart.getRadians()));
			}

			movingPart.process(gameData, player);
			positionPart.process(gameData, player);

			updateShape(player);
		}
	}

	private void updateShape(Entity entity) {
		float[] shapex = entity.getShapeX();
		float[] shapey = entity.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();
		float radians = positionPart.getRadians();

		shapex[0] = (float) (x + Math.cos(radians) * 8);
		shapey[0] = (float) (y + Math.sin(radians) * 8);

		shapex[1] = (float) (x + Math.cos(radians - 4 * Math.PI / 5) * 8);
		shapey[1] = (float) (y + Math.sin(radians - 4 * Math.PI / 5) * 8);

		shapex[2] = (float) (x + Math.cos(radians + Math.PI) * 5);
		shapey[2] = (float) (y + Math.sin(radians + Math.PI) * 5);

		shapex[3] = (float) (x + Math.cos(radians + 4 * Math.PI / 5) * 8);
		shapey[3] = (float) (y + Math.sin(radians + 4 * Math.PI / 5) * 8);

		entity.setShapeX(shapex);
		entity.setShapeY(shapey);
	}

	private Entity shoot(GameData gameData, float x, float y, float radians) {

		float maxSpeed = 500;
		float acceleration = 5000;
		float deceleration = 0;

		float rotationSpeed = 0;

		float radius = 0.5f;

		Entity bullet = new Bullet(true);
		bullet.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
		bullet.add(new PositionPart(x + (float) Math.cos(radians) * 12, y + (float) Math.sin(radians) * 12, radians));
		bullet.add(new LifePart(1,1));
		bullet.setRadius(radius);

		return bullet;
	}

}
