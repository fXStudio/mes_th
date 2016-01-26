package com.qm.mes.system.dao;

import com.qm.mes.system.elements.IMaterial;

public interface IDAO_Material {

	String getSQL_queryElement(String name);

	String getSQL_queryElement(int id);

	String getSQL_innerElement(IMaterial material);

	String getSQL_UpdateElement(IMaterial material);
	
	String getSQL_queryElementAll(String name);
	
	String getSQL_queryCharacters(int id);

	String getSQL_queryIdentifys(int id);

	String getSQLTemplete_innerCharacter(IMaterial material);

	String getSQLTemplete_innerIdentify(IMaterial material);

	String getSQL_deleteCharacters(int id);

	String getSQL_deleteIdentifys(int id);
	
}
