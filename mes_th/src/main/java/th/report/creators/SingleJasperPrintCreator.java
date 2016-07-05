package th.report.creators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Conn_MES;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;
import th.report.api.IReportBaseInfoFacade;
import th.report.api.IReportDataSetFacade;
import th.report.entities.ReportBaseInfo;
import th.report.entities.RequestParam;
import th.report.facades.ReportBaseInfoFacade;
import th.report.facades.ReportDataSetFacade;
import th.report.helper.JasperTemplateLoader;

/**
 * 单独打印
 * 
 * @author Ajaxfan
 */
public class SingleJasperPrintCreator extends SimpleCreator {
	/** 报表基础数据 */
	private IReportBaseInfoFacade reportBaseInfoFacade = new ReportBaseInfoFacade();
	/** 报表明细数据 */
	private IReportDataSetFacade reportDataSetFacade = new ReportDataSetFacade();

	/**
	 * 创建打印报表
	 */
	public List<JasperPrint> createJasperPrints(Connection conn, RequestParam requestParam) {
		List<JasperPrint> list = new ArrayList<JasperPrint>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = new Conn_MES().getConn();
			stmt = conn.prepareStatement("SELECT cCode FROM printSet WHERE iPrintGroupId = ? ORDER BY id");
			stmt.setString(1, requestParam.getGroupId());
			rs = stmt.executeQuery();

			while (rs.next()) {// 遍历要打印的组下包含的所有打印项目
				PrintSet printSet = createPrintSet(rs);
				ReportBaseInfo reportBaseInfo = reportBaseInfoFacade.obtainBaseInfo(conn, requestParam, printSet);
				List<JConfigure> dataset = reportDataSetFacade.createDataSet(conn, printSet, reportBaseInfo);

				createJasperPrints(conn, printSet, reportBaseInfo, dataset, requestParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		return list;
	}

	/**
	 * @param conn
	 * @param printSet
	 * @param reportBaseInfo
	 * @param dataset
	 * @param requestParam
	 * @return
	 */
	private JasperPrint createJasperPrints(Connection conn, PrintSet printSet, ReportBaseInfo reportBaseInfo,
	        List<JConfigure> dataset, RequestParam requestParam) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();

		// 定义datasource;
		parameters.put("REPORT_CONNECTION", conn);
		parameters.put("js", reportBaseInfo.getCarno());
		parameters.put("zrq", requestParam.getRequestDate());
		parameters.put("ch", reportBaseInfo.getCarno());
		parameters.put("tm", reportBaseInfo.getChassisNumber());
		parameters.put("mc", printSet.getCDescrip());
		parameters.put("id", printSet.getId());

		JasperReport jasperReport = (JasperReport) JRLoader
		        .loadObject(JasperTemplateLoader.load(printSet.getCPrintMD()));

		return JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(dataset));
	}
}
