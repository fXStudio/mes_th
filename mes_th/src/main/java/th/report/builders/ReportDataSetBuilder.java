package th.report.builders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;
import th.report.api.IReportDataSetBuilder;
import th.report.entities.ReportBaseInfo;

/**
 * 数据集构建工具
 * 
 * @author Ajaxfan
 */
public class ReportDataSetBuilder implements IReportDataSetBuilder {
    private Connection conn;
    private PrintSet printSet;
    private ReportBaseInfo reportBaseInfo;
    private List<JConfigure> list;
    private String queryExpression;

    /** 系统日志工具 */
    private Logger logger = Logger.getLogger(ReportDataSetBuilder.class);

    /**
     * 
     * @param conn
     * @param printSet
     * @param reportBaseInfo
     */
    public ReportDataSetBuilder(Connection conn, PrintSet printSet, ReportBaseInfo reportBaseInfo) {
        this.conn = conn;
        this.printSet = printSet;
        this.reportBaseInfo = reportBaseInfo;

        list = new ArrayList<JConfigure>();
    }

    /**
     * 查询表达式
     */
    public void buildQueryExpression() {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT top ");
        sb.append(printSet.getNTFASSCount());
        sb.append(" c.cSEQNo_A, c.cVinCode, c.cCarType, cQADNo, sc.ITFASSNameId, sc.iTFASSNum, c.cCarNo, ks.ccode");
        sb.append(" FROM carData c LEFT JOIN carData_D sc");
        sb.append(" ON c.ccarno = sc.icarid AND itfassnameid = ");
        sb.append(reportBaseInfo.getTfassId());
        sb.append(" LEFT JOIN TFASSName t ON sc.itfassnameid = t.id ");
        sb.append(" LEFT JOIN kinset ks ON substring(c.ccarno,6,1) = ks.csubkin");
        sb.append(" WHERE ((dabegin = '");
        sb.append(reportBaseInfo.getDabegin());
        sb.append("' AND c.cSEQNo_A>'");
        sb.append(reportBaseInfo.getDaseqa());
        sb.append("') or (dabegin> '");
        sb.append(reportBaseInfo.getDabegin());
        sb.append("'))");

        if (printSet.getCCarType() != null && !printSet.getCCarType().equals("")) {
            String carType = "'" + printSet.getCCarType() + "'";
            carType = carType.replace(",", "','");
            sb.append(" AND substring(c.ccarno,6,1) in (" + carType + ")");
        }

        if (printSet.getCFactory() != null && !printSet.getCFactory().equals("")) {
            String factoryNo = "'" + printSet.getCFactory() + "'";
            factoryNo = factoryNo.replace(",", "','");
            sb.append(" AND (subString(c.cSEQNo_A,1,2) in(" + factoryNo + ")) ");
        }
        if (printSet.getCVinRule() != null && !"".equals(printSet.getCVinRule().trim())) {
            String cvinRule = "'" + printSet.getCVinRule() + "'";
            cvinRule = cvinRule.replace(",", "','");
            sb.append(" and (subString(c.cVinCode,7,2) in(" + cvinRule + ")) ");
        }
        sb.append(" ORDER BY c.dabegin, c.cSEQNo_A");

        logger.debug(sb.toString());

        queryExpression = sb.toString();
    }

    /**
     * 构建数据集合
     */
    public void buildBusinessDataSet() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(queryExpression);
            rs = stmt.executeQuery();

            // 填充数据集合
            for (int i = 1; list.size() < printSet.getNTFASSCount(); i++) {
                JConfigure obj = new JConfigure(i);

                if (rs.next()) {
                    obj.setCQADNo(rs.getString("cQADNo"));// 天合零件号
                    obj.setCSEQNo_A(rs.getString("cSEQNo_A"));// 总装顺序号
                    obj.setCVinCode(rs.getString("cVinCode"));// VIN码
                    obj.setCCarNo(rs.getString("cCarNo"));// kin号
                    obj.setCCarType(rs.getString("ccode"));// 车型
                    obj.setChassisNumber(reportBaseInfo.getChassisNumber());
                    obj.setTfassId(rs.getInt("ITFASSNameId"));
                    obj.setJs(reportBaseInfo.getCarno());
                    obj.setPrintSetId(printSet.getIPrintGroupId());
                }
                list.add(obj);
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
    }

    /**
     * 获得最近的Vin码
     * 
     * @param obj
     * @return
     */
    private String getLastVin(String curVin) {
        // 辆份不足的时候，VIN可能为空
        if (curVin == null || curVin.trim().length() == 0) {
            return null;
        }

        // 截取车型信息
        String cartype = curVin.substring(6, 8);
        // 获得上次打印的Vin码
        String lastVin = reportBaseInfo.getVinByCarType(cartype);
        // 保存最新的VIN码
        reportBaseInfo.putVinMap2CarType(cartype, curVin);

        return lastVin;
    }

    /**
     * 判断Vin是否连续
     * 
     * @param list2
     * 
     * @param objs
     * @return
     */
    private List<JConfigure> autoAdjust(List<JConfigure> configures) {
        for (int i = 0; i < configures.size(); i++) {// 检查数据是否连续，重新组织数据集合
            JConfigure configure = configures.get(i);
            String lastVin = getLastVin(configure.getCVinCode());

            if (lastVin != null && lastVin.trim().length() > 11) {// 确认数据
                int lastSerial = Integer.valueOf(lastVin.substring(11));// 上一次打印数据的流水号
                int curSerial = Integer.valueOf(configure.getCVinCode().substring(11));// 本次打印的流水号

                if (curSerial - lastSerial != 1) {// 如果不连续，则需要在数据前面插入空白
                    configures.add(configure.getIndex() - 1, new JConfigure(configure.getIndex()));
                    configure.setIndex(configure.getIndex() + 1);// 如果不连续，则当前元素的索引自动加1
                    configures.remove(configures.size() - 1);
                }
            }
        }
        return configures;
    }

    /*
     * 数据集合
     */
    public List<JConfigure> getDataSet() {
        return autoAdjust(list);
    }
}
