package mes.system.dao;

import mes.system.elements.IMaterialType;

abstract class DAO_MaterialType implements IDAO_MaterialType {

	public String getSQL_queryElement(String name) {
		return "select * from element e inner join materialtype mt on e.id = mt.element_id where e.name='"
				+ name + "'";
	}

	public String getSQL_queryElement(int id) {
		return "select * from element e inner join materialtype mt on e.id = mt.element_id where e.id="
				+ id;
	}

	public String getSQL_UpdateElement(IMaterialType materialtype) {
		return "update materialtype set parent_id="
				+ materialtype.getParentId() + " where element_id="
				+ materialtype.getId();
	}

	public String getSQL_queryElementAll(String name) {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join materialType mt on e.id = mt.element_id "
				+ (name == null || name.equals("") ? "" : "where e.name='"
						+ name + "'") + " order by e.discard,e.id";
	}

	public String getSQL_queryElementAll() {
		//return "select mt.id,e.name,mt.element_id,e.discard from ELEMENT e inner join materialType mt on e.id = mt.element_id where e.discard='0' order by e.name";
		//return "select mt.id,e.name,mt.element_id,e.discard from element e inner join materialType mt on e.id = mt.element_id order by e.discard,e.id";
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join materialType mt on e.id = mt.element_id order by e.name";
	}

	public String getSQL_queryElementOtherType(int id) {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join materialType mt on e.id = mt.element_id "
				+ "where mt.element_id not in" + "(" + id + ") order by e.name";
	}

	/*public String getSQL_queryElementNotOwner(int id, int parent_id) {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join materialType mt on e.id = mt.element_id "
				+ "where mt.element_id not in"
				+ "("
				+ id
				+ ","
				+ parent_id
				+ ") order by e.name";
	}*/
	
	public String getSQL_queryElementCount(String name){
		return "select count(*) from element e inner join materialtype mt on e.id = mt.element_id where e.name='"
		+ name + "'";
	}
}
