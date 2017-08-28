package leantracer.masterdata;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import leantracer.tables.Authorization;
import leantracer.tables.Category;
import leantracer.tables.Landscape;
import leantracer.tables.Project;
import leantracer.tables.Role;
import leantracer.tables.SAPSystem;
import leantracer.tables.StandardTask;
import leantracer.tables.User;

/**
 * @author Cyrill Steiner
 * @version 1.0
 * Is the data model of the master data view. It contains the master data that needs to be displayed in the
 * form of table record object array lists. It creates string arrays from the array lists which serve as data 
 * model for the JComboBoxes present in the view. It contains the currently selected user name, that was 
 * selected by the user. The currently selected user is used in the master data DAOs to select for the desired
 * data.
 */
public class MasterDataModel {
	private Logger logger = LogManager.getLogger();
	private List<StandardTask> standardTaskList;	
	private List<Category> categoryList;
	private List<Project> projectList;
	private List<SAPSystem> systemList;
	private List<User> userList;
	private List<Role> roleList;
	private List<Authorization> authorizationList;
	private List<Landscape> landscapeList;
	private ArrayList<DefaultComboBoxModel<String>> comboBoxModelArrayList;
	private String[] categoryArray = {""};
	private String[] projectArray = {""};
	private String[] systemArray = {""};
	private String[] userArray = {""};
	private String[] roleArray = {""};
	private String[] authorizationArray = {""};
	private String[] landscapeArray = {""};
	private boolean adminMode;
	private String selectedUserName;
	private int selectedUserID;
	private int selectedUserIndex;
	
	public MasterDataModel(String activeUserName, int activeUserID) {
		this.selectedUserName = activeUserName;
		this.selectedUserID = activeUserID;
		adminMode = true;
		selectedUserIndex=0;
	}
	
	
	/**
	 * Setter method for the array list standard task list.
	 * @param standardTaskList the array list containing the table rows 
	 */
	public void setStandardTaskList(List<StandardTask> standardTaskList) {
		this.standardTaskList = standardTaskList;
		toStandardTaskArrays();
	}
	
	
	/**
	 * Getter method for the array list standard task list.
	 * @param standardTaskList the array list containing the table rows 
	 */
	public List<StandardTask> getStandardTaskList() {
		return standardTaskList;		
	}

	
	/**
	 * Getter method for the arrays of standard task for combo boxes of view.
	 * @param String[] the string array containing the column values for a certain combo box
	 */
	public DefaultComboBoxModel<String> getStandardTaskModels(int i) {
		if (!comboBoxModelArrayList.isEmpty()) {
			return comboBoxModelArrayList.get(i);
		} else {
			String[] tempStringArray = {""};
			return new DefaultComboBoxModel<String>(tempStringArray);
		}	
	}

	
	/**
	 * Fills the values of the standard task list into several String Arrays
	 */
	public void toStandardTaskArrays() {
		comboBoxModelArrayList = new ArrayList<DefaultComboBoxModel<String>>();
		int size = standardTaskList.size();
		String[] tempStandardTaskArray = new String[size];
		String[] tempStandardTaskCategoryArray = new String[size];
		String[] tempStandardTaskProjectArray = new String[size];
		String[] tempStandardTaskSystemArray = new String[size];
		String[] tempStandardTaskUserArray = new String[size];
		StandardTask tempStandardTask = null;
		for (int i=0; i < standardTaskList.size(); i++) {
			tempStandardTask = standardTaskList.get(i);
			tempStandardTaskArray[i] = ("" + tempStandardTask.getStandardaufgabe_bez());
			tempStandardTaskCategoryArray[i] = tempStandardTask.getKategorie_bez();
			tempStandardTaskProjectArray[i] = tempStandardTask.getProjekt_bez();
			tempStandardTaskSystemArray[i] = tempStandardTask.getSystem_id();
			tempStandardTaskUserArray[i] = tempStandardTask.getBenutzername();
		}
		comboBoxModelArrayList.add(new DefaultComboBoxModel<String>(tempStandardTaskArray));
		comboBoxModelArrayList.add(new DefaultComboBoxModel<String>(tempStandardTaskCategoryArray));
		comboBoxModelArrayList.add(new DefaultComboBoxModel<String>(tempStandardTaskProjectArray));
		comboBoxModelArrayList.add(new DefaultComboBoxModel<String>(tempStandardTaskSystemArray));
		comboBoxModelArrayList.add(new DefaultComboBoxModel<String>(tempStandardTaskUserArray));				
	}

	
	/**
	 * Setter method for the array list category list.
	 * @param categoryList the array list containing the table rows 
	 */
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;		
	}
	
	
	/**
	 * Getter method for the array list category list.
	 * @param categoryList the array list containing the table rows 
	 */
	public List<Category> getCategoryList() {
		return categoryList;		
	}
	
	
	/**
	 * Setter method for the array list project list.
	 * @param projectList the array list containing the table rows 
	 */
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;		
	}
	
	
	/**
	 * Getter method for the array list project list.
	 * @param projectList the array list containing the table rows 
	 */
	public List<Project> getProjectList() {
		return projectList;		
	}
	
	
	/**
	 * Setter method for the array list category list.
	 * @param categoryList the array list containing the table rows 
	 */
	public void setSystemList(List<SAPSystem> systemList) {
		this.systemList = systemList;		
	}
	
	
	/**
	 * Getter method for the array list category list.
	 * @param categoryList the array list containing the table rows 
	 */
	public List<SAPSystem> getSystemList() {
		return systemList;		
	}

	
	/**
	 * Setter method for the array list user list.
	 * @param userList the array list containing the table rows 
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
		toUserArrays();
	}
	
	
	/**
	 * Getter method for the array list user list.
	 * @param userList the array list containing the table rows 
	 */
	public List<User> getUserList() {
		return userList;		
	}
	
	
	/**
	 * Fills the values of the user list into a string array which serves as a data model for a JComboBox.
	 * Is called by user list setter method in order to refresh user string array when user list is refreshed
	 */
	public void toUserArrays() {
		int size = userList.size();
		logger.info("Methode toUserArrays(), user list size = " + size);
		User tempUser = null;
		if (!userList.isEmpty()) {
			userArray = new String[size];
			for (int i=0; i < size; i++) {				
				tempUser = userList.get(i);
				userArray[i] = tempUser.getBenutzername();
				logger.info("Methode toUserArrays(), userArray[" + i + "] = " + userArray[i]);
			}
			determineSelectedUserIndex();
		}
	}
	
	
	/**
	 * Getter method for the user JComboBox data model
	 * @return userCBModel the user JComboBox data model
	 */
	public DefaultComboBoxModel<String> getUserCBModel() {
		DefaultComboBoxModel<String> userCBModel = new DefaultComboBoxModel<String>(userArray);
		return userCBModel;
	}
	
	
	/**
	 * Determines from user name which user array index needs to be displayed in selected user JComboBox
	  @return userArray the user names string array
	 */
	private void determineSelectedUserIndex() { 
		for (int i = 0; i < userArray.length; i++) {
			if (userArray[i].equals(selectedUserName)) {
				logger.info("Methode determineSelectedUserIndex(), userArray[" + i +"].equals(" + selectedUserName + ")");
				selectedUserIndex = i;
			}
		}
	}
	
	
	/**
	 * Getter method for the selected user index.
	 * @param roleList the array list containing the table rows 
	 */
	public int getSelectedUserIndex() {
		return selectedUserIndex;		
	}
	
	
	/**
	 * Setter method for the selected user index. Is called from Controller when user changes selected user
	 * from JComboBox, resets userID and user name initially provided from connection data model.
	 * @param roleList the array list containing the table rows 
	 */
	public void setSelectedUserIndex(int index) {
		User tempUser = userList.get(index);
		selectedUserIndex = index;
		selectedUserName = tempUser.getBenutzername();
		selectedUserID = tempUser.getBenutzer_id();
		
	}
	
	
	/**
	 * Setter method for the array list role list.
	 * @param roleList the array list containing the table rows 
	 */
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;		
	}
	
	
	/**
	 * Getter method for the array list role list.
	 * @param roleList the array list containing the table rows 
	 */
	public List<Role> getRoleList() {
		return roleList;		
	}
	
	
	/**
	 * Setter method for the array list authorization list.
	 * @param authorizationList the array list containing the table rows 
	 */
	public void setAuthorizationList(List<Authorization> authorizationList) {
		this.authorizationList = authorizationList;		
	}
	
	
	/**
	 * Getter method for the array list authorization list.
	 * @param authorizationList the array list containing the table rows 
	 */
	public List<Authorization> getAuthorizationList() {
		return authorizationList;		
	}
	
	
	/**
	 * Setter method for the array list category list.
	 * @param categoryList the array list containing the table rows 
	 */
	public void setLandscapeList(List<Landscape> landscapeList) {
		this.landscapeList = landscapeList;		
	}
	
	
	/**
	 * Getter method for the array list category list.
	 * @param categoryList the array list containing the table rows 
	 */
	public List<Landscape> getSLandscapeList() {
		return landscapeList;		
	}
	
	
	/**
	 * Setter method to set the name of the selected user
	 * @param activeUserName the name  of the selected user
	 */
	public void setSelectedUserName(String selectedUserName) {
		this.selectedUserName = selectedUserName;
	}
	
	
	/**
	 * Getter method to return the name of the selected user
	 * @param selectedUserName the name  of the selected user
	 */
	public String getSelectedUserName() {
		return selectedUserName;		
	}
	
	
	/**
	 * Setter method to set the name of the selected user
	 * @param selectedUserName the name  of the selected user
	 */
	public void setSelectedUserID(int selectedUserID) {
		this.selectedUserID = selectedUserID;
	}
	
	
	/**
	 * Getter method to return the name of the selected user
	 * @param selectedUserName the name  of the selected user
	 */
	public int getSelectedUserID() {
		return selectedUserID;		
	}
}



