package team04.strategies;

import team04.BasePlayer;
import hockey.api.IPlayer;
import hockey.api.IPlayerControl;
import hockey.api.IPuck;
import hockey.api.Position;

public class ChargeStrategy {
	private static final Position GOAL_POSITION = new Position(2600, 0);
	
	public static void act(BasePlayer player, IPuck iPuck) {
		if (!player.hasPuck()) {
			player.moveStick(IPlayerControl.MIN_STICK_ANGLE, 0);
		}
		if (player.isInOffensiveZone()) {
			IPlayer goalie = player.getPlayer(6);
			int yTarget = GOAL_POSITION.getY();
			if (goalie.getStickY() > 0) {
				yTarget -= 85;
			} else {
				yTarget += 85;
			}
			
			if (player.clearShot(yTarget, GOAL_POSITION.getY())){
				player.shoot(GOAL_POSITION.getX(), yTarget, IPlayerControl.MAX_SHOT_SPEED);
			} else {
				player.pass(5);
			}
		} else {
			player.smartSkate(GOAL_POSITION, IPlayerControl.MAX_SPEED);
		}

	}
}