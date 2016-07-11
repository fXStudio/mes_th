package th.report.facades;

import java.sql.Connection;

import th.pz.bean.PrintSet;
import th.report.api.IReportBaseInfoBuilder;
import th.report.api.IReportBaseInfoFacade;
import th.report.builders.ReportBaseInfoBuilder;
import th.report.entities.ReportBaseInfo;
import th.report.entities.RequestParam;

/**
 * 基本信息生成器
 * 
 * @author Ajaxfan
 */
public class ReportBaseInfoFacade implements IReportBaseInfoFacade {
	/**
	 * 获取报表基本信息
	 */
	public ReportBaseInfo obtainBaseInfo(Connection conn, RequestParam requestParam, PrintSet printSet) {
		IReportBaseInfoBuilder builder = new ReportBaseInfoBuilder(conn, printSet, requestParam);
		builder.buildDAInfo();
		builder.buildMaxCarNo();
		builder.buildTFassId();
		builder.buildMaxPageNo();
		builder.buildChassisNumber();
		builder.buildVinAndCarTypePair();

		return builder.getReportBaseInfo();
	}
}
