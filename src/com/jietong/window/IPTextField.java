package com.jietong.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;


// 主IP输入框，IPTextField：
public class IPTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	private String st1 = "";
	private String st2 = "";
	private String st3 = "";
	private String st4 = "";

	private JTextIPSpace t1;
	private JTextIPSpace t2;
	private JTextIPSpace t3;
	private JTextIPSpace t4;

	final private static int RADII = 2;
	final private static int SPACEINTERVAL = 2;

	private Color DefaultColor_Selected = new Color(255, 0, 0);
	private Color DefaultColor_NoSelected = Color.black;

	public IPTextField(boolean isSelected) {
		this(isSelected, "");
	}

	public IPTextField(String ipAddress) {
		this(false, ipAddress);
	}

	public IPTextField() {
		this(false);
	}

	public IPTextField(boolean isSelected, Color colorSelect,
			Color colorNoSelect) {
		this(isSelected, "", colorSelect, colorNoSelect);
	}

	public IPTextField(boolean isSelected, String ipAddress) {
		this(isSelected, "", null, null);
	}

	public IPTextField(boolean isSelected, String ipAddress, Color colorSelect,
			Color colorNoSelect) {
		super();
		analyzeStr(ipAddress);
		InitIPField();
		setChildComponent(false);
		setLayout(null);

		t1.setPrevNextComponent(t4, t2);
		t2.setPrevNextComponent(t1, t3);
		t3.setPrevNextComponent(t2, t4);
		t4.setPrevNextComponent(t3, t1);

		add(t1);
		add(t2);
		add(t3);
		add(t4);

		addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				changeChildComponent();
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
			}
		});
		setChildComponent(true);
		if (colorSelect != null)
			DefaultColor_Selected = colorSelect;
		if (colorNoSelect != null)
			DefaultColor_NoSelected = colorNoSelect;
		setSelectEdge(isSelected, DefaultColor_Selected,
				DefaultColor_NoSelected);
	}

	public void setSelected(Color color) {
		DefaultColor_Selected = color;
	}

	public void setSelected() {
		DefaultColor_Selected = Color.blue;
	}

	public void setNoSelected(Color color) {
		DefaultColor_NoSelected = color;
	}

	public void setNoSelected() {
		DefaultColor_NoSelected = Color.black;
	}

	public void setSelectEdge(boolean isSelected, Color selected,
			Color noSelected) {
		Border border;
		if (isSelected)
			border = BorderFactory.createLineBorder(selected.brighter(), 1);
		else
			border = BorderFactory.createLineBorder(noSelected, 1);
		setBorder(border);
	}

	public Dimension getPreferredSize() {
		Dimension dim = new Dimension(170, 30);
		return dim;
	}

	private void InitIPField() {
		t1 = new JTextIPSpace(st1, 255, true);
		t2 = new JTextIPSpace(st2, 255, true);
		t3 = new JTextIPSpace(st3, 255, true);
		t4 = new JTextIPSpace(st4, 255, true);
	}

	private void setTextIPField() {
		t1.setText(st1);
		t2.setText(st2);
		t3.setText(st3);
		t4.setText(st4);
	}

	public void setText(String text) {
		st1 = "";
		st2 = "";
		st3 = "";
		st4 = "";
		analyzeStr(text);
		setTextIPField();
	}

	public void setIP(String ip) {
		setText(ip);
	}

	public String getIP() {
		return availIP();
	}

	public String getText() {
		return getIP();
	}

	public void setIPValue(int ip) {
	}

	public int getIPValue() {
		return isIPaddress(getIP());
	}

	private String availIP() {
		String strTemp[] = { t1.getText().trim(), t2.getText().trim(),
				t3.getText().trim(), t4.getText().trim() };
		String returnIP = "";
		for (int i = 0; i < strTemp.length; i++) {
			if (!strTemp[i].equals("")) {
				if (!returnIP.equals(""))
					returnIP += "." + strTemp[i];
				else
					returnIP = strTemp[i];
			}
		}
		return returnIP;
	}

	public static int isIPaddress(String ipAddress) {
		int pointNum = 0, numberNum = 0;
		int numberVal = 0;
		int len = ipAddress.length();
		int m = 0;
		int IPVal = 0;
		char tempchar;
		while (m < len
				&& ((tempchar = ipAddress.charAt(m)) == '.' || Character
						.isDigit(tempchar))) {
			m++;
			if ((48 <= tempchar) && (tempchar <= 57)) {
				if (numberNum > 2)
					return -1;
				numberVal *= 10;
				numberVal += tempchar - '0';
				if (numberVal > 255)
					return -1;
				numberNum++;
			} else {
				if (numberNum == 0)
					return -1;
				if (pointNum == 3)
					return -1;
				pointNum++;
				IPVal *= 256;
				IPVal += numberVal;
				numberNum = 0;
				numberVal = 0;
			}
		}
		if (m != len || pointNum != 3 || numberNum == 0)
			return -1;
		pointNum++;
		IPVal *= 256;
		IPVal += numberVal;
		return IPVal;
	}

	private void analyzeStr(String str) {
		try {
			StringTokenizer st = new StringTokenizer(str, ".");

			String tk = "";

			int i = 0;
			while (st.hasMoreTokens() && i <= 3) {
				i++;
				tk = st.nextToken();
				if (!tk.equals("")) {
					switch (i) {
					case 1:
						st1 = tk;
						break;
					case 2:
						st2 = tk;
						break;
					case 3:
						st3 = tk;
						break;
					case 4:
						st4 = tk;
						break;
					}
				}
				tk = "";
			}
		} catch (Exception ex) {
			st1 = "";
			st2 = "";
			st3 = "";
			st4 = "";
		}
	}

	private void changeChildComponent() {
		Dimension sz = getSize();
		int spaceWidth = sz.width / 4;
		int spaceHeight = sz.height - 2 * SPACEINTERVAL;
		int r = RADII;

		t1.setBounds(0 + SPACEINTERVAL, 0 + SPACEINTERVAL, spaceWidth - r,
				spaceHeight);
		t2.setBounds(spaceWidth + r, 0 + SPACEINTERVAL, spaceWidth - r,
				spaceHeight);
		t3.setBounds(2 * spaceWidth + r, 0 + SPACEINTERVAL, spaceWidth - r,
				spaceHeight);
		t4.setBounds(3 * spaceWidth + r, 0 + SPACEINTERVAL, spaceWidth - r
				- SPACEINTERVAL, spaceHeight);

		repaint();
	}

	private void setChildComponent(boolean isVisible) {
		t1.setVisible(isVisible);
		t2.setVisible(isVisible);
		t3.setVisible(isVisible);
		t4.setVisible(isVisible);
		repaint();
	}

	/**
	 * 绘制IP点
	 */
	public void paintComponent(Graphics g) {
		Dimension sz = getSize();
		g.setColor(Color.white);
		g.fillRect(0, 0, sz.width, sz.height);

		int spaceWidth = sz.width / 4;
		int spaceHeight = sz.height;
		int r = RADII;
		g.setColor(Color.black);
		for (int i = 1; i <= 3; i++) {
			g.fillOval(i * spaceWidth, spaceHeight - 2 * r, r, r);
		}
	}
}

@SuppressWarnings("serial")
class IPDocument extends PlainDocument {
	public JTextField jtext;

	public IPDocument(JTextField jtext) {
		this.jtext = jtext;
	}

	@SuppressWarnings("unused")
	public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {
		char[] insertChars = str.toCharArray();
		boolean valid = true;
		if (getLength() >= 3) {
			valid = false;
			return;
		}
		for (int i = 0; i < Math.min(insertChars.length, 3); i++) {
			if (!(Character.isDigit(insertChars[i]))) {
				valid = false;
				break;
			} else {
				valid = true;
				break;
			}
		}
		if (valid)
			super.insertString(offset, str, a);
		if (getLength() > 3)
			super.remove(offset + str.length(), getLength() - 2);
	}
}

// IP框中的每一个输入框，JTextIPSpace：

@SuppressWarnings("serial")
class JTextIPSpace extends JTextField {
	public int minValue = 0;
	public int maxValue = 255;
	public JTextIPSpace textipspace;
	public boolean bFirstLostFocus = true;
	public boolean isCanFocus = false;
	public JTextField prevComponent;
	public JTextField nextComponent;

	public JTextIPSpace() {
		this(null, 255, false);
	}

	public JTextIPSpace(String str) {
		this(str, 255, true);
	}

	public JTextIPSpace(int maxValue, boolean isCanFocus) {
		this(null, maxValue, isCanFocus);
	}

	public JTextIPSpace(String str, int maxValue, boolean isCanFocus) {
		super(str);
		setNoEdge();
		setMidHorizontal();
		addLostFocus();
		addKeySet();
		addComponentChange();

		textipspace = this;
		this.maxValue = maxValue;
		this.isCanFocus = isCanFocus;
	}

	public void setNoEdge() {
		setBorder(null);
		setOpaque(true);
	}

	public void setPrevNextComponent(JTextField prev, JTextField next) {
		this.prevComponent = prev;
		this.nextComponent = next;
	}

	private void addComponentChange() {
		ComponentListener cl = new ComponentListener() {
			public void componentResized(ComponentEvent e) {

			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
			}
		};
		addComponentListener(cl);
	}

	private void addLostFocus() {
		FocusListener fl = new FocusListener() {
			public void focusLost(FocusEvent e) {
				String tmp = getText().trim();
				if (tmp.equals(""))
					return;
				if (Integer.parseInt(tmp) > maxValue && bFirstLostFocus == true) {
					bFirstLostFocus = false;
					JOptionPane.showConfirmDialog(textipspace, tmp
							+ "不是一个合法的项目，请输入" + minValue + "～" + maxValue
							+ "之间的数值！", "提示", JOptionPane.CLOSED_OPTION);
					setText(String.valueOf(maxValue));
					requestFocus();
					repaint();
				}
			}

			public void focusGained(FocusEvent e) {
				bFirstLostFocus = true;
				repaint();
			}

		};
		addFocusListener(fl);
	}

	private void addKeySet() {
		KeyAdapter ka = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					int pos = getCaretPosition();
					if (pos == 0) {
						gotoComponent(prevComponent);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (prevComponent != null)
						prevComponent.selectAll();
					gotoComponent(prevComponent);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (nextComponent != null)
						nextComponent.selectAll();
					gotoComponent(nextComponent);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					int pos = getCaretPosition();
					int maxPos = getText().trim().length();
					if (pos == maxPos || maxPos == 0) {
						gotoComponent(nextComponent);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					int pos = getCaretPosition();
					if (pos == 0) {
						gotoComponent(prevComponent);
					}
				} else if (e.getKeyChar() == '.') {
					int maxPos = getText().trim().length();
					if (maxPos > 0) {
						if (getSelectedText() == null) {
							if (nextComponent != null)
								nextComponent.selectAll();
							gotoComponent(nextComponent);
						}
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar())) {
					int pos = getCaretPosition();
					if (pos >= 3) {
						gotoComponent(nextComponent);
					}
				}
			}
		};
		addKeyListener(ka);
	}

	public void gotoComponent(JComponent jc) {
		if (jc != null)
			jc.requestFocus();
	}

	public void setMidHorizontal() {
		setHorizontalAlignment(JTextField.CENTER);
	}

	protected Document createDefaultModel() {
		return new IPDocument(this);
	}

	public boolean isFocusTraversable() {
		return isCanFocus;
	}
}
