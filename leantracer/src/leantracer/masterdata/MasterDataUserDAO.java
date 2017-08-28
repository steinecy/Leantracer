package leantracer.masterdata;

import java.math.BigDecimal;
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
import leantracer.tables.User;

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
public class MasterDataUserDAO {
	private Connection connection;
	private Logger logger = LogManager.getLogger();
	private ErrorHandler errorHandler;
	
	public MasterDataUserDAO(ConnectionModel connectionModel) {
		logger.info(this.getClass().toString() + " constructor was called..");
		errorHandler = new ErrorHandler(this.getClass().toString());
		Connection connection = connectionModel.getDbverbindung();
		this.connection = connection;
	}

	
	/**
	* Calls fetchUsers(), but catches all Exceptions occurring in fetchUsers(), as there are a number exceptions which 
	* need to be catch in fetchUsers().
	* @return list the array list that contains the table rows if no error occurs, empty list otherwise
	*/
	public List<User> getUsers() {
		try {
			return fetchUsers();
		} catch (Exception e) {
			errorHandler.handleError(e);
			List<User> list = new ArrayList<>();
			return list;
		}
	}


	/**
	* Builds up an array list containing the user account data retrieved from the database. Connects to the database
	* and queries the database. Reads out the result set obtained from the database query and puts each result set
	* row into an array list row.
 	* @throws Exception an SQL exception that occurred when trying to read from database
 	* @return list the array list that contains the table rows
 	*/
	private List<User> fetchUsers() throws Exception {
		List<User> list = new ArrayList<>();	
		PreparedStatement statement = null;	
		ResultSet resultset = null;	
		try {			
			statement = connection.prepareStatement("SELECT b.benutzer_id, b.benutzername, b.vorname, "
													+ "b.nachname, b.sollarbeitszeit FROM benutzer b ");
			resultset= statement.executeQuery();
			logger.info("The statement \"" + statement.toString() + "\" was executed ...");
			int i = 0;
			while (resultset.next()) {
				User tempUser = convertResultrowToUser(resultset);
				tempUser.setReihenfolge(i);
				list.add(tempUser);
				i++;
			}
			logger.info("User list was generated..");
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
     * data types. Creates a User object and updates it with the single result values from the result set
     * row.
     * @param resultset the result from the database SQL query
     * @throws SQLException an exception that occurred while parsing the result set
     * @return tempUser a User object that can be added to the system array list
     */
	private User convertResultrowToUser(ResultSet resultset) throws SQLException {	
		int benutzer_id = resultset.getInt("benutzer_id");
		String benutzername = resultset.getString("benutzername");
		String vorname = resultset.getString("vorname");
		String nachname = resultset.getString("nachname");
		BigDecimal zeitdauer = resultset.getBigDecimal("sollarbeitszeit");
		int reihenfolge = -1;
		User tempUser = new User(benutzer_id, benutzername, vorname, nachname, zeitdauer, reihenfolge);
		logger.info("The following resultset row was converted into a User object: " + tempUser.toString());
		return tempUser;
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
	 * Updates all columns of a database record of type User. 
	 * @param tempSystem the system table record that needs to be updated
	 */
	public void updateUser(User tempUser) {
		PreparedStatement statement = null;		
		int benutzer_id = tempUser.getBenutzer_id();
		String benutzername = tempUser.getBenutzername();
		String vorname = tempUser.getVorname(); 
		String nachname = tempUser.getNachname();
		BigDecimal sollarbeitszeit = tempUser.getSollarbeitszeit();	
		try {			
			statement = connection.prepareStatement("update benutzer set benutzername=?, vorname=?, nachname=?, "
													+ "sollarbeitszeit=? where benutzer_id=?");			
			statement.setString(1, benutzername);
			statement.setString(2, vorname);
			statement.setString(3, nachname);
			statement.setBigDecimal(4, sollarbeitszeit);
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
	 * Creates a new entry in table system based on a System object. 
	 * @param tempSystem the System record that needs to be created in table system
	 */
	public void createUserTableRecord(User tempUser) {
		PreparedStatement statement = null;		
		String benutzername = tempUser.getBenutzername();
		String vorname = tempUser.getVorname(); 
		String nachname = tempUser.getNachname();
		BigDecimal sollarbeitszeit = tempUser.getSollarbeitszeit();		
		try {			
			statement = connection.prepareStatement("insert into benutzer"
					+ " (benutzername, vorname, nachname, sollarbeitszeit)"
					+ " values (?, ?, ?, ?)");			
			statement.setString(1, benutzername);
			statement.setString(2, vorname);
			statement.setString(3, nachname);
			statement.setBigDecimal(4, sollarbeitszeit);
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
