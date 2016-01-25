package mes.system.dao;

import mes.system.elements.IMaterial;

abstract class DAO_Material implements IDAO_Material {

	public String getSQL_queryElement(int id) {
		return "select * from element e inner join material  mt on e.id = mt.element_id where e.id="
				+ id;
	}

	public String getSQL_queryElement(String name) {
		return "select * from element e inner join material  mt on e.id = mt.element_id where e.name='"
				+ name + "'";
	}

	public String getSQL_UpdateElement(IMaterial material) {
		return "update material set materialType_id="
				+ material.getMaterialTypeId() + " where element_id="
				+ material.getId();
	}

	public String getSQL_queryElementAll(String name) {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join MATERIAL mt on e.id = mt.element_id "
				+ (name == null || name.equals("") ? "" : "where e.name='"
						+ name + "'") + " order by e.discard,e.id";
	}

	public String getSQL_queryIdentifys(int id) {
		return "select mtoi.* from material m inner join materialtoidentify mtoi on m.element_id = mtoi.material_id where material_id="
				+ id;
	}

	public String getSQL_deleteCharacters(int id) {
		return "delete from materialtocharacter mtoc where material_id=" + id;
	}

	public String getSQL_deleteIdentifys(int id) {
		return "delete from materialtoidentify mtoc where material_id=" + id;
	}

	public String getSQL_queryCharacters(int id) {
		return "select mtoi.* from material m inner join materialtocharacter mtoi on m.element_id = mtoi.material_id where material_id="
				+ id;
	}

}
