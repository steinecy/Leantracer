package leantracer.stack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
public class StackView {
	
	private JFrame frame;
	private ImageIcon inselGroup;
	private Logger logger = LogManager.getLogger();
	private StackModel stackModel;
	private JFormattedTextField activeLabel;
	private JFormattedTextField newLabel;
	private JTable stackTable;
	private JButton closeButton;
	private JButton pauseButton;
	private JButton stopButton;
	private JButton closeAllButton;
	private JMenuItem itemClose;
	private JMenuItem itemPause;
	private JMenuItem itemActivate;
	private JMenuItem itemStop;
	private JMenuItem itemCloseAll;
	
	public StackView(StackModel stackModel) {		
		logger.info(this.getClass().toString() + "constructor was called..");
		this.stackModel = stackModel;
		activeLabel = new JFormattedTextField();
		newLabel = new JFormattedTextField();
		stackTable = new JTable();
		closeButton = new JButton();
		pauseButton = new JButton();
		stopButton = new JButton();
		closeAllButton = new JButton();
		itemClose = new JMenuItem();
		itemPause = new JMenuItem();
		itemActivate = new JMenuItem();
		itemStop = new JMenuItem();
		itemCloseAll = new JMenuItem();
		// Create main window	
		inselGroup = new ImageIcon("inselgruppe.png");
		JFrame frame = new JFrame("Leantracer Stackansicht");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setIconImage(inselGroup.getImage());
		this.frame = frame;		
	}
	
	
	/**
	 * Creates the elements of the GUI and adds them to various panes and adds the panes to the frame.
	 * Adds the table model to the table. Adds an action listener to the GUI elements. Displays frame.
	 * @param stackModel the table model for the stack table to be displayed
	 * @param controller the action listener used to listen for formatted text field and button actions
	 */
	public void showStack(EventListener controller) {
		
		logger.info("In der StackView wurde die Methode showStack aufgerufen..");
		frame.addWindowListener((WindowListener)controller);
		
		// Creating a ContentPane, ScrollPane and all necessary panels and layouts
        Container contentPane = frame.getContentPane();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.putClientProperty("JComponent.sizeVariant", "large");
		JPanel northPanel = new JPanel();		
		SpringLayout springLayout = new SpringLayout();
		northPanel.setLayout(springLayout);
		JPanel centerPanel = new JPanel();
		GridLayout centerGrid = new GridLayout(2,1);
		centerPanel.setLayout(centerGrid);
		centerPanel.add(northPanel);
		centerPanel.add(scrollPane);
        contentPane.add(BorderLayout.CENTER, centerPanel);
		
		// Creating buttons and adding action listeners to them
		JButton newButton = new JButton("Neu");
		pauseButton.setText("Pausieren");
		closeButton.setText("Abschliessen");
		stopButton.setText("Stoppen");
		closeAllButton.setText("Alle Schliessen");
		newButton.addActionListener((ActionListener)controller);
		pauseButton.addActionListener((ActionListener)controller);
		closeButton.addActionListener((ActionListener)controller);
		stopButton.addActionListener((ActionListener)controller);
		closeAllButton.addActionListener((ActionListener)controller);
		
		// Creating formatted text fields and adding action listener
		activeLabel.setEditable(false);
		activeLabel.setFocusable(false);
		activeLabel.setForeground(Color.RED);
		activeLabel.setColumns(70);
		newLabel.setColumns(70);
		newLabel.addActionListener((ActionListener)controller);
        northPanel.add(newLabel);
        northPanel.add(newButton);
        northPanel.add(activeLabel);
        northPanel.add(pauseButton);
        northPanel.add(closeButton);
        northPanel.add(stopButton);
        northPanel.add(closeAllButton);

        // Placing active label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, activeLabel, 5, SpringLayout.WEST, northPanel);
        springLayout.putConstraint(SpringLayout.NORTH, activeLabel, 5, SpringLayout.NORTH, northPanel);
        // Placing pause button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, pauseButton, 5, SpringLayout.EAST, activeLabel);
        springLayout.putConstraint(SpringLayout.NORTH, pauseButton, 5, SpringLayout.NORTH, northPanel);
        // Placing close button in spring layout
        springLayout.putConstraint(SpringLayout.WEST, closeButton, 5, SpringLayout.EAST, pauseButton);
        springLayout.putConstraint(SpringLayout.NORTH, closeButton, 5, SpringLayout.NORTH, northPanel);
        // Placing stop button in spring layout
 		springLayout.putConstraint(SpringLayout.WEST, stopButton, 5, SpringLayout.EAST, closeButton);
        springLayout.putConstraint(SpringLayout.NORTH, stopButton, 5, SpringLayout.NORTH, northPanel);
        // Placing new label in spring layout
        springLayout.putConstraint(SpringLayout.WEST, newLabel, 5, SpringLayout.WEST, northPanel);
        springLayout.putConstraint(SpringLayout.NORTH, newLabel, 5, SpringLayout.SOUTH, activeLabel);
        // Placing new button in spring layout
		springLayout.putConstraint(SpringLayout.WEST, newButton, 5, SpringLayout.EAST, newLabel);
        springLayout.putConstraint(SpringLayout.NORTH, newButton, 5, SpringLayout.SOUTH, activeLabel);
        // Placing closeAll button in spring layout
		springLayout.putConstraint(SpringLayout.WEST, closeAllButton, 5, SpringLayout.EAST, newButton);
        springLayout.putConstraint(SpringLayout.NORTH, closeAllButton, 5, SpringLayout.SOUTH, activeLabel);

        // set JTable properties, and add table model, create pop up menu, its items and add it to JTable
        JPopupMenu tableMenu = new JPopupMenu();
        tableMenu.setLightWeightPopupEnabled(false);
		itemActivate.setText("Aktivieren");	
		itemClose.setText("Abschliessen");			
		itemStop.setText("Stoppen");		
		itemCloseAll.setText("Alle Schliessen");
		itemPause.setText("Pausieren");

		tableMenu.add(itemActivate);
		tableMenu.add(itemClose);
		tableMenu.add(itemStop);
		tableMenu.add(itemCloseAll);
		tableMenu.add(itemPause);
		itemActivate.addActionListener((ActionListener)controller);
		itemClose.addActionListener((ActionListener)controller);
		itemStop.addActionListener((ActionListener)controller);
		itemCloseAll.addActionListener((ActionListener)controller);
		itemPause.addActionListener((ActionListener)controller);
		
		// in order to activate an entry in stack list, the row of table should be selectable, but only one row
		// at a time, multiple rows should not be selectable. Additionally, a single column should not be selectable
        stackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stackTable.setColumnSelectionAllowed(false);
        stackTable.setRowSelectionAllowed(true);
		scrollPane.setViewportView(stackTable);
		stackTable.setModel(stackModel);
		
		// setting row high according to font size
		FontMetrics tableFontMetrics = stackTable.getFontMetrics(stackTable.getFont());
		int fontPixelHeigth = tableFontMetrics.getHeight();
		stackTable.setRowHeight(fontPixelHeigth + 4);
		
		// Here we listen for right mouse clicks on the table. If done so, the row is selected and a popup menu 
		// is displayed presenting actions for the selected row. The action on the pop up menu is handled by the
		// action listener assigned to the pop up menu
		stackTable.addMouseListener(new MouseAdapter(){
			@Override
		    public void mouseReleased(MouseEvent e) {

				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
				       int r = stackTable.rowAtPoint(e.getPoint());
				       // if the row number has a valid value, the proper row is set as selected
				        if (r >= 0 && r < stackTable.getRowCount()) {
				            stackTable.setRowSelectionInterval(r, r);
				        } else {
				            stackTable.clearSelection();
				        }
				        // if no row is selected rowindex has a negative value (-1)
				        int rowindex = stackTable.getSelectedRow();
				        if (rowindex < 0)
				            return;
				        // the pop up menu is displayed if mouse action is from the table and action is a pop up trigger
				        if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
				        	// the pop up menu is displayed over the proper component (table) at the spot of the mouse click
				            tableMenu.show(e.getComponent(), e.getX(), e.getY());
				        }

				}
			}
		});
		
		// Draw and display elements
		SwingUtilities.updateComponentTreeUI(frame);
		frame.setSize(1550, 400);
		redraw();
	}

	
	/**
	 * Redraws screen.
	 */
	public void redraw() {
		setButtonItemAndLabelStates();
		frame.repaint();
	    frame.setVisible(true);
	}
	
	
	/**
	 * Shows error messages.
	 */
	public void showPopUp(String titel, String message, int messagetype) {	
		JOptionPane.showMessageDialog(frame, message, titel, messagetype);		
	}
	
	
	/**
	 * Sets displayed text of formatted text field "active label"
	 * @param labelText the text to be displayed
	 */
	public void setActiveLabelText(String labelText) {
		activeLabel.setText(labelText);
		redraw();
	}
	
	
	/**
	 * Returns the text contained in the formatted text field "new label"
	 * @return the text contained in the formatted text field "new label"
	 */
	public String getNewLabelText() {
		return newLabel.getText();
	}


	/**
	 * Sets displayed text of formatted text field "new label", normally used to delete contained text with empty string
	 * @param newLabelText the text to be displayed
	 */
	public void setNewLabelText(String newLabelText) {
		newLabel.setText(newLabelText);
	}
	
	
	/**
	 * Getter method for the currently selected row of stack table (-1 if no row is selected)
	 * @param the index number of the selected row
	 */
	public int getSelectedRow() {
		return stackTable.getSelectedRow();
	}
	
	
	/**
	 * Enables or disables the buttons and menu items depending on data model state
	 */
	public void setButtonItemAndLabelStates() {
		activeLabel.setText(stackModel.getActiveLabel());
		if (stackModel.doesActiveExists()) {
			pauseButton.setEnabled(true);
			closeButton.setEnabled(true);
			stopButton.setEnabled(true);
			itemPause.setEnabled(true);
			itemStop.setEnabled(true);
			activeLabel.setBackground(Color.GREEN);
		} else {
			pauseButton.setEnabled(false);
			closeButton.setEnabled(false);
			stopButton.setEnabled(false);
			itemPause.setEnabled(false);
			itemStop.setEnabled(false);
			activeLabel.setBackground(Color.LIGHT_GRAY);
		}
		if (stackModel.isEmpty()) {
			itemClose.setEnabled(false);
			itemActivate.setEnabled(false);
		} else {
			itemClose.setEnabled(true);
			itemActivate.setEnabled(true);
		}
		if (stackModel.isEmpty() && !stackModel.doesActiveExists()) {
			closeAllButton.setEnabled(false);
			itemCloseAll.setEnabled(false);
		} else {
			closeAllButton.setEnabled(true);
			itemCloseAll.setEnabled(true);
		}
	}
}
