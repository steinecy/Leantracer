package leantracer.masterdata;

import leantracer.login.ConnectionModel;

public class MasterDataController {
	
	private MasterDataModel masterdataModel;
	private MasterDataStandardTaskDAO masterdataStandardTaskDAO;
	private MasterDataUserDAO masterdataUserDAO;
	private MasterDataView masterdataView;

	public MasterDataController(ConnectionModel connectionModel) {
		masterdataModel = new MasterDataModel(connectionModel.getBenutzer(), connectionModel.getUserID());
		masterdataUserDAO = new MasterDataUserDAO(connectionModel);
		masterdataModel.setUserList(masterdataUserDAO.getUsers());
		masterdataStandardTaskDAO = new MasterDataStandardTaskDAO(connectionModel);
		masterdataModel.setStandardTaskList(masterdataStandardTaskDAO.getStandardTasks());
		masterdataView = new MasterDataView(masterdataModel, new MasterDataCBActionListener(this));
		masterdataView.showStack(new MasterDataActionListener(this));
	}


	private void refreshSelectedUserCB() {
		masterdataModel.setUserList(masterdataUserDAO.getUsers());
		masterdataView.updateSelectedUserCB();
	}
	
	
	public void selectedUserCBChanged(int index) {
		masterdataModel.setSelectedUserIndex(index);
	}	
}
