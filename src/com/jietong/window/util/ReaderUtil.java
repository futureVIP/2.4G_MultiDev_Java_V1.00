package com.jietong.window.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;

import com.jietong.rfid.uhf.dao.impl.Reader;

public class ReaderUtil {
	public static JLabel lblShowInfo;
	public static JTable tableConnectInfo;
	public static List<Reader> connectList = new ArrayList<Reader>();
	public static int selectDevice = -1;

	public static boolean selectConnect() {
		int count = ReaderUtil.tableConnectInfo.getSelectedRow();
		if (count < 0) {
			selectDevice = -1;
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgPleaseConnectDevice"));
			return false;
		}else{
			selectDevice = count;
			return true;
		}
	}
}
