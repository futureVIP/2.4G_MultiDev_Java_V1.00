package com.jietong.window.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import com.jietong.rfid.uhf.dao.impl.Reader;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.util.DataConvert;
import com.jietong.rfid.util.Regex;
import com.jietong.window.util.BaseTableModule;
import com.jietong.window.util.LanguageUtil;
import com.jietong.window.util.ReaderUtil;

public class DeviceParamsSettingManagerJPanel implements ActionListener{
	public JPanel backgroundPanel;
	JPanel tablePanel;
	BaseTableModule baseTableModule;
	JLabel tool_modify;
	private JPanel panel_paramsSetLeft;
	private JPanel panel_paramsSet;
	private JLabel lblOperatingFrequency;
	protected JTextField tfOperatingFrequency;
	private JLabel lblTagType;
	protected JTextField tfTagType;
	private JLabel lblAttenuationCoefficient;
	protected JTextField tfAttenuationCoefficient;
	private JLabel lblTransmittedPower;
	protected JTextField tfTransmittedPower;
	private JPanel panel_workMode;
	private JLabel lblDataCommunicationMode;
	protected JComboBox cbbDataCommunicationMode;
	private JPanel panel_readCardMode;
	private JLabel lblReadCardMode;
	protected JComboBox cbbReadCardMode;
	private JPanel panel_buzzer;
	protected JRadioButton rbOpenBuzzer;
	protected JRadioButton rbCloseBuzzer;
	private JPanel panel_led;
	protected JRadioButton rbOpenLed;
	protected JRadioButton rbCloseLed;
	private JButton btnGetIOOperation;
	private JButton btnSetIOOperation;
	private JPanel panel_rightTop;
	protected JTextField tfDeviceId;
	protected JButton btnGetDeviceId;
	protected JButton btnSetDeviceId;
	private JLabel lblDeviceId;
	private JPanel panel_paramsSetRight;
	private JPanel panel_rightCenter;
	private JPanel panel_leftCenter;
	private JPanel panel_leftBottom;
	private JPanel panel_leftTop;
	private JCheckBox cbInput1;
	private JCheckBox cbInput2;
	private JCheckBox cbOutput1;
	private JCheckBox cbOutput2;
	private JPanel panel_rightBottom;
	private JPanel panel_readerDeviceNo;
	private JPanel panel_restartModule;
	private JPanel panel_carrierTesting;
	private JPanel panel_factoryDataReset;
	private JLabel lblRestartModule;
	protected JComboBox cbbRestartModule;
	protected JButton btnRestartModule;
	private JLabel lblCarrierTesting;
	protected JButton btnCarrierTesting;
	private JLabel lblFactoryDataReset;
	protected JButton btnFactoryDataReset;
	protected JButton btnGetParams;
	protected JButton btnSetParams;
	private JPanel panel_paramsSettingLable;
	private JPanel panel_paramsSettingScope;
	ReaderService readerService = new ReaderServiceImpl();

	public DeviceParamsSettingManagerJPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		initTablePanelUI();
		paramsSetLeftUI();
		paramsSetRightUI();
	}

	public void initTablePanelUI() {
		tablePanel = new JPanel();
		tablePanel.setOpaque(false);
		backgroundPanel.add(tablePanel, "Center");
		tablePanel.setLayout(new GridLayout(0, 2, 0, 0));
	}

	private void paramsSetLeftUI() {
		panel_paramsSetLeft = new JPanel();
		panel_paramsSetLeft.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobGeneralParamseterSetting"), TitledBorder.LEADING,TitledBorder.TOP, null, null));
		tablePanel.add(panel_paramsSetLeft);
		panel_paramsSetLeft.setLayout(new BorderLayout(0, 0));
		leftTopUI();
		leftCenterUI();
		leftBottomUI();
	}
	
	private void leftCenterUI() {
		panel_leftCenter = new JPanel();
		panel_paramsSetLeft.add(panel_leftCenter, BorderLayout.CENTER);
		panel_leftCenter.setLayout(new GridLayout(0, 1, 0, 0));
		ledUI();
		buzzerUI();
		workModeUI();
		readCardModeUI();
	}

	private void leftBottomUI() {
		panel_leftBottom = new JPanel();
		panel_paramsSetLeft.add(panel_leftBottom, BorderLayout.SOUTH);
		panel_leftBottom.setLayout(new GridLayout(0, 2, 0, 0));
		btnGetParams = new JButton(LanguageUtil.rs.getString("btnGetParams"));
		panel_leftBottom.add(btnGetParams);
		btnSetParams = new JButton(LanguageUtil.rs.getString("btnSetParams"));
		panel_leftBottom.add(btnSetParams);
		JPanel panel_empty = new JPanel();
		panel_leftBottom.add(panel_empty);
		
		btnGetParams.addActionListener(this);
		btnSetParams.addActionListener(this);
	}

	private void ledUI() {
		panel_led = new JPanel();
		panel_leftCenter.add(panel_led);
		panel_led.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LED", TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel_led.setLayout(new GridLayout(0, 2, 0, 0));
		rbOpenLed = new JRadioButton(LanguageUtil.rs.getString("rbOpenLed"));
		rbOpenLed.setSelected(true);
		rbOpenLed.setHorizontalAlignment(SwingConstants.CENTER);
		panel_led.add(rbOpenLed);
		rbCloseLed = new JRadioButton(LanguageUtil.rs.getString("rbCloseLed"));
		panel_led.add(rbCloseLed);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbOpenLed);
		bg.add(rbCloseLed);
	}

	private void buzzerUI() {
		panel_buzzer = new JPanel();
		panel_leftCenter.add(panel_buzzer);
		panel_buzzer.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobBuzzer"),TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_buzzer.setLayout(new GridLayout(0, 2, 0, 0));
		rbOpenBuzzer = new JRadioButton(LanguageUtil.rs.getString("rbOpenBuzzer"));
		rbOpenBuzzer.setSelected(true);
		rbOpenBuzzer.setHorizontalAlignment(SwingConstants.CENTER);
		panel_buzzer.add(rbOpenBuzzer);
		rbCloseBuzzer = new JRadioButton(LanguageUtil.rs.getString("rbCloseBuzzer"));
		rbCloseBuzzer.setHorizontalAlignment(SwingConstants.LEFT);
		panel_buzzer.add(rbCloseBuzzer);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbOpenBuzzer);
		bg.add(rbCloseBuzzer);
	}

	private void readCardModeUI() {
		panel_readCardMode = new JPanel();
		panel_leftCenter.add(panel_readCardMode);
		panel_readCardMode.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobReadCardMode"), TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel_readCardMode.setLayout(new GridLayout(0, 2, 0, 0));
		lblReadCardMode = new JLabel(LanguageUtil.rs.getString("lblReadCardMode"));
		lblReadCardMode.setHorizontalAlignment(SwingConstants.CENTER);
		panel_readCardMode.add(lblReadCardMode);
		cbbReadCardMode = new JComboBox();
		String [] readCardMode = new String[3];
		readCardMode[0] = LanguageUtil.rs.getString("cbbReadCardModeNormal");
		readCardMode[1] = LanguageUtil.rs.getString("cbbReadCardModeSimple");
		readCardMode[2] = LanguageUtil.rs.getString("cbbReadCardModeStop");
		cbbReadCardMode.setModel(new DefaultComboBoxModel(readCardMode));
		panel_readCardMode.add(cbbReadCardMode);
	}

	private void workModeUI() {
		panel_workMode = new JPanel();
		panel_leftCenter.add(panel_workMode);
		panel_workMode.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobWorkMode"), TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel_workMode.setLayout(new GridLayout(0, 2, 0, 0));
		lblDataCommunicationMode = new JLabel(LanguageUtil.rs.getString("lblDataCommunicationMode"));
		lblDataCommunicationMode.setHorizontalAlignment(SwingConstants.CENTER);
		panel_workMode.add(lblDataCommunicationMode);
		cbbDataCommunicationMode = new JComboBox();//cbbDataCommunicationModeWiggins
		String [] dataCommunicationMode = new String[7];
		dataCommunicationMode[0] = "RS232";
		dataCommunicationMode[1] = "RS485";
		dataCommunicationMode[2] = LanguageUtil.rs.getString("cbbDataCommunicationModeWiggins26");
		dataCommunicationMode[3] = LanguageUtil.rs.getString("cbbDataCommunicationModeWiggins34");
		dataCommunicationMode[4] = "RJ45";
		dataCommunicationMode[5] = "WIFI";
		dataCommunicationMode[6] = "3G/4G";
		cbbDataCommunicationMode.setModel(new DefaultComboBoxModel(dataCommunicationMode));
		panel_workMode.add(cbbDataCommunicationMode);
	}

	private void leftTopUI() {
		panel_leftTop = new JPanel();
		panel_paramsSetLeft.add(panel_leftTop, BorderLayout.NORTH);
		panel_leftTop.setLayout(new BorderLayout(0, 0));
		panel_paramsSet = new JPanel();
		panel_leftTop.add(panel_paramsSet);
		panel_paramsSet.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobParamseterSetting"), TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel_paramsSet.setLayout(new GridLayout(0, 2, 0, 10));
		operatingFrequencyUI();
		tagTypeUI();
		attenuationCoefficientUI();
		transmittedPowerUI();
	}

	private void operatingFrequencyUI() {
		
	}

	private void tagTypeUI() {
		
	}

	private void attenuationCoefficientUI() {
		
	}

	private void transmittedPowerUI() {
		panel_paramsSettingLable = new JPanel();
		panel_paramsSet.add(panel_paramsSettingLable);
		panel_paramsSettingLable.setLayout(new GridLayout(4, 1, 0, 20));
		lblOperatingFrequency = new JLabel(LanguageUtil.rs.getString("lblOperatingFrequency"));
		panel_paramsSettingLable.add(lblOperatingFrequency);
		lblOperatingFrequency.setHorizontalAlignment(SwingConstants.CENTER);
		lblTagType = new JLabel(LanguageUtil.rs.getString("lblTagType"));
		panel_paramsSettingLable.add(lblTagType);
		lblTagType.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttenuationCoefficient = new JLabel(LanguageUtil.rs.getString("lblAttenuationCoefficient"));
		panel_paramsSettingLable.add(lblAttenuationCoefficient);
		lblAttenuationCoefficient.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransmittedPower = new JLabel(LanguageUtil.rs.getString("lblTransmittedPower"));
		panel_paramsSettingLable.add(lblTransmittedPower);
		lblTransmittedPower.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_paramsSettingScope = new JPanel();
		panel_paramsSet.add(panel_paramsSettingScope);
		panel_paramsSettingScope.setLayout(new GridLayout(4, 2, 0, 8));
		tfOperatingFrequency = new JTextField();
		panel_paramsSettingScope.add(tfOperatingFrequency);
		tfOperatingFrequency.setText("0");
		tfOperatingFrequency.setColumns(10);
		JLabel lblOperatingFrequencyScope = new JLabel("(0-80)");
		panel_paramsSettingScope.add(lblOperatingFrequencyScope);
		tfTagType = new JTextField();
		panel_paramsSettingScope.add(tfTagType);
		tfTagType.setText("0");
		tfTagType.setColumns(10);
		JLabel lblTagTypeScope = new JLabel("(0-16)");
		panel_paramsSettingScope.add(lblTagTypeScope);
		tfAttenuationCoefficient = new JTextField();
		panel_paramsSettingScope.add(tfAttenuationCoefficient);
		tfAttenuationCoefficient.setText("0");
		tfAttenuationCoefficient.setColumns(10);
		JLabel lblAttenuationCoefficientScope = new JLabel("(0-31)");
		panel_paramsSettingScope.add(lblAttenuationCoefficientScope);
		tfTransmittedPower = new JTextField();
		panel_paramsSettingScope.add(tfTransmittedPower);
		tfTransmittedPower.setText("7");
		tfTransmittedPower.setColumns(10);
		JLabel TransmittedPowerScope = new JLabel("(0-7)");
		panel_paramsSettingScope.add(TransmittedPowerScope);
	}

	private void paramsSetRightUI() {
		panel_paramsSetRight = new JPanel();
		tablePanel.add(panel_paramsSetRight);
		panel_paramsSetRight.setLayout(new GridLayout(0, 1, 0, 0));
		rightTopUI();
		rightCenterUI();
		rightBottomUI();
	}

	private void rightTopUI() {
		panel_rightTop = new JPanel();
		panel_paramsSetRight.add(panel_rightTop);
		panel_rightTop.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobCommonOperation"), TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel_rightTop.setLayout(new GridLayout(4, 1, 10, 5));
		readerDeviceNoUI();
		restartModuleUI();
		carrierTestingUI();
		factoryDataResetUI();
	}

	private void readerDeviceNoUI() {
		panel_readerDeviceNo = new JPanel();
		panel_rightTop.add(panel_readerDeviceNo);
		panel_readerDeviceNo.setLayout(new GridLayout(0, 4, 0, 0));
		lblDeviceId = new JLabel(LanguageUtil.rs.getString("lblDeviceId"));
		panel_readerDeviceNo.add(lblDeviceId);
		lblDeviceId.setHorizontalAlignment(SwingConstants.LEFT);
		tfDeviceId = new JTextField();
		panel_readerDeviceNo.add(tfDeviceId);
		tfDeviceId.setColumns(10);
		btnGetDeviceId = new JButton(LanguageUtil.rs.getString("btnGetDeviceId"));
		panel_readerDeviceNo.add(btnGetDeviceId);
		btnSetDeviceId = new JButton(LanguageUtil.rs.getString("btnSetDeviceId"));
		panel_readerDeviceNo.add(btnSetDeviceId);
		
		btnGetDeviceId.addActionListener(this);
		btnSetDeviceId.addActionListener(this);
	}

	private void restartModuleUI() {
		panel_restartModule = new JPanel();
		panel_rightTop.add(panel_restartModule);
		panel_restartModule.setLayout(new BorderLayout(0, 0));
		lblRestartModule = new JLabel(LanguageUtil.rs.getString("lblRestartModule"));
		panel_restartModule.add(lblRestartModule,"West");
		lblRestartModule.setHorizontalAlignment(SwingConstants.LEFT);
		String[] restartModule = new String[4];
		restartModule[0] = LanguageUtil.rs.getString("cbbRestartModuleCardReader");
		restartModule[1] = LanguageUtil.rs.getString("cbbRestartModuleCardReaderRadioFrequencyModule");
		restartModule[2] = "3G/4G";
		restartModule[3] = LanguageUtil.rs.getString("cbbRestartModuleWifiModule");
		cbbRestartModule = new JComboBox();
		cbbRestartModule.setModel(new DefaultComboBoxModel(restartModule));
		panel_restartModule.add(cbbRestartModule,"Center");
		btnRestartModule = new JButton(LanguageUtil.rs.getString("btnRestartModule"));
		panel_restartModule.add(btnRestartModule,"East");
		
		btnRestartModule.addActionListener(this);
	}

	private void carrierTestingUI() {
		panel_carrierTesting = new JPanel();
		panel_rightTop.add(panel_carrierTesting);
		panel_carrierTesting.setLayout(new BorderLayout(0, 0));
		lblCarrierTesting = new JLabel(LanguageUtil.rs.getString("lblCarrierTesting"));
		panel_carrierTesting.add(lblCarrierTesting,"West");
		btnCarrierTesting = new JButton(LanguageUtil.rs.getString("btnStartCarrierTesting"));
		panel_carrierTesting.add(btnCarrierTesting,"East");
		
		btnCarrierTesting.addActionListener(this);
	}

	private void factoryDataResetUI() {
		panel_factoryDataReset = new JPanel();
		panel_rightTop.add(panel_factoryDataReset);
		panel_factoryDataReset.setLayout(new BorderLayout(0, 0));
		lblFactoryDataReset = new JLabel(LanguageUtil.rs.getString("lblFactoryDataReset"));
		panel_factoryDataReset.add(lblFactoryDataReset,"West");
		btnFactoryDataReset = new JButton(LanguageUtil.rs.getString("btnFactoryDataReset"));
		panel_factoryDataReset.add(btnFactoryDataReset,"East");
		
		btnFactoryDataReset.addActionListener(this);
	}

	private void rightCenterUI() {
		panel_rightCenter = new JPanel();
		panel_rightCenter.setBorder(new TitledBorder(null,LanguageUtil.rs.getString("gobIOOperation"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_paramsSetRight.add(panel_rightCenter);
		panel_rightCenter.setLayout(new GridLayout(3, 2, 15, 0));
		cbInput1 = new JCheckBox("Input 1");
		cbInput1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_rightCenter.add(cbInput1);
		cbInput2 = new JCheckBox("Input 2");
		panel_rightCenter.add(cbInput2);
		cbOutput1 = new JCheckBox("Output 1");
		cbOutput1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_rightCenter.add(cbOutput1);
		cbOutput2 = new JCheckBox("Output 2");
		panel_rightCenter.add(cbOutput2);
		btnGetIOOperation = new JButton(LanguageUtil.rs.getString("btnGetIOOperation"));
		panel_rightCenter.add(btnGetIOOperation);
		btnSetIOOperation = new JButton(LanguageUtil.rs.getString("btnSetIOOperation"));
		panel_rightCenter.add(btnSetIOOperation);
		
		btnGetIOOperation.addActionListener(this);
		btnSetIOOperation.addActionListener(this);
	}

	private void rightBottomUI() {
		panel_rightBottom = new JPanel();
		panel_paramsSetRight.add(panel_rightBottom);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object instanceof JButton){
			if(object == btnGetParams){
				readParams();
			}else if(object == btnSetParams){
				setParams();
			}else if(object == btnGetDeviceId){
				readDeviceId();
			}else if(object == btnSetDeviceId){
				setDeviceId();
			}else if(object == btnRestartModule){
				restartDevice();
			}else if(object == btnCarrierTesting){
				carrierTesting();
			}else if(object == btnFactoryDataReset){
				factoryDataReset();
			}else if(object == btnGetIOOperation){
				getIOOperation();
			}else if(object == btnSetIOOperation){
				setIOOperation();
			}
		}
	}
	
	private void setIOOperation() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		boolean result = readerService.setGpio(reader,setGPIO());
		if (result) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetGPIOStatusSuccess"));
		}else{
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetGPIOStatusFailure"));
		}
	}
	
	private void getIOOperation() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		Byte getGpio = readerService.getGpio(reader);
		if(null != getGpio){
			int result = DataConvert.byteToInt(getGpio);
			updateGPIO(result);
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetGPIOStatusSuccess"));
		}else{
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetGPIOStatusFailure"));
		}
	}
	
	public byte setGPIO() {
		byte outPort = 0;
		if (cbInput1.isSelected()) {
			outPort |= 0x01;
		}
		if (cbInput2.isSelected()) {
			outPort |= 0x02;
		}
		if (cbOutput1.isSelected()) {
			outPort |= 0x04;
		}
		if (cbOutput2.isSelected()) {
			outPort |= 0x08;
		}
		return outPort;
	}

	public void updateGPIO(int status) {
		if ((status & 0x01) != 0x00) {
			cbInput1.setSelected(true);
		} else {
			cbInput1.setSelected(false);
		}
		if ((status & 0x02) != 0x00) {
			cbInput2.setSelected(true);
		} else {
			cbInput2.setSelected(false);
		}
		if ((status & 0x04) != 0x00) {
			cbOutput1.setSelected(true);
		} else {
			cbOutput1.setSelected(false);
		}
		if ((status & 0x08) != 0x00) {
			cbOutput2.setSelected(true);
		} else {
			cbOutput2.setSelected(false);
		}
	}
	
	

	public byte [] frequencyParameters(){
		byte [] setParameters = new byte[11];
		setParameters[0] = 0x00;
		setParameters[1] = Byte.valueOf((tfTransmittedPower.getText()));
		setParameters[2] = Byte.valueOf(tfAttenuationCoefficient.getText());
		setParameters[3] = (byte) (cbbReadCardMode.getSelectedIndex());
		setParameters[4] = Byte.valueOf(tfOperatingFrequency.getText());
		setParameters[5] = Byte.valueOf(tfTagType.getText());
		
		byte ledState = (byte) (rbOpenLed.isSelected() ? 1 : 0);
		byte buzzerState = (byte) (rbOpenBuzzer.isSelected() ? 1 : 0);
		byte workmode =  (byte) (cbbDataCommunicationMode.getSelectedIndex() + 1);
		setParameters[6] = (byte) (ledState << 6 | buzzerState << 7 | workmode << 3);
		return setParameters;
	}
	
	public void updateFrequencyParameters(byte[] result) {
		tfTransmittedPower.setText(String.valueOf(DataConvert.byteToInt(result[1])));
		tfAttenuationCoefficient.setText(String.valueOf(DataConvert.byteToInt(result[2])));
		int readCardMode = DataConvert.byteToInt(result[3]);
		if(readCardMode > -1 || readCardMode < 3){
			cbbReadCardMode.setSelectedIndex(readCardMode);
		}
		
		tfOperatingFrequency.setText(String.valueOf(DataConvert.byteToInt(result[4])));
		tfTagType.setText(String.valueOf(DataConvert.byteToInt(result[5])));

		int ledState = (byte) ((DataConvert.byteToInt(result[6]) & 0x40) >> 6);
		int buzzerState = (byte) ((DataConvert.byteToInt(result[6]) & 0x80) >> 7);
		int workMode = (byte) ((DataConvert.byteToInt(result[6]) & 0x38) >> 3) - 1;
		if(workMode > -1 || workMode < 7){
			cbbDataCommunicationMode.setSelectedIndex(workMode);
		}
		if (buzzerState == 0x01) {
			rbOpenBuzzer.setSelected(true);
		} else {
			rbCloseBuzzer.setSelected(true);
		}
		if (ledState == 0x01) {
			rbOpenLed.setSelected(true);
		} else {
			rbCloseLed.setSelected(true);
		}
	}
	
	private void readParams() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		byte[] result = readerService.getWorkMode(reader);
		if (null != result) {
			updateFrequencyParameters(result);
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetReaderWriteCommonParamsSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetReaderWriteCommonParamsFailure"));
		}
	}
	
	private void setParams() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		boolean result = readerService.setWorkMode(reader,frequencyParameters());
		if (result) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetReaderWriteCommonParamsSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetReaderWriteCommonParamsFailure"));
		}
	}
	
	private void readDeviceId() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		String result = readerService.getDevID(reader);
		if (null != result) {
			tfDeviceId.setText(result);
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetDeviceIdSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgGetDeviceIdFailure"));
		}
	}
	
	private void setDeviceId() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		String total = tfDeviceId.getText().trim();
		if(total.length() < 1){
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDeviceMumberCannotBeEmpty"));
			return;
		}
		if(!Regex.isHexCharacter(total)){
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgVerifyDeviceIdIllegalCharacter"));
			return;
		}
		StringBuffer devNo = new StringBuffer();
		byte []deviceNo = new byte[4];
		if(total.length() < 8){
			for (int j = 0; j < 8 - total.length(); j++) {
				devNo.append("0");
			}
		}
		devNo.append(total);
		for (int k = 0; k < deviceNo.length; k++) {
			String temp = devNo.toString().substring(k * 2,(k * 2) + 2);
			deviceNo[k] = (byte) Integer.parseInt(temp,16);
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		boolean result = readerService.setDevID(reader,deviceNo);
		if (result) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetDeviceIdSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgSetDeviceIdFailure"));
		}
	}
	
	private void restartDevice() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		int module = cbbRestartModule.getSelectedIndex();
		boolean result = readerService.reStart(reader,module);
		if (result) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgRestartModuleSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgRestartModuleFailure"));
		}
	}
	
	private void carrierTesting() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		if(btnCarrierTesting.getText() == LanguageUtil.rs.getString("btnStartCarrierTesting")){
			startCarrier();
		}else{
			stopCarrier();
		}
	}
	
	private void stopCarrier() {
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		boolean result = readerService.stopCarrier(reader);
		if (result) {
			btnCarrierTesting.setText(LanguageUtil.rs.getString("btnStartCarrierTesting"));
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgStopCarrierTestingSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgStopCarrierTestingFailure"));
		}
	}

	private void startCarrier() {
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		boolean result = readerService.startCarrier(reader);
		if (result) {
			btnCarrierTesting.setText(LanguageUtil.rs.getString("btnStopCarrierTesting"));
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgStartCarrierTestingSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgStartCarrierTestingFailure"));
		}
	}
	
	private void factoryDataReset() {
		if (!ReaderUtil.selectConnect()) {
			return;
		}
		Reader reader = ReaderUtil.connectList.get(ReaderUtil.selectDevice);
		boolean result = readerService.factoryReset(reader);
		if (result) {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgFactoryDataResetSuccess"));
		} else {
			ReaderUtil.lblShowInfo.setText(LanguageUtil.rs.getString("msgFactoryDataResetFailure"));
		}
	}
}
