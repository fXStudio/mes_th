package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.DistriItem;
import com.qm.mes.pm.bean.DistributionDoc;

public class DAO_DistributionDocForOracle implements DAO_DistributionDoc {

	/**
	 * 创建配送指示单的sql语句
	 * 
	 * @param distributionDoc 配送指示单对象+
	 * 
	 * @return
	 */
	public String saveDistributionDoc(DistributionDoc distributionDoc){
		String sql = "insert into t_pm_DistributionDoc(int_id,Str_Name,Str_description,Dat_createDate" +
				",Dat_upDate,Int_CreateUID,Int_UpdateUID,Int_delete,Int_request,Int_response,Int_target " +
				",Str_materiel,Int_bomid) values(seq_pm_DistributionDoc.nextval,'"+distributionDoc.getName()+"','"+
				distributionDoc.getDescription()+"',sysdate,sysdate,"+distributionDoc.getCreateUID()+","+
				distributionDoc.getCreateUID()+",0,"+distributionDoc.getRequest_proUnit()+","+
				distributionDoc.getResponse_proUnit()+","+distributionDoc.getTarget_proUnit()+",'"+
				distributionDoc.getMaterielType()+"',"+distributionDoc.getBomId()+")";
		return sql;
	}
	
	/**
	 * 通过序号查出配送指示单的sql语句
	 * 
	 * @param id 配送指示单序号
	 * @return 通过序号查出配送指示单对象
	 */
	public String getDistributionDocById(int id){
		String sql = "select * from t_pm_DistributionDoc where int_id ="+id;
		return sql;
	}
	
	/**
	 * 通过序号删除配送指示单的sql语句
	 * 
	 * @param id 配送指示单序号
	 * @return
	 */
	public String delDistributionDocById(int id){
		String sql = "update t_pm_DistributionDoc set int_delete=1 where int_id ="+id;
		return sql;
	}
	
	/**
	 * 通过物料标示查出配送指示单的sql语句
	 * 
	 * @param materiel 物料标示 
	 * @return 通过物料标示查出配送指示单对象
	 */
	public String getDistributionDocByMateriel(String materiel){
		String sql = "select * from t_pm_DistributionDoc where Str_materiel ='"+materiel+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 倒叙查询所有配送指示单
	 * 
	 * @return 倒叙查询所有配送指示单的列表
	 */
	public String getAllDistributionDocsByDESC(){
		String sql = "select * from t_pm_DistributionDoc where int_delete<>1 order by int_id desc";
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * 倒叙查询所有配送指示单
	 * 
	 * @return 倒叙查询所有配送指示单的列表
	 */
	public String getAllDistributionDocs(){
		String sql = "select tpd.INT_ID as tpd_id,tpd.STR_NAME as tpd_name,tpd.STR_DESCRIPTION,tpd.STR_MATERIEL,"+
			"(select trp.str_name from T_RA_PRODUCEUNIT trp where INT_REQUEST = trp.int_id ) req_proUnitName,"+
			"(select trp.str_name from T_RA_PRODUCEUNIT trp where INT_RESPONSE = trp.int_id ) res_proUnitName,"+
			"(select trp.str_name from T_RA_PRODUCEUNIT trp where INT_TARGET = trp.int_id ) tar_proUnitName,"+
			"to_char(tpd.DAT_CREATEDATE,'yyyy-mm-dd hh24:mi:ss') as createdate,"+
			"to_char(tpd.DAT_UPDATE,'yyyy-mm-dd hh24:mi:ss') as updatedate,"+
			"(select cusrname from data_user where tpd.int_createuid = nusrno ) createname,"+
			"(select cusrname from data_user where tpd.int_updateuid = nusrno ) updatename "+
			"from T_PM_DISTRIBUTIONDOC tpd "+
			"where tpd.int_delete=0 order by tpd_id asc";
		return sql;
	}
	
	/**
	 * 通过配送指示单名查询序号
	 * 
	 * @param name
	 * @return 通过配送指示单名查询的序号
	 */
	public String getDistributionDocIdByName(String name){
		String sql = "select int_id from t_pm_DistributionDoc where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 通过配送指示单名查询配送指示单数量
	 * 
	 * @param name 配送指示单名 
	 * @return 配送指示单数量
	 */
	public String getDistributionDocCountByName(String name){
		String sql = "select count(*) from t_pm_DistributionDoc where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 更新配送指示单
	 * 
	 * @param distributionDoc 配送指示单对象
	 * @return 
	 */
	public String updateDistributionDoc(DistributionDoc distributionDoc){
		String sql = "update t_pm_DistributionDoc set str_name='"+distributionDoc.getName()+"', Str_description='"+
			distributionDoc.getDescription()+"', Dat_upDate=sysdate"+", Int_UpdateUID= "+
			distributionDoc.getUpdateUID()+",Int_delete=0,Int_request="+distributionDoc.getRequest_proUnit()+
			",Int_response="+distributionDoc.getResponse_proUnit()+",Int_target="+distributionDoc.getTarget_proUnit()
			+",Str_materiel='"+distributionDoc.getMaterielType()+"',Int_bomid="+distributionDoc.getBomId()+" where int_id="+distributionDoc.getId();
		return sql;
	}
	
	/**
	 * 通过请求生产单元号查询配送指示单
	 * 
	 * @param requestProUnitId 请求生产单元号
	 * @return 通过请求生产单元号查询配送指示单列表
	 */
	public String getDistributionDocsByRequestProUnitId(int requestProUnitId){
		String sql ="select * from t_pm_DistributionDoc where Int_request="+requestProUnitId+" and int_delete<>1";
		return sql;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建配送指示单号
	 * 
	 * @param distriItem 配送物料项对象
	 * @return
	 */
	public String saveDistriItem(DistriItem distriItem){
		String sql = "insert into t_pm_DistriItem(int_id,Str_Name,Str_matitem,Int_count,Int_DisDoc,Str_description)" +
				"values(seq_pm_distriItem.nextval,'"+distriItem.getName()+"','"+distriItem.getMatitem()+"',"+
				distriItem.getCount()+","+distriItem.getDistributionDocId()+",'"+distriItem.getDescription()+"')";
		return sql;		
	}
	
	/**
	 * 通过配送指示单号查出配送物料项列表的sql语句
	 * 
	 * @param id 配送指示单号
	 * @return 通过配送指示单号查出配送物料项列表
	 */
	public String getDistriItemByDistributionDocId(int id){
		String sql = "select * from t_pm_DistriItem where Int_DisDoc ="+id;
		return sql;
	}
	
	/**
	 * 通过配送指示单号删除配送物料项的sql语句
	 * 
	 * @param id 配送指示单序号
	 * @return
	 */
	public String delDistriItemByDistributionDocId(int id){
		String sql = "delete from t_pm_DistriItem where Int_DisDoc ="+id;
		return sql;
	}

}
