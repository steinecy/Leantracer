package leantracer.stack;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leantracer.login.ConnectionModel;
import leantracer.shared.ErrorHandler;
import leantracer.tables.Stack;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Is the data access object (DAO) of module stack. It connects to the database and retrieves data from the database
 * or it updates the database. It converts the result rows of a database statement from JDBC data types to java data types.
 * It extracts the result rows of a database statement and fills an array list with these result rows. It creates the SQL
 * statements used to query or update the database. Its array list is used in the data model. It is called by the controller.
 * It always reads and writes a complete row of the table that it accesses, even though maybe not the complete row needs to be
 * displayed or even though.
 *
 */
public class StackDAO {

	private Connection connection;
	private ConnectionModel connectionModel;
	private Logger logger = LogManager.getLogger();
	private ErrorHandler errorHandler;

	public StackDAO(ConnectionModel connectionModel) {
		
		logger.info(this.getClass().toString() + " constructor was called..");
		errorHandler = new ErrorHandler(this.getClass().toString());
		Connection connection = connectionModel.getDbverbindung();
		this.connection = connection;
		this.connectionModel = connectionModel;
		
		
	}
	
	
	/**
	* Same as fetchStack(), but catches all Exceptions occurring in fetchStack(), an there are many of them in fetchStack().
	* Otherwise,
	*  if fetchStack() is called directly from another object, one always needs to catch the exceptions.
	* @return list the array list that contains the table rows if no error occurs, empty list otherwise
	*/
	public List<Stack> getStack() {
		try {
			return fetchStack();
		} catch (Exception e) {
			errorHandler.handleError(e);
			List<Stack> list = new ArrayList<>();
			return list;
		}
	}


	/**
	* Builds up an array list containing the stack list data retrieved from the database. Connects to the database
	* and queries the database. Reads out the result set obtained from the database query and puts each result set
	* row into an array list row. Fills the sequence number field of a stack record since this is not stored in the 
	* database.
 	* @throws Exception Exception an SQL exception that occured when trying to read from database
 	* @return list the array list that contains the table rows
 	*/
	public List<Stack> fetchStack() throws Exception {
		logger.info("Die Methode getStack() welche die Stackliste erstellen soll wurde aufgerufen..");
		List<Stack> list = new ArrayList<>();
		
		Statement statement = null;
		ResultSet resultset = null;
		
		try {
			statement = connection.createStatement();
			logger.info("In der Methode getStack() wurde das Statement erzeugt..");
			resultset = statement.executeQuery("select * from stack where benutzer_id = (select benutzer_id from benutzer "
					+ "where benutzername = \"" + connectionModel.getBenutzer() +"\") order by stack_nr DESC");
			logger.info("Die Abfrage \"select * from stack where benutzer_id = (select benutzer_id from benutzer "
					+ "where benutzername = \"" + connectionModel.getBenutzer() +"\") order by stack_nr");
			int i = 0;
			while (resultset.next()) {
				logger.info("Die Resultset while-Schlaufe wird durchlaufen");
				Stack tempStack = this.convertRowToStack(resultset);
				tempStack.setReihenfolge(i);
				i++;
				list.add(tempStack);
			}
			logger.info("Die Stackliste wurde erzeugt..");
			return list;
		}
		finally {
			close(statement, resultset);
		}
	}
	
	
    /**
     * Retrieves the result row from the result set and converts the single values of the result row to java 
     * data types. Creates a tasklist object and updates it with the single result values from the result set
     * row. Adds the tasklist object it created and filled with data to the task list array list.
     * @param resultset the result from the database SQL query
     * @throws SQLException an exception that occurred while parsing the result set
     * @return tempTaskList a TaskList object that can be added to task list array list
     */
	private Stack convertRowToStack(ResultSet resultset) throws SQLException {
		
		int stackaufgabe_id = resultset.getInt("stackaufgabe_id");
		String stackaufgabe_bez = resultset.getString("stackaufgabe_bez");
		BigDecimal zeitdauer = resultset.getBigDecimal("zeitdauer");
		int benutzer_id = resultset.getInt("benutzer_id");
		int standardaufgabe_id = resultset.getInt("standardaufgabe_id");
		int stack_nr = resultset.getInt("stack_nr");
		Date datum = resultset.getDate("datum");
		
		Stack tempStack = new Stack(stackaufgabe_id, stackaufgabe_bez, zeitdauer, benutzer_id, standardaufgabe_id, stack_nr, datum);
		logger.info("Folgende Stacktabelle-Zeile wurde in eine Stacklistenzeile umgewandelt:");
		logger.info(stackaufgabe_id + ", " + stackaufgabe_bez + ", " + zeitdauer + ", " + benutzer_id + ", " + standardaufgabe_id + ", " + stack_nr + ", " + datum.toString());
		return tempStack;
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
	 * Iterates over a list of Stack objects and calls a database stack record update for each Stack object in the Stack list. 
	 * @param updateList the list of Stack objects corresponding to records of table "stack" which need to be updated
	 */
	public void updateStackTable(List<Stack> updateList) {
		for (Stack tempStack: updateList) {
			updateStackTableRecord(tempStack);
			logger.info("DB update list iterated");
		}
	}
	
	
	/**
	 * Iterates over a list of Stack objects and deletes the corresponding record from stack table for each Stack object in the Stack list. 
	 * @param updateList the list of Stack objects corresponding to records of table "stack" which need to be deleted
	 */
	public void deleteStackTable(List<Stack> updateList) {
		for (Stack tempStack: updateList) {
			deleteStackTableRecord(tempStack);
			logger.info("DB delete list iterated");
		}
	}
	
	
	/**
	 * Iterates over a list of Stack objects and creates the corresponding records in table "tasklist" for each Stack object in the Stack list. 
	 * @param updateList the list of Stack objects corresponding to records of table "stack" which need to be deleted
	 */
	public void createTaskListTable(List<Stack> updateList) {
		for (Stack tempStack: updateList) {
			createTaskListTableRecord(tempStack);
			logger.info("DB delete list iterated");
		}
	}
	
	
	/**
	 * Updates all columns of a database record of type Stack. 
	 * @param tempStack the Stack Record that needs to be updated
	 */
	public void updateStackTableRecord(Stack tempStack) {
		PreparedStatement statement = null;		
		String stackaufgabe_bez = tempStack.getStackaufgabe_bez();
		BigDecimal zeitdauer = tempStack.getZeitdauer();
		int stack_nr = tempStack.getStack_nr();
		Date datum = tempStack.getDatum();
		int stackaufgabe_id = tempStack.getStackaufgabe_id();
		String datestring = new SimpleDateFormat("yyyy-MM-dd").format(datum);
		logger.info("Updating DB Record: stackaufgabe_bez = " + stackaufgabe_bez + ", zeitdauer = " + zeitdauer
				    + ", stack_nr =" + stack_nr + ", datum = " + datestring + ", stackaufgabe_id = " + stackaufgabe_id);
		
		try {
			
			statement = connection.prepareStatement("update stack set stackaufgabe_bez=?, zeitdauer=?,"
					    + " stack_nr=?, datum=? where stackaufgabe_id=?");
			
			statement.setString(1, stackaufgabe_bez);
			statement.setBigDecimal(2, zeitdauer);
			statement.setInt(3, stack_nr);
			statement.setString(4, datestring);
			statement.setInt(5, stackaufgabe_id);

			statement.executeUpdate();
			
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
	 * Creates a new entry in table task list based on a record in table "stack". 
	 * @param tempStack the Stack record that needs to be converted into a TaskList record and that needs to be created in
	 *        table "tasklist" 
	 */
	public void createTaskListTableRecord(Stack tempStack) {
		PreparedStatement statement = null;		
		String aufgabe_bez = tempStack.getStackaufgabe_bez();
		Date datum = tempStack.getDatum();
		BigDecimal zeitdauer = tempStack.getZeitdauer();
		int benutzer_id = tempStack.getBenutzer_id();
		String datestring = new SimpleDateFormat("yyyy-MM-dd").format(datum);
		
		try {			
			statement = connection.prepareStatement("insert into aufgabenliste"
					+ " (aufgabe_bez, datum, zeitdauer, benutzer_id)"
					+ " values (?, ?, ?, ?)");
			
			statement.setString(1, aufgabe_bez);
			statement.setString(2, datestring);
			statement.setBigDecimal(3, zeitdauer);
			statement.setInt(4, benutzer_id);
			logger.info("statement.toString() = " + statement.toString());
			
			statement.executeUpdate();
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
	 * Creates a new entry in table task list based on a record in table "stack". 
	 * @param tempStack the Stack record that needs to be converted into a TaskList record and that needs to be created in
	 *        table "tasklist" 
	 */
	public void deleteStackTableRecord(Stack tempStack) {
		PreparedStatement statement = null;		
		int stackaufgabe_id = tempStack.getStackaufgabe_id();

		try {			
			statement = connection.prepareStatement("DELETE FROM stack WHERE stackaufgabe_id=?");
			
			statement.setInt(1, stackaufgabe_id);

			logger.info("statement.toString() = " + statement.toString());
			
			statement.executeUpdate();
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
	 * Creates a new entry in table stack  based on a Stack object. 
	 * @param tempStack the stack object to be created in table stack
	 */
	public void createStackTableRecord(Stack tempStack) {
		PreparedStatement statement = null;				
		String stackaufgabe_bez = tempStack.getStackaufgabe_bez();
		BigDecimal zeitdauer = tempStack.getZeitdauer();
		int userID = tempStack.getBenutzer_id();
		int stack_nr = tempStack.getStack_nr();
		String datestring = new SimpleDateFormat("yyyy-MM-dd").format(tempStack.getDatum());
		
		try {			
			statement = connection.prepareStatement("insert into stack"
					+ " (stackaufgabe_bez, zeitdauer, benutzer_id, stack_nr, datum)"
					+ " values (?, ?, ?, ?, ?)");
			
			statement.setString(1, stackaufgabe_bez);
			statement.setBigDecimal(2, zeitdauer);
			statement.setInt(3, userID);
			statement.setInt(4, stack_nr);
			statement.setString(5, datestring);
			logger.info("statement.toString() = " + statement.toString());
			
			statement.executeUpdate();
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
