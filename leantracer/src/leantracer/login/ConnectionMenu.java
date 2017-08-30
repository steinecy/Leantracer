package leantracer.login;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import leantracer.masterdata.MasterDataController;
import leantracer.stack.StackController;
import leantracer.tasklist.TaskListMain;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * Is the graphical user interface, the view of the login module. It displays the login mask for the user at which he can 
 * enter his login credentials.
 */
public class ConnectionMenu {
	private JFrame frame;
	private ImageIcon inselGroup;
	
	public ConnectionMenu() {		
		// Create the main window
		
		inselGroup = new ImageIcon("inselgruppe.png");		
		frame = new JFrame("Leantracer Auswahlmenü");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setIconImage(inselGroup.getImage());
	}
	
	public void showLogin(ConnectionModel connectionModel) {	
        Container contentPane = frame.getContentPane();
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		contentPane.add(BorderLayout.NORTH, northPanel);
		contentPane.add(BorderLayout.CENTER, centerPanel);
		contentPane.add(BorderLayout.SOUTH, southPanel);
		GridLayout centerGrid = new GridLayout(3,1);
		centerPanel.setLayout(centerGrid);
	
		JLabel titelLabel = new JLabel("Auswahlmenü");
		JButton stackButton = new JButton("Stack");
		JButton taskListButton = new JButton("Aufgabenliste");
		JButton masterDataButton = new JButton("Stammdaten");
		JButton dashboardButton = new JButton("Dashboard");
		northPanel.add(titelLabel);
		centerPanel.add(stackButton);
		centerPanel.add(taskListButton);
		centerPanel.add(masterDataButton);
		centerPanel.add(dashboardButton);
		
		stackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StackController(connectionModel);
			}
		});
		
		taskListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TaskListMain(connectionModel);
			}
		});
		
		masterDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new MasterDataController(connectionModel);
			}
		});
		
		dashboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//new DashboardController(connectionModel);
			}
		});
	
        SwingUtilities.updateComponentTreeUI(frame);
		frame.setSize(650, 200);
		frame.repaint();
	    frame.setVisible(true);
	}	
}
