package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.NoPedigreeRecord;

public class IDAO_NoPedigreeRecordForSqlserver extends IDAO_NoPedigreeRecordForOracle{
	public String saveNoPedigreeRecord (NoPedigreeRecord noPedigreeRecord){
		  String sql="insert into t_tg_NoPedigreeRecord(int_gatherrecord_id,str_value,str_gatheritemExtName)"
		  +"values("
		  + noPedigreeRecord.getGatherRecordId()
		  + ",'" + noPedigreeRecord.getValue() + "','" +noPedigreeRecord.getGatheritemextname() + "')";
		 
		  return sql;
		  
		
	  }
}
