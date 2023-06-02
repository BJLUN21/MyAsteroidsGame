package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {
	@Override
	public void process(GameData gameData, World world) {
		for (Entity e1 : world.getEntities()) {
			for (Entity e2 : world.getEntities()) {
				if (checkCollisions(e1, e2)) {
					LifePart lp1 = e1.getPart(LifePart.class);
					LifePart lp2 = e2.getPart(LifePart.class);

					if (checkIfFriendly(e1, e2)) {
						continue;
					}

					if (e1.getIsAsteroid() && e2.getIsAsteroid()) {
						continue;
					}

					decreaseLife(lp1);
					decreaseLife(lp2);

					if (e1.getIsPlayer()) {
						System.out.println("Player HP = " + lp1.getLife());
					} else if (e2.getIsPlayer()) {
						System.out.println("Player HP = " + lp2.getLife());
					}
					if (e1.getIsEnemy()) {
						System.out.println("Enemy HP = " + lp1.getLife());
					} else if (e2.getIsEnemy()) {
						System.out.println("Enemy HP = " + lp2.getLife());
					}
					if (e1.getIsAsteroid()) {
						System.out.println("Asteroid HP = " + lp1.getLife());
					} else if (e2.getIsAsteroid()) {
						System.out.println("Asteroid HP = " + lp2.getLife());
					}
				}
			}
		}
	}

	public boolean checkIfFriendly(Entity e1, Entity e2) {
		return e1.getIsFriendly() == e2.getIsFriendly();
	}

	public boolean checkCollisions(Entity e1, Entity e2) {
		if (e1 != e2) {
			PositionPart pp1 = e1.getPart(PositionPart.class);
			PositionPart pp2 = e2.getPart(PositionPart.class);
			float dx = pp1.getX() - pp2.getX();
			float dy = pp1.getY() - pp2.getY();
			float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

			return distance < (e1.getRadius() + e2.getRadius());
		}
		return false;
	}

	public void decreaseLife(LifePart lp) {
		lp.setLife(lp.getLife() - 1);
	}
}