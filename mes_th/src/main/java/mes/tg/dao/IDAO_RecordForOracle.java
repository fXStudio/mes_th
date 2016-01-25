package mes.tg.dao;

import mes.tg.bean.GatherRecord;

public class IDAO_RecordForOracle implements IDAO_Record {
	public String getGatherRecordId(GatherRecord gr) {
		String sql = "select int_id  from t_tg_GatherRecord where int_gatherid ="
				+ gr.getGatherId()
				+ " and str_materielvalue = '"
				+ gr.getMaterielValue() + "' order by int_id desc";
	
		return sql;
	}

	public String getGatherRecordByGatherId(int grId) {
		String sql = "select a.int_id,b.str_name,a.str_materielvalue,a.INT_USERID,to_char(a.DAT_DATE,'yyyy-mm-dd hh24:mi:ss') as strDate,a.STR_MATERIELNAME"
			   
				+ " from t_tg_gatherrecord a,t_tg_gather b where a.int_gatherId=b.int_id and INT_GATHERID="
				+ grId + " order by a.int_id";
		return sql;
	}

	public String saveGatherRecord(GatherRecord gr) {
		String sql = "insert into t_tg_GatherRecord(int_id,int_gatherid,int_userid,str_materielvalue,str_materielname,dat_date)"
				+ " values ( seq_tg_GatherRecord.nextval,"
				+ gr.getGatherId()
				+ ","
				+ gr.getUserId()
				+ ",'"
				+ gr.getMaterielValue()
				+ "','"
				+ gr.getMaterielName() + "',sysdate)";
		return sql;
	}
	

	public String savePedigreeRecord(int grecordId, String materielValue,
			String materielName) {
		String sql = "insert into t_tg_PedigreeRecord(int_id,int_gatherrecordid,str_materielvalue,str_materielname)"
				+ " values (seq_tg_PedigreeRecord.nextval,"
				+ grecordId
				+ ",'"
				+ materielValue + "','" + materielName + "')";
		
		return sql;
	}

	public String getGatherRecordById(int id) {
		String sql = "select a.STR_MATERIELVALUE,a.STR_MATERIELNAME,a.int_gatherid,a.int_userid,"
				+ "to_char(a.DAT_DATE,'yyyy-mm-dd hh24:mi:ss') as strDate,m.STR_VALIDATECLASS,m.str_desc from T_TG_GATHERRECORD a inner join"
				+ " t_tg_MaterielRule m on a.STR_MATERIELNAME = m.STR_NAME"
				+ " where a.int_id =" + id + "";
		return sql;
	}

	public String getPedigreeRecordBygrid(int gatherrecordid) {
		String sql = "select p.INT_ID,p.STR_MATERIELVALUE,p.STR_MATERIELNAME,m.STR_VALIDATECLASS,m.str_desc from T_TG_PEDIGREERECORD p inner join"
				+ " t_tg_MaterielRule m on p.STR_MATERIELNAME = m.STR_NAME"
				+ " where p.int_gatherrecordid = " + gatherrecordid + "";
		return sql;
	}
	//更新主物料值
   public String upDateGatherRecord(int id,String mavalue){
	   
	   String sql="update T_TG_GATHERRECORD set STR_MATERIELVALUE='"+mavalue+"' where int_id ="+id;
	   return sql;
   }
   
   public String upDatePedigreeRecord (int id,String str_materiervalue){
	   String sql="update T_TG_PEDIGREERECORD set STR_MATERIELVALUE='"+str_materiervalue+"' where int_id = "+id;
	   return sql;
   }
    public  String savePEDIGREEHISTORY(int origid,String origvalue,String cause,String userid){
        String sql="insert into T_TG_PEDIGREEHISTORY(INT_ID,INT_PEDIGREERECORDID,STR_ORIGINAL,DAT_DATE,STR_CAUSE,INT_USERID)"
				+"values(seq_tg_PEDIGREEHISTORY.nextval,"+origid+",'"+origvalue+"',sysdate,'"+cause+"',"+Integer.parseInt(userid)+")";
    	 return sql;
   }
   
    public String countBygatherId(int id){
    	String sql="select count(*) from t_tg_gatherrecord  where INT_GATHERID="+id;
    	return sql;
    }
    
    /**
     * 查询物料标识规则名在谱系表中的数量
     * 
     * @author YuanPeng
     * @param MaterielRuleName 物料标识规则名
     * @return 数量
     */
    public String countByMaterielRuleName(String MaterielRuleName){
    	String sql = "select count(*) from T_TG_PEDIGREERECORD where str_materielName = '" + MaterielRuleName + "'";
    	return sql;
    }
    
    /**
     * 查询物料标识规则名在过点记录表中的数量
     * 
     * @author YuanPeng
     * @param MaterielRuleName 物料标识规则名
     * @return 数量
     */
    public String countGatherReByMaterielRuleName(String MaterielRuleName){
    	String sql = "select count(*) from t_tg_gatherrecord where str_materielName = '" + MaterielRuleName + "'";
    	System.out.println(sql);
    	return sql;
    }
    
    /**
     * 	通过ID查询谱系对象
     * @author YuanPeng
     * @param id
     * @return	谱系对象
     */
    public String getPedigreeRecordById(int id){
    	String sql = "select * from T_TG_PEDIGREERECORD where int_id = "+id;
    	return sql;
    }
  
}
