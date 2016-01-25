package mes.util.tree;

import java.sql.Connection;

import mes.framework.DataBaseType;

public final class DAOFactory_UserManage {
	/**
	 * 数据访问层对象
	 */
	@SuppressWarnings("unused")
	private static IDAO_UserManage dao = null;

	public static IDAO_UserManage getInstance(Connection conn) throws Exception
	{
		DataBaseType dataBaseType=DataBaseType.getDataBaseType(conn);
		switch (dataBaseType) 
		{
		case SQLSERVER:
			return new ADAO_UserManage();
		case ORACLE:
			return new ADAO_UserManage();
		}
		return null;
	}
}
