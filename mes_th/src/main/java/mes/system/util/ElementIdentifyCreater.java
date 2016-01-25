package mes.system.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 这个工具类用于辅助获得新的可用的元素表的ID值
 * @author 张光磊 2008-3-4
 * @deprecated 因为并发情况难以控制，建议用数据库自己的方法处理自增值
 */
public final class ElementIdentifyCreater {
	public static int getNewElementIdentify(Connection con) throws SQLException {
		Statement st = con.createStatement();
		ResultSet set = st
				.executeQuery("select nvl(max(id),0)+1 as new_id from element");
		set.next();
		return set.getInt(1);
	}
}
