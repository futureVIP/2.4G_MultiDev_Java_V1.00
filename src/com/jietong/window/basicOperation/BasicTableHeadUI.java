package com.jietong.window.basicOperation;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;

import com.jietong.MainStart;



/**
 * 基本操作界面的表格列头
 * 
 * @author zhuQixiang
 */
public class BasicTableHeadUI extends BasicTableHeaderUI {
	private JTableHeader header;

	public void paint(Graphics g, JComponent c) {
		header = (JTableHeader) c;
		JLabel label = getLabel(MainStart.rs.getString("strLvHeadNo"));
		rendererPane.paintComponent(g, label, header, getX(0), 1, getWidth(0),20, true);
		label = getLabel(MainStart.rs.getString("strLvHeadEPC"));
		rendererPane.paintComponent(g, label, header, getX(1), 2, getWidth(1),20, true);
		label = getLabel(MainStart.rs.getString("strLvHeadCount"));
		rendererPane.paintComponent(g, label, header, getX(2), 1, getWidth(2),20, true);
		label = getLabel(MainStart.rs.getString("strLvHeadAntNo"));
		rendererPane.paintComponent(g, label, header, getX(3), 1, getWidth(3),20, true);
		label = getLabel(MainStart.rs.getString("strLvHeadDevNo"));
		rendererPane.paintComponent(g, label, header, getX(4), 1, getWidth(4),20, true);
	}
	
	/**
	 * 获取指定列的起始坐标
	 * @param column 列
	 * @return x坐标的位置
	 */
	private int getX(int column) {
		int x = 0;
		for (int i = 0; i < column; i++){
			x += header.getColumnModel().getColumn(i).getWidth();
		}
		return x;
	}

	/**
	 * 指定列的宽度
	 * @param column 列
	 * @return 宽度
	 */
	private int getWidth(int column) {
		return header.getColumnModel().getColumn(column).getWidth();
	}

	/**
	 * 获得指定文本的标签
	 * @param text 列头名称
	 * @return 列头的标题
	 */
	private JLabel getLabel(String text) {
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		return label;
	}
}
