package team04;

import java.util.Random;

import hockey.api.*;

public abstract class BasePlayer extends Player {
	// The middle of the opponents goal, on the goal line
	public static final Position GOAL_POSITION = new Position(2600, 0);
	protected static final Position HOME_GOAL_POSITION = new Position(-2600, 0);
	protected static final int SHOT_CAREFULNESS = 5;
	protected static final int PASS_SPEED = 2222;
	private static final Position ORIGO = new Position(0,0);
	private static final int CENTER_PLAYER = 5;
	
	protected static boolean isFaceOff = false;
	protected static boolean isPenaltyShot = false;

	// Left handed?
	public boolean isLeftHanded() { return false; }

	// Initiate
	public void init() {
		setAimOnStick(true);
	}

	// Face off
	public void faceOff() {
		isFaceOff = true;
		moveStick(MIN_STICK_ANGLE, 0);
	}

	// Penalty shot
	public void penaltyShot() {
		isPenaltyShot = true;
	}

	// Player intelligence goes here
	public void step() {
	}

	public String getName() {
		return QAK.names[getIndex()];
	}

	public Position getDefaultPosition() {
		if (offend()) {
			if (defaultOffensivePositionIsOffside()) {
				return ORIGO;
			} else
				return QAK.defaultOffensivePositions[getIndex()];
		} else {
			return QAK.defaultDefensivePositions[getIndex()];
		}

	}

	private boolean defaultOffensivePositionIsOffside() {
		return getPuck().getX() < 900 && QAK.defaultOffensivePositions[getIndex()].getX() > 900;
	}

	public boolean offend() {
		Position puckPosition = new Position(getPuck().getX(), getPuck().getY());
		if (puckPosition.getX() > 0)
			return true;
		else if (puckIsHeldByOwnTeam()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean puckIsHeldByOwnTeam() {
		return getPuck().isHeld() && getPuck().getHolder().getIndex() < 6;
	}

	public boolean defend() {
		return !offend();
	}

	/**
	 * Shoot towards opposing goal, with x-position deviating between +85 and -85 from 0 
	 */
	public void shootToScore() {	
		int yTarget = (((new Random().nextInt(2) * -2 ) + 1 ) * 85) + GOAL_POSITION.getY();
		if (clearShot(yTarget, GOAL_POSITION.getY())){
			shoot(GOAL_POSITION.getX(), yTarget, MAX_SHOT_SPEED);
		} else {
			pass(CENTER_PLAYER);
		}
	}

	/*
	 * see below.
	 */
	public boolean clearShot(int x, int y){
		return clearShot(new Position(x, y));
	}
	/*
	 * Not done yet, for now its optimistic. slightly.
	 */
	protected boolean clearShot(Position target){
		for (int i = 6; i <= 11; i++){
			if (Math.abs(Util.collisionHeading(target, this, MAX_SHOT_SPEED) - Util.collisionHeading(getPlayer(i), this, MAX_SHOT_SPEED)) < SHOT_CAREFULNESS){
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return true if the holder of the puch is closer to goal than *this*. Nullpointer if no puck holder.
	 */
	public boolean puckIsCloserToHomeGoal() {
		if(getPuck().isHeld()){
			return Util.dist2(HOME_GOAL_POSITION, getPuck().getHolder()) > Util.dist2(HOME_GOAL_POSITION, this);
		}else{
			return Util.dist2(HOME_GOAL_POSITION, getPuck()) > Util.dist2(HOME_GOAL_POSITION, this);
		}

	}

	public boolean isBehindOpposingGoal() {
		boolean isBehindOpposingGoal = getX() > GOAL_POSITION.getX();
		return isBehindOpposingGoal;
	}

	public boolean isInOffensiveZone() {
		return getX() > 900;
	}

	public void smartSkate(IObject target, int speed){
		int ANGULAR_WEIGHT = 5; // How important is an error in heading? larger numbers make players slow down more to correct heading.
		int DISTANCE_WEIGHT = 100;  // what does a player consider "close" in cm.   smaller numbers will make players go faster closer.
 		
		if (Util.dangle(Util.datan2(target, this), getHeading()) < 10) { // good enough heading?
			// math to check if intersecting slowly will happen.
			if (Util.dist(target, this) < 100) {   // getting close?
				if (target.getSpeed() > 200){
					if (Util.dangle(getHeading(),target.getHeading()) < 10){
						speed = Math.min(MAX_SPEED, target.getSpeed() + 400);
					}
					else {
						speed = Math.min(MAX_SPEED, target.getSpeed());
					}
				} else {
				skate(target,Math.min(speed, 300));  // slow down!
				}
			} else {
				skate(target,speed); // move along as usual.
			}
		} else {
			double factor = Util.dist(this, target) / DISTANCE_WEIGHT;  // slow down based on distance. or rather, allow more speed if we are far away.
			factor = factor / (Util.dangle(Util.datan2(target, this), getHeading()) * ANGULAR_WEIGHT); // slow down if angle is bad.
			skate(target,Math.min(speed,(int)(speed*factor)));		// skate smarter if someone wasn't smart already.
		}
	}

	public void smartSkate(int x, int y, int speed){
		smartSkate(new Position(x,y),speed);
	}
	
	protected void multiPass(IObject target, IObject origin){
		
	}
	
	public void defaultSkate(int speed){
		if(Util.dist2(getDefaultPosition(), this) < 300){
			turn(getPuck(), MAX_TURN_SPEED);
		}
		smartSkate(getDefaultPosition(), speed);
	}
		

	public void pass(int playerIndex) {
		pass(getPlayer(playerIndex));
	}

	protected void pass(IPlayer player) {
		shoot(Util.collisionHeading(player, getPuck(), PASS_SPEED), PASS_SPEED);
	}

	public void chasePuck() {
		int speed = MAX_SPEED;
		if (Util.dist(this, getPuck()) < MAX_STICK_R) {
			moveStick(MIN_STICK_ANGLE, MIN_STICK_R);
		} else {
			moveStick(MIN_STICK_ANGLE, MAX_STICK_R);
		}
		
		if (getStickR() > Util.dist(this, getPuck())) {
			speed = (int) (getSpeed() * 0.6);
		}


		turn(Util.collisionHeading(getPuck(), this, MAX_SPEED), MAX_TURN_SPEED);
		skate(speed);
	}

	public void passAForward() {
		pass(new Random().nextInt(2) + 3);
	}

	public Position getDefensivePosition() {
		int dx = getPuck().getX() - HOME_GOAL_POSITION.getX();
		int dy = getPuck().getY() - HOME_GOAL_POSITION.getY();
		double factor = Math.hypot(dx, dy) / 600;
		
		if (dx < HOME_GOAL_POSITION.getX()) {
			dx = HOME_GOAL_POSITION.getX();
		}
		
		Position target = new Position((int)(HOME_GOAL_POSITION.getX() + dx/factor), (int)(dy/factor));
		return target;
	}

	public void setIsFaceOff(boolean faceOff) {
		isFaceOff = faceOff;		
	}

	public boolean getIsFaceOff() {
		return isFaceOff;
	}

}
