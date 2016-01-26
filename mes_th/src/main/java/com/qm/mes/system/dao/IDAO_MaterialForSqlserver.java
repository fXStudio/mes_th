package com.qm.mes.system.dao;

import com.qm.mes.system.elements.IMaterial;

class IDAO_MaterialForSqlserver extends DAO_Material {
	public String getSQL_innerElement(IMaterial material) {
		return "insert into material(element_id,materialType_id)values("
				+ material.getId() + "," + material.getMaterialTypeId() + ")";
	}
	public String getSQLTemplete_innerCharacter(IMaterial material) {
		return "insert into materialcharacter(id,material_id,MATERIALCHARACTER_ID)values(seq_materialtoidentify,"
				+ material.getId() + ",?)";
	}
	
	public String getSQLTemplete_innerIdentify(IMaterial material) {
		return "insert into materialidentify(id,material_id,MATERIALIDENTIFY_ID)values(seq_materialtoidentify,"
				+ material.getId() + ",?)";
	}
}
