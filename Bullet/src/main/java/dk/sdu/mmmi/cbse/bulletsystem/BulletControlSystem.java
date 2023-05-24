package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
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

	@Override
	public Entity createBullet(Entity e, GameData gameData) {
		PositionPart pp = e.getPart(PositionPart.class);

		float x = pp.getX();
		float y = pp.getY();
		float radians = pp.getRadians();

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
