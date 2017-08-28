package leantracer.shared;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Cyrill Steiner
 * @version 1.0
 * Handles exceptions by logging them to log file and displaying them in a
 * pop up window
 */
public class ErrorHandler {
	private Logger logger;
	private String classString;

	public ErrorHandler(String classString) {
		logger = LogManager.getLogger();
		this.classString = classString;
	}
	
	/**
	 * Displays error messages.
	 */
	private void showPopUp(String titel, String message, int messagetype){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, message, titel, messagetype);	
	}
	
	
	/**
	 * Handles exceptions by logging them to log file and displaying them in a
	 * pop up window
	 * @param e the exception to handle
	 */
	public void handleError(Exception e){
		logger.error("\nError from Class: " + classString + "\n" + e.toString() + "\n"
				+ "Stacktrace:\n" + e.getStackTrace().toString());
		showPopUp("Fehler", ("\nError from Class: " + classString + "\n" + e.toString() + "\n"), 1);
	}
	
}
