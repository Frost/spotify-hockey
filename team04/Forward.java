package team04;

import java.security.acl.Owner;

public class Forward extends BasePlayer {
	// Number of forward
	public int getNumber() { return 15; }

	public boolean isLeftForward(){
		return getIndex() == 1;
	}

	// Make left defender left handed, right defender right handed.
	public boolean isLeftHanded() { return isLeftForward(); }

	// Intelligence of forward
	public void step() {
		if (isPenaltyShot)
			charge();
		else {
			if (hasPuck()) {
				if (isBehindOpposingGoal()) {
					smartSkate(GOAL_POSITION.getX() - 200, GOAL_POSITION.getY(), MAX_SPEED);
				} else {
					charge();
				}
							
			} else if(isFaceOff){
				isFaceOff = false;		
			}else if (puckIsHeldByOwnTeam()) {
				defaultSkate(3000);
			} else {
				chasePuck();
			}

		}
		
	}

}
