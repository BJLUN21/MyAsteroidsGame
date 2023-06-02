package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {
	@Override
	public void process(GameData gameData, World world) {

		for (Entity bullet : world.getEntities(Bullet.class)) {

			PositionPart positionPart = bullet.getPart(PositionPart.class);
			MovingPart movingPart = bullet.getPart(MovingPart.class);
			LifePart lifePart = bullet.getPart(LifePart.class);

			// accelerating
			movingPart.setUp(true);

			// reduce lifepart
			lifePart.reduceExpiration(gameData.getDelta());

			if (lifePart.getExpiration() <= 0) {
				world.removeEntity(bullet);
			}

			movingPart.process(gameData, bullet);
			positionPart.process(gameData, bullet);
			lifePart.process(gameData, bullet);

			updateShape(bullet);
		}
	}

	private void updateShape(Entity entity) {
		float[] shapex = entity.getShapeX();
		float[] shapey = entity.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();
		float radians = positionPart.getRadians();

		shapex[0] = (float) (x + Math.cos(radians) * 1);
		shapey[0] = (float) (y + Math.sin(radians) * 1);

		shapex[1] = (float) (x + Math.cos(radians) * 2);
		shapey[1] = (float) (y + Math.sin(radians) * 2);

		entity.setShapeX(shapex);
		entity.setShapeY(shapey);
	}
}
