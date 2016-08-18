package com.dhgh.jspatch.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhgh.jspatch.util.Des3Util;
import com.dhgh.jspatch.util.JSReader;
import com.dhgh.jspatch.util.KeysUtils;
import com.dhgh.jspatch.util.JSReader.JsFile;
import com.dhgh.jspatch.util.JSReader.MDFileNotExists;

public class JSServlet extends HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2910730214903663112L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/javascript; charset=utf-8");
		String uri = req.getServletPath();
		
		JsFile jsFile = null;
		try {
			jsFile = JSReader.getJsPatch(uri);
		} catch (MDFileNotExists e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String version = req.getParameter("v");
		PrintWriter out = resp.getWriter();
		resp.setHeader("version", jsFile.getMd5());
		if (version != null && version.trim().length() > 0) {
			if (jsFile.getMd5().equalsIgnoreCase(version)) {
				out.print("");
				return;
			}
		}
		String content = jsFile.getBody();
		String pkKey = getPK(uri);
		String pk = KeysUtils.getPK(pkKey);
		if (pk != null) {
			try {
				content = Des3Util.encode(content, pk);
			} catch (Exception e) {
				e.printStackTrace();
				content = "";
			}
		}
		out.println(content);
	}
	
	private String getPK(String uri) {
		int in = uri.indexOf('/', 1);
		if (in <= 0) {
			return "ROOT";
		}
		return uri.substring(1, in);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
