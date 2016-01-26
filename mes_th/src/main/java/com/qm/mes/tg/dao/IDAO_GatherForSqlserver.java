package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.Gather;

public class IDAO_GatherForSqlserver extends IDAO_GatherForOracle {
	public String saveGather(Gather gather) {
		String sql = "insert into t_tg_Gather(str_name,str_desc,int_produnitid,int_materielruleid,startgo,compel) "
				+ "values('"
				+ gather.getName()
				+ "','"
				+ gather.getDesc()
				+ "',"
				+ gather.getProdunitId()
				+ ","
				+ gather.getMaterielruleId() 
				+","
				+ gather.getStartgo()
		        +","
		        +gather.getCompel()
				+ ")";
		
		return sql;
	}
	
//--------------------------------------------------东阳项目添加---------------------------------------------------------------
	
	public String saveGather_Q(int g,int q,int order){
		 String sql = "insert into T_QM_R_GATHER_QUALITYSTATES(INT_GATHER_ID,INT_QUALITYSTATES_ID,int_order) "
				+ "values("
				+g+","+q+","+order+")";	   
		return sql;
	}

}
