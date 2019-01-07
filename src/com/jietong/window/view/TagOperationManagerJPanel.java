package com.jietong.window.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.window.util.LanguageUtil;
import com.jietong.window.util.ReaderUtil;
import com.jietong.window.util.TableTools;

public class TagOperationManagerJPanel implements ActionListener,MouseListener, DocumentListener {
	public JPanel backgroundPanel;
	JPanel panel_top;
	JPanel toolPanel;
	JPanel panel_tableShowTagInfo;
	public static DefaultTableModel baseTableModule;
	public static JTable table_showTagInfo;
	JScrollPane jScrollPane;
	JLabel tool_modify;
	private JLabel lblTagCounts;
	public static JLabel lblResultTags;
	private JLabel lblCounts;
	public static JLabel lblReadTagCounts;
	private JPanel panel_bottom;
	private JPanel panel_buttonGroup;
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnClear;
	private JPanel panel_empty1;
	private JPanel panel_empty2;
	ReaderService readerService = new ReaderServiceImpl();

	public TagOperationManagerJPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		initTopPanel();
		initTablePanel();
		buttoGroup();
	}

	public void initTopPanel() {
		panel_top = new JPanel(new BorderLayout());
		initToolPanel();
		backgroundPanel.add(panel_top, "North");
	}

	public void initToolPanel() {
		toolPanel = new JPanel();
		toolPanel.setLayout(new GridLayout(0, 4, 0, 0));
		lblTagCounts = new JLabel(LanguageUtil.rs.getString("lblTagCounts"));
		lblTagCounts.setHorizontalAlignment(SwingConstants.CENTER);
		toolPanel.add(lblTagCounts);
		lblResultTags = new JLabel("0");
		lblResultTags.setForeground(Color.RED);
		lblResultTags.setFont(new Font("\u5B8B\u4F53", Font.PLAIN, 40));
		lblResultTags.setHorizontalAlignment(SwingConstants.CENTER);
		toolPanel.add(lblResultTags);
		panel_top.add(toolPanel, "West");
		lblCounts = new JLabel(LanguageUtil.rs.getString("lblCounts"));
		toolPanel.add(lblCounts);
		lblReadTagCounts = new JLabel("0");
		toolPanel.add(lblReadTagCounts);
		lblReadTagCounts.setFont(new Font("\u5B8B\u4F53", Font.PLAIN, 40));
		lblReadTagCounts.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public void initTablePanel() {
		String params[] = { "ID","ID", "Nubmer", "Counts", "IP/COM" };
		baseTableModule = new DefaultTableModel(null, params) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
		table_showTagInfo = new JTable(baseTableModule);
		TableTools.setTableStyle(table_showTagInfo);
		DefaultTableColumnModel dcm = (DefaultTableColumnModel) table_showTagInfo.getColumnModel();
		dcm.getColumn(0).setMinWidth(0); 
		dcm.getColumn(0).setMaxWidth(0);
		jScrollPane = new JScrollPane(table_showTagInfo);
		TableTools.setJspStyle(jScrollPane);
		panel_tableShowTagInfo = new JPanel(new BorderLayout());
		panel_tableShowTagInfo.setOpaque(false);
		panel_tableShowTagInfo.add(jScrollPane);
		backgroundPanel.add(panel_tableShowTagInfo, "Center");
		table_showTagInfo.getTableHeader().setReorderingAllowed(false);
	}
	
	private void buttoGroup(){
		panel_bottom = new JPanel();
		backgroundPanel.add(panel_bottom, BorderLayout.SOUTH);
		panel_bottom.setLayout(new GridLayout(1, 3, 0, 0));
		panel_empty1 = new JPanel();
		panel_bottom.add(panel_empty1);
		panel_buttonGroup = new JPanel();
		panel_bottom.add(panel_buttonGroup);
		panel_buttonGroup.setLayout(new GridLayout(1, 0, 10, 0));
		btnStart = new JButton("Start");
		panel_buttonGroup.add(btnStart);
		btnStop = new JButton("Stop");
		panel_buttonGroup.add(btnStop);
		btnClear = new JButton("Clear");
		panel_buttonGroup.add(btnClear);
		panel_empty2 = new JPanel();
		panel_bottom.add(panel_empty2);
		
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnClear.addActionListener(this);
	}

	public void refreshTablePanel() {
		backgroundPanel.remove(panel_tableShowTagInfo);
		String params[] = { "ID","ID", "Nubmer", "Counts", "IP/COM" };
		baseTableModule = new DefaultTableModel(null, params);
		table_showTagInfo = new JTable(baseTableModule);
		TableTools.setTableStyle(table_showTagInfo);
		DefaultTableColumnModel dcm = (DefaultTableColumnModel) table_showTagInfo.getColumnModel();
		dcm.getColumn(0).setMinWidth(0); 
		dcm.getColumn(0).setMaxWidth(0);
		jScrollPane = new JScrollPane(table_showTagInfo);
		TableTools.setJspStyle(jScrollPane);
		panel_tableShowTagInfo = new JPanel(new BorderLayout());
		panel_tableShowTagInfo.setOpaque(false);
		panel_tableShowTagInfo.add(jScrollPane);
		backgroundPanel.add(panel_tableShowTagInfo, "Center");
		backgroundPanel.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object instanceof JButton){
			if(object == btnStart){
				readCard();
			}else if(object == btnStop){
				stop();
			}else if(object == btnClear){
				lblReadTagCounts.setText("0");
				lblResultTags.setText("0");
				baseTableModule.setRowCount(0);
			}
		}
	}

	private void stop() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		readerService.stop(ReaderUtil.connectList.get(ReaderUtil.selectDevice));
	}

	private void readCard() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		readerService.startMultiTag(reader, new ReadData(reader));
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		refreshTablePanel();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		refreshTablePanel();
	}

	class ReadData implements CallBack{
		Reader reader = null;
		public ReadData(Reader reader ){
			this.reader = reader;
		}
		@Override
		public void getReadData(String data, int antNo, String deviceId) {
			BasicTable.tableInfoShow(reader,data,antNo,deviceId);
		}
	}
}
