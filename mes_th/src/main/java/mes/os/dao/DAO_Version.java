package mes.os.dao;
import mes.os.bean.*;
/**
 * 
 * @author 谢静天 2009-05-15
 *
 */

public interface DAO_Version {
	/**
	 * 得到所有的版本信息  谢静天
	 */
	String getAllVersions();
	
	/**
	 * 创建版本 谢静天
	 */
	String saveVersion(Version version);

	/**
	 * 得到版本信息通过版本id号 谢静天
	 */
	String getVersionbyid(int int_id);
	
	/**
	 * 删除版本号指定的版本信息  谢静天
	 */
	String deleteVersionbyversioncode(String versioncode);
	
	/**
	 * 删除版本表记录通过版本的int_id 
	 */
	String deleteversionbyid(int int_id);
	   
	/**
	 *判断是否可以删除版本 通过版本的id 谢静天
	 */
	String checkdeleteversionbyid(int id );
	/**
	 * @author 根据版本号改版本备注信息
	 *
	 */
	String upversiondescriptionbyvcode(String versioncode,String description);
	/**
	 *  谢静天 根据版本号得到计划信息
	 */
	public String getPlanbyversioncord(String versioncode);
}
