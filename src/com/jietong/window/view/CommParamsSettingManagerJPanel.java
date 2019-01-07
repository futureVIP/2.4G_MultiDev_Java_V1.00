package com.jietong.window.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.ByteBuffer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.util.DataConvert;
import com.jietong.rfid.util.Regex;
import com.jietong.rfid.util.StrUtil;
import com.jietong.window.util.BaseTableModule;
import com.jietong.window.util.IPTextField;
import com.jietong.window.util.LanguageUtil;
import com.jietong.window.util.ReaderUtil;

public class CommParamsSettingManagerJPanel implements ActionListener,MouseListener, DocumentListener,WindowListener {
	public JPanel backgroundPanel;
	JPanel tablePanel;
	BaseTableModule baseTableModule;
	JLabel tool_modify;
	private JPanel panel_leftNetworkParams;
	private JPanel panel_networkParams;
	private JLabel lblMAC;
	private JLabel lblDeviceIPAddress;
	private JLabel lblSubnetMask;
	private JLabel lblGateway;
	private JLabel lblDNS;
	private JLabel lblPortNo;
	private JLabel lblDestinationIP;
	private JLabel lblDestinaltionPort;
	private JTextField tfMAC;
	private IPTextField tfDeviceIPAddress;
	private IPTextField tfSubnetMask;
	private IPTextField tfGateway;
	private IPTextField tfDNS;
	private JTextField tfPortNo;
	private IPTextField tfDestinationIP;
	private JTextField tfDestinaltionPort;
	private JButton btnGetParams;
	private JButton btnDefaultParams;
	private JButton btnSetParams;
	ReaderService readerService = new ReaderServiceImpl();
	private JLabel lblEmpty;

	public CommParamsSettingManagerJPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		initTablePanel();
	}

	public void initTablePanel() {
		tablePanel = new JPanel();
		tablePanel.setOpaque(false);
		backgroundPanel.add(tablePanel, "Center");
		tablePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		panel_leftNetworkParams = new JPanel();
		tablePanel.add(panel_leftNetworkParams);
		panel_leftNetworkParams.setLayout(new BorderLayout(0, 0));
		
		panel_networkParams = new JPanel();
		panel_networkParams.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobNetworkParams") , TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_leftNetworkParams.add(panel_networkParams, BorderLayout.NORTH);
		panel_networkParams.setLayout(new GridLayout(8, 1, 0, 15));
		
		lblMAC = new JLabel("MAC");
		lblMAC.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblMAC);
		
		tfMAC = new JTextField();
		panel_networkParams.add(tfMAC);
		tfMAC.setHorizontalAlignment(SwingConstants.LEFT);
		tfMAC.setColumns(10);
		
		lblDeviceIPAddress = new JLabel(LanguageUtil.rs.getString("lblDeviceIPAddress"));
		lblDeviceIPAddress.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblDeviceIPAddress);
		
		tfDeviceIPAddress = new IPTextField();
		panel_networkParams.add(tfDeviceIPAddress);
		tfDeviceIPAddress.setColumns(10);
		
		lblSubnetMask = new JLabel(LanguageUtil.rs.getString("lblSubnetMask"));
		lblSubnetMask.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblSubnetMask);
		
		tfSubnetMask = new IPTextField();
		panel_networkParams.add(tfSubnetMask);
		tfSubnetMask.setColumns(10);
		
		lblGateway = new JLabel(LanguageUtil.rs.getString("lblGateway"));
		lblGateway.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblGateway);
		
		tfGateway = new IPTextField();
		panel_networkParams.add(tfGateway);
		tfGateway.setColumns(10);
		
		lblDNS = new JLabel("DNS");
		lblDNS.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblDNS);
		
		tfDNS = new IPTextField();
		panel_networkParams.add(tfDNS);
		tfDNS.setColumns(10);
		
		lblPortNo = new JLabel(LanguageUtil.rs.getString("lblPortNo"));
		lblPortNo.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblPortNo);
		
		tfPortNo = new JTextField();
		panel_networkParams.add(tfPortNo);
		tfPortNo.setColumns(10);
		
		lblDestinationIP = new JLabel(LanguageUtil.rs.getString("lblDestinationIP"));
		lblDestinationIP.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblDestinationIP);
		
		tfDestinationIP = new IPTextField();
		panel_networkParams.add(tfDestinationIP);
		tfDestinationIP.setColumns(10);
		
		lblDestinaltionPort = new JLabel(LanguageUtil.rs.getString("lblDestinaltionPort"));
		lblDestinaltionPort.setHorizontalAlignment(SwingConstants.CENTER);
		panel_networkParams.add(lblDestinaltionPort);
		
		tfDestinaltionPort = new JTextField();
		panel_networkParams.add(tfDestinaltionPort);
		tfDestinaltionPort.setColumns(10);
		
		JPanel panel_buttonGroup = new JPanel();
		panel_leftNetworkParams.add(panel_buttonGroup, BorderLayout.CENTER);
		panel_buttonGroup.setLayout(new GridLayout(1, 3, 0, 0));
		
		btnGetParams = new JButton(LanguageUtil.rs.getString("btnGetParams"));
		panel_buttonGroup.add(btnGetParams);
		
		btnDefaultParams = new JButton(LanguageUtil.rs.getString("btnDefaultParams"));
		panel_buttonGroup.add(btnDefaultParams);
		
		btnSetParams = new JButton(LanguageUtil.rs.getString("btnSetParams"));
		panel_buttonGroup.add(btnSetParams);
		
		JPanel panel_empty = new JPanel();
		panel_leftNetworkParams.add(panel_empty, BorderLayout.SOUTH);
		panel_empty.setLayout(new GridLayout(6, 1, 5, 0));
		
		lblEmpty = new JLabel("\"");
		panel_empty.add(lblEmpty);
		lblEmpty.setVisible(false);
		JPanel panel_rightEmpty1 = new JPanel();
		tablePanel.add(panel_rightEmpty1);
		
		btnDefaultParams.addActionListener(this);
		btnSetParams.addActionListener(this);
		btnGetParams.addActionListener(this);
		defaultParams();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object instanceof JButton){
			if(object == btnDefaultParams){
				defaultParams();
			}else if(object == btnGetParams){
				readParams();
			}else if(object == btnSetParams){
				setParams();
			}
		}
	}

	private void setParams() {
		ReaderUtil.lblShowInfo.setText("");
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		byte [] netParams = netParameters();
		if(null == netParams){
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		boolean result = readerService.setNet(reader,netParams);
		if (result) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetNetworkParametersSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetNetworkParametersFailure"));
		}
	}

	private void readParams() {
		ReaderUtil.lblShowInfo.setText("");
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		clearParams();
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		byte[] result = readerService.getNet(reader);
		if (result != null) {
			updateNetParameters(result);
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetNetworkParametersSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetNetworkParametersFailure"));
		}
	}
	
	public void updateNetParameters(byte[] result) {
		String mac = DataConvert.convertToHexString(result, 0, 6, '-');
		String deviceIP = DataConvert.convertToDecimalString(result, 6, 4, '.');
		String subnetMask = DataConvert.convertToDecimalString(result, 10, 4,'.');
		String gateway = DataConvert.convertToDecimalString(result, 14, 4,'.');
		String dns = DataConvert.convertToDecimalString(result, 18, 4, '.');
		String destinationIP = DataConvert.convertToDecimalString(result, 22,	4, '.');
		tfMAC.setText(mac);
		tfDeviceIPAddress.setText(deviceIP);
		tfSubnetMask.setText(subnetMask);
		tfGateway.setText(gateway);
		tfDNS.setText(dns);
		tfDestinationIP.setText(destinationIP);
		int port = DataConvert.byteToInt(result[26]) << 8;
		int port2 = DataConvert.byteToInt(result[27]);
		tfPortNo.setText(String.valueOf(port | port2));
		int destinationPort = DataConvert.byteToInt(result[28]) << 8;
		int destinationPort2 = DataConvert.byteToInt(result[29]);
		tfDestinaltionPort.setText(String.valueOf(destinationPort | destinationPort2));
		
		//cbbDataOutputPor.setSelectedIndex(DataConvert.byteToInt(result[30]));
		// 保留result[31]
		// 设备地址码ID
		//DataConvert.convertToString(result, 32, 4);
	}
	
	private void clearParams() {
		tfMAC.setText("");
		tfDeviceIPAddress.setText("");
		tfSubnetMask.setText("");
		tfGateway.setText("");
		tfDNS.setText("");
		tfPortNo.setText("");
		tfDestinationIP.setText("");
		tfDestinaltionPort.setText("");
	}

	private void defaultParams() {
		tfMAC.setText("AA-00-01-02-03-04");
		tfDeviceIPAddress.setText("192.168.0.238");
		tfSubnetMask.setText("255.255.255.0");
		tfGateway.setText("192.168.0.1");
		tfDNS.setText("192.168.0.1");
		tfPortNo.setText("8000");
		tfDestinationIP.setText("192.168.0.248");
		tfDestinaltionPort.setText("8000");
	}
	
	private boolean isVerifyNetworkParams(String params, String paramsType) {
		switch (paramsType) {
		case "deviceIPAddress":
			if (params.length() < 1) {
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDeviceIPCannotBeEmpty"));
				tfDeviceIPAddress.grabFocus();
				return false;
			} else if (!DataConvert.isValidIP(params)) {
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDeviceIPError"));
				tfDeviceIPAddress.grabFocus();
				return false;
			}
			break;
		case "subnetMask":
			if(params.length() < 1){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifySubnetMaskCannotBeEmpty"));
				tfSubnetMask.grabFocus();
				return false;
			}else if(!DataConvert.isValidIP(params)){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifySubnetMaskError"));
				tfSubnetMask.grabFocus();
				return false;
			}
			break;
		case "gateway":
			if(params.length() < 1){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyGatewayCannotBeEmpty"));
				tfGateway.grabFocus();
				return false;
			}else if(!DataConvert.isValidIP(params)){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyGatewayError"));
				tfGateway.grabFocus();
				return false;
			}
			break;
		case "dns":
			if(params.length() < 1){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDNSCannotBeEmpty"));
				tfDNS.grabFocus();
				return false;
			}else if(!DataConvert.isValidIP(params)){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDNSError"));
				tfDNS.grabFocus();
				return false;
			}
			break;
		case "destinationIP":
			if(params.length() < 1){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDestinationIPCannotBeEmpty"));
				tfDestinationIP.grabFocus();
				return false;
			}else if(!DataConvert.isValidIP(params)){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDestinationIPError"));
				tfDestinationIP.grabFocus();
				return false;
			}
			break;
		case "portNo":
			if(params.length() < 1){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDevicePortCannotBeEmpty"));
				tfPortNo.grabFocus();
				return false;
			}else if(!Regex.isDecNumber(params)){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDevicePortError"));
				tfPortNo.grabFocus();
				return false;
			}
			break;
		case "destPort":
			if(params.length() < 1){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyServersPortCannotBeEmpty"));
				tfDestinaltionPort.grabFocus();
				return false;
			}else if(!Regex.isDecNumber(params)){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyServersPortError"));
				tfDestinaltionPort.grabFocus();
				return false;
			}
			break;
		case "mac":
			if(params.length() < 1){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyMACAddressCannotBeEmpty"));
				tfMAC.grabFocus();
				return false;
			}else if(!DataConvert.isValidMAC(params)){
				ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyMACAddressError"));
				tfMAC.grabFocus();
				return false;
			}
			break;
		}
		return true;
	}
	
	private byte[] netParameters() {
		ReaderUtil.lblShowInfo.setText("");
		ByteBuffer netParam = ByteBuffer.allocate(35);
		// netParam[0],netParam[1],netParam[2],netParam[3]
		String deviceIPAddress = tfDeviceIPAddress.getText().replace(" ", "");
		String subnetMask = tfSubnetMask.getText();
		String gateway = tfGateway.getText();
		String dns = tfDNS.getText();
		String destinationIP = tfDestinationIP.getText();
		String portNo = tfPortNo.getText();
		String destPort = tfDestinaltionPort.getText();
		String mac = tfMAC.getText();
		int destinationPort = Integer.parseInt(tfDestinaltionPort.getText());
		if(!isVerifyNetworkParams(gateway,"gateway")){
			return null;
		}
		if(!isVerifyNetworkParams(deviceIPAddress,"deviceIPAddress")){
			return null;
		}
		if(!isVerifyNetworkParams(subnetMask,"subnetMask")){
			return null;
		}
		if(!isVerifyNetworkParams(dns,"dns")){
			return null;
		}
		if(!isVerifyNetworkParams(destinationIP,"destinationIP")){
			return null;
		}
		if(!isVerifyNetworkParams(portNo,"portNo")){
			return null;
		}
		if(!isVerifyNetworkParams(destPort,"destPort")){
			return null;
		}
		if(!isVerifyNetworkParams(mac,"mac")){
			return null;
		}
		StrUtil.StringToBytes(deviceIPAddress, '.', netParam, 4, 4);
		StrUtil.StringToBytes(subnetMask, '.', netParam, 8, 4);
		StrUtil.StringToBytes(gateway, '.', netParam, 12, 4);
		StrUtil.StringToBytes(dns, '.', netParam, 16, 4);
		StrUtil.StringToBytes(destinationIP, '.', netParam, 20, 4);
		
		int port = Integer.parseInt(portNo);
		int port1 = port >> 8;
		int port2 = port;
		
		netParam.put(24, (byte) port1);
		netParam.put(25, (byte) port2);

		int destinationPort1 = destinationPort >> 8;
		int destinationPort2 = destinationPort;
		netParam.put(26, (byte) destinationPort1);
		netParam.put(27, (byte) destinationPort2);
		// communication port 
		//netParam.put(28, (byte) cbb_dataOutputPort.getSelectedIndex());
		
		StrUtil.convertToHexString(mac, '-', netParam, 29, 6);
		return netParam.array();
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
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
//		Object object = e.getSource();
//		if(object instanceof JTextField){
//			if(object == tfMAC){
//				
//			}
//		}
	}
}
