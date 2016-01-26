package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.AssDocItem;
import com.qm.mes.pm.bean.AssembleDoc;

/**
 * 装配指示单DAO接口类
 * 
 * @author YuanPeng
 *
 */
public interface DAO_AssembleDoc {
	
	/**
	 * 创建装配指示单的sql语句
	 * 
	 * @param assembleDoc 装配指导单对象
	 * @param CreateUID 创建用户ID
	 * @return
	 */
	public String saveAssembleDoc(AssembleDoc assembleDoc);
	
	/**
	 * 通过序号查出装配指示单的sql语句
	 * 
	 * @param id 装配指导单序号
	 * @return 通过序号查出装配指示单对象
	 */
	public String getAssembleDocById(int id);
	
	/**
	 * 通过序号删除装配指示单的sql语句
	 * 
	 * @param id 装配指导单序号
	 * @return
	 */
	public String delAssembleDocById(int id);
	
	/**
	 * 通过物料标示查出装配指示单的sql语句
	 * 
	 * @param materiel 物料标示 
	 * @return 通过物料标示查出装配指示单对象
	 */
	public String getAssembleDocByMateriel(String materiel);
	
	/**
	 * 倒叙查询所有装配指示单
	 * 
	 * @return 倒叙查询所有装配指示单的列表
	 */
	public String getAllAssembleDocsByDESC();
	
	/**
	 * 倒叙查询所有装配指示单
	 * 
	 * @return 倒叙查询所有装配指示单的列表
	 */
	public String getAllAssembleDocs();
	
	/**
	 * 通过装配指示单名查询序号
	 * 
	 * @param name
	 * @return 通过装配指示单名查询的序号
	 */
	public String getAssembleDocIdByName(String name);
	
	/**
	 * 通过装配指示单名查询装配指示单数量
	 * 
	 * @param name 装配指示单名 
	 * @return 装配指示单数量
	 */
	public String getAssembleDocCountByName(String name);
	
	/**
	 * 更新装配指示单
	 * 
	 * @param assembleDoc 装配指示单对象
	 * @return 
	 */
	public String updateAssembleDoc(AssembleDoc assembleDoc);
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建装配指示单号
	 * 
	 * @param assDocItem 装配指示单对象
	 * @return
	 */
	public String saveAssDocItem(AssDocItem assDocItem);
	
	/**
	 * 通过装配指示单号查出装配指示项列表的sql语句
	 * 
	 * @param id 装配指示单号
	 * @return 通过装配指示单号查出装配指示项列表
	 */
	public String getAssDocItemByAssembleDocId(int id);
	
	/**
	 * 通过装配指示单号删除装配指示项的sql语句
	 * 
	 * @param id 装配指示单序号
	 * @return
	 */
	public String delAssDocItemByAssembleDocId(int id);
}
