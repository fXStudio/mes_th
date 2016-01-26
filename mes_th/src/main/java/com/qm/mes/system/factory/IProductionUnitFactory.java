package com.qm.mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.qm.mes.system.elements.IProductionUnit;


public class IProductionUnitFactory extends AElementFactory<IProductionUnit> {
	IProductionUnitFactory(){}
	public IProductionUnit createElement() {
		return null;
	}

	public boolean deleteElement(IProductionUnit e, Connection con) throws SQLException {
		return false;
	}

	public boolean saveElement(IProductionUnit e, Connection con) throws SQLException {
		return false;}

	public boolean updateElement(IProductionUnit e, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}
	public IProductionUnit queryElement(int id, Connection con) throws SQLException {
		return null;
	}
	public IProductionUnit queryElement(String name, Connection con) throws SQLException {
		return null;
	}
}
 
