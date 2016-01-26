package com.qm.mes.ra.dao;
import com.qm.mes.ra.bean.*;
/**
 * 
 * @author 谢静天
 *
 */
public class DAO_ProduceUnitForSqlserver extends DAO_ProduceUnitForOracle {
	/** 创建生产单元
	 * 谢静天
	 * param produceUnit;
	 */
	public String saveProduceUnit(ProduceUnit produceUnit){
		String sql="insert into t_ra_produceUnit(str_name,str_code,int_instructstateid,int_planincorporate,int_instcount,int_type,int_delete,int_materielruleid) values('"
			+produceUnit.getStr_name()
			+"','"
			+produceUnit.getStr_code()
			+"',"
			+produceUnit.getInt_instructStateID()
			+","
			+produceUnit.getInt_planIncorporate()
			+","
			+produceUnit.getInt_instCount()
			+","
			+produceUnit.getInt_Type()
			+","
			+produceUnit.getInt_delete()
			+","
			+produceUnit.getInt_materielRuleid()
			+")";
		
		return sql;
	}
}
