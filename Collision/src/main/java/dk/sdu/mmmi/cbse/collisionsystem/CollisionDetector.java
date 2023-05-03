package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.asteroidsystem.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {
	@Override
	public void process(GameData gameData, World world) {
		for (Entity e1 : world.getEntities()) {
			for (Entity e2 : world.getEntities()) {
				if (checkCollisions(e1, e2)) {
					System.out.println("Collision detected between " + e1 + " & " + e2);

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

					if (checkLife(lp1)) {
						if (e1.getIsAsteroid()) {
							if (!e1.getIsSplit()) {
								world.addEntity(createSplitAsteroid(e1, 20f));
								world.addEntity(createSplitAsteroid(e1, -20f));
							}
						}
						world.removeEntity(e1);
					}
					if (checkLife(lp2)) {
						if (e2.getIsAsteroid()) {
							if (!e2.getIsSplit()) {
								world.addEntity(createSplitAsteroid(e2, 20f));
								world.addEntity(createSplitAsteroid(e2, -20f));
							}
						}
						world.removeEntity(e2);
					}
				}
			}
		}
	}

	public boolean checkIfFriendly(Entity e1, Entity e2) {
		if(e1.getIsPlayer() && e2.getIsBullet()) {
			System.out.println(e2.getIsFriendly());
			return e2.getIsFriendly();
		} else if (e2.getIsPlayer() && e1.getIsBullet()) {
			System.out.println(e2.getIsFriendly());
			return e1.getIsFriendly();
		}

		if(e1.getIsEnemy() && e2.getIsBullet()) {
			System.out.println(e2.getIsFriendly());
			return !e2.getIsFriendly();
		} else if (e2.getIsEnemy() && e1.getIsBullet()) {
			System.out.println(e2.getIsFriendly());
			return !e1.getIsFriendly();
		}
		return false;
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

	public boolean checkLife(LifePart lp) {
		return lp.getLife() <= 0;
	}

	private Entity createSplitAsteroid(Entity e, float displacement) {

		PositionPart pp = e.getPart(PositionPart.class);

		float x = pp.getX() + displacement;
		float y = pp.getY() + displacement;

		float maxSpeed = 50;
		float acceleration = 50;
		float deceleration = 0;

		float radians = (float) Math.PI * 2 * (float) Math.random();
		float rotationSpeed = 0;

		float radius = 3f;

		Entity asteroid = new Asteroid(true);
		asteroid.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
		asteroid.add(new PositionPart(x, y, radians));
		asteroid.add(new LifePart(1));
		asteroid.setRadius(radius);

		return asteroid;
	}
}