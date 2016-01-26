package com.qm.mes.system.dao;

import com.qm.mes.system.elements.IElement;

/**
 * 元素表Element的通用sql语句操作
 * 
 * @author 张光磊 2008-3-6
 */
abstract class DAO_Element implements IDAO_Element {

	public String getSQL_queryElement(String name) {
		return "select * from element where name='" + name + "'";
	}

	public String getSQL_deleteElement(int id) {
		return "update Element set discard=1 where id=" + id;
	}

	public String getSQL_deleteElement(String name) {
		return "update Element set discard=1 where name='" + name + "'";
	}

	public String getSQL_updateElement(IElement element) {
		return getSQL_updateElement(element.getName(),
				element.getDescription(), element.getUpdateUserId());
	}

	public String getSQL_updateElement(String name, String description,
			int updateUserId) {
		return "update Element set description='" + description
				+ "',updateUserId=" + updateUserId + ",version=version+1 where name='" + name
				+ "'";
	}
	
	public String getSQL_queryElementCount(String name){
		return "select count(name) from element where name='" + name + "'";
	}
}
