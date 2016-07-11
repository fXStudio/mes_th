package th.report.creators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
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
 * 多页打印报表
 * 
 * @author Ajaxfan
 */
public class MultiPageJasperPrintCreator extends BaseImplCreator {

    public List<JasperPrint> createJasperPrints(Connection conn, RequestParam requestParam) {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM printSet WHERE iPrintGroupId = ? ORDER BY id");
            stmt.setString(1, requestParam.getGroupId());
            rs = stmt.executeQuery();

            while (rs.next()) {// 遍历要打印的组下包含的所有打印项目
                PrintSet printSet = createPrintSet(rs);
                ReportBaseInfo reportBaseInfo = reportBaseInfoFacade.obtainBaseInfo(conn, requestParam, printSet);
                List<JConfigure> dataset = reportDataSetFacade.createDataSet(conn, printSet, reportBaseInfo);

                Map<String, Object> parameters = new HashMap<String, Object>();
                // 定义datasource;
                parameters.put("js", String.valueOf(reportBaseInfo.getCarno()));
                parameters.put("zrq", requestParam.getRequestDate());
                parameters.put("ch", reportBaseInfo.getCarno());
                parameters.put("tm", reportBaseInfo.getChassisNumber());
                parameters.put("mc", printSet.getCTFASSName());
                parameters.put("id", printSet.getId());
                parameters.put("SUBREPORT_DIR", "/th/report/resources/");
                // 数据拆分
                parameters.put("dataSource", getSubList(dataset, 1, 3));
                parameters.put("dataSource1", getSubList(dataset, 2, 3));
                parameters.put("dataSource2", getSubList(dataset, 3, 3));

                list.add(createJasperPrints(printSet.getCPrintMD(), parameters));

                // 增加追溯表的打印操作
                if ("1".equals(rs.getString("cCode"))) {
                    list.addAll(createTraceJasperPrints(reportBaseInfo, printSet, requestParam, dataset));
                }
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
     * 追溯表单
     * @param reportBaseInfo
     * @param printSet
     * @param requestParam
     * @param dataset
     * @return
     * @throws Exception
     */
    private List<JasperPrint> createTraceJasperPrints(ReportBaseInfo reportBaseInfo, PrintSet printSet, RequestParam requestParam,
            List<JConfigure> dataset) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("js", String.valueOf(reportBaseInfo.getCarno()));
        parameters.put("zrq", requestParam.getRequestDate());
        parameters.put("mc", printSet.getCTFASSName());
        parameters.put("SUBREPORT_DIR", "/th/report/resources/");

        for (int i = 0, n = 1; i < 2; i++) {
            for (int j = 1; j <= 2; j++) {
                parameters.put("dataSource" + j, getSubList(dataset, n++, 4));
            }
            JasperPrint print = createJasperPrints("new_qnfxpzs.jasper", parameters);
            print.setProperty("repeat", "false");// 追溯单不能重复打印
            list.add(print);
        }
        return list;
    }

    /**
     * 获取子数据集和
     * 
     * @param configures
     * @param percent
     *            第几份
     * @param all
     *            总份数
     * @return
     */
    private List<JConfigure> getSubList(List<JConfigure> configures, int percent, int all) {
        int size = configures.size();
        int every = size / (all == 0 ? 1 : all);

        if (size >= every * percent) {
            return configures.subList(percent * every - every, percent * every);
        }
        return Collections.emptyList();
    }

    /**
     * @param conn
     * @param printSet
     * @param reportBaseInfo
     * @param dataset
     * @param requestParam
     * @return
     */
    private JasperPrint createJasperPrints(String printMd, Map<String, Object> parameters) throws Exception {
        return JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(JasperTemplateLoader.load(printMd)),
                parameters, new JREmptyDataSource());
    }
}
