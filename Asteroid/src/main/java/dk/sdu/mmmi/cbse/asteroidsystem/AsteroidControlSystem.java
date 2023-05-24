package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService, IAsteroidSplitter {
	@Override
	public void process(GameData gameData, World world) {

		for (Entity asteroid : world.getEntities(Asteroid.class)) {
			PositionPart positionPart = asteroid.getPart(PositionPart.class);
			MovingPart movingPart = asteroid.getPart(MovingPart.class);
			LifePart lp = asteroid.getPart(LifePart.class);

			// accelerating
			movingPart.setUp(true);

			movingPart.process(gameData, asteroid);
			positionPart.process(gameData, asteroid);

			if (lp.getLife() <= 0) {
				if (!asteroid.getIsSplit()) {
					createSplitAsteroid(asteroid, world);
				}
				world.removeEntity(asteroid);
			}

			updateShape(asteroid);
		}
	}

	private void updateShape(Entity entity) {
		float[] shapex = entity.getShapeX();
		float[] shapey = entity.getShapeY();
		PositionPart positionPart = entity.getPart(PositionPart.class);
		float x = positionPart.getX();
		float y = positionPart.getY();
		float radians = positionPart.getRadians();
		float sizeMod = 3f;
		
		if(entity.getIsSplit()) {
			sizeMod = 2f;
		}

		shapex[0] = (float) (x + Math.cos(radians) * 9 * sizeMod);
		shapey[0] = (float) (y + Math.sin(radians) * 9 * sizeMod);

		shapex[1] = (float) (x + Math.cos(radians + Math.PI / 8) * 7 * sizeMod);
		shapey[1] = (float) (y + Math.sin(radians + Math.PI / 8) * 7 * sizeMod);

		shapex[2] = (float) (x + Math.cos(radians - 7 * Math.PI / 4) * 9 * sizeMod);
		shapey[2] = (float) (y + Math.sin(radians - 7 * Math.PI / 4) * 9 * sizeMod);

		shapex[3] = (float) (x + Math.cos(radians + 4 * Math.PI / 10) * 8 * sizeMod);
		shapey[3] = (float) (y + Math.sin(radians + 4 * Math.PI / 10) * 8 * sizeMod);

		shapex[4] = (float) (x + Math.cos(radians + Math.PI / 2) * 6 * sizeMod);
		shapey[4] = (float) (y + Math.sin(radians + Math.PI / 2) * 6 * sizeMod);

		shapex[5] = (float) (x + Math.cos(radians + 2 * Math.PI / 3) * 8 * sizeMod);
		shapey[5] = (float) (y + Math.sin(radians + 2 * Math.PI / 3) * 8 * sizeMod);

		shapex[6] = (float) (x + Math.cos(radians - 7 * Math.PI / 6) * 7 * sizeMod);
		shapey[6] = (float) (y + Math.sin(radians - 7 * Math.PI / 6) * 7 * sizeMod);

		shapex[7] = (float) (x + Math.cos(radians + 6 * Math.PI / 5) * 7 * sizeMod);
		shapey[7] = (float) (y + Math.sin(radians + 6 * Math.PI / 5) * 7 * sizeMod);

		shapex[8] = (float) (x + Math.cos(radians + 5 * Math.PI / 4) * 6 * sizeMod);
		shapey[8] = (float) (y + Math.sin(radians + 5 * Math.PI / 4) * 6 * sizeMod);

		shapex[9] = (float) (x + Math.cos(radians + 3 * Math.PI / 2) * 7 * sizeMod);
		shapey[9] = (float) (y + Math.sin(radians + 3 * Math.PI / 2) * 7 * sizeMod);

		shapex[10] = (float) (x + Math.cos(radians - 7 * Math.PI / 3) * 6 * sizeMod);
		shapey[10] = (float) (y + Math.sin(radians - 7 * Math.PI / 3) * 6 * sizeMod);

		shapex[11] = (float) (x + Math.cos(radians - Math.PI / 4) * 8 * sizeMod);
		shapey[11] = (float) (y + Math.sin(radians - Math.PI / 4) * 8 * sizeMod);

		entity.setShapeX(shapex);
		entity.setShapeY(shapey);
	}

	@Override
	public void createSplitAsteroid(Entity entity, World world) {

		PositionPart pp = entity.getPart(PositionPart.class);

		float maxSpeed = 50;
		float acceleration = 50;
		float deceleration = 0;

		float[] displacements = {
				 20f,
				-20f
		};
		float[] radians = {
				(float) Math.PI * 2 * (float) Math.random(),
				(float) Math.PI * 2 * (float) Math.random()
		};
		float rotationSpeed = 0;

		float radius = 3f;

		for (int i = 0; i < 2; i++) {

			float x = pp.getX() + displacements[i];
			float y = pp.getY() + displacements[i];

			Entity asteroid = new Asteroid(true);
			asteroid.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
			asteroid.add(new PositionPart(x, y, radians[i]));
			asteroid.add(new LifePart(1));
			asteroid.setRadius(radius);

			world.addEntity(asteroid);
		}
	}
}
