package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.GatherRecord;

public class IDAO_RecordForSqlserver extends IDAO_RecordForOracle {
	
	public String getGatherRecordByGatherId(int grId) {
		String sql = "select a.int_id,b.str_name,a.str_materielvalue,a.INT_USERID,convert(varchar,a.DAT_DATE,20) as strDate,a.STR_MATERIELNAME"
			   
				+ " from t_tg_gatherrecord a,t_tg_gather b where a.int_gatherId=b.int_id and INT_GATHERID="
				+ grId + " order by a.int_id";
		return sql;
	}
	
	public String saveGatherRecord(GatherRecord gr) {
		String sql = "insert into t_tg_GatherRecord(int_gatherid,int_userid,str_materielvalue,str_materielname,dat_date)"
				+ " values ( "
				+ gr.getGatherId()
				+ ","
				+ gr.getUserId()
				+ ",'"
				+ gr.getMaterielValue()
				+ "','"
				+ gr.getMaterielName() + "',getdate() )";
		return sql;
	}
	public String savePedigreeRecord(int grecordId, String materielValue,
			String materielName) {
		String sql = "insert into t_tg_PedigreeRecord(int_gatherrecordid,str_materielvalue,str_materielname)"
				+ " values ("
				+ grecordId
				+ ",'"
				+ materielValue + "','" + materielName + "')";
		
		return sql;
	}
	public String getGatherRecordById(int id) {
		String sql = "select a.STR_MATERIELVALUE,a.STR_MATERIELNAME,a.int_gatherid,a.int_userid,"
				+ "convert(varchar,a.DAT_DATE,20) as strDate,m.STR_VALIDATECLASS,m.str_desc from T_TG_GATHERRECORD a inner join"
				+ " t_tg_MaterielRule m on a.STR_MATERIELNAME = m.STR_NAME"
				+ " where a.int_id =" + id + "";
		return sql;
	}
	 public  String savePEDIGREEHISTORY(int origid,String origvalue,String cause,String userid){
	        String sql="insert into T_TG_PEDIGREEHISTORY(INT_PEDIGREERECORDID,STR_ORIGINAL,DAT_DATE,STR_CAUSE,INT_USERID)"
					+"values("+origid+",'"+origvalue+"',getdate() ,'"+cause+"',"+Integer.parseInt(userid)+")";
	    	 return sql;
	   }
}
