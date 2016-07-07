package th.report.persistens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import th.pz.bean.JConfigure;
import th.report.api.IPrintDataSaver;
import th.report.entities.RequestParam;

public class PrintDataSaver implements IPrintDataSaver {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void save(Connection conn, RequestParam requestParam, List<JConfigure> list) {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(createInsertSql());

            for (JConfigure configure : list) {
                stmt.setString(1, requestParam.getGroupId());// 打印组ID
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
                stmt.setString(12, requestParam.getRequestDate());// 查询日期

                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
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

    private String createInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO print_data (iPrintGroupId, cPageNo, dPrintTime, iCarNo,");
        sql.append(" inum, cVinCode, cSEQNo, cKinNo, cTFAss, ITFASSNameId, iBigNo, cRemark)");
        sql.append(" VALUES");
        sql.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        return sql.toString();
    }
}
