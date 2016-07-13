package com.qm.mes.th.assembly.repeatprint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.qm.mes.th.assembly.IReportDataSetBuilder;
import com.qm.mes.th.assembly.entities.ReportBaseInfo;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;

/**
 * 数据集构建工具
 * 
 * @author Ajaxfan
 */
class DataSetBuilder implements IReportDataSetBuilder {
    private Connection conn;
    private PrintSet printSet;
    private ReportBaseInfo reportBaseInfo;
    private List<JConfigure> list;
    private String queryExpression;

    /**
     * 
     * @param conn
     * @param printSet
     * @param reportBaseInfo
     */
    public DataSetBuilder(Connection conn, PrintSet printSet, ReportBaseInfo reportBaseInfo) {
        this.conn = conn;
        this.printSet = printSet;
        this.reportBaseInfo = reportBaseInfo;

        list = new ArrayList<JConfigure>();
    }

    /**
     * 查询表达式
     */
    public void buildQueryExpression() {
        queryExpression = "SELECT * FROM print_data WHERE iCarNo = ? AND cRemark = ? AND iPrintGroupId = ? ORDER BY inum";
    }

    /**
     * 构建数据集合
     */
    public void buildBusinessDataSet() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(queryExpression);
            stmt.setInt(1, reportBaseInfo.getCarno());
            stmt.setString(2, reportBaseInfo.getDabegin());
            stmt.setInt(3, printSet.getId());

            rs = stmt.executeQuery();

            // 填充数据集合

            while (rs.next()) {
                JConfigure obj = new JConfigure();

                obj.setIndex(rs.getInt("inum"));// 索引
                obj.setCQADNo(rs.getString("cTFAss"));// 天合零件号
                obj.setCSEQNo_A(rs.getString("cSEQNo"));// 总装顺序号
                obj.setCVinCode(rs.getString("cVinCode"));// VIN码
                obj.setCCarNo(rs.getString("cKinNo"));// kin号
                obj.setCCarType(printSet.getCCode());// 车型
                obj.setChassisNumber(rs.getString("cPageNo"));
                obj.setTfassId(rs.getInt("ITFASSNameId"));
                obj.setJs(rs.getInt("iCarNo"));
                obj.setPrintSetId(printSet.getId());

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

    /*
     * 数据集合
     */
    public List<JConfigure> getDataSet() {
        return list;
    }
}
