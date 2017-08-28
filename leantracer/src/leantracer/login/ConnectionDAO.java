package leantracer.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Is the data access object (DAO) of module login. It connects to the database and retrieves data from the database
 * or it updates the database. It creates the SQL statements used to query or update the database. It reads the user-ID
 * from the database based on the user name. The user-ID is used in the connection data model.
 */
public class ConnectionDAO {
	
	private Logger logger = LogManager.getLogger();
	private ConnectionModel connectionModel;
	private Connection connection;
	
	public ConnectionDAO(ConnectionModel connectionModel) {
		
		Connection connection = connectionModel.getDbverbindung();
		this.connection = connection;
		this.connectionModel = connectionModel;
		logger.info("Constructor of " + this.toString() + "called.");
	}

	
	/**
	 * Queries the database to return the user-ID of a corresponding user name.
	 * @return userID the user-ID belonging to the corresponding user name
	 */
	public int getUserID() {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		// UserID are generated from the database automatically as automatically generated primary keys, therefore their
		// value ranges from 1, 2, 3 ... on wards, a value of -1 therefore indicates an indicates an initial value.
		int userID = -1;
		try {			
			statement = connection.prepareStatement("SELECT benutzer_id FROM benutzer WHERE benutzername =?");
			statement.setString(1, connectionModel.getBenutzer());
			resultSet = statement.executeQuery();
			logger.info("Statement \"" + statement.toString() + "\" was executed.");
			resultSet.next();
			userID = resultSet.getInt("benutzer_id");
			logger.info("The user Id of user \"" + connectionModel.getBenutzer() + "\" is: " + userID);
			
		} catch (SQLException e) {
			logger.error(e.toString());
		}
		finally {
			try {
				close(statement, resultSet);
			} catch (SQLException e) {
				logger.error(e.toString());
			}
		}
		return userID;
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
}
