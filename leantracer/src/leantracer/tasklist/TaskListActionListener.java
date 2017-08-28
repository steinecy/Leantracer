package leantracer.tasklist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import org.jdatepicker.impl.JDatePanelImpl;

public class TaskListActionListener implements ActionListener {
	private TaskListController tasklistController;
	
	public TaskListActionListener(TaskListController tasklistController) {
		this.tasklistController = tasklistController;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
		JDatePanelImpl tempDatePickerImpl = (JDatePanelImpl)e.getSource();
		Calendar tempCalendar = (Calendar)tempDatePickerImpl.getModel().getValue();
		System.out.println(tempCalendar.toString());
		tasklistController.displayDateChanged(tempCalendar);	
	}

}
