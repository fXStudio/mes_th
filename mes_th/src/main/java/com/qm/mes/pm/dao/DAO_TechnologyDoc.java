package com.qm.mes.pm.dao;

import com.qm.mes.pm.bean.TechDocItem;
import com.qm.mes.pm.bean.TechItemFile;
import com.qm.mes.pm.bean.TechnologyDoc;


/**
 * 工艺操作说明书DAO接口类
 * 
 * @author YuanPeng
 *
 */
public interface DAO_TechnologyDoc {
	
	/**
	 * 创建工艺操作说明书的sql语句
	 * 
	 * @param technogyDoc 工艺操作说明书对象
	 * @return
	 */
	public String saveTechnologyDoc(TechnologyDoc technogyDoc);
	
	/**
	 * 通过序号查出工艺操作说明书的sql语句
	 * 
	 * @param id 工艺操作说明书序号
	 * @return 通过序号查出工艺操作说明书对象
	 */
	public String getTechnologyDocById(int id);
	
	/**
	 * 通过序号删除工艺操作说明书的sql语句
	 * 
	 * @param id 工艺操作说明书序号
	 * @return
	 */
	public String delTechnologyDocById(int id);
	
	
	/**
	 * 倒叙查询所有工艺操作说明书
	 * 
	 * @return 倒叙查询所有工艺操作说明书的列表
	 */
	public String getAllTechnologyDocsByDESC();
	
	/**
	 * 查询所有工艺操作说明书
	 * 
	 * @return 倒叙查询所有工艺操作说明书的列表
	 */
	public String getAllTechnologyDocs();
	
	/**
	 * 通过工艺操作说明书名查询序号
	 * 
	 * @param name
	 * @return 通过工艺操作说明书名查询的序号
	 */
	public String getTechnologyDocIdByName(String name);
	
	/**
	 * 通过工艺操作说明书名查询装配指示单数量
	 * 
	 * @param name 工艺操作说明书名 
	 * @return 工艺操作说明书数量
	 */
	public String getTechnologyDocCountByName(String name);
	
	/**
	 * 更新工艺操作说明书
	 * 
	 * @param technologyDoc 工艺操作说明书
	 * @return 
	 */
	public String updateTechnologyDoc(TechnologyDoc technologyDoc);
	
	/**
	 * 通过产品类别标示查询工艺操作说明书数量
	 * 
	 * @param materiel 产品类别标示
	 * @return 工艺操作说明书数量
	 */
	public String getTechnologyDocCountByMateriel(String materiel);
	
	
	
	
	
	
	
	
	
	
	/**
	 * 创建工艺操作项
	 * 
	 * @param techDocItem 工艺操作项
	 * @return
	 */
	public String saveTechDocItem(TechDocItem techDocItem);
	
	/**
	 * 通过工艺操作说明书号查出工艺操作项列表的sql语句
	 * 
	 * @param id 工艺操作说明书号
	 * @return 通过工艺操作说明书号查出工艺操作项列表
	 */
	public String getTechDocItemByTechnologyDocId(int id);
	
	/**
	 * 通过工艺操作说明书号删除工艺操作项的sql语句
	 * 
	 * @param id 工艺操作说明书号
	 * @return
	 */
	public String delTechDocItemByTechnologyDocId(int id);
	
	/**
	 * 查询工艺操作项最大序号
	 * 
	 * @return 工艺操作项最大序号
	 */
	public String getTechDocItemMaxId();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 通过工艺操作项序号查询工艺操作项文件
	 * 
	 * @param techDocItemId 工艺操作项序号
	 * @return 工艺操作项文件
	 */
	public String getTechItemFileByTechDocItemId(int techDocItemId);
	
	/**
	 * 创建工艺操作项文件
	 * 
	 * @param tif 工艺操作项文件对象
	 * @return 
	 */
	public String saveTechItemFile(TechItemFile tif);
	
	/**
	 * 通过工艺操作项删除工艺操作项文件
	 * 
	 * @param techDocItemId	工艺操作项序号
	 * @return
	 */
	public String delTechItemFile(int techDocItemId);
	
	/**
	 * 通过工艺操作说明书序号删除工艺操作项文件
	 * 
	 * @param techDocId  工艺操作说明书序号
	 * @return
	 */
	public String delTechItemFileByTechDoc(int techDocId);
	
	/**
	 * 通过旧工艺操作项序号更新新工艺操作项
	 * 
	 * @param oldItemId 旧工艺操作项
	 * @return
	 */
	public String updateTechItemId(int oldItemId);
}