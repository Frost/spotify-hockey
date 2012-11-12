package team04.strategies;

import hockey.api.IPuck;
import team04.BasePlayer;

public class CenterFaceOffStrategy {
	public static void act(BasePlayer player, IPuck puck) {
		player.setMessage(new Boolean(player.hasPuck()).toString());
		if (player.hasPuck()) {
			player.passAForward();
			player.setIsFaceOff(false);
		}
	}
}
