package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.ProLineItem;
import com.qm.mes.pm.bean.ProduceUnitLine;

/**
 * 生产单元线性配置DAO接口类
 * 
 * @author YuanPeng
 *
 */
public interface DAO_ProduceUnitLine {

	/**
	 * 创建生产单元线性配置的sql语句
	 * 
	 * @param ProduceUnitLine 装配指导单对象
	 * @param CreateUID 创建用户ID
	 * @return
	 */
	public String saveProduceUnitLine(ProduceUnitLine ProduceUnitLine);
	
	/**
	 * 通过序号查出生产单元线性配置的sql语句
	 * 
	 * @param id 装配指导单序号
	 * @return 通过序号查出生产单元线性配置对象
	 */
	public String getProduceUnitLineById(int id);
	
	/**
	 * 通过序号删除生产单元线性配置的sql语句
	 * 
	 * @param id 装配指导单序号
	 * @return
	 */
	public String delProduceUnitLineById(int id);
	
	
	/**
	 * 倒叙查询所有生产单元线性配置
	 * 
	 * @return 倒叙查询所有生产单元线性配置的列表
	 */
	public String getAllProduceUnitLinesByDESC();
	
	/**
	 * 倒叙查询所有生产单元线性配置
	 * 
	 * @return 倒叙查询所有生产单元线性配置的列表
	 */
	public String getAllProduceUnitLines();
	
	/**
	 * 通过生产单元线性配置名查询序号
	 * 
	 * @param name
	 * @return 通过生产单元线性配置名查询的序号
	 */
	public String getProduceUnitLineIdByName(String name);
	
	/**
	 * 通过生产单元线性配置名查询生产单元线性配置数量
	 * 
	 * @param name 生产单元线性配置名 
	 * @return 生产单元线性配置数量
	 */
	public String getProduceUnitLineCountByName(String name);
	
	/**
	 * 更新生产单元线性配置
	 * 
	 * @param ProduceUnitLine 生产单元线性配置对象
	 * @return 
	 */
	public String updateProduceUnitLine(ProduceUnitLine ProduceUnitLine);
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建生产单元线性配置号
	 * 
	 * @param ProLineItem 生产单元线性内容对象
	 * @return
	 */
	public String saveProLineItem(ProLineItem ProLineItem);
	
	/**
	 * 通过生产单元线性配置号查出生产单元线性内容列表的sql语句
	 * 
	 * @param id 生产单元线性配置号
	 * @return 通过生产单元线性配置号查出生产单元线性内容列表
	 */
	public String getProLineItemByProduceUnitLineId(int id);
	
	/**
	 * 通过生产单元线性配置号删除生产单元线性内容的sql语句
	 * 
	 * @param id 生产单元线性配置序号
	 * @return
	 */
	public String delProLineItemByProduceUnitLineId(int id);
}
