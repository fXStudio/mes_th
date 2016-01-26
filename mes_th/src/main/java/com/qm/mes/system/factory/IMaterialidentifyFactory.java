package com.qm.mes.system.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.system.dao.DAOFactoryAdapter;
import com.qm.mes.system.dao.IDAO_MaterialIdentify;
import com.qm.mes.system.elements.IMaterialidentify;

/**
 * 物料标识类
 * 
 * @author 张光磊 2008-3-10
 */
public class IMaterialidentifyFactory extends
		AElementFactory<IMaterialidentify> {

	IMaterialidentifyFactory() {
	}

	public IMaterialidentify createElement() {
		return new DefMaterialidentifiy();
	}

	@Override
	public IMaterialidentify queryElement(int id, Connection con)
			throws SQLException {
		IDAO_MaterialIdentify dao = (IDAO_MaterialIdentify) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterialIdentify.class);
		return queryElement2(dao.getSQL_queryElement(id), con);
	}

	@Override
	public IMaterialidentify queryElement(String name, Connection con)
			throws SQLException {
		IDAO_MaterialIdentify dao = (IDAO_MaterialIdentify) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterialIdentify.class);
		return queryElement2(dao.getSQL_queryElement(name), con);
	}

	private IMaterialidentify queryElement2(String sql, Connection con)
			throws SQLException {
		Statement st = con.createStatement();
		ResultSet set = st.executeQuery(sql);
		IMaterialidentify material = null;

		if (set.next()) {
			material = createElement();
			initObjectFromResultSet(material, set);
			material.setCodeLength(set.getInt("codelength"));
		}
		st.close();
		return material;
	}

	@Override
	boolean saveElement(IMaterialidentify materialidentify,
			Connection con) throws SQLException {
		IMaterialidentify type = super.queryElement(materialidentify.getName(),
				con);
		materialidentify.setId(type.getId());
		IDAO_MaterialIdentify dao = (IDAO_MaterialIdentify) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterialIdentify.class);
		doUpdateStatement(dao.getSQL_innerElement(materialidentify), con);
		return true;
	}

	@Override
	synchronized boolean updateElement(IMaterialidentify midentify,
			Connection con) throws SQLException {
		IDAO_MaterialIdentify dao = (IDAO_MaterialIdentify) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterialIdentify.class);
		doUpdateStatement(dao.getSQL_UpdateElement(midentify), con);
		return true;
	}

}
