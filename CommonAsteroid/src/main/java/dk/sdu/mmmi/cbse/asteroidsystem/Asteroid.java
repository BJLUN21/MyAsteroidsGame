package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {

	public Asteroid(boolean isSplit) {
		setIsAsteroid(true);
		setIsSplit(isSplit);
		setShapeX(new float[12]);
		setShapeY(new float[12]);
	}
}
