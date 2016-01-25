package mes.os.dao;

import mes.os.bean.Version;

public class DAO_VersionForOracle implements DAO_Version {

	/* (non-Javadoc)
	 * 得到所有的版本信息  谢静天
	 * @see mes.os.dao.DAO_Version#getAllVersions()
	 */
	public String getAllVersions(){
		String sql=" select int_id,to_char(dat_versionDatime,'yyyy-MM-dd')as dat_versionDatime,str_user,str_versioncode,str_description from t_os_PlanVersion order by int_id desc";
		
		return sql;
	}
	
	/* (non-Javadoc)
	 * 创建版本 谢静天
	 * @see mes.os.dao.DAO_Version#saveVersion(mes.os.bean.Version)
	 */
	public String saveVersion(Version version){
		  String description=version.getDescription()==null?" ":version.getDescription();
		String sql="insert into t_os_PlanVersion values(SEQ_os_PlanVersion.nextval,"
			+"sysdate,'"
			+version.getUser()+"','"
			+version.getVersionCode()+"','" 
			+description+ "')";
		
		return sql;
	}
	
   /* (non-Javadoc)
    * 得到版本信息通过版本id号
    * @see mes.os.dao.DAO_Version#getVersionbyid(int)
    */
	public String getVersionbyid(int int_id){
	   String sql="select * from t_os_PlanVersion where int_id="+int_id+" ";
	   
	   return sql;
    }
   
   /* (non-Javadoc)
   * 删除版本号指定的版本信息  谢静天
   * @see mes.os.dao.DAO_Version#deleteVersionbyversioncode(java.lang.String)
   */
	public	String deleteVersionbyversioncode(String versioncode){
		String sql=" delete from t_os_PlanVersion where str_versionCode='"+versioncode+"'";
	 
		return sql;
	}
 
   /* (non-Javadoc)
   * 删除版本表记录通过版本的int_id  谢静天
   * @see mes.os.dao.DAO_Version#deleteversionbyid(int)
   */
	public	String deleteversionbyid(int int_id){
		String sql="delete from t_os_planVersion where int_id="+int_id+"";
		return sql;
	}
 
    /* (non-Javadoc)
   * 判断是否可以删除版本 通过版本的id 谢静天
   * @see mes.os.dao.DAO_Version#checkdeleteversionbyid(int)
   */
	public 	String checkdeleteversionbyid(int id ){
		String sql="select op.* from t_os_plan  op, t_os_planversion pv where op.str_versioncode in(select max(p.str_versioncode) from t_os_plan p "
			+"where  int_upload=1 group by p.str_workOrder ,p.dat_producedate,p.int_produnitid ) and pv.int_id="+id+" and pv.str_versioncode=op.str_versioncode";
		return sql;
	}
	/**
	 * @author 根据版本号改版本备注信息
	 *
	 */
	public String upversiondescriptionbyvcode(String versioncode,String description){
		String sql="update t_os_planversion set str_description='"+description+"' where str_versioncode='"+versioncode+"'";
		return sql;
	}
	/**
	 *  谢静天 根据版本号得到计划信息
	 */
	public String getPlanbyversioncord(String versioncode){
		String sql="select * from t_os_planversion  where str_versioncode='"+versioncode+"'";
		//System.out.println("xxxxxxxxxxxxxxxxxxxx"+sql);
		return sql;
	}
}
