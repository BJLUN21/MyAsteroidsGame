package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

	private Entity player = new Entity();
	private Entity enemy = new Entity();
	private CollisionDetector collisionDetector = new CollisionDetector();


	@BeforeEach
	void setUp() {
		player.setRadius(5);
		enemy.setRadius(5);
	}

	@Test
	void collision() {
		player.add(new PositionPart(1, 4, 10));
		enemy.add(new PositionPart(1, 4, 10));

		assertTrue(collisionDetector.checkCollisions(player, enemy));
	}

	@Test
	void noCollision() {
		player.add(new PositionPart(1, 4, 10));
		enemy.add(new PositionPart(20, 24, 10));

		assertFalse(collisionDetector.checkCollisions(player, enemy));
	}
}