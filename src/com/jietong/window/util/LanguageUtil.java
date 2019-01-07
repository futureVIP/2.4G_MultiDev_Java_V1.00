package com.jietong.window.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtil {
	public static ResourceBundle rs;
	
	static{
		Locale locale = Locale.getDefault();
		StringBuffer sb = new StringBuffer();
		sb.append(locale.getLanguage());
		sb.append("_");
		sb.append(locale.getCountry());
		String language = sb.toString();
		if(language.equals("zh_CN")){
			rs = ResourceBundle.getBundle("language",Locale.CHINA);
		}else{
			rs = ResourceBundle.getBundle("language",Locale.ENGLISH);
		}
	}
}
