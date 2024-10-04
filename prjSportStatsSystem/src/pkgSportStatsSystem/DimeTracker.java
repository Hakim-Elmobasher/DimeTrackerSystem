package pkgSportStatsSystem;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import pkgSportStatsSystem.ASCIIArt.ASCIIArtFont;

public class DimeTracker {
	
	Division divs[];
	Team teams[];
	ArrayList<Player> players;
	ArrayList<Game> games;
	Vector <Stats> stats;
	int coachID;			//class variable to retain info for logged in user
	int gameNum;			//class variable to create game number for transactions
	int statNum;			//class variable to create stat number for transactions
	Scanner reader;
	Hashtable<Integer, String> playerNames;
	Hashtable<Integer, String> teamNames;
	
	//Database objects
	 Connection connection;
	 Statement statement;
	 ResultSet resultSet;

/*******************************CONSTRUCTOR**************************************/

/************************************CONSTRUCTOR****************************************/
	
	DimeTracker()
	{	divs = new Division[8];
		teams = new Team[32];
		players = new ArrayList<Player>();
		games = new ArrayList<Game>();
		stats = new Vector<Stats>();
		reader = new Scanner(System.in);
		playerNames = new Hashtable<>();
		teamNames = new Hashtable<>();
		coachID = 0;	//used for logged in user
		statNum = 0;	//used for next stat entry
		gameNum = 0;	//used for next game entry
		
		connection = null;
		statement = null;
		resultSet = null;
		
	}
	
/*******************************END OF CONSTRUCTOR**************************************/

/************************************WELCOME****************************************/

	void welcome() throws Exception
	{	displayHeader("Dime Tracker", 18);
		
	}
/*******************************END OF WELCOME**************************************/

/**********************************LOAD DATA
 * @throws SQLException ***************************************/

	void loadData() throws IOException, SQLException
	{
		loadPlayersDB();
		loadTeamsDB();
		loadDivisionsDB();
		loadGamesDB();
		loadStatsDB();
	}
/*******************************END OF LOAD DATA**********************************/

/**********************************LOGIN**************************************/
	void login() throws Exception

	{	int enterID = 0;
		boolean isValidLogin = false, isPlayer =false;
		int numattempts = 0;
		
		displayHeader("Login Screen", 12);
		System.out.print("Enter your user ID: ");
		enterID = reader.nextInt();
	
		
		do {
		
			for (int i = 0; i < players.size(); i++)
			{	if (enterID == players.get(i).getPlayerID())
				{	System.out.println("Player found, " +  players.get(i).getPlayerName());
					isPlayer= true;
				}
			}
			
			if (isPlayer)
			{	
				for (int i = 0; i < teams.length; i++)
				{	if (enterID == teams[i].getCoach())
					{	System.out.println(teams[i].getCoach());
					
						System.out.println("Coach found!");
						isValidLogin= true;
						coachID = teams[i].getCoach();
					}
				}	
			}
			numattempts++;
		} while (numattempts < 3 && !isValidLogin);
		
		
		if (!isValidLogin)
			{	System.out.print("Error, Invalid Login, Program Terminated!");
				System.exit(0);
			}
		else
			displayMenu();
		
	}

/*******************************END OF LOGIN**************************************/

/*******************************DISPLAY MENU**************************************/
	void displayMenu() throws Exception
	{	int menuchoice = 0;
		char moredata = 'N';
		
		do
		{
			System.out.println("1. Enter Game Data");
			System.out.println("2. Enter Game Stats by Player");
			System.out.println("3. View Game Stats by Player");
			System.out.println("4. Process Averages by Player");
			System.out.println("5. Exit");
			
			menuchoice = reader.nextInt();
			
			switch (menuchoice)
			{
				case 1:	
				{	//call enter game data - by player
					processGameData();
					break;
				}
				case 2:	
				{	//view game data by player
					processPlayerStats();
					break;
				}
				case 3:	
				{	//view game data by player
					viewStats();
					break;
				}
				case 4:	
				{	//calculate averages by player
					calcAverages();
					break;
				}
				case 5:	
				{	writeGames();
					writeStats();
					System.out.println("Thank you for using, Dime Tracker System, Program Terminated!");
					System.exit(0);
				}
				default:	
				{	System.out.println("Invalid Choice!"); }
	
			}
			System.out.print("Would you like to process more stats? (Y/N): "); 
			moredata = reader.next().charAt(0);
		}while (moredata == 'Y' || moredata == 'y');
		
		//HARD EXIT THE PROGRAM
		writeStats();
		System.out.println("Thank you for using, Dime Tracker System, Program Terminated!");
		System.exit(0);
	}

/*******************************END OF DISPLAY MENU**************************************/

/*******************************GET SYSTEM DATE**************************************/
	String getSystemDate()
	{
		Calendar calendar = Calendar.getInstance();
		String timeStamp= new SimpleDateFormat("MM-dd-yyyy").format(calendar.getTime());
		return timeStamp;

	}
	
/***************************END OF GET SYSTEM DATE**************************************/

/*******************************LOAD PLAYERS**************************************/
	//load players from text file - #include appropriate header files
	void loadPlayers() throws IOException
			{	//create a File for reading from your data file – assuming the file is with the .src folder
				BufferedReader br = new BufferedReader(new FileReader("players.dat"));
				
				//create the variables for each field in the file
				int pid = 0, jersey = 0,  team = 0;
				String pName = "", position = "";
			
				//Create a string to read each line and a tokenizer to separate at the field at the comma
				String eachLine = "";
				StringTokenizer st; 
				eachLine = br.readLine(); //read the first line

				while (eachLine != null) //tests for the eof
				 { st = new StringTokenizer(eachLine, ","); //read each line
				 	while (st.hasMoreTokens()) //read each field
				 	{ //remember the order of the text file
				 		
				 		
				 		 pid= Integer.parseInt(st.nextToken());
				 		 pName = st.nextToken();
				 		 jersey = Integer.parseInt(st.nextToken());
				 		 position = st.nextToken();
				 		 team = Integer.parseInt(st.nextToken());
				 		 //Add to the players ArrayList
				 		  players.add(new Player(pid, pName, jersey, position, team));
				 		//Add to the Player Hashtable
				 		  playerNames.put(pid, pName);
				 		
				 	}//end of reading one line

			 		 eachLine = br.readLine(); //read the next line
				 }//end of reading the file
				
				br.close(); //close the file
				
				System.out.println("Players Loaded!");

			}	
/************************END OF LOAD PLAYERS**************************************/

/*******************************LOAD GAMES**************************************/
	//load games from text file - #include appropriate header files
	void loadGames() throws IOException
	{	//create a File for reading from your data file – assuming the file is with the .src folder
		BufferedReader br = new BufferedReader(new FileReader("games.dat"));
		
		//create the variables for each field in the file
		int gameNo = 0, team1 = 0,  team2 = 0, score1 = 0, score2 = 0;
		String gameDate = "", position = "";
		
		//Create a string to read each line and a tokenizer to separate at the field at the comma
		String eachLine = "";
		StringTokenizer st; 
		eachLine = br.readLine(); //read the first line

		while (eachLine != null) //tests for the eof
		 { st = new StringTokenizer(eachLine, ","); //read each line
		 	while (st.hasMoreTokens()) //read each field
		 	{ //remember the order of the text file
		 		
		 		gameNo= Integer.parseInt(st.nextToken());
		 		gameDate = st.nextToken();
		 		team1 = Integer.parseInt(st.nextToken());
		 		team2 = Integer.parseInt(st.nextToken());
		 		score1 = Integer.parseInt(st.nextToken());
		 		score2 = Integer.parseInt(st.nextToken());
		 		 //Add to the games ArrayList
		 		games.add(new Game(gameNum, gameDate, team1,team2, score1, score2));
		 			
		 		
		 	}//end of reading one line

	 		 eachLine = br.readLine(); //read the next line
		 }//end of reading the file
		gameNum = ++gameNo; //prime the gameNum for the next data entry
		br.close(); //close the file
		
		System.out.println("Games Loaded!");

	}

/****************************END OF LOAD GAMES**************************************/

/*******************************LOAD DIVISIONS**************************************/
	//load divisions from text file - #include appropriate header files
	void loadDivisions() throws IOException
	{	//create a File for reading from your data file – assuming the file is with the .src folder
		BufferedReader br = new BufferedReader(new FileReader("divisions.dat"));
		
		//create the variables for each field in the file
		int dNum = 0;
		String dName = "";
		int di= 0;
	
		
		//Create a string to read each line and a tokenizer to separate at the field at the comma
		String eachLine = "";
		StringTokenizer st; 
		eachLine = br.readLine(); //read the first line

		while (eachLine != null) //tests for the eof
		 { st = new StringTokenizer(eachLine, ","); //read each line
		 	while (st.hasMoreTokens()) //read each field
		 	{ //remember the order of the text file
		 		 dNum= Integer.parseInt(st.nextToken());
		 		 dName = st.nextToken();
		 		 //Add to the Divisions Array
		 		 divs[di] = new Division(dNum, dName);
		 		
		 	}//end of reading one line
		 	
		 	 di++;
	 		 eachLine = br.readLine(); //read the next line
		 }//end of reading the file
		
		br.close(); //close the file
		
		System.out.println("Divisions Loaded");

	}	

/************************* END OF LOAD DIVISIONS**************************************/

/*******************************LOAD TEAMS**************************************/
	//load teams from text file - #include appropriate header files
	void loadTeams() throws IOException
	{	//create a File for reading from your data file – assuming the file is with the .src folder
		BufferedReader br = new BufferedReader(new FileReader("teams.dat"));
		
		//create the variables for each field in the file
		int tNum = 0;
		String tName ="";
		int numPlayers= 0;
		String city= "";
		String state= "";
		int coach= 0;
		int owner= 0;
		int divNum= 0;
		
		int ti = 0;
	
		//Create a string to read each line and a tokenizer to separate at the field at the comma
		String eachLine = "";
		StringTokenizer st; 
		eachLine = br.readLine(); //read the first line

		while (eachLine != null) //tests for the eof
		 { st = new StringTokenizer(eachLine, ","); //read each line
		 	while (st.hasMoreTokens()) //read each field
		 	{ //remember the order of the text file
		 		
		 		tNum= Integer.parseInt(st.nextToken());
		 		 tName = st.nextToken();
		 		 numPlayers= Integer.parseInt(st.nextToken());
		 		 city = st.nextToken();
		 		 state = st.nextToken();
		 		 coach = Integer.parseInt(st.nextToken());
		 		 owner = Integer.parseInt(st.nextToken());
		 		 divNum= Integer.parseInt(st.nextToken());
		 		 //Add to the teams Array
		 		 
		 		teams[ti] = new Team(tNum, tName, numPlayers, city, state, coach, owner, divNum);
		 		//Add to the Team Hashtable
		 		  teamNames.put(tNum, tName);
		 		 
		 	}//end of reading one line
		 	ti++;
	 		 eachLine = br.readLine(); //read the next line
		 }//end of reading the file
		
		br.close(); //close the file
		
		System.out.println("Teams Loaded");

	}	

/**************************END OF LOAD TEAMS**************************************/

/*******************************LOAD STATS**************************************/	
	//load stats from text file - #include appropriate header files	
	void loadStats() throws IOException
		{	//create a File for reading from your data file – assuming the file is with the .src folder
			BufferedReader br = new BufferedReader(new FileReader("stats.dat"));
			
			//create the variables for each field in the file
			 int statNo  = 0;
			 int gameNum =0 ;
			 int playerID =0 ;
			 int teamNum =0 ;
			 int numTD =0 ;	
			 int passes =0 ;	
			 int catches =0 ;	
			 int runningYards =0 ;	
			 int penalties =0 ;	
		
			
			//Create a string to read each line and a tokenizer to separate at the field at the comma
			String eachLine = "";
			StringTokenizer st; 
			eachLine = br.readLine(); //read the first line
		
			while (eachLine != null) //tests for the eof
			 { st = new StringTokenizer(eachLine, ","); //read each line
			 	while (st.hasMoreTokens()) //read each field
			 	{ //remember the order of the text file
			 		//1001,100,100000,1000,0,2,2,1,21,0
			 		statNo= Integer.parseInt(st.nextToken());
			 		gameNum= Integer.parseInt(st.nextToken());
			 		playerID= Integer.parseInt(st.nextToken());
			 		teamNum= Integer.parseInt(st.nextToken());
			 		numTD= Integer.parseInt(st.nextToken());
			 		passes= Integer.parseInt(st.nextToken());
			 		catches= Integer.parseInt(st.nextToken());
			 		runningYards= Integer.parseInt(st.nextToken());
			 		penalties= Integer.parseInt(st.nextToken());
			 		 
			 		//Add to the Stats Vector
			 		 stats.add(new Stats(statNum, gameNum,playerID,teamNum,numTD,passes,catches,runningYards,penalties));
			 		
			 	}//end of reading one line
			 	
		 		 eachLine = br.readLine(); //read the next line
			 }//end of reading the file
			statNum = ++statNo; //prime the statNum for the next data entry

			br.close(); //close the file
			
			System.out.println("Stats Loaded");

		}	

/**************************END OF LOAD STATS**************************************/

/**************************PROCESS GAME DATA**************************************/

void processGameData() throws Exception
	{ 	
			 String gameDate = getSystemDate();
			 int teamHome; //Home Team
			 int teamVisitor; //Visiting Team
			 int scoreHome; //Home Team Score
			 int scoreVisitor; //Visiting Team Score
			 
		    displayHeader("Process Game Data",12);
		    //display Game Option
   
		    System.out.print("Enter Home Team Number: ");
		    teamHome = reader.nextInt();
		    
		    System.out.print("Enter Home Team Score: ");
		    scoreHome = reader.nextInt();
		    
		    
		    System.out.print("Enter Visiting Team Number: ");
		    teamVisitor= reader.nextInt();
		
		    System.out.print("Enter Visiting Team Score: ");
		    scoreVisitor= reader.nextInt();
		    
		  games.add(new Game(gameNum, gameDate, teamHome, teamVisitor, scoreHome, scoreVisitor));
		  gameNum++;
	}
/**********************END OF PROCESS GAME DATA**************************************/

/**************************PROCESS PLAYER STATS**************************************/

void processPlayerStats() throws Exception
	{ 	
			 int gameNo =0 ;
			 int playerID =0 ;
			 int teamNum =0 ;
			 int numTD =0 ;	//touchdowns per player
			 int passes =0 ;	//passes per player
			 int catches =0 ;	//catches per player
			 int runningYards =0 ;	//running yards per player
			 int penalties =0 ;	//penalties per player
			 String home = "", visitor = "";
			 String tab = "\t", tabs = "\t\t";
			 String displaytabs = tab;
			 
		    displayHeader("Process Player Stats",12);
		    //display Game Option
		   
		    System.out.println("Game Number\tHome Team\t\tVisting Team");
			for (int i = 0; i < games.size(); i++)
			{	System.out.print(games.get(i).getGameNum() + "\t\t");
				home = teamNames.get(games.get(i).getTeamHome());
				visitor = teamNames.get(games.get(i).getTeamVisitor());
				if (home.length() <= 13) displaytabs = tabs;
				System.out.println(home + displaytabs + visitor); 
			}	
		    
		    System.out.print("Choose Game Number: ");
		    gameNo= reader.nextInt();
		    
		    System.out.print("Enter Player Number: ");
		    playerID= reader.nextInt();
		    
		    System.out.print("Enter Number of Touch Downs: ");
		    numTD=reader.nextInt();
		    System.out.print("Enter Number of Passes: ");
		    passes=reader.nextInt();
		    System.out.print("Enter Number of Catches: ");
		    catches=reader.nextInt();
		    System.out.print("Enter Number of Running Yards: ");
		    runningYards=reader.nextInt();
		    System.out.print("Enter Number of Penalties: ");
		    penalties=reader.nextInt();
		    
		  stats.add(new Stats(statNum, gameNo, playerID, teamNum, numTD, passes, catches,  runningYards, penalties));
		  statNum++;
	}
/**********************END OF PROCESS STATS**************************************/
	
/********************************VIEW STATS**************************************/
	
	void viewStats() throws Exception
	{ 	int playerID = 0, statcount = 0, gameNo = 0, ht = 0, vt = 0;
		String playerName = "", hometeam = "", visitteam = "";
	
		displayHeader("View Stats",12);
		 //Get player ID
		 System.out.println("Enter Player Number: ");
		 playerID= reader.nextInt();
	
		 //Get Game Number
		 System.out.println("Enter Game Number: ");
		 gameNo= reader.nextInt();
		 
		 //Get Team Names to Display Team Header
			for (int i = 0; i < stats.size(); i++)
			{
				if(gameNo ==  stats.get(i).getGameNum())
				{	for(int  j = 0; j < games.size(); j++)
					{	ht = games.get(j).getTeamHome();
						vt = games.get(j).getScoreVisitor();
						hometeam = teamNames.get(ht);
						visitteam = teamNames.get(vt);
						System.out.println("Displaying stats for game # " + gameNo + hometeam + " vs " + visitteam);
					}
				}
				else
				{
					 System.out.println("Error, incorrect game number!");
					
				}
			}	
		     
	    //display Game Option
		for (int i = 0; i < stats.size(); i++)
		{
			
			if(playerID ==  stats.get(i).getPlayerID()&& gameNo == stats.get(i).getGameNum())
			{	playerName = playerNames.get(playerID);
			
				System.out.print("Touchdowns: "+ stats.get(i).getNumTD() + "\t");
				System.out.print("Passes: "+ stats.get(i).getPasses()+ "\t");
				System.out.print("Catches: "+ stats.get(i).getCatches()+ "\t");
				System.out.print("Yards: "+ stats.get(i).getRunningYards()+ "\t");
				System.out.println("Penalties: "+ stats.get(i).getPenalties());
				statcount++;
			}
		}	
	    if (statcount > 0)
	    	System.out.println("Total Stats for: " + playerName);
	    else
	    	System.out.println("Player #: " + playerID + " has no stats!");
	}
/**********************END OF VIEW STATS**************************************/

/********************************CALCULATE AVERAGES**************************************/
	void calcAverages() throws Exception
	{ 	int playerID = 0, statcount = 0;
		int tdtotal = 0, passtotal = 0, catchtotal = 0, yardstotal = 0, pentotal = 0;
		float tdavg = 0, passavg = 0, catchavg = 0, yardsavg = 0, penavg = 0;
		String playerName = "";
	
		 displayHeader("Calculate Averages",12);
		 //Get player ID
		 System.out.println("Enter Player Number: ");
		    playerID= reader.nextInt();
	 
	    //display Game Option
		for (int i = 0; i < stats.size(); i++)
		{	if(playerID ==  stats.get(i).getPlayerID())
			{	playerName = playerNames.get(playerID);
			
				tdtotal += stats.get(i).getNumTD();
				passtotal +=  stats.get(i).getPasses();
				catchtotal += stats.get(i).getCatches();
				yardstotal += stats.get(i).getRunningYards();
				pentotal += stats.get(i).getPenalties();
				statcount++;		
			}
		}
		
		//PROCESS AVERAGES - UPDATE FORMAT TO FLOAR
		if(tdtotal > 0) tdavg = tdtotal /statcount;
		if(passtotal > 0)passavg = passtotal /statcount;
		if(catchtotal > 0)catchavg = catchtotal/statcount;
		if(yardstotal > 0)yardsavg = yardstotal /statcount;
		if(pentotal > 0)penavg = pentotal /statcount;
		
		
	    if (statcount == 0)
	    	System.out.println("Player #: " + playerID + " has no stats!");
	    else
	    {	System.out.println("Averages for: " + playerName);
			System.out.print("Touchdown Average: "+ tdavg + "\t");
			System.out.print("Passes Average:"+ passavg + "\t");
			System.out.print("Catches Average: "+ catchavg + "\t");
			System.out.print("Yards Average: "+ yardsavg + "\t");
			System.out.println("Penalties Average: "+ penavg);
	    }
	    	
	}
/**********************END OF CALCULATE AVERAGES**************************************/

//*****************************WRITE STATS**************************************/
//These are the transactions for the system
	void writeGames() throws Exception
	{ 		
		//create a File for overwriting – assuming the file is with the .src folder
		FileWriter fw = new FileWriter("games.dat");
	
		for (int i =0; i < games.size(); i++) //read each field
		{ //remember the order of the text file
		 		
			fw.write(games.get(i).getGameNum() +",");
			fw.write(games.get(i).getGameDate() +",");
			fw.write(games.get(i).getTeamHome() +",");
			fw.write(games.get(i).getTeamVisitor() +",");
			fw.write(games.get(i).getScoreHome() +",");
			fw.write(games.get(i).getScoreVisitor()+"\n");	
	
		}//end of writing the file

		
		fw.close(); //close the file
		
		System.out.println("Games File Written!");

}
	/**********************END OF WRITE STATS**************************************/

	//*****************************WRITE STATS**************************************/
	//These are the transactions for the system
		void writeStats() throws Exception
		{ 		
			//create a File for overwriting – assuming the file is with the .src folder
			FileWriter fw = new FileWriter("stats.dat");
		
			for (int i =0; i < stats.size(); i++) //read each field
			{ //remember the order of the text file
				
				
				fw.write(stats.get(i).getStatNum() +",");
				fw.write(stats.get(i).getGameNum()+",");
				fw.write(stats.get(i).getPlayerID() +",");
				fw.write(stats.get(i).getTeamNum() +",");
				fw.write(stats.get(i).getNumTD() +",");
				fw.write(stats.get(i).getPasses() +",");
				fw.write(stats.get(i).getCatches() +",");
				fw.write(stats.get(i).getRunningYards() +",");
				fw.write(stats.get(i).getPenalties() +"\n");	
			 		
			}//end of writing the file

			
			fw.close(); //close the file
			
			System.out.println("Stats File Written!");

	}
		/**********************END OF WRITE STATS**************************************/

	
	
/*****************************CHANGE COLORS**************************************/
	
	void changeColors(String bgcolor, String fgcolor)
	{	Colors.resetColors();
		Colors.setBackground(bgcolor);
		Colors.setForeground(fgcolor);
		
	}
/**********************END OF CHANGE COLORS**************************************/

/******************************DISPLAY HEADER**************************************/

	void displayHeader(String header, int size) throws Exception
	{  
			changeColors(Colors.WHITE, Colors.BLUE);	         
			System.out.println();
	       if (size == 32)
	    	   {	ASCIIArt.printTextArt(header, ASCIIArt.ART_SIZE_HUGE, ASCIIArtFont.ART_FONT_MONO,"*");}
	       else if (size == 24)
    	   {	ASCIIArt.printTextArt(header, ASCIIArt.ART_SIZE_LARGE, ASCIIArtFont.ART_FONT_MONO,"*");}
	       else if (size == 18)
    	   {	ASCIIArt.printTextArt(header, ASCIIArt.ART_SIZE_MEDIUM, ASCIIArtFont.ART_FONT_MONO,"*");}
	       else 
    	   {	ASCIIArt.printTextArt(header, ASCIIArt.ART_SIZE_SMALL, ASCIIArtFont.ART_FONT_MONO,".");}
   
	        System.out.println();		
	}	
/**********************END OF DISPLAY HEADER**************************************/
	
/******************************LOAD PLAYERS DB**************************************/

	//load Players
			void loadPlayersDB() throws IOException, SQLException
			{
				//create the variables for each field in the file
				int pid = 0, jersey = 0,  team = 0;
				String pName = "", position = "";
			
				//create the variables for each field in the database
				String r = "", n = "", a = "";
				int i = 0, totalrows = 0;
				//Get the total rows in the table to loop through the result set
				 resultSet = statement.executeQuery("SELECT * FROM PLAYERS"); 
				 resultSet.last();
				 totalrows = resultSet.getRow();	//get the first row
				while (i < totalrows) //tests for the eof
				{   totalrows = resultSet.getRow();
					pid = resultSet.getInt(1);
					pName = resultSet.getString(2);
				 	jersey = resultSet.getInt(3);
				 	position = resultSet.getString(4);
				 	team = resultSet.getInt(5);
				 	
				 
				 //Add to the players ArrayList
		 		  players.add(new Player(pid, pName, jersey, position, team));
		 			//Add to the Player Hashtable
		 		  playerNames.put(pid, pName);
				 	i++;	//increment row number
				}//end of loading to  ArrayList
				
					System.out.println("Players Loaded!");

			}

/******************************END OF LOAD PLAYERS DB**************************************/

	/*******************************LOAD DIVISIONS
	 * @throws SQLException **************************************/
			//load divisions from text file - #include appropriate header files
			void loadDivisionsDB() throws IOException, SQLException
			{					
				//create the variables for each field in the database
				int dNum = 0;
				String dName = "";
				int di = 0, totalrows = 0;
				
				//Get the total rows in the table to loop through the result set
				 resultSet = statement.executeQuery("SELECT * FROM DIVISIONS"); 
				 resultSet.last();
				 totalrows = resultSet.getRow();	//get the first row
				while (di < totalrows) //tests for the eof
				{   totalrows = resultSet.getRow();
					dNum = resultSet.getInt(1);
				 	dName= resultSet.getString(2);
				 	 //Add to the Divisions Array
			 		 divs[di] = new Division(dNum, dName);
	
			 		 di++; //increment row number
				}//end of loading to  Array
				
				System.out.println("Divisions Loaded");

			}	

		/************************* END OF LOAD DIVISIONS**************************************/
			/*******************************LOAD GAMES
			 * @throws SQLException **************************************/
			//load games from text file - #include appropriate header files
			void loadGamesDB() throws IOException, SQLException
			{	
				//create the variables for each field in the file
				int gameNo = 0, team1 = 0,  team2 = 0, score1 = 0, score2 = 0, totalrows=0;
				String gameDate = "", position = "";
				//Get the total rows in the table to loop through the result set
				 resultSet = statement.executeQuery("SELECT * FROM GAMES"); 
				 resultSet.last();
				 totalrows = resultSet.getRow();	//get the first row
				while (resultSet.next()) //tests for the eof) //tests for the eof
				{	totalrows = resultSet.getRow();
				 		gameNo= resultSet.getInt(1);
				 		gameDate = resultSet.getString(2);
				 		team1 = resultSet.getInt(3);
				 		team2 = resultSet.getInt(4);
				 		score1 = resultSet.getInt(5);
				 		score2 = resultSet.getInt(6);
				 		
				 	 //Add to the games ArrayList
			 		games.add(new Game(gameNum, gameDate, team1,team2, score1, score2));		
				}//end of loading to  ArrayList
				
				gameNum = ++gameNo; //prime the gameNum for the next data entry
				System.out.println("Games Loaded!");

			}

		/****************************END OF LOAD GAMES**************************************/
		/*******************************LOAD TEAMS
		 * @throws SQLException **************************************/
			//load teams from text file - #include appropriate header files
			void loadTeamsDB() throws IOException, SQLException
			{	//create a File for reading from your data file – assuming the file is with the .src folder
				BufferedReader br = new BufferedReader(new FileReader("teams.dat"));
				
				//create the variables for each field in the file
				int tNum = 0;
				String tName ="";
				int numPlayers= 0;
				String city= "";
				String state= "";
				int coach= 0;
				int owner= 0;
				int divNum= 0;
				int totalrows = 0;
				int ti = 0;
			
								
				//Get the total rows in the table to loop through the result set
				 resultSet = statement.executeQuery("SELECT * FROM TEAMS"); 
				 resultSet.last();
				 totalrows = resultSet.getRow();	//get the first row
				while (ti < totalrows) //tests for the eof
				{   totalrows = resultSet.getRow();
					tNum = resultSet.getInt("teamNum");
				 	tName= resultSet.getString("teamName");
				 	 numPlayers=  resultSet.getInt("numPlayers");
			 		 city = resultSet.getString("city");
			 		 state = resultSet.getString("state");
			 		 coach = resultSet.getInt("coach");
			 		 owner = resultSet.getInt("owner");
			 		 divNum=resultSet.getInt("divNum");
	
				 	 //Add to the Teams Array
				 	teams[ti] = new Team(tNum, tName, numPlayers, city, state, coach, owner, divNum);
			 		//Add to the Team Hashtable
			 		  teamNames.put(tNum, tName);
			 		 
			 		 ti++; //increment row number
				}//end of loading to  Array
				
				System.out.println("Teams Loaded");

			}	

		/**************************END OF LOAD TEAMS**************************************/

		/*******************************LOAD STATS
		 * @throws SQLException **************************************/	
			//load stats from text file - #include appropriate header files	
			void loadStatsDB() throws IOException, SQLException
				{	
					//create the variables for each field in the file
					 int statNo  = 0;
					 int gameNum =0 ;
					 int playerID =0 ;
					 int teamNum =0 ;
					 int numTD =0 ;	
					 int passes =0 ;	
					 int catches =0 ;	
					 int runningYards =0 ;	
					 int penalties =0 ;	
					 int totalrows = 0;

						//Get the total rows in the table to loop through the result set
					 resultSet = statement.executeQuery("SELECT * FROM STATS"); 
					 resultSet.last();
					 totalrows = resultSet.getRow();	//get the first row
	
					while (resultSet.next()) //tests for the eof
					{   totalrows = resultSet.getRow();
					
				 		statNo= resultSet.getInt(1);
				 		gameNum= resultSet.getInt(2);
				 		playerID= resultSet.getInt(3);
				 		teamNum= resultSet.getInt(4);
				 		numTD= resultSet.getInt(5);
				 		passes= resultSet.getInt(6);
				 		catches= resultSet.getInt(7);
				 		runningYards= resultSet.getInt(8);
				 		penalties= resultSet.getInt(9);
				 		 
				 		//Add to the Stats Vector
				 		 stats.add(new Stats(statNum, gameNum,playerID,teamNum,numTD,passes,catches,runningYards,penalties));
				 		
					}//end of loading to  Vector
					
					statNum = ++statNo; //prime the statNum for the next data entry
					System.out.println("Stats Loaded");

				}	

		/**************************END OF LOAD STATS**************************************/

			/**********************************************************UPDATE GAMES
			 * @throws SQLException *****************************************************/
			void updateGames() throws SQLException {
				int results = 0;
				//NOTE:  ONLY UPDATE NEW RECORDS
				 
				 //update Game table - strings must be quoted
				 for (int i = 0; i < games.size();i++)
				 {
					results = statement.executeUpdate("INSERT INTO Games VALUES ("
				 				+  games.get(i).getGameNum() 			+ ", "
				 				+ "'"	+ games.get(i).getGameDate()   	+"', " 	
				 				+  games.get(i).getTeamHome()			+ ", "
				 				+  games.get(i).getTeamVisitor()		+ ", "
				 				+  games.get(i).getScoreHome()			+ ", "
				 				+  games.get(i).getScoreVisitor()		+ ")");	
				 }
						 System.out.println("Games updated successfully." );
	

			 }

			/*************************************END OF UPDATE GAMES********************************/
						
			/**********************************************************UPDATE GAMES
			 * @throws SQLException *****************************************************/
			void updateStats() throws SQLException {
				int results = 0;
				//NOTE:  ONLY UPDATE NEW RECORDS
				//create the variables for each field in the file
				 int statNo  = 0;
				 int gameNum =0 ;
				 int playerID =0 ;
				 int teamNum =0 ;
				 int numTD =0 ;	
				 int passes =0 ;	
				 int catches =0 ;	
				 int runningYards =0 ;	
				 int penalties =0 ;	
				 int totalrows = 0;

				 //update Game table - strings must be quoted
				 for (int i = 0; i < stats.size();i++)
				 {
					results = statement.executeUpdate("INSERT INTO Stats VALUES ("
				 				+  stats.get(i).getStatNum()			+ ", "
				 				+  stats.get(i).getGameNum()  			+ ", " 	
				 				+  stats.get(i).getPlayerID() 			+ ", " 	
				 				+  stats.get(i).getTeamNum() 			+ ", " 	
				 				+  stats.get(i).getNumTD()	 			+ ", " 
				 				+  stats.get(i).getPasses()	 			+ ", " 
				 				+  stats.get(i).getCatches()	 		+ ", " 
				 				+  stats.get(i).getRunningYards()		+ ", " 
				 				+  stats.get(i).getPenalties()			+ ")");	
				 }
						 System.out.println("Stats updated successfully." );
	

			 }

			/*************************************END OF UPDATE STATS********************************/
						
			
			
			
			
			
/***********************************************DATABASE CONNECTION*************************************************/
void connectDB() throws ClassNotFoundException, SQLException
{		
// Database variables
			
	 int totalrows = 0;
		
	// Step 1: Loading or registering JDBC driver class 
	 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver"); 
			 
	 // Step 2: Opening database connection
	 String msAccDB = "DimeTracker.accdb";
	 String dbURL = "jdbc:ucanaccess://" + msAccDB; 
	 // Step 3: Create and get connection using DriverManager class
	 connection = DriverManager.getConnection(dbURL); 
	 // Step 4: Creating JDBC Statement 
	 // It is scrollable so we can use next() and last() & It is updatable so we can enter new records
	 statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 ResultSet.CONCUR_UPDATABLE);
	 
	 System.out.println("Database Connected!");
}
			
/****************************************END OF DATABASE CONNECTION*************************************************/
			

}



