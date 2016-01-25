package mes.os.dao;

import java.text.SimpleDateFormat;

import mes.os.bean.Plan;
/**
 * 
 * @author 谢静天 2009-05-15
 *
 */


public class DAO_PlanForSqlserver extends DAO_PlanForOracle{

	
	 /* (non-Javadoc)
	 * 创建作业计划  谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#savePlan(mes.os.bean.Plan)
	 */
	public  String savePlan(Plan plan){
		 String plandate=plan.getPlanDate()==null||plan.getPlanDate().toString().equals("null")?"":(""+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(plan.getPlanDate())+"");
		 String producedate=plan.getProduceDate()==null||plan.getProduceDate().toString().equals("null")?"":(""+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(plan.getProduceDate())+"");
			
		 String sql="insert into t_os_plan values("
			 +"convert(datetime,'"+plandate+"',20)" + "," 
			 +"convert(datetime,'"+producedate+"',20)" + ",'" 
			 +plan.getOrderFormId()+"',"
			 +plan.getPlanGroupId()+",'"
			 +plan.getProduceType()+"','"
			 +plan.getProduceName()+"','"
			 +plan.getProduceMarker()+"',"
			 +plan.getProdunitid()+",'"
			 +plan.getWorkTeam()+"','"
			 +plan.getWorkOrder()+"',"
			 +plan.getAmount()+",'"
			 +plan.getVersioncode()+"',"
			 +plan.getUpload()+","
			 +plan.getPlanOrder()+",'" 
			 + plan.getDescription()+ "','" 
			 +   " '" +")";
			
			 
			 
		 return sql;
	 }

	
	/* (non-Javadoc)
	 * 按照生产单元，生产日期，班组和班次查找作业计划 最大的版本信息 谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#getPlanbyProdateProidWorder(java.lang.String, int, java.lang.String)
	 */
	public String getPlanbyProdateProidWorder(String produceDate,int produnitid,String workOrder ){
		
		String sql="select * from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where convert(varchar,dat_produceDate,20)='"+produceDate+"'"
		+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"'  "  +"))order by int_planOrder asc";
		
		return sql;
		
	}
	
	
	
	/* (non-Javadoc)
	 * 查询一段时间内的作业计划
	 * @see mes.os.dao.DAO_PlanForOracle#getplanbybystarttimeandendtimeproduceunitoverendtime(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public  String getplanbybystarttimeandendtimeproduceunitoverendtime(int produnitid,String workOrder ,String overtime,String endtime){
		String sql="select * from t_os_plan where str_versioncode in(select max(str_versioncode) from( "
		         + " select * from t_os_plan where str_versioncode!='temp' and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and "
		         +"convert(datetime,Dat_produceDate)>=to_date('"+overtime+"',20) and to_date(Dat_produceDate)<=convert(datetime,'"+endtime+"',20)"
		         +" ) group by str_workOrder ,dat_producedate  "
		         +") order by int_planOrder asc";
		
		return sql;
	
	}
	 
	/* (non-Javadoc)
	 * 修改指定id值的计划  谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#updatePlanbyid(mes.os.bean.Plan)
	 */
	public   String updatePlanbyid(Plan plan){
		 String plandate=plan.getPlanDate()==null||plan.getPlanDate().toString().equals("null")?"":(""+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(plan.getPlanDate())+"");
		String sql="update t_os_plan set dat_planDate=convert(datetime,'"+plandate+"',20) ," 
		        +"int_planorder="+plan.getPlanOrder()+","
				+"str_description='"+plan.getDescription()+"',"
				+"int_planGroupId="+plan.getPlanGroupId()+","
				+"str_produceType='"+plan.getProduceType()+"',"
				+"str_produceName='"+plan.getProduceName()+"',"
				+"str_produceMarker='"+plan.getProduceMarker()+"',"
				+"str_workTeam='"+plan.getWorkTeam()+"',"
				+"int_count="+plan.getAmount()+" where int_id="+plan.getId()+"";
		return sql;
	}
	
	  
	/* (non-Javadoc)
	 * 删除生产单元，生产日期，班组和班次  顺序号的作业计划
	 * @see mes.os.dao.DAO_PlanForOracle#deleteplanbyPlanOrder(java.lang.String, int, java.lang.String, int)
	 */
	public   String deleteplanbyPlanOrder(String produceDate,int produnitid,String workOrder,int planorder){
		String sql="delete  from t_os_plan where int_planOrder="+planorder+" and str_versioncode='temp' " 
			+" and  convert(varchar,dat_produceDate,20)='"+produceDate+"'"
	+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' ";
	
	return sql;
	  }
	
	   
	 /* (non-Javadoc)
	 * 删除计划时调用调整指令顺序方法  谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#updatePlanOrder(java.lang.String, int, java.lang.String, int)
	 */
	public String updatePlanOrder(String produceDate,int produnitid,String workOrder,int planorder){
			String sql="update t_os_plan  set int_planOrder=int_planOrder-1 where int_planOrder>"+planorder+" and str_versioncode='temp' " 
			+" and convert(varchar,dat_produceDate,20)='"+produceDate+"'"
	+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"'  ";
	
	return sql;
	 }
	 
	 
	/* (non-Javadoc)
	 * 撤销编辑计划 谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#disfrockplan(java.lang.String, int, java.lang.String)
	 */
	public  String disfrockplan(String produceDate,int produnitid,String workOrder){
		 String sql="delete from  t_os_plan   where  str_versioncode='temp'" 
			+" and convert(varchar,dat_produceDate,20)='"+produceDate+"'"
	+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' ";
	
	return sql;
	}

	 
	/* (non-Javadoc)
	 * 提交编辑计划 谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#submitplan(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public  String submitplan(String produceDate,int produnitid,String workOrder,String versioncode){
		 String sql="update   t_os_plan set str_versioncode='"+versioncode+"'  where  str_versioncode='temp' " 
				+" and convert(varchar,dat_produceDate,20)='"+produceDate+"'"
		+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' ";
		
		return sql;
	}
	 
	 /* (non-Javadoc)
	 * 查询提交的版本号 谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#getversioncodewhensubmit(java.lang.String, int, java.lang.String)
	 */
	public String getversioncodewhensubmit(String produceDate,int produnitid,String workOrder){
		String sql=" select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan "
					+" where convert(varchar,dat_produceDate,20)='"+produceDate+"' "
			+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp')";
		return sql;
	 }

	  
	 /* (non-Javadoc)
	 * 发布作业时只能看到最大的版本并且版本号不为temp 谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#getmaxPlanexcepttemp(java.lang.String, int, java.lang.String)
	 */
	public  String getmaxPlanexcepttemp(String produceDate,int produnitid,String workOrder){
		 String sql="select * from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where convert(varchar,dat_produceDate,20)='"+produceDate+"'"
		+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' and str_versioncode!='temp' "  +"))order by int_planOrder asc";
		
		return sql;
	 }
	 
	/* (non-Javadoc)
	 *发布作业计划 谢静天
	 * @see mes.os.dao.DAO_PlanForOracle#uploadplan(java.lang.String, int, java.lang.String)
	 */
	public  String uploadplan(String produceDate,int produnitid,String workOrder){
		 String sql="update t_os_plan set int_upload=1 where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where convert(varchar,dat_produceDate,20)='"+produceDate+"'"
		+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp' "  +"))";
		
		return sql;
	}
	 
  /* (non-Javadoc)
 * 取消作业计划发布 谢静天
 * @see mes.os.dao.DAO_PlanForOracle#canceluploadplan(java.lang.String, int, java.lang.String)
 */
public  String  canceluploadplan(String produceDate,int produnitid,String workOrder){
   	 String sql="update t_os_plan set int_upload=0 where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where convert(varchar,dat_produceDate,20)='"+produceDate+"'"
		+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp' "  +"))";
		
		return sql;
   }
	

  
 /* (non-Javadoc)
 * 替换时不核对被替掉内容的作业计划
 * @see mes.os.dao.DAO_PlanForOracle#geteverydaymaxversioncodeplanexceptnow(java.lang.String, int, java.lang.String)
 */
public String geteverydaymaxversioncodeplanexceptnow(String produceDate,int produnitid,String workOrder){
	  String sql="select * from t_os_plan where str_versioncode in(select max(str_versioncode) from t_os_plan  where str_versioncode!='temp'  and  str_versioncode!=("
		  +"select max(str_versioncode) from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan where convert(varchar,dat_produceDate,20)='"+produceDate+"' and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp' ))"
		  +") group by str_workOrder ,dat_producedate )";
	 
	  return sql;
	  
	  
 }
 
  
 /* (non-Javadoc)
 * 编辑页面查找记录 版本号用temp  谢静天
 * @see mes.os.dao.DAO_PlanForOracle#getplancontainstemp(java.lang.String, int, java.lang.String)
 */
public  String getplancontainstemp(String produceDate,int produnitid,String workOrder){
	  String sql="select * from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
			+"where convert(varchar,dat_produceDate,20)='"+produceDate+"'"
	+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode='temp' "  +")) " 
	+" and convert(varchar,dat_produceDate,20)='"+produceDate+"'"
	+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' order by int_planOrder asc";
	
	return sql;
 }
 
 
/* (non-Javadoc)
 * 核对是否存在order的计划顺序号 谢静天
 * @see mes.os.dao.DAO_PlanForOracle#checkplanorder(java.lang.String, int, java.lang.String, int)
 */
public  String checkplanorder(String produceDate,int produnitid,String workOrder,int order){
	 String sql="select * from  t_os_plan where  str_versioncode='temp' and int_planOrder="+order+" " 
		+" and convert(varchar,dat_produceDate,20)='"+produceDate+"'"
       +" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' ";

    return sql;
}
 

 


/* (non-Javadoc)
 * 计划生成指令时 没发布的计划调度员看不见。谢静天
 * @see mes.os.dao.DAO_PlanForOracle#getmaxPlanexcepttempupload(java.lang.String, int, java.lang.String)
 */
public String getmaxPlanexcepttempupload(String produceDate,int produnitid,String workOrder){
	 String sql="select * from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
			+"where convert(varchar,dat_produceDate,20)='"+produceDate+"'"
	+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' and str_versioncode!='temp' "  +")) and int_upload=1 order by int_planOrder asc";
   return sql;
	}  
}




