package team04.strategies;

import hockey.api.GoalKeeper;
import hockey.api.IGoalKeeperControl;
import hockey.api.IPuck;

public class GoaliePenaltyStrategy {
	public static void act(GoalKeeper goalie, IPuck puck) {
		if (puck.isHeld()) {
			goalie.skate(puck.getHolder(), IGoalKeeperControl.MAX_SPEED);
		} else {
			goalie.skate(0, 0, IGoalKeeperControl.MAX_SPEED);
		}
			
	}
}
