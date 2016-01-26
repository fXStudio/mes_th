package com.qm.th.tg.dao;

import com.qm.th.tg.bean.SearchSet;

/**
 * 查询设置
 * 
 * @author YuanPeng
 *
 */
public class DAO_SearchSet {

	/**
	 * 创建查询设置
	 * 
	 * @param ss	查询设置对象
	 * @return
	 */
	public String saveSearchSet(SearchSet ss){
		String sql = "insert into searchSet(cSearchName,cWA,cFactory,cDscFactory,cCarType,cDscCarType,cRemark)" +
			" values('"+ss.getCsearchName()+"','"+ss.getCwa()+"','"+ss.getCfactory()+"','"+ss.getCdscFactory()
			+"','"+ss.getCcarType()+"','"+ss.getCdscCarType()+"','"+ss.getCremark()+"')";
		return sql;
	}

	/**
	 * 查询所有查询对象
	 * 
	 * @return	List<SearchSet>  查询对象列表
	 */
	public String getAllSearchSets(){
		String sql = "select * from searchSet";
		return sql;
	}

	/**
	 * 通过序号查找查询对象
	 * @param id	序号
	 * @return	SearchSet  查询对象
	 */
	public String getSearchSetById(int id){
		String sql = "select * from searchSet where id = " + id;
		return sql;
	}
	
	/**
	 * 通过序号删除查询设置
	 * 
	 * @param id	序号
	 * @return
	 */
	public String delSearchSetById(int id){
		String sql = "delete searchSet where id = " + id;
		return sql;
	}
	
	/**
	 * 通过序号更新查询设置
	 * 
	 * @param id	序号
	 * @param ss	查询设置对象
	 * @return
	 */
	public String updateSearchSetById(int id,SearchSet ss){
		String sql = "update searchSet set " +
			"cSearchName = '" + ss.getCsearchName() +
			"',cWA = '" + ss.getCwa() +
			"',cFactory = '" + ss.getCfactory() +
			"',cDscFactory = '" + ss.getCdscFactory() +
			"',cCarType = '" + ss.getCcarType() +
			"',cDscCarType = '" + ss.getCdscCarType() +
			"',cRemark = '" + ss.getCremark() + 
			"' where id = " + id;
		return sql;
	}
}
