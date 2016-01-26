package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.NoPedigreeRecord;
/**
 * @author 谢静天
 * 
 */
public class IDAO_NoPedigreeRecordForOracle implements IDAO_NoPedigreeRecord{
	

	/**
	 * 保存NoPedigreeRecord noPedigreeRecord 实体对象
	 * 
	 * noPedigreeRecord.int_gatherrecord_id
	 * noPedigreeRecord.str_value
	 * noPedigreeRecord.str_gatheritemExtName
	 * @return
	 */
	  public String saveNoPedigreeRecord (NoPedigreeRecord noPedigreeRecord){
		  String sql="insert into t_tg_NoPedigreeRecord(int_id,int_gatherrecord_id,str_value,str_gatheritemExtName)"
		  +"values(seq_tg_NoPedigreeRecord.nextval,"
		  + noPedigreeRecord.getGatherRecordId()
		  + ",'" + noPedigreeRecord.getValue() + "','" +noPedigreeRecord.getGatheritemextname() + "')";
		 
		  return sql;
		  
		
	  }
	  
	  
	  /**
	    * @param int id 
	    * 通过id查询出非谱系记录的对应的所有字段值
	    * @return 
	    */
	   public String getNoPedigreeRecordById(int id){
		   String sql="select *from t_tg_NoPedigreeRecord  where int_id="+ id + " order by int_id";
			  
			  return sql;
		   
	   }
	   
	   

	   /**
	    * 
	    * 查询出非谱系记录的所有字段值
	    * @return 
	    */
	   public  String getAllNoPedigreeRecord (){
		   String sql= "select int_id,int_gatherrecord_id,str_value,str_gatheritemextname "
				+ "from t_tg_NoPedigreeRecord order by int_id";
			  return sql;
		   
	   }
	  
	

	   /**
	    * @param  materielvalue
	    * 通过主物料的值来查询与它有关的非谱系记录。
	    * @return 
	    */
	   public String getNoPedigreeRecordByMaterielValue(String materielvalue){
		   String sql="select str_value ,str_gatheritemextname,np.int_id ,int_gatherrecord_id  from T_TG_GATHERRECORD gc inner join" 
		   		+" (SELECT int_gatherid,max(dat_date) as dat_date FROM T_TG_GATHERRECORD " 
		   		+" gc2 where STR_MATERIELVALUE='"+materielvalue+"' "
		   		+" group by int_gatherid) a on gc.int_gatherid = a.int_gatherid and gc.dat_date = a.dat_date  "
		   	    +" inner join T_TG_NOPEDIGREERECORD np on gc.int_id=np.INT_GATHERRECORD_ID" ;
		   return sql;
	   }
	   
	   
	   
	   /**
		 * 更新NoPedigreeRecord noPedigreeRecord 实体对象
		 * 
		 * noPedigreeRecord.int_id
		 * noPedigreeRecord.str_value
		 * noPedigreeRecord.str_gatheritemExtName
		 * @return
		 */
	   
	   public String updateNoPedigreeRecord(NoPedigreeRecord np ){
          String sql="update T_TG_NOPEDIGREERECORD set  str_value='"+np.getValue()+"' ,str_gatherItemextname='"+np.getGatheritemextname()+"' where int_id="+np.getId();
	     return sql;
	   }
	   
	   
	   
	   
	   
	   /**
		 * 通过过点记录表的id来查询与其有关的非谱系记录
		 * @param id
		 * 
		 * @return
		 */
	   public String getNoPedigreeRecordBygatherid(int id){
		   String sql="select str_value ,str_gatheritemextname,ng.int_id ,int_gatherrecord_id  from  t_tg_gatherrecord gr, t_tg_nopedigreerecord ng "
				+" where gr.int_id=ng.int_gatherrecord_id  and  gr.int_id='"+ id+"'";
		   return sql; 
	   }
}
