package pkgSportStatsSystem;

public class Player {
	
	private int playerID;
	private String playerName;
	private int jerseyNum;
	private String position;
	private int teamNum;
	
	public Player() {
		super();
		this.playerID = 0;
		this.playerName = "";
		this.jerseyNum = 0;
		this.position = "";
		this.teamNum = 0;
	}
	
	public Player(int playerID, String playerName, int jerseyNum, String position, int teamNum) {
		super();
		this.playerID = playerID;
		this.playerName = playerName;
		this.jerseyNum = jerseyNum;
		this.position = position;
		this.teamNum = teamNum;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getJerseyNum() {
		return jerseyNum;
	}
	public void setJerseyNum(int jerseyNum) {
		this.jerseyNum = jerseyNum;
	}
	public String getPositionNum() {
		return position;
	}
	public void setPositionNum(String position) {
		this.position = position;
	}
	public int getTeamNum() {
		return teamNum;
	}
	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}
	
	

}
