package team04.strategies;

import team04.BasePlayer;
import hockey.api.IPlayerControl;
import hockey.api.IPuck;

public class DefaultDefendStrategy {
	public static void act(BasePlayer player, IPuck puck) {
		if(player.offend()){
			if(player.hasPuck()){
				player.pass(5);
			}else{
				player.defaultSkate(1000);
			}
		}else{
			if(puck.isHeld()){
				if(player.puckIsCloserToHomeGoal()){
					player.chasePuck();
				}else{
					player.smartSkate(player.getDefensivePosition(), IPlayerControl.MAX_SPEED);
				}
			}else{
				if(player.hasPuck()){
					player.pass(5);
				}else{
					player.chasePuck();
				}
			}
		}

	}
}
