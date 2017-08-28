package leantracer.tasklist;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import leantracer.tables.TaskList;

/**
 * @author Cyrill Steiner
 * @version 1.0
 * Is a table data model of a table object of the view. It contains the column names of the table. It contains an array list
 * containing the data to be displayed in the table. It is assigned to one of the five weekdays with 0 representing Monday, 
 * 1 representing Tuesday, 2 representing Wednesday, 3 representing Thursday, and 4 representing Friday. Overwrites methods of 
 * AbstractTableModel.
 */
public class TaskListDisplayModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 2259499861189873586L;
	private static final int AUFGABE_BEZ_COL = 0;
	private static final int ZEITDAUER_COL = 1;
	private static final int DATUM_COL = 2;
	
	private Logger logger = LogManager.getLogger();
	private List<TaskList> tasklist;
	private int dayOfWeekIndex;
	private String[] columnNames = { "Aufgabe", "Zeitdauer", "Datum"};
	private boolean conversionError;
	
	public TaskListDisplayModel () {
		conversionError = false;
	}
	
	
	/**
	 * Setter method for the array list tasklist.
	 * @param tasklist the array list containing the table rows 
	 */
	public void setTaskList(List<TaskList> tasklist){
		this.tasklist = tasklist;
	}
	
	
    /**
     * Sets the value of a particular table cell (row / columns coordinates) and fires a update event to all 
     * registered listeners. This method is empty in the AbstractTableModel. It needs to be override if cells are
     * supposed to be editable. It is called by JTable on the model if a cell is edited. In order to notify all
     * listeners about a change, for example the JTable itself, the method fireTableCellUpdated(row, col) implemented
     * in the AbstractTableModel needs to be called. If it is not called, a change in a table row vanishes after
     * hitting enter key, because JTable is not aware of any changes.
     * @param newValue the object that is added to a table cell
     * @param row the row number of the cell to be modified
     * @param col the column number of the cell to be modified
     */
	public void setValueAt(Object newValue, int row, int col) {
		logger.info("Holen der Werte von Reihe " + row + ", Spalte " + col);
		TaskList tempTaskList = tasklist.get(row);
		logger.info("Das aus der ArrayList geholte TaskList Objekt hat die Werte: " + tempTaskList.toString());

		switch (col) {
		case AUFGABE_BEZ_COL:
			logger.info("tempTaskList.getAufgabe_bez() = " + tempTaskList.getAufgabe_bez());
			tempTaskList.setAufgabe_bez((String) newValue);
			break;
		case DATUM_COL:
			logger.info("tempTaskList.getDatum() = " + tempTaskList.getDatum());
			tempTaskList.setDatum((Date) newValue);
			break;
		case ZEITDAUER_COL:
			logger.info("tempTaskList.getZeitdauer() = " + tempTaskList.getZeitdauer());
			//tempTaskList.setZeitdauer(new BigDecimal((String)newValue));
			tempTaskList.setZeitdauer(convertValueToBigDecimal(newValue));
			break;
		default:
			logger.info("Nicht gefunden, Default: tempTaskList.getAufgabe_bez() = " + tempTaskList.getAufgabe_bez());
			tempTaskList.setAufgabe_bez((String) newValue);
			break;
		}
		// here conversion errors that occurred when converting a BigDecimal are catched, do nothing in case of error
		if (conversionError) {
			conversionError = false;
		} else {
			tasklist.set(row, tempTaskList);
			fireTableCellUpdated(row, col);
		}
	}
	
	
	/**
	 * Setter method for the weekday index, where 0 represents Monday, 1 represents Tuesday, 2 represents Wednesday,
	 * 3 represents Thursday, and 4 represents Friday.
	 * @param weekday a number that determines which weekday this model represents
	 */
	public void setWeekDay(int weekday) {
		dayOfWeekIndex = weekday;
	}
	
	
	/**
	 * Getter method for the array list tasklist.
	 * @return tasklist the array list containing the table row data
	 */
	public List<TaskList> getTaskList(){
		return tasklist;
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
		return tasklist.size();
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
	 * Determines which table cells are editable and which are not.
	 * @return true if a cell is editable
	 */
	public boolean isCellEditable(int row, int col) {
		if (col==0||col==1) {
			return true;
		} else return false;
    }
	
	
	@Override
	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 * Returns the table value for a particular table cell (row / column coordinates).
	 * @return the object of a particular cell
	 */
	public Object getValueAt(int row, int col) {
        logger.info("Holen der Werte von Reihe " + row + ", Spalte " + col);
		TaskList tempTaskList = tasklist.get(row);
		logger.info("Das aus der ArrayList geholte TaskList Objekt hat die Werte: " + tempTaskList.toString());

		switch (col) {
		case AUFGABE_BEZ_COL:
			logger.info("tempTaskList.getAufgabe_bez() = " + tempTaskList.getAufgabe_bez());
			return tempTaskList.getAufgabe_bez();
		case ZEITDAUER_COL:
			logger.info("tempTaskList.getZeitdauer() = " + tempTaskList.getZeitdauer());
			return tempTaskList.getZeitdauer();
		case DATUM_COL:
			logger.info("tempTaskList.getDatum() = " + tempTaskList.getDatum());
			return tempTaskList.getDatum();
		default:
			logger.info("Nicht gefunden, Default: tempTaskList.getAufgabe_bez() = " + tempTaskList.getAufgabe_bez());
			return tempTaskList.getAufgabe_bez();
		}
	}
	
	
	/*
	 * Getter method for the weekday index where 0 represents Monday, 1 represents Tuesday, 2 represents Wednesday,
	 * 3 represents Thursday, and 4 represents Friday.
	 * @return dayOfWeekIndex a number that determines which weekday this model represents
	 */
	public int getWeekDay() {	
		return dayOfWeekIndex;
	}
	
	
	/*
	 * Convertes a object to a string an tries to create a BigDecimal from string, catches error
	 * if conversion to BigDecimal fails.
	 * @param newValue the object to be converted into a BigDecimal
	 * @return tempBigDecimal a number that determines which weekday this model represents
	 */
	public BigDecimal convertValueToBigDecimal(Object newValue) {	
		BigDecimal tempBigDecimal = BigDecimal.ZERO;
		try {
			BigDecimal newBigDecimal = new BigDecimal((String)newValue);
			return newBigDecimal;
		} catch (NumberFormatException e) {
			conversionError = true;
			return tempBigDecimal;
		}
	}
}
