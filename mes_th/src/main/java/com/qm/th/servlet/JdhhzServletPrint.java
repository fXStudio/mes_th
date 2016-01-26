package com.qm.th.servlet;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.th.beans.JConfigure;
import com.qm.th.helpers.Conn_MES;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class JdhhzServletPrint extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Connection con = null;
		String ls = "";
		String rq = "";
		String ch = "";
		String sql = "";
		String js = "";
		ls = request.getParameter("ls");
		rq = request.getParameter("rq");
		ch = request.getParameter("ch");
		js = request.getParameter("js");

		Statement stmt = null;
		Statement insert_stmt = null;
		Statement insert_c = null;
		Statement x_stmt = null;
		Statement v_stmt = null;
		Statement ps_stmt = null;
		ResultSet rs = null;
		ResultSet rs_c = null;
		ResultSet x_rs = null;
		ResultSet v_rs = null;
		List list = new ArrayList();
		int index = 0;
		String insert_sql = "";
		String x_sql = "";
		int zhid; // 组合id
		String dh = ""; // 单号
		String dysj = ""; // 打印时间
		int cjs; // 车架数
		int xh; // 序号
		String vincode = ""; // vin码
		String sxh = ""; // 顺序号
		int zcid; // 天合总成名称id
		int cls; // 车辆数
		String bz = ""; // 备注
		String kinh = ""; // kin号

		int indexx = 0;
		// 系统日期
		Calendar ca = Calendar.getInstance();
		Date da = ca.getTime();
		SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
		String sj = formt.format(da);

		// 架数
		String c_sql = "select max(iCarNo) as tt  from print_data where iPrintGroupId=4 and convert(varchar(10),dPrintTime,20)='"
				+ rq + "'";
		// 序号
		x_sql = "select max(inum) as x  from print_data where iPrintGroupId=4 and convert(varchar(10),dPrintTime,20)='"
				+ rq + "'";
		String v_sql = "select cSEQNo_A,id from printSet where iPrintGroupId=4 ";

		try {
			Conn_MES conmes = new Conn_MES();
			con = conmes.getConn();
			ps_stmt = con.createStatement();
			// 车架数
			insert_c = con.createStatement();
			rs_c = insert_c.executeQuery(c_sql);
			if (rs_c.next()) {
				cjs = rs_c.getInt("tt") + 1;

			} else {
				cjs = 1;
			}
			// 序号
			x_stmt = con.createStatement();
			x_rs = x_stmt.executeQuery(x_sql);
			if (x_rs.next()) {
				index = x_rs.getInt("x") + 1;
			} else {
				index = 1;
			}
			StringBuilder st = new StringBuilder();
			v_stmt = con.createStatement();
			v_rs = v_stmt.executeQuery(v_sql);
			if (v_rs.next()) {

				sql += "select top 6 c.cSEQNo_A,c.cVinCode,c.cCarType,cQADNo,sc.ITFASSNameId,sc.iTFASSNum,c.cCarNo from carData c,carData_D sc,TFASSName t";
				sql += " where c.ccarno=sc.iCarId and substring(c.ccarno,6,1)='0' and t.cTFASSName= '后滑轴' and sc.ITFASSNameId = t.id and convert(varchar(10),c.dABegin,20) = '"
						+ rq + "' and c.cSEQNo_A>'" + v_rs.getString("c.cSEQNo_A") + "' order by c.cSEQNo_A";
				insert_stmt = con.createStatement();
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					JConfigure jc = new JConfigure();
					jc.setCQADNo(rs.getString("cQADNo"));
					jc.setCSEQNo_A(rs.getString("cSEQNo_A"));
					jc.setCVinCode(rs.getString("cVinCode"));
					jc.setCCarType(rs.getString("cCarType"));
					index++;
					indexx++;
					jc.setIndex(indexx);
					list.add(jc);
					vincode = rs.getString("cVinCode");// vin
					sxh = rs.getString("cSEQNo_A");// 顺序号
					zcid = rs.getInt("ITFASSNameId");// 总成id
					cls = rs.getInt("iTFASSNum");// 车辆数
					dysj = formt.format(da); // 打印时间
					kinh = rs.getString("cCarNo"); // kin号

					// 打印表插入
					insert_sql = " insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) "
							+ " values('4','1','"
							+ dysj
							+ "',"
							+ cjs
							+ ","
							+ index
							+ ",'"
							+ vincode
							+ "','"
							+ sxh
							+ "','d','" + zcid + "','" + cls + "','454545','" + kinh + "')";
					insert_stmt.executeUpdate(insert_sql);
				}
				if (rs.isLast()) {
					String psu_sql = "update printSet set cSEQNo_A ='" + rs.getString("cSEQNo_A") + "' where  id ="
							+ v_rs.getInt("id") + "";
					ps_stmt.executeUpdate(psu_sql);
				}
			} else {
				sql += "select top 6 c.cSEQNo_A,c.cVinCode,c.cCarType,cQADNo,sc.ITFASSNameId,sc.iTFASSNum,c.cCarNo from carData c,carData_D sc,TFASSName t";
				sql += " where  c.ccarno=sc.iCarId and substring(c.ccarno,6,1)='0' and t.cTFASSName= '后滑轴' and sc.ITFASSNameId = t.id and convert(varchar(10),c.dABegin,20) = '"
						+ rq + "'  order by c.cSEQNo_A";

				insert_stmt = con.createStatement();
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					JConfigure jc = new JConfigure();
					jc.setCQADNo(rs.getString("cQADNo"));
					jc.setCSEQNo_A(rs.getString("cSEQNo_A"));
					jc.setCVinCode(rs.getString("cVinCode"));
					jc.setCCarType(rs.getString("cCarType"));
					index++;
					indexx++;
					jc.setIndex(indexx);
					list.add(jc);
					vincode = rs.getString("cVinCode");// vin
					sxh = rs.getString("cSEQNo_A");// 顺序号
					zcid = rs.getInt("ITFASSNameId");// 总成id
					cls = rs.getInt("iTFASSNum");// 车辆数
					dysj = formt.format(da); // 打印时间
					kinh = rs.getString("cCarNo"); // kin号

					// 打印表插入
					insert_sql = " insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) "
							+ " values('4','1','"
							+ dysj
							+ "',"
							+ cjs
							+ ","
							+ index
							+ ",'"
							+ vincode
							+ "','"
							+ sxh
							+ "','d','" + zcid + "','" + cls + "','454545','" + kinh + "')";
					insert_stmt.executeUpdate(insert_sql);
					if (rs.isLast()) {
						String ps_sql = "insert into printSet(cCode,cDescrip,iPrintGroupId,cLastVin,cSEQNo_A) values(1,'捷达后轴',4,'"
								+ rs.getString("cVinCode") + "','" + rs.getString("cSEQNo_A") + "')";
						ps_stmt.executeUpdate(ps_sql);

					}
				}

			}

			// ireport
			ServletContext context = this.getServletConfig().getServletContext();
			String temp = "ireport/jhhz.jasper";
			File reportFile = new File(context.getRealPath(temp));
			File reportFile1 = new File(context.getRealPath("ireport"));
			Map parameters = new HashMap();

			parameters.put("REPORT_CONNECTION", con);
			parameters.put("SUBREPORT_DIR", reportFile1.getPath() + "\\");
			parameters.put("js", cjs);
			parameters.put("zrq", rq);
			JasperPrint jasperPrint = null;
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
			if (jasperPrint != null) {
				response.setContentType("application/octet-stream");
				ServletOutputStream ouputStream = response.getOutputStream();

				ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
				oos.writeObject(jasperPrint);

				oos.flush();
				oos.close();

				ouputStream.flush();
				ouputStream.close();
			} else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>JasperReports - Web Application Sample</title>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
				out.println("</head>");

				out.println("<body bgcolor=\"white\">");

				out.println("<span class=\"bold\">Empty response.</span>");

				out.println("</body>");
				out.println("</html>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
		}
	}

}
