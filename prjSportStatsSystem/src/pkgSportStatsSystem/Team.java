package pkgSportStatsSystem;

public class Team {

		private int teamNum;
		private String teamName;
		private int numPlayers;
		private String city;
		private String stateCode;
		private int coach;
		private int owner;
		private int divNum;
		
		public Team() {
			super();
			this.teamNum = 0;
			this.teamName = "";
			this.numPlayers = 0;
			this.city = "";
			this.stateCode = "";
			this.coach = 0;
			this.owner = 0;
			this.divNum = 0;
		}
		
		
		public Team(int teamNum, String teamName, int numPlayers, String city, String stateCode, int coach, int owner,
				int divNum) {
			super();
			this.teamNum = teamNum;
			this.teamName = teamName;
			this.numPlayers = numPlayers;
			this.city = city;
			this.stateCode = stateCode;
			this.coach = coach;
			this.owner = owner;
			this.divNum = divNum;
		}
		public int getTeamNum() {
			return teamNum;
		}
		public void setTeamNum(int teamNum) {
			this.teamNum = teamNum;
		}
		public String getTeamName() {
			return teamName;
		}
		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}
		public int getNumPlayers() {
			return numPlayers;
		}
		public void setNumPlayers(int numPlayers) {
			this.numPlayers = numPlayers;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getStateCode() {
			return stateCode;
		}
		public void setStateCode(String stateCode) {
			this.stateCode = stateCode;
		}
		public int getCoach() {
			return coach;
		}
		public void setCoach(int coach) {
			this.coach = coach;
		}
		public int getOwner() {
			return owner;
		}
		public void setOwner(int owner) {
			this.owner = owner;
		}
		public int getDivNum() {
			return divNum;
		}
		public void setDivNum(int divNum) {
			this.divNum = divNum;
		}
		
		
		
		
		
}
