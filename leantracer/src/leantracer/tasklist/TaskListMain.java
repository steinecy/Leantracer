package leantracer.tasklist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leantracer.login.ConnectionModel;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * 
 * Is the main application of module task list. It creates the view and the model of the module task list. It creates
 * the controller of module task list and adds the view and the model to it. Since the view has to display the data of
 * five different weekdays, in fact five models are instantiated, one model for each weekday.
 *
 */
public class TaskListMain {
	
	private Logger logger = LogManager.getLogger();
	private TaskListDisplayModel[] tasklistDisplayModels = new TaskListDisplayModel[5];

	public TaskListMain(ConnectionModel connectionModel) {
		
		logger.info("Der " + this.getClass().toString() + "-Kostruktor wurde aufgerufen..");		
		setUpModels();
		TaskListView tasklistview = new TaskListView(tasklistDisplayModels, connectionModel);
		new TaskListController(connectionModel, tasklistDisplayModels, tasklistview);

	}
	
	
	/**
	 * Instantiates five weekday models used in the view and the controller, where one model represents one of the five
	 * weekdays Monday, Tuesday, Wednesday, Thursday and Friday. Creates a model array consisting of five models where by 
	 * definition, Monday corresponds to array no. 0, Tuesday to no. 1, Wednesday to no. 2, Thursday to no. 3, and Friday 
	 * to no. 4.
	 */
	public void setUpModels() {
	
		for (int i=0; i<5; i++) {
		
			tasklistDisplayModels[i] = new TaskListDisplayModel();
			logger.info("Das TaskListModel \"tasklistModels[" + i + "]\" wurde erzeugt..");
		
		}
	}
	

}
