package com.qm.mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.qm.mes.system.elements.IFinishedGoods;


public class IFinishedGoodsFactory extends AElementFactory<IFinishedGoods> {

	IFinishedGoodsFactory(){}
	
	public IFinishedGoods createElement() {
		return null;
	}

	public boolean deleteElement(IFinishedGoods e, Connection con) throws SQLException {
		return false;
	}

	public boolean saveElement(IFinishedGoods e, Connection con) throws SQLException {
		return false;
	}

	public boolean updateElement(IFinishedGoods e, Connection con) throws SQLException {
		return false;
	}

	public boolean deleteElement(int id, Connection con) throws SQLException {
		return false;
	}

	public boolean deleteElement(String name, Connection con) throws SQLException {
		return false;
	}

	public IFinishedGoods queryElement(int id, Connection con) throws SQLException {
		return null;
	}

	public IFinishedGoods queryElement(String name, Connection con) throws SQLException {
		return null;
	}
	 
}
 
