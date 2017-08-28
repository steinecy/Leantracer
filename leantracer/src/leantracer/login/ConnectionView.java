
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * Is the graphical user interface, the view of the login module. It displays the login mask for the user at which he can 
 * enter his login credentials.
 */
public class ConnectionView {

	private JFrame frame;
	private ImageIcon inselGroup;
	
	public ConnectionView() {
		inselGroup = new ImageIcon("inselgruppe.png");
		// Create the main window
		frame = new JFrame("Leantracer Anmeldung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(inselGroup.getImage());		
	}
	
	public void showLogin(ConnectionModel verbindungmodel) {
		
        Container contentPane = frame.getContentPane();
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		contentPane.add(BorderLayout.NORTH, northPanel);
		contentPane.add(BorderLayout.CENTER, centerPanel);
		contentPane.add(BorderLayout.SOUTH, southPanel);
		GridLayout centerGrid = new GridLayout(2,2);
		centerPanel.setLayout(centerGrid);
		
		JLabel titelLabel = new JLabel("Verbinden mit:  " + verbindungmodel.getDburl());
		JLabel benutzerLabel = new JLabel("Benutzername: ");
		JLabel passwortLabel = new JLabel("Passwort: ");
		JTextField benutzerField = new JTextField(verbindungmodel.getBenutzer());
		JPasswordField passwortField = new JPasswordField();
		passwortField.setEchoChar('*');
		JButton okButton = new JButton("OK");
		okButton.putClientProperty("JComponent.sizeVariant", "large");
		northPanel.add(titelLabel);
		centerPanel.add(benutzerLabel);
		centerPanel.add(benutzerField);
		centerPanel.add(passwortLabel);
		centerPanel.add(passwortField);
		southPanel.add(okButton);
		
		okButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				// Benutzername und Passwort einlesen und Datenmodell aktualisieren
				if (!benutzerField.getText().isEmpty() & !passwortField.getText().isEmpty()) {
					verbindungmodel.setBenutzer(benutzerField.getText());
					verbindungmodel.setPasswort(passwortField.getText());
					System.out.println("VerbindungView.class verbindungmodel.getPasswort: " + verbindungmodel.getPasswort());
					frame.dispose();
				} else {
					showPopUp("Fehlende Eingabe", "Sie haben kein Passwort eingegeben,\n bitte geben Sie ein Passwort ein!",0);
				}
			}
		});
		
        SwingUtilities.updateComponentTreeUI(frame);
		frame.setSize(650, 200);
		frame.repaint();
	    frame.setVisible(true);
	}
	
	
	/**
	 * Displays error messages, if connection to database fails
	 */
	public void showPopUp(String titel, String message, int messagetype){	
		JOptionPane.showMessageDialog(frame, message, titel, messagetype);		
	}
	
}
