package team04;

import hockey.api.Util;

public class Center extends BasePlayer {
	// Number of center player
	public int getNumber() { return 19; }

	// Center player's intelligence
	public void step() {
		if (isPenaltyShot) {
			charge();
		} else {
			if (isFaceOff) {
				setMessage(new Boolean(hasPuck()).toString());
				if (hasPuck()) {
					passAForward();
					isFaceOff = false;
				}
				
			}
			if (offend()){
				if (hasPuck()){
					if(Util.dist(getPlayer(5), getPlayer(6)) < 1500)
						shootToScore();
					else {
						smartSkate(GOAL_POSITION, MAX_SPEED);
					}
							
				}
				else
					defaultSkate(1000);
			}
			else
				if(Util.dist(getPuck(), getDefaultPosition()) < 1000)
					smartSkate(getPuck(), 1000);
				else
					defaultSkate(1000);

			}
		}
	}

