package leantracer.stack;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import leantracer.tables.Stack;


/**
 * @author Cyrill Steiner
 * @version 1.0
 * Is the data model of the stack view and in particularly is also the table model for the table displayed it the stack view.
 * It contains the column names of the table "stack" to be displayed in the View. It contains an array list containing the 
 * data to be displayed in the table of the view. It contains a active task in form of a Stack object and an its state 
 * (active / inactive). The active task is the task from the top of the stack list. When active, the active task is removed
 * from the stack list, in order not to be displayed as paused task in the view. It measures elapsed time of the active task.
 * It implements and thus overwrites methods of AbstractTableModel.
 */
public class StackModel extends AbstractTableModel {

	private static final long serialVersionUID = -7778409061810981357L;
	private static final int STACKAUFGABE_BEZ_COL = 0;
	private static final int ZEITDAUER_COL = 1;
	private static final int REIHENFOLGE_COL = 2;
	private List<Stack> stackList;	
	private StackController controller;
	private Logger logger = LogManager.getLogger();
	private String[] columnNames = { "Bezeichnung", "Zeitdauer", "Reihenfolge"};
	private Stack activeStack;
	private boolean activeExists = false;
	private String activeLabel = "";
	private Instant startTime;
	private Instant stopTime;
	private Duration duration;
	private BigDecimal timeLaps;
	private String	timeSpan;

	public StackModel(StackController controller) {
		this.controller = controller;
	}
	
	
	/**
	 * Getter method for the array list stackList.
	 * @return stackList the array list containing the table row data
	 */
	public List<Stack> getStackList(){
		return stackList;
	}

	
	/**
	 * Setter method for the array list stackList.
	 * @param tasklist the array list containing the table rows 
	 */
	public void setStackList(List<Stack> stackList){
		this.stackList = stackList;
		// If data in data model is refreshed, the state of the active task needs to be reset as well.
		activeExists = false;
		activeStack = null;
		activeLabel = "";
		// if the stack list is reloaded, the GUI needs to be notified, that stack model has changed in order for the
		// GUI to be refreshed, by adding this data model to the component JTable, JTable is registered as observer on
		// this model. The method fireTableDataChanged() is implemented in AbstractTableModel.
		fireTableDataChanged();
	}
	
	
	/**
	 * Indicates if data model is empty or not, is true if stack list is null or if stack list is empty,
	 * returns false if stack list exists and contains elements.
	 * @return true if stackList is null or empty, false otherwise 
	 */
	public boolean isEmpty() {
		if (stackList == null) {
			return true;
		} else {
			return stackList.isEmpty();
		}
	}


	/**
	 * Getter method for active task.
	 * @return activeStack the active task for which time is measured
	 */
	public Stack getActiveTask() {
		return activeStack;
	}

	
	/**
	 * Setter method for active task.
	 * @param activeStack the active task for which time is measured
	 */
	public void setActiveTask(Stack activeStack) {
		this.activeStack = activeStack;
	}
	
	
	/**
	 * Getter method for the designation (label) of the active task.
	 * @return activeLabel the designation (label) of the active task
	 */
	public String getActiveLabel() {
		return activeLabel;
	}

	
	@Override
	/**
	 * Getter method for the number of columns.
	 * @return the number of columns of the table
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	
	@Override
	/**
	 * Getter method for the number of rows.
	 * @return the number of rows of the table
	 */
	public int getRowCount() {
		return stackList.size();
	}

	
	@Override
	/**
	 * Getter method for the name of a column.
	 * @return columnNames[i] the name of column number i
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	
	@Override
	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 * Defines which cells are supposed to be editable and which cells cannot be edited. Returns true if a cell is editable,
	 * false otherwise. In a stack table, only the task designation is supposed to be editable. Thus returns true only for 
	 * cells with column number zero.
	 * @return true if column number of a cell is zero, false otherwise
	 */
	public boolean isCellEditable(int row, int col) {
		if (col == 0) {
			return true;
		} else {
			return false;
		}
    }

	
	@Override
	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 * Returns the table value for a particular table cell (row / column coordinates).
	 * @return the object of a particular cell
	 */
	public Object getValueAt(int row, int col) {

		Stack tempStack = stackList.get(row);

		switch (col) {	
			case STACKAUFGABE_BEZ_COL:
				return tempStack.getStackaufgabe_bez();
			case ZEITDAUER_COL:
				return tempStack.getZeitdauer();
			case REIHENFOLGE_COL:
				return tempStack.getReihenfolge();
			default:
				return tempStack.getStackaufgabe_bez();
		}
	}

	
	@Override
    /**
     * This method is empty in the AbstractTableModel. It needs to be override if cells are supposed to be editable.
     * It is called by JTable on the model if a cell is edited. The implementation deviates a little bit from the 
     * intended usage, since the update of table cell needs not only to be represented in the view, but it also needs
     * to be written to the database. Normally one would update the table model data here (stackList), and then notify
     * the listening JTable by calling method fireTableCellUpdated(row, col), this would refresh the displayed JTable.
     * Instead, in this method the changed value is read out, the change is written to the database by notifying the
     * the controller and the display is refreshed by the controller by reloading the data from the database and by 
     * reloading the refreshed data model into the JTable. Only after a new stack list is loaded into the model the
     * method fireTableDataChanged() is executed and GUI is notified.
     * @param newValue the object that is added to a table cell
     * @param row the row number of the cell to be modified
     * @param col the column number of the cell to be modified
     */
	public void setValueAt(Object newValue, int row, int col) {
		if (col == 0) {
			logger.info("Fetch stack object of row " + row + ", & column " + col);
			Stack tempStack = stackList.get(row);
			logger.info("The stack object fetched has the value: " + stackList.toString());
			tempStack.setStackaufgabe_bez((String) newValue);
			controller.tableCellChanged(tempStack);
		}		
	}

	
	/**
	 * Extracts the top record of the stack task list and makes it the new active task. Stores the active task in a Stack
	 * object. Removes active task from list of stack objects in order to not display it as paused task in the view. Starts
	 * measuring of time for active task. Notifies GUI that table data has changed.
	 * @return true if a active task exists, false if no active task exists
	 */
	public boolean startActiveTask() {
		// Only if the list is not empty it is possible to remove an element from the list, therefore to avoid 
		// null pointer exceptions, this is checked beforehand
		if (!stackList.isEmpty()) {
			activeStack = stackList.remove(0);
			activeLabel = activeStack.getStackaufgabe_bez();
			startWatch();
			activeExists = true;
			fireTableDataChanged();
			return activeExists;
		} else {
			activeExists = false;
			activeLabel = "";
			return activeExists;
		}
	}
	
	
	/**
	 * Returns state of active task, is true if an active task exists, false otherwise
	 * @return activeExists the state of the active task, true if it exists, false if no active task exists
	 */
	public boolean doesActiveExists() {
		return activeExists;
	}
	
	
	/**
	 * Creates a time stamp, can be used if a new active task is started
	 */
	private void startWatch() {
		startTime = Instant.now();
		logger.info("Time started: " + startTime.toString());
	}
	
	
	/**
	 * Creates a time stamp, can be used if a active task is stopped
	 */
	private void stopWatch() {
		stopTime = Instant.now();
		logger.info("Time stopped: " + stopTime.toString());
	}
	
	
	/**
	 * Calculates elapsed time from two different time stamps. Converts minutes to decimal part of an hour.
	 */
	private void calculateDuration() {
		// If no active task exists, startTime and activeStack are null, this throws null pointer exceptions
		// therefore if no activStack exists, we do nothing here...
		if (activeExists) {
			duration = Duration.between(startTime,stopTime);
			logger.info("Duration = " + duration.toString());
			//timeSpan = duration.toHours() + ":" + (duration.toMinutes() - (duration.toHours()*60)) + ":00";
			logger.info("Stunden = " + duration.toHours() + ", Minuten = " + duration.toMinutes());
			String minutes = "" + (((duration.toMinutes() - (duration.toHours()*60))*100)/60);
			if ((minutes.length() == 1)) {
				minutes = "0" + minutes;
			}
			String hours = "" + duration.toHours();
			timeSpan = hours + "." + minutes;
			logger.info("The calculated time span is: " + timeSpan);
			timeLaps = new BigDecimal(timeSpan);
			logger.info("Time laps is: " + timeLaps.toString());
		}		
	}

	
	
	/**
	 * Calculates elapsed time if an active task is stopped and updates time span of current active task
	 */
	public void updateTimeSpan() {
		// If no active task exists, startTime and activeStack are null, this throws null pointer exceptions
		// therefore if no activStack exists we do nothing here...
		if (activeExists) {
			stopWatch();
			calculateDuration();
			BigDecimal newTimeLaps = activeStack.getZeitdauer().add(timeLaps);
			logger.info("old time laps = " + activeStack.getZeitdauer().toString() + ", new time laps = " 
			            + newTimeLaps.toString());
			activeStack.setZeitdauer(newTimeLaps);
		}
	}
}
