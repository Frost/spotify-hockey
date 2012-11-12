package team04;

import team04.strategies.ChargeStrategy;
import team04.strategies.DefaultDefendStrategy;

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
			ChargeStrategy.act(this, getPuck());
		} else {
			DefaultDefendStrategy.act(this, getPuck());
		}
		
	}

}
