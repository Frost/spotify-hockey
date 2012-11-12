package team04;

import team04.strategies.ChargeStrategy;
import team04.strategies.DefaultForwardStrategy;

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
			ChargeStrategy.act(this, getPuck());
		else {
			DefaultForwardStrategy.act(this, getPuck());
		}
	}

}
