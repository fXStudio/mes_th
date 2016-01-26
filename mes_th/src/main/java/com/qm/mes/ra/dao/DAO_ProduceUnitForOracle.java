package com.qm.mes.ra.dao;
import com.qm.mes.ra.bean.*;
/**
 * 
 * @author 谢静天
 *
 */
public class DAO_ProduceUnitForOracle implements DAO_ProduceUnit {
	/** 创建生产单元
	 * 谢静天
	 * param produceUnit;
	 */
	public String saveProduceUnit(ProduceUnit produceUnit){
		String sql="insert into t_ra_produceUnit values(seq_ra_produceUnit.nextval,'"
			+produceUnit.getStr_name()
			+"','"
			+produceUnit.getStr_code()
			+"',"
			+produceUnit.getInt_instructStateID()
			+","
			+produceUnit.getInt_planIncorporate()
			+","
			+produceUnit.getInt_instCount()
			+","
			+produceUnit.getInt_Type()
			+","
			+produceUnit.getInt_delete()
			+","
			+produceUnit.getInt_materielRuleid()
			+")";
		
		return sql;
	}
	
	/**
	 * 谢静天
	 * 得到所有的生产单元的信息
	 */
	public String getAllProduceUnit(){
		String sql="select pu.int_id ,pu.Str_name ,pu.Str_code,pu.int_instructStateid,"
        +"pu.int_instcount,pu.int_planIncorporate,pu.int_Type,pu.int_delete,"
          +"pu.INT_MATERIELRULEID,s.str_statename,m.str_name as materielname from"
          +" t_ra_produceunit pu left join t_ra_state s on s.int_id=pu.int_instructStateid"
         +" left join t_tg_materielrule m  on m.int_id=pu.INT_MATERIELRULEID"
         +" where  pu.int_delete=0";
		
		return sql;
	}
	/**
	 * 谢静天
	 * 得到id号的生产单元的信息
	 */
    public  String getProduceUnitById(int id){
    	String sql="select * from t_ra_produceUnit where Int_delete=0 and int_id="+id;
    	
    	return sql;
    }
    /**
     * 
     * 谢静天
     * 更改生产单元信息
     * param produceunit
     */
   public  String updateProduceUnit(ProduceUnit produceunit){
	   String sql=" update t_ra_produceUnit set Str_name='"+produceunit.getStr_name()+"',"
	   +"Str_code='"+produceunit.getStr_code()+"'," 
	   +"Int_instructstateID="+produceunit.getInt_instructStateID()+","
	   +"Int_planIncorporate="+produceunit.getInt_planIncorporate()+","
	   +"Int_materielRuleid="+produceunit.getInt_materielRuleid()+","
	   	+"Int_instCount="+produceunit.getInt_instCount()+" where int_id="+produceunit.getInt_id()+"";
	 
	   return sql;
   }
   /**
    * 
    * 通过id删除指定的生产单元
    * 逻辑删除
    * 谢静天
    */
   public   String deleteProduceUnitById(int id){
	   String sql="update t_ra_produceUnit set Int_delete=1 where int_id="+id;
	   return sql;
   }
   /**
    * 通过名字查询相关的生产单元
    * 
    * 谢静天
    */
  public  String getProduceUnitByName(String name){
	  String sql="select * from t_ra_produceUnit where Int_delete=0 and Str_name='"+name+"'";
	  return sql;
  }
  /**
   * 通过生产单元的id查到生产单元的初始指令状态
   * 谢静天
   */
  public  String getInstructionstateIdByproduceunitid(int id){
	  String sql="select Int_instructStateID from t_ra_produceUnit where int_id="+id;
	 
	  return sql;
  }
  /**
	 * 谢静天
	 * 得到所有的生产单元的信息
	 * 查询结果包括被删除的生产单元。
	 */
  public String getAllProduceUnitDESC(){
	  String sql="select * from t_ra_produceunit order by int_id desc ";
		return sql;
  }
  
  /**
   * 通过物料标识规则号查询生产单元对象
   * 
   * @param materielurleid	物料标识规则号
   * @return	生产单元对象
   */
  public String countProduceUnitByMateritelRuleID(int materielurleid){
	  String sql = "select count(*) from t_ra_produceunit where INT_MATERIELRULEID = "+materielurleid+" and int_delete = 0";
	  return sql;
  }
  
  /**
   * 倒叙查询所有生产单元的序号和名称
   * 
   * @return	倒叙查询所有生产单元的序号和名称的集合
   */
  public String getAllProdUnitIdNamesByDesc(){
	  String sql =" select int_id,str_name from t_ra_produceunit where int_delete<>1 order by int_id desc";
	  return sql;
  }
  
  //--------------------------------------------------------东阳项目添加-----------------------------------------------------------
  public String getCunitByid(int id){
	  String sql="select int_childunit_id FROM  T_RA_R_PRODUCTUNIT where int_parentunit_id="+id;
	  return sql;
  }
  
   public String delCunitByid(int id){
	   String sql="delete from T_RA_R_PRODUCTUNIT where int_parentunit_id="+id;
	   return sql;
   }
	  
	public String addCunit(int id, String CunitId){
		String sql="insert into T_RA_R_PRODUCTUNIT(int_parentunit_id,int_childunit_id) values("+id+",'"+CunitId+"')";
		return sql;
	}
	
}
