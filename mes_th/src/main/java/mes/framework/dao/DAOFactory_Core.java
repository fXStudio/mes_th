package mes.framework.dao;

import mes.framework.DataBaseType;

public class DAOFactory_Core {
	public static IDAO_Core getInstance(DataBaseType dataBaseType) 
	{
		switch (dataBaseType) 
		{
			case SQLSERVER:
				return new DAO_CoreForSqlserver();
			case ORACLE:
				return new DAO_CoreForOracle();
		}
		return null;
	}
}
