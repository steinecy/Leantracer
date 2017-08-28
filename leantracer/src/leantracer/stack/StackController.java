package leantracer.stack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leantracer.login.ConnectionModel;
import leantracer.tables.Stack;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Controls the view, the model and the data access object of the module stack. Creates the view, the data model and the 
 * data access object. Makes the data access object to populated the data model with data. Creates the View and makes it 
 * display the data of the data model. Starts time tracking. Acts as an action listener and as a window listener of the 
 * various GUI elements of the View. Reacts appropriate to user interaction with the various GUI elements of the view.
 * Orchestrates the various tasks that needs to be processed after a user interaction.
 */
public class StackController implements ActionListener, WindowListener {
	
	private StackDAO stackDAO;
	private Logger logger = LogManager.getLogger();
	private StackModel stackModel;
	private StackView stackView;
	private ConnectionModel connectionModel;
	private Calendar calendar;
	private Date date;
	
	public StackController(ConnectionModel connectionModel) {		
		logger.info(this.toString() + "was called..");
		this.connectionModel = connectionModel;
		calendar = connectionModel.getCalendar();
		date = calendar.getTime();
		StackModel stackModel = new StackModel(this);		
		this.stackModel = stackModel;
		stackDAO = new StackDAO(connectionModel);
		stackView = new StackView(stackModel);
		// Read data from table "stack", put data into data model, start to track time for active task and display
	    // active task and data from table "stack". Be aware that there might be no active task in case table "stack"
		// is empty.
		stackModel.setStackList(stackDAO.getStack());
		stackModel.startActiveTask();	
		stackView.showStack(this);
	}


	/**
	 * Overrides the actionPeformed() method since this object implements an action listener. Depending on which GUI 
	 * element of the View the user interacted with, a different reaction method is called. Detects if formatted text
	 * field "new" was edited or if one of the buttons or menu items was clicked and calls the appropriate action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().toString().contains("javax.swing.JFormattedTextField")) {
			newButtonAction();			
		}
		if (e.getSource().toString().contains("javax.swing.JButton")) {
			if (e.getActionCommand().equals("Neu")) {
				newButtonAction();
			}
			if (e.getActionCommand().equals("Pausieren")) {
				pauseButtonAction();
			}
			if (e.getActionCommand().equals("Abschliessen")) {
				closeButtonAction();
			}
			if (e.getActionCommand().equals("Stoppen")) {
				stopButtonAction();
			}
			if (e.getActionCommand().equals("Alle Schliessen")) {
				closeAllButtonAction();
			}
		}
		if (e.getSource().toString().contains("javax.swing.JMenuItem")) {
			if (e.getActionCommand().equals("Aktivieren")) {
				activateItemAction();
			}
			if (e.getActionCommand().equals("Abschliessen")) {
				closeItemAction();
			}
			if (e.getActionCommand().equals("Stoppen")) {
				stopButtonAction();
			}
			if (e.getActionCommand().equals("Alle Schliessen")) {
				closeAllButtonAction();
			}
			if (e.getActionCommand().equals("Pausieren")) {
				pauseButtonAction();
			}
		}
	}

	
	/**
	 * Calculates elapsed time of an active task. Updates the time span of an active task. Saves the active task with 
	 * update time span in the stack table. Gets designation of new task from the formatted text field "new". Creates a 
	 * time stamp as a designation for a new task if formatted text field "new" is an empty String. Saves the newly created
	 * task with highest stack number in table "stack". Refreshes view and thus sets new task as new active task, starts 
	 * time measurement for new active task and pauses the former active task.
	 */
	private void newButtonAction() {
		String labelText = stackView.getNewLabelText();
		logger.info("User entered the following new Task to GUI: " + labelText);
		// For the convenience of the user, he can create a new task without a designation, a time stamp is then generated
		// to name the new task, therefore here is checked if the label of a new task is empty and if a time stamp needs
		// to be created.
		if (labelText.length() == 0) {
			Calendar tempCalendar = Calendar.getInstance();
			Date tempDate = tempCalendar.getTime();
			String datestring = new SimpleDateFormat("EEE, dd.MM.yyyy, HH:mm:ss").format(tempDate);
			labelText = datestring;
			logger.info("Empty Labeltext was changed to Timestamp: " + labelText);
		}
		updateAndSaveActiveTask();
		// the new task Stack object is created and written to the database
		Stack newTask = generateNewTask(labelText);
		stackDAO.createStackTableRecord(newTask);
		// since the new task will become the new active task we have to clear the new task text field in order
		// for it to be ready for the next user input
		stackView.setNewLabelText("");
		// the data is reloaded from the database and GUI is updated with latest data and redrawn
		updateModelAndView(true);
	}
	
	
	/**
	 * Calculates elapsed time. Updates time span of active task. Swaps stack number between active task and task on top of
	 * stack. Saves active task with update time span and swapped stack number in table "stack". Refreshes view and thus sets
	 * last paused task on top of stack as new active task, starts time measurement for new active task and pauses the former
	 * active task. If there is no active task, the pause button is disabled and cannot be pressed, thus this method cannot be
	 * called.
	 */
	private void pauseButtonAction() {
		// If the stack list is empty there is nothing to swap with, in this special case, the pause action actually
		// is a stop action.
		if (stackModel.isEmpty()) {
			stopButtonAction();
		} else {
			stackModel.updateTimeSpan();
			stackDAO.updateStackTable(swapStackNo());
			updateModelAndView(true);
		}
	}


	/**
	 * Calculates elapsed time. Updates time span of active task. Saves active task with update time span in table "tasklist".
	 * Deletes the active task (which was on top of table "stack") from table "stack". Refreshes view and thus sets the task
	 * which was second on stack as the new active task.
	 */
	private void closeButtonAction() {
		// if there is no active task, the close button and menu item is disabled and method cannot be called, that is why
		// it is not necessary to check whether active task exists
		stackModel.updateTimeSpan();
		stackDAO.createTaskListTableRecord(stackModel.getActiveTask());
		stackDAO.deleteStackTableRecord(stackModel.getActiveTask());
		updateModelAndView(true);	
	}
	
	
	/**
	 * Calculates elapsed time. Updates time span of active task. Saves active task with update time span in stack table 
	 * Refreshes view but does not start a active task, thus measuring time is stopped. If there is no active task, the
	 * stop button is deactivated and method cannot be called from GUI
	 */
	private void stopButtonAction() {
		updateAndSaveActiveTask();
		updateModelAndView(false);
	}
	
	
	/**
	 * Calculates elapsed time. Updates time span of active task. Saves active task with update time span in stack table 
	 * Refreshes view, since this method closes all task, view is empty after refresh. If there is no active task and if
	 * task list is empty, the close all button is deactivated and this method cannot be called from GUI.
	 */
	private void closeAllButtonAction() {
		// If there is any, the active task is updated and closed, to make shure really all
		// records are closed and saved to stack list.
		stopButtonAction();
		// Stack list is saved to task list and all records are deleted from stack list, but only if 
		// stack list is not empty.
		if (!stackModel.isEmpty()) {
			stackDAO.createTaskListTable(stackModel.getStackList());
			stackDAO.deleteStackTable(stackModel.getStackList());
			updateModelAndView(false);
		}
	}
	
	
	/**
	 * Calculates elapsed time. Updates time span of active task. Sets stack number of selected task as top stack number.
	 * Saves active task with update time span and unchanged stack number in table "stack". Refreshes view and thus sets
	 * selected task from stack list as new active task, starts time measurement for new active task and pauses the former
	 * active task. If stack list is empty and there are no paused tasks, context pop up menu cannot be called and thus this
	 * method cannot be called.
	 */
	private void activateItemAction() {
		// If stack list is empty, the pop up menu cannot be called, therefore this action can only be called if there
		// exists at least one task list entry, therefore there is no need to check for an empty task list.
		updateAndSaveActiveTask();
		int rowindex = stackView.getSelectedRow();
		Stack tempStack = stackModel.getStackList().get(rowindex);
		tempStack.setStack_nr(getTopStackNo() + 1);
		stackDAO.updateStackTableRecord(tempStack);
		updateModelAndView(true);	
	}
	
	
	/**
	 * Calculates elapsed time. Updates time span of active task. Saves active task with updated time span in stack table.
	 * Deletes the selected task from table "stack". Refreshes view and thus restarts measuring elapsed time of the former
	 * active task. If task list is empty an there are no paused task, context pop menu on stack list cannot be called and
	 * thus this method cannot be called.
	 */
	private void closeItemAction() {
		updateAndSaveActiveTask();
		int rowindex = stackView.getSelectedRow();
		// we only have to close a paused task if a row is selected, if no row is selected row index is -1
		if (!(rowindex == -1)) {
			List<Stack> tempStackList = stackModel.getStackList();
			Stack tempStack = tempStackList.get(rowindex);
			stackDAO.createTaskListTableRecord(tempStack);
			stackDAO.deleteStackTableRecord(tempStack);
			updateModelAndView(stackModel.doesActiveExists());
		}
	}

	
	/**
	 * Saves a changed task designation edited on task list in the GUI to the database. This method is called by the
	 * table model if the "setTableValueAt()" method is called from the table model by the JTable component.
	 */
	public void tableCellChanged(Stack tempStack) {
		stackDAO.updateStackTableRecord(tempStack);
		updateModelAndView(stackModel.doesActiveExists());	
	}
	

	/**
	 * Calculates elapsed time. Updates time span of active task. Saves active task with update time span in stack table 
	 * Refreshes view but does not start a active task, thus measuring time is stopped. If a user closes the window and
	 * thus terminates the application while there is still an active task running, there needs to be cared for that the
	 * elapsed time of the active task is saved to the database before the application is terminated.
	 */
	private void closeWindowAction() {
		updateAndSaveActiveTask();
		//System.exit(0);	
	}

	
	/**
	 * Calculates the elapsed time of the active task. Updates time span of active task. Saves the active task record 
	 * with updated time span in the stack table. Checks if active task exists before executing anything to avoid null
	 * pointer exceptions. If no active task exists, no action is taken.
	 */
	private void updateAndSaveActiveTask() {
		// if there is an active task its elapsed time is calculate and the update is saved to the database
		// however we can update the active task only if there exists one (otherwise null pointers exceptions are thrown)
		// therefore we need to check for existence of an active task before
		if (stackModel.doesActiveExists()) {
			stackModel.updateTimeSpan();
			stackDAO.updateStackTableRecord(stackModel.getActiveTask());
		}
	}

	
	/**
	 * Causes the data access object to fetch data from table "stack". Updates data model with recent data. If desired,
	 * sets the task on top of stack list as the active task. If not desired, no task is set as active and solely the
	 * display of the task list is refreshed. Repaints the GUI.
	 */
	private void updateModelAndView(boolean state) {
		logger.info("updateModelAndView was called, screen should refresh ...");
		stackModel.setStackList(stackDAO.getStack());
		if (state) {
			// Be aware that stack list might be empty. However method startActiveTask() in table model is taking care
			// of this special case, therefore there is no need to deal with it at this place.
			stackModel.startActiveTask();
			stackView.setActiveLabelText(stackModel.getActiveLabel());
		}
		stackView.redraw();		
	}

	
	/**
	 * Swaps stack number between active task and top paused task. Returns a list with two Stack objects that need to
	 * be updated on the database. Is called by the pause action in order to swap active task with top paused task.
	 * @return updateList the list with the two Stack objects to be updated in the database
	 */
	private List<Stack> swapStackNo() {
		// this method is only called if active task exists therefore it 
        // is not necessary to check for presence of an active task
		Stack tempStack = stackModel.getStackList().get(0);
		Stack activeStack = stackModel.getActiveTask();
		logger.info("Before: activeStack Stack Nr. = " + activeStack.getStack_nr() 
				    + ", tempStack Stack Nr. =" + tempStack.getStack_nr());
		int secondStackNo = tempStack.getStack_nr();
		activeStack.setStack_nr(secondStackNo);
		tempStack.setStack_nr(secondStackNo + 1);
		logger.info("After: activeStack Stack Nr. = " + activeStack.getStack_nr() 
		            + ", tempStack Stack Nr. =" + tempStack.getStack_nr());
		List<Stack> updateList = new ArrayList<Stack>();
		updateList.add(activeStack);
		updateList.add(tempStack);
		return updateList;
	}
	
	
	/**
	 * Creates an new Task Stack object with proper designation and proper stack number.
	 * @param labelText the designation of the new Task
	 * @return the new task to be stored in the database
	 */
	private Stack generateNewTask(String labelText) {
		Stack newTask = new Stack();
		newTask.setStackaufgabe_bez(labelText);	
		newTask.setBenutzer_id(connectionModel.getUserID());
		BigDecimal zeitdauer = BigDecimal.ZERO;
		newTask.setZeitdauer(zeitdauer);
		newTask.setDatum(date);
		newTask.setStack_nr((getTopStackNo() + 1));
		return newTask;
	}
		
	
	/**
	 * Returns the current highest stack number. If stack list empty zero is returned as highest stack number.
	 * @param labelText the designation of the new task
	 * @return the new task as a Stack object to be stored in the database
	 */
	private int getTopStackNo() {
		List<Stack> tempStack = stackDAO.getStack();
		int topStackNo = -1;
		if (!tempStack.isEmpty()) {
			topStackNo = tempStack.get(0).getStack_nr();
		} else {
			topStackNo = 0;
		}
		return topStackNo;
	}

	
	/**
	 * Overrides a Window Listener method, however this method is not used by StackController.
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// do nothing, event not needed	
	}


	@Override
	/**
	 * If the window is closed, active task needs to be updated and saved, comparable to the stop button operation.
	 * Only if this is done, application can be exited.
	 */
	public void windowClosing(WindowEvent e) {
		JFrame tempFrame = (JFrame)e.getSource();
		closeWindowAction();
		tempFrame.dispose();
	}

	
	/**
	 * Overrides a Window Listener method, however this method is not used by StackController.
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		// do nothing, event not needed	
	}

	
	/**
	 * Overrides a Window Listener method, however this method is not used by StackController.
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// do nothing, event not needed		
	}

	
	/**
	 * Overrides a Window Listener method, however this method is not used by StackController.
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// do nothing, event not needed		
	}

	
	/**
	 * Overrides a Window Listener method, however this method is not used by StackController.
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// do nothing, event not needed		
	}

	
	/**
	 * Overrides a Window Listener method, however this method is not used by StackController.
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// do nothing, event not needed		
	}	
}
