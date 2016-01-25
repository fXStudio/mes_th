package mes.ra.dao;
import mes.ra.bean.*;

public interface DAO_InstructionHistory {
	/**
	 * 
	 * 创建指令版本内容的sql语句
	 * 谢静天
	*/
	String saveVersionHistory(Instruction instruction,String str_versioncode);
	/**
	 * 
	 * 通过版本号查出指令版本内容的sql语句
	 * 谢静天
	*/
	String getVersionHistory(String versionCode);
	/**
	 * 
	 * 通过版本号删除相应的版本内容的sql语句
	 * 谢静天
	*/
	String delVersionHistory(String versionCode);
	
	/**
	 * 
	 * 通过指令版本序号得到指令版本记录表
	 * 谢静天
	*/
	String getInstructionHistory(int int_id);
	

	/**
	 * 
	 * 通过生产单元和生产日期查询指令版本记录表
	 * 谢静天
	*/
	String getInstructionhistorybyproduceunitdate(int int_produnitid,String Dat_produceDate);
	 /**
	  * 查找最大的版本号
	  * 谢静天
	  */
	
	     String checkcodebyproduceunitanddateWorkorder(int int_produnitid,String str_date,String workOrder);
	     /**
	      * 谢静天
	      * 通过版本号到指令历史表中查找内容的集合
	      */
	     String getInstructionbyversioncode(String versioncode);
}
