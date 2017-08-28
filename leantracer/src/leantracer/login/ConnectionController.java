package leantracer.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import leantracer.masterdata.MasterDataController;
import leantracer.stack.StackController;
import leantracer.tasklist.TaskListMain;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Controls the view and the model of the module login. Initializes the data model and creates its data access objects.
 * Enables the user to log on to the database. Creates a database connection and stores its for further use in the data
 * model. Starts the subsequent default user GUI or, if no default user GUI is set, it allows the user to choose the next
 * screen.
 */
public class ConnectionController {
	
	private Connection connection;
	private ConnectionModel connectionModel;
	
	public ConnectionController() {
		
		Logger logger = LogManager.getLogger();
		
		// create data model and view and display log on window
	    this.connectionModel = new ConnectionModel();
	    ConnectionView connectionView = new ConnectionView();
	    logger.info(this.getClass().toString() + " connectionView called ...");
	    connectionView.showLogin(connectionModel);
	    // wait until user entered password (to be improved with runnables)
	    while (connectionModel.getPasswort() == null) { }

		// now that user has entered his password we can create the connection to the database
		try {
			this.connection = DriverManager.getConnection(connectionModel.getDburl(), 
			connectionModel.getBenutzer(), connectionModel.getPasswort());
			logger.info("Database connection to \"" + connectionModel.getDburl() + "\" created successfully");
		} catch (SQLException e) {
			logger.error(e.toString());
		}
		 // update the connection model with database connection and user-ID
		connectionModel.setDbverbindung(connection);
		ConnectionDAO connectionDAO = new ConnectionDAO(connectionModel);
		connectionModel.setUserID(connectionDAO.getUserID());
		
		// call default user screens or if there is none, the menu screen to choose the next screen
		if (connectionModel.getStandardgui().equals("")) {
			ConnectionMenu connectionMenu = new ConnectionMenu();
			connectionMenu.showLogin(connectionModel);
		} else  {
			if (connectionModel.getStandardgui().equals("stack")) {
				new StackController(connectionModel);
				logger.info("Stack controller was called ...");
			}
			
			if (connectionModel.getStandardgui().equals("tasklist")) {
				new TaskListMain(connectionModel);
				logger.info("Task list controller was called ...");
			}
			
			if (connectionModel.getStandardgui().equals("dashboard")) {
				//new DashboardMain(connectionModel);
				logger.info("Dashboard controller was called ...");
			}
			
			if (connectionModel.getStandardgui().equals("masterdata")) {
				new MasterDataController(connectionModel);
				logger.info("Master data controller was called ...");
			}
		}
	}
}
