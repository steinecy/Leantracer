package leantracer.login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Cyrill Steiner
 * @version 1.0
 * Is the connection data model of a users database connection. it reads out connection information from a configuration
 * file. It contains the database URL of the application. It also contains the user name, the password, the user-ID, the 
 * role and the standard gui of a successfully authenticated user. It additionally contains the current system date as a
 * Calendar Object. It is passed and used in the subsequent user screens in order to be able to connect to the database 
 * and to query it.
 */
public class ConnectionModel {
	
	private String userName;
	private String dburl;
	private String passWord;
	private Connection connection;
	private String standardgui;
	private Calendar calendar;
	private int userID;
	private String userRole;
	
	public ConnectionModel() {
		
		Logger logger = LogManager.getLogger();
		
		// Reading database connection information from configuration file connection.conf
	
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("connection.conf"));
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		}
				
		userName = props.getProperty("benutzer");
		dburl = props.getProperty("dburl");
		standardgui = props.getProperty("standardgui");
		calendar = Calendar.getInstance();
	}
	
	
	/**
	 * Getter method for the user name.
	 * @return userName the user name of the current application user
	 */
	public String getBenutzer(){
		return userName;
	}
	
	
	/**
	 * Getter method for the database URL
	 * @return dburl the database URL of the database the application is supposed to connect to
	 */
	public String getDburl(){
		return dburl;
	}
	
	
	/**
	 * Getter method for the user password
	 * @return passWord the database password of the current application user
	 */	
	public String getPasswort(){
		return passWord;
	}


	/**
	 * Getter method for the standard application of the user
	 * @return standardgui the standard application of the user
	 */	
	public String getStandardgui(){
		return standardgui;
	}
	
	
	/**
	 * Getter method for the database connection of the application
	 * @return connection the database connection of the application
	 */	
	public Connection getDbverbindung(){
		return connection;
	}
	
	
	/**
	 * Getter method for the current system date in the form of a Calendar object
	 * @return calendar the current system date
	 */	
	public Calendar getCalendar(){
		return calendar;
	}
	
	
	/**
	 * Getter method for the user-ID of the current application user
	 * @return userID the user-ID of the current application user
	 */	
	public int getUserID(){
		return userID;
	}
	
	
	/**
	 * Getter method for the user-ID of the current application user
	 * @return userID the user-ID of the current application user
	 */	
	public String getUserRole(){
		return userRole;
	}
	
	
	/**
	 * Setter method  for the database connection
	 * @param dbverbindung the database connection of the current application user
	 */	
	public void setDbverbindung(Connection dbverbindung){
		this.connection = dbverbindung;
	}
	
	
	/**
	 * Setter method for the user name of the current application user
	 * @param userName the user name of the current application user
	 */
	public void setBenutzer(String userName){
		this.userName = userName;
	}
	
	
	/**
	 * Setter method for the password of the current application user
	 * @param userName the password of the current application user
	 */
	public void setPasswort(String passWord){
		this.passWord = passWord;
	}

	
	/**
	 * Setter method for the current system date in the form of a Calendar object
	 * @param calendar the current system date
	 */
	public void setCalendar(Calendar calendar){
		this.calendar = calendar;
	}
	
	
	/**
	 * Setter method for the current system date in the form of a Calendar object
	 * @param calendar the current system date
	 */
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	
	/**
	 * Setter method for the role of the current application user
	 * @param userRole the role of the current application user
	 */
	public void setUserRole(String userRole){
		this.userRole = userRole;
	}
}
