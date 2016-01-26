package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.DistriItem;
import com.qm.mes.pm.bean.DistributionDoc;

/**
 * 配送指示单DAO接口类
 * 
 * @author YuanPeng
 *
 */
public interface DAO_DistributionDoc {

	/**
	 * 创建配送指示单的sql语句
	 * 
	 * @param distributionDoc 配送指导单对象
	 * @return
	 */
	public String saveDistributionDoc(DistributionDoc distributionDoc);
	
	/**
	 * 通过序号查出配送指示单的sql语句
	 * 
	 * @param id 配送指示单序号
	 * @return 通过序号查出装配指示单对象
	 */
	public String getDistributionDocById(int id);
	
	/**
	 * 通过序号删除配送指示单的sql语句
	 * 
	 * @param id 配送指示单序号
	 * @return
	 */
	public String delDistributionDocById(int id);
	
	/**
	 * 通过物料标示查出配送指示单的sql语句
	 * 
	 * @param materiel 物料标示 
	 * @return 通过物料标示查出配送指示单对象
	 */
	public String getDistributionDocByMateriel(String materiel);
	
	/**
	 * 倒叙查询所有配送指示单
	 * 
	 * @return 倒叙查询所有配送指示单的列表
	 */
	public String getAllDistributionDocsByDESC();
	
	/**
	 * 倒叙查询所有配送指示单
	 * 
	 * @return 倒叙查询所有配送指示单的列表
	 */
	public String getAllDistributionDocs();
	
	/**
	 * 通过配送指示单名查询序号
	 * 
	 * @param name
	 * @return 通过配送指示单名查询的序号
	 */
	public String getDistributionDocIdByName(String name);
	
	/**
	 * 通过配送指示单名查询配送指示单数量
	 * 
	 * @param name 配送指示单名 
	 * @return 配送指示单数量
	 */
	public String getDistributionDocCountByName(String name);
	
	/**
	 * 更新配送指示单
	 * 
	 * @param distributionDoc 配送指示单对象
	 * @return 
	 */
	public String updateDistributionDoc(DistributionDoc distributionDoc);
	
	/**
	 * 通过请求生产单元号查询配送指示单
	 * 
	 * @param requestProUnitId 请求生产单元号
	 * @return 通过请求生产单元号查询配送指示单列表
	 */
	public String getDistributionDocsByRequestProUnitId(int requestProUnitId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建配送指示单号
	 * 
	 * @param distriItem 配送物料项对象
	 * @return
	 */
	public String saveDistriItem(DistriItem distriItem);
	
	/**
	 * 通过配送指示单号查出配送物料项列表的sql语句
	 * 
	 * @param id 配送指示单号
	 * @return 通过配送指示单号查出配送物料项列表
	 */
	public String getDistriItemByDistributionDocId(int id);
	
	/**
	 * 通过配送指示单号删除配送物料项的sql语句
	 * 
	 * @param id 配送指示单序号
	 * @return
	 */
	public String delDistriItemByDistributionDocId(int id);
}
