package leantracer.tasklist;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TaskListChangeListener implements ChangeListener {
	private TaskListController tasklistController;
	
	public TaskListChangeListener(TaskListController tasklistController) {
		this.tasklistController = tasklistController;
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e){
		System.out.println("Tabbed Pane Changed event: " + e.toString());
		JTabbedPane tempTabPane = (JTabbedPane)e.getSource();
		int index = tempTabPane.getSelectedIndex();
		tasklistController.setDatePicker(index);
	}

}
