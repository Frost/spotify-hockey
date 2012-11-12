package team04.strategies;

import team04.Goalie;
import hockey.api.IGoalKeeperControl;
import hockey.api.IPlayerControl;
import hockey.api.IPuck;

public class DefaultGoalieStrategy {
	public static void act(Goalie goalie, IPuck puck) {
		goalie.setMessage(new Integer(Goalie.MAX_SPEED).toString());
		if (goalie.hasPuck()) {
			goalie.shoot(goalie.getPlayer(5), IGoalKeeperControl.MAX_THROW_SPEED);
		} else {			
			goalie.skate(goalie.calculateTargetPosition(), 150);
			goalie.turn(puck, IPlayerControl.MAX_TURN_SPEED);
		}
	}
}
