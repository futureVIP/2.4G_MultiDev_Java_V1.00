package com.jietong.window.tagoperation;

import com.jietong.MainStart;
import com.jietong.rfid.util.DataConvert;
import com.jietong.rfid.util.ReaderUtil;

public class TagOperation extends MainStart {
	private static final long serialVersionUID = 1L;

	public static byte setGPIO() {
		byte outPort = 0;
		if (chk_IO1.isSelected()) {
			outPort |= 0x01;
		}
		if (chk_IO2.isSelected()) {
			outPort |= 0x02;
		}
		if (chk_IO3.isSelected()) {
			outPort |= 0x04;
		}
		if (chk_IO4.isSelected()) {
			outPort |= 0x08;
		}
		if (chk_IO5.isSelected()) {
			outPort |= 0x10;
		}
		if (chk_IO6.isSelected()) {
			outPort |= 0x20;
		}
		if (chk_IO7.isSelected()) {
			outPort |= 0x40;
		}
		if (chk_IO8.isSelected()) {
			outPort |= 0x80;
		}
		return outPort;
	}

	public static void updateGPIO(int status) {
		if ((status & 0x01) != 0x00) {
			chk_IO1.setSelected(true);
		} else {
			chk_IO1.setSelected(false);
		}
		if ((status & 0x02) != 0x00) {
			chk_IO2.setSelected(true);
		} else {
			chk_IO2.setSelected(false);
		}
		if ((status & 0x04) != 0x00) {
			chk_IO3.setSelected(true);
		} else {
			chk_IO3.setSelected(false);
		}
		if ((status & 0x08) != 0x00) {
			chk_IO4.setSelected(true);
		} else {
			chk_IO4.setSelected(false);
		}
		if ((status & 0x10) != 0x00) {
			chk_IO5.setSelected(true);
		} else {
			chk_IO5.setSelected(false);
		}
		if ((status & 0x20) != 0x00) {
			chk_IO6.setSelected(true);
		} else {
			chk_IO6.setSelected(false);
		}
		if ((status & 0x40) != 0x00) {
			chk_IO7.setSelected(true);
		} else {
			chk_IO7.setSelected(false);
		}
		if ((status & 0x80) != 0x00) {
			chk_IO8.setSelected(true);
		} else {
			chk_IO8.setSelected(false);
		}
	}

	public static void getGpio() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				Byte gpio = ReaderUtil.readerService.getGpio(ReaderUtil.readers[i]);
				if(gpio != null){
					int result = DataConvert.byteToInt(gpio);
					updateGPIO(result);
					lbl_infoContent.setText("²éÑ¯GPIO×´Ì¬³É¹¦!");
				}else{
					lbl_infoContent.setText("²éÑ¯GPIO×´Ì¬Ê§°Ü!");
				}
			}
		}
	}

	public static void setGpio() {
		for (int i = 0; i < ReaderUtil.MAX_DEVICE_NUM && ReaderUtil.readers[i] != null; i++) {
			if (ReaderUtil.readers[i] != null) {
				boolean result = ReaderUtil.readerService.setGpio(ReaderUtil.readers[i],setGPIO());
				if (result) {
					lbl_infoContent.setText("ÉèÖÃGPIO×´Ì¬³É¹¦!");
				}else{
					lbl_infoContent.setText("ÉèÖÃGPIO×´Ì¬Ê§°Ü!");
				}
			}
		}
	}
}
