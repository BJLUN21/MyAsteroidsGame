package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Enemy extends Entity {

	public Enemy() {
		setIsEnemy(true);
		setIsFriendly(false);
		setShapeX(new float[8]);
		setShapeY(new float[8]);
	}
}
