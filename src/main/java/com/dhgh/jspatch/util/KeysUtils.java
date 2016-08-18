package com.dhgh.jspatch.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KeysUtils {
	static Properties prop = null;
	static {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(JSReader.class.getResource("/keys.properties").getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static String getPK(String key) {
		if (key == null || key.trim().length() == 0) {
			return null;
		}
		String pK = prop.getProperty(key);
		if (pK == null || pK.trim().length() == 0) {
			return null;
		}
		return pK;
	}
}
