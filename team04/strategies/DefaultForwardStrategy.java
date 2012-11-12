package team04.strategies;

import team04.BasePlayer;
import hockey.api.IPuck;

public class DefaultForwardStrategy {
	public static void act(BasePlayer player, IPuck puck) {
		if (player.hasPuck()) {
			if (player.isBehindOpposingGoal()) {
				player.smartSkate(BasePlayer.GOAL_POSITION.getX() - 200, player.getY(), BasePlayer.MAX_SPEED);
			} else {
				ChargeStrategy.act(player, puck);
			}
						
		} else if(player.getIsFaceOff()){
			player.setIsFaceOff(false);		
		}else if (player.puckIsHeldByOwnTeam()) {
			player.defaultSkate(3000);
		} else {
			player.chasePuck();
		}
	}
}
