package leantracer.masterdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leantracer.login.ConnectionModel;
import leantracer.shared.ErrorHandler;
import leantracer.tables.StandardTask;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Is a data access object (DAO) of module master data. It connects to the database and retrieves data from the database
 * or it updates the database. It converts the result rows of a database statement from JDBC data types to java data types.
 * It extracts the result rows of a database statement and fills an array list with these result rows. It creates the SQL
 * statements used to query or update the database. Its array list is used in the data model. It is called by the controller.
 * It always reads and writes a complete row of the table that it accesses, even though maybe not the complete row needs to be
 * displayed or updated.
 *
 */
public class MasterDataStandardTaskDAO {
	private Connection connection;
	private Logger logger = LogManager.getLogger();
	private ErrorHandler errorHandler;

	
	public MasterDataStandardTaskDAO(ConnectionModel connectionModel) {
		logger.info(this.getClass().toString() + " constructor was called..");
		errorHandler = new ErrorHandler(this.getClass().toString());
		Connection connection = connectionModel.getDbverbindung();
		this.connection = connection;
	}

	
	/**
	* Calls fetchSystems(), but catches all Exceptions occurring in fetchSystems(), as there are a number exceptions which 
	* need to be catch in fetchSystems().
	* @return list the array list that contains the table rows if no error occurs, empty list otherwise
	*/
	public List<StandardTask> getStandardTasks() {
		try {
			return fetchSystems();
		} catch (Exception e) {
			errorHandler.handleError(e);
			List<StandardTask> list = new ArrayList<>();
			return list;
		}
	}


	/**
	* Builds up an array list containing the SAP systems data retrieved from the database. Connects to the database
	* and queries the database. Reads out the result set obtained from the database query and puts each result set
	* row into an array list row.
 	* @throws Exception an SQL exception that occurred when trying to read from database
 	* @return list the array list that contains the table rows
 	*/
	public List<StandardTask> fetchSystems() throws Exception {
		List<StandardTask> list = new ArrayList<>();	
		PreparedStatement statement = null;	
		ResultSet resultset = null;	
		try {			
			statement = connection.prepareStatement("SELECT sa.standardaufgabe_id, sa.standardaufgabe_bez, sa.kategorie_id, "
													+ "kategorie.kategorie_bez, sa.projekt_id, projekt.projekt_bez, sa.system_id, "
													+ "sa.benutzer_id, b.benutzername FROM standardaufgabe sa "
													+ "LEFT JOIN kategorie using (kategorie_id) "
													+ "LEFT JOIN projekt using (projekt_id) "
													+ "LEFT JOIN benutzer b ON b.benutzer_id = sa.benutzer_id "
													+ "ORDER BY standardaufgabe_bez");
			resultset= statement.executeQuery();
			logger.info("The statement \"" + statement.toString() + "\" was executed ...");
			while (resultset.next()) {
				StandardTask tempStandardTask = convertResultrowToStandardtask(resultset);
				list.add(tempStandardTask);
			}
			logger.info("StandardTask list was generated..");
		} catch (SQLException e) {
			errorHandler.handleError(e);
		}
		finally {
			try {
				close(statement, resultset);
			} catch (SQLException e) {
				errorHandler.handleError(e);
			}
		}
		return list;
	}

	
    /**
     * Retrieves the result row from the result set and converts the single values of the result row to java 
     * data types. Creates a System object and updates it with the single result values from the result set
     * row.
     * @param resultset the result from the database SQL query
     * @throws SQLException an exception that occurred while parsing the result set
     * @return tempSystem a System object that can be added to the system array list
     */
	private StandardTask convertResultrowToStandardtask(ResultSet resultset) throws SQLException {	
		int standardaufgabe_id = resultset.getInt("standardaufgabe_id");
		String standardaufgabe_bez = resultset.getString("standardaufgabe_bez");
		int kategorie_id = resultset.getInt("kategorie_id");
		String kategorie_bez = resultset.getString("kategorie_bez");
		int projekt_id = resultset.getInt("projekt_id");
		String projekt_bez = resultset.getString("projekt_bez");
		String system_id = resultset.getString("system_id");
		int benutzer_id = resultset.getInt("benutzer_id");
		String benutzername = resultset.getString("benutzername");
		StandardTask tempStandardTask = new StandardTask(standardaufgabe_id, standardaufgabe_bez, kategorie_id, kategorie_bez, 
														 projekt_id, projekt_bez, system_id, benutzer_id, benutzername);
		logger.info("The following resultset row was converted into a System object: " + tempStandardTask.toString());
		return tempStandardTask;
	}	
	
	
	/**
	 * Closes the database connection, statement and resultset.
	 * @param connection the database connection to be closed
	 * @param statement the statement used to query the database
	 * @param resultset the result set from the database query
	 * @throws SQLException an exception that occurred when trying to close connection, resultset or statement
	 */
	private static void close(Connection connection, Statement statement, ResultSet resultset)
			throws SQLException {

		if (resultset != null) {
			resultset.close();
		}

		if (statement != null) {
			statement.close();
		}
		
		if (connection != null) {
			connection.close();
		}
	}

	
	/**
	 * Closes the statement and resultset of a database query.
	 * @param statement the statement used to query the database
	 * @param resultset the result set from the database query
	 * @throws SQLException an exception that occurred when trying to close statement or resultset
	 */
	private void close(Statement statement, ResultSet resultset) throws SQLException {
		close(null, statement, resultset);		
	}

	
	/**
	 * Closes the statement of a database query.
	 * @param statement the statement used to query the database
	 * @throws SQLException an exception that occurred when trying to close statement
	 */
	private void close(Statement statement) throws SQLException {
		close(null, statement, null);		
	}

	
	/**
	 * Updates all columns of a database record of type StandardTask. 
	 * @param tempSystem the system table record that needs to be updated
	 */
	public void updateSystemTableRecord(StandardTask tempStandardTask) {
		PreparedStatement statement = null;		
		int standardaufgabe_id = tempStandardTask.getStandardaufgabe_id();
		String standardaufgabe_bez = tempStandardTask.getStandardaufgabe_bez();
		int kategorie_id = tempStandardTask.getKategorie_id();
		int projekt_id = tempStandardTask.getProjekt_id();
		String system_id = tempStandardTask.getSystem_id();
		int benutzer_id = tempStandardTask.getBenutzer_id();
		try {			
			statement = connection.prepareStatement("update standardaufgabe set standardaufgabe_bez=?, kategorie_id=?, "
													+ "projekt_id=?, system_id=?, benutzer_id=? where standardaufgabe_id=?");			
			statement.setString(1, standardaufgabe_bez);
			statement.setInt(2, kategorie_id);
			statement.setInt(3, projekt_id);
			statement.setString(4, system_id);
			statement.setInt(5, benutzer_id);
			statement.setInt(6, standardaufgabe_id);
			statement.executeUpdate();
			logger.info("The statement = " + statement.toString() + " was executed ...");
		} catch (SQLException e) {
			errorHandler.handleError(e);
		}
		finally {
			try {
				close(statement);
			} catch (SQLException e) {
				errorHandler.handleError(e);
			}
		}
	}
	
	
	/**
	 * Creates a new entry in table system based on a System object. 
	 * @param tempSystem the System record that needs to be created in table system
	 */
	public void createSystemTableRecord(StandardTask tempStandardTask) {
		PreparedStatement statement = null;	
		String standardaufgabe_bez = tempStandardTask.getStandardaufgabe_bez();
		int kategorie_id = tempStandardTask.getKategorie_id();
		int projekt_id = tempStandardTask.getProjekt_id();
		String system_id = tempStandardTask.getSystem_id();
		int benutzer_id = tempStandardTask.getBenutzer_id();	
		try {			
			statement = connection.prepareStatement("insert into standardaufgabe"
					+ " (standardaufgabe_bez, kategorie_id, projekt_id, system_id, benutzer_id)"
					+ " values (?, ?, ?, ?, ?)");			
			statement.setString(1, standardaufgabe_bez);
			statement.setInt(2, kategorie_id);
			statement.setInt(3, projekt_id);
			statement.setString(4, system_id);
			statement.setInt(5, benutzer_id);
			statement.executeUpdate();
			logger.info("The statement = " + statement.toString() + " was executed ...");	
		} catch (SQLException e) {
			errorHandler.handleError(e);
		}
		finally {
			try {
				close(statement);
			} catch (SQLException e) {
				errorHandler.handleError(e);
			}
		}
	}
	
	
	/**
	 * Deletes a record in table system. 
	 * @param tempSystem the system table record that needs to be deleted
	 */
	public void deleteSystemTableRecord(StandardTask tempStandardTask) {
		PreparedStatement statement = null;		
		int standardaufgabe_id = tempStandardTask.getStandardaufgabe_id();
		try {			
			statement = connection.prepareStatement("DELETE FROM standardaufgabe WHERE standardaufgabe_id=?");
			statement.setInt(1, standardaufgabe_id);			
			statement.executeUpdate();
			logger.info("The statement = " + statement.toString() + " was executed ...");
		} catch (SQLException e) {
			errorHandler.handleError(e);
		}
		finally {
			try {
				close(statement);
			} catch (SQLException e) {
				errorHandler.handleError(e);
			}
		}
	}
}
