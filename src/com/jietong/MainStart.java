package com.jietong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import serialports.service.impl.SerialPortServiceImpl;
import com.jietong.rfid.uhf.entity.ConnectInfo;
import com.jietong.rfid.uhf.entity.EPC;
import com.jietong.rfid.uhf.tool.CallBack;
import com.jietong.rfid.util.ReaderUtil;
import com.jietong.util.Message;
import com.jietong.window.basicOperation.BasicTableHeadUI;
import com.jietong.window.basicOperation.ConnectTableHeadUI;
import com.jietong.window.basicOperation.SocketConnect;
import com.jietong.window.commsetup.CommParams;
import com.jietong.window.deviceparams.DeviceParam;
import com.jietong.window.tagoperation.TagOperation;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MainStart extends JFrame {
	public static final long serialVersionUID = 1L;
	/**
	 * main 容器
	 */
	public static JPanel contentPane;
	/**
	 * main panel
	 */
	public static JPanel panel_main;
	/**
	 * 多页选项板
	 */
	public static JTabbedPane tabbedPane;
	/**
	 * 用中文和英文切换下拉框的内容
	 */
	public static String[] language = { "简体中文", "English" };
	/**
	 * 默认加载中文
	 */
	public static ResourceBundle rs = ResourceBundle.getBundle("language",
			Locale.CHINA);
	/**************************************************************************
	 * 基本操作 start
	 *************************************************************************/
	/**
	 * 通用top panel
	 */
	public static JPanel panel_general;
	/***********************************
	 * 通用left
	 ***********************************/
	/**
	 * 通用left panel
	 */
	public static JPanel panel_generalLeft;
	/**
	 * 通讯模式
	 */
	public static JPanel panel_serialPortConnect;
	/**
	 * 在线设备panel
	 */
	public static JPanel panel_onlineDevice;
	/**
	 * 在线设备滚动条
	 */
	public static JScrollPane scrollPane_tree;
	/**
	 * 显示通讯方式IP或COM
	 */
	public static DefaultTableModel tableModel;
	/**
	 * 显示通讯方式IP或COM
	 */
	public static DefaultTableModel tableConnectModel;
	/**
	 * 
	 */
	public static DefaultMutableTreeNode[] nodeTree = null;
	/**
	 * 在线设备树形顶级
	 */
	public static DefaultMutableTreeNode node_1 = new DefaultMutableTreeNode(
			MainStart.rs.getString("LVOnlineEquipment"));
	/**
	 * 在线设备的树形默认模式
	 */
	public static DefaultTreeModel model = new DefaultTreeModel(node_1, true);
	/**
	 * 在线设备树形二级
	 */
	public static DefaultMutableTreeNode node_2 = new DefaultMutableTreeNode(
			MainStart.rs.getString("LVOnlineEquipment"));
	/**
	 * 
	 */
	public static DefaultMutableTreeNode note = new DefaultMutableTreeNode();
	/**
	 * 获取选中节点的父节点
	 */
	public static DefaultMutableTreeNode parent = new DefaultMutableTreeNode();
	/**
	 * 新增的IP地址
	 */
	public static JPanel panel_newAddIPAdress;
	/**
	 * 通讯方式连接和断开按扭
	 */
	public static JPanel panel_connectAndDisconnect;
	/**
	 * 通讯方式-断开
	 */
	public static JButton btn_disconnect;

	/**
	 * 显示标签table
	 */
	public static JTable tbl_showConnectInfo;

	/***********************************
	 * 通用right
	 ***********************************/
	/**
	 * 通用right panel
	 */
	public static JPanel panel_generalRight;
	/**
	 * 显示标签panel
	 */
	public static JPanel panel_readerData;
	/***************************
	 * 显示标签信息
	 **************************/
	/**
	 * 显示标签信息panel
	 */
	public static JPanel panel_tableDataShow;
	/**
	 * 显示标签table
	 */
	public static JTable tbl_showTagInfo;
	/**
	 * 显示标签信息滚动条
	 */
	public static JScrollPane sp_showTagInfo;
	/**
	 * 选择保存文件
	 */
	public static boolean chooseSaveFile;
	/**************************************************************************
	 * 基本操作 end
	 *************************************************************************/
	/**************************************************************************
	 * 标签操作start
	 *************************************************************************/
	/**
	 * 标签操作top panel
	 */
	public static JPanel panel_tagAccess;
	/********************
	 * 快速读写
	 *******************/
	public static JPanel panel_fastReadWrite;
	/**
	 * 快写数据
	 */
	public static JLabel labFastReadWrite;
	/**
	 * 读取EPC数据
	 */
	public static JButton btnEpc;
	/**
	 * 快写标签
	 */
	public static JButton btnFastWrite;
	/**
	 * 快写标签滚动条
	 */
	public static JScrollPane scrollPane;
	/**
	 * 快写标签
	 */
	public static JTextArea tfFastReadWrite;
	/**
	 * 指定读写区域按扭panel
	 */
	public static JPanel panel_Designated;
	/**
	 * 连续读卡计数
	 */
	public static int counts = 1;
	/**
	 * 连续读卡定时器
	 */
	public static Timer timer = null;
	/**
	 * 访问密码
	 */
	public static JLabel lblLockAccessPwd;
	/**
	 * 访问密码
	 */
	public static JTextField tfLockAccessPwd;
	/**
	 * 灭活密码
	 */
	public static JLabel lblKillPwd;
	/**
	 * 灭活密码
	 */
	public static JTextField tfKillPwd;
	/**
	 * 锁卡区域
	 */
	public static JLabel labLockBank;
	/**
	 * 锁卡区域
	 */
	public static JComboBox cbbLockBank;
	/**
	 * 锁毁标签
	 */
	public static JButton btnKillTag;
	/**
	 * 标签加解锁
	 */
	public static JPanel panel_tagUnlockAndLock;
	/**
	 * 标签解锁
	 */
	public static JButton btnUnlockTag;
	/**
	 * 标签初始化
	 */
	public static JButton btnInitTag;
	/**
	 * 标签加锁
	 */
	public static JButton btnLockTag;
	/**************************************************************************
	 * 标签操作end
	 *************************************************************************/
	/**************************************************************************
	 * 通讯参数设置start
	 *************************************************************************/
	/**
	 * 通讯参数设置top panel
	 */
	public static JPanel panel_commParamsSetting;
	/**
	 * 卓岚IP显示table
	 */
	public static DefaultTableModel ZLtableModel;
	/**************************************************************************
	 * 通讯参数设置end
	 *************************************************************************/
	/**************************************************************************
	 * 设备参数start
	 *************************************************************************/
	/**
	 * 设备参数
	 */
	public static JPanel panel_parametersSetup;
	/**********************
	 * 设备参数right_panel_end
	 **********************/
	/**************************************************************************
	 * 设备参数end
	 *************************************************************************/
	/**
	 * 判断是否在连续读卡
	 */
	public static boolean isContinueReadCard = false;

	/**
	 * 底部信息提示
	 */
	public static JPanel panel_infoShow;
	/**
	 * 底部信息提示
	 */
	public static JLabel lbl_operaResultShow;
	/**
	 * 底部信息提示
	 */
	public static JLabel lbl_infoContent;

	public static JPanel panel_commParamsSet;
	public static JPanel panel_netParam;
	public static JPanel panel_wifiSetAnd3G_4G;
	public static JPanel panel_MACAddressParamBottom;
	public static JPanel panel_netParamBtnGroup;
	public static JPanel panel_netParamLbl;
	public static JPanel panel_netParamContent;
	public static JLabel lbl_dataOutputPort;
	public static JLabel lbl_deviceID;
	public static JLabel lbl_destinationPort;
	public static JLabel lbl_destinationIP;
	public static JLabel lbl_port;
	public static JLabel lbl_dns;
	public static JLabel lbl_gateway;
	public static JLabel lbl_subnetMask;
	public static JLabel lbl_readIDAddress;
	public static JLabel lbl_mac;
	public static JTextField tf_mac;
	public static JTextField tf_readIPAddress;
	public static JTextField tf_subnetMask;
	public static JTextField tf_gateway;
	public static JTextField tf_dns;
	public static JTextField tf_port;
	public static JTextField tf_destinationIP;
	public static JTextField tf_destinationPort;
	public static JTextField tf_deviceID;
	public static JComboBox cbb_dataOutputPort;
	public static JButton btn_netParamQuery;
	public static JButton btn_netParamSet;
	public static JPanel panel_wifiSet;
	public static JPanel panel_3G_4G;
	public static JPanel panel_wifiSetContent;
	public static JPanel panel_wifiParamBtnGroup;
	public static JButton btn_wifiParamQuery;
	public static JButton btn_wifiParamSet;
	public static JPanel panel_wifiSetLeftLbl;
	public static JPanel panel_wifiSetLeftContent;
	public static JPanel panel_wifiSetRightLbl;
	public static JPanel panel_wifiSetRightContent;
	public static JLabel lbl_ssid;
	public static JLabel lbl_APEncryptionPattern;
	public static JLabel lbl_APEncryptionArithmetic;
	public static JLabel lbl_password;
	public static JLabel lbl_readerIP;
	public static JTextField tf_ssid;
	public static JComboBox cbb_APEncryptionPattern;
	public static JComboBox cbb_APEncryptionArithmetic;
	public static JTextField tf_password;
	public static JTextField tf_readerIP;
	public static JLabel lbl_subnetMaskRight;
	public static JLabel lbl_gatewayRight;
	public static JLabel lbl_serverIP;
	public static JLabel lbl_serverPort;
	public static JTextField tf_subnetMaskRight;
	public static JTextField tf_gatewayRight;
	public static JTextField tf_serverIP;
	public static JTextField tf_serverPort;
	public static JPanel panel_3G_4GBtnGroup;
	public static JButton btn_3G4GQuery;
	public static JButton btn_3G4GSet;
	public static JPanel panel_3G_4GLeftLbl;
	public static JPanel panel_3G_4GLeftContent;
	public static JPanel panel_3G_4GBtnGroup_1;
	public static JPanel panel_3G_4GRightContent;
	public static JLabel lbl_apn;
	public static JLabel lbl_userName;
	public static JLabel lbl_serverIP_1;
	public static JTextField tf_apn;
	public static JTextField tf_userName;
	public static JTextField tf_serverIP_1;
	public static JLabel lbl_reserve;
	public static JLabel lbl_userPwd;
	public static JLabel lbl_serverPortRight;
	public static JTextField tf_reserve;
	public static JTextField tf_userPWD;
	public static JTextField tf_serverPortRight;
	public static JPanel panel_MACAddressParam;
	public static JPanel panel_MACAddressLbl;
	public static JLabel lbl_MACAddress;
	public static JLabel lbl_localhostID;
	public static JPanel panel_MACContent;
	public static JTextField tf_MACAddress;
	public static JTextField tf_localhostID;
	public static JPanel panel_MACRight;
	public static JLabel lbl_localhostModelNumber;
	public static JTextField tf_localhostModelNumber;
	public static JPanel panel_MACAddressBtnGroup;
	public static JButton btn_MACQuery;
	public static JButton btn_MACSet;
	public static JPanel panel_tagOpera;
	public static JPanel panel_IOOpera;
	public static JPanel panel_IOGroup;
	public static JPanel panel_IOButtonGroup;
	public static JButton btn_ReadIOInputStatus;
	public static JButton btn_setIOInputStatus;
	public static JTextField tf_startAddress;
	public static JTextField tf_length;
	public static JPanel panel_commonlyUsedParametersSet;
	public static JPanel panel_commonlyUsedParametersSetContent;
	public static JPanel panel_workPattern;
	public static JPanel panel_parametersSet;
	public static JLabel lbl_workingFrequency;
	public static JLabel lbl_attenuationCoefficient;
	public static JTextField tf_workingFrequency;
	public static JLabel lbl_workingFrequencyRange;
	public static JLabel lbl_tagType;
	public static JTextField tf_tagType;
	public static JLabel lbl_tagTypeRange;
	public static JTextField tf_attenuationCoefficient;
	public static JLabel lbl_attenuationCoefficientRange;
	public static JLabel lbl_transmittedPower;
	public static JTextField tf_transmittedPower;
	public static JLabel lbl_transmittedPowerRange;
	public static JPanel panel_readTagPattern;
	public static JLabel lbl_readTagPattern;
	public static JComboBox cbb_readTagPattern;
	public static JPanel panel_commonlyUsedParametersSetButtonGroup;
	public static JButton btn_readCommonlyUsedParameters;
	public static JButton btn_setCommonlyUsedParameters;
	public static JLabel lbl_dataCommunicationMode;
	public static JComboBox cbb_dataCommunicationMode;
	public static JPanel panel_buzzer;
	public static JButton btn_reset;
	public static JPanel panel_commonlyOperaLbl;
	public static JLabel lbl_dataCommunicationMode_1;
	public static JLabel lbl_buzzer;
	public static JLabel lbl_carrierTesting;
	public static JLabel lbl_attenuationCoefficientOpera;
	public static JLabel lbl_readerWriterID;
	public static JLabel lbl_restartTheModule;
	public static JComboBox cbb_restartTheModule;
	public static JButton btn_restartTheModule;
	public static JComboBox cbb_dataCommunicationMode_1;
	public static JButton btn_dataCommunicationModeSet;
	public static JPanel panel_commonlyUsedOperaBtnGroup;
	public static JButton btn_buzzerOpenSet;
	public static JButton btn_carrierTestingSet;
	public static JButton btn_attenuationCoefficientSet;
	public static JButton btn_readerWriterIDSet;
	public static JRadioButton rb_buzzerOpenStatus;
	public static JRadioButton rb_buzzerCloseStatus;
	public static JButton btn_carrierTesting;
	public static JTextField tf_attenuationCoefficientOpera;
	public static JTextField tf_readerWriterID;
	public static JButton btn_readerWriterIDQuery;
	public static JButton btn_attenuationCoefficientQuery;
	public static JLabel lbl_resultTag;
	public static JCheckBox chkSingleDevice;
	public static JLabel lbl_resultCount;
	public static JButton btn_start;
	public static JButton btn_stop;
	public static JButton btn_readTagClear;
	public static JLabel lbl_dataRefreshTimeInterval;
	public static JTextField tf_timeInterval;
	public static JLabel lbl_ms;
	public static JButton btn_timeIntervalSet;
	public static JButton btn_queryVersion;
	public static JLabel lbl_versionInfo;
	public static JCheckBox chk_IO1;
	public static JCheckBox chk_IO8;
	public static JCheckBox chk_IO7;
	public static JCheckBox chk_IO6;
	public static JCheckBox chk_IO5;
	public static JCheckBox chk_IO4;
	public static JCheckBox chk_IO3;
	public static JCheckBox chk_IO2;
	public static JRadioButton rb_buzzerOpen;
	public static JRadioButton rb_buzzerClose;
	public static JRadioButton rb_ledClose;
	public static JRadioButton rb_ledOpen;
	public static JTextField tf_localhostIP;
	public static JTextField tf_localhostPort;
	public static JLabel lbl_serialPortNo;
	public static JButton btn_getIPAddress;
	public static JButton btn_openAndCloseServer;
	public static JComboBox cbb_serialPortNo;
	public static JButton btn_refresh;
	public static JScrollPane scrollPane_commInfo;
	public static ArrayList<ConnectInfo> listConnect = new ArrayList<ConnectInfo>();
	public static ArrayList<Map<String, Object>> listMap;
	public static SocketConnect connect = new SocketConnect();
	private JButton btn_net_params_default_set;
	SerialPortServiceImpl serialPortService = new SerialPortServiceImpl();
	private List<EPC> listEPC = new ArrayList<EPC>();

	/**
	 * Create the frame.
	 */
	public MainStart() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Object[] options = { "Yes", "No" };
				JOptionPane pane2 = new JOptionPane(
						MainStart.rs.getString("MainWindowFormClosing"),
						JOptionPane.QUESTION_MESSAGE,
						JOptionPane.YES_NO_OPTION, null, options, options[1]);
				JDialog dialog = pane2.createDialog(MainStart.rs
						.getString("ClosePrompt"));
				dialog.setVisible(true);
				Object selectedValue = pane2.getValue();
				if (selectedValue == null || selectedValue == options[1]) {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 这个是关键
				} else if (selectedValue == options[0]) {
					setDefaultCloseOperation(EXIT_ON_CLOSE);
				}
			}
		});
		init();
		mainPanel();
		// 操作界面公共方法
		commonality();
		// 常用的操作
		general();
		// 标签操作
		tagOperation();
		// 参数设置
		parametersSetup();
		// 通讯设置
		commParamsSetting();
		// 底部信息提示
		infoShow();

		baseOperationOnclick();

		commonalityOnclick();

		tagOperationOnclick();

		deviceParamOnclick();

		commParamOnclick();

		btn_getIPAddress.doClick();
	}

	/**
	 * 底部信息提示
	 */
	public static void infoShow() {
		JPanel panel_bottowInfo = new JPanel();
		panel_bottowInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_bottowInfo.setBounds(0, 602, 1083, 30);
		panel_main.add(panel_bottowInfo);
		panel_bottowInfo.setLayout(null);

		panel_infoShow = new JPanel();
		panel_infoShow.setBounds(278, 1, 803, 28);
		panel_bottowInfo.add(panel_infoShow);
		panel_infoShow.setLayout(null);

		lbl_operaResultShow = new JLabel(
				"\u64CD\u4F5C\u7ED3\u679C\u63D0\u793A\uFF1A");
		lbl_operaResultShow.setBounds(10, 10, 84, 15);
		panel_infoShow.add(lbl_operaResultShow);

		lbl_infoContent = new JLabel("");
		lbl_infoContent.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_infoContent.setForeground(Color.BLUE);
		lbl_infoContent.setBounds(104, 10, 579, 15);
		panel_infoShow.add(lbl_infoContent);
	}

	public static void commonality() {
		JPanel panel_leftCommunication = new JPanel();
		panel_leftCommunication.setBounds(0, 1, 278, 598);
		panel_main.add(panel_leftCommunication);
		panel_leftCommunication.setLayout(null);
		panel_generalLeft = new JPanel();
		panel_generalLeft.setBounds(0, 0, 278, 598);
		panel_leftCommunication.add(panel_generalLeft);
		panel_generalLeft.setOpaque(false);
		panel_serialPortConnect = new JPanel();
		panel_serialPortConnect.setBounds(9, 133, 269, 60);
		panel_serialPortConnect.setToolTipText("");
		panel_serialPortConnect.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u4E32\u53E3\u8FDE\u63A5",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_onlineDevice = new JPanel();
		panel_onlineDevice.setBounds(13, 196, 265, 347);
		panel_newAddIPAdress = new JPanel();
		panel_newAddIPAdress.setBounds(13, 544, 265, 54);
		panel_newAddIPAdress.setLayout(null);
		panel_serialPortConnect.setLayout(null);

		ButtonGroup bg = new ButtonGroup();
		panel_generalLeft.setLayout(null);
		panel_generalLeft.add(panel_onlineDevice);
		panel_onlineDevice.setLayout(new GridLayout(0, 1, 0, 0));
		scrollPane_commInfo = new JScrollPane();
		panel_onlineDevice.add(scrollPane_commInfo);
		panel_generalLeft.add(panel_newAddIPAdress);
		/**
		 * table
		 */
		panel_onlineDevice.add(scrollPane_commInfo);
		String columnHeader[] = { "序号", "IP/COM", "端口", "状态", "ID" };
		// 表格模型对象
		tableConnectModel = new DefaultTableModel(null, columnHeader);
		tbl_showConnectInfo = new JTable(tableConnectModel);
		setConnectTableStyle(tbl_showConnectInfo);
		scrollPane_commInfo.setViewportView(tbl_showConnectInfo);

		/**
		 * table
		 */
		JLabel lbl_softwareVersion = new JLabel(
				"\u8F6F\u4EF6\u7248\u672C\u53F7\uFF1A");
		lbl_softwareVersion.setBounds(10, 6, 72, 15);
		panel_newAddIPAdress.add(lbl_softwareVersion);

		lbl_versionInfo = new JLabel("");
		lbl_versionInfo.setBounds(81, 6, 123, 15);
		panel_newAddIPAdress.add(lbl_versionInfo);

		btn_queryVersion = new JButton("\u67E5\u8BE2");
		btn_queryVersion.setBounds(42, 28, 65, 23);
		panel_newAddIPAdress.add(btn_queryVersion);

		btn_refresh = new JButton("\u5237\u65B0");
		btn_refresh.setBounds(139, 28, 65, 23);
		panel_newAddIPAdress.add(btn_refresh);
		panel_generalLeft.add(panel_serialPortConnect);

		lbl_serialPortNo = new JLabel("\u4E32\u53E3\u53F7\uFF1A");
		lbl_serialPortNo.setBounds(16, 25, 54, 15);
		panel_serialPortConnect.add(lbl_serialPortNo);

		cbb_serialPortNo = new JComboBox();
		cbb_serialPortNo.setBounds(65, 20, 79, 25);
		panel_serialPortConnect.add(cbb_serialPortNo);
		btn_disconnect = new JButton("\u8FDE\u63A5");
		btn_disconnect.setBounds(159, 20, 90, 25);
		panel_serialPortConnect.add(btn_disconnect);

		JPanel panel_autoGetConnectInfo = new JPanel();
		panel_autoGetConnectInfo.setBorder(new TitledBorder(null,
				"\u81EA\u52A8\u83B7\u53D6\u8FDE\u63A5\u4FE1\u606F",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_autoGetConnectInfo.setBounds(10, 2, 268, 51);
		panel_generalLeft.add(panel_autoGetConnectInfo);
		panel_autoGetConnectInfo.setLayout(null);

		btn_getIPAddress = new JButton("\u83B7\u53D6");
		btn_getIPAddress.setBounds(89, 19, 84, 23);
		panel_autoGetConnectInfo.add(btn_getIPAddress);

		JPanel panel_netConnect = new JPanel();
		panel_netConnect.setLayout(null);
		panel_netConnect.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u7F51\u7EDC\u8FDE\u63A5",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_netConnect.setBounds(10, 52, 268, 84);
		panel_generalLeft.add(panel_netConnect);

		JLabel lbl_localhostIP = new JLabel("\u672C\u673AIP\uFF1A");
		lbl_localhostIP.setBounds(14, 22, 54, 15);
		panel_netConnect.add(lbl_localhostIP);

		JLabel lbl_serverPort_1 = new JLabel("\u7AEF\u53E3\uFF1A");
		lbl_serverPort_1.setBounds(14, 53, 54, 15);
		panel_netConnect.add(lbl_serverPort_1);

		tf_localhostIP = new JTextField();
		tf_localhostIP.setBounds(64, 19, 183, 24);
		panel_netConnect.add(tf_localhostIP);
		tf_localhostIP.setColumns(10);

		tf_localhostPort = new JTextField();
		tf_localhostPort.setBounds(64, 52, 80, 22);
		panel_netConnect.add(tf_localhostPort);
		tf_localhostPort.setColumns(10);

		btn_openAndCloseServer = new JButton("\u5F00\u542F\u670D\u52A1\u5668");

		btn_openAndCloseServer.setBounds(154, 51, 93, 23);
		panel_netConnect.add(btn_openAndCloseServer);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainStart frame = new MainStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 操作树形_方法未用
	 */
	public void opertaionTree() {
		// tree_onlineDevice.setModel(new DefaultTreeModel(node_2));
		// if (null != nodeTree && nodeTree.length > 0) {
		// for (int i = 0; i < nodeTree.length; i++) {
		// node_1.add(nodeTree[i]);
		// }
		// }
		// node_2.add(node_1);
	}

	public void init() {
		setTitle("2.4G_MultiDev_Java_V1.00");
		// window display style
		String manager = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		try {
			UIManager.setLookAndFeel(manager);
		} catch (Exception e) {
			// e.printStackTrace();
			// Message.Show("Display window style exception",
			// "");
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1101, 670);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public static void mainPanel() {
		panel_main = new JPanel();
		contentPane.add(panel_main, BorderLayout.CENTER);
		panel_main.setLayout(null);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(277, 10, 798, 589);
		panel_main.add(tabbedPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addComponent(panel_main,
				GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addComponent(panel_main, GroupLayout.DEFAULT_SIZE,
								515, Short.MAX_VALUE).addGap(0)));
		contentPane.setLayout(gl_contentPane);
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
		tbl_showTagInfo.getTableHeader().setPreferredSize(
				new Dimension(450, 30));
		tbl_showTagInfo.setRowHeight(25);
		tbl_showTagInfo.setEnabled(false);
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		tbl_showTagInfo.setDefaultRenderer(Object.class, tcr);
		// 设置table列头居中显示
		((DefaultTableCellRenderer) tbl_showTagInfo.getTableHeader()
				.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tbl_showTagInfo.setPreferredScrollableViewportSize(new Dimension(400,
				260));
		// 设置列宽
		TableColumn firsetColumn = tbl_showTagInfo.getColumnModel()
				.getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(50);
		firsetColumn.setMinWidth(50);

		TableColumn secondColumn = tbl_showTagInfo.getColumnModel()
				.getColumn(1);
		secondColumn.setPreferredWidth(250);
		secondColumn.setMaxWidth(250);
		secondColumn.setMinWidth(250);
	}

	/**
	 * 设置表格样式
	 * 
	 * @param panel
	 *            面板(相当于一个html中的div)
	 */
	public static void setConnectTableStyle(JTable tbl_showTagInfo) {
		// 建头组件
		ConnectTableHeadUI ui = new ConnectTableHeadUI();
		// 获得头组件并设置
		tbl_showTagInfo.getTableHeader().setUI(ui);

		// 设置表头的大小，要够长，高度最好要和表头的高度一样，否则会出现多余部分
		tbl_showTagInfo.getTableHeader().setPreferredSize(
				new Dimension(450, 30));
		tbl_showTagInfo.setRowHeight(25);
		tbl_showTagInfo.setEnabled(false);
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		tbl_showTagInfo.setDefaultRenderer(Object.class, tcr);
		// 设置table列头居中显示
		((DefaultTableCellRenderer) tbl_showTagInfo.getTableHeader()
				.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tbl_showTagInfo.setPreferredScrollableViewportSize(new Dimension(400,
				260));
		// 设置列宽
		TableColumn firsetColumn = tbl_showTagInfo.getColumnModel()
				.getColumn(0);
		firsetColumn.setPreferredWidth(30);
		firsetColumn.setMaxWidth(30);
		firsetColumn.setMinWidth(30);

		TableColumn secondColumn = tbl_showTagInfo.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(90);
		secondColumn.setMaxWidth(90);
		secondColumn.setMinWidth(90);
	}

	/**
	 * 基本操作
	 */
	public void general() {
		panel_general = new JPanel();
		panel_general.setOpaque(false);
		panel_general.setToolTipText("");
		tabbedPane.addTab(MainStart.rs.getString("tabGeneral"), null,
				panel_general, null);
		panel_generalRight = new JPanel();
		panel_generalRight.setBounds(10, 0, 783, 560);
		panel_generalRight.setOpaque(false);
		panel_readerData = new JPanel();
		panel_readerData.setBounds(0, 0, 773, 55);
		JPanel panel_tableDataShow = new JPanel();
		panel_tableDataShow.setBounds(0, 67, 773, 490);
		panel_tableDataShow.setLayout(new GridLayout(1, 0, 0, 0));

		sp_showTagInfo = new JScrollPane();
		panel_tableDataShow.add(sp_showTagInfo);
		String columnHeader[] = { MainStart.rs.getString("strLvHeadNo"), "EPC",
				MainStart.rs.getString("strLvHeadCount"),
				MainStart.rs.getString("strLvHeadAntNo"),
				MainStart.rs.getString("gopDevice") };
		// 表格模型对象
		tableModel = new DefaultTableModel(null, columnHeader);
		tbl_showTagInfo = new JTable(tableModel);
		setTableStyle(tbl_showTagInfo);
		sp_showTagInfo.setViewportView(tbl_showTagInfo);
		panel_general.setLayout(null);
		panel_general.add(panel_generalRight);
		panel_generalRight.setLayout(null);
		panel_generalRight.add(panel_readerData);
		panel_readerData.setLayout(null);

		lbl_resultTag = new JLabel("\u603B\u6807\u7B7E\uFF1A");
		lbl_resultTag.setBounds(22, 8, 54, 15);
		panel_readerData.add(lbl_resultTag);

		chkSingleDevice = new JCheckBox("\u5355\u53F0\u8BBE\u5907");
		chkSingleDevice.setBounds(16, 26, 103, 23);
		panel_readerData.add(chkSingleDevice);

		lbl_resultCount = new JLabel("0");
		lbl_resultCount.setBounds(83, 8, 54, 15);
		panel_readerData.add(lbl_resultCount);

		btn_start = new JButton("\u8FDE\u7EED\u5BFB\u5361");

		btn_start.setBounds(136, 8, 81, 41);
		panel_readerData.add(btn_start);

		btn_stop = new JButton("\u505C\u6B62");

		btn_stop.setBounds(415, 8, 81, 41);
		panel_readerData.add(btn_stop);

		btn_readTagClear = new JButton("\u6E05\u7A7A");

		btn_readTagClear.setBounds(506, 8, 81, 41);
		panel_readerData.add(btn_readTagClear);

		lbl_dataRefreshTimeInterval = new JLabel(
				"\u6570\u636E\u5237\u65B0\u65F6\u95F4\u95F4\u9694\uFF1A");
		lbl_dataRefreshTimeInterval.setBounds(601, 8, 112, 15);
		panel_readerData.add(lbl_dataRefreshTimeInterval);

		tf_timeInterval = new JTextField();
		tf_timeInterval.setBounds(601, 27, 66, 21);
		panel_readerData.add(tf_timeInterval);
		tf_timeInterval.setColumns(10);

		lbl_ms = new JLabel("ms");
		lbl_ms.setBounds(678, 30, 27, 15);
		panel_readerData.add(lbl_ms);

		btn_timeIntervalSet = new JButton("\u8BBE\u7F6E");
		btn_timeIntervalSet.setBounds(711, 26, 57, 23);
		panel_readerData.add(btn_timeIntervalSet);
		panel_generalRight.add(panel_tableDataShow);
	}

	/**
	 * 标签操作
	 */
	public void tagOperation() {
		panel_tagAccess = new JPanel();
		tabbedPane.addTab(MainStart.rs.getString("tabTagsOperate"), null,
				panel_tagAccess, null);
		panel_tagAccess.setLayout(null);
		panel_tagOpera = new JPanel();
		panel_tagOpera.setBounds(14, 14, 766, 178);
		panel_tagAccess.add(panel_tagOpera);
		panel_tagOpera.setLayout(null);

		JLabel lbl_startAddress = new JLabel("\u8D77\u59CB\u5730\u5740");
		lbl_startAddress.setBounds(23, 28, 54, 15);
		panel_tagOpera.add(lbl_startAddress);

		tf_startAddress = new JTextField();
		tf_startAddress.setBounds(107, 25, 98, 21);
		panel_tagOpera.add(tf_startAddress);
		tf_startAddress.setColumns(10);

		JLabel lbl_length = new JLabel("\u957F\u5EA6");
		lbl_length.setBounds(215, 28, 54, 15);
		panel_tagOpera.add(lbl_length);

		tf_length = new JTextField();
		tf_length.setBounds(279, 25, 98, 21);
		panel_tagOpera.add(tf_length);
		tf_length.setColumns(10);

		JLabel lbl_dataArea = new JLabel("\u6570\u636E");
		lbl_dataArea.setBounds(23, 65, 54, 15);
		panel_tagOpera.add(lbl_dataArea);

		JTextArea ta_dataArea = new JTextArea();
		ta_dataArea.setBounds(105, 61, 630, 69);
		panel_tagOpera.add(ta_dataArea);

		JPanel panel_tagOperaBtnGroup = new JPanel();
		panel_tagOperaBtnGroup.setBounds(107, 140, 323, 28);
		panel_tagOpera.add(panel_tagOperaBtnGroup);
		panel_tagOperaBtnGroup.setLayout(new GridLayout(1, 3, 15, 0));

		JButton btn_tagRead = new JButton("\u8BFB\u53D6");
		btn_tagRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panel_tagOperaBtnGroup.add(btn_tagRead);

		JButton btn_tagWrite = new JButton("\u5199\u5165");
		panel_tagOperaBtnGroup.add(btn_tagWrite);

		JButton btn_tagOperaClear = new JButton("\u6E05\u7A7A");
		panel_tagOperaBtnGroup.add(btn_tagOperaClear);

		panel_IOOpera = new JPanel();
		panel_IOOpera.setBorder(new TitledBorder(null, "I/O\u64CD\u4F5C",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_IOOpera.setBounds(15, 207, 346, 192);
		panel_tagAccess.add(panel_IOOpera);
		panel_IOOpera.setLayout(null);

		panel_IOGroup = new JPanel();
		panel_IOGroup.setBounds(11, 26, 324, 97);
		panel_IOOpera.add(panel_IOGroup);
		panel_IOGroup.setLayout(new GridLayout(2, 4, 0, 0));

		chk_IO1 = new JCheckBox("I/0  1");
		panel_IOGroup.add(chk_IO1);

		chk_IO2 = new JCheckBox("I/0  2");
		panel_IOGroup.add(chk_IO2);

		chk_IO3 = new JCheckBox("I/0  3");
		panel_IOGroup.add(chk_IO3);

		chk_IO4 = new JCheckBox("I/0  4");
		panel_IOGroup.add(chk_IO4);

		chk_IO5 = new JCheckBox("I/0  5");
		panel_IOGroup.add(chk_IO5);

		chk_IO6 = new JCheckBox("I/0  6");
		panel_IOGroup.add(chk_IO6);

		chk_IO7 = new JCheckBox("I/0  7");
		panel_IOGroup.add(chk_IO7);

		chk_IO8 = new JCheckBox("I/0  8");
		panel_IOGroup.add(chk_IO8);

		panel_IOButtonGroup = new JPanel();
		panel_IOButtonGroup.setBounds(11, 138, 324, 40);
		panel_IOOpera.add(panel_IOButtonGroup);
		panel_IOButtonGroup.setLayout(new GridLayout(1, 2, 50, 0));

		btn_ReadIOInputStatus = new JButton(
				"\u8BFB\u53D6\u8F93\u5165\u72B6\u6001");
		panel_IOButtonGroup.add(btn_ReadIOInputStatus);

		btn_setIOInputStatus = new JButton(
				"\u8BBE\u7F6E\u8F93\u51FA\u72B6\u6001");
		panel_IOButtonGroup.add(btn_setIOInputStatus);
	}

	public void parametersSetup() {
		panel_parametersSetup = new JPanel();
		tabbedPane.addTab(MainStart.rs.getString("tabDeviceParams"), null,
				panel_parametersSetup, null);
		panel_parametersSetup.setLayout(null);
		panel_commonlyUsedParametersSet = new JPanel();
		panel_commonlyUsedParametersSet.setBorder(new TitledBorder(null,
				"\u5E38\u7528\u53C2\u6570\u8BBE\u7F6E", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_commonlyUsedParametersSet.setBounds(10, 0, 347, 560);
		panel_parametersSetup.add(panel_commonlyUsedParametersSet);
		panel_commonlyUsedParametersSet.setLayout(null);

		panel_commonlyUsedParametersSetContent = new JPanel();
		panel_commonlyUsedParametersSetContent.setBounds(10, 26, 321, 526);
		panel_commonlyUsedParametersSet
				.add(panel_commonlyUsedParametersSetContent);
		panel_commonlyUsedParametersSetContent.setLayout(null);
		panel_parametersSet = new JPanel();
		panel_parametersSet.setBorder(new TitledBorder(null,
				"\u53C2\u6570\u8BBE\u7F6E", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_parametersSet.setBounds(10, 10, 304, 174);
		panel_commonlyUsedParametersSetContent.add(panel_parametersSet);
		panel_parametersSet.setLayout(new GridLayout(0, 3, 0, 15));

		lbl_workingFrequency = new JLabel("\u5DE5\u4F5C\u9891\u6BB5");
		lbl_workingFrequency.setHorizontalAlignment(SwingConstants.CENTER);
		panel_parametersSet.add(lbl_workingFrequency);

		tf_workingFrequency = new JTextField();
		tf_workingFrequency.setText("0");
		panel_parametersSet.add(tf_workingFrequency);
		tf_workingFrequency.setColumns(10);

		lbl_workingFrequencyRange = new JLabel("(0-80)");
		panel_parametersSet.add(lbl_workingFrequencyRange);

		lbl_tagType = new JLabel("\u6807\u7B7E\u7C7B\u578B");
		lbl_tagType.setHorizontalAlignment(SwingConstants.CENTER);
		panel_parametersSet.add(lbl_tagType);

		tf_tagType = new JTextField();
		tf_tagType.setText("0");
		panel_parametersSet.add(tf_tagType);
		tf_tagType.setColumns(10);

		lbl_tagTypeRange = new JLabel("(0-16)");
		panel_parametersSet.add(lbl_tagTypeRange);

		lbl_attenuationCoefficient = new JLabel("\u8870\u51CF\u7CFB\u6570");
		lbl_attenuationCoefficient
				.setHorizontalAlignment(SwingConstants.CENTER);
		panel_parametersSet.add(lbl_attenuationCoefficient);

		tf_attenuationCoefficient = new JTextField();
		tf_attenuationCoefficient.setText("0");
		panel_parametersSet.add(tf_attenuationCoefficient);
		tf_attenuationCoefficient.setColumns(10);

		lbl_attenuationCoefficientRange = new JLabel("(0-31)");
		panel_parametersSet.add(lbl_attenuationCoefficientRange);

		lbl_transmittedPower = new JLabel("\u53D1\u5C04\u529F\u7387");
		lbl_transmittedPower.setHorizontalAlignment(SwingConstants.CENTER);
		panel_parametersSet.add(lbl_transmittedPower);

		tf_transmittedPower = new JTextField();
		tf_transmittedPower.setText("7");
		panel_parametersSet.add(tf_transmittedPower);
		tf_transmittedPower.setColumns(10);

		lbl_transmittedPowerRange = new JLabel("(0-7)");
		panel_parametersSet.add(lbl_transmittedPowerRange);

		panel_readTagPattern = new JPanel();
		panel_readTagPattern.setBorder(new TitledBorder(null,
				"\u8BFB\u5361\u6A21\u5F0F", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_readTagPattern.setBounds(10, 262, 300, 60);
		panel_commonlyUsedParametersSetContent.add(panel_readTagPattern);
		panel_readTagPattern.setLayout(new GridLayout(0, 2, 0, 0));

		lbl_readTagPattern = new JLabel("\u8BFB\u5361\u6A21\u5F0F");
		lbl_readTagPattern.setHorizontalAlignment(SwingConstants.CENTER);
		panel_readTagPattern.add(lbl_readTagPattern);

		cbb_readTagPattern = new JComboBox();
		cbb_readTagPattern.setModel(new DefaultComboBoxModel(new String[] {
				"\u6B63\u5E38\u8BFB\u5361\u6A21\u5F0F",
				"\u7B80\u5355\u8BFB\u5361\u72B6\u6001",
				"\u505C\u6B62\u8BFB\u53D6\u72B6\u6001" }));
		panel_readTagPattern.add(cbb_readTagPattern);

		panel_commonlyUsedParametersSetButtonGroup = new JPanel();
		panel_commonlyUsedParametersSetButtonGroup.setBounds(32, 477, 251, 39);
		panel_commonlyUsedParametersSetContent
				.add(panel_commonlyUsedParametersSetButtonGroup);
		panel_commonlyUsedParametersSetButtonGroup.setLayout(new GridLayout(0,
				2, 20, 0));

		btn_readCommonlyUsedParameters = new JButton(
				"\u8BFB\u53D6\u5E38\u7528\u53C2\u6570");

		panel_commonlyUsedParametersSetButtonGroup
				.add(btn_readCommonlyUsedParameters);

		btn_setCommonlyUsedParameters = new JButton(
				"\u8BBE\u7F6E\u5E38\u7528\u53C2\u6570");

		panel_commonlyUsedParametersSetButtonGroup
				.add(btn_setCommonlyUsedParameters);

		panel_workPattern = new JPanel();
		panel_workPattern.setBounds(10, 194, 300, 60);
		panel_commonlyUsedParametersSetContent.add(panel_workPattern);
		panel_workPattern.setBorder(new TitledBorder(null,
				"\u5DE5\u4F5C\u6A21\u5F0F", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_workPattern.setLayout(new GridLayout(0, 2, 0, 0));

		lbl_dataCommunicationMode = new JLabel(
				"\u6570\u636E\u901A\u4FE1\u6A21\u5F0F\uFF1A");
		lbl_dataCommunicationMode.setHorizontalAlignment(SwingConstants.CENTER);
		panel_workPattern.add(lbl_dataCommunicationMode);

		cbb_dataCommunicationMode = new JComboBox();
		cbb_dataCommunicationMode.setModel(new DefaultComboBoxModel(
				new String[] { "RS232", "RS485", "\u97E6\u683926",
						"\u97E6\u683934", "RJ45", "WIFI", "3G/4G" }));
		panel_workPattern.add(cbb_dataCommunicationMode);

		panel_buzzer = new JPanel();
		panel_buzzer.setBounds(10, 332, 301, 60);
		panel_commonlyUsedParametersSetContent.add(panel_buzzer);
		panel_buzzer.setBorder(new TitledBorder(null, "\u8702\u9E23\u5668",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_buzzer.setLayout(new GridLayout(0, 2, 0, 0));

		rb_buzzerOpen = new JRadioButton("\u6253\u5F00");
		rb_buzzerOpen.setHorizontalAlignment(SwingConstants.CENTER);
		panel_buzzer.add(rb_buzzerOpen);
		rb_buzzerClose = new JRadioButton("\u5173\u95ED");
		rb_buzzerClose.setHorizontalAlignment(SwingConstants.LEFT);
		panel_buzzer.add(rb_buzzerClose);

		rb_buzzerOpen.setSelected(true);
		ButtonGroup buzzerGroupBtn = new ButtonGroup();
		buzzerGroupBtn.add(rb_buzzerOpen);
		buzzerGroupBtn.add(rb_buzzerClose);

		JPanel panel_led = new JPanel();
		panel_led.setBounds(10, 402, 304, 60);
		panel_commonlyUsedParametersSetContent.add(panel_led);
		panel_led.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "LED", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_led.setLayout(new GridLayout(0, 2, 0, 0));

		rb_ledOpen = new JRadioButton("\u6253\u5F00");
		rb_ledOpen.setHorizontalAlignment(SwingConstants.CENTER);
		panel_led.add(rb_ledOpen);

		rb_ledClose = new JRadioButton("\u5173\u95ED");
		panel_led.add(rb_ledClose);

		rb_ledOpen.setSelected(true);
		ButtonGroup ledGroupBtn = new ButtonGroup();
		ledGroupBtn.add(rb_ledOpen);
		ledGroupBtn.add(rb_ledClose);

		JPanel panel_commonlyUsedOpera = new JPanel();
		panel_commonlyUsedOpera.setBorder(new TitledBorder(null,
				"\u5E38\u7528\u64CD\u4F5C", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_commonlyUsedOpera.setBounds(367, 0, 426, 560);
		panel_parametersSetup.add(panel_commonlyUsedOpera);
		panel_commonlyUsedOpera.setLayout(null);

		btn_reset = new JButton("\u6062\u590D\u51FA\u5382\u8BBE\u7F6E");

		btn_reset.setBounds(16, 276, 112, 35);
		panel_commonlyUsedOpera.add(btn_reset);

		panel_commonlyOperaLbl = new JPanel();
		panel_commonlyOperaLbl.setBounds(16, 29, 90, 237);
		panel_commonlyUsedOpera.add(panel_commonlyOperaLbl);
		panel_commonlyOperaLbl.setLayout(new GridLayout(0, 1, 0, 0));

		lbl_restartTheModule = new JLabel("\u91CD\u542F\u6A21\u5757");
		panel_commonlyOperaLbl.add(lbl_restartTheModule);

		lbl_dataCommunicationMode_1 = new JLabel(
				"\u6570\u636E\u901A\u4FE1\u6A21\u5F0F");
		panel_commonlyOperaLbl.add(lbl_dataCommunicationMode_1);

		lbl_buzzer = new JLabel("\u8702\u9E23\u5668\u72B6\u6001");
		panel_commonlyOperaLbl.add(lbl_buzzer);

		lbl_carrierTesting = new JLabel("\u8F7D\u6CE2\u6D4B\u8BD5");
		panel_commonlyOperaLbl.add(lbl_carrierTesting);

		lbl_attenuationCoefficientOpera = new JLabel(
				"\u8870\u51CF\u7CFB\u6570(0-31)");
		panel_commonlyOperaLbl.add(lbl_attenuationCoefficientOpera);

		lbl_readerWriterID = new JLabel("\u8BFB\u5199\u5668ID");
		panel_commonlyOperaLbl.add(lbl_readerWriterID);

		cbb_restartTheModule = new JComboBox();
		cbb_restartTheModule.setModel(new DefaultComboBoxModel(new String[] {
				"\u8BFB\u5199\u5668",
				"\u8BFB\u5199\u5668\u5C04\u9891\u6A21\u5757", "3G/4G",
				"WIFI\u6A21\u5757" }));
		cbb_restartTheModule.setBounds(112, 39, 120, 21);
		panel_commonlyUsedOpera.add(cbb_restartTheModule);

		cbb_dataCommunicationMode_1 = new JComboBox();
		cbb_dataCommunicationMode_1.setModel(new DefaultComboBoxModel(
				new String[] { "RS232", "RS485", "\u97E6\u683926",
						"\u97E6\u683934", "RJ45", "WIFI", "3G/4G" }));
		cbb_dataCommunicationMode_1.setBounds(113, 78, 120, 21);
		panel_commonlyUsedOpera.add(cbb_dataCommunicationMode_1);

		panel_commonlyUsedOperaBtnGroup = new JPanel();
		panel_commonlyUsedOperaBtnGroup.setBounds(329, 35, 81, 225);
		panel_commonlyUsedOpera.add(panel_commonlyUsedOperaBtnGroup);
		panel_commonlyUsedOperaBtnGroup.setLayout(new GridLayout(0, 1, 0, 10));

		btn_restartTheModule = new JButton("\u91CD\u542F");
		panel_commonlyUsedOperaBtnGroup.add(btn_restartTheModule);

		btn_dataCommunicationModeSet = new JButton("\u8BBE\u7F6E");

		panel_commonlyUsedOperaBtnGroup.add(btn_dataCommunicationModeSet);

		btn_buzzerOpenSet = new JButton("\u8BBE\u7F6E");

		panel_commonlyUsedOperaBtnGroup.add(btn_buzzerOpenSet);

		btn_carrierTestingSet = new JButton("\u8BBE\u7F6E");
		panel_commonlyUsedOperaBtnGroup.add(btn_carrierTestingSet);
		btn_carrierTestingSet.setVisible(false);

		btn_attenuationCoefficientSet = new JButton("\u8BBE\u7F6E");

		panel_commonlyUsedOperaBtnGroup.add(btn_attenuationCoefficientSet);

		btn_readerWriterIDSet = new JButton("\u8BBE\u7F6E");

		panel_commonlyUsedOperaBtnGroup.add(btn_readerWriterIDSet);

		rb_buzzerOpenStatus = new JRadioButton("\u6253\u5F00");
		rb_buzzerOpenStatus.setBounds(112, 116, 49, 23);
		panel_commonlyUsedOpera.add(rb_buzzerOpenStatus);

		rb_buzzerCloseStatus = new JRadioButton("\u5173\u95ED");
		rb_buzzerCloseStatus.setBounds(181, 115, 49, 23);
		panel_commonlyUsedOpera.add(rb_buzzerCloseStatus);

		rb_buzzerOpenStatus.setSelected(true);
		ButtonGroup buzzerBtnGroup = new ButtonGroup();
		buzzerBtnGroup.add(rb_buzzerOpenStatus);
		buzzerBtnGroup.add(rb_buzzerCloseStatus);

		btn_carrierTesting = new JButton("\u5F00\u542F\u8F7D\u6CE2\u6D4B\u8BD5");

		btn_carrierTesting.setBounds(112, 153, 116, 23);
		panel_commonlyUsedOpera.add(btn_carrierTesting);

		tf_attenuationCoefficientOpera = new JTextField();
		tf_attenuationCoefficientOpera.setBounds(114, 195, 90, 21);
		panel_commonlyUsedOpera.add(tf_attenuationCoefficientOpera);
		tf_attenuationCoefficientOpera.setColumns(10);

		tf_readerWriterID = new JTextField();
		tf_readerWriterID.setBounds(116, 235, 90, 21);
		panel_commonlyUsedOpera.add(tf_readerWriterID);
		tf_readerWriterID.setColumns(10);

		btn_readerWriterIDQuery = new JButton("\u67E5\u8BE2");

		btn_readerWriterIDQuery.setBounds(221, 234, 93, 23);
		panel_commonlyUsedOpera.add(btn_readerWriterIDQuery);

		btn_attenuationCoefficientQuery = new JButton("\u67E5\u8BE2");

		btn_attenuationCoefficientQuery.setBounds(219, 193, 93, 23);
		panel_commonlyUsedOpera.add(btn_attenuationCoefficientQuery);

	}

	/**
	 * 通讯参数设置
	 */
	public void commParamsSetting() {
		panel_commParamsSetting = new JPanel();
		tabbedPane.addTab(MainStart.rs.getString("strTpSetCommParam"), null,
				panel_commParamsSetting, null);

		panel_commParamsSetting.setLayout(null);
		panel_commParamsSet = new JPanel();
		panel_commParamsSet.setBounds(0, 0, 793, 474);
		panel_commParamsSetting.add(panel_commParamsSet);
		panel_commParamsSet.setLayout(null);
		panel_netParam = new JPanel();
		panel_netParam.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u7F51\u7EDC\u53C2\u6570",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_netParam.setBounds(0, 0, 312, 474);
		panel_commParamsSet.add(panel_netParam);
		panel_netParam.setLayout(null);

		panel_netParamBtnGroup = new JPanel();
		panel_netParamBtnGroup.setBounds(30, 416, 256, 39);
		panel_netParam.add(panel_netParamBtnGroup);
		panel_netParamBtnGroup.setLayout(new GridLayout(1, 2, 5, 0));

		btn_netParamQuery = new JButton("\u67E5\u8BE2\u53C2\u6570");

		panel_netParamBtnGroup.add(btn_netParamQuery);

		btn_net_params_default_set = new JButton("\u9ED8\u8BA4\u8BBE\u7F6E");

		panel_netParamBtnGroup.add(btn_net_params_default_set);

		btn_netParamSet = new JButton("\u8BBE\u7F6E\u53C2\u6570");

		panel_netParamBtnGroup.add(btn_netParamSet);

		panel_netParamLbl = new JPanel();
		panel_netParamLbl.setBounds(30, 20, 99, 390);
		panel_netParam.add(panel_netParamLbl);
		panel_netParamLbl.setLayout(new GridLayout(10, 1, 0, 15));

		lbl_mac = new JLabel("MAC");
		panel_netParamLbl.add(lbl_mac);

		lbl_readIDAddress = new JLabel("\u8BFB\u5199\u5668IP\u5730\u5740");
		panel_netParamLbl.add(lbl_readIDAddress);

		lbl_subnetMask = new JLabel("\u5B50\u7F51\u63A9\u7801");
		panel_netParamLbl.add(lbl_subnetMask);

		lbl_gateway = new JLabel("\u7F51\u5173");
		panel_netParamLbl.add(lbl_gateway);

		lbl_dns = new JLabel("DNS");
		panel_netParamLbl.add(lbl_dns);

		lbl_port = new JLabel("\u7AEF\u53E3\u53F7");
		panel_netParamLbl.add(lbl_port);

		lbl_destinationIP = new JLabel("\u76EE\u7684IP");
		panel_netParamLbl.add(lbl_destinationIP);

		lbl_destinationPort = new JLabel("\u76EE\u7684\u7AEF\u53E3");
		panel_netParamLbl.add(lbl_destinationPort);

		lbl_deviceID = new JLabel("\u8BBE\u5907ID");
		panel_netParamLbl.add(lbl_deviceID);

		lbl_dataOutputPort = new JLabel("\u6570\u636E\u8F93\u51FA\u7AEF\u53E3");
		lbl_dataOutputPort.setHorizontalAlignment(SwingConstants.LEFT);
		panel_netParamLbl.add(lbl_dataOutputPort);

		panel_netParamContent = new JPanel();
		panel_netParamContent.setBounds(128, 20, 158, 390);
		panel_netParam.add(panel_netParamContent);
		panel_netParamContent.setLayout(new GridLayout(10, 1, 0, 15));

		tf_mac = new JTextField();
		tf_mac.setHorizontalAlignment(SwingConstants.LEFT);
		tf_mac.setText("AA-00-01-02-03-04");
		panel_netParamContent.add(tf_mac);
		tf_mac.setColumns(10);

		tf_readIPAddress = new JTextField();
		tf_readIPAddress.setText("192.168.0.238");
		panel_netParamContent.add(tf_readIPAddress);
		tf_readIPAddress.setColumns(10);

		tf_subnetMask = new JTextField();
		tf_subnetMask.setText("255.255.255.0");
		panel_netParamContent.add(tf_subnetMask);
		tf_subnetMask.setColumns(10);

		tf_gateway = new JTextField();
		tf_gateway.setText("192.168.0.1");
		panel_netParamContent.add(tf_gateway);
		tf_gateway.setColumns(10);

		tf_dns = new JTextField();
		tf_dns.setText("192.168.0.1");
		panel_netParamContent.add(tf_dns);
		tf_dns.setColumns(10);

		tf_port = new JTextField();
		tf_port.setText("8000");
		panel_netParamContent.add(tf_port);
		tf_port.setColumns(10);

		tf_destinationIP = new JTextField();
		String localhostIP;
		try {
			localhostIP = InetAddress.getLocalHost().getHostAddress();
			tf_destinationIP.setText(localhostIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		panel_netParamContent.add(tf_destinationIP);
		tf_destinationIP.setColumns(10);

		tf_destinationPort = new JTextField();
		tf_destinationPort.setText("8000");
		panel_netParamContent.add(tf_destinationPort);
		tf_destinationPort.setColumns(10);

		tf_deviceID = new JTextField();
		tf_deviceID.setVisible(false);
		tf_deviceID.setText("0000001");
		panel_netParamContent.add(tf_deviceID);
		tf_deviceID.setColumns(10);

		cbb_dataOutputPort = new JComboBox();
		cbb_dataOutputPort.setModel(new DefaultComboBoxModel(
				new String[] { "\u4EE5\u592A\u7F51", "\u4E32\u53E3", "wifi",
						"RS485", "3G/4G" }));
		panel_netParamContent.add(cbb_dataOutputPort);

		panel_wifiSetAnd3G_4G = new JPanel();
		panel_wifiSetAnd3G_4G.setBounds(310, 0, 483, 474);
		panel_commParamsSet.add(panel_wifiSetAnd3G_4G);
		panel_wifiSetAnd3G_4G.setLayout(null);

		panel_wifiSet = new JPanel();
		panel_wifiSet.setBorder(new TitledBorder(null, "wifi\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_wifiSet.setBounds(1, 1, 482, 257);
		panel_wifiSetAnd3G_4G.add(panel_wifiSet);
		panel_wifiSet.setLayout(null);

		panel_wifiSetContent = new JPanel();
		panel_wifiSetContent.setBounds(10, 19, 462, 189);
		panel_wifiSet.add(panel_wifiSetContent);
		
		panel_wifiSetContent.setLayout(null);

		panel_wifiSetLeftLbl = new JPanel();
		panel_wifiSetLeftLbl.setBounds(0, 0, 91, 189);
		panel_wifiSetContent.add(panel_wifiSetLeftLbl);
		panel_wifiSetLeftLbl.setLayout(new GridLayout(5, 1, 0, 0));

		lbl_ssid = new JLabel("SSID");
		panel_wifiSetLeftLbl.add(lbl_ssid);

		lbl_APEncryptionPattern = new JLabel("AP\u52A0\u5BC6\u6A21\u5F0F");
		panel_wifiSetLeftLbl.add(lbl_APEncryptionPattern);

		lbl_APEncryptionArithmetic = new JLabel("AP\u52A0\u5BC6\u7B97\u6CD5");
		panel_wifiSetLeftLbl.add(lbl_APEncryptionArithmetic);

		lbl_password = new JLabel("\u5BC6\u7801");
		panel_wifiSetLeftLbl.add(lbl_password);

		lbl_readerIP = new JLabel("\u8BFB\u5199\u5668IP");
		panel_wifiSetLeftLbl.add(lbl_readerIP);

		panel_wifiSetLeftContent = new JPanel();
		panel_wifiSetLeftContent.setBounds(101, 0, 106, 189);
		panel_wifiSetContent.add(panel_wifiSetLeftContent);
		panel_wifiSetLeftContent.setLayout(new GridLayout(5, 1, 0, 15));

		tf_ssid = new JTextField();
		tf_ssid.setHorizontalAlignment(SwingConstants.LEFT);
		tf_ssid.setText("JT123456");
		panel_wifiSetLeftContent.add(tf_ssid);
		tf_ssid.setColumns(10);

		cbb_APEncryptionPattern = new JComboBox();
		cbb_APEncryptionPattern.setModel(new DefaultComboBoxModel(new String[] {
				"OPEN", "SHARED", "WPAPSK", "WPA2PSK" }));
		panel_wifiSetLeftContent.add(cbb_APEncryptionPattern);

		cbb_APEncryptionArithmetic = new JComboBox();
		cbb_APEncryptionArithmetic.setModel(new DefaultComboBoxModel(
				new String[] { "NONE", "WEP-A", "TKIP", "AES" }));
		panel_wifiSetLeftContent.add(cbb_APEncryptionArithmetic);

		tf_password = new JTextField();
		tf_password.setText("123456");
		panel_wifiSetLeftContent.add(tf_password);
		tf_password.setColumns(10);

		tf_readerIP = new JTextField();
		tf_readerIP.setText("192.168.0.1");
		panel_wifiSetLeftContent.add(tf_readerIP);
		tf_readerIP.setColumns(10);

		panel_wifiSetRightLbl = new JPanel();
		panel_wifiSetRightLbl.setBounds(260, 0, 81, 156);
		panel_wifiSetContent.add(panel_wifiSetRightLbl);
		panel_wifiSetRightLbl.setLayout(new GridLayout(4, 1, 0, 15));

		lbl_subnetMaskRight = new JLabel("\u5B50\u7F51\u63A9\u7801");
		panel_wifiSetRightLbl.add(lbl_subnetMaskRight);

		lbl_gatewayRight = new JLabel("\u7F51\u5173");
		panel_wifiSetRightLbl.add(lbl_gatewayRight);

		lbl_serverIP = new JLabel("\u670D\u52A1\u5668IP");
		panel_wifiSetRightLbl.add(lbl_serverIP);

		lbl_serverPort = new JLabel("\u670D\u52A1\u5668\u7AEF\u53E3");
		panel_wifiSetRightLbl.add(lbl_serverPort);

		panel_wifiSetRightContent = new JPanel();
		panel_wifiSetRightContent.setBounds(351, 0, 106, 156);
		panel_wifiSetContent.add(panel_wifiSetRightContent);
		panel_wifiSetRightContent.setLayout(new GridLayout(4, 1, 0, 15));

		tf_subnetMaskRight = new JTextField();
		tf_subnetMaskRight.setText("255.255.255.0");
		panel_wifiSetRightContent.add(tf_subnetMaskRight);
		tf_subnetMaskRight.setColumns(10);

		tf_gatewayRight = new JTextField();
		tf_gatewayRight.setText("192.168.0.0");
		panel_wifiSetRightContent.add(tf_gatewayRight);
		tf_gatewayRight.setColumns(10);

		tf_serverIP = new JTextField();
		tf_serverIP.setText("192.168.0.101");
		panel_wifiSetRightContent.add(tf_serverIP);
		tf_serverIP.setColumns(10);

		tf_serverPort = new JTextField();
		tf_serverPort.setText("10000");
		panel_wifiSetRightContent.add(tf_serverPort);
		tf_serverPort.setColumns(10);

		panel_wifiParamBtnGroup = new JPanel();
		panel_wifiParamBtnGroup.setBounds(57, 211, 349, 39);
		panel_wifiSet.add(panel_wifiParamBtnGroup);
		
		panel_wifiSet.setVisible(false);
		
		panel_wifiParamBtnGroup.setLayout(new GridLayout(1, 2, 50, 0));

		btn_wifiParamQuery = new JButton("\u67E5\u8BE2\u53C2\u6570");

		panel_wifiParamBtnGroup.add(btn_wifiParamQuery);

		btn_wifiParamSet = new JButton("\u8BBE\u7F6E\u53C2\u6570");

		panel_wifiParamBtnGroup.add(btn_wifiParamSet);

		panel_3G_4G = new JPanel();
		panel_3G_4G.setBorder(new TitledBorder(null, "3G/4G",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3G_4G.setBounds(3, 255, 480, 220);
		panel_wifiSetAnd3G_4G.add(panel_3G_4G);
		panel_3G_4G.setLayout(null);

		panel_3G_4GBtnGroup = new JPanel();
		panel_3G_4GBtnGroup.setBounds(69, 169, 357, 41);
		panel_3G_4G.add(panel_3G_4GBtnGroup);
		panel_3G_4GBtnGroup.setLayout(new GridLayout(1, 2, 60, 0));

		btn_3G4GQuery = new JButton("\u67E5\u8BE2\u53C2\u6570");
		panel_3G_4GBtnGroup.add(btn_3G4GQuery);

		btn_3G4GSet = new JButton("\u8BBE\u7F6E\u53C2\u6570");
		panel_3G_4GBtnGroup.add(btn_3G4GSet);

		panel_3G_4GLeftLbl = new JPanel();
		panel_3G_4GLeftLbl.setBounds(10, 25, 90, 134);
		panel_3G_4G.add(panel_3G_4GLeftLbl);
		panel_3G_4GLeftLbl.setLayout(new GridLayout(3, 1, 0, 15));

		lbl_apn = new JLabel("APN");
		panel_3G_4GLeftLbl.add(lbl_apn);

		lbl_userName = new JLabel("\u7528\u6237\u540D\u79F0");
		panel_3G_4GLeftLbl.add(lbl_userName);

		lbl_serverIP_1 = new JLabel("\u670D\u52A1\u5668IP");
		panel_3G_4GLeftLbl.add(lbl_serverIP_1);

		panel_3G_4GLeftContent = new JPanel();
		panel_3G_4GLeftContent.setBounds(100, 25, 103, 134);
		panel_3G_4G.add(panel_3G_4GLeftContent);
		panel_3G_4GLeftContent.setLayout(new GridLayout(3, 1, 0, 15));

		tf_apn = new JTextField();
		tf_apn.setText("JT123456");
		panel_3G_4GLeftContent.add(tf_apn);
		tf_apn.setColumns(10);

		tf_userName = new JTextField();
		tf_userName.setText("name123");
		panel_3G_4GLeftContent.add(tf_userName);
		tf_userName.setColumns(10);

		tf_serverIP_1 = new JTextField();
		tf_serverIP_1.setText("192.168.1.102");
		panel_3G_4GLeftContent.add(tf_serverIP_1);
		tf_serverIP_1.setColumns(10);

		panel_3G_4GBtnGroup_1 = new JPanel();
		panel_3G_4GBtnGroup_1.setBounds(259, 25, 95, 134);
		panel_3G_4G.add(panel_3G_4GBtnGroup_1);
		panel_3G_4GBtnGroup_1.setLayout(new GridLayout(3, 1, 0, 15));

		lbl_reserve = new JLabel("\u4FDD\u7559");
		panel_3G_4GBtnGroup_1.add(lbl_reserve);

		lbl_userPwd = new JLabel("\u7528\u6237\u5BC6\u7801");
		panel_3G_4GBtnGroup_1.add(lbl_userPwd);

		lbl_serverPortRight = new JLabel("\u670D\u52A1\u5668\u7AEF\u53E3");
		panel_3G_4GBtnGroup_1.add(lbl_serverPortRight);

		panel_3G_4GRightContent = new JPanel();
		panel_3G_4GRightContent.setBounds(354, 25, 95, 134);
		panel_3G_4G.add(panel_3G_4GRightContent);
		panel_3G_4G.setVisible(false);
		panel_3G_4GRightContent.setLayout(new GridLayout(3, 1, 0, 15));
		tf_reserve = new JTextField();
		tf_reserve.setEnabled(false);
		panel_3G_4GRightContent.add(tf_reserve);
		tf_reserve.setColumns(10);

		tf_userPWD = new JTextField();
		tf_userPWD.setText("user123");
		panel_3G_4GRightContent.add(tf_userPWD);
		tf_userPWD.setColumns(10);

		tf_serverPortRight = new JTextField();
		tf_serverPortRight.setText("11000");
		panel_3G_4GRightContent.add(tf_serverPortRight);
		tf_serverPortRight.setColumns(10);

		panel_MACAddressParamBottom = new JPanel();
		panel_MACAddressParamBottom.setBounds(0, 474, 793, 86);
		panel_commParamsSetting.add(panel_MACAddressParamBottom);
		panel_MACAddressParamBottom.setLayout(null);

		panel_MACAddressParam = new JPanel();
		panel_MACAddressParam.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"MAC\u5730\u5740\u53C2\u6570", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_MACAddressParam.setBounds(0, 0, 407, 86);
		panel_MACAddressParamBottom.add(panel_MACAddressParam);
		panel_MACAddressParam.setLayout(null);

		panel_MACAddressLbl = new JPanel();
		panel_MACAddressLbl.setBounds(10, 17, 97, 62);
		panel_MACAddressParam.add(panel_MACAddressLbl);
		panel_MACAddressLbl.setLayout(new GridLayout(2, 1, 0, 0));

		lbl_MACAddress = new JLabel("MAC\u5730\u5740");
		panel_MACAddressLbl.add(lbl_MACAddress);

		lbl_localhostID = new JLabel("\u672C\u673AID");
		panel_MACAddressLbl.add(lbl_localhostID);

		panel_MACContent = new JPanel();
		panel_MACContent.setBounds(106, 17, 130, 62);
		panel_MACAddressParam.add(panel_MACContent);
		panel_MACContent.setLayout(new GridLayout(2, 1, 0, 10));

		tf_MACAddress = new JTextField();
		tf_MACAddress.setText("AA-11-22-33-44-55");
		panel_MACContent.add(tf_MACAddress);
		tf_MACAddress.setColumns(10);

		tf_localhostID = new JTextField();
		tf_localhostID.setText("11223344");
		panel_MACContent.add(tf_localhostID);
		tf_localhostID.setColumns(10);

		panel_MACRight = new JPanel();
		panel_MACRight.setBounds(251, 18, 147, 23);
		panel_MACAddressParam.add(panel_MACRight);
		panel_MACRight.setLayout(new GridLayout(0, 2, 0, 0));

		lbl_localhostModelNumber = new JLabel("\u672C\u673A\u578B\u53F7");
		panel_MACRight.add(lbl_localhostModelNumber);

		tf_localhostModelNumber = new JTextField();
		panel_MACRight.add(tf_localhostModelNumber);
		tf_localhostModelNumber.setColumns(10);

		panel_MACAddressBtnGroup = new JPanel();
		panel_MACAddressBtnGroup.setBounds(249, 54, 148, 24);
		panel_MACAddressParam.add(panel_MACAddressBtnGroup);
		panel_MACAddressBtnGroup.setLayout(new GridLayout(1, 2, 15, 0));

		btn_MACQuery = new JButton("\u67E5\u8BE2");
		panel_MACAddressBtnGroup.add(btn_MACQuery);

		btn_MACSet = new JButton("\u8BBE\u7F6E");
		panel_MACAddressBtnGroup.add(btn_MACSet);
	}

	/**
	 * 通讯参数设置
	 */
	public void commParamOnclick() {
		btn_netParamQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommParams.getNet();
			}
		});

		btn_netParamSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommParams.setNet();
			}
		});

		btn_wifiParamQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommParams.getWifi();
			}
		});

		btn_wifiParamSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommParams.setWifi();
			}
		});

		btn_net_params_default_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_mac.setText("AA-00-01-02-03-04");
				tf_readIPAddress.setText("192.168.0.238");
				tf_subnetMask.setText("255.255.255.0");
				tf_gateway.setText("192.168.0.1");
				tf_dns.setText("192.168.0.1");
				tf_port.setText("8000");
				String localhostIP;
				try {
					localhostIP = InetAddress.getLocalHost().getHostAddress();
					tf_destinationIP.setText(localhostIP);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				tf_destinationPort.setText("8000");
				tf_deviceID.setText("0000001");
				cbb_dataOutputPort.setSelectedIndex(0);
			}
		});
	}

	/**
	 * 设备参数
	 */
	public void deviceParamOnclick() {
		btn_setCommonlyUsedParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.setWorkMode();
			}
		});

		btn_readCommonlyUsedParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.getWorkMode();
			}
		});

		btn_restartTheModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.reStart();
			}
		});

		btn_dataCommunicationModeSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.setTransferMode();
			}
		});

		btn_buzzerOpenSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.setBuzzerState();
			}
		});

		btn_carrierTesting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btn_carrierTesting.getText() == "开启载波测试") {
					DeviceParam.startCarrier();
				} else {
					DeviceParam.stopCarrier();
				}
			}
		});

		btn_attenuationCoefficientSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.setAttenuation();
			}
		});

		btn_attenuationCoefficientQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.getAttenuation();
			}
		});

		btn_readerWriterIDQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.getDevID();
			}
		});

		btn_readerWriterIDSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.setDevID();
			}
		});

		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParam.factoryReset();
			}
		});
	}

	/**
	 * 标签操作
	 */
	public void tagOperationOnclick() {
		btn_ReadIOInputStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagOperation.getGpio();
			}
		});

		btn_setIOInputStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagOperation.setGpio();
			}
		});
	}
	/**
	 * 基本操作
	 */
	public void baseOperationOnclick() {
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isConn = false;
				for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM; i++) {
					if (ReaderUtil.readers[i] != null) {
						isConn = true;
						ReaderUtil.readerService.startMultiTag(ReaderUtil.readers[i],new GetDatas());
					}
				}
			}
		});

		btn_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM; i++) {
					if (ReaderUtil.readers[i] != null) {
						ReaderUtil.readerService.stop(ReaderUtil.readers[i]);
					}
				}
			}
		});

		btn_readTagClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listEPC.clear();
				lbl_resultCount.setText("0");
				tableModel.setRowCount(0);
			}
		});
	}
	
	public static void connect() {
		String serialPort = cbb_serialPortNo.getSelectedItem().toString();
		if (serialPort.length() < 3) {
			return;
		}
		String connection = null;
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM; i++) {
			if (ReaderUtil.readers[i] == null) {
				ReaderUtil.readers[i] = ReaderUtil.readerService.connect(serialPort, 115200);
				if (ReaderUtil.readers[i] != null) {
					connection = "连接成功";
					btn_disconnect.setText("断开");
					addToList(listConnect, serialPort, 0, "连接", 0);
				} else {
					connection = "连接失败";
				}
				lbl_infoContent.setText(connection);
				break;
			}
		}
	}
	
	public void disconnect() {
		boolean isConn = false;
		try {
			for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM; i++) {
				if (ReaderUtil.readers[i] != null) {
					ReaderUtil.readerService.stop(ReaderUtil.readers[i]);
				}
			}
			for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM; i++) {
				if (ReaderUtil.readers[i] != null) {
					isConn = true;
						for (int j = 0; j < listConnect.size(); j++) {
							ConnectInfo connectInfo= listConnect.get(j);
							if(ReaderUtil.readers[i].host.equals(connectInfo.getDeviceNo())&& ReaderUtil.readers[i].host.length() < 5){
								ReaderUtil.readerService.disconnect(ReaderUtil.readers[i]);
								ReaderUtil.readers[i] = null;
								listConnect.remove(j);
								tableConnectModel.removeRow(j);
								break;
							}
						}
				}
			}
			if (!isConn) {
				Message.Show(MainStart.rs.getString("MsgExNodevicetoconnect"),
						"");
			} else {
				btn_disconnect.setText("连接");
				ReaderUtil.connectCount = 0;
			}
		} catch (Exception ex) {
			Message.Show(MainStart.rs.getString("MsgConnectionfailure"), "");// "连接断开失败");
		}
	}

	/**
	 * 通讯连接
	 */
	public void commonalityOnclick() {
		btn_getIPAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<String> devCnt = serialPortService.findSerialPorts();
					String localhostIP = InetAddress.getLocalHost().getHostAddress();
					if (devCnt.size() > 0) {
						cbb_serialPortNo.removeAllItems();
						for (int i = 0; i < devCnt.size(); i++) {
							cbb_serialPortNo.addItem(devCnt.get(i));
						}
					}
					if (localhostIP.length() > 0) {
						tf_localhostIP.setText(localhostIP);
						//String config = "config.port";
						//String port = PropertiesUtils.getProps(config);
						tf_localhostPort.setText("8000");
					}
				} catch (UnknownHostException excep) {
					excep.printStackTrace();
				}
			}
		});

		btn_disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean isConnect = btn_disconnect.getText() == "连接" ? true : false;
				if (isConnect) {
					connect();
				} else {
					disconnect();
				}
			}
		});

		btn_queryVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lbl_versionInfo.setText("");
				for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM
						&& ReaderUtil.readers[i] != null; i++) {
					if (ReaderUtil.readers[i] != null) {
						String version = ReaderUtil.readerService.version(ReaderUtil.readers[i]);
						lbl_versionInfo.setText(version);
					}
				}
			}
		});

		btn_openAndCloseServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String portNo = tf_localhostPort.getText();
				String server = btn_openAndCloseServer.getText();
				int port = Integer.parseInt(portNo);
				if (port < 0) {
					return;
				} else if (server.equals("开启服务器")) {
					btn_openAndCloseServer.setText("关闭服务器");
					String address = null;
					try {
						address = InetAddress.getLocalHost().getHostAddress().toString();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
					connect.startServer(port, 10, address);
					lbl_infoContent.setText("开启服务器成功!");
				} else if (server.equals("关闭服务器")) {
					btn_openAndCloseServer.setText("开启服务器");
					connect.stopServer();
					lbl_infoContent.setText("关闭服务器成功!");
				}
			}
		});
	}

	public static void addToList(final List<ConnectInfo> list, String device,
			int port, String state, int id) {
		// 第一次读入数据
		if (list.isEmpty()) {
			list.clear();
			ConnectInfo connectInfo = new ConnectInfo();
			connectInfo.setId(list.size() + 1);
			connectInfo.setDeviceNo(device);
			connectInfo.setPort(port);
			connectInfo.setState(state);
			list.add(connectInfo);
		} else {
			boolean falg = false;
			for (int i = 0; i < list.size(); i++) {
				ConnectInfo connectInfo = list.get(i);
				// list中有此EPC
				if (device.equals(connectInfo.getDeviceNo())
						&& device.length() < 6) {
					connectInfo.setId(connectInfo.getId() + 1);
					list.set(i, connectInfo);
					falg = true;
					break;
				}
			}
			if (!falg) {
				// list中没有此epc
				ConnectInfo connect = new ConnectInfo();
				connect.setDeviceNo(device);
				connect.setPort(port);
				connect.setState(state);
				list.add(connect);
			}
		}
		tableConnectModel.setRowCount(0);
		int idcount = 1;
		for (ConnectInfo connectInfo : list) {
			Object[] rowValues = { idcount, connectInfo.getDeviceNo(),connectInfo.getPort(), connectInfo.getState(), 0 };
			tableConnectModel.addRow(rowValues);
			idcount++;
		}
	}
	
	public void dataFilter(List<EPC> listEPC, String data, String deviceId) {
		if (listEPC.isEmpty()) {
			EPC epcTag = new EPC();
			epcTag.setId(1);
			epcTag.setData(data);
			epcTag.setDeviceId(deviceId);
			epcTag.setCount(1);
			listEPC.add(epcTag);
		} else {
			for (int i = 0; i < listEPC.size(); i++) {
				int count = listEPC.size();
				EPC mEPC = listEPC.get(i);
				if (data.equals(mEPC.getData()) && deviceId.equals(mEPC.getDeviceId())) {
					mEPC.setCount(mEPC.getCount()+1);
					listEPC.set(i, mEPC);
					break;
				} else if (i == (listEPC.size() - 1)) {
					count++;
					EPC newEPC = new EPC();
					newEPC.setId(count);
					newEPC.setData(data);
					newEPC.setDeviceId(deviceId);
					newEPC.setCount(1);
					listEPC.add(newEPC);
				}
			}
		}
	}
	
	public void addToList(final List<EPC> listEPC2, final String data,final String deviceId) {
		synchronized(this){
			dataFilter(listEPC2, data, deviceId);
			tableModel.setRowCount(0);
			tableInfoShow(listEPC);
			showCount(listEPC);
		}
	}
	
	private void showCount(final List<EPC> listEPC2) {
		Map<String,String> dataMap = new HashMap<String,String>();
		int tagCount = 0;
		for (int i = 0; i < listEPC2.size(); i++) {
			dataMap.put(listEPC2.get(i).getData(), listEPC2.get(i).getData());
		}
		tagCount = dataMap.size();
		lbl_resultCount.setText(String.valueOf(tagCount));
	}

	public synchronized void tableInfoShow(final List<EPC> listEPC) {
		// 添加数据到table中
		for (int j = 0; j < listEPC.size() ; j++) {
			// 获取集合中的数据
			Object[] rowValues = {listEPC.get(j).getId(), listEPC.get(j).getData(),listEPC.get(j).getCount(),null, listEPC.get(j).getDeviceId()};
			tableModel.addRow(rowValues); // 添加一行
		}
	}
	
	
	class GetDatas implements CallBack {
		@Override
		public void getReadData(String data, int antNo,String deviceId) {
			addToList(listEPC, data, deviceId);
			System.out.println(data);
		}
	}
}
