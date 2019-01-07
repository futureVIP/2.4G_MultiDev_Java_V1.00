package com.jietong.window.util;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class BaseTableModule extends AbstractTableModel {

	Vector<Vector> rows;
	Vector<String> colums;

	public BaseTableModule(String[] params, Vector<Vector> vector) {
		this.colums = new Vector<String>();
		for (String colum : params) {
			colums.add(colum);
		}
		this.rows = vector;
	}

	@Override
	public String getColumnName(int column) {
		return this.colums.get(column);
	}

	@Override
	public int getColumnCount() {
		return this.colums.size();
	}

	@Override
	public int getRowCount() {
		return this.rows.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Vector) rows.get(rowIndex)).get(columnIndex);
	}

}
