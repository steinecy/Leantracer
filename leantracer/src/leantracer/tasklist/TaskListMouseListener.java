package leantracer.tasklist;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 * 
 * @author Cyrill Steiner
 * @version 1.0
 * Is an mouse action listener for task list JTables. Calls the pop up menu if a right mouse click is executed
 * on a JTable.
 */
public class TaskListMouseListener implements MouseListener {
	private JTable tasklistTable;
	private JPopupMenu tableMenu;
	
	public TaskListMouseListener(JTable tasklistTable, JPopupMenu tableMenu) {
		this.tasklistTable = tasklistTable;
		this.tableMenu = tableMenu;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// no action required		
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		// no action required		
	}

	
   /** 
    * Does listen for right mouse clicks on a JTable. If done so, the row is selected and a pop up menu 
	* is displayed presenting actions for the selected row. The action on the pop up menu is handled by the
	* action listener assigned to the pop up menu.
	*/
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
		       int r = tasklistTable.rowAtPoint(e.getPoint());
		       // if the row number has a valid value, the proper row is set as selected
		        if (r >= 0 && r < tasklistTable.getRowCount()) {
		        	tasklistTable.setRowSelectionInterval(r, r);
		        } else {
		        	tasklistTable.clearSelection();
		        }
		        // if no row is selected rowindex has a negative value (-1)
		        int rowindex = tasklistTable.getSelectedRow();
		        if (rowindex < 0)
		            return;
		        // the pop up menu is displayed if mouse action is from the table and action is a pop up trigger
		        if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
		        	// the pop up menu is displayed over the proper component (table) at the spot of the mouse click
		        	tableMenu.show(e.getComponent(), e.getX(), e.getY());
		        }
		}
	}

	
	@Override
	public void mouseEntered(MouseEvent e) {
		// no action required		
	}

	
	@Override
	public void mouseExited(MouseEvent e) {
		// no action required		
	}
}
