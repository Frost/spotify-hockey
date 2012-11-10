package team04;

import java.util.Random;

import hockey.api.GoalKeeper;
import hockey.api.Position;

public class Goalie extends GoalKeeper {
	// Middle of our own goalcage, on the goal line
	protected static final Position HOME_GOAL_POSITION = new Position(-2600, 0);

	private String[] possibleNames = {
			"Joey Pardella",
			"Doctor Doom",
			"Ultra Laser",
			"Master of Disaster"
	};

	// Number of the goalie.
	public int getNumber() { return 1; }

	// Name of the goalie.
	public String getName() { 
		return possibleNames[new Random().nextInt(4)];
	}

	// Left handed goalie
	public boolean isLeftHanded() { return true; }

	// Initiate
	public void init() { }

	// Face off
	public void faceOff() { }

	// Called when the goalie is about to receive a penalty shot
	public void penaltyShot() { }

	// Intelligence of goalie.
	public void step() {
		setMessage(new Integer(MAX_SPEED).toString());
		if (hasPuck()) {
			shoot(getPlayer(5), MAX_THROW_SPEED);
		} else {			
			skate(calculateTargetPosition(), 150);
			turn(getPuck(), MAX_TURN_SPEED);
		}
	}

	private Position calculateTargetPosition() {
		int dx = getPuck().getX() - HOME_GOAL_POSITION.getX() + 20;
		int dy = getPuck().getY() - HOME_GOAL_POSITION.getY();
		double factor = Math.hypot(dx, dy) / 125;
		
		if (dx < HOME_GOAL_POSITION.getX()) {
			dx = HOME_GOAL_POSITION.getX() + 50;
		}
		
		int xTarget = (int)(HOME_GOAL_POSITION.getX() + dx/factor);

		if (xTarget < HOME_GOAL_POSITION.getX()) {
			xTarget = HOME_GOAL_POSITION.getX() + 50;
		}

		Position target = new Position(xTarget, (int)(dy/factor));
		return target;
	}
}
