package th.report.api;

import java.sql.Connection;
import java.util.List;

import th.pz.bean.JConfigure;
import th.report.entities.RequestParam;

public interface IPrintDataSaver {
    public void save(Connection conn, RequestParam requestParam, List<JConfigure> list);
}
