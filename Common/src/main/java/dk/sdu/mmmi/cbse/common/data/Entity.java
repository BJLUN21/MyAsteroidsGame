package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
	private final UUID ID = UUID.randomUUID();

	private float[] shapeX = new float[4];
	private float[] shapeY = new float[4];
	private float radius;
	private Map<Class, EntityPart> parts;
	
	private boolean isPlayer = false;
	private boolean isEnemy = false;
	private boolean isAsteroid = false;
	private boolean isSplit = false;
	private boolean isBullet = false;
	private boolean isFriendly = false;

	public Entity() {
		parts = new ConcurrentHashMap<>();
	}

	public void add(EntityPart part) {
		parts.put(part.getClass(), part);
	}

	public void remove(Class partClass) {
		parts.remove(partClass);
	}

	public <E extends EntityPart> E getPart(Class partClass) {
		return (E) parts.get(partClass);
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float r) {
		this.radius = r;
	}

	public String getID() {
		return ID.toString();
	}

	public float[] getShapeX() {
		return shapeX;
	}

	public void setShapeX(float[] shapeX) {
		this.shapeX = shapeX;
	}

	public float[] getShapeY() {
		return shapeY;
	}

	public void setShapeY(float[] shapeY) {
		this.shapeY = shapeY;
	}
	
	public boolean getIsPlayer() {
		return isPlayer;
	}
	
	public void setIsPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public boolean getIsEnemy() {
		return isEnemy;
	}

	public void setIsEnemy(boolean isEnemy) {
		this.isEnemy = isEnemy;
	}

	public boolean getIsAsteroid() {
		return isAsteroid;
	}

	public void setIsAsteroid(boolean isAsteroid) {
		this.isAsteroid = isAsteroid;
	}

	public boolean getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(boolean isSplit) {
		this.isSplit = isSplit;
	}

	public boolean getIsBullet() {
		return isBullet;
	}

	public void setIsBullet(boolean isBullet) {
		this.isBullet = isBullet;
	}

	public boolean getIsFriendly() {
		return isFriendly;
	}

	public void setIsFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}
}
