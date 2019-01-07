package com.jietong.window.view;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableConnectAction implements ListSelectionListener {
	JLabel debugger;
	ListSelectionModel model;

	public TableConnectAction(JLabel target, ListSelectionModel lsm) {
		debugger = target;
		model = lsm;
	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			// skip all the intermediate events . . .
			StringBuffer buf = new StringBuffer();
			int[] selection = getSelectedIndices(model.getMinSelectionIndex(),model.getMaxSelectionIndex());
			if (selection.length == 0) {
				buf.append("none");
			} else {
				for (int i = 0; i < selection.length - 1; i++) {
					buf.append(selection[i]);
					buf.append(", ");
				}
				buf.append(selection[selection.length - 1]);
			}
			//debugger.setText(buf.toString());
		}

		// int count = tableConnectInfo.getSelectedRow();//获取你选中的行号（记录）
		// String getname= tableConnectInfo.getValueAt(count,1).toString();
	}

	// This method returns an array of selected indices. It's guaranteed to
	// return a nonnull value.
	protected int[] getSelectedIndices(int start, int stop) {
		if ((start == -1) || (stop == -1)) {
			// no selection, so return an empty array
			return new int[0];
		}

		int guesses[] = new int[stop - start + 1];
		int index = 0;
		// manually walk through these . . .
		for (int i = start; i <= stop; i++) {
			if (model.isSelectedIndex(i)) {
				guesses[index++] = i;
			}
		}

		// ok, pare down the guess array to the real thing
		int realthing[] = new int[index];
		System.arraycopy(guesses, 0, realthing, 0, index);
		return realthing;
	}
}
