package com.qm.mes.th.assembly;

import java.sql.Connection;
import java.util.List;

import com.qm.mes.th.assembly.entities.RequestParam;

/**
 * 报表订单生成工具
 * @author Ajaxfan
 */
public interface IReportOrderCreator {
    public List<IReportOrder> createReportOrders(Connection conn, RequestParam requestParam);
}
