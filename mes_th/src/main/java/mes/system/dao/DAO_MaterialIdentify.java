package mes.system.dao;

import mes.system.elements.IMaterialidentify;

public abstract class DAO_MaterialIdentify implements IDAO_MaterialIdentify {

	public String getSQL_UpdateElement(IMaterialidentify materialidentify) {
		return "update Materialidentify set codelength="
				+ materialidentify.getCodeLength() + " where element_id="
				+ materialidentify.getId();
	}

	public String getSQL_queryElement(int id) {
		return "select * from element e inner join Materialidentify mt on e.id = mt.element_id where e.id="
				+ id;
	}

	public String getSQL_queryElement(String name) {
		return "select * from element e inner join Materialidentify mt on e.id = mt.element_id where e.name='"
				+ name + "'";
	}

	public String getSQL_queryElementAll(String name) {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join Materialidentify mt on e.id = mt.element_id "
				+ (name == null || name.equals("") ? "" : "where e.name='"
						+ name + "'") + " order by e.discard,e.id";
	}

	public String getSQL_queryElementAll() {
		return "select mt.id,e.name,mt.element_id,e.discard from element e inner join Materialidentify mt on e.id = mt.element_id order by e.discard,e.id";
	}

	public String getSQL_queryIdentifyById(int id) {
		return "select e.id,e.name from materialidentify mc "
				+ "inner join element e on mc.element_id=e.id "
				+ "inner join MATERIALTOIDENTIFY mtoi on mc.element_id = mtoi.MATERIALIDENTIFY_ID "
				+ "where mtoi.material_id=" + id;
	}
}
