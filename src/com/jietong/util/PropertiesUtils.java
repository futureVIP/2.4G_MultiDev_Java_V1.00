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
	 * 通过配置文件名获取配置信息
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getProps(String fileName) {
		String value = null;
		Properties props = new Properties();
		try {
			// 只需要文件名 dbconfig.properties与resource/dbconfig.properties的区别
			String path = "dbconfig.properties";
			props.load(new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(path), "UTF-8"));
			value = props.getProperty(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 通过配置文件路径获取配置信息
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties getProperty(String fileName) {
		// 第一步是取得一个Properties对象
		Properties props = new Properties();
		// 第二步是取得配置文件的输入流
		// InputStream is =
		// PropertiesUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");//在非WEB环境下用这种方式比较方便
		try {
			InputStream input = new FileInputStream(fileName);
			// 第三步是把配置文件的输入流load到Properties对象中，
			props.load(input);
			// 注意两种的区别
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
	 * properties获取key值
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getProperty(String fileName, String key) {
		String value = "";
		// 第一步是取得一个Properties对象
		Properties props = new Properties();
		// 第二步是取得配置文件的输入流
		// InputStream is =
		// PropertiesUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");//在非WEB环境下用这种方式比较方便
		try {
			// InputStream input = new
			// FileInputStream("dbconfig.properties");//在WEB环境下用这种方式比较方便，不过当配置文件是放在非Classpath目录下的时候也需要用这种方式
			// 第三步讲配置文件的输入流load到Properties对象中，这样在后面就可以直接取来用了
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
	 * properties写入key值
	 * 
	 * @param fileName
	 * @param data
	 */
	public static String setProperty(String fileName, Map<String, String> data) {
		String message = "true";

		// 第一步也是取得一个Properties对象
		Properties props = new Properties();
		// 第二步也是取得该配置文件的输入流
		//"dbconfig.properties"
		 InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
		try {
			//InputStream input = new FileInputStream(fileName);
			// 第三步是把配置文件的输入流load到Properties对象中，
			// props.load(new
			// InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName),
			// "UTF-8"));
			props.load(input);
			// 接下来就可以随便往配置文件里面添加内容了
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
			// 在保存配置文件之前还需要取得该配置文件的输出流，切记，如果该项目是需要导出的且是一个非WEB项目，
			// 则该配置文件应当放在根目录下，否则会提示找不到配置文件
			OutputStream out = new FileOutputStream(fileName);
			// 最后就是利用Properties对象保存配置文件的输出流到文件中;
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
	 * 取出值
	 * 
	 * @param k
	 * @param filepath
	 * @return
	 */
	public static String getValue(String k, String filepath) {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(filepath));
			prop.load(in); // /加载属性列表
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
	 * 设置键值
	 * 
	 * @param filepath
	 * @param map
	 */
	public static void setValue(String filepath, Map<String, String> map) {
		// /保存属性到b.properties文件
		FileOutputStream oFile;
		try {
			System.out.println("s" + filepath);
			oFile = new FileOutputStream(filepath, false);
			// true表示追加打开
			System.out.println(map.get("key") + ",,," + map.get("value"));
			prop.setProperty(map.get("key"), map.get("value"));
			// prop.put(map.get("key"), map.get("value"));
			prop.store(oFile, "The New properties file");
			oFile.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
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