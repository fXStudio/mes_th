package com.qm.mes.system.dao;

import com.qm.mes.framework.DataBaseType;

public final class DAOFactoryAdapter {
	public static final IDAO_Material getInstance(DataBaseType dataBaseType) {
		if (dataBaseType == null)
			return null;
		switch (dataBaseType) {
		case SQLSERVER:
			return new IDAO_MaterialForSqlserver();
		case ORACLE:
			return new IDAO_MaterialForOracle();
		}
		return null;
	}

	/**
	 * 根据数据库类型返回接口的对象。<br>
	 * 规则：<br>
	 * 1、目标类与接口在同一个包内<br>
	 * 2、目标类名=接口名+ForSqlserver/ForOracle（根据数据库类型选择使用）
	 * @param dataBaseType 数据库类型，目前有Sqlserver和Oracle
	 * @param interfaceClass 目标接口类型
	 * @return 返回成功生成的对象，若出现异常返回null。
	 */
	@SuppressWarnings("unchecked")
	public static final Object getInstance(DataBaseType dataBaseType,
			Class interfaceClass) {
		//若没有指定所需要的接口类型及数据库类型则直接返回null
		if (dataBaseType == null || interfaceClass == null)
			return null;
		String className = interfaceClass.getName();
		switch (dataBaseType) {
		case SQLSERVER:
			className += "ForSqlserver";
			break;
		case ORACLE:
			className += "ForOracle";
			break;
		}
		try {
			Object object = Class.forName(className).newInstance();
			if (interfaceClass.isInstance(object))
				return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
