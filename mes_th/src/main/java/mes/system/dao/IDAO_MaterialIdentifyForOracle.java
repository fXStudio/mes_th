package mes.system.dao;

import mes.system.elements.IMaterialidentify;

public class IDAO_MaterialIdentifyForOracle extends DAO_MaterialIdentify {

	public String getSQL_innerElement(IMaterialidentify materialidentify) {
		return "insert into materialidentify(id,element_id,codelength)values(seq_materialidentify.nextval,"
				+ materialidentify.getId()
				+ ","
				+ materialidentify.getCodeLength() + ")";
	}

}
