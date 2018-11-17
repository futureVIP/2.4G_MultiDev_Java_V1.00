package com.jietong.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesUtils {
	public static Properties prop = new Properties();

	/**
	 * ͨ�������ļ�����ȡ������Ϣ
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getProps(String fileName) {
		String value = null;
		Properties props = new Properties();
		try {
			// ֻ��Ҫ�ļ��� dbconfig.properties��resource/dbconfig.properties������
			String path = "dbconfig.properties";
			props.load(new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(path), "UTF-8"));
			value = props.getProperty(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * ͨ�������ļ�·����ȡ������Ϣ
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties getProperty(String fileName) {
		// ��һ����ȡ��һ��Properties����
		Properties props = new Properties();
		// �ڶ�����ȡ�������ļ���������
		// InputStream is =
		// PropertiesUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");//�ڷ�WEB�����������ַ�ʽ�ȽϷ���
		try {
			InputStream input = new FileInputStream(fileName);
			// �������ǰ������ļ���������load��Properties�����У�
			props.load(input);
			// ע�����ֵ�����
			// props.load(new
			// InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),
			// "UTF-8"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * properties��ȡkeyֵ
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getProperty(String fileName, String key) {
		String value = "";
		// ��һ����ȡ��һ��Properties����
		Properties props = new Properties();
		// �ڶ�����ȡ�������ļ���������
		// InputStream is =
		// PropertiesUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");//�ڷ�WEB�����������ַ�ʽ�ȽϷ���
		try {
			// InputStream input = new
			// FileInputStream("dbconfig.properties");//��WEB�����������ַ�ʽ�ȽϷ��㣬�����������ļ��Ƿ��ڷ�ClasspathĿ¼�µ�ʱ��Ҳ��Ҫ�����ַ�ʽ
			// �������������ļ���������load��Properties�����У������ں���Ϳ���ֱ��ȡ������
			props.load(new InputStreamReader(PropertiesUtils.class
					.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
			value = props.getProperty(key);
			// is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * propertiesд��keyֵ
	 * 
	 * @param fileName
	 * @param data
	 */
	public static String setProperty(String fileName, Map<String, String> data) {
		String message = "true";

		// ��һ��Ҳ��ȡ��һ��Properties����
		Properties props = new Properties();
		// �ڶ���Ҳ��ȡ�ø������ļ���������
		//"dbconfig.properties"
		 InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
		try {
			//InputStream input = new FileInputStream(fileName);
			// �������ǰ������ļ���������load��Properties�����У�
			// props.load(new
			// InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName),
			// "UTF-8"));
			props.load(input);
			// �������Ϳ�������������ļ��������������
			// props.setProperty(key, value);
			if (data != null) {
				Iterator<Entry<String, String>> iter = data.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					props.setProperty(entry.getKey().toString(), entry
							.getValue().toString());
				}
			}
			// �ڱ��������ļ�֮ǰ����Ҫȡ�ø������ļ�����������мǣ��������Ŀ����Ҫ����������һ����WEB��Ŀ��
			// ��������ļ�Ӧ�����ڸ�Ŀ¼�£��������ʾ�Ҳ��������ļ�
			OutputStream out = new FileOutputStream(fileName);
			// ����������Properties���󱣴������ļ�����������ļ���;
			props.store(out, null);
			input.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "false";
			e.printStackTrace();
		}

		return message;
	}

	/**
	 * ȡ��ֵ
	 * 
	 * @param k
	 * @param filepath
	 * @return
	 */
	public static String getValue(String k, String filepath) {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(filepath));
			prop.load(in); // /���������б�
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (key.equals(k)) {
					return prop.getProperty(key);
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ���ü�ֵ
	 * 
	 * @param filepath
	 * @param map
	 */
	public static void setValue(String filepath, Map<String, String> map) {
		// /�������Ե�b.properties�ļ�
		FileOutputStream oFile;
		try {
			System.out.println("s" + filepath);
			oFile = new FileOutputStream(filepath, false);
			// true��ʾ׷�Ӵ�
			System.out.println(map.get("key") + ",,," + map.get("value"));
			prop.setProperty(map.get("key"), map.get("value"));
			// prop.put(map.get("key"), map.get("value"));
			prop.store(oFile, "The New properties file");
			oFile.close();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//System.out.println(PropertiesUtils.getProps("dbconfig.properties"));
		//System.out.println(PropertiesUtils.getProperty("src/dbconfig.properties"));
//		 Map<String, String> data = new HashMap<String, String>();
//		 data.put("config.port", "8000");
//		 data.put("db.username", "root1");
//		 data.put("db.password", "root1");
//		 PropertiesUtils.setProperty("dbconfig.properties", data);
		 System.out.println(getProps("config.port"));
	}
}