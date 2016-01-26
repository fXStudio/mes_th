package com.qm.mes.ra.dao;

import com.qm.mes.ra.bean.Version;

public class DAO_InstructionVersionForSqlserver extends DAO_InstructionVersionForOracle{
	/**
     * 创建指令版本的sql语句
     */
	public String saveVersion(Version version){
	String sql = "insert into t_ra_InstructionVersion(Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,Int_delete) "
		+ "values(convert(varchar,getdate(),20)"
		
		+ ",'"+ version.getUser()+"'"
		+ ",'"+ version.getProdunitid()+"'"
		+ ",'"+ version.getVersionCode() + "'"
		+","+version.getInt_delete()+")";
	    
		return sql;
	}	
	/**
     * 查询规定生产日期的指令版本集合的sql语句
     */
	public String getVersionsByDate(String str_date){
		String sql = "select int_id,Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,Int_delete from t_ra_InstructionVersion" 
			+ " where Str_versionCode in(select Str_versionCode from t_ra_InstructionHistory where convert(datetime,Dat_produceDate,20) =convert(datetime,'"+str_date+"',20))";
	    return sql;
	}
	/**
     * 通过生产单元及生产日期查出相应的版本集合
     */
	public String getVersionsByDateAndUnit(String date,String unit){
		String sql = "select int_id,Datime_versionDatime,Str_user,Str_produceUnit,Str_versionCode,Int_delete from t_ra_InstructionVersion,t_ra_InstructionHistory "
			+ "where t_ra_InstructionVersion.Str_versionCode=t_ra_InstructionHistory.Str_versionCode and Str_produceUnit = " + unit + " and convert(datetime,t_ra_InstructionHistory.Dat_produceDate,20)= convert(datetime,'"+date+"',20)";
	    return sql;
	}
	/**
     * 通过生产单元及生产日期查出相应的版本集合
     */
	public String getAllVersions(){
		String sql = "select iv.int_id,convert(datetime,iv.Datime_versionDatime,20) as Datime_versionDatime,Str_user,int_produnitid,Str_versionCode,iv.Int_delete ,pu.str_name  from t_ra_InstructionVersion iv, t_ra_produceUnit pu where iv.Int_delete=0 and pu.int_id=iv.int_produnitid  ";
      
	    return sql;
	}
}
	

