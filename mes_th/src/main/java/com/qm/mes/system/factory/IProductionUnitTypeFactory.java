package com.qm.mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.qm.mes.system.elements.IProductionUnitType;


public class IProductionUnitTypeFactory extends AElementFactory<IProductionUnitType> {
	IProductionUnitTypeFactory(){}
	public IProductionUnitType createElement() {
		return null;
	}

	public boolean deleteElement(IProductionUnitType e, Connection con) throws SQLException {
		return false;
	}

	public boolean saveElement(IProductionUnitType e, Connection con) throws SQLException {return false;}

	public boolean updateElement(IProductionUnitType e, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}
	public IProductionUnitType queryElement(int id, Connection con) throws SQLException {
		return null;
	}
	public IProductionUnitType queryElement(String name, Connection con) throws SQLException {
		return null;
	}
 
}
 
