package th.util.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常报文提示
 * 
 * @author GaoHF
 */
public class HintProductDataError extends HttpServlet {
	/** SerialCode */
	private static final long serialVersionUID = 1L;
	/** 最后一个异常文件时间 */
	private volatile long lasted;
	/** 最后5个异常报文 */
	private static File[] arr = new File[5];
	/** 目录 */
	private static final String PATH = AccessRegedit.getPath(IProperty.LOCAL_FAILURE_DIR_PATH);
	/** 日期格式 */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 文件头类型
		response.setContentType("text/html;charset=GBK");
		// 输出流对象
		PrintWriter out = response.getWriter();
		// 文件完整路径
		lastErrorFile(new File(PATH + new SimpleDateFormat("/yyyy/MM/dd").format(new Date())));
		// 请求类型 0: 提示窗  1:文件列表
		String requestType = request.getParameter("reqType");

		// 异常文件查询
		if("1".equals(requestType)){
			out.println("{");
			for(int i = 0; i < arr.length; i++){
				File f = arr[i];
				if(f != null){
					if(i > 0){out.print(",");}

					out.print("\"");
					out.print(f.getName());
					out.print("\":\"");
					out.print(df.format(new Date(f.lastModified())));
					out.print("\"");
				}
			}
			out.println("}");
		}
		// 异常信息提示
		else if("0".equals(requestType)){
			if (lasted > 0) {
				String time = df.format(new Date(lasted));
				out.println("{date:\"" + time + "\"}");
			}
		}
		lasted = 0;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 获得最后一个文件的生成日期
	 * 
	 * @param dir
	 */
	private synchronized void lastErrorFile(File dir) {
		if (dir.exists()) {
			for (File f : dir.listFiles()) {
				if (f.isDirectory()) {
					lastErrorFile(f);
					continue;
				}
				// 异常文件长生时间
				long modifyTime = f.lastModified();
				// 获得最近异常文件时间
				lasted = Math.max(lasted, modifyTime);
				// 组建文件队列
				swap(f);
			}
		}
	}
	
	/**
	 * 获得最后几个异常报文
	 * 
	 * @param file
	 */
	private synchronized void swap(File file) {
		// 文件序列
		for (int i = 0; i < arr.length; i++) {
			// 取出一个文件对象
			File f = arr[i];
			// 前一个文件修改时间
			long pre = f != null ? f.lastModified() : 0;
			// 当前文件修改时间
			long no = file.lastModified();
			
			// 将新文件放入队列
			if (f == null || pre <= no) {
				arr[i] = file;
				break;
			}
		}
	}
}
