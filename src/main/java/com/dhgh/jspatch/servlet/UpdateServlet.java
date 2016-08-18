package com.dhgh.jspatch.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhgh.jspatch.util.CMDExcute;
import com.dhgh.jspatch.util.JSReader;

public class UpdateServlet extends HttpServlet{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5639488752688533580L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();

		String rootPath = JSReader.getDocRoot4Config();
		String file = null;
		if (rootPath == null) {
			URL url = JSReader.class.getResource("/");
			if (url == null) {
				out.println("{\"result\":\"0\", \"msg\": \"api路径不正确\"}");
				return;
			}
			file = url.getFile();
		} else {
			file = rootPath;
		}
		String cmd = "pushd "+file+" && git pull && popd";
		System.out.println(cmd);
		String result = CMDExcute.exec(cmd);
		out.println("{\"result\":\"0\", \"msg\": \""+result.trim().replaceAll("\"", "\\\"").replaceAll("\r\n",	"\\\\r\\\\n").replaceAll("\n", "\\\\r\\\\n")+"\"}");
	}

	
}
