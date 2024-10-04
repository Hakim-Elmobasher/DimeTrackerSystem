package pkgSportStatsSystem;

public class Stats {
	
	private int statNum;
	private int gameNum;
	private int playerID;
	private int teamNum;
	private int numTD;	//touchdowns per player
	private int passes;	//passes per player
	private int catches;	//catches per player
	private int runningYards;	//running yards per player
	private int penalties;	//penalties per player
	
	public Stats() {
		super();
		this.statNum = 0;
		this.gameNum = 0;
		this.playerID = 0;
		this.teamNum = 0;
		this.numTD = 0;
		this.passes = 0;
		this.catches = 0;
		this.runningYards = 0;
		this.penalties = 0;
	}
	
	public Stats(int s, int g, int p, int t, int n, int p2, int c, int r, int p3) {
		super();
		this.statNum = s;
		this.gameNum = g;
		this.playerID = p;
		this.teamNum = t;
		this.numTD = n;
		this.passes = p2;
		this.catches = c;
		this.runningYards = r;
		this.penalties = p3;
	}
	
	public int getStatNum() {
		return statNum;
	}
	public void setStatNum(int statNum) {
		this.statNum = statNum;
	}
	public int getGameNum() {
		return gameNum;
	}
	public void setGameNum(int gameNum) {
		this.gameNum = gameNum;
	}
	public int getNumTD() {
		return numTD;
	}
	public void setNumTD(int numTD) {
		this.numTD = numTD;
	}
	public int getPasses() {
		return passes;
	}
	public void setPasses(int passes) {
		this.passes = passes;
	}
	public int getCatches() {
		return catches;
	}
	public void setCatches(int catches) {
		this.catches = catches;
	}
	public int getRunningYards() {
		return runningYards;
	}
	public void setRunningYards(int runningYards) {
		this.runningYards = runningYards;
	}
	public int getPenalties() {
		return penalties;
	}
	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

	
	
	
	
}
