package leantracer.masterdata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public class MasterDataActionListener implements ActionListener {
	private MasterDataController masterdataController;
	
	public MasterDataActionListener (MasterDataController masterdataController) {
		this.masterdataController = masterdataController;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> tempBox = (JComboBox<String>)e.getSource();
		if ((tempBox.getName()).equals("userSelection")); {
			int index = tempBox.getSelectedIndex();
			masterdataController.selectedUserCBChanged(index);
		}		
	}
}
