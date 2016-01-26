package com.qm.mes.os.dao;

import com.qm.mes.os.bean.Version;



public class DAO_VersionForSqlserver extends DAO_VersionForOracle{

	/* (non-Javadoc)
	 * 得到所有的版本信息  谢静天
	 * @see mes.os.dao.DAO_VersionForOracle#getAllVersions()
	 */
	public String getAllVersions(){
		String sql=" select int_id,convert(varchar,dat_versionDatime,20)as dat_versionDatime,str_user,str_versioncode from t_os_PlanVersion order by int_id desc";
		
		return sql;
	}
	/* (non-Javadoc)
	 * 创建版本 谢静天
	 * @see mes.os.dao.DAO_VersionForOracle#saveVersion(mes.os.bean.Version)
	 */
	public String saveVersion(Version version){
		String sql="insert into t_os_PlanVersion values("
			+"sysdate,'"
			+version.getUser()+"','"
			+version.getVersionCode()+"')";
		
		return sql;
	}
	
 

}
