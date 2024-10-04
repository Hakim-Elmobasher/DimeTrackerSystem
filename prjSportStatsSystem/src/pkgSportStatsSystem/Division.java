package pkgSportStatsSystem;

public class Division {

		private int divNum;
		private String divName;
		
		public Division() {
			super();
			this.divNum = 0;
			this.divName = "";
		}
		
		public Division(int divNum, String divName) {
			super();
			this.divNum = divNum;
			this.divName = divName;
		}
		
		public int getDivNum() {
			return divNum;
		}
		public void setDivNum(int divNum) {
			this.divNum = divNum;
		}
		public String getDivName() {
			return divName;
		}
		public void setDivName(String divName) {
			this.divName = divName;
		}
		
		
}
