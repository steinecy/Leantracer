package leantracer.run;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import leantracer.login.ConnectionController;

public class Run {
	
	private Font defaultFont;

	public static void main(String[] args) {
		
		try {
			setLookAndFeel();
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}
		setUIDefaultFonts();
		new ConnectionController();

	}
	
	private static void setLookAndFeel() throws Exception {
	
            // Set System L&F
        UIManager.setLookAndFeel(
            //UIManager.getSystemLookAndFeelClassName());
        	"javax.swing.plaf.nimbus.NimbusLookAndFeel");
		
	}
	
	private static void setUIDefaultFonts() {
		Font defaultFont = getDefaultFont();
		UIManager.getLookAndFeelDefaults().put("Table.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("TableHeader.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("TextField.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("PasswordField.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("TextArea.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("TextPane.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("Label.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("Button.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("Spinner.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("DatePicker.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("TabbedPane.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("FormattedTextField.font", defaultFont);
		UIManager.getLookAndFeelDefaults().put("JComponent.sizeVariant", "large");
		UIManager.getLookAndFeelDefaults().put("TabbedPane.sizeVariant", "large");
		UIManager.getLookAndFeelDefaults().put("Table.gridColor", new ColorUIResource(Color.BLACK));
		
	}
	
	private static Font getDefaultFont() {	
		Font defaultFont = new Font("Calibri", Font.PLAIN, 20);
		return defaultFont;
	}

}
