package mes.ra.dao;

import mes.ra.bean.WorkTO;

public class DAO_WorkTOForSqlserver extends  DAO_WorkTOForOracle{
	/** 
	 * 创建班组班次记录表
	 * 包金旭
	 */
	public String saveWorkTO(WorkTO  workto){
		String sql = "insert into t_ra_workto(int_id,int_produnitid,str_workOrder)"
			+ "values(seq_ra_WorkTO.nextval"	
			+ ","+ workto.getProdunitid()+""
			+ ",'"+ workto.getWorkOrder()+ "')";
			
		return sql;
	}
	
	/**通过ID获得班组班次记录表
	 * author : 包金旭
	 */
	public String getWorkTOById(int id){
		String sql="select * from t_ra_workto where int_id="+id;
		return sql;
	}

	/**获得所有班组班次记录表
	 * author : 包金旭
	 */
	public String getAllWorkTO(){
		String sql = "select wo.int_id,wo.int_produnitid,wo.str_workorder,pu.str_name " +
				"from t_ra_workto wo,t_ra_produceUnit pu where pu.int_id=wo.int_produnitid order by wo.int_id desc";
	    return sql;
	}

	/**修改班组班次记录表
	 * author : 包金旭
	 */
	public String updateWorkTO(WorkTO  workto){
		String sql=" update t_ra_workto set int_produnitid="+workto.getProdunitid()+"," 
		  
		   +"str_workOrder='"+workto.getWorkOrder()+"' where int_id="+workto.getId()+" ";
		return sql;
	
	}
	
	/**通过ID删除班组班次记录表
	 * author : 包金旭
	 */
	public String deleteWorkTOById(int id){
		String sql="delete from t_ra_workto where int_id=" + id;
		return sql;
	}
	
	/**获得班组班次记录表中生产单元号
	 * author : 包金旭
	 */
	public String getprodunitid(){
		String sql="select int_produnitid from t_ra_workto";
		return sql;

	}
	

	
	/**获得班组班次记录表中班次
	 * author : 包金旭
	 */
	public String getworkOrder(){
		String sql="select str_workOrder from t_ra_workto";
		return sql;
	
	}
	
	/**除去本ID查询获得班组班次记录表中生产单元号用于更改判断
	 * author : 包金旭
	 */
	public String getprodunitidById(int id){
		String sql="select int_produnitid from t_ra_workto where not int_id="+id;
		return sql;

	}
	
	
	
	/**除去本ID查询获得班组班次记录表中班次用于更改判断
	 * author : 包金旭
	 */
	public String getworkOrderById(int id){
		String sql="select str_workOrder from t_ra_workto where not int_id="+id;
		return sql;
	
	}
	/**通过生产单元的id来获取班组班次信息 
	 * author :谢静天
	 */
	public String getworkOrderByprodunitid(int id){
		String sql="select Str_workOrder from t_ra_workto where int_produnitid="+id+" ";
		return sql;
	}
	
	
}








