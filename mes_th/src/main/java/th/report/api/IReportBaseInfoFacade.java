package th.report.api;

import java.sql.Connection;

import th.pz.bean.PrintSet;
import th.report.entities.ReportBaseInfo;
import th.report.entities.RequestParam;

public interface IReportBaseInfoFacade {
	/**
	 * 获取基本信息
	 * @param conn
	 * @param requestParam
	 * @param printSet
	 * @return
	 */
	public ReportBaseInfo obtainBaseInfo(Connection conn, RequestParam requestParam, PrintSet printSet);
}
