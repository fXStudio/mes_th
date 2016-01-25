package mes.system.dao;

import mes.system.elements.IMaterialidentify;

public class IDAO_MaterialIdentifyForSqlserver extends DAO_MaterialIdentify {

	public String getSQL_innerElement(IMaterialidentify materialidentify) {
		return "insert into materialidentify(element_id,codelength)values("
				+ materialidentify.getId() + ","
				+ materialidentify.getCodeLength() + ")";
	}

}
