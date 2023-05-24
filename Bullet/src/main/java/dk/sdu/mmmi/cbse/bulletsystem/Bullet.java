package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Bullet extends Entity {

	public Bullet(boolean isFriendly) {
		setIsBullet(true);
		setIsFriendly(isFriendly);
		setShapeX(new float[2]);
		setShapeY(new float[2]);
	}
}
