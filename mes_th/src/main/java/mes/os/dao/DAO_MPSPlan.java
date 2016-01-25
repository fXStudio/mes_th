package mes.os.dao;
import mes.os.bean.*;

 /**
  * 
  * @author 谢静天 2009-5-13
  *
  */
public interface DAO_MPSPlan {
	 /** 
	  * 创建主计划  谢静天 
	  */ 
   String  saveMPSPlan(MPSPlan mpsPlan);
   /** 
	  *  得到所有的主计划 谢静天 
	  */ 
   String getALLMPSPlan();
   /** 
	  *  替换（删除）计划日期的主计划 谢静天 
	  */  
   String deleteMPSPlanbystartDate(String startDate);
   /** 
	  *   查看某一个主计划通过主计划号 谢静天
	  */ 
   String getMPSPlanbyid(int id);
   /** 
	  *   通过计划日期得到一个集合的主计划 谢静天
	  */ 
   String getMPSPlanbystartDate(String  dat_startDate);
   /** 
	*   更新主计划 通过id值 谢静天
	*/ 
   String updateMPSPlanbyid(MPSPlan mpsplan);
	/** 
	*    删除主计划 通过id值 谢静天
	*/
   String deleteMPSPlanbyid(int int_id);
}
