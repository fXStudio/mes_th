package th.servlet;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.mes.th.helper.Conn_MES;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import th.fx.bean.PrintOrder;
import th.pz.bean.JConfigure;

/*
 * 打印说明：该文件用来处理历史打印。
 * 打印print_data中的数据
 * 打印信息取自print_data表。
 */
public class RepeatPrint extends HttpServlet {
	/**
	 * 重复打印逻辑函数处理
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 设置响应头
		response.setContentType("application/octet-stream");

		// 获得客户端请求参数
		String groupId = request.getParameter("groupid");// 组合ID
		String rq = request.getParameter("rq");// 日期
		String ch = request.getParameter("ch");// 车号
		String js = request.getParameter("js");// 架号

		// 生成要打印的报表
		JasperPrint jasperPrint = handle(groupId, rq, ch, js);

		// 向客户端输出报表
		ServletOutputStream ouputStream = response.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
		oos.writeObject(jasperPrint);

		oos.flush();
		oos.close();

		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * 打印处理
	 * 
	 * @param groupId
	 * @param rq
	 * @param ch
	 * @param js
	 */
	private JasperPrint handle(String groupId, String rq, String ch, String js) {
		Connection conn = null;

		try {
			conn = new Conn_MES().getConn();
			// 展开打印组
			List<PrintOrder> orders = getPrintOrders(conn, groupId, rq, js);

			// 生成答应你报表
			return getJasperPrint(conn, js, rq, ch, orders);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					conn = null;
				}
			}
		}
		return null;
	}

	/**
	 * 展开打印配置项
	 * 
	 * @param conn
	 *            数据库连接
	 * @param groupId
	 *            打印组ID
	 * @throws Exception
	 *             异常
	 */
	private List<PrintOrder> getPrintOrders(Connection conn, String groupId, String rq, String js) throws Exception {
		List<PrintOrder> list = new ArrayList<PrintOrder>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		// 查询语句
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MAX(id) maxId, MIN(id) minId, cCarTypeDesc, cPrintMd, ccode ");
		sb.append("FROM printset ");
		sb.append("WHERE iPrintGroupId = ? ");
		sb.append("GROUP BY cCarTypeDesc, cPrintMd, ccode, iPrintGroupId ");
		sb.append("ORDER BY maxId");

		try {
			// 查询出组内打印配置的数量
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, groupId);
			rs = stmt.executeQuery();

			// 生成展开后的配置单
			while (rs.next()) {
				PrintOrder order = new PrintOrder();
				order.setBeginId(rs.getInt("minId"));
				order.setEndId(rs.getInt("maxId"));
				order.setPrintMd(rs.getString("cPrintMd"));
				order.setcCode(rs.getString("ccode"));
				order.setPrintTitle(rs.getString("cCarTypeDesc"));

				// 增加一个配置单到列表中
				list.add(fillItems(conn, rq, js, order));
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		return list;
	}

	/**
	 * 打印数据项目
	 * 
	 * @param conn
	 * @param printSetId
	 * @param rq
	 * @return
	 */
	private PrintOrder fillItems(Connection conn, String rq, String js, PrintOrder order) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT cseqno, cvincode, cTFAss, cKinNo, inum, cpageNo ");
		sb.append("FROM print_data ");
		sb.append("WHERE iCarNo=? AND cRemark=? AND (iPrintGroupId=? OR iPrintGroupId=?) ");
		sb.append("ORDER BY inum, iPrintGroupId");

		try {
			// 查询出组内打印配置的数量
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, js);
			stmt.setString(2, rq);
			stmt.setInt(3, order.getBeginId());
			stmt.setInt(4, order.getEndId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				JConfigure jc = new JConfigure();
				jc.setCSEQNo_A(rs.getString("cseqno"));
				jc.setCVinCode(rs.getString("cvincode"));
				jc.setCQADNo(rs.getString("cTFAss"));
				jc.setCCarNo(rs.getString("cKinNo"));
				jc.setIndex(rs.getInt("inum"));

				order.setPageNo(rs.getString("cpageNo"));
				order.addItem(jc);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		return order;
	}

	/**
	 * 生成打印报表对象
	 * 
	 * @param order
	 * @return
	 */
	private JasperPrint getJasperPrint(Connection conn, String js, String rq, String ch, List<PrintOrder> orders)
			throws Exception {
		// Servlet上线文对象，可以通过这个对象获得我们所需的应用程序配置信息
		ServletContext context = this.getServletConfig().getServletContext();
		// 报表文件夹路径
		File reportFolder = new File(context.getRealPath("ireport"));
		// 报表对象
		JasperPrint jasperPrint = null;

		// 要传给报表的参数
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("REPORT_CONNECTION", conn);
		parameters.put("js", js);
		parameters.put("zrq", rq);
		parameters.put("ch", ch);
		parameters.put("x_sub", reportFolder.getPath() + "\\");
		parameters.put("xdir", reportFolder.getPath() + "\\");
		parameters.put("SUBREPORT_DIR", reportFolder.getPath() + "\\");

		// 当要打印的配置单项为1的时候，表示在一张单子上只需打印唯一的零件
		if (orders.size() == 1) {
			// 配置单信息对象
			PrintOrder order = orders.get(0);
			// 报表文件模板位置
			File reportFile = new File(context.getRealPath(order.getPrintMd()));

			// 个性参数
			parameters.put("id", order.getBeginId());
			parameters.put("mc", order.getPrintTitle());
			parameters.put("tm", order.getPageNo());

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jasperPrintDataSource(order));
		} else {
			File reportFile = null;

			// 一张报表上面要打印多个不同的件
			for (int i = 0, index = 1; i < orders.size(); i++, index++) {
				// 配置单信息对象
				PrintOrder order = orders.get(i);
				// 获得报表模板文件位置
				if (reportFile == null) {
					reportFile = new File(context.getRealPath(order.getPrintMd()));
				}
				parameters.put(order.getcCode(), new JRBeanCollectionDataSource(order.getItems()));
				parameters.put("id" + index, order.getBeginId());
				parameters.put("mc" + index, order.getPrintTitle());
				parameters.put("tm" + index, order.getPageNo());
			}
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
		}
		return jasperPrint;
	}

	/**
	 * 报表数据源
	 * 
	 * @param order
	 * @return
	 */
	private JRRewindableDataSource jasperPrintDataSource(PrintOrder order) {
		if (order.getItems().size() > 0) {
			return new JRBeanCollectionDataSource(order.getItems());
		}
		return new JREmptyDataSource();
	}
}
