package th.report.facades;

import java.sql.Connection;
import java.util.List;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;
import th.report.api.IReportDataSetBuilder;
import th.report.api.IReportDataSetFacade;
import th.report.builders.ReportDataSetBuilder;
import th.report.entities.ReportBaseInfo;

public class ReportDataSetFacade implements IReportDataSetFacade {

	public List<JConfigure> createDataSet(Connection conn, PrintSet printSet, ReportBaseInfo reportBaseInfo) {
		IReportDataSetBuilder builder = new ReportDataSetBuilder(conn, printSet, reportBaseInfo);
		builder.buildQueryExpression();
		builder.buildBusinessDataSet();

		return builder.getDataSet();
	}
}
