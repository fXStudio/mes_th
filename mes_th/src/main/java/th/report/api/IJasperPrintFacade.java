package th.report.api;

import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;
import th.report.entities.RequestParam;

public interface IJasperPrintFacade {
	public List<JasperPrint> createReports(RequestParam requestParam);
}
