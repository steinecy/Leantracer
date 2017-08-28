package leantracer.tasklist;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import leantracer.tables.TaskList;

public class TaskListDetailsModel extends AbstractTableModel {

	/**
	 * Controls the view and the model of the module task list
	 */
	private static final long serialVersionUID = -9151098122204142082L;
	private List<TaskList> tasklist;
	private Logger logger = LogManager.getLogger();
	
	private static final int AUFGABE_ID_COL = 0;
	private static final int AUFGABE_BEZ_COL = 1;
	private static final int DATUM_COL = 2;
	private static final int ZEITDAUER_COL = 3;
	private static final int BENUTZER_ID_COL = 4;
	private static final int KATEGORIE_ID_COL = 5;
	private static final int KATEGORIE_BEZ_COL = 6;
	private static final int PROJEKT_ID_COL = 7;
	private static final int PROJEKT_BEZ_COL = 8;
	private static final int SYSTEM_ID_COL = 9;
	private static final int STANDARDAUFGABE_ID_COL = 10;

	private String[] columnNames = { "Aufgabe_ID", "Aufgabe", "Datum", "Zeitdauer",
			"Benutzer_ID", "Kategorie_ID", "Kategorie", "Projekt_ID", "Projekt", "SAP SID", "Standardaufgabe_ID" };

	public TaskListDetailsModel() {}
	
	public List<TaskList> getTaskList(){
		return tasklist;
	}
	
	public void setTaskList(List<TaskList> tasklist){
		this.tasklist = tasklist;
	}
	
    /**
     *
     *  @param  aValue   value to assign to cell
     *  @param  rowIndex   row of cell
     *  @param  columnIndex  column of cell
     */
    public void setValueAt(Object newValue, int row, int col) {
    	logger.info("Holen der Werte von Reihe " + row + ", Spalte " + col);
    	TaskList tempTaskList = tasklist.get(row);
    	logger.info("Das aus der ArrayList geholte TaskList Objekt hat die Werte: " + tempTaskList.toString());

    	switch (col) {
    	case AUFGABE_ID_COL:
    		logger.info("tempTaskList.getAufgabe_id() = " + tempTaskList.getAufgabe_id());
    		tempTaskList.setAufgabe_id((int) newValue);
    		break;
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
    		tempTaskList.setZeitdauer((BigDecimal) newValue);
    		break;
    	case BENUTZER_ID_COL:
    		logger.info("tempTaskList.getBenutzer_id() = " + tempTaskList.getBenutzer_id());
    		tempTaskList.setBenutzer_id((int) newValue);
    		break;
    	case KATEGORIE_ID_COL:
    		logger.info("tempTaskList.getKategorie_id() = " + tempTaskList.getKategorie_id());
    		tempTaskList.setKategorie_id((int) newValue);
    		break;
    	case KATEGORIE_BEZ_COL:
    		logger.info("tempTaskList.getKategorie_bez() = " + tempTaskList.getKategorie_bez());
    		tempTaskList.setKategorie_bez((String) newValue);
    		break;
    	case PROJEKT_ID_COL:
    		logger.info("tempTaskList.getProjekt_id() = " + tempTaskList.getProjekt_id());
    		tempTaskList.setProjekt_id((int) newValue);
    		break;
    	case PROJEKT_BEZ_COL:
    		logger.info("tempTaskList.getProjekt_bez() = " + tempTaskList.getProjekt_bez());
    		tempTaskList.setProjekt_bez((String) newValue);
    		break;
    	case SYSTEM_ID_COL:
    		logger.info("tempTaskList.getSystem_id() = " + tempTaskList.getSystem_id());
    		tempTaskList.setSystem_id((String) newValue);
    		break;
    	case STANDARDAUFGABE_ID_COL:
    		logger.info("tempTaskList.getStandardaufgabe_id() = " + tempTaskList.getStandardaufgabe_id());
    		tempTaskList.setStandardaufgabe_id((int) newValue);
    		break;
    	default:
    		logger.info("Nicht gefunden, Default: tempTaskList.getAufgabe_bez() = " + tempTaskList.getAufgabe_bez());
    		tempTaskList.setAufgabe_bez((String) newValue);
    		break;
    	}
	
    	tasklist.add(row, tempTaskList);
    	fireTableCellUpdated(row, col);
    }
	
	public Logger getLogger(){
		return logger;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return tasklist.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	// This method fills every single table cells of the JTable with the correct value to display)
	public Object getValueAt(int row, int col) {
        logger.info("Holen der Werte von Reihe " + row + ", Spalte " + col);
		TaskList tempTaskList = tasklist.get(row);
		logger.info("Das aus der ArrayList geholte TaskList Objekt hat die Werte: " + tempTaskList.toString());

		switch (col) {
		case AUFGABE_ID_COL:
			logger.info("tempTaskList.getAufgabe_id() = " + tempTaskList.getAufgabe_id());
			return tempTaskList.getAufgabe_id();
		case AUFGABE_BEZ_COL:
			logger.info("tempTaskList.getAufgabe_bez() = " + tempTaskList.getAufgabe_bez());
			return tempTaskList.getAufgabe_bez();
		case DATUM_COL:
			logger.info("tempTaskList.getDatum() = " + tempTaskList.getDatum());
			return tempTaskList.getDatum();
		case ZEITDAUER_COL:
			logger.info("tempTaskList.getZeitdauer() = " + tempTaskList.getZeitdauer());
			return tempTaskList.getZeitdauer();
		case BENUTZER_ID_COL:
			logger.info("tempTaskList.getBenutzer_id() = " + tempTaskList.getBenutzer_id());
			return tempTaskList.getBenutzer_id();
		case KATEGORIE_ID_COL:
			logger.info("tempTaskList.getKategorie_id() = " + tempTaskList.getKategorie_id());
			return tempTaskList.getKategorie_id();
		case KATEGORIE_BEZ_COL:
			logger.info("tempTaskList.getKategorie_bez() = " + tempTaskList.getKategorie_bez());
			return tempTaskList.getKategorie_bez();
		case PROJEKT_ID_COL:
			logger.info("tempTaskList.getProjekt_id() = " + tempTaskList.getProjekt_id());
			return tempTaskList.getProjekt_id();
		case PROJEKT_BEZ_COL:
			logger.info("tempTaskList.getProjekt_bez() = " + tempTaskList.getProjekt_bez());
			return tempTaskList.getProjekt_bez();
		case SYSTEM_ID_COL:
			logger.info("tempTaskList.getSystem_id() = " + tempTaskList.getSystem_id());
			return tempTaskList.getSystem_id();
		case STANDARDAUFGABE_ID_COL:
			logger.info("tempTaskList.getStandardaufgabe_id() = " + tempTaskList.getStandardaufgabe_id());
			return tempTaskList.getStandardaufgabe_id();
		default:
			logger.info("Nicht gefunden, Default: tempTaskList.getAufgabe_bez() = " + tempTaskList.getAufgabe_bez());
			return tempTaskList.getAufgabe_bez();
		}
	}

}
