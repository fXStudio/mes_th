package com.qm.mes.os.dao;
import com.qm.mes.os.bean.WorkSchedle;

public class DAO_WorkSchedleForOracle implements DAO_WorkSchedle{
	
	/** 创建工作时刻表
	 * 包金旭
	 */
	public String saveWorkSchedle(WorkSchedle  workSchedle){
		String sql = "insert into t_os_workSchedle(int_id,int_produnitid,str_workOrder,str_workSchedle,str_advanceTime)"
			+ "values(seq_os_WorkSchedle.nextval"	
			+ ","+ workSchedle.getProdunitid()+""
			+ ",'"+ workSchedle.getWorkOrder()+ "'"
			+ ",'"+ workSchedle.getWorkSchedle()+ "'"
			+ ",'"+ workSchedle.getAdvanceTime()+"')";
			
		return sql;
	}
	
	/**获得所有工作时刻表
	 * author : 包金旭
	 */
	public String getAllWorkSchedle(){
		
		String sql = "select wo.int_id,wo.int_produnitid,wo.str_workorder,wo.str_workschedle,wo.str_advancetime,pu.str_name" +
				" from t_os_workschedle wo,t_ra_produceUnit pu where pu.int_id=wo.int_produnitid order by wo.int_id desc";
		
	    return sql;
	}
	
	/**通过ID删除工作时刻表
	 * author : 包金旭
	 */
	public String deleteWorkSchedleById(int id){
		String sql="delete from T_OS_WORKSCHEDLE where int_id=" + id;
		return sql;
	}
	
	/**通过ID获得工作时刻表
	 * author : 包金旭
	 */
	public String getWorkSchedleById(int id){
		String sql="select * from T_OS_WORKSCHEDLE where int_id="+id;
		return sql;
	}
	
	/**修改工作时刻表
	 * author : 包金旭
	 */
	public String updateWorkSchedle(WorkSchedle  workSchedle){
		String sql=" update T_OS_WORKSCHEDLE set int_produnitid="+workSchedle.getProdunitid()+"," 
		  
		   +"str_workOrder='"+workSchedle.getWorkOrder()+"',"
		   +"str_workSchedle='"+workSchedle.getWorkSchedle()+"',"
		   	+"str_advanceTime='"+workSchedle.getAdvanceTime()+"' where int_id="+workSchedle.getId()+" ";
		 
		   return sql;
	
	}
	
	
	/**获得工作时刻表中生产单元号
	 * author : 包金旭
	 */
	public String getprodunitid(){
		String sql="select int_produnitid from T_OS_WORKSCHEDLE";
		return sql;

	}
	
	/**获得工作时刻表中班次
	 * author : 包金旭
	 */
	public String getworkOrder(){
		String sql="select str_workOrder from T_OS_WORKSCHEDLE";
		return sql;
	
	}
	
	/**除去本ID查询获得工作时刻表中生产单元号用于更改判断
	 * author : 包金旭
	 */
	public String getprodunitidById(int id){
		String sql="select int_produnitid from T_OS_WORKSCHEDLE where not int_id="+id;
		return sql;

	}
	
	/**除去本ID查询获得工作时刻表中班次用于更改判断
	 * author : 包金旭
	 */
	public String getworkOrderById(int id){
		String sql="select str_workOrder from T_OS_WORKSCHEDLE where not int_id="+id;
		return sql;
	
	}
	
	/**查询所有序号，提前期
	 * author : 包金旭
	 */
	public String getAllAdvanceTime(){
		String sql="select int_id,str_advanceTime from T_OS_WORKSCHEDLE";
		return sql;
	
	}
	
	/**通过ID查询生产单元班次用于判断删除条件
	 * author : 包金旭
	 */
	public String getProdunitidOrderById(int id){
		String sql="select int_produnitid,Str_workOrder from T_OS_WORKSCHEDLE where int_id="+id;
		return sql;
	}
	
	/**通过生产单元班次查询工作时刻表用于删除班次做判断
	 * author : 包金旭
	 */
	public String getSchedleByProdunitidOrder(int Produnitid,String Order){
		String sql="select * from T_OS_WORKSCHEDLE where int_produnitid="+Produnitid+" and str_workOrder='"+Order+"'";
		return sql;
	}
	
	/**通过生产单元的id来获取班次信息 
	 * author : 谢静天
	 */
	public String getworkOrderByprodunitid(int id){
		String sql="select Str_workOrder  from t_os_workschedle where int_produnitid="+id+" ";
		return sql;
	}
	 /**通过生产单元班次来查询开工时间和提前期 
		 * author :  谢静天
		 */
	public String getworkschedleadtime(int produnitid,String workorder){
		String sql="select * from t_os_workSchedle where int_produnitid="+produnitid+" "
		+"  and str_workOrder='"+workorder+"'";
		
		return sql;
	}
	/**通过生产单元查询开工时间
	 * author : 包金旭
	 */
	public String getWorkSchedelByProuunitid(int str_produceunit){
		String sql="select str_workschedle from t_os_workSchedle where int_produnitid="+str_produceunit;
		return sql;
		
	}
	
	/**除本ID通过生产单元查询开工时间
	 * author : 包金旭
	 */
	public String getWorkSchedelByProuunitidAndID(int str_produceunit,int int_id){
		String sql="select str_workschedle from t_os_workSchedle where int_produnitid="+str_produceunit+" and not int_id="+int_id;
		return sql;
		
	}
	
	
	
	
}
