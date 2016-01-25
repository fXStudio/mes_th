package mes.ra.dao;

import mes.ra.bean.Version;

public class DAO_InstructionVersionForOracle implements DAO_InstructionVersion{
	//实现了DAO_InstructionVersion接口,并提供一组针对oracle的sql语句集
	/**
	 * 创建指令版本的sql语句
	 * 谢静天
	*/
	public String saveVersion(Version version){
	String sql = "insert into t_ra_InstructionVersion(int_id,Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,Int_delete,STR_DESCRIPTION) "
		+ "values(seq_RA_InstructionVersion.nextval"
		+",sysdate"
		+ ",'"+ version.getUser()+"'"
		+ ",'"+ version.getProdunitid()+"'"
		+ ",'"+ version.getVersionCode()+"'"
		+ ","+ version.getInt_delete()+""
		+",'"+version.getDescription()+"')";
	    
		return sql;
	}
	/**
	 * 通过版本号查出指令版本的sql语句
	 * 谢静天
	*/
	public String getVersion(String versionCode){
		String sql = "select int_id,Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,Int_delete,STR_DESCRIPTION from t_ra_InstructionVersion "
			+ "where Int_delete=0 and Str_versionCode = '" + versionCode + "'order by int_id";
	
	    return sql;
	}
	
	/**
	 * 通过序号删除指令版本的sql语句
	 * 谢静天
	*/
	public String delVersionById(int id){
		String sql = " update t_ra_InstructionVersion  set int_delete=1 where int_id = " + id;
		
        return sql;
	}
	
	/**
	 * 查询规定生产日期的指令版本集合的sql语句
	 * 谢静天
	*/
	public String getVersionsByDate(String str_date){
		String sql = "select int_id,Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,Int_delete,STR_DESCRIPTION from t_ra_InstructionVersion" 
			+ " where Str_versionCode in(select Str_versionCode from t_ra_InstructionHistory where Dat_produceDate =" + str_date + ")";
	    return sql;
	}
	
	
	/**
	 * 通过生产单元查询指令版本集合
	 * 谢静天
	*/
	public String getVersionsByProduceUnit(String produceUnit){
		String sql = "select int_id,Datime_versionDatime,Str_user,Str_produceUnit,Str_versionCode,Int_delete,STR_DESCRIPTION from t_ra_InstructionVersion "
			+ "where Str_produceUnit = " + produceUnit + " order by Str_produceUnit";
	    return sql;
	}
	
	/**
	 * 通过生产单元及生产日期查出相应的版本集合
	 * 谢静天
	*/
	public String getVersionsByDateAndUnit(String date,String unit){
		String sql = "select int_id,Datime_versionDatime,Str_user,Str_produceUnit,Str_versionCode,Int_delete,STR_DESCRIPTION from t_ra_InstructionVersion,t_ra_InstructionHistory "
			+ "where t_ra_InstructionVersion.Str_versionCode=t_ra_InstructionHistory.Str_versionCode and Str_produceUnit = " + unit + " and t_ra_InstructionHistory.Dat_produceDate= " + date + " order by Str_produceUnit";
	    return sql;
	}
	/**通过生产单元及生产日期查出相应的版本集合
	 * author : 谢静天
	 */
	public String getAllVersions(){
		String sql = "select iv.int_id,to_char(Datime_versionDatime,'yyyy-MM-dd') as Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,iv.Int_delete ,iv.STR_DESCRIPTION,pu.str_name  from t_ra_InstructionVersion iv, t_ra_produceUnit pu where iv.Int_delete=0 and pu.int_id=iv.int_produnitid ";
      
	    return sql;
	}
	public String getVersionsCount(){
        String sql = "select count(*) from t_ra_InstructionVersion where int_delete=0 ";
        return sql;
    }

	/**通过版本号查出指令版本
	 * author : 谢静天
	 */
	public String getVersionByid(int int_id){
		String sql="select int_id,Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,str_description from t_ra_instructionversion where  int_delete=0 and int_id="+int_id;
		
		return sql;
	}
	/**
	 * 谢静天
	 * 通过模糊查询版本号返回相应的结果
	 */
  public  String getVersionbylike(String versioncode){
	String sql = "select int_id,Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,Int_delete,STR_DESCRIPTION from t_ra_InstructionVersion "
			+ "where Int_delete=0 and Str_versionCode like'%" + versioncode + "%'order by int_id";
		
	    return sql;
  }
  /**根据版本号改版本备注信息
	 * @author  包金旭
	 *
	 */
	public String upversiondescriptionbyvcode(String versioncode,String description){
		String sql="update t_ra_InstructionVersion set str_description='"+description+"' where str_versioncode='"+versioncode+"'";
		return sql;
	}
  
  
  
}
	

