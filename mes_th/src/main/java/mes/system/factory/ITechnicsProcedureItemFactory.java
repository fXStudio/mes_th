package mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import mes.system.elements.ITechnicsProcedureItem;


public class ITechnicsProcedureItemFactory extends AElementFactory<ITechnicsProcedureItem> {
	ITechnicsProcedureItemFactory(){}
	public ITechnicsProcedureItem createElement() {
		return null;
	}

	public boolean deleteElement(ITechnicsProcedureItem e, Connection con) throws SQLException {
		return false;
	}

	public boolean saveElement(ITechnicsProcedureItem e, Connection con) throws SQLException {
		return false;}

	public boolean updateElement(ITechnicsProcedureItem e, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}
	public ITechnicsProcedureItem queryElement(int id, Connection con) throws SQLException {
		return null;
	}
	public ITechnicsProcedureItem queryElement(String name, Connection con) throws SQLException {
		return null;
	}
}
 
