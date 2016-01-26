package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.DistributionAccept;

public class DAO_DistributionAcceptForOracle implements DAO_DistributionAccept {

	/**
	 * 创建配送确认单的sql语句
	 * 
	 * @param distributionAccept 配送确认单对象
	 * @param CreateUID 创建用户ID
	 * @return
	 */
	public String saveDistributionAccept(DistributionAccept distributionAccept){
		String sql = "insert into t_pm_DistributionAccept(int_id,Int_DisDocId,Int_State,Dat_requestDate," +
				"Str_materiel) " +
				"values(seq_pm_DistributionAccept.nextval,"+distributionAccept.getDisDocId()+",0,sysdate,'"
				+distributionAccept.getMateriel()+"')";
		return sql;
	}
	
	/**
	 * 通过ID处理配送确认单
	 * 
	 * param id 序号
	 * @param userid 用户id
	 * return  
	 */
	public String transactDistributionAccept(int id,int userid){
		String sql = "update t_pm_DistributionAccept set Int_State=1,Dat_responseDate=sysdate,Int_responseUID=" +
				+userid+" where int_id="+id;
		return sql;
	}
	
	/**
	 * 通过序号查出配送确认单的sql语句
	 * 
	 * @param id 配送确认单序号
	 * @return 通过序号查出配送确认单对象
	 */
	public String getDistributionAcceptById(int id){
		String sql = "select * from t_pm_DistributionAccept where int_id ="+id;
		return sql;
	}
	
	/**
	 * 倒叙查询所有配送确认单
	 * 
	 * @return 倒叙查询所有配送确认单的列表
	 */
	public String getAllDistributionAcceptsByDESC(){
		String sql = "select * from t_pm_DistributionAccept order by int_id desc";
		System.out.println(sql);
		return sql;
	}
	
	/**
	 * 通过配送指示单响应生产单元查询配送确认单
	 * 
	 * @return 通过配送指示单响应生产单元查询配送确认单的列表
	 */
	public String getDistributionAcceptsByresponseProUnit(int responseProUnit){
		String sql = "select da.int_id,Int_DisDocId,Int_State,Dat_requestDate,Dat_responseDate,Int_responseUID,da.Str_materiel" +
				",dd.INT_TARGET from t_pm_DistributionAccept da,t_pm_DistributionDoc dd where Int_response="+responseProUnit+
				" and Int_DisDocId=dd.int_id order by da.Int_State";
		return sql;
	}
}
