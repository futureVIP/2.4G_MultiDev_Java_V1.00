package com.jietong.window.basicOperation;

import java.util.HashMap;
import java.util.Map;
import com.jietong.MainStart;
import com.jietong.rfid.util.DataConvert;

public class BasicTable extends MainStart{
	private static final long serialVersionUID = 1L;

	public static void tableInfoShow(String data, String deviceId) {
		if (data.trim().length() != 8) {
			return;
		}
		String cardNo = DataConvert.getCardNo(data);
		boolean flag = false;
		int currentRowCount = 0;
		// ������ݵ�table��
		for (int j = 0; j < tbl_showTagInfo.getRowCount(); j++) {
			// ���������������뼯�����ݶԱ�,������������ͬ�����������Ӷ�ȡ�Ĵ���
			String rows = (String) tbl_showTagInfo.getValueAt(j, 1);
			String device = tbl_showTagInfo.getValueAt(j, 4).toString();
			if (rows.equals(cardNo) && deviceId.equals(device)) {
				int count = Integer.parseInt(tbl_showTagInfo.getValueAt(j, 2).toString());
				count++;
				tbl_showTagInfo.setValueAt(count, j, 2);
				flag = true;
				break;
			}
		}
		// ��������û���뼯������ͬ������������һ������
		if (!flag) {
			// ��ȡ�����е�����
			Object[] rowValues = { tbl_showTagInfo.getRowCount() + 1, cardNo,1,null, deviceId};
			tableModel.addRow(rowValues); // ���һ��
		}
		// ��������Ĵ���
		int cardResultTime = 0;
		Map<String,String> tagCount = new HashMap<String,String>();
		for (int i = 0; i < tbl_showTagInfo.getRowCount(); i++) {
			String rows = (String) tbl_showTagInfo.getValueAt(i, 1);
			tagCount.put(rows, rows);
		}
		lbl_resultCount.setText(String.valueOf(tagCount.size()));
		//labelCount.setText(String.valueOf(cardResultTime));
		// �ܹ��м��ű�ǩ
	}
}
