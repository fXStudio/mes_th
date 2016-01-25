package mes.system.dao;

import mes.system.elements.IMaterialType;

public class IDAO_MaterialTypeForSqlserver extends DAO_MaterialType {

	public String getSQL_innerElement(IMaterialType materialtype) {
		// Sqlserver数据库支持自增列，所以id列不需要出现在sql语句中。
		return "insert into MaterialType(element_id,parent_id)values("
				+ materialtype.getId() + "," + materialtype.getParentId() + ")";
	}

}
