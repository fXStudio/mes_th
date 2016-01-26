package com.qm.mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.qm.mes.system.elements.ITechnicsProcedure;


public class ITechnicsProcedureFactory extends AElementFactory<ITechnicsProcedure> {
	ITechnicsProcedureFactory(){}
	public ITechnicsProcedure createElement() {
		return null;
	}

	public boolean deleteElement(ITechnicsProcedure e, Connection con) throws SQLException {
		return false;
	}

	public boolean saveElement(ITechnicsProcedure e, Connection con) throws SQLException {
		return false;}

	public boolean updateElement(ITechnicsProcedure e, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}
	public ITechnicsProcedure queryElement(int id, Connection con) throws SQLException {
		return null;
	}
	public ITechnicsProcedure queryElement(String name, Connection con) throws SQLException {
		return null;
	}
}
 
