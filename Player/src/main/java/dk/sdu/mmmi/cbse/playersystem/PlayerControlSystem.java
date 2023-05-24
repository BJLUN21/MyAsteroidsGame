package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.BulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;
import static java.util.stream.Collectors.toList;

/**
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

	@Override
	public void process(GameData gameData, World world) {

		for (Entity player : world.getEntities(Player.class)) {
			PositionPart positionPart = player.getPart(PositionPart.class);
			MovingPart movingPart = player.getPart(MovingPart.class);
			LifePart lp = player.getPart(LifePart.class);

			// turning
			movingPart.setLeft(gameData.getKeys().isDown(LEFT));
			movingPart.setRight(gameData.getKeys().isDown(RIGHT));

			// accelerating
			movingPart.setUp(gameData.getKeys().isDown(UP));

			// shooting
			if (gameData.getKeys().isPressed(SPACE)) {
				for (BulletSPI bullet : getBulletSPIs()) {
					world.addEntity(bullet.createBullet(player, gameData));
				}
			}

			movingPart.process(gameData, player);
			positionPart.process(gameData, player);

			if (lp.getLife() <= 0) {
				world.removeEntity(player);
			}

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

	private Collection<? extends BulletSPI> getBulletSPIs() {
		return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}
}
