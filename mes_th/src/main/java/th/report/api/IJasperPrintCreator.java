package th.report.api;

import java.sql.Connection;
import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;
import th.report.entities.RequestParam;

public interface IJasperPrintCreator {
	public List<JasperPrint> createJasperPrints(Connection conn, RequestParam requestParam);
}
