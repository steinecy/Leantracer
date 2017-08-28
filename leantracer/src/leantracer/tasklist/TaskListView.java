package leantracer.tasklist;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.UtilCalendarModel;

import leantracer.login.ConnectionModel;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * Is the graphical user interface, the view of the task list module. It displays the task lists of the five working days
 * in a tabbed pane. The user can change the date. He can add a new task or he can edit an existing task.
 *
 */
public class TaskListView {
	
	private JFrame frame;
	private Logger logger = LogManager.getLogger();
	private String[] tage = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"};
	private JTable[] tasklistJtables = new JTable[5];
	private JScrollPane[] jscrollpanes = new JScrollPane[5];
	private JPopupMenu[] jpopupmenues = new JPopupMenu[5];
	private JMenuItem[] jmenuDeleteItems = new JMenuItem[5];
	private JMenuItem[] jmenuDetailsItems = new JMenuItem[5];
	private JLabel titleLabel;
	private JTabbedPane tabbedPane;
	private TaskListDisplayModel[] tasklistDisplayModels = new TaskListDisplayModel[5];
	private ConnectionModel connectionModel;
	private ImageIcon inselGroup;
	private JDatePicker datePicker;
	
	public TaskListView(TaskListDisplayModel[] tasklistDisplayModels, ConnectionModel connectionModel) {
		
		logger.info(this.getClass().toString() + " was called...");
		
		this.tasklistDisplayModels = tasklistDisplayModels;
		this.connectionModel = connectionModel;
		
		// Creating the main frame
		inselGroup = new ImageIcon("inselgruppe.png");
		titleLabel = new JLabel();
		tabbedPane = new JTabbedPane();
		JFrame frame = new JFrame("Leantracer Aufgabenliste");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImage(inselGroup.getImage());
		this.frame = frame;		
	}
	
	
	/**
	 * Creates the elements of the gui and adds them to various panes and adds the panes to the frame.
	 * Adds the table models to the tables.
	 */
	public void showTaskList(TaskListIMActionListener tasklistIMActionListener) {
		
		logger.info("In der TaskListView wurde die Methode showTasklist aufgerufen..");
		
		// create a content pane
        Container contentPane = frame.getContentPane();
        
		// create a tabbed Pane for the tables
        //JTabbedPane tabbedPane = new JTabbedPane();
        //tabbedPane.putClientProperty("JComponent.sizeVariant", "large");
		
		// create the panel for titel and date picker
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2,1));
		contentPane.add(BorderLayout.NORTH, northPanel);
		
		// create gui elemts an add them to the various panes
		//JLabel titleLabel = new JLabel(" Aufgabenliste Woche " + connectionModel.getCalendar().get(Calendar.WEEK_OF_YEAR));
       
		JDateComponentFactory componentFactory = new JDateComponentFactory();
        datePicker = componentFactory.createJDatePicker();
		northPanel.add(titleLabel);
		northPanel.add((Component) datePicker);
		
		// create the tables and add table data model to them, set table height according to font size
		for (int i=0; i<5; i++){
			jscrollpanes[i] = new JScrollPane();
			tasklistJtables[i] = new JTable();	
			jpopupmenues[i] = new JPopupMenu();
			jmenuDeleteItems[i] = new JMenuItem("Löschen");
			jmenuDetailsItems[i] = new JMenuItem("Details");
			jmenuDeleteItems[i].setName("deleteItem" + i);
			jmenuDetailsItems[i].setName("detailsItem" + i);
			jmenuDeleteItems[i].addActionListener(tasklistIMActionListener);
			jmenuDetailsItems[i].addActionListener(tasklistIMActionListener);
			jscrollpanes[i].putClientProperty("JComponent.sizeVariant", "large");
			jscrollpanes[i].setViewportView(tasklistJtables[i]);
			tasklistJtables[i].setModel(tasklistDisplayModels[i]);
			FontMetrics tableFontMetrics = tasklistJtables[i].getFontMetrics(tasklistJtables[i].getFont());
			int fontPixelHeigth = tableFontMetrics.getHeight();
			tasklistJtables[i].setRowHeight(fontPixelHeigth + 4);
			jpopupmenues[i].add(jmenuDetailsItems[i]);
			jpopupmenues[i].add(jmenuDeleteItems[i]);
			TaskListMouseListener tasklistMouseListener = new TaskListMouseListener(tasklistJtables[i],jpopupmenues[i]);
			tasklistJtables[i].addMouseListener(tasklistMouseListener);
		}

		// create the tabs of the tabbed pan and add the scroll panes containing the tables to the tabs
		for (int i=0; i<5; i++){
			tabbedPane.addTab(tage[i], null, jscrollpanes[i], tage[i]);
		}
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		SwingUtilities.updateComponentTreeUI(frame);
		frame.setSize(1500, 400);
		frame.repaint();
		logger.info("In der TaskListView wurde die Methode showTaskList aufgerufen, nun sollte das Fenster angezeigt werden..");
	    frame.setVisible(true);
	}

	
	/**
	 * Displays error messages as a pop up window.
	 * @param titel the title of the pop up window
	 * @param message the error message to be displayed
	 * @param messagetype the type of pop up window (error message)
	 */
	public void showPopUp(String titel, String message, int messagetype){		
		JOptionPane.showMessageDialog(frame, message, titel, messagetype);	
	}
	
	
	/**
	 * Causes the screen to be repainted
	 */
	public void repaint() {
		frame.repaint();
		frame.setVisible(true);
	}
	
	
	/**
	 * adds ActionListener to datepicker
	 */
	public void addDatePickerAL(ActionListener actionListener) {
		datePicker.addActionListener(actionListener);
	}
	
	
	/**
	 * adds ActionListener to tabbed pane
	 */
	public void addTabbedPaneCL(ChangeListener tasklistChangeListener) {
		tabbedPane.addChangeListener(tasklistChangeListener);
	}
	
	
	/**
	 * sets the text of the JLabel calendar week to the appropriate calendar week value
	 */
	public void setCalendarWeekLabel(Calendar calendar) {
		titleLabel.setText(" Aufgabenliste Woche " + calendar.get(Calendar.WEEK_OF_YEAR));
	}
	
	
	/**
	 * chooses a certain tab of the tabbed pane
	 */
	public void setSelectedTab(int index) {
		tabbedPane.setSelectedIndex(index);
	}
	
	
	/**
	 * Sets the DatePicker to the date selected by tabbed pane
	 */
	public void setDatePicker(Calendar calendar) {
		UtilCalendarModel calendarModel = (UtilCalendarModel)datePicker.getModel();
		calendarModel.setValue(calendar);
	}
}