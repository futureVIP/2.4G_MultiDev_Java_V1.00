package com.jietong.window.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jietong.window.util.LanguageUtil;
import com.jietong.window.util.ReaderUtil;

public class FootInfoShowManagerJPanel {
	public JPanel backgroundPanel;
	JPanel tablePanel;
	private JLabel lblOperationHint;

	public FootInfoShowManagerJPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		initTablePanel();
	}

	public void initTablePanel() {
		tablePanel = new JPanel();
		tablePanel.setOpaque(false);
		backgroundPanel.add(tablePanel, "Center");
		tablePanel.setLayout(new GridLayout(0, 2, 0, 0));

		lblOperationHint = new JLabel(LanguageUtil.rs.getString("lblOperationHint") + "  :  ");
		lblOperationHint.setFont(new Font("\u5B8B\u4F53", Font.PLAIN, 16));
		lblOperationHint.setHorizontalAlignment(SwingConstants.RIGHT);
		tablePanel.add(lblOperationHint);

		ReaderUtil.lblShowInfo = new JLabel("");
		ReaderUtil.lblShowInfo.setForeground(Color.BLUE);
		ReaderUtil.lblShowInfo.setFont(new Font("\u5B8B\u4F53", Font.PLAIN, 16));
		tablePanel.add(ReaderUtil.lblShowInfo);
	}
}
