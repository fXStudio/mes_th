package mes.framework;

import java.sql.Connection;
import java.sql.SQLException;

public enum DataBaseType {
	SQLSERVER, ORACLE;
	public static DataBaseType getDataBaseType(Connection con)
			throws SQLException {
		String dbname = con.getMetaData().getDatabaseProductName();
		if (dbname.toUpperCase().equals("MICROSOFT SQL SERVER"))
			return SQLSERVER;
		if (dbname.toUpperCase().equals("ORACLE"))
			return ORACLE;
		return null;
	}
}
