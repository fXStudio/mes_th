package th.report.api;

import java.sql.Connection;
import java.util.List;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;
import th.report.entities.ReportBaseInfo;
import th.report.entities.RequestParam;

public interface IReportDataSetFacade {
	public List<JConfigure> createDataSet(Connection conn, PrintSet printSet, ReportBaseInfo reportBaseInfo);
}
