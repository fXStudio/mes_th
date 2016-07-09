package com.qm.mes.th.assembly.newprint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.qm.mes.th.assembly.IPersistence;
import com.qm.mes.th.assembly.IReportOrder;

import th.pz.bean.JConfigure;

public class AssemblyPersistence implements IPersistence {
    /** 打印日期格式化 */
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 打印数据持久化
     * 
     * @param conn
     * @param orderList
     * @throws SQLException 
     */
    @Override
    public void store(Connection conn, List<IReportOrder> orderList) throws SQLException {
        // 遍历所有的订单信息（订单的内容即我们要保存的数据信息）
        for (IReportOrder order : orderList) {
            savePrintData(conn, order);
            savePrintSetVin(conn, order);
            updatePrintSet(conn, order);
        }
    }

    /**
     * 保存打印信息
     * @throws SQLException 
     */
    private void savePrintData(Connection conn, IReportOrder order) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(getSavePrintDataSql());

            for (JConfigure configure : order.getDatas()) {
                stmt.setInt(1, order.getPrintSet().getId());// 打印组ID
                stmt.setString(2, configure.getChassisNumber());// 底盘号
                stmt.setString(3, df.format(GregorianCalendar.getInstance().getTime()));
                stmt.setInt(4, configure.getJs());// 架子号
                stmt.setInt(5, configure.getIndex());// 元素索引
                stmt.setString(6, configure.getCVinCode());
                stmt.setString(7, configure.getCSEQNo_A());
                stmt.setString(8, configure.getCCarNo());
                stmt.setString(9, configure.getCQADNo());
                stmt.setInt(10, configure.getTfassId());
                stmt.setInt(11, 1);
                stmt.setString(12, order.getRequestParam().getRequestDate());// 查询日期

                stmt.addBatch();
            }
            stmt.executeBatch();
        }  finally {
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
     * 保存打印数据的VIN对照关系
     * 
     * @param conn
     * @param order
     * @throws SQLException 
     */
    private void savePrintSetVin(Connection conn, IReportOrder order) throws SQLException {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            Map<String, String> map = order.getReportBaseInfo().getHmVin();

            for (String key : map.keySet()) {
                stmt.addBatch("DELETE FROM printSetVin WHERE ctype = '" + key + "'");
                stmt.addBatch("INSERT INTO printSetVin (clastvin, ctype) VALUES ('" + map.get(key) + "', '" + key + "')");
            }
            stmt.executeBatch();
        }  finally {
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
     * 修改打印配置表
     * 
     * @param conn
     * @param order
     * @throws SQLException 
     */
    private void updatePrintSet(Connection conn, IReportOrder order) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("UPDATE printSet SET clastvin = ? where id = ?");
            stmt.setString(1, order.getPrintSet().getCLastVin());
            stmt.setInt(2, order.getPrintSet().getId());

            stmt.executeUpdate();
        } finally {
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
     * 打印数据表SQL
     * 
     * @return
     */
    private String getSavePrintDataSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO print_data (iPrintGroupId, cPageNo, dPrintTime, iCarNo,");
        sql.append(" inum, cVinCode, cSEQNo, cKinNo, cTFAss, ITFASSNameId, iBigNo, cRemark)");
        sql.append(" VALUES");
        sql.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        return sql.toString();
    }
}
