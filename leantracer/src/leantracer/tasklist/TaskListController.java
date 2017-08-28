package leantracer.tasklist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leantracer.login.ConnectionModel;
import leantracer.shared.WeekDays;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Controls the view and the model of the module task list. Initializes the data models and creates their data access objects.
 * Adds a table model listener to each data model. Calls a database update if table model listener detects an change event.
 * Updates the view with new data if database was changed.
 */
public class TaskListController {
	
	private ConnectionModel connectionModel;
	private TaskListDisplayModel[] tasklistDisplayModels;
	private TaskListView tasklistView;
	private TaskListDAO[] tasklistDAOs = new TaskListDAO[5];
	private Logger logger = LogManager.getLogger();
	private Calendar[] weekDays;
	private Calendar displayedDayOfWeek;
	
	public TaskListController (ConnectionModel connectionModel, TaskListDisplayModel[] tasklistDisplayModels, 
			                   TaskListView tasklistView) {
		this.connectionModel = connectionModel;
		this.tasklistDisplayModels = tasklistDisplayModels;
		this.tasklistView = tasklistView;
		TaskListActionListener tasklistActionListener = new TaskListActionListener(this);
		TaskListChangeListener tasklistChangeListener = new TaskListChangeListener(this);
		displayedDayOfWeek = connectionModel.getCalendar();
		generateWeekdays(displayedDayOfWeek);
		initializeDisplayModels();
		addTableModelListener();
		tasklistView.setCalendarWeekLabel(displayedDayOfWeek);
		tasklistView.showTaskList(new TaskListIMActionListener(this));
		setSelectedTab();
		tasklistView.addDatePickerAL(tasklistActionListener);
		tasklistView.addTabbedPaneCL(tasklistChangeListener);
	}

	
	/**
	 * Gets the dates of the five weekdays of the current week for a certain date.
	 */
	public void generateWeekdays(Calendar calendar) {
		logger.info("Weekdays will be generated for current date : " 
		            + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		WeekDays weekDaysGenerator = new WeekDays(calendar);
		weekDays = weekDaysGenerator.getWeekDayArray();
	}
	
	/**
	 * Initializes the five data models with their proper weekday date. Gets the current system date. Gets the dates of the 
	 * five weekdays of the current week. Sets the date of the weekday it represents to each data model. Creates a data
	 * access object DAO for each data model.
	 */
	public void initializeDisplayModels() {
		
//		Calendar calendar = connectionModel.getCalendar();
//		logger.info("Der TaskListController calendar hat den Wert : " 
//		            + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
//		WeekDays weekDaysGenerator = new WeekDays(calendar);
//		Calendar[] weekDays = weekDaysGenerator.getWeekDayArray();
		for (int i=0; i<5; i++) {
			tasklistDAOs[i] = new TaskListDAO(connectionModel, tasklistDisplayModels[i], 
					                                  weekDays[i]);
			tasklistDisplayModels[i].setWeekDay(i);
			logger.info("Das TaskListDisplayModel[" + i + "] wurde aktualisiert..");
		}
	}
	
	
//	/**
//	 * Resets the five data models to their proper weekday date. Gets the date from JDatePicker. Gets the dates of the 
//	 * five weekdays of the choosen date. Sets the date of the weekday it represents to each data model. Creates a data
//	 * access object DAO for each data model.
//	 */
//	public void resetDisplayModels(Calendar calendar) {
//		logger.info("Der TaskListController calendar wurde geändert auf : " 
//		            + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
//		WeekDays weekDaysGenerator = new WeekDays(calendar);
//		Calendar[] weekDays = weekDaysGenerator.getWeekDayArray();
//		for (int i=0; i<5; i++) {
//			tasklistDAOs[i] = new TaskListDAO(connectionModel, tasklistDisplayModels[i], 
//					                                  weekDays[i]);
//			tasklistDisplayModels[i].setWeekDay(i);
//			logger.info("Das TaskListDisplayModel[" + i + "] wurde aktualisiert..");
//		}
//		tasklistView.repaint();
//	}

	
	/**
	 * Adds a listener to each data model (table data model), the listener calls a database update that writes 
	 * the data changed in the data model to the database.
	 */
	public void addTableModelListener() {
		
		for (int i=0; i<5; i++) {
			tasklistDisplayModels[i].addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int row = e.getFirstRow();
					int column = e.getColumn();
					//int type = e.getType(); -> Returns the type of event - one of: INSERT, UPDATE and DELETE
					TaskListDisplayModel tasklistDisplayModel = (TaskListDisplayModel)e.getSource();
					Object data = tasklistDisplayModel.getValueAt(row, column);
					updateDatabase(row, column, tasklistDisplayModel);
					System.out.println("Table was changed ...");
					logger.info("TableModelListener wurde ausgelöst: "
							    + "Der Wert bei Reihe " + row + " und Spalte "
	        		            + column + "auf" + data.toString(), "Tabellenwert verändert", 0);
				}
			});
		}
	}

	/**
	 * Is called by the data model listener. Calls the database update or create method of the database access object
	 * corresponding to the data model that was changed. Calls the create method only if both values of a task,
	 * task designation and time amount are maintained by the user. Reloads database data into to the data model after
	 * database update. Repaints view after data model update.
	 * @param row
	 * @param column
	 * @param tasklistDisplayModel
	 */
	public void updateDatabase(int row, int column, TaskListDisplayModel tasklistDisplayModel) {
		int weekdayIndex = tasklistDisplayModel.getWeekDay();
		// if the first, empty row is updated, we have to wait until both values are maintained
		// before we create the new entry in the database
		if (row==0) {
			Object designation = tasklistDisplayModel.getValueAt(0,0);
			Object duration = tasklistDisplayModel.getValueAt(0,1);
			if ((designation!=null) && (duration!=null)) {
				tasklistDAOs[weekdayIndex].createInDatabase();
				tasklistDAOs[weekdayIndex].updateTaskList();
			}
		} else {
			// if the update is not from the first row, an existing database value was
			// edited and needs to be updated in the database
			tasklistDAOs[weekdayIndex].updateInDatabase(row);
			tasklistDAOs[weekdayIndex].updateTaskList();
		}
		tasklistView.repaint();
	}
	
	/**
	 * Is called by the date picker action listener. Changes displayed date and tabulators and initiates
	 * reload and displayof appropriate data.
	 */
	public void displayDateChanged(Calendar calendar){
		displayedDayOfWeek = calendar;
		generateWeekdays(calendar);
		initializeDisplayModels();
		tasklistView.setCalendarWeekLabel(displayedDayOfWeek);
		setSelectedTab();
		tasklistView.repaint();
	}
	
	
	/**
	 * Sets the selected tabulator of the five weekdays
	 */
	public void setSelectedTab() {
		int tabIndex = 0;
		int dayIndex = displayedDayOfWeek.get(Calendar.DAY_OF_WEEK);
		if (dayIndex == 1 || dayIndex == 7) {
			tabIndex = 4;
		} else {
			tabIndex = dayIndex - 2;
		}
		tasklistView.setSelectedTab(tabIndex);
		tasklistView.repaint();
	}
	
	
	/**
	 * Sets the DatePicker to the date selected by tabbed pane
	 */
	public void setDatePicker(int index) {
		displayedDayOfWeek = weekDays[index];
		tasklistView.setDatePicker(displayedDayOfWeek);	
		tasklistView.repaint();
	}
	
	
	/**
	 * Deletes the selected item from task list
	 */
	public void deleteItem(int index) {

	}
	
	
	/**
	 * Calls menu to maintain task details
	 */
	public void displayDetails(int index) {

	}
}
