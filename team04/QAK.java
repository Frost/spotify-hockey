package team04;

import java.awt.Color;
import hockey.api.GoalKeeper;
import hockey.api.Player;
import hockey.api.ITeam;
import hockey.api.Position;

public class QAK implements ITeam {
	// Team Short Name.  Max 4 characters.
	public String getShortName() { return "QAK"; }

	// Team Name
	public String getTeamName() { return "Crash & Burn"; }

	// Team color; body color
	public Color getTeamColor() { return Color.BLACK; }

	// Team color; helmet color.
	public Color getSecondaryTeamColor() { return Color.ORANGE; }

	// The team's LUCKY NUMBER!!
	public int getLuckyNumber() { return 1995; }

	protected static String[] names = {
		"",
		"Crash Override",
		"Acid Burn",
		"The Phantom Phreak",
		"Cereal Killer",
		"Lord Nikon"
	}; 

	protected static Position[] defaultOffensivePositions = {
		new Position(-2550, 0),
		new Position(-500, -700),
		new Position(-500, 700),
		new Position(1200, 700),
		new Position(1200, -700),
		new Position(1000, 0)
	};
	
	protected static Position[] defaultDefensivePositions = {
		new Position(-2550, 0),
		new Position(-2000, -500),
		new Position(-2000, 500),
		new Position(-900, 700),
		new Position(-900, -700),
		new Position(-800, 0)
	};

	
	// Get the goal keeper of the team.
	public GoalKeeper getGoalKeeper() { return new Goalie(); }

	// Get the other five players of the team.
	public Player getPlayer(int index) {
		switch (index) {
		case 1: return new Defender(); // Left defender
		case 2: return new Defender(); // Right defender
		case 3: return new Forward(); // Left forward
		case 4: return new Forward(); // Right forward
		case 5: return new Center(); // Center
		}
		return null;
	}
}
