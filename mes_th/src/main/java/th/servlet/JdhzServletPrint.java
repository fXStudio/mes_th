package th.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import th.report.entities.RequestParam;

/**
 * @author Administrator
 */
public class JdhzServletPrint extends HttpServlet {
	/** 序列号 */
	private static final long serialVersionUID = 1L;
	/** 日期格式化工具 */
	private SimpleDateFormat date_fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 系统日志工具 */
	private Logger logger = Logger.getLogger(JdhzServletPrint.class);

	/**
	 * 数据处理
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 设置响应头
		response.setContentType("application/octet-stream");
		
		// 从客户端接收到的请求参数
		RequestParam requestParam = new RequestParam();
		requestParam.setRequestDate(request.getParameter("rq"));// 请求日期
		requestParam.setChassisNumber(request.getParameter("ch"));// 底盘号
		requestParam.setGroupId(request.getParameter("groupid"));// 打印组号
	}
}
