/***************************************************************
 * System:	Dime Tracker System
 * Author:	K. Moorning
 * Date: 	February 15, 2023
 * Purpose:	This system calculates stats for football players.
 * Version:	v1
 */
/***************************************************************/

package pkgSportStatsSystem;

import java.io.IOException;
import java.sql.SQLException;

public class DimeTrackerSystem {

	public static void main(String[] args)
	{
		DimeTracker	dt = new DimeTracker();
		
		try
		{	dt.connectDB();
			dt.loadData();
			dt.welcome();
			dt.login();
		}
		catch(IOException e)
		{
			System.out.println (e.getMessage());
			System.out.println("Error Processing Files");
		}
		catch(SQLException e)
		{
			System.out.println (e.getMessage());
			System.out.println("Error Connecting to Database");
		}
		catch(Exception e)
		{
			System.out.println (e.getMessage());
			System.out.println("Error Processing Data");
	
		}

	}

}
