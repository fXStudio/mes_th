package com.qm.mes.system.dao;

import com.qm.mes.system.elements.IMaterial;

class IDAO_MaterialForOracle extends DAO_Material {

	public String getSQL_innerElement(IMaterial material) {
		return "insert into material(id,element_id,materialType_id)values(seq_material.nextval,"
				+ material.getId() + "," + material.getMaterialTypeId() + ")";
	}
	public String getSQLTemplete_innerCharacter(IMaterial material) {
		return "insert into materialtocharacter(id,material_id,MATERIALCHARACTER_ID)values(seq_materialtocharacter.nextval,"
				+ material.getId() + ",?)";
	}
	
	public String getSQLTemplete_innerIdentify(IMaterial material) {
		return "insert into materialtoidentify(id,material_id,MATERIALIDENTIFY_ID)values(seq_materialtoidentify.nextval,"
				+ material.getId() + ",?)";
	}

}
