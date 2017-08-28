package leantracer.tasklist;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leantracer.login.ConnectionModel;
import leantracer.tables.TaskList;


/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Is the data access object (DAO) of module task list. It connects to the database and retrieves data from the database
 * or it updates the database. It converts the result rows of a database statement from JDBC data types to java data types.
 * It extracts the result rows of a database statement and fills an array list with these result rows. It creates the SQL
 * statements used to query or update the database. Its array list is used in the data model. It is called by the data model.
 * It always reads an write a complete row of the table that it accesses, even though maybe not the complete row needs to be
 * displayed or even though in case of an update, not all values of a row did change.
 *
 */
public class TaskListDAO {

	private Connection connection;
	private ConnectionModel connectionModel;
	private Logger logger = LogManager.getLogger();
	private TaskListDisplayModel tasklistDisplayModel;
	private Calendar calendar;
	private String datestring;

	public TaskListDAO(ConnectionModel connectionModel, TaskListDisplayModel tasklistDisplayModel,
			           Calendar calendar) {
		
		logger.info("Der " + this.getClass().toString() + "-Kostruktor wurde aufgerufen..");
		
		// Reading connection and date from connection model
		Connection connection = connectionModel.getDbverbindung();
		this.connection = connection;
		this.connectionModel = connectionModel;
		this.tasklistDisplayModel = tasklistDisplayModel;
		this.calendar = calendar;
		datestring = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		logger.info("Die Verbindungsinformationen wurden an die " + this.getClass().toString() + " übergeben..");
		logger.info("Der " + this.getClass().toString() + "  calendar hat den Wert : " + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        updateTaskList();
	}
	
	
	/**
	 * Updates the array list of the model that called this DAO with an freshly build array list containing the tasks list
	 * data freshly retrieved from the database.
	 */
	public void updateTaskList() {
		
		try {
			tasklistDisplayModel.setTaskList(this.buildTaskList());
		} catch (Exception e) {
			logger.error(e.toString());
		}
			logger.info("Dem Stackmodel wurden die Stackliste übergeben..");
	}
	
	
	/**
	 * Builds up an array list containing the tasks list freshly retrieved from the database.
	 * Adds an empty row at the beginning of the task list that serves as input field for the user for new tasks.
	 * Connects to the database and queries the database. Reads out the result set obtained from the database query 
	 * and puts each result set row into an array list row.
	 * @throws Exception Exception an SQL exception that occured when trying to read from database
	 * @return list the array list that contains the table rows
	 */
	public List<TaskList> buildTaskList() throws Exception {
		
		logger.info("Die Methode buildTaskList welche die TaskList-Liste erstellen soll wurde aufgerufen..");
		
		List<TaskList> list = new ArrayList<>();
		
		// We want the first row of the table to be empty as an input field for the user for new tasks,
		// therefore an empty line is added to the TaskList list before filling it with database data
		int nullInt = 0;
		String nullString = null; 
		Date nullDate = null;
		BigDecimal nullZeitdauer = null; 
		list.add(new TaskList(nullInt, nullString, nullDate, nullZeitdauer, nullInt,
				nullInt, nullString, nullInt, nullString, nullString, nullInt));
		
		Statement statement = null;
		ResultSet resultset = null;
		
		try {
			statement = connection.createStatement();
			logger.info("In der Methode getTaskList() wurde das Statement erzeugt..");
			resultset = statement.executeQuery(getReadSQLStatement());

			logger.info("Die Abfrage:\n" + getReadSQLStatement() + "\nwurde abgesetzt");
			while (resultset.next()) {
				logger.info("Die Resultset while-Schlaufe wird durchlaufen");
				TaskList tempTaskList = this.convertRowToStack(resultset);
				list.add(tempTaskList);
			}
			logger.info("Die Taskliste wurde erzeugt..");
			return list;		
		}
		finally {
			close(statement, resultset);
		}	
	}

	
    /**
     * Retrieves the result row from the result set and converts the single result values to java data types.
     * Creats a tasklist object and updates it with the single result values from the result set row. Adds the 
     * tasklist object it created and filled with data to the task list array list.
     * @param resultset the result from the database SQL query
     * @throws SQLException an exception that occurred while parsing the result set
     * @return tempTaskList a TaskList object that can be added to task list array list
     */
	private TaskList convertRowToStack(ResultSet resultset) throws SQLException {
		
		int aufgabe_id = resultset.getInt("aufgabe_id");
		String aufgabe_bez = resultset.getString("aufgabe_bez");
		Date datum = resultset.getDate("datum");
		BigDecimal zeitdauer = resultset.getBigDecimal("zeitdauer");
		int benutzer_id = resultset.getInt("benutzer_id");
		int kategorie_id = resultset.getInt("kategorie_id");
		String kategorie_bez = resultset.getString("kategorie_bez");
		int projekt_id = resultset.getInt("projekt_id");
		String projekt_bez = resultset.getString("projekt_bez");
		String system_id = resultset.getString("system_id");
		int standardaufgabe_id = resultset.getInt("standardaufgabe_id");
		
		TaskList tempTaskList = new TaskList(aufgabe_id, aufgabe_bez, datum, zeitdauer, benutzer_id, kategorie_id, 
				                             kategorie_bez, projekt_id, projekt_bez, system_id, standardaufgabe_id);
		logger.info("Folgende Stacktabelle-Zeile wurde in eine Stacklistenzeile umgewandelt:");
		logger.info(aufgabe_id + ", " + aufgabe_bez + ", " + datum.toString() + ", " + zeitdauer + ", " + benutzer_id + ", " 
		            + kategorie_id + ", " + kategorie_bez + ", " + projekt_id + ", " + projekt_bez + ", " 
		            + system_id + ", " + standardaufgabe_id);
		logger.info("Das erzeugte Tasklistobjekt hat die Werte: " + tempTaskList.toString());
		return tempTaskList;
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
	 * Updates the database with new data entered by the user. Retrieves the data to be written to the database from
	 * the array list from the model that called this DAO. Writes the whole row of a tasklist object to the database,
	 * even though maybe only a single value of a whole row was changed.
	 * @param row the table row that contains changed data
	 */
	public void updateInDatabase(int row) {
		PreparedStatement statement = null;
		String aufgabe_bez = (String)tasklistDisplayModel.getValueAt(row, 0);
		BigDecimal zeitdauer = (BigDecimal)tasklistDisplayModel.getValueAt(row, 1);
		List<TaskList> tasklist = tasklistDisplayModel.getTaskList();
		TaskList tempTaskList = tasklist.get(row);
		int aufgabe_id = tempTaskList.getAufgabe_id();
		
		try {
			
			statement = connection.prepareStatement("update aufgabenliste"
					+ " set aufgabe_bez=?, zeitdauer=? where aufgabe_id=?");
			
			statement.setString(1, aufgabe_bez);
			statement.setBigDecimal(2, zeitdauer);
			statement.setInt(3, aufgabe_id);

			statement.executeUpdate();
			
		} catch (SQLException e) {
			logger.error(e.toString());
		}
		finally {
			try {
				close(statement);
			} catch (SQLException e) {
				logger.error(e.toString());
			}
		}
	}

	
	/**
	 * Creates an new task in the database that was entered by the user. Gets its data from the task list of the data model
	 * that called this DAO. Since the user enters new data into the first empty row of the table, this row does not contain
	 * the information about the user id, therefore it is simply taken from the second row.
	 */
	public void createInDatabase() {
		PreparedStatement statement = null;
		String aufgabe_bez = (String)tasklistDisplayModel.getValueAt(0, 0);
		BigDecimal zeitdauer = (BigDecimal)tasklistDisplayModel.getValueAt(0, 1);
		//List<TaskList> tasklist = tasklistDisplayModel.getTaskList();
		// we have to get the value for the benutzer_id from the second row (row 1), since the value for the 
		// benutzer_id is zero in the first row (row 0) since this row is created empty on purpose
		//TaskList tempTaskList = tasklist.get(1);
		//int benutzer_id = tempTaskList.getBenutzer_id();
		int benutzer_id = connectionModel.getUserID();
		try {
			// prepare statement
			statement = connection.prepareStatement("insert into aufgabenliste"
					+ " (aufgabe_bez, datum, zeitdauer, benutzer_id)"
					+ " values (?, ?, ?, ?)");
			
			// set params
			statement.setString(1, aufgabe_bez);
			statement.setString(2, datestring);
			statement.setBigDecimal(3, zeitdauer);
			statement.setInt(4, benutzer_id);
			logger.info("statement.toString() = " + statement.toString());
			
			// execute SQL
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
		finally {
			try {
				close(statement);
			} catch (SQLException e) {
				logger.error(e.toString());
			}
		}
		//updateTaskList();
	}
	
	
	/**
	 * Converts the current data into a string suitable for a SQL statement. Generates the SQL statement used to 
	 * retrieve the data from the database.
	 * @return sqlstmnt the SQL statement used to query the database
	 */
	private String getReadSQLStatement() {
		
		String datestring = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		logger.info("Der Datums-String für das SQL-Kommando lautet: \"" + datestring + "\"");
		String sqlstmnt = "select al.aufgabe_id, al.aufgabe_bez, al.datum, al.zeitdauer, al.benutzer_id,"
                		  + " al.kategorie_id, kategorie.kategorie_bez, al.projekt_id, projekt.projekt_bez,"
                		  + " al.system_id, al.standardaufgabe_id "
                		  + "from aufgabenliste al "
                		  + "left join projekt using(projekt_id) "
                		  + "left join kategorie using(kategorie_id) "
                		  + "where al.benutzer_id = (select b.benutzer_id from benutzer b "
                		  + "where b.benutzername = \"" + connectionModel.getBenutzer() + "\") "
                		  + "AND al.datum = \"" + datestring + "\" " 
                		  + "order by datum, aufgabe_bez";	
		return sqlstmnt;
	}
}

