package com.qm.mes.th.assembly;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IPersistence {
    public void store(Connection conn, List<IReportOrder> orderList) throws SQLException;
}
