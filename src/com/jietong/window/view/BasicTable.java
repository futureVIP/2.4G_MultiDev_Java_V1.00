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
	 * 读取卡的信息处理后显示在表格中
	 * @param data 电子标签数据
	 * @param antNo 天线编号
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
		// 添加数据到table中
		for (int j = 0; j < table_showTagInfo.getRowCount(); j++) {
			 //如果表格中有数据与集合数据对比,如果表格中有相同的数据则增加读取的次数
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
		// 如果表格中没有与集合中相同的数据则新增一行数据
		if (!flag) {
			// 获取集合中的数据
			int id = table_showTagInfo.getRowCount() + 1;
			Object[] rowValues = { id, id, cardNo,1, deviceId};
			baseTableModule.addRow(rowValues); // 添加一行
			
			currentRowCount = table_showTagInfo.getRowCount()-1;
			table_showTagInfo.getSelectionModel().setSelectionInterval(currentRowCount, currentRowCount);
			Rectangle rect = table_showTagInfo.getCellRect(currentRowCount, 5, true);
			table_showTagInfo.scrollRectToVisible(rect);
		}
		// 计算读卡的次数
		int cardResultTime = 0;
		for (int i = 0; i < table_showTagInfo.getRowCount(); i++) {
			cardResultTime += Integer.parseInt(table_showTagInfo.getValueAt(i, 3).toString());
		}
		lblResultTags.setText(String.valueOf(table_showTagInfo.getRowCount()));
		// 总共有几张标签
		lblReadTagCounts.setText(String.valueOf(cardResultTime));
	}

	/**
	 * 设置表格样式
	 * 
	 * @param panel
	 *            面板(相当于一个html中的div)
	 */
	public static void setTableStyle(JTable tbl_showTagInfo) {
		// 建头组件
		BasicTableHeadUI ui = new BasicTableHeadUI();
		// 获得头组件并设置
		tbl_showTagInfo.getTableHeader().setUI(ui);

		// 设置表头的大小，要够长，高度最好要和表头的高度一样，否则会出现多余部分
		tbl_showTagInfo.getTableHeader().setPreferredSize(new Dimension(450, 30));
		tbl_showTagInfo.setRowHeight(25);
		tbl_showTagInfo.setEnabled(false);
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		tbl_showTagInfo.setDefaultRenderer(Object.class, tcr);
		// 设置table列头居中显示
		((DefaultTableCellRenderer) tbl_showTagInfo.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tbl_showTagInfo.setPreferredScrollableViewportSize(new Dimension(500, 260));
		// 设置列宽
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
