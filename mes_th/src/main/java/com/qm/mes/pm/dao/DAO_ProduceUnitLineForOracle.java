package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.ProLineItem;
import com.qm.mes.pm.bean.ProduceUnitLine;

public class DAO_ProduceUnitLineForOracle implements DAO_ProduceUnitLine{

	/**
	 * 创建生产单元线性配置的sql语句
	 * 
	 * @param produceUnitLine 生产单元线性配置对象
	 * @return
	 */
	public String saveProduceUnitLine(ProduceUnitLine ProduceUnitLine){
		String sql = "insert into t_pm_ProduceUnitLine(int_id,Str_Name,str_Description,int_delete) " +
				"values(seq_pm_ProduceUnitLine.nextval,'"+ProduceUnitLine.getName()+"','"+
				ProduceUnitLine.getDescription()+"',0)";
		return sql;
	}
	
	/**
	 * 通过序号查出生产单元线性配置的sql语句
	 * 
	 * @param id 生产单元线性配置序号
	 * @return 通过序号查出生产单元线性配置对象
	 */
	public String getProduceUnitLineById(int id){
		String sql = "select * from t_pm_ProduceUnitLine where int_id ="+id+" and int_delete<>1";
		return sql;
	}
	
	/**
	 * 通过序号删除生产单元线性配置的sql语句
	 * 
	 * @param id 生产单元线性配置序号
	 * @return
	 */
	public String delProduceUnitLineById(int id){
		String sql = "update t_pm_ProduceUnitLine set int_delete=1 where int_id ="+id;
		return sql;
	}
	
	/**
	 * 倒叙查询所有生产单元线性配置
	 * 
	 * @return 倒叙查询所有生产单元线性配置的列表
	 */
	public String getAllProduceUnitLinesByDESC(){
		String sql = "select * from t_pm_ProduceUnitLine where int_delete<>1 order by int_id desc";
		return sql;
	}
	
	/**
	 * 倒叙查询所有生产单元线性配置
	 * 
	 * @return 倒叙查询所有生产单元线性配置的列表
	 */
	public String getAllProduceUnitLines(){
		String sql = "select * from t_pm_ProduceUnitLine where int_delete<>1";
		return sql;
	}
	
	/**
	 * 通过生产单元线性配置名查询序号
	 * 
	 * @param name
	 * @return 通过生产单元线性配置名查询的序号
	 */
	public String getProduceUnitLineIdByName(String name){
		String sql = "select int_id from t_pm_ProduceUnitLine where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 通过生产单元线性配置名查询生产单元线性配置数量
	 * 
	 * @param name 生产单元线性配置名 
	 * @return 生产单元线性配置数量
	 */
	public String getProduceUnitLineCountByName(String name){
		String sql = "select count(*) from t_pm_ProduceUnitLine where str_name='"+name+"' and int_delete<>1";
		return sql;
	}
	
	/**
	 * 更新生产单元线性配置
	 * 
	 * @param ProduceUnitLine 生产单元线性配置对象
	 * @return 
	 */
	public String updateProduceUnitLine(ProduceUnitLine ProduceUnitLine){
		String sql = "update t_pm_ProduceUnitLine set str_name='"+ProduceUnitLine.getName()+
		"',Str_description='"+ProduceUnitLine.getDescription()+"' where int_id="+ProduceUnitLine.getId();
		return sql;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建生产单元线性配置号
	 * 
	 * @param ProLineItem 生产单元线性内容对象
	 * @return
	 */
	public String saveProLineItem(ProLineItem ProLineItem){
		String sql = "insert into t_pm_ProLineItem(int_id,Int_produceUnitId,Int_Order,Int_LineId)" +
				"values(seq_pm_ProLineItem.nextval,"+ProLineItem.getProduceUnitId()+
				","+ProLineItem.getOrder()+","+ProLineItem.getLineId()+")";
		return sql;		
	}
	
	/**
	 * 通过生产单元线性配置号查出生产单元线性内容列表的sql语句
	 * 
	 * @param id 生产单元线性配置号
	 * @return 通过生产单元线性配置号查出生产单元线性内容列表
	 */
	public String getProLineItemByProduceUnitLineId(int id){
		String sql = "select * from t_pm_ProLineItem where Int_LineId ="+id+" order by Int_Order";
		return sql;
	}
	
	/**
	 * 通过生产单元线性配置号删除生产单元线性内容的sql语句
	 * 
	 * @param id 生产单元线性配置序号
	 * @return
	 */
	public String delProLineItemByProduceUnitLineId(int id){
		String sql = "delete from t_pm_ProLineItem where Int_LineId ="+id;
		return sql;
	}

}
