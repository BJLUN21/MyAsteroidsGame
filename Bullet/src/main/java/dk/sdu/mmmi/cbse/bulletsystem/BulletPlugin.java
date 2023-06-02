package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService, BulletSPI {

	@Override
	public void start(GameData gameData, World world) {
	}

	@Override
	public void stop(GameData gameData, World world) {
	}

	@Override
	public Entity createBullet(Entity entity, GameData gameData) {
		PositionPart pp = entity.getPart(PositionPart.class);

		float x = pp.getX();
		float y = pp.getY();
		float radians = pp.getRadians();

		float maxSpeed = 500;
		float acceleration = 5000;
		float deceleration = 0;

		float rotationSpeed = 0;

		float radius = 0.5f;

		Entity bullet = new Bullet(entity.getIsFriendly());
		bullet.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
		bullet.add(new PositionPart(x + (float) Math.cos(radians) * 12, y + (float) Math.sin(radians) * 12, radians));
		bullet.add(new LifePart(1,1));
		bullet.setRadius(radius);

		return bullet;
	}
}
