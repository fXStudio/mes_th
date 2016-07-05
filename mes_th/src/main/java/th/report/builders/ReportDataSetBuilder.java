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

            for (; list.size() < printSet.getNTFASSCount();) {// 按打印数量组织数据集合
                JConfigure obj = new JConfigure();
                obj.setIndex(list.size() + 1);

                if (rs.next()) {
                    obj.setCQADNo(rs.getString("cQADNo"));// 天合零件号
                    obj.setCSEQNo_A(rs.getString("cSEQNo_A"));// 总装顺序号
                    obj.setCVinCode(rs.getString("cVinCode"));// VIN码
                    obj.setCCarNo(rs.getString("cCarNo"));// kin号
                    obj.setCCarType(rs.getString("ccode"));// 车型
                }
                list.add(obj);
                // 自动匹配集合(判断是否连续，以及自动补齐)
                autoAdjustList(printSet.getNTFASSCount());
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
     * 判断Vin是否连续
     * 
     * @param tfassCount
     * 
     * @param objs
     * @return
     */
    private void autoAdjustList(int tfassCount) {
        JConfigure obj = list.get(list.size() - 1);// 列表中的最后一个元素

        // 要参与比对的数据信息
        String curVin = obj.getCVinCode();
        String cartype = obj.getCCarType();
        String lastVin = reportBaseInfo.getVinByCarType(cartype);

        // 如果没有之前的VIN记录，则这两车型为首次打印，不需要进行VIN比对
        if (lastVin == null || lastVin.trim().length() == 0) {
            reportBaseInfo.putVinMap2CarType(cartype, curVin);

            return;
        }
        // 要比对的VIN流水号信息
        int lastSerial = Integer.valueOf(lastVin.substring(11));
        int curSerial = Integer.valueOf(curVin.substring(11));

        while (list.size() < tfassCount && curSerial - lastSerial > 1) {
            JConfigure config = new JConfigure();
            config.setIndex(list.size() + 1);

            list.add(list.size() - 1, config);
        }
    }

    /*
     * 数据集合
     */
    public List<JConfigure> getDataSet() {
        while (list.size() > printSet.getNTFASSCount()) {
            list.remove(list.size() - 1);
        }
        return list;
    }
}
