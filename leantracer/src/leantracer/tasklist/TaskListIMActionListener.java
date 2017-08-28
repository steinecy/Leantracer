package leantracer.tasklist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * Is an action listener for task list tables pop up menu items. Calls the controller to execute the appropriate
 * action, corresponding to the table pop up menu item command.
 *
 */
public class TaskListIMActionListener implements ActionListener {
	private TaskListController tasklistController;
	
	public TaskListIMActionListener(TaskListController tasklistController) {
		this.tasklistController = tasklistController;
	}
	
	/**
	 * Identifies the JTable index and the pop up menu item based on the menu item name and calls
	 * the appropriate reaction method in controller
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem tempItem = (JMenuItem)e.getSource();
		String itemName = tempItem.getName();
		if (itemName.contains("delete")) {
			String substring = itemName.substring(10);
			int index = new Integer(substring);
			tasklistController.deleteItem(index);	
		}
		if (itemName.contains("details")) {
			String substring = itemName.substring(11);
			int index = new Integer(substring);
			tasklistController.displayDetails(index);
		}	
	}
}
