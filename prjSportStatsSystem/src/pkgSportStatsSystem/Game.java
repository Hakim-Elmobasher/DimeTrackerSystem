package pkgSportStatsSystem;

public class Game {
	
	private int gameNum;
	private String gameDate;
	private int teamHome; //Home Team
	private int teamVisitor; //Visiting Team
	private int scoreHome; //Home Team Score
	private int scoreVisitor; //Visiting Team Score
	
	public Game() {
		super();
		this.gameNum = 0;
		this.gameDate = "";
		this.teamHome = 0;
		this.teamVisitor = 0;
		this.scoreHome = 0;
		this.scoreVisitor = 0;
	}
	

	public Game(int gameNum, String gameDate, int teamHome, int teamVisitor, int scoreHome, int scoreVisitor) {
		super();
		this.gameNum = gameNum;
		this.gameDate = gameDate;
		this.teamHome = teamHome;
		this.teamVisitor = teamVisitor;
		this.scoreHome = scoreHome;
		this.scoreVisitor = scoreVisitor;
	}



	public int getGameNum() {
		return gameNum;
	}
	public void setGameNum(int gameNum) {
		this.gameNum = gameNum;
	}
	public String getGameDate() {
		return gameDate;
	}
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}



	public int getTeamHome() {
		return teamHome;
	}



	public void setTeamHome(int teamHome) {
		this.teamHome = teamHome;
	}



	public int getTeamVisitor() {
		return teamVisitor;
	}



	public void setTeamVisitor(int teamVisitor) {
		this.teamVisitor = teamVisitor;
	}



	public int getScoreHome() {
		return scoreHome;
	}



	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}



	public int getScoreVisitor() {
		return scoreVisitor;
	}



	public void setScoreVisitor(int scoreVisitor) {
		this.scoreVisitor = scoreVisitor;
	}

	
	
	
}
