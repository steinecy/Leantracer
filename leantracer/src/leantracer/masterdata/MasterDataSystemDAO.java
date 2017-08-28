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
import leantracer.tables.SAPSystem;

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
public class MasterDataSystemDAO {
	private Connection connection;
	private Logger logger = LogManager.getLogger();
	private ErrorHandler errorHandler;
	
	public MasterDataSystemDAO(ConnectionModel connectionModel) {		
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
	public List<SAPSystem> getSystems() {
		try {
			return fetchSystems();
		} catch (Exception e) {
			errorHandler.handleError(e);
			List<SAPSystem> list = new ArrayList<>();
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
	public List<SAPSystem> fetchSystems() throws Exception {
		List<SAPSystem> list = new ArrayList<>();	
		PreparedStatement statement = null;	
		ResultSet resultset = null;	
		try {			
			statement = connection.prepareStatement("select system_id, system_bez, landschaft_id, landschaft_bez"
					                                + " from system left join landschaft using (lanschaft_id) "
					                                + "order by system_bez");
			resultset= statement.executeQuery();
			logger.info("The statement \"" + statement.toString() + "\" was executed ...");
			while (resultset.next()) {
				SAPSystem tempSystem = convertResultrowToSystem(resultset);
				list.add(tempSystem);
			}
			logger.info("Systems list was generated..");
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
	private SAPSystem convertResultrowToSystem(ResultSet resultset) throws SQLException {	
		String system_id = resultset.getString("system_id");
		String system_bez = resultset.getString("system_bez");
		int landschaft_id = resultset.getInt("landschaft_id");
		String landschaft_bez = resultset.getString("landschaft_bez");	
		SAPSystem tempSystem = new SAPSystem(system_id, system_bez, landschaft_id, landschaft_bez);
		logger.info("The following resultset row was converted into a System object: "
					+ system_id + ", " + system_bez + ", " + landschaft_id + ", " + landschaft_bez);
		return tempSystem;
	}

	
	/**
	 * Closes the statement and resultset of a database query.
	 * @param statement the statement used to query the database
	 * @param resultset the result set from the database query
	 * @throws SQLException an exception that occurred when trying to close statement or resultset
	 */
	private void close(Statement statement, ResultSet resultset) throws SQLException {

		if (resultset != null) {
			resultset.close();
		}

		if (statement != null) {
			statement.close();			
		}
	}
	
	
	/**
	 * Closes the statement of a database query.
	 * @param statement the statement used to query the database
	 * @throws SQLException an exception that occurred when trying to close statement
	 */
	private void close(Statement statement) throws SQLException {
		close(statement, null);		
	}
	
	
	/**
	 * Updates all columns of a database record of type System. 
	 * @param tempSystem the system table record that needs to be updated
	 */
	public void updateSystemTableRecord(SAPSystem tempSystem) {
		PreparedStatement statement = null;		
		String system_id = tempSystem.getSystem_id();
		String system_bez = tempSystem.getSystem_bez();
		int landschaft_id = tempSystem.getLandschaft_id();		
		try {			
			statement = connection.prepareStatement("update system set system_bez=?, landschaft_id=?,"
					    + " where system_id=?");			
			statement.setString(1, system_bez);
			statement.setInt(2, landschaft_id);
			statement.setString(3, system_id);
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
	public void createSystemTableRecord(SAPSystem tempSystem) {
		PreparedStatement statement = null;	
		String system_id = tempSystem.getSystem_id();
		String system_bez = tempSystem.getSystem_bez();
		int landschaft_id = tempSystem.getLandschaft_id();		
		try {			
			statement = connection.prepareStatement("insert into system"
					+ " (system_id, system_bez, landschaft_id)"
					+ " values (?, ?, ?)");			
			statement.setString(1, system_id);
			statement.setString(2, system_bez);
			statement.setInt(3, landschaft_id);	
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
	public void deleteSystemTableRecord(SAPSystem tempSystem) {
		PreparedStatement statement = null;		
		String system_id = tempSystem.getSystem_id();
		try {			
			statement = connection.prepareStatement("DELETE FROM system WHERE system_id=?");
			statement.setString(1, system_id);			
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
