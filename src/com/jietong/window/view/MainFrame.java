package com.jietong.window.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import com.jietong.window.util.LanguageUtil;
import com.jietong.window.util.ReaderUtil;
import javax.swing.border.TitledBorder;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MainFrame extends JFrame {
	public static final long serialVersionUID = 1L;
	/**
	 * main panel
	 */
	public static JPanel panel_main;
	private static JPanel panel_bottomInfo;
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("2.4G_MultiDev_Java_V1.00");
		// window display style
		String manager = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		try {
			UIManager.setLookAndFeel(manager);
		} catch (Exception e) {
			// e.printStackTrace();
			// Message.Show("Display window style exception","");
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1101, 670);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		panel_main = new JPanel();
		contentPane.add(panel_main, BorderLayout.CENTER);
		panel_main.setLayout(null);
		init();
	}

	public static void infoShow() {
		panel_bottomInfo = new JPanel();
		panel_bottomInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_bottomInfo.setBounds(0, 602, 1083, 30);
		panel_main.add(panel_bottomInfo);
		panel_bottomInfo.add(new FootInfoShowManagerJPanel().backgroundPanel);
		panel_bottomInfo.setLayout(new GridLayout(1, 0, 0, 0));
	}

	public static void commonality() {
		JPanel panel_leftCommunication = new JPanel();
		panel_leftCommunication.setBounds(10, 19, 275, 573);
		panel_main.add(panel_leftCommunication);
		panel_leftCommunication.add(new ConnectDeviceManagerJPanel().backgroundPanel);
		panel_leftCommunication.setLayout(new GridLayout(1, 0, 0, 0));
	}


	public void init() {
		mainPanel();
		commonality();
		infoShow();
	}

	public void mainPanel() {
		//language(); 
		JTabbedPane panel_table = new JTabbedPane(JTabbedPane.TOP);
		panel_table.setBounds(294, 19, 780, 573);
		panel_main.add(panel_table);
		//jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		panel_table.addTab(LanguageUtil.rs.getString("tabBasicOperation"),new TagOperationManagerJPanel().backgroundPanel);
		panel_table.addTab(LanguageUtil.rs.getString("tabEquipmentParameterSetting"),new DeviceParamsSettingManagerJPanel().backgroundPanel);
		panel_table.addTab(LanguageUtil.rs.getString("tabCommunicationParameterSetting"),new CommParamsSettingManagerJPanel().backgroundPanel);
	}
	
	public void language(){
		JPanel panel_language = new JPanel();
		panel_language.setBounds(907, 4, 167, 26);
		panel_main.add(panel_language);
		panel_language.setLayout(new GridLayout(1, 2, 0, 0));
		JLabel lbl_language = new JLabel("Language:");
		lbl_language.setHorizontalAlignment(SwingConstants.CENTER);
		panel_language.add(lbl_language);
		String[] language = { "\u7B80\u4F53\u4E2D\u6587", "English" };
		JComboBox cbb_language = new JComboBox(language);
		panel_language.add(cbb_language);
	}
}
