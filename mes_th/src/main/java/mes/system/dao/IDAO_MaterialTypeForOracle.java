package mes.system.dao;

import mes.system.elements.IMaterialType;

public class IDAO_MaterialTypeForOracle extends DAO_MaterialType {

	public String getSQL_innerElement(IMaterialType materialtype) {
		// 物料类型需要一个自增序列
		return "insert into MaterialType(id,element_id,parent_id)values(seq_materialtype.nextval,"
				+ materialtype.getId() + "," + materialtype.getParentId() + ")";
	}

}
