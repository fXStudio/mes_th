package com.qm.mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.qm.mes.system.elements.ITechnicsCapability;


public class ITechnicsCapabilityFactory extends AElementFactory<ITechnicsCapability> {
	ITechnicsCapabilityFactory(){}
	public ITechnicsCapability createElement() {
		return null;
	}

	public boolean deleteElement(ITechnicsCapability e, Connection con) throws SQLException {
		return false;
	}

	public boolean saveElement(ITechnicsCapability e, Connection con) throws SQLException {
		return false;}

	public boolean updateElement(ITechnicsCapability e, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}
	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}
	public ITechnicsCapability queryElement(int id, Connection con) throws SQLException {
		return null;
	}
	public ITechnicsCapability queryElement(String name, Connection con) throws SQLException {
		return null;
	}
}
 
