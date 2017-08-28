package leantracer.masterdata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public class MasterDataCBActionListener implements ActionListener {
	private MasterDataController masterdataController;
	
	public MasterDataCBActionListener (MasterDataController masterdataController) {
		this.masterdataController = masterdataController;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ActionEvent = " + e.toString());
		JComboBox<String> tempBox = (JComboBox<String>)e.getSource();
		if ((tempBox.getName()).equals("userSelection")); {
			int index = tempBox.getSelectedIndex();
			masterdataController.selectedUserCBChanged(index);
		}		
	}
}
