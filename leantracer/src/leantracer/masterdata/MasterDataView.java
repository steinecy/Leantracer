package leantracer.masterdata;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Cyrill Steiner
 * @version 1.0
 * Is the graphical user interface, the view of the stack module. It displays the active task. It displays the paused tasks 
 * of the stack list. It enables the user to add a new task, to pause a current active task and to close a active or paused
 * task.
 */
public class MasterDataView {	
	private JFrame frame;
	private ImageIcon inselGroup;
	private Logger logger = LogManager.getLogger();
	private MasterDataModel masterdataModel;
	private MasterDataCBActionListener actionListenerCB;
	private JButton newPreallocationButton;
	private JButton deletePreallocationButton;
	private JButton newButtonStandardTask;
	private JButton changeButtonStandardTask;
	private JButton newButtonCategory;
	private JButton newButtonProject;
	private JButton newButtonSystem;
	private JButton newButtonLandscape;
	private JButton newButtonUser;
	private JButton changeButtonUser;
	private JButton newButtonRole;
	private JButton changeButtonRole;
	private JButton delRoleToUserButton;
	private JButton newRoleToUserButton;
	
	private JLabel selectedUsernameLabel;
	private JComboBox<String> userSelection;
	
	private JLabel taskPreallocationLabel;
	private JLabel durationLabel;
	private JComboBox<String> taskPreallocationSelection;
	private JFormattedTextField durationFTF;
	private JComboBox<String> secondTaskPreallocationSelection;
	private JTextField secondDurationText;
	
	private JLabel standardTaskLabel;
	private JLabel categoryLabel;
	private JLabel projectLabel;
	private JLabel systemLabel;
	private JLabel usernameLabel;
	
	private JComboBox<String> delStandardTaskSelection;
	private JTextField categoryText;
	private JTextField projectText;
	private JTextField systemText;
	private JTextField usernameText;
	
	private JFormattedTextField newStandardTaskFT;
	private JComboBox<String> categorySelection;
	private JComboBox<String> projectSelection;
	private JComboBox<String> systemSelection;
	private JComboBox<String> usernameSelection;
	
	private JFormattedTextField categoryFTF;
	private JFormattedTextField projectFTF;
	private JFormattedTextField systemFTF;
	private JFormattedTextField landscapeFTF;
	
	private JLabel secondCategoryLabel;
	private JLabel secondProjectLabel;
	private JLabel secondSystemLabel;
	private JLabel landscapeLabel;
	
	private JLabel secondUsernameLabel;
	private JLabel vornameLabel;
	private JLabel nachnameLabel;
	private JLabel sollArbeitszeitLabel;
	
	private JComboBox<String> secondUsernameSelection;
	private JFormattedTextField vornameFTF;
	private JFormattedTextField nachnameFTF;	
	private JFormattedTextField sollArbeitszeitFTF;
	
	private JLabel roleLabel;
	private JComboBox<String> rollenSelection;
	private JLabel thirdUsernameLabel;
	private JLabel secondRoleLabel;
	private JLabel rolesLabel;
	private JLabel fourthUsernameLabel;
	
	private JTextField secondUsernameText;
	private JComboBox<String> secondRollenSelection;
	private JTextField thirdUsernameText;
	private JComboBox<String> thirdRollenSelection;
	
	public MasterDataView(MasterDataModel masterdataModel, MasterDataCBActionListener actionListenerCB) {		
		logger.info(this.getClass().toString() + "constructor was called..");
		this.masterdataModel = masterdataModel;
		this.actionListenerCB = actionListenerCB;
		newPreallocationButton = new JButton();
		deletePreallocationButton = new JButton();
		newButtonStandardTask = new JButton();
		changeButtonStandardTask = new JButton();
		newButtonCategory = new JButton();
		newButtonProject = new JButton();
		newButtonSystem = new JButton();
		newButtonLandscape = new JButton();
		newButtonUser = new JButton();
		changeButtonUser = new JButton();
		newButtonRole = new JButton();
		changeButtonRole = new JButton();
		delRoleToUserButton = new JButton();
		newRoleToUserButton = new JButton();
		
		selectedUsernameLabel = new JLabel();
		userSelection = new JComboBox<String>();
		
		taskPreallocationLabel = new JLabel();
		durationLabel = new JLabel();
		taskPreallocationSelection = new JComboBox<String>();
		durationFTF = new JFormattedTextField();
		secondTaskPreallocationSelection = new JComboBox<String>();
		secondDurationText = new JTextField();
		
		standardTaskLabel = new JLabel();
		categoryLabel = new JLabel();
		projectLabel = new JLabel();
		systemLabel = new JLabel();	
		usernameLabel = new JLabel();
		
		delStandardTaskSelection = new JComboBox<String>();
		categoryText = new JTextField();
		projectText = new JTextField();
		systemText = new JTextField();
		usernameText = new JTextField();
		
		newStandardTaskFT = new JFormattedTextField();
		categorySelection = new JComboBox<String>();
		projectSelection = new JComboBox<String>();
		systemSelection = new JComboBox<String>();
		usernameSelection = new JComboBox<String>();
		
		categoryFTF = new JFormattedTextField();
		projectFTF = new JFormattedTextField();
		systemFTF = new JFormattedTextField();
		landscapeFTF = new JFormattedTextField();
		
		secondCategoryLabel = new JLabel();
		secondProjectLabel = new JLabel();
		secondSystemLabel = new JLabel();	
		landscapeLabel = new JLabel();
		
		secondUsernameLabel = new JLabel();
		vornameLabel = new JLabel();
		nachnameLabel = new JLabel();
		sollArbeitszeitLabel = new JLabel();
		
		secondUsernameSelection = new JComboBox<String>();
		vornameFTF = new JFormattedTextField();
		nachnameFTF = new JFormattedTextField();	
		sollArbeitszeitFTF = new JFormattedTextField();
		
		roleLabel = new JLabel();
		rollenSelection = new JComboBox<String>();
		thirdUsernameLabel = new JLabel();
		secondRoleLabel = new JLabel();
		
		fourthUsernameLabel = new JLabel();
		rolesLabel = new JLabel();
		
		secondUsernameText = new JTextField();
		secondRollenSelection = new JComboBox<String>();
		thirdUsernameText = new JTextField();
		thirdRollenSelection = new JComboBox<String>();

		// Create main window
		JFrame frame = new JFrame("Leantracer Stammdaten");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inselGroup = new ImageIcon("inselgruppe.png");
		frame.setIconImage(inselGroup.getImage());
		this.frame = frame;		
	}
	
	
	/**
	 * Creates the elements of the GUI and adds them to various panes and adds the panes to the frame.
	 * Adds the table model to the table. Adds an action listener to the GUI elements. Displays frame.
	 * @param masterdataModel the table model for the stack table to be displayed
	 * @param controller the action listener used to listen for formatted text field and button actions
	 */
	public void showStack(MasterDataActionListener masterDataActionListener) {
		
		logger.info("In der StackView wurde die Methode showStack aufgerufen..");
		//frame.addWindowListener((WindowListener)controller);
		
		// Creating a ContentPane, ScrollPane and all necessary panels and layouts
        Container contentPane = frame.getContentPane();
        JPanel centerPanel = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        centerPanel.setLayout(springLayout);
        centerPanel.setPreferredSize(new Dimension(1500, 800));
        JScrollPane scrollPane = new JScrollPane(centerPanel);	
		//scrollPane.setViewportView(centerPanel);		
        contentPane.add(BorderLayout.CENTER, scrollPane);
		
		// Creating buttons and adding action listeners
    	newPreallocationButton.setText("Neu");
    	newPreallocationButton.setName("newPreallocationButton");
    	deletePreallocationButton.setText("Löschen");
    	deletePreallocationButton.setName("deletePreallocationButton");
		newButtonStandardTask.setText("Neu");
		newButtonStandardTask.setName("newButtonStandardTask");
		changeButtonStandardTask.setText("Ändern");
		changeButtonStandardTask.setName("changeButtonStandardTask");
		newButtonCategory.setText("Neu");
		newButtonCategory.setName("newButtonCategory");
		newButtonProject.setText("Neu");
		newButtonProject.setName("newButtonProject");
		newButtonSystem.setText("Neu");
		newButtonSystem.setName("newButtonSystem");
		newButtonLandscape.setText("Neu");
		newButtonLandscape.setName("newButtonLandscape");
		newButtonUser.setText("Neu");
		newButtonUser.setName("newButtonUser");
		changeButtonUser.setText("Ändern");
		changeButtonUser.setName("changeButtonUser");
		newButtonRole.setText("Neu");
		newButtonRole.setName("newButtonRolle");
		changeButtonRole.setText("Ändern");
		changeButtonRole.setName("changeButtonRolle");
		delRoleToUserButton.setText("Zuweisung löschen");
		delRoleToUserButton.setName("delRoleToUserButton");
		newRoleToUserButton.setText("Neu zuweisen");
		newRoleToUserButton.setName("newRoleToUserButton");
				
		newPreallocationButton.addActionListener(masterDataActionListener);
    	deletePreallocationButton.addActionListener(masterDataActionListener);
		newButtonStandardTask.addActionListener(masterDataActionListener);
		changeButtonStandardTask.addActionListener(masterDataActionListener);
		newButtonCategory.addActionListener(masterDataActionListener);
		newButtonProject.addActionListener(masterDataActionListener);
		newButtonSystem.addActionListener(masterDataActionListener);
		newButtonLandscape.addActionListener(masterDataActionListener);
		newButtonUser.addActionListener(masterDataActionListener);
		changeButtonUser.addActionListener(masterDataActionListener);
		newButtonRole.addActionListener(masterDataActionListener);
		changeButtonRole.addActionListener(masterDataActionListener);
		delRoleToUserButton.addActionListener(masterDataActionListener);
		newRoleToUserButton.addActionListener(masterDataActionListener);
		
		centerPanel.add(newPreallocationButton);
		centerPanel.add(deletePreallocationButton);
		centerPanel.add(newButtonStandardTask);
		centerPanel.add(changeButtonStandardTask);
		centerPanel.add(newButtonCategory);
		centerPanel.add(newButtonProject);
		centerPanel.add(newButtonSystem);
		centerPanel.add(newButtonLandscape);
		centerPanel.add(newButtonUser);
		centerPanel.add(changeButtonUser);
		centerPanel.add(newButtonRole);
		centerPanel.add(changeButtonRole);
		centerPanel.add(delRoleToUserButton);
		centerPanel.add(newRoleToUserButton);
		
		// Creating labels, formatted text fields and combo boxes and adding action listeners		
		selectedUsernameLabel.setText("Benutzername");
		userSelection.setPrototypeDisplayValue("012345678901234");
		userSelection.addActionListener(actionListenerCB);
		userSelection.setName("userSelection");
		
		taskPreallocationLabel.setText("Aufgabenvorbelegung");
		durationLabel.setText("Zeitdauer");
		taskPreallocationSelection.setPrototypeDisplayValue("0123456789012345678901234567890123456789012345678901234");
		durationFTF.setColumns(8);
		secondTaskPreallocationSelection.setPrototypeDisplayValue("0123456789012345678901234567890123456789012345678901234");
		secondDurationText.setColumns(8);	
		
		standardTaskLabel.setText("Standardaufgabe");
		categoryLabel.setText("Kategorie");
		projectLabel.setText("Projekt");
		systemLabel.setText("SID");
		usernameLabel.setText("Benutzername");
		
		delStandardTaskSelection.setPrototypeDisplayValue("0123456789012345678901234567890123456789012345678901234");
		delStandardTaskSelection.setModel(masterdataModel.getStandardTaskModels(0));
		delStandardTaskSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = delStandardTaskSelection.getSelectedIndex();
				updateTaskListComboBoxes(index);
			}
		});
		categoryText.setColumns(15);
		categoryText.setEditable(false);
		projectText.setColumns(15);
		projectText.setEditable(false);
		systemText.setColumns(5);
		systemText.setEditable(false);
		usernameText.setColumns(10);
		usernameText.setEditable(false);
		
		newStandardTaskFT.setColumns(36);
		categorySelection.setPrototypeDisplayValue("012345678901234567890");	
		projectSelection.setPrototypeDisplayValue("012345678901234567890");	
		systemSelection.setPrototypeDisplayValue("01234");	
		usernameSelection.setPrototypeDisplayValue("0123456789012");	
		
		categoryFTF.setColumns(15);
		projectFTF.setColumns(15);
		systemFTF.setColumns(5);
		landscapeFTF.setColumns(15);
		
		secondCategoryLabel.setText("Kategorie");
		secondProjectLabel.setText("Projekt");
		secondSystemLabel.setText("SID");
		landscapeLabel.setText("Landschaft");
		
		secondUsernameLabel.setText("Benutzername");
		vornameLabel = new JLabel("Vorname");
		nachnameLabel = new JLabel("Nachname");
		sollArbeitszeitLabel = new JLabel("Sollarbeitszeit");
		
		secondUsernameSelection.setPrototypeDisplayValue("0123456789012");	
		vornameFTF.setColumns(15);
		nachnameFTF.setColumns(15);	
		sollArbeitszeitFTF.setColumns(8);
		
		roleLabel.setText("Rolle");
		rollenSelection.setPrototypeDisplayValue("012345678901234567890");
		
		thirdUsernameLabel.setText("Benutzername");
		secondRoleLabel.setText("Zugewiesene Rollen");
		fourthUsernameLabel.setText("Benutzername");
		rolesLabel.setText("Neue Rolle");
		
		secondUsernameText.setColumns(10);
		secondUsernameText.setEditable(false);
		secondRollenSelection.setPrototypeDisplayValue("012345678901234567890");
		thirdUsernameText.setColumns(10);
		thirdUsernameText.setEditable(false);
		thirdRollenSelection.setPrototypeDisplayValue("012345678901234567890");
		
		centerPanel.add(taskPreallocationLabel);
		centerPanel.add(durationLabel);	
		centerPanel.add(taskPreallocationSelection);
		centerPanel.add(durationFTF);	
		centerPanel.add(secondTaskPreallocationSelection);
		centerPanel.add(secondDurationText);
		
		centerPanel.add(selectedUsernameLabel);
		centerPanel.add(userSelection);
		
		centerPanel.add(standardTaskLabel);
		centerPanel.add(categoryLabel);
		centerPanel.add(projectLabel);
		centerPanel.add(systemLabel);
		centerPanel.add(usernameLabel);
		
		centerPanel.add(delStandardTaskSelection);
		centerPanel.add(categoryText);
		centerPanel.add(projectText);
		centerPanel.add(systemText);
		centerPanel.add(usernameText);
		
		centerPanel.add(newStandardTaskFT);
		centerPanel.add(categorySelection);
		centerPanel.add(projectSelection);
		centerPanel.add(systemSelection);
		centerPanel.add(usernameSelection);
		
		centerPanel.add(secondCategoryLabel);
		centerPanel.add(secondProjectLabel);
		centerPanel.add(secondSystemLabel);
		centerPanel.add(landscapeLabel);	
		
		centerPanel.add(categoryFTF);
		centerPanel.add(projectFTF);
		centerPanel.add(systemFTF);
		centerPanel.add(landscapeFTF);
		
		centerPanel.add(secondUsernameLabel);
		centerPanel.add(vornameLabel);
		centerPanel.add(nachnameLabel);
		centerPanel.add(sollArbeitszeitLabel);
		
		centerPanel.add(secondUsernameSelection);
		centerPanel.add(vornameFTF);
		centerPanel.add(nachnameFTF);
		centerPanel.add(sollArbeitszeitFTF);
		
		centerPanel.add(roleLabel);	
		centerPanel.add(rollenSelection);	
		
		centerPanel.add(thirdUsernameLabel);
		centerPanel.add(secondRoleLabel);
		centerPanel.add(fourthUsernameLabel);
		centerPanel.add(rolesLabel);
		
		centerPanel.add(secondUsernameText);
		centerPanel.add(secondRollenSelection);
		centerPanel.add(thirdUsernameText);
		centerPanel.add(thirdRollenSelection); 
        
        // Placing user label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, selectedUsernameLabel, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, selectedUsernameLabel, 10, SpringLayout.NORTH, centerPanel);
        // Placing user selection combo box in spring layout
        springLayout.putConstraint(SpringLayout.WEST, userSelection, 10, SpringLayout.EAST, selectedUsernameLabel);
        springLayout.putConstraint(SpringLayout.NORTH, userSelection, 10, SpringLayout.NORTH, centerPanel);
        
	    // Placing role selection combo box in spring layout
	    springLayout.putConstraint(SpringLayout.WEST, taskPreallocationLabel, 10, SpringLayout.WEST, centerPanel);
	    springLayout.putConstraint(SpringLayout.NORTH, taskPreallocationLabel, 40, SpringLayout.SOUTH, userSelection);      
        // Placing new role button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, durationLabel, 10, SpringLayout.EAST, taskPreallocationSelection);
        springLayout.putConstraint(SpringLayout.NORTH, durationLabel, 40, SpringLayout.SOUTH, userSelection);
        // Placing new role button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, taskPreallocationSelection, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, taskPreallocationSelection, 5, SpringLayout.SOUTH, taskPreallocationLabel);       
	    // Placing role selection combo box in spring layout
	    springLayout.putConstraint(SpringLayout.WEST, durationFTF, 10, SpringLayout.EAST, taskPreallocationSelection);
	    springLayout.putConstraint(SpringLayout.NORTH, durationFTF, 5, SpringLayout.SOUTH, taskPreallocationLabel); 
        // Placing delete button in spring layout
		springLayout.putConstraint(SpringLayout.WEST, newPreallocationButton, 10, SpringLayout.EAST, durationFTF);
        springLayout.putConstraint(SpringLayout.NORTH, newPreallocationButton, 5, SpringLayout.SOUTH, taskPreallocationLabel);   
        // Placing change role button in spring layout
   		springLayout.putConstraint(SpringLayout.WEST, secondTaskPreallocationSelection, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, secondTaskPreallocationSelection, 5, SpringLayout.SOUTH, taskPreallocationSelection);
        // Placing change role button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, secondDurationText, 10, SpringLayout.EAST, secondTaskPreallocationSelection);
        springLayout.putConstraint(SpringLayout.NORTH, secondDurationText, 5, SpringLayout.SOUTH, taskPreallocationSelection);
        // Placing delete button in spring layout
		springLayout.putConstraint(SpringLayout.WEST, deletePreallocationButton, 10, SpringLayout.EAST, secondDurationText);
        springLayout.putConstraint(SpringLayout.NORTH, deletePreallocationButton, 5, SpringLayout.SOUTH, taskPreallocationSelection);  
        
        // Placing standard task label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, standardTaskLabel, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, standardTaskLabel, 40, SpringLayout.SOUTH, secondTaskPreallocationSelection);      
        // Placing category label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, categoryLabel, 10, SpringLayout.EAST, delStandardTaskSelection);
        springLayout.putConstraint(SpringLayout.NORTH, categoryLabel, 40, SpringLayout.SOUTH, secondTaskPreallocationSelection);
        // Placing project label in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, projectLabel, 10, SpringLayout.EAST, categoryText);
        springLayout.putConstraint(SpringLayout.NORTH, projectLabel, 40, SpringLayout.SOUTH, secondTaskPreallocationSelection);
        // Placing system label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, systemLabel, 10, SpringLayout.EAST, projectText);
        springLayout.putConstraint(SpringLayout.NORTH, systemLabel, 40, SpringLayout.SOUTH, secondTaskPreallocationSelection);
        // Placing user name label in spring layout
		springLayout.putConstraint(SpringLayout.WEST, usernameLabel, 10, SpringLayout.EAST, systemText);
        springLayout.putConstraint(SpringLayout.NORTH, usernameLabel, 40, SpringLayout.SOUTH, secondTaskPreallocationSelection);      
        
        // Placing standard task selection combo box in spring layout
        springLayout.putConstraint(SpringLayout.WEST, delStandardTaskSelection, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, delStandardTaskSelection, 5, SpringLayout.SOUTH, standardTaskLabel);      
        // Placing category text field in spring layout
        springLayout.putConstraint(SpringLayout.WEST, categoryText, 10, SpringLayout.EAST, delStandardTaskSelection);
        springLayout.putConstraint(SpringLayout.NORTH, categoryText, 5, SpringLayout.SOUTH, standardTaskLabel);
        // Placing project text field in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, projectText, 10, SpringLayout.EAST, categoryText);
        springLayout.putConstraint(SpringLayout.NORTH, projectText, 5, SpringLayout.SOUTH, standardTaskLabel);
        // Placing system text field in spring layout
        springLayout.putConstraint(SpringLayout.WEST, systemText, 10, SpringLayout.EAST, projectText);
        springLayout.putConstraint(SpringLayout.NORTH, systemText, 5, SpringLayout.SOUTH, standardTaskLabel);
        // Placing user name text field in spring layout
		springLayout.putConstraint(SpringLayout.WEST, usernameText, 10, SpringLayout.EAST, systemText);
        springLayout.putConstraint(SpringLayout.NORTH, usernameText, 5, SpringLayout.SOUTH, standardTaskLabel);
        // Placing delete button in spring layout
		springLayout.putConstraint(SpringLayout.WEST, changeButtonStandardTask, 10, SpringLayout.EAST, usernameText);
        springLayout.putConstraint(SpringLayout.NORTH, changeButtonStandardTask, 5, SpringLayout.SOUTH, standardTaskLabel);
   
        // Placing standard task text field in spring layout
        springLayout.putConstraint(SpringLayout.WEST, newStandardTaskFT, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, newStandardTaskFT, 5, SpringLayout.SOUTH, delStandardTaskSelection);      
        // Placing category selection combo box in spring layout
        springLayout.putConstraint(SpringLayout.WEST, categorySelection, 10, SpringLayout.EAST, delStandardTaskSelection);
        springLayout.putConstraint(SpringLayout.NORTH, categorySelection, 5, SpringLayout.SOUTH, delStandardTaskSelection);
        // Placing project selection combo box in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, projectSelection, 10, SpringLayout.EAST, categoryText);
        springLayout.putConstraint(SpringLayout.NORTH, projectSelection, 5, SpringLayout.SOUTH, delStandardTaskSelection);
        // Placing system selection combo box in spring layout
        springLayout.putConstraint(SpringLayout.WEST, systemSelection, 10, SpringLayout.EAST, projectText);
        springLayout.putConstraint(SpringLayout.NORTH, systemSelection, 5, SpringLayout.SOUTH, delStandardTaskSelection);
        // Placing user name selection combo box in spring layout
		springLayout.putConstraint(SpringLayout.WEST, usernameSelection, 10, SpringLayout.EAST, systemText);
        springLayout.putConstraint(SpringLayout.NORTH, usernameSelection, 5, SpringLayout.SOUTH, delStandardTaskSelection);
        // Placing delete button in spring layout
		springLayout.putConstraint(SpringLayout.WEST, newButtonStandardTask, 10, SpringLayout.EAST, usernameText);
        springLayout.putConstraint(SpringLayout.NORTH, newButtonStandardTask, 5, SpringLayout.SOUTH, delStandardTaskSelection);   
        
        // Placing 2nd category label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, secondCategoryLabel, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, secondCategoryLabel, 40, SpringLayout.SOUTH, newStandardTaskFT);      
        // Placing 2nd project label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, secondProjectLabel, 40, SpringLayout.EAST, newButtonCategory);
        springLayout.putConstraint(SpringLayout.NORTH, secondProjectLabel, 40, SpringLayout.SOUTH, newStandardTaskFT);
        // Placing 2nd system label in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, secondSystemLabel, 40, SpringLayout.EAST, newButtonProject);
        springLayout.putConstraint(SpringLayout.NORTH, secondSystemLabel, 40, SpringLayout.SOUTH, newStandardTaskFT);
        // Placing landscape label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, landscapeLabel, 40, SpringLayout.EAST, newButtonSystem);
        springLayout.putConstraint(SpringLayout.NORTH, landscapeLabel, 40, SpringLayout.SOUTH, newStandardTaskFT);
             
        // Placing category formatted text field in spring layout
        springLayout.putConstraint(SpringLayout.WEST, categoryFTF, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, categoryFTF, 5, SpringLayout.SOUTH, secondCategoryLabel);      
        // Placing category new button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, newButtonCategory, 10, SpringLayout.EAST, categoryFTF);
        springLayout.putConstraint(SpringLayout.NORTH, newButtonCategory, 5, SpringLayout.SOUTH, secondCategoryLabel);
        // Placing project formatted text field in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, projectFTF, 40, SpringLayout.EAST, newButtonCategory);
        springLayout.putConstraint(SpringLayout.NORTH, projectFTF, 5, SpringLayout.SOUTH, secondCategoryLabel);
        // Placing project new button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, newButtonProject, 10, SpringLayout.EAST, projectFTF);
        springLayout.putConstraint(SpringLayout.NORTH, newButtonProject, 5, SpringLayout.SOUTH, secondCategoryLabel);
        // Placing system formatted text field in spring layout
		springLayout.putConstraint(SpringLayout.WEST, systemFTF, 40, SpringLayout.EAST, newButtonProject);
        springLayout.putConstraint(SpringLayout.NORTH, systemFTF, 5, SpringLayout.SOUTH, secondCategoryLabel);
        // Placing system new button in spring layout
		springLayout.putConstraint(SpringLayout.WEST, newButtonSystem, 10, SpringLayout.EAST, systemFTF);
        springLayout.putConstraint(SpringLayout.NORTH, newButtonSystem, 5, SpringLayout.SOUTH, secondCategoryLabel); 
        // Placing landscape formatted text field in spring layout
		springLayout.putConstraint(SpringLayout.WEST, landscapeFTF, 40, SpringLayout.EAST, newButtonSystem);
        springLayout.putConstraint(SpringLayout.NORTH, landscapeFTF, 5, SpringLayout.SOUTH, secondCategoryLabel);
        // Placing landscape new button  in spring layout
		springLayout.putConstraint(SpringLayout.WEST, newButtonLandscape, 10, SpringLayout.EAST, landscapeFTF);
        springLayout.putConstraint(SpringLayout.NORTH, newButtonLandscape, 5, SpringLayout.SOUTH, secondCategoryLabel); 
        
        // Placing 2nd category label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, secondUsernameLabel, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, secondUsernameLabel, 40, SpringLayout.SOUTH, categoryFTF);      
        // Placing 2nd project label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, vornameLabel, 10, SpringLayout.EAST, secondUsernameSelection);
        springLayout.putConstraint(SpringLayout.NORTH, vornameLabel, 40, SpringLayout.SOUTH, categoryFTF);
        // Placing 2nd system label in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, nachnameLabel, 10, SpringLayout.EAST,  vornameFTF);
        springLayout.putConstraint(SpringLayout.NORTH, nachnameLabel, 40, SpringLayout.SOUTH, categoryFTF);
        // Placing landscape label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, sollArbeitszeitLabel, 10, SpringLayout.EAST, nachnameFTF);
        springLayout.putConstraint(SpringLayout.NORTH, sollArbeitszeitLabel, 40, SpringLayout.SOUTH, categoryFTF);
        
        // Placing user name selection in spring layout
        springLayout.putConstraint(SpringLayout.WEST, secondUsernameSelection, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, secondUsernameSelection, 5, SpringLayout.SOUTH, secondUsernameLabel);      
        // Placing vorname formatted text field in spring layout
        springLayout.putConstraint(SpringLayout.WEST, vornameFTF, 10, SpringLayout.EAST, secondUsernameSelection);
        springLayout.putConstraint(SpringLayout.NORTH, vornameFTF, 5, SpringLayout.SOUTH, secondUsernameLabel);
        // Placing nachname formatted text field in spring layout
  		springLayout.putConstraint(SpringLayout.WEST, nachnameFTF, 10, SpringLayout.EAST, vornameFTF);
        springLayout.putConstraint(SpringLayout.NORTH, nachnameFTF, 5, SpringLayout.SOUTH, secondUsernameLabel);
        // Placing sollarbeitszeit formatted text field in spring layout
        springLayout.putConstraint(SpringLayout.WEST, sollArbeitszeitFTF, 10, SpringLayout.EAST, nachnameFTF);
        springLayout.putConstraint(SpringLayout.NORTH, sollArbeitszeitFTF, 5, SpringLayout.SOUTH, secondUsernameLabel);
        // Placing user new button in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, newButtonUser, 10, SpringLayout.EAST, sollArbeitszeitFTF);
        springLayout.putConstraint(SpringLayout.NORTH, newButtonUser, 5, SpringLayout.SOUTH, secondUsernameLabel); 
        // Placing user change button in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, changeButtonUser, 10, SpringLayout.EAST, newButtonUser);
        springLayout.putConstraint(SpringLayout.NORTH, changeButtonUser, 5, SpringLayout.SOUTH, secondUsernameLabel); 
         
        // Placing role label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, roleLabel, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, roleLabel, 40, SpringLayout.SOUTH, secondUsernameSelection); 
         
	    // Placing role selection combo box in spring layout
	    springLayout.putConstraint(SpringLayout.WEST, rollenSelection, 10, SpringLayout.WEST, centerPanel);
	    springLayout.putConstraint(SpringLayout.NORTH, rollenSelection, 5, SpringLayout.SOUTH, roleLabel);      
        // Placing new role button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, newButtonRole, 10, SpringLayout.EAST, rollenSelection);
        springLayout.putConstraint(SpringLayout.NORTH,newButtonRole, 5, SpringLayout.SOUTH, roleLabel);
        // Placing change role button in spring layout
   		springLayout.putConstraint(SpringLayout.WEST, changeButtonRole, 10, SpringLayout.EAST, newButtonRole);
        springLayout.putConstraint(SpringLayout.NORTH, changeButtonRole, 5, SpringLayout.SOUTH, roleLabel);
        
        // Placing role label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, thirdUsernameLabel, 10, SpringLayout.WEST, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, thirdUsernameLabel, 40, SpringLayout.SOUTH, rollenSelection); 
        // Placing role label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, secondRoleLabel, 10, SpringLayout.EAST, secondUsernameText);
        springLayout.putConstraint(SpringLayout.NORTH, secondRoleLabel, 40, SpringLayout.SOUTH, rollenSelection);
        // Placing role label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, fourthUsernameLabel, 40, SpringLayout.EAST, delRoleToUserButton);
        springLayout.putConstraint(SpringLayout.NORTH, fourthUsernameLabel, 40, SpringLayout.SOUTH, rollenSelection); 
        // Placing role label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, rolesLabel, 10, SpringLayout.EAST, thirdUsernameText);
        springLayout.putConstraint(SpringLayout.NORTH, rolesLabel, 40, SpringLayout.SOUTH, rollenSelection); 
        
	    // Placing role selection combo box in spring layout
	    springLayout.putConstraint(SpringLayout.WEST, secondUsernameText, 10, SpringLayout.WEST, centerPanel);
	    springLayout.putConstraint(SpringLayout.NORTH, secondUsernameText, 5, SpringLayout.SOUTH, thirdUsernameLabel);      
        // Placing new role button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, secondRollenSelection, 10, SpringLayout.EAST, secondUsernameText);
        springLayout.putConstraint(SpringLayout.NORTH, secondRollenSelection, 5, SpringLayout.SOUTH, thirdUsernameLabel);
        // Placing new role button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, delRoleToUserButton, 10, SpringLayout.EAST, secondRollenSelection);
        springLayout.putConstraint(SpringLayout.NORTH, delRoleToUserButton, 5, SpringLayout.SOUTH, thirdUsernameLabel);
	    // Placing role selection combo box in spring layout
	    springLayout.putConstraint(SpringLayout.WEST, thirdUsernameText, 40, SpringLayout.EAST, delRoleToUserButton);
	    springLayout.putConstraint(SpringLayout.NORTH, thirdUsernameText, 5, SpringLayout.SOUTH, thirdUsernameLabel); 
        // Placing change role button in spring layout
   		springLayout.putConstraint(SpringLayout.WEST, thirdRollenSelection, 10, SpringLayout.EAST, thirdUsernameText);
        springLayout.putConstraint(SpringLayout.NORTH, thirdRollenSelection, 5, SpringLayout.SOUTH, thirdUsernameLabel);
        // Placing change role button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, newRoleToUserButton, 10, SpringLayout.EAST, thirdRollenSelection);
        springLayout.putConstraint(SpringLayout.NORTH, newRoleToUserButton, 5, SpringLayout.SOUTH, thirdUsernameLabel);
        

        // set JTable properties, and add table model, create pop up menu, its items and add it to JTable
//        JPopupMenu tableMenu = new JPopupMenu();
//        tableMenu.setLightWeightPopupEnabled(false);
//		itemActivate.setText("Aktivieren");	
//		itemClose.setText("Abschliessen");			
//		itemStop.setText("Stoppen");		
//		itemCloseAll.setText("Alle Schliessen");
//		itemPause.setText("Pausieren");
//
//		tableMenu.add(itemActivate);
//		tableMenu.add(itemClose);
//		tableMenu.add(itemStop);
//		tableMenu.add(itemCloseAll);
//		tableMenu.add(itemPause);
//		itemActivate.addActionListener((ActionListener)controller);
//		itemClose.addActionListener((ActionListener)controller);
//		itemStop.addActionListener((ActionListener)controller);
//		itemCloseAll.addActionListener((ActionListener)controller);
//		itemPause.addActionListener((ActionListener)controller);
		
//		// in order to activate an entry in stack list, the row of table should be selectable, but only one row
//		// at a time, multiple rows should not be selectable. Additionally, a single column should not be selectable
//        stackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        stackTable.setColumnSelectionAllowed(false);
//        stackTable.setRowSelectionAllowed(true);
//		scrollPane.setViewportView(stackTable);
//		stackTable.setModel(masterdataModel);
//		
//		// setting row high according to font size
//		FontMetrics tableFontMetrics = stackTable.getFontMetrics(stackTable.getFont());
//		int fontPixelHeigth = tableFontMetrics.getHeight();
//		stackTable.setRowHeight(fontPixelHeigth + 4);
//		
//		// Here we listen for right mouse clicks on the table. If done so, the row is selected and a popup menu 
//		// is displayed presenting actions for the selected row. The action on the pop up menu is handled by the
//		// action listener assigned to the pop up menu
//		stackTable.addMouseListener(new MouseAdapter(){
//			@Override
//		    public void mouseReleased(MouseEvent e) {
//
//				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
//				       int r = stackTable.rowAtPoint(e.getPoint());
//				       // if the row number has a valid value, the proper row is set as selected
//				        if (r >= 0 && r < stackTable.getRowCount()) {
//				            stackTable.setRowSelectionInterval(r, r);
//				        } else {
//				            stackTable.clearSelection();
//				        }
//				        // if no row is selected rowindex has a negative value (-1)
//				        int rowindex = stackTable.getSelectedRow();
//				        if (rowindex < 0)
//				            return;
//				        // the pop up menu is displayed if mouse action is from the table and action is a pop up trigger
//				        if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
//				        	// the pop up menu is displayed over the proper component (table) at the spot of the mouse click
//				            tableMenu.show(e.getComponent(), e.getX(), e.getY());
//				        }
//
//				}
//			}
//		});
		
        updateSelectedUserCB();
		// Draw and display elements
		SwingUtilities.updateComponentTreeUI(frame);
		frame.setSize(1500, 400);
		redraw();
	}

	
	/**
	 * Redraws screen.
	 */
	public void redraw() {
		//setButtonItemAndLabelStates();
		frame.repaint();
	    frame.setVisible(true);
	}
	
	
	/**
	 * Shows error messages.
	 */
	public void showPopUp(String titel, String message, int messagetype) {	
		JOptionPane.showMessageDialog(frame, message, titel, messagetype);		
	}
	
	
//	/**
//	 * Sets displayed text of formatted text field "active label"
//	 * @param labelText the text to be displayed
//	 */
//	public void setActiveLabelText(String labelText) {
//		activeLabel.setText(labelText);
//		redraw();
//	}
//	
//	
//	/**
//	 * Returns the text contained in the formatted text field "new label"
//	 * @return the text contained in the formatted text field "new label"
//	 */
//	public String getNewLabelText() {
//		return newLabel.getText();
//	}
//
//
//	/**
//	 * Sets displayed text of formatted text field "new label", normally used to delete contained text with empty string
//	 * @param newLabelText the text to be displayed
//	 */
//	public void setNewLabelText(String newLabelText) {
//		newLabel.setText(newLabelText);
//	}
//	
//	
//	/**
//	 * Getter method for the currently selected row of stack table (-1 if no row is selected)
//	 * @param the index number of the selected row
//	 */
//	public int getSelectedRow() {
//		return stackTable.getSelectedRow();
//	}
//	
//	
	/**
	 * Updates the displayed data
	 */
	public void updateSelectedUserCB() {
		userSelection.setModel(masterdataModel.getUserCBModel());
		userSelection.removeActionListener(actionListenerCB);
		userSelection.setSelectedIndex(masterdataModel.getSelectedUserIndex());
		userSelection.addActionListener(actionListenerCB);
		
	}
	
	private void updateTaskListComboBoxes(int index){

		redraw();
	}
	
}

