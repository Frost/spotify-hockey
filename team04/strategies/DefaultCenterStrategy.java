package team04.strategies;

import team04.BasePlayer;
import hockey.api.IPlayerControl;
import hockey.api.IPuck;
import hockey.api.Util;

public class DefaultCenterStrategy {
	public static void act(BasePlayer player, IPuck puck) {
		if (player.offend()){
			if (player.hasPuck()){
				if(Util.dist(player, player.getPlayer(6)) < 1500) {
					player.shootToScore();					
				} else {
					player.smartSkate(BasePlayer.GOAL_POSITION, IPlayerControl.MAX_SPEED);
				}
			} else {
				player.defaultSkate(1000);				
			}
		} else {
			if(Util.dist(puck, player.getDefaultPosition()) < 1000) {
				player.smartSkate(puck, 1000);
			}
			else {
				player.defaultSkate(1000);	
			}
		}

	}
}
