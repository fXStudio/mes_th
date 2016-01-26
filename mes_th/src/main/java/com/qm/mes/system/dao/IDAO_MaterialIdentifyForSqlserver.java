package com.qm.mes.system.dao;

import com.qm.mes.system.elements.IMaterialidentify;

public class IDAO_MaterialIdentifyForSqlserver extends DAO_MaterialIdentify {

	public String getSQL_innerElement(IMaterialidentify materialidentify) {
		return "insert into materialidentify(element_id,codelength)values("
				+ materialidentify.getId() + ","
				+ materialidentify.getCodeLength() + ")";
	}

}
