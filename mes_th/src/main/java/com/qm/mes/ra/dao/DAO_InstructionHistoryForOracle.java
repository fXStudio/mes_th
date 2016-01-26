package com.qm.mes.ra.dao;

import java.text.SimpleDateFormat;

import com.qm.mes.ra.bean.Instruction;

public class DAO_InstructionHistoryForOracle implements DAO_InstructionHistory{
	//实现了DAO_InstructionHistory接口,并提供一组针对oracle的sql语句集
	/**
	 * 
	 * 创建指令版本内容的sql语句
	 * 谢静天
	*/
	public String saveVersionHistory(Instruction instruction,String str_versioncode){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String a=instruction.getProduceDate()==null||instruction.getProduceDate().toString().equals("null")?"":(""+sdf.format(instruction.getProduceDate())+"");
		  String b=instruction.getPlanDate()==null||instruction.getPlanDate().toString().equals("null")?"":(""+sdf.format(instruction.getPlanDate())+"");
		  
		  
		  String c=instruction.getPlanBegin()==null||instruction.getPlanBegin().toString().equals("null")?"":(""+sdf.format(instruction.getPlanBegin())+"");
		
		  String d=instruction.getPlanFinish()==null||instruction.getPlanFinish().toString().equals("null")?"":(""+sdf.format(instruction.getPlanFinish())+"");
		  String sql = "insert into t_ra_InstructionHistory "
			+ "values(seq_RA_INSTRUCTIONHistory.nextval,"
			+ instruction.getProdunitid() + ","
			
			+"to_date('"+
			a
			+"','yyyy-mm-dd hh24:mi:ss')"+ ",'"
			+  str_versioncode + "',"
			+ instruction.getInstructionOrder() + ","
			+"to_date('"+
			b
			+"','yyyy-mm-dd hh24:mi:ss')"+ ","
			+ instruction.getPlanOrder() + ",'"
			+ instruction.getProduceType() + "','"
			+ instruction.getProduceName() + "','"
			+ instruction.getProduceMarker() + "','"
			+ instruction.getWorkOrder() + "','"
			+ instruction.getWorkTeam() + "',"
			+"to_date('"+
			c
			+"','yyyy-mm-dd hh24:mi:ss')"+ ","
			
			+"to_date('"+
			d
			+"','yyyy-mm-dd hh24:mi:ss')"+ ","
			+ instruction.getCount()+ ","
			+ instruction.getInstructStateID() 
			+")";
		
			return sql;
	}
	
	/**
	 * 
	 * 通过版本号查出指令版本内容的sql语句
	 * 谢静天
	*/
	public String getVersionHistory(String versionCode){
		//
		String sql = "select to_char(Dat_produceDate,'yyyy-MM-dd')as produceDate  from t_ra_InstructionHistory "
			+ "where Str_versionCode = '" + versionCode + "'";
		
	    return sql;
	}
	
	/**
	 * 
	 * 通过版本号删除相应的版本内容的sql语句
	 * 谢静天
	*/
	public String delVersionHistory(String versionCode){
		//
		String sql = "delete from t_ra_InstructionHistory where Str_versionCode ='"+versionCode+"'";
       
		return sql;
	}
	/**
	 * 
	 * 通过指令版本序号得到指令版本记录表
	 * 谢静天
	*/
	public 	String getInstructionHistory(int int_id){
		String sql="select rh.int_id,rh.int_produnitid,rh.Dat_produceDate,rh.Str_versionCode," +
				"rh.Int_instructOrder,rh.Dat_planDate,rh.Int_planOrder,rh.Str_produceType," +
				"rh.Str_produceName,rh.Str_produceMarker,rh.Str_workOrder,rh.Str_workTeam,rh.Tim_planBegin,rh.Tim_planFinish," +
				"rh.Int_count,rh.Int_instructStateID from t_ra_instructionhistory rh,t_ra_instructionversion rv where rh.Str_versioncode=rv.Str_versioncode "
			+" and rh.int_produnitid=rv.int_produnitid and rv.int_id="+int_id+"  order by rh.Int_instructOrder asc";
		
		return sql;
	}
	

   /**
	 * 
	 * 通过生产单元和生产日期查询指令版本记录表
	 * 谢静天
	*/
  public String getInstructionhistorybyproduceunitdate(int int_produnitid,String Dat_produceDate){
	  String sql="select * from t_ra_instructionhistory where int_produnitid="+int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+Dat_produceDate+"'";
	 
	  return sql;
  }
	 /**
	  * 查找最大的版本号
	  * 谢静天
	  */
	
	 public    String checkcodebyproduceunitanddateWorkorder(int int_produnitid,String str_date,String workOrder){
		  String sql="select * from t_ra_instructionhistory where int_produnitid="+int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workOrder='"+workOrder+"' order by int_id desc ";
		 
		  return sql;
	 }
	   /**
	    * 谢静天
      * 
      * 通过版本号到指令历史表中查找内容的集合
      */
    public    String getInstructionbyversioncode(String versioncode){
    	String sql="select * from t_ra_instructionhistory where str_versioncode='"+versioncode+"'";
    	return sql;
    }
}
