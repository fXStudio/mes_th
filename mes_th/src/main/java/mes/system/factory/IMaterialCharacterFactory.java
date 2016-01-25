package mes.system.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mes.framework.DataBaseType;
import mes.system.dao.DAOFactoryAdapter;
import mes.system.dao.IDAO_MaterialCharacter;
import mes.system.elements.IMaterialCharacter;

public class IMaterialCharacterFactory extends
		AElementFactory<IMaterialCharacter> {
	// TODO 下一步做物料特征，然后是关联方面的操作
	IMaterialCharacterFactory() {
	}

	public IMaterialCharacter createElement() {
		return new DefMaterialCharacter();
	}

	boolean saveElement(IMaterialCharacter e, Connection con)
			throws SQLException {
		IMaterialCharacter type = super.queryElement(e.getName(), con);
		e.setId(type.getId());// 元素存储后可以通过元素的name找到id值
		IDAO_MaterialCharacter dao = (IDAO_MaterialCharacter) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterialCharacter.class);
		doUpdateStatement(dao.getSQL_innerElement(e), con);
		return true;
	}

	boolean updateElement(IMaterialCharacter e, Connection con)
			throws SQLException {
		return true;
	}

	public IMaterialCharacter queryElement(int id, Connection con)
			throws SQLException {
		IDAO_MaterialCharacter dao = (IDAO_MaterialCharacter) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_MaterialCharacter.class);
		return queryElement2(dao.getSQL_queryElement(id), con);
	}

	public IMaterialCharacter queryElement(String name, Connection con)
			throws SQLException {
		IDAO_MaterialCharacter dao = (IDAO_MaterialCharacter) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_MaterialCharacter.class);
		return queryElement2(dao.getSQL_queryElement(name), con);
	}

	private IMaterialCharacter queryElement2(String sql, Connection con)
			throws SQLException {
		Statement st = con.createStatement();
		ResultSet set = st.executeQuery(sql);
		IMaterialCharacter material = null;

		if (set.next()) {
			material = createElement();
			initObjectFromResultSet(material, set);
		}
		st.close();
		return material;
	}
}
