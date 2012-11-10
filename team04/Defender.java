package team04;

import hockey.api.*;

public class Defender extends BasePlayer {
	// Number of defender
	public int getNumber() { return 5; }
	
	public boolean isLeftDefender(){
		return getIndex() == 1;
	}

	// Make left defender left handed, right defender right handed.
	public boolean isLeftHanded() { return isLeftDefender(); }

	// Initiate
	public void init() {
		setAimOnStick(false);
	}

	// Defender intelligence
	public void step() {
		if (isPenaltyShot) {
			charge();
		} else {
			if(offend()){
				if(hasPuck()){
					pass(5);
				}else{
					defaultSkate(1000);
				}
			}else{
				if(getPuck().isHeld()){
					if(puckIsCloserToHomeGoal()){
						chasePuck();
					}else{
						smartSkate(getDefensivePosition(), MAX_SPEED);
					}
				}else{
					if(hasPuck()){
						pass(5);
					}else{
						chasePuck();
					}
				}
			}
		}
		
	}

	private Position getDefensivePosition() {
		int dx = getPuck().getX() - HOME_GOAL_POSITION.getX();
		int dy = getPuck().getY() - HOME_GOAL_POSITION.getY();
		double factor = Math.hypot(dx, dy) / 600;
		
		if (dx < HOME_GOAL_POSITION.getX()) {
			dx = HOME_GOAL_POSITION.getX();
		}
		
		Position target = new Position((int)(HOME_GOAL_POSITION.getX() + dx/factor), (int)(dy/factor));
		return target;
	}
}
