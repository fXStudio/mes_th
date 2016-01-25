package mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import mes.system.elements.IWorkingProcedure;


public class IWorkingProcedureFactory extends
		AElementFactory<IWorkingProcedure> {
	IWorkingProcedureFactory() {
	}

	public IWorkingProcedure createElement() {
		return null;
	}

	public boolean deleteElement(IWorkingProcedure e, Connection con)
			throws SQLException {
		return false;
	}

	public boolean saveElement(IWorkingProcedure e, Connection con) throws SQLException {
		return false;}

	public boolean updateElement(IWorkingProcedure e, Connection con)
			throws SQLException {
		return false;
	}

	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}

	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}

	public IWorkingProcedure queryElement(int id, Connection con) throws SQLException {
		return null;
	}

	public IWorkingProcedure queryElement(String name, Connection con) throws SQLException {
		return null;
	}
}
