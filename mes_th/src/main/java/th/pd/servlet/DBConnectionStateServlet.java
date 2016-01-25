package th.pd.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.mes.th.helper.Conn_MES;

/**
 * 校验数据库连接状态
 * 
 * @author GaoHF
 */
public class DBConnectionStateServlet extends HttpServlet {
	/***/
	private static final long serialVersionUID = 1L;
	/** 数据库连接池 */
	private static Conn_MES cm = new Conn_MES();

	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean isConn = false;
		
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = cm.getConn();
			stmt = conn.createStatement();
			isConn = stmt.execute("select getDate()");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
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
		resp.getWriter().print(isConn);
	}

	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
