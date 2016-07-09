package com.qm.mes.th.assembly.historyprint;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.qm.mes.th.assembly.IAssemblyPrintFacade;
import com.qm.mes.th.assembly.IReportOrder;
import com.qm.mes.th.assembly.entities.RequestParam;
import com.qm.mes.th.assembly.report.JasperPrintCollectionCreator;

import common.Conn_MES;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 新规报表
 * 
 * @author Ajaxfan
 */
public class AssemblyHistoryPrintFacade implements IAssemblyPrintFacade {

    /**
     * 打印报表
     * 
     * @param conn
     * @param requestParam
     */
    @Override
    public List<JasperPrint> assemblyPrint(RequestParam requestParam) {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        Connection conn = null;

        try {
            // 创建数据库连接对象
            conn = new Conn_MES().getConn();
            // 开启事务模式
            conn.setAutoCommit(false);

            // 生成订单对象（订单存储了要生成报表所必须的数据信息）
            List<IReportOrder> orderList = new ReportOrderCreator().createReportOrders(conn, requestParam);

            // 最终的报表对象集合
            if (orderList.size() > 0) {
                list.addAll(new JasperPrintCollectionCreator()
                        .getJasperPrintCollection(orderList.get(0).getPrintSet().getCCode(), orderList));
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    conn = null;
                }
            }
        }
        return list;
    }
}
