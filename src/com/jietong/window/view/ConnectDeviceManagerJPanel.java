package com.jietong.window.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import serialports.service.SerialPortService;
import serialports.service.impl.SerialPortServiceImpl;
import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.util.Regex;
import com.jietong.window.util.IPTextField;
import com.jietong.window.util.LanguageUtil;
import com.jietong.window.util.ReaderUtil;
import com.jietong.window.util.TableTools;

public class ConnectDeviceManagerJPanel implements ActionListener,MouseListener {
	public JPanel backgroundPanel;
	DefaultTableModel baseTableModule;
	JLabel tool_modify;
	private JPanel panel_top;
	private JPanel panel_center;
	private JPanel panel_bottom;
	private JPanel panel_connectMode;
	private JPanel panel_communicationMode;
	private JLabel lblVersion;
	private JLabel lblVersionInfo;
	private JButton btnGetVersion;
	private JButton btnRefreshVersion;
	private JScrollPane spConnectInfo;
	private JPanel panel_SerialPort;
	private JPanel panel_TCPClient;
	private JRadioButton rbRs232;
	private JRadioButton rbTcpClient;
	private JRadioButton rbTcpService;
	private JPanel panel_TCPServer;
	private JLabel lblSerialport;
	private JComboBox cbbSerialPort;
	private JPanel panel_empty;
	private JLabel lblEmpty1;
	private JLabel lblEmpty2;
	private JLabel lblEmpty3;
	private CardLayout clConnectMode;
	private IPTextField tfDeviceIP;
	private JTextField tfTCPClientPort;
	private JButton btnMonitor;
	private JTextField tfServerPort;
	private IPTextField tfLocalhost;
	private JLabel lblLocalhost;
	private JLabel lblServerPort;
	private JButton btnSerialPortRefresh;
	private JButton btnSerialPortConnect;
	static SerialPortService serialPortSerivce = new SerialPortServiceImpl();
	ReaderService readerService = new ReaderServiceImpl();
	SocketConnect connect = new SocketConnect();

	public ConnectDeviceManagerJPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		panel_top = new JPanel();
		backgroundPanel.add(panel_top, BorderLayout.NORTH);
		panel_top.setLayout(new BorderLayout(0, 0));
		topInital();
		centerInital();
		tableInital();
		bottomInital();
	}
	
	public void defaultLocalSerialPort() {
		cbbSerialPort.removeAllItems();
		List<String> serialPorts = serialPortSerivce.findSerialPorts();
        for (int i = serialPorts.size() -1; i >= 0; i--) {
        	cbbSerialPort.addItem(serialPorts.get(i));
		}
	}

	private void tableInital() {
		panel_center = new JPanel();
		backgroundPanel.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new BorderLayout(0, 0));
		spConnectInfo = new JScrollPane();
		panel_center.add(spConnectInfo, BorderLayout.CENTER);
		String params[] = { "ID", "IP/COM", "Port", "Status" };
		baseTableModule = new DefaultTableModel(null, params) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
		ReaderUtil.tableConnectInfo = new JTable(baseTableModule);
		spConnectInfo.setViewportView(ReaderUtil.tableConnectInfo);
		TableTools.setTableStyle(ReaderUtil.tableConnectInfo);
		DefaultTableColumnModel dcm = (DefaultTableColumnModel) ReaderUtil.tableConnectInfo.getColumnModel();
		dcm.getColumn(0).setMinWidth(30); 
		dcm.getColumn(0).setMaxWidth(30);
		dcm.getColumn(1).setMinWidth(100); 
		dcm.getColumn(1).setMaxWidth(100);
		TableTools.setJspStyle(spConnectInfo);
		ReaderUtil.tableConnectInfo.getTableHeader().setReorderingAllowed(false);
	}

	public void refreshTablePanel() {
		backgroundPanel.remove(panel_center);
		String params[] = {"ID", "IP/COM", "Port", "Status" };
		int size = ReaderUtil.connectList.size();
		Object [][]object = null;
		if(size > 0){
			object = new Object[size][5];
			for (int i = 0; i < ReaderUtil.connectList.size(); i++) {
				object[i][0] = i + 1;
				object[i][1] = ReaderUtil.connectList.get(i).host;
				object[i][2] = ReaderUtil.connectList.get(i).port;
				object[i][3] = "Connect";
			}
		}
		baseTableModule = new DefaultTableModel(object, params) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
		ReaderUtil.tableConnectInfo = new JTable(baseTableModule);
		TableTools.setTableStyle(ReaderUtil.tableConnectInfo);
		DefaultTableColumnModel dcm = (DefaultTableColumnModel) ReaderUtil.tableConnectInfo.getColumnModel();
		dcm.getColumn(0).setMinWidth(30); 
		dcm.getColumn(0).setMaxWidth(30);
		dcm.getColumn(1).setMinWidth(100); 
		dcm.getColumn(1).setMaxWidth(100);
		spConnectInfo = new JScrollPane(ReaderUtil.tableConnectInfo);
		TableTools.setJspStyle(spConnectInfo);
		panel_center = new JPanel(new BorderLayout());
		panel_center.setOpaque(false);
		panel_center.add(spConnectInfo);
		backgroundPanel.add(panel_center, "Center");
		backgroundPanel.validate();

		ListSelectionModel rsm = ReaderUtil.tableConnectInfo.getSelectionModel();
		rsm.addListSelectionListener(new TableConnectAction(ReaderUtil.lblShowInfo, rsm));
		if(ReaderUtil.tableConnectInfo.getRowCount() > 0){
			ReaderUtil.tableConnectInfo.setRowSelectionInterval(0,0);
		}
		ReaderUtil.tableConnectInfo.addMouseListener(this);
		ReaderUtil.tableConnectInfo.getTableHeader().setReorderingAllowed(false);
		createPopupMenu();
	}

	private void bottomInital() {
		panel_bottom = new JPanel();
		backgroundPanel.add(panel_bottom, BorderLayout.SOUTH);
		panel_bottom.setLayout(new GridLayout(2, 4, 0, 0));
		panel_version = new JPanel();
		panel_bottom.add(panel_version);
		panel_version.setLayout(new GridLayout(0, 2, 0, 0));
		lblVersion = new JLabel(LanguageUtil.rs.getString("lblVersion"));
		panel_version.add(lblVersion);
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersionInfo = new JLabel("");
		lblVersionInfo.setHorizontalAlignment(SwingConstants.LEFT);
		panel_version.add(lblVersionInfo);
		panel_versionButton = new JPanel();
		panel_bottom.add(panel_versionButton);
		btnGetVersion = new JButton(LanguageUtil.rs.getString("btnGetVersion"));
		panel_versionButton.add(btnGetVersion);
		btnRefreshVersion = new JButton(LanguageUtil.rs.getString("btnRefreshVersion"));
		panel_versionButton.add(btnRefreshVersion);
		btnRefreshVersion.addActionListener(this);
		btnGetVersion.addActionListener(this);
	}

	private void centerInital() {
		panel_communicationMode = new JPanel();
		panel_communicationMode.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gopCommunicationMode"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_top.add(panel_communicationMode, BorderLayout.SOUTH);
		clConnectMode = new CardLayout();
		panel_communicationMode.setLayout(clConnectMode);
		
		serialPortUI();
		tCPClientUI();
		tCPService();
		emptyUI();
		
		btnSerialPortRefresh.addActionListener(this);
		btnSerialPortConnect.addActionListener(this);
	}

	private void emptyUI() {
		panel_empty = new JPanel();
		panel_communicationMode.add(panel_empty, BorderLayout.EAST);
		panel_empty.setLayout(new GridLayout(2, 2, 0, 30));
		lblEmpty1 = new JLabel("\"");
		panel_empty.add(lblEmpty1);
		lblEmpty2 = new JLabel("\"");
		panel_empty.add(lblEmpty2);
		lblEmpty3 = new JLabel("\"");
		panel_empty.add(lblEmpty3);
	}

	private void tCPService() {
		panel_TCPServer = new JPanel();
		panel_communicationMode.add(panel_TCPServer, "TCPService");
		panel_TCPServer.setLayout(null);
		btnMonitor = new JButton(LanguageUtil.rs.getString("btnStartMonitor"));
		btnMonitor.setBounds(148, 28, 93, 29);
		panel_TCPServer.add(btnMonitor);
		tfServerPort = new JTextField();
		tfServerPort.setColumns(10);
		tfServerPort.setBounds(81, 29, 66, 28);
		panel_TCPServer.add(tfServerPort);
		tfLocalhost = new IPTextField();
		tfLocalhost.setColumns(10);
		tfLocalhost.setBounds(81, 0, 160, 28);
		panel_TCPServer.add(tfLocalhost);
		lblLocalhost = new JLabel(LanguageUtil.rs.getString("lblLocalhost"));
		lblLocalhost.setBounds(0, 10, 78, 15);
		panel_TCPServer.add(lblLocalhost);
		lblServerPort = new JLabel(LanguageUtil.rs.getString("lblServicePort"));
		lblServerPort.setBounds(0, 32, 78, 15);
		panel_TCPServer.add(lblServerPort);
		
		btnMonitor.addActionListener(this);
	}

	private void tCPClientUI() {
		panel_TCPClient = new JPanel();
		panel_communicationMode.add(panel_TCPClient, "TCPClient");
		panel_TCPClient.setLayout(null);
		JLabel lblDeviceIP = new JLabel(LanguageUtil.rs.getString("lblDeviceIP"));
		lblDeviceIP.setBounds(0, 10, 78, 15);
		panel_TCPClient.add(lblDeviceIP);
		JLabel lblTCPClientPort = new JLabel(LanguageUtil.rs.getString("lblTCPClientPort"));
		lblTCPClientPort.setBounds(0, 35, 78, 15);
		panel_TCPClient.add(lblTCPClientPort);
		tfDeviceIP = new IPTextField();
		tfDeviceIP.setBounds(81, 0, 160, 28);
		panel_TCPClient.add(tfDeviceIP);
		tfDeviceIP.setColumns(10);
		tfTCPClientPort = new JTextField();
		tfTCPClientPort.setBounds(81, 29, 66, 28);
		panel_TCPClient.add(tfTCPClientPort);
		tfTCPClientPort.setColumns(10);
		btnTCPClientConnect = new JButton(LanguageUtil.rs.getString("btnTCPClientConnect"));
		btnTCPClientConnect.setBounds(148, 28, 93, 29);
		panel_TCPClient.add(btnTCPClientConnect);
		
		btnTCPClientConnect.addActionListener(this);
	}

	private void serialPortUI() {
		panel_SerialPort = new JPanel();
		panel_communicationMode.add(panel_SerialPort, "SerialPort");
		panel_SerialPort.setLayout(null);
		lblSerialport = new JLabel(LanguageUtil.rs.getString("lblSerialport"));
		lblSerialport.setBounds(0, 19, 72, 26);
		lblSerialport.setHorizontalAlignment(SwingConstants.CENTER);
		panel_SerialPort.add(lblSerialport);
		cbbSerialPort = new JComboBox();
		cbbSerialPort.setBounds(74, 19, 80, 26);
		panel_SerialPort.add(cbbSerialPort);
		btnSerialPortConnect = new JButton(LanguageUtil.rs.getString("btnSerialPortConnect"));
		btnSerialPortConnect.setBounds(163, 34, 80, 26);
		panel_SerialPort.add(btnSerialPortConnect);
		btnSerialPortRefresh = new JButton(LanguageUtil.rs.getString("btnSerialPortRefresh"));
		btnSerialPortRefresh.setBounds(163, 0, 80, 26);
		panel_SerialPort.add(btnSerialPortRefresh);
	}

	private void topInital() {
		panel_connectMode = new JPanel();
		panel_connectMode.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), LanguageUtil.rs.getString("gopConnectMode"),TitledBorder.LEADING, TitledBorder.TOP, null,new Color(0, 0, 0)));
		panel_top.add(panel_connectMode, BorderLayout.NORTH);
		panel_connectMode.setLayout(new GridLayout(3, 1, 0, 10));
		rbRs232 = new JRadioButton("RS232");
		panel_connectMode.add(rbRs232);
		rbTcpClient = new JRadioButton("TCP Client");
		panel_connectMode.add(rbTcpClient);
		rbTcpService = new JRadioButton("TCP Service");
		panel_connectMode.add(rbTcpService);
		ButtonGroup bgConnectMode = new ButtonGroup();
		bgConnectMode.add(rbRs232);
		bgConnectMode.add(rbTcpClient);
		bgConnectMode.add(rbTcpService);
		rbRs232.setSelected(true);
		
		rbRs232.addActionListener(this);
		rbTcpClient.addActionListener(this);
		rbTcpService.addActionListener(this);
	}

	JPopupMenu m_popupMenu;
	private JPanel panel_version;
	private JPanel panel_versionButton;
	private JButton btnTCPClientConnect;

	private void createPopupMenu() {
		m_popupMenu = new JPopupMenu();
		JMenuItem delMenItem = new JMenuItem();
		delMenItem.setText(LanguageUtil.rs.getString("menuItemDisconnect"));
		delMenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int count = ReaderUtil.tableConnectInfo.getSelectedRow();
				boolean result = readerService.disconnect(ReaderUtil.connectList.get(count));
				if (result) {
					ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgDisconnectSuccess"));
					ReaderUtil.connectList.remove(count);
					refreshTablePanel();
				} else {
					ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgDisconnectFailure"));
				}
			}
		});
		m_popupMenu.add(delMenItem);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			int focusedRowIndex = ReaderUtil.tableConnectInfo.rowAtPoint(e.getPoint());
			if (focusedRowIndex == -1) {
				return;
			}
			ReaderUtil.tableConnectInfo.setRowSelectionInterval(focusedRowIndex,focusedRowIndex);
			m_popupMenu.show(ReaderUtil.tableConnectInfo, e.getX(), e.getY());
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
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
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		ReaderUtil.lblShowInfo.setText("");
		if (object instanceof JRadioButton) {
			JRadioButton rb = (JRadioButton) object;
			if (rb == rbRs232) {
				clConnectMode.show(panel_communicationMode, "SerialPort");
			} else if (rb == rbTcpClient) {
				clConnectMode.show(panel_communicationMode, "TCPClient");
				tfDeviceIP.setText("192.168.0.248");
				tfTCPClientPort.setText("8000");
			} else if (rb == rbTcpService) {
				clConnectMode.show(panel_communicationMode, "TCPService");
				String localhostIP;
				try {
					localhostIP = InetAddress.getLocalHost().getHostAddress();
					if (localhostIP.length() > 0) {
						tfLocalhost.setText(localhostIP);
						tfServerPort.setText("8000");
					}
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (object instanceof JButton) {
			if (object == btnSerialPortRefresh) {
				defaultLocalSerialPort();
			} else if (object == btnSerialPortConnect) {
				serialPortConnectDevice();
			} else if (object == btnGetVersion) {
				version();
			} else if (object == btnRefreshVersion) {
				ReaderUtil.lblShowInfo.setText("");
				lblVersionInfo.setText("");
			} else if(object == btnTCPClientConnect){
				tCPClientConnect();
			} else if(object == btnMonitor){
				tCPServersConnect();
			}
		}
	}
	private void tCPServersConnect() {
		String start = LanguageUtil.rs.getString("btnStartMonitor");
		if(btnMonitor.getText() == start){
			startMonitor();
		}else{
			stopMonitor();
		}
	}

	private void startMonitor() {
		String tCPServerPort = tfServerPort.getText().replace(" ", "");
		if (null == tCPServerPort) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyServersPortCannotBeEmpty"));
			return;
		}
		if(!Regex.isDecNumber(tCPServerPort)){
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyServersPortError"));
			return;
		}
		btnMonitor.setText(LanguageUtil.rs.getString("btnStopMonitor"));
		String address = null;
		try {
			address = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		int portNo = Integer.parseInt(tCPServerPort);
		connect.startServer(portNo,10,address);
	}

	private void stopMonitor() {
		btnMonitor.setText(LanguageUtil.rs.getString("btnStartMonitor"));
		connect.stopServer();
	}

	private void tCPClientConnect() {
		String deviceIP = tfDeviceIP.getText().replace(" ", "");
		String tCPClientPort = tfTCPClientPort.getText().replace(" ", "");
		if (null == deviceIP) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDeviceIPCannotBeEmpty"));
			return;
		}
		if (null == tCPClientPort) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDevicePortCannotBeEmpty"));
			return;
		}
		if(!Regex.isValidIP(deviceIP)){
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDeviceIPError"));
			return;
		}
		if(!Regex.isDecNumber(tCPClientPort)){
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDevicePortError"));
			return;
		}
		if (ReaderUtil.connectList.size() > 0) {
			for (int i = 0; i < ReaderUtil.connectList.size(); i++) {
				if (ReaderUtil.connectList.get(i).host.equals(deviceIP)) {
					ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgRepeatedConnect"));
					return;
				}
			}
		}
		int port = Integer.parseInt(tCPClientPort);
		Reader reader = readerService.connect(deviceIP, port);
		if (null == reader) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgConnectFailure"));
		} else {
			ReaderUtil.connectList.add(reader);
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgConnectSuccess"));
			refreshTablePanel();
		}
	}

	private void version() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		String version = readerService.version(ReaderUtil.connectList.get(ReaderUtil.selectDevice));
		if (null != version) {
			lblVersionInfo.setText(version);
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetVersionSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetVersionFailure"));
		}
	}

	private void serialPortConnectDevice() {
		if (null == cbbSerialPort.getSelectedItem()) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifySerialPortEmpty"));
			return;
		}
		String serialPort = String.valueOf(cbbSerialPort.getSelectedItem());
		if (ReaderUtil.connectList.size() > 0) {
			for (int i = 0; i < ReaderUtil.connectList.size(); i++) {
				if (ReaderUtil.connectList.get(i).host.equals(serialPort)) {
					ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgRepeatedConnect"));
					return;
				}
			}
		}
		Reader reader = readerService.connect(serialPort, 0);
		if (null == reader) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgConnectFailure"));
		} else {
			ReaderUtil.connectList.add(reader);
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgConnectSuccess"));
			refreshTablePanel();
		}
	}
	
	class SocketConnect{
		private static final long serialVersionUID = 1L;
		private List<ServerThread> socketList = new ArrayList<ServerThread>();
		private ServerSocket serverSocket = null;
		private boolean isStartServer = false;
		private Thread sockets;
		
		public void startServer(int port,int backlog,String bindAddrIP) {
			try {
				serverSocket = new ServerSocket(port,10, InetAddress.getByName(bindAddrIP));
				//PropertiesUtils.setValue(filepath, map);
				System.out.println("open " + serverSocket);
				isStartServer = true;
				if (isStartServer) {
					sockets = new Thread(new sockets());
					sockets.start();
					ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgOpenServerSuccess"));
				}
			} catch (IOException e1) {
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgOpenServerFailure"));
				// e1.printStackTrace();
				isStartServer = false;
			}
		}

		public void startServer(int port) {
			try {
				serverSocket = new ServerSocket(port);
				System.out.println("open " + serverSocket);
				isStartServer = true;
				if (isStartServer) {
					sockets = new Thread(new sockets());
					sockets.start();
				}
			} catch (IOException e1) {
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgOpenServerFailure"));
				// e1.printStackTrace();
				isStartServer = false;
			}
		}
		
		public class sockets implements Runnable {
			int ports = 0;
			@Override
			public void run() {
				while (isStartServer) {
					try {
						Socket client = serverSocket.accept();
						ServerThread serverThread = new ServerThread(client);
						new Thread(serverThread).start();
						socketList.add(serverThread);
						ports = client.getPort();
						String device = client.getInetAddress().getHostAddress();
						System.out.println(device);
						Reader reader = new Reader();
						reader.host = device;
						reader.socket = client;
						ReaderUtil.connectList.add(reader);
						refreshTablePanel();
					} catch (IOException e) {
						//e.printStackTrace();
					}finally{
						//isStartServer = false;
					}
				}
			}
		}

		public void stopServer() {
			if (isStartServer) {
				for (ServerThread serverThread : socketList) {
					serverThread.stop();
				}
				socketList.clear();
				isStartServer = false;
				try {
					serverSocket.close();
					ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgCloseServerSuccess"));
					System.out.println("close " + serverSocket);
					for (int i = 0; i < ReaderUtil.connectList.size(); i++) {
						if(ReaderUtil.connectList.get(i).socket != null){
							ReaderUtil.connectList.remove(i);
						}
					}
					refreshTablePanel();
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("非正常close " + serverSocket);
				}
			}
		}

		public void sendMessage(String str) {
			socketList.get(0).sendMessage(str);
		}

		private class ServerThread implements Runnable {
			private boolean isRunning = false;
			private final Socket socket;
			private final BufferedReader reader;
			private final PrintWriter writer;

			public ServerThread(Socket socket) throws IOException {
				this.socket = socket;
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				isRunning = true;
			}

			public void stop() {
				isRunning = false;
				String port = null;
				try {
					String ports = socket.getInetAddress().getHostAddress();
//					for (int j = 0; j < tbl_showConnectInfo.getRowCount(); j++) {
//						port = (String) tbl_showConnectInfo.getValueAt(j, 1);
//						if (ports.equals(port)) {
//							listConnect.remove(j);
//							tableConnectModel.removeRow(j);
//							break;
//						}
//					}
					writer.close();
					reader.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public void sendMessage(String str) {
				writer.print(str + "\n");
				writer.flush();
			}

			@Override
			public void run() {
				String msg = null;
				String IP2 = null;
				while (isRunning) {
					try {
						//msg = reader.readLine();
						//System.out.println(ServerThread.this.toString() + ": " + msg);
							//System.out.println("断开连接"	+ socket.getPort() + " " + socket.getInetAddress());
							//break;
					} catch (Exception e) {
						//System.out.println("非正常断?	+ socket.getPort());
						// e.printStackTrace();
					} finally {
						if (!socket.isConnected()) {
							System.out.println("断开连接"	+ socket.getPort() + " " + socket.getInetAddress());
							String IP = socket.getInetAddress().getHostAddress();
//							for (int j = 0; j < tbl_showConnectInfo.getRowCount(); j++) {
//								IP2 = (String) tbl_showConnectInfo.getValueAt(j, 1);
//								if (IP.length() > 5) {
//									listConnect.remove(j);
//									tableConnectModel.removeRow(j);
//									break;
//								}
//							}
							isRunning = false;
						}
					}
				}
			}
		}
	}
}
