package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 * @author corfixen
 */
public class Player extends Entity {

	public Player() {
		setIsPlayer(true);
		setIsFriendly(true);
	}
}
