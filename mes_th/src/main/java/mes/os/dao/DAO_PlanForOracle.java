package mes.os.dao;

import java.text.SimpleDateFormat;

import mes.os.bean.Plan;
/**
 * 
 * @author 谢静天 2009-05-15
 *
 */

public class DAO_PlanForOracle implements DAO_Plan{
/** 
* 创建作业计划  谢静天
*/
	 public  String savePlan(Plan plan){
		 String plandate=plan.getPlanDate()==null||plan.getPlanDate().toString().equals("null")?"":(""+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(plan.getPlanDate())+"");
		 String producedate=plan.getProduceDate()==null||plan.getProduceDate().toString().equals("null")?"":(""+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(plan.getProduceDate())+"");
		// System.out.println("11111111111111111111111111111111111"); 
		 String sql=" insert into t_os_plan values(SEQ_os_plan.nextval,"
			 +"to_date('"+plandate+"','yyyy-mm-dd hh24:mi:ss')" + "," 
			 +"to_date('"+producedate+"','yyyy-mm-dd hh24:mi:ss')" + ",'" 
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
			//System.out.println(sql); 
		 return sql;
	 }

	 /** 
	  * 按照生产单元，生产日期，班组和班次查找作业计划 最大的版本信息 谢静天
	  */
	public String getPlanbyProdateProidWorder(String produceDate,int produnitid,String workOrder ){
		
		String sql="select * from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
		+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"'  "  +"))order by int_planOrder asc";
		
		return sql;
		
	}
	  /** 
	   * 通过版本号得到作业计划  谢静天
	   */
	public String getPlanbyversioncord(String versioncode){
		String sql="select * from t_os_plan where str_versionCode='"+versioncode+"' order by int_planOrder desc";
		
		return sql;
	}
	   /** 
	    * 删除指定版本号的作业计划 谢静天
	    */
	public  String deletePlanbyversioncode(String versioncode){
		String sql="delete from t_os_plan where str_versionCode='"+versioncode+"' ";
		return sql;
		
	}
	    /** 
	     * 通过版本int_id  得到版本计划信息 按照计划顺序号升序排列 谢静天
	     */
	public   String getPlanbyversionid(int int_id){
		String sql="select * from t_os_plan where str_versionCode=(select str_versionCode from t_os_planversion where int_id="+int_id+") order by int_planOrder asc";
		
		return sql;
	}
	     /** 
	      * 查询一段时间内的作业计划
	      */
	public  String getplanbybystarttimeandendtimeproduceunitoverendtime(int produnitid,String workOrder ,String overtime,String endtime){
		String sql="select * from t_os_plan  where str_versioncode in(select max(str_versioncode) from( "
		         + " select * from t_os_plan where str_versioncode!='temp' and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and "
		         +"to_date(Dat_produceDate)>=to_date('"+overtime+"','yyyy-MM-DD') and to_date(Dat_produceDate)<=to_date('"+endtime+"','yyyy-MM-DD')"
		         +" ) group by str_workOrder ,dat_producedate ,int_produnitid "
		         +") order by dat_producedate asc";
		
		return sql;
	
	}
	      /** 
	       * 修改指定id值的计划  谢静天
	       */
	public   String updatePlanbyid(Plan plan){
		 String plandate=plan.getPlanDate()==null||plan.getPlanDate().toString().equals("null")?"":(""+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(plan.getPlanDate())+"");
		String sql="update t_os_plan set dat_planDate=to_date('"+plandate+"','yyyy-mm-dd hh24:mi:ss') ," 
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
	       /** 
	        * 得到指定id的计划   谢静天
	        */
	public   String getplanbyid(int id ){
		String sql="select * from t_os_plan where int_id="+id+"";
		return sql;
	}
	        /** 
	        *删除生产单元，生产日期，班组和班次  顺序号的作业计划
	        */ 
	public   String deleteplanbyPlanOrder(String produceDate,int produnitid,String workOrder,int planorder){
		String sql="delete  from t_os_plan where int_planOrder="+planorder+" and str_versioncode='temp' " 
			+" and  to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
	+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' ";
	
	return sql;
	  }
	
	        /** 
	        * 删除计划时调用调整指令顺序方法  谢静天
	        */ 
	 public String updatePlanOrder(String produceDate,int produnitid,String workOrder,int planorder){
			String sql="update t_os_plan  set int_planOrder=int_planOrder-1 where int_planOrder>"+planorder+" and str_versioncode='temp' " 
			+" and to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
	+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"'  ";
	
	return sql;
	 }
	 
	 
	/** (non-Javadoc)
	 * 下移或者上移 计划顺序通过计划的id  和互换的顺序号谢静天
	 * @see mes.os.dao.DAO_Plan#down_or_upPlanOrderbyplanid(int, int)
	 */
	public   String down_or_upPlanOrderbyplanid(int id,int nextorder){
		String sql="update t_os_plan set int_planOrder="+nextorder+" where int_id="+id+"";
		return sql;
	}
	
	
	
	  
	/* (non-Javadoc)
	 * 撤销编辑计划 谢静天
	 * @see mes.os.dao.DAO_Plan#disfrockplan(java.lang.String, int, java.lang.String)
	 */
	public  String disfrockplan(String produceDate,int produnitid,String workOrder){
		 String sql="delete from  t_os_plan   where  str_versioncode='temp'" 
			+" and to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
	+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' ";
	
	return sql;
	}

	  
	/* (non-Javadoc)
	 * 提交编辑计划 谢静天
	 * @see mes.os.dao.DAO_Plan#submitplan(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public  String submitplan(String produceDate,int produnitid,String workOrder,String versioncode){
		 String sql="update   t_os_plan set str_versioncode='"+versioncode+"'  where  str_versioncode='temp' " 
				+" and to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
		+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' ";
		
		return sql;
	}
	  
	 /* (non-Javadoc)
	 * 查询提交的版本号 谢静天
	 * @see mes.os.dao.DAO_Plan#getversioncodewhensubmit(java.lang.String, int, java.lang.String)
	 */
	public String getversioncodewhensubmit(String produceDate,int produnitid,String workOrder){
		String sql=" select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan "
					+" where to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"' "
			+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp')";
		return sql;
	 }

	 
	 /* (non-Javadoc)
	 * 发布作业时只能看到最大的版本并且版本号不为temp 谢静天
	 * @see mes.os.dao.DAO_Plan#getmaxPlanexcepttemp(java.lang.String, int, java.lang.String)
	 */
	public  String getmaxPlanexcepttemp(String produceDate,int produnitid,String workOrder){
		 String sql="select * from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
		+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' and str_versioncode!='temp' "  +"))order by int_planOrder asc";
		
		return sql;
	 }
	 
	/* (non-Javadoc)
	 * 发布作业计划 谢静天
	 * @see mes.os.dao.DAO_Plan#uploadplan(java.lang.String, int, java.lang.String)
	 */
	public  String uploadplan(String produceDate,int produnitid,String workOrder){
		 String sql="update t_os_plan set int_upload=1 where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
		+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp' "  +"))";
		
		return sql;
	}
	 
   /* (non-Javadoc)
 * 取消作业计划发布 谢静天
 * @see mes.os.dao.DAO_Plan#canceluploadplan(java.lang.String, int, java.lang.String)
 */
public  String  canceluploadplan(String produceDate,int produnitid,String workOrder){
    	 String sql="update t_os_plan set int_upload=0 where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan " 
				+"where to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
		+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp' "  +"))";
		
		return sql;
    }
	
 
  /* (non-Javadoc)
 * 查询不同生产日期生产单元班次的最大版本信息 谢静天
 * @see mes.os.dao.DAO_Plan#geteverydaymaxversioncodeplan()
 */
public  String geteverydaymaxversioncodeplan(){
	  String sql="select * from t_os_plan where str_versioncode in(select max(str_versioncode) from t_os_plan where str_versioncode!='temp' group by str_workOrder ,dat_producedate )";
	  
	  return sql;
  }
  
  /* (non-Javadoc)
 * 替换时不核对被替掉内容的作业计划 
 * @see mes.os.dao.DAO_Plan#geteverydaymaxversioncodeplanexceptnow(java.lang.String, int, java.lang.String)
 */
public String geteverydaymaxversioncodeplanexceptnow(String produceDate,int produnitid,String workOrder){
	  String sql="select * from t_os_plan where str_versioncode in(select max(str_versioncode) from t_os_plan  where str_versioncode!='temp'  and  str_versioncode!=("
		  +"select max(str_versioncode) from t_os_plan where str_versioncode=(select str_versioncode  from  t_os_plan where  int_id=(select max(int_id) from t_os_plan where to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"' and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_versioncode!='temp' ))"
		  +") group by str_workOrder ,dat_producedate )";
	 
	  return sql;
	  
	  
  }
  
  /* (non-Javadoc)
 * 查询不同生产日期生产单元班组班次的最大版本信息 中是否有要查找的主物料值 谢静天
 * @see mes.os.dao.DAO_Plan#getmaterielfromplan(java.lang.String)
 */
public  String getmaterielfromeditplan(String produceDate,int produnitid,String workOrder,String materiel){
	  String sql="select * from t_os_plan where str_versioncode='temp'" 
			+" and to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
			+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' and str_producemarker='"+materiel+"' order by int_planOrder asc";
			
	 
	 return sql;
 }
  
  /* (non-Javadoc)
 * 编辑页面查找记录 版本号用temp  谢静天
 * @see mes.os.dao.DAO_Plan#getplancontainstemp(java.lang.String, int, java.lang.String)
 */
public  String getplancontainstemp(String produceDate,int produnitid,String workOrder){
	  String sql="select * from t_os_plan where str_versioncode='temp'" 
	+" and to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
	+" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' order by int_planOrder asc";
	
	return sql;
  }
  
 
 /* (non-Javadoc)
 * 核对是否存在order的计划顺序号 谢静天
 * @see mes.os.dao.DAO_Plan#checkplanorder(java.lang.String, int, java.lang.String, int)
 */
public  String checkplanorder(String produceDate,int produnitid,String workOrder,int order){
	 String sql="select * from  t_os_plan where  str_versioncode='temp' and int_planOrder="+order+" " 
		+" and to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
        +" and int_produnitid="+produnitid+"  and str_workOrder='"+workOrder+"' ";

     return sql;
 }
  

  
 /**通过id查询生产单元班次用于检查工作时刻表删除条件
	 * author : 包金旭
	 */
 public String getAllPlanByProdunitidOrder(int Produnitid,String Order){
	 String sql="select int_id from  t_os_plan where int_produnitid="+Produnitid+"  and str_workOrder='"+Order+"' ";
	 return sql;

	 
 }
 
 /* (non-Javadoc)
 *计划生成指令时 没发布的计划调度员看不见。谢静天
 * @see mes.os.dao.DAO_Plan#getmaxPlanexcepttempupload(java.lang.String, int, java.lang.String)
 */
public String getmaxPlanexcepttempupload(String produceDate,int produnitid,String workOrder){
	 String sql="select * from t_os_plan where str_versioncode=(select max(str_versioncode) from t_os_plan" 
			+" where to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'"
	+" and int_produnitid="+produnitid+" and str_workOrder='"+workOrder+"' and str_versioncode!='temp' and int_upload=1 " +")  order by int_planOrder asc";
    return sql;
	}

/**
 * @author 谢静天
 * 在计划发布时做主物料验证，发布的最大版本的生产单元主物料是否重复。
 *
 */
  public String getplanbymaterielproid_upload_andmaxversion(String materiel,int produnitid){
	  String sql="select * from t_os_plan where str_versioncode in (select max(str_versioncode) from t_os_plan group by str_workOrder ,dat_producedate,int_produnitid ) and int_upload=1 and int_produnitid="+produnitid+" and str_producemarker='"+materiel+"'";
	  return sql;
  }
 }



