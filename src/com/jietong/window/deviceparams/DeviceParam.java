package com.jietong.window.deviceparams;

import com.jietong.MainStart;
import com.jietong.rfid.util.DataConvert;
import com.jietong.rfid.util.ReaderUtil;

public class DeviceParam extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void updateFrequencyParameters(byte[] result) {
		tf_transmittedPower.setText(String.valueOf(DataConvert.byteToInt(result[1])));
		tf_attenuationCoefficient.setText(String.valueOf(DataConvert.byteToInt(result[2])));
		if(DataConvert.byteToInt(result[3]) < 10){
			cbb_readTagPattern.setSelectedIndex(DataConvert.byteToInt(result[3]));
		}
		tf_workingFrequency.setText(String.valueOf(DataConvert.byteToInt(result[4])));
		tf_tagType.setText(String.valueOf(DataConvert.byteToInt(result[5])));

		int ledState = (byte) ((DataConvert.byteToInt(result[6]) & 0x40) >> 6);
		int buzzerState = (byte) ((DataConvert.byteToInt(result[6]) & 0x80) >> 7);
		int workMode = (byte) ((DataConvert.byteToInt(result[6]) & 0x38) >> 3);

		cbb_dataCommunicationMode.setSelectedIndex(workMode - 1);
		if (buzzerState == 0x01) {
			rb_buzzerOpen.setSelected(true);
		} else {
			rb_buzzerClose.setSelected(true);
		}
		if (ledState == 0x01) {
			rb_ledOpen.setSelected(true);
		} else {
			rb_ledClose.setSelected(true);
		}
	}

	public static byte [] frequencyParameters(){
		byte [] setParameters = new byte[11];
		setParameters[0] = 0x00;
		setParameters[1] = Byte.valueOf((tf_transmittedPower.getText()));
		setParameters[2] = Byte.valueOf(tf_attenuationCoefficient.getText());
		setParameters[3] = (byte) (cbb_readTagPattern.getSelectedIndex());
		setParameters[4] = Byte.valueOf(tf_workingFrequency.getText());
		setParameters[5] = Byte.valueOf(tf_tagType.getText());
		
		byte ledState = (byte) (rb_ledOpen.isSelected()?1:0);
		byte buzzerState = (byte) (rb_buzzerOpen.isSelected()?1:0);
		byte workmode =  (byte) (cbb_dataCommunicationMode.getSelectedIndex() +1);
		setParameters[6] = (byte) (ledState<<6 | buzzerState << 7 | workmode <<3);
		return setParameters;
	}

	public static void getWorkMode() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM
				&& ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				byte[] result = ReaderUtil.readerService.getWorkMode(ReaderUtil.readers[i]);
				if (result != null) {
					updateFrequencyParameters(result);
					lbl_infoContent.setText("��ѯ2.4G��д�����ò����ɹ�!");
				} else {
					lbl_infoContent.setText("��ѯ2.4G��д�����ò���ʧ��!");
				}
			}
		}
	}

	public static void setWorkMode() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				boolean result = ReaderUtil.readerService.setWorkMode(ReaderUtil.readers[i],frequencyParameters());
				if (result) {
					lbl_infoContent.setText("����2.4G��д�����ò����ɹ�!");
				} else {
					lbl_infoContent.setText("����2.4G��д�����ò���ʧ��!");
				}
			}
		}
	}

	public static void reStart() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				int module = cbb_restartTheModule.getSelectedIndex();
				boolean result = ReaderUtil.readerService.reStart(ReaderUtil.readers[i],module);
				if (result) {
					lbl_infoContent.setText("����ģ��ɹ�!");
				} else {
					lbl_infoContent.setText("����ģ��ʧ��!");
				}
			}
		}
	}

	public static void setTransferMode() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				int pattern = cbb_dataCommunicationMode_1.getSelectedIndex() + 1;
				boolean result = ReaderUtil.readerService.setTransferPattern(ReaderUtil.readers[i],pattern);
				if (result) {
					lbl_infoContent.setText("�������ݴ���ģʽ�ɹ�!");
				} else {
					lbl_infoContent.setText("�������ݴ���ģʽʧ��!");
				}
			}
		}
	}

	public static void setBuzzerState() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				int state = rb_buzzerOpenStatus.isSelected() ? 1 : 0;
				boolean result = ReaderUtil.readerService.setBuzzerState(ReaderUtil.readers[i],state);
				if (result) {
					lbl_infoContent.setText("���÷������ɹ�!");
				} else {
					lbl_infoContent.setText("���÷�����ʧ��!");
				}
			}
		}
	}

	public static void startCarrier() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				boolean result = ReaderUtil.readerService.startCarrier(ReaderUtil.readers[i]);
				if (result) {
					btn_carrierTesting.setText("ֹͣ�ز�����");
					lbl_infoContent.setText("�����ز����Գɹ�!");
				} else {
					lbl_infoContent.setText("�����ز�����ʧ��!");
				}
			}
		}
	}

	public static void stopCarrier() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				boolean result = ReaderUtil.readerService.stopCarrier(ReaderUtil.readers[i]);
				if (result) {
					btn_carrierTesting.setText("�����ز�����");
					lbl_infoContent.setText("ֹͣ�ز����Գɹ�!");
				} else {
					lbl_infoContent.setText("ֹͣ�ز�����ʧ��!");
				}
			}
		}
	}

	public static void setAttenuation() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				String total = tf_attenuationCoefficientOpera.getText().trim();
				int attenuation = (total.length() > 0 ? Integer.parseInt(total) : 0);
				boolean result = ReaderUtil.readerService.setAttenuation(ReaderUtil.readers[i],attenuation);
				if (result) {
					lbl_infoContent.setText("���ö�д��˥��ϵ���ɹ�!");
				} else {
					lbl_infoContent.setText("���ö�д��˥��ϵ��ʧ��!");
				}
			}
		}
	}

	public static void getAttenuation() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				String result = ReaderUtil.readerService.getAttenuation(ReaderUtil.readers[i]);
				if (result != null) {
					tf_attenuationCoefficientOpera.setText(result);
					lbl_infoContent.setText("��ȡ��д��˥��ϵ���ɹ�!");
				} else {
					lbl_infoContent.setText("��ȡ��д��˥��ϵ��ʧ��!");
				}
			}
		}
	}

	public static void setDevID() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				String total = tf_readerWriterID.getText().trim();
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
				
				boolean result = ReaderUtil.readerService.setDevID(ReaderUtil.readers[i],deviceNo);
				if (result) {
					lbl_infoContent.setText("�����豸�ųɹ�!");
				} else {
					lbl_infoContent.setText("�����豸��ʧ��!");
				}
			}
		}
	}

	public static void getDevID() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				String result = ReaderUtil.readerService.getDevID(ReaderUtil.readers[i]);
				if (result != null) {
					tf_readerWriterID.setText(result);
					lbl_infoContent.setText("��ȡ�豸�ųɹ�!");
				} else {
					lbl_infoContent.setText("��ȡ�豸��ʧ��!");
				}
			}
		}
	}

	public static void factoryReset() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				boolean result = ReaderUtil.readerService.factoryReset(ReaderUtil.readers[i]);
				if (result) {
					lbl_infoContent.setText("�ָ��������óɹ�!");
				} else {
					lbl_infoContent.setText("�ָ���������ʧ��!");
				}
			}
		}
	}
}
