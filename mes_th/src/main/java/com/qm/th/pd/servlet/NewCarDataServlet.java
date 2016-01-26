package com.qm.th.pd.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.th.listener.InitialListener;

/**
 * 新车查询
 * 
 * @author AjaxFan
 */
public class NewCarDataServlet extends HttpServlet {
	/** 序列号 */
	private static final long serialVersionUID = 1L;
	
	/** 用户临时目录 */
	private String _user_tmp_dir = System.getProperty("java.io.tmpdir");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		File file = new File(_user_tmp_dir + File.separator
				+ InitialListener.FILE_NAME);

		if (file.exists()) {
			file.delete();
		}
		resp.getWriter().println("true");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		while (true) {
			// 判断文件是否存在
			if (new File(_user_tmp_dir + File.separator + InitialListener.FILE_NAME).exists()) {
				resp.getWriter().println("true");

				return;
			}
			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
