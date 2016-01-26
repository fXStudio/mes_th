package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.DistributionAccept;

/**
 * 配送确认单DAO接口类
 * 
 * @author YuanPeng
 *
 */
public interface DAO_DistributionAccept {

	/**
	 * 创建配送确认单的sql语句
	 * 
	 * @param distributionAccept 装配指导单对象
	 * @param CreateUID 创建用户ID
	 * @return
	 */
	public String saveDistributionAccept(DistributionAccept distributionAccept);
	
	/**
	 * 通过ID处理配送确认单
	 * 
	 * param id 序号
	 * @param userid 用户ID
	 * return  
	 */
	public String transactDistributionAccept(int id,int userid);
	
	/**
	 * 通过序号查出配送确认单的sql语句
	 * 
	 * @param id 配送确认单序号
	 * @return 通过序号查出配送确认单对象
	 */
	public String getDistributionAcceptById(int id);
	
	/**
	 * 倒叙查询所有配送确认单
	 * 
	 * @return 倒叙查询所有配送确认单的列表
	 */
	public String getAllDistributionAcceptsByDESC();
	
	/**
	 * 通过配送指示单响应生产单元查询配送确认单
	 * 
	 * @return 通过配送指示单响应生产单元查询配送确认单的列表
	 */
	public String getDistributionAcceptsByresponseProUnit(int responseProUnit);
}
