package com.qm.mes.framework.dao;

import java.sql.*;

import com.qm.mes.framework.DataBaseType;


public final class DAOFactory_UserManager {
	public static IDAO_UserManager getInstance(DataBaseType dataBaseType) {
		switch (dataBaseType) {
		case SQLSERVER:
			return new DAO_UserManagerForSqlserver();
		case ORACLE:
			return new DAO_UserManagerForOracle();
		}
		return null;
	}

	public static IDAO_UserManager getInstance(Connection con) throws SQLException {
		return getInstance(DataBaseType.getDataBaseType(con));
	}

}