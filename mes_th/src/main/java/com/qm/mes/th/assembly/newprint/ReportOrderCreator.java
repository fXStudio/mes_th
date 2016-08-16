package com.qm.mes.th.assembly.newprint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.qm.mes.th.assembly.IReportBaseInfoBuilder;
import com.qm.mes.th.assembly.IReportDataSetBuilder;
import com.qm.mes.th.assembly.IReportOrder;
import com.qm.mes.th.assembly.IReportOrderCreator;
import com.qm.mes.th.assembly.entities.ReportBaseInfo;
import com.qm.mes.th.assembly.entities.ReportOrder;
import com.qm.mes.th.assembly.entities.RequestParam;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;

/**
 * 单独打印
 * 
 * @author Ajaxfan
 */
class ReportOrderCreator implements IReportOrderCreator {
    /** 配置打印参数查询语句 */
    private static final String QUERY_LITERAL = "SELECT * FROM printSet WHERE iPrintGroupId = ? ORDER BY id";

    /**
     * 报表订单生成工具
     * 
     * @param conn
     * @param requestParam
     * @return
     */
    @Override
    public List<IReportOrder> createReportOrders(Connection conn, RequestParam requestParam) {
        List<IReportOrder> list = new ArrayList<IReportOrder>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(QUERY_LITERAL);
            stmt.setString(1, requestParam.getGroupId());
            rs = stmt.executeQuery();

            while (rs.next()) {// 遍历要打印的组下包含的所有打印项目
                ReportOrder reportOrder = new ReportOrder();
                PrintSet printSet = createPrintSet(rs);// 报表配置信息
                ReportBaseInfo reportBaseInfo = obtainBaseInfo(conn, requestParam, printSet);
                List<JConfigure> dataset = obtainDataSet(conn, printSet, reportBaseInfo, requestParam);
                
                // 设置报表订单参数
                reportOrder.setPrintSet(printSet);
                reportOrder.setReportBaseInfo(reportBaseInfo);
                reportOrder.setRequestParam(requestParam);
                reportOrder.addAll(dataset);

                list.add(reportOrder);
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
     * 获取报表基本信息
     */
    private ReportBaseInfo obtainBaseInfo(Connection conn, RequestParam requestParam, PrintSet printSet) {
        IReportBaseInfoBuilder builder = new BaseInfoBuilder(conn, printSet, requestParam);
        builder.buildDAInfo();
        builder.buildMaxCarNo();
        builder.buildTFassId();
        builder.buildMaxPageNo();
        builder.buildChassisNumber();
        builder.buildVinAndCarTypePair();

        return builder.getReportBaseInfo();
    }

    /**
     * 数据集合
     * 
     * @param conn
     * @param printSet
     * @param reportBaseInfo
     * @return
     */
    public List<JConfigure> obtainDataSet(Connection conn, PrintSet printSet, ReportBaseInfo reportBaseInfo, RequestParam requestParam) {
        IReportDataSetBuilder builder = new DataSetBuilder(conn, printSet, reportBaseInfo, requestParam);
        builder.buildQueryExpression();
        builder.buildBusinessDataSet();

        return builder.getDataSet();
    }

    /**
     * 创建打印对象
     * 
     * @param rs
     * @return
     * @throws Exception
     */
    private PrintSet createPrintSet(ResultSet rs) throws Exception {
        PrintSet printSet = new PrintSet();
        printSet.setId(rs.getInt("id"));
        printSet.setIPrintGroupId(rs.getInt("iprintGroupId"));
        printSet.setCCode(rs.getString("cCode"));
        printSet.setCTFASSName(rs.getString("cTfassName"));
        printSet.setCCarType(rs.getString("cCarType"));
        printSet.setNTFASSCount(rs.getInt("nTFassCount"));
        printSet.setNPerTimeCount(rs.getInt("nPerTimeCount"));
        printSet.setIMESseq(rs.getInt("iMesSeq"));
        printSet.setCFactory(rs.getString("cFactory"));
        printSet.setCPrintMD(rs.getString("cPrintMd"));
        printSet.setCCarTypeDesc(rs.getString("ccartypedesc"));
        printSet.setCLastVin(rs.getString("clastvin"));
        printSet.setCVinRule(rs.getString("cVinRule"));

        return printSet;
    }
}
