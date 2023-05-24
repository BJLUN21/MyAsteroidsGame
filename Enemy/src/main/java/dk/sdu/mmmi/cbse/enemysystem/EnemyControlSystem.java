package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
	@Override
	public void process(GameData gameData, World world) {

		double rng = Math.random();

		for (Entity enemy : world.getEntities(Enemy.class)) {
			PositionPart positionPart = enemy.getPart(PositionPart.class);
			MovingPart movingPart = enemy.getPart(MovingPart.class);
			LifePart lp = enemy.getPart(LifePart.class);

			// turning
			if (rng < 0.5) {
				movingPart.setLeft(true);
			} else {
				movingPart.setRight(true);
			}

			// accelerating
			if (rng < 0.15 || rng >= 0.85) {
				movingPart.setUp(true);
			}

			// shooting
			if (rng > 0.48  && rng <= 0.52) {
				for (BulletSPI bullet : getBulletSPIs()) {
					world.addEntity(bullet.createBullet(enemy, gameData));
				}
			}

			movingPart.process(gameData, enemy);
			positionPart.process(gameData, enemy);

			if (lp.getLife() <= 0) {
				world.removeEntity(enemy);
			}

			updateShape(enemy);

			//reset directions
			movingPart.setLeft(false);
			movingPart.setRight(false);
			movingPart.setUp(false);
		}
	}

	private void updateShape(Entity entity) {
		Enemy enemy = (Enemy) entity;

		float[] shapex = enemy.getShapeX();
		float[] shapey = enemy.getShapeY();
		PositionPart positionPart = enemy.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();
		float radians = positionPart.getRadians();

		shapex[0] = (float) (x + Math.cos(radians) * 9);
		shapey[0] = (float) (y + Math.sin(radians) * 9);

		shapex[1] = (float) (x + Math.cos(radians - Math.PI / 3) * 8);
		shapey[1] = (float) (y + Math.sin(radians - Math.PI / 3) * 8);

		shapex[2] = (float) (x + Math.cos(radians - 2 * Math.PI / 3) * 2);
		shapey[2] = (float) (y + Math.sin(radians - 2 * Math.PI / 3) * 2);

		shapex[3] = (float) (x + Math.cos(radians - 4 * Math.PI / 5) * 8);
		shapey[3] = (float) (y + Math.sin(radians - 4 * Math.PI / 5) * 8);

		shapex[4] = (float) (x + Math.cos(radians + Math.PI) * 9);
		shapey[4] = (float) (y + Math.sin(radians + Math.PI) * 9);

		shapex[5] = (float) (x + Math.cos(radians + 4 * Math.PI / 5) * 8);
		shapey[5] = (float) (y + Math.sin(radians + 4 * Math.PI / 5) * 8);

		shapex[6] = (float) (x + Math.cos(radians + 2 * Math.PI / 3) * 2);
		shapey[6] = (float) (y + Math.sin(radians + 2 * Math.PI / 3) * 2);

		shapex[7] = (float) (x + Math.cos(radians + Math.PI / 3) * 8);
		shapey[7] = (float) (y + Math.sin(radians + Math.PI / 3) * 8);

		enemy.setShapeX(shapex);
		enemy.setShapeY(shapey);
	}

	private Collection<? extends BulletSPI> getBulletSPIs() {
		return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}
}
