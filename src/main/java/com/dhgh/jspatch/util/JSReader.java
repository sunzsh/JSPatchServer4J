package com.dhgh.jspatch.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class JSReader {
	public static String DOC_ROOT = null;
	public static String getDocRoot4Config() throws FileNotFoundException, IOException {
		if (DOC_ROOT != null) {
			if (DOC_ROOT.equals("")) {
				return null;
			} else {
				return DOC_ROOT;
			}
		}
		Properties prop = new Properties();
		prop.load(new FileInputStream(JSReader.class.getResource("/config.properties").getPath())); 
		String docRoot = prop.getProperty("doc-root");
		if (docRoot == null || docRoot.trim().length() == 0) {
			DOC_ROOT = "";
			return null;
		}
		DOC_ROOT = docRoot;
		return DOC_ROOT;
	}
	
	
	public static JsFile getJsPatch(String uri) throws MDFileNotExists, FileNotFoundException, IOException {
		String rootPath = JSReader.getDocRoot4Config();
		String file = null;
		if (rootPath == null) {
			URL url = JSReader.class.getResource(uri);
			if (url == null) {
				throw new MDFileNotExists();
			}
			file = url.getFile();
		} else {
			file = rootPath + uri;
		}
		try {
			return ReadFromFile.readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new MDFileNotExists();
		}
	}
	
	public static class JsFile {
		private String fileName;
		private String body;
		private String md5;
		public JsFile() { }
		public JsFile(String fileName, String body) {
			this.fileName = fileName;
			this.body = body;
			if (this.getBody() != null) {
				this.md5 = ParseMD5.parseStrToMd5L32(this.getBody());
			}
		}
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getMd5() {
			if (this.md5 == null) {
				if (this.getBody() != null) {
					this.md5 = ParseMD5.parseStrToMd5L32(this.getBody());
				}
			}
			return md5;
		}
		public void setMd5(String md5) {
			this.md5 = md5;
		}

	}
	
	/**
	 * 文件不存在
	 */
	public static class MDFileNotExists extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7701467538454493465L;
		
		public MDFileNotExists() { }
	}
}
