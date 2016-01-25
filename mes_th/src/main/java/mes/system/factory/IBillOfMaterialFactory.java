package mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import mes.system.elements.IBillOfMaterial;


public class IBillOfMaterialFactory extends AElementFactory<IBillOfMaterial> {
	/**
	 * 非公有的构造函数，防止包外的随意的实例化
	 */
	IBillOfMaterialFactory() {
	}

	public IBillOfMaterial createElement() {
		return null;
	}

	public boolean deleteElement(IBillOfMaterial e, Connection con)
			throws SQLException {
		return false;
	}

	public boolean saveElement(IBillOfMaterial e, Connection con) throws SQLException {
		return false;
	}

	public boolean updateElement(IBillOfMaterial e, Connection con)
			throws SQLException {
		return false;
	}

	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}

	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}


	public IBillOfMaterial queryElement(String name, Connection con) throws SQLException {
		return null;
	}

	public IBillOfMaterial queryElement(int id, Connection con) throws SQLException {
		return null;
	}

}
