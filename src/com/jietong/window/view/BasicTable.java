package com.jietong.window.view;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.window.basicOperation.BasicTableHeadUI;

public class BasicTable extends TagOperationManagerJPanel{
	private static final long serialVersionUID = 1L;

	public static String getCardNo(String cardNo) {
		char[] ch = cardNo.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
			if ((i + 1) % 2 == 0 && i < 24) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	/**
	 * ��ȡ������Ϣ�������ʾ�ڱ����
	 * @param data ���ӱ�ǩ����
	 * @param antNo ���߱��
	 */
	
	public static void tableInfoShow(Reader reader,String data,int antNo,String deviceNo) {
		String cardNo = getCardNo(data);
		String deviceId = deviceNo;
		try {
			//if(chooseSaveFile){
			   // FileOperation.writeFile(cardNo);
			//}
		} catch (Exception e) {
			// e.printStackTrace();
			//Message.Show(rs.getString("strMsgFailedSaveFile"), "");
		}
		boolean flag = false;
		int currentRowCount = 0;
		// ������ݵ�table��
		for (int j = 0; j < table_showTagInfo.getRowCount(); j++) {
			 //���������������뼯�����ݶԱ�,������������ͬ�����������Ӷ�ȡ�Ĵ���
			String rows = (String) table_showTagInfo.getValueAt(j, 2);
			if (rows.equals(cardNo)) {
				int count = Integer.parseInt(table_showTagInfo.getValueAt(j, 3).toString());
				count++;
				table_showTagInfo.setValueAt(deviceNo, j, 4);
				table_showTagInfo.setValueAt(count, j, 3);
				flag = true;
				break;
			}
		}
		// ��������û���뼯������ͬ������������һ������
		if (!flag) {
			// ��ȡ�����е�����
			int id = table_showTagInfo.getRowCount() + 1;
			Object[] rowValues = { id, id, cardNo,1, deviceId};
			baseTableModule.addRow(rowValues); // ���һ��
			
			currentRowCount = table_showTagInfo.getRowCount()-1;
			table_showTagInfo.getSelectionModel().setSelectionInterval(currentRowCount, currentRowCount);
			Rectangle rect = table_showTagInfo.getCellRect(currentRowCount, 5, true);
			table_showTagInfo.scrollRectToVisible(rect);
		}
		// ��������Ĵ���
		int cardResultTime = 0;
		for (int i = 0; i < table_showTagInfo.getRowCount(); i++) {
			cardResultTime += Integer.parseInt(table_showTagInfo.getValueAt(i, 3).toString());
		}
		lblResultTags.setText(String.valueOf(table_showTagInfo.getRowCount()));
		// �ܹ��м��ű�ǩ
		lblReadTagCounts.setText(String.valueOf(cardResultTime));
	}

	/**
	 * ���ñ����ʽ
	 * 
	 * @param panel
	 *            ���(�൱��һ��html�е�div)
	 */
	public static void setTableStyle(JTable tbl_showTagInfo) {
		// ��ͷ���
		BasicTableHeadUI ui = new BasicTableHeadUI();
		// ���ͷ���������
		tbl_showTagInfo.getTableHeader().setUI(ui);

		// ���ñ�ͷ�Ĵ�С��Ҫ�������߶����Ҫ�ͱ�ͷ�ĸ߶�һ�����������ֶ��ಿ��
		tbl_showTagInfo.getTableHeader().setPreferredSize(new Dimension(450, 30));
		tbl_showTagInfo.setRowHeight(25);
		tbl_showTagInfo.setEnabled(false);
		// ����table���ݾ���
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// �����Ͼ�����һ��
		tbl_showTagInfo.setDefaultRenderer(Object.class, tcr);
		// ����table��ͷ������ʾ
		((DefaultTableCellRenderer) tbl_showTagInfo.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tbl_showTagInfo.setPreferredScrollableViewportSize(new Dimension(500, 260));
		// �����п�
		TableColumn firsetColumn = tbl_showTagInfo.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(50);
		firsetColumn.setMinWidth(50);

		TableColumn secondColumn = tbl_showTagInfo.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(250);
		secondColumn.setMaxWidth(250);
		secondColumn.setMinWidth(250);
	}
}
