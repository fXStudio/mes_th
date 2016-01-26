package com.qm.mes.ra.dao;
import com.qm.mes.ra.bean.*;
/**
 * author : 谢静天
 */
public interface DAO_InstructionVersion {
	  
	/**创建指令版本
	 * author : 谢静天
	 */
	String saveVersion(Version version);
	/**通过版本号得到版本
	 * author : 谢静天
	 */
	
	String getVersion(String versionCode);

	/**删除指令版本
	 * author : 谢静天
	 */
	String delVersionById(int id);
	/**查询规定生产日期的指令版本集合
	 * author : 谢静天
	 */
	String getVersionsByDate(String str_date);
	/**
	 * 通过生产单元查询指令版本集合
	 * author : 谢静天
	 */
	String getVersionsByProduceUnit(String produceUnit);
	/**
	 * 通过生产单元及生产日期查出相应的版本集合
	 * author : 谢静天
	 */
	
	String getVersionsByDateAndUnit(String date,String unit);
	
	/**
	 * 查出所有版本集合
	 * author : 谢静天
	 */
	
	String getAllVersions();
	/**
	 * 查出所有版本个数
	 * author : 谢静天
	 */
	String getVersionsCount();
	/**
	 * 通过版本号查询
	 * author : 谢静天
	 */
	String getVersionByid(int int_id);
	/**
	 * 谢静天
	 * 通过模糊查询版本号返回相应的结果
	 */
   String getVersionbylike(String versioncode);
   /**
	 * @author 根据版本号改版本备注信息
	 *
	 */
	String upversiondescriptionbyvcode(String versioncode,String description);
	
}
