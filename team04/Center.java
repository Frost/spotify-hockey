package team04;

import team04.strategies.ChargeStrategy;
import team04.strategies.DefaultCenterStrategy;

public class Center extends BasePlayer {
	// Number of center player
	public int getNumber() { return 19; }

	// Center player's intelligence
	public void step() {
		if (isPenaltyShot) {
			ChargeStrategy.act(this, getPuck());
		} else {
			if (isFaceOff) {
				DefaultCenterStrategy.act(this, getPuck());
			}
			DefaultCenterStrategy.act(this, getPuck());
		}
	}
}

