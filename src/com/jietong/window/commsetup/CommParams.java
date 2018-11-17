package com.jietong.window.commsetup;

import java.nio.ByteBuffer;

import com.jietong.MainStart;
import com.jietong.rfid.util.DataConvert;
import com.jietong.rfid.util.ReaderUtil;
import com.jietong.rfid.util.StrUtil;

public class CommParams extends MainStart {
	private static final long serialVersionUID = 1L;

	private static void updateGetWifi(byte[] result) {
		// 关联的AP SSID名称 16BYTES
		tf_ssid.setText(StrUtil.bytesToAscii(result, 0, 16));

		// 加密密码 32BYTES
		tf_password.setText(StrUtil.bytesToAscii(result, 16, 32));

		// 设备/STA IP地址 4BYTES
		tf_readerIP.setText(DataConvert.convertToDecimalString(result, 48, 4,
				'.'));

		// 子网掩码 4BYTES
		tf_subnetMaskRight.setText(DataConvert.convertToDecimalString(result,
				52, 4, '.'));

		// 网关 4BYTES
		tf_gatewayRight.setText(DataConvert.convertToDecimalString(result, 56,
				4, '.'));

		// 服务器地址 4BYTES
		tf_serverIP.setText(DataConvert.convertToDecimalString(result, 60, 4,
				'.'));

		// 服务器端口号 2BYTES
		int serverPort = DataConvert.byteToInt(result[64]) << 8;
		int serverPort2 = result[65];
		tf_serverPort.setText(String.valueOf(serverPort | serverPort2));

		// AP密码认证模式 1BYTE 0:OPEN，1:SHARED,2:WPAPSK,3:WPA2PSK
		cbb_APEncryptionPattern.setSelectedIndex(result[66]);

		// AP加密算法 1BYTE 0:NONE，1:WEP-A（效）,2:TKIP, 3:AES
		cbb_APEncryptionArithmetic.setSelectedIndex(result[67]);

		// Reserved 2BYTES
	}

	private static byte[] netParameters() {
		ByteBuffer netParam = ByteBuffer.allocate(35);
		// 保留
		// netParam[0],netParam[1],netParam[2],netParam[3]

		// 设备IP地址
		StrUtil.StringToBytes(tf_readIPAddress.getText(), '.', netParam, 4, 4);

		// 设备子网掩码
		StrUtil.StringToBytes(tf_subnetMask.getText(), '.', netParam, 8, 4);

		// 网关
		StrUtil.StringToBytes(tf_gateway.getText(), '.', netParam, 12, 4);

		// DNS
		StrUtil.StringToBytes(tf_dns.getText(), '.', netParam, 16, 4);

		// 服务器IP
		StrUtil.StringToBytes(tf_destinationIP.getText(), '.', netParam, 20, 4);

		// 设备端口
		int port1 = Integer.parseInt(tf_port.getText()) >> 8;
		int port2 = Integer.parseInt(tf_port.getText());
		netParam.put(24, (byte) port1);
		netParam.put(25, (byte) port2);

		// 服务器端口
		int destinationPort1 = Integer.parseInt(tf_destinationPort.getText()) >> 8;
		int destinationPort2 = Integer.parseInt(tf_destinationPort.getText());
		netParam.put(26, (byte) destinationPort1);
		netParam.put(27, (byte) destinationPort2);

		// 通信端口选择
		netParam.put(28, (byte) cbb_dataOutputPort.getSelectedIndex());

		// MAC地址
		StrUtil.convertToHexString(tf_mac.getText(), '-', netParam, 29, 6);

		// 设备ID
		// tf_deviceID
		return netParam.array();
	}

	public static void updateNetParameters(byte[] result) {
		tf_mac.setText(DataConvert.convertToHexString(result, 0, 6, '-'));

		tf_readIPAddress.setText(DataConvert.convertToDecimalString(result, 6,
				4, '.'));

		tf_subnetMask.setText(DataConvert.convertToDecimalString(result, 10, 4,
				'.'));

		tf_gateway.setText(DataConvert.convertToDecimalString(result, 14, 4,
				'.'));

		tf_dns.setText(DataConvert.convertToDecimalString(result, 18, 4, '.'));

		tf_destinationIP.setText(DataConvert.convertToDecimalString(result, 22,
				4, '.'));

		// 设备端口
		int port = DataConvert.byteToInt(result[26]) << 8;
		int port2 = DataConvert.byteToInt(result[27]);
		tf_port.setText(String.valueOf(port | port2));

		// 服务器端口
		int destinationPort = DataConvert.byteToInt(result[28]) << 8;
		int destinationPort2 = DataConvert.byteToInt(result[29]);
		tf_destinationPort.setText(String.valueOf(destinationPort
				| destinationPort2));

		// 端口选择
		cbb_dataOutputPort.setSelectedIndex(DataConvert.byteToInt(result[30]));

		// 保留result[31]

		// 设备地址码
		tf_deviceID.setText(DataConvert.convertToString(result, 32, 4));
	}

	public static void getNet() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM
				&& ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				byte[] result = ReaderUtil.readerService
						.getNet(ReaderUtil.readers[i]);
				if (result != null) {
					updateNetParameters(result);
					lbl_infoContent.setText("获取网络参数成功!");
				} else {
					lbl_infoContent.setText("获取网络参数失败!");
				}
			}
		}
	}

	public static void setNet() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				byte[] netParam = netParameters();
				boolean result = ReaderUtil.readerService.setNet(ReaderUtil.readers[i], netParam);
				if (result) {
					lbl_infoContent.setText("设置网络参数成功!");
				} else {
					lbl_infoContent.setText("设置网络参数失败!");
				}
			}
		}
	}

	public static void getWifi() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM
				&& ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				byte[] result = ReaderUtil.readerService
						.getWifi(ReaderUtil.readers[i]);
				if (result != null) {
					updateGetWifi(result);
					lbl_infoContent.setText("获取wifi参数成功!");
				} else {
					lbl_infoContent.setText("获取wifi参数失败!");
				}
			}
		}
	}

	public static void setWifi() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM
				&& ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				byte[] wifiParam = wifiParameters();
				boolean result = ReaderUtil.readerService.setWifi(
						ReaderUtil.readers[i], wifiParam);
				if (result) {
					lbl_infoContent.setText("设置wifi参数成功!");
				} else {
					lbl_infoContent.setText("设置wifi参数失败!");
				}
			}
		}
	}

	private static byte[] wifiParameters() {
		ByteBuffer wifiParam = ByteBuffer.allocate(68);
		// 关联的AP SSID名称 16BYTES
		StrUtil.StringToBytes(tf_ssid.getText().trim(), wifiParam, 0, 16);

		// 加密密码 32BYTES
		StrUtil.StringToBytes(tf_password.getText().trim(), wifiParam, 16, 32);

		// 设备/STA IP地址 4BYTES
		StrUtil.StringToBytes(tf_readerIP.getText().trim(), '.', wifiParam, 48,
				4);

		// 子网掩码 4BYTES
		StrUtil.StringToBytes(tf_subnetMaskRight.getText().trim(), '.',
				wifiParam, 52, 4);

		// 网关 4BYTES
		StrUtil.StringToBytes(tf_gatewayRight.getText().trim(), '.', wifiParam,
				56, 4);

		// 服务器地址 4BYTES
		StrUtil.StringToBytes(tf_serverIP.getText().trim(), '.', wifiParam, 60,
				4);

		// 服务器端口号 2BYTES
		int serverPort2 = Integer.parseInt(tf_serverPort.getText()) >> 8;
		int serverPort = Integer.parseInt(tf_serverPort.getText());
		wifiParam.put(64, (byte) serverPort2);
		wifiParam.put(65, (byte) serverPort);

		// AP密码认证模式 1BYTE 0:OPEN，1:SHARED,2:WPAPSK,3:WPA2PSK
		wifiParam.put(66, (byte) cbb_APEncryptionPattern.getSelectedIndex());

		// AP加密算法 1BYTE 0:NONE，1:WEP-A（效）,2:TKIP, 3:AES
		wifiParam.put(67, (byte) cbb_APEncryptionArithmetic.getSelectedIndex());
		return wifiParam.array();
	}
}
