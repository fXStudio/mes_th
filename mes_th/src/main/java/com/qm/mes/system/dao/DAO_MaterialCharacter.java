package com.qm.mes.system.dao;

import com.qm.mes.system.elements.IMaterialCharacter;

abstract class DAO_MaterialCharacter implements IDAO_MaterialCharacter {

	public String getSQL_innerElement(IMaterialCharacter e) {
		return "insert into MaterialCharacter(id,element_id)values(SEQ_MATERIALCharacter.nextval,"
				+ e.getId() + ")";
	}

	public String getSQL_queryElement(int id) {
		return "select * from element e inner join MaterialCharacter mc on e.id = mc.element_id where e.id="
				+ id;
	}

	public String getSQL_queryElement(String name) {
		return "select * from element e inner join MaterialCharacter mc on e.id = mc.element_id where e.name='"
				+ name + "'";
	}

	public String getSQL_queryElementAll(String name) {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join MATERIALCHARACTER mt on e.id = mt.element_id "
				+ (name == null || name.equals("") ? "" : "where e.name='"
						+ name + "'") + " order by e.discard,e.id";
	}

	public String getSQL_queryElementAll() {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join MATERIALCHARACTER mt on e.id = mt.element_id order by e.discard,e.id";
	}

	public String getSQL_queryCharactersById(int id) {
		return "select e.id,e.name from materialcharacter mc "
				+ "inner join element e on mc.element_id=e.id "
				+ "inner join materialtocharacter mtoc on mc.element_id = mtoc.MATERIALCHARACTER_ID "
				+ "where mtoc.material_id=" + id;
	}

}
