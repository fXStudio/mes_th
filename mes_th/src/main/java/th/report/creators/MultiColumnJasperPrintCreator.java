package th.report.creators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;
import th.report.entities.ReportBaseInfo;
import th.report.entities.RequestParam;
import th.report.helper.JasperTemplateLoader;

/**
 * 多列报表打印
 * 
 * @author Ajaxfan
 */
public class MultiColumnJasperPrintCreator extends BaseImplCreator {
    /**
     * 创建多列报表
     */
    public List<JasperPrint> createJasperPrints(Connection conn, RequestParam requestParam) {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM printSet WHERE iPrintGroupId = ? ORDER BY id");
            stmt.setString(1, requestParam.getGroupId());
            rs = stmt.executeQuery();

            Map<String, Object> parameters = new HashMap<String, Object>();
            JasperReport jasperReport = null;

            for (int i = 1; rs.next(); i++) {// 遍历要打印的组下包含的所有打印项目
                PrintSet printSet = createPrintSet(rs);
                ReportBaseInfo reportBaseInfo = reportBaseInfoFacade.obtainBaseInfo(conn, requestParam, printSet);
                List<JConfigure> dataset = reportDataSetFacade.createDataSet(conn, printSet, reportBaseInfo);
                
                System.out.println(dataset.size());
                parameters.put("datasource" + i, dataset);
                parameters.put("mc" + i, printSet.getCCarTypeDesc());
                parameters.put("id" + i, printSet.getId());
                parameters.put("tm" + i, printSet.getCRemark());

                if (jasperReport == null) {
                    parameters.put("js", String.valueOf(reportBaseInfo.getCarno()));
                    parameters.put("zrq", requestParam.getRequestDate());
                    parameters.put("ch", reportBaseInfo.getChassisNumber());
                    parameters.put("SUBREPORT_DIR", "/th/report/resources/");
                    
                    jasperReport = (JasperReport) JRLoader.loadObject(JasperTemplateLoader.load(printSet.getCPrintMD()));
                }
            }
            list.add(JasperFillManager.fillReport(jasperReport, parameters, conn));
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
}
