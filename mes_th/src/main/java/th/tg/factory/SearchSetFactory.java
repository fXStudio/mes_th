package th.tg.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import th.tg.bean.SearchSet;
import th.tg.dao.DAO_SearchSet;

/**
 * 查询设置工厂
 * 
 * @author YuanPeng
 *
 */
public class SearchSetFactory {

	//日志
	private final Log log = LogFactory.getLog(SearchSetFactory.class);
	
	/**
	 * 创建查询设置对象
	 * 
	 * @param ss	查询设置对象
	 * @param con	连接对象
	 * @throws SQLException	SQL异常
	 */
	public void saveSearchSet(SearchSet ss,Connection con)throws SQLException{
		DAO_SearchSet dao = new DAO_SearchSet();
		Statement stmt = con.createStatement();
		log.debug("创建查询设置"+dao.saveSearchSet(ss));
		stmt.execute(dao.saveSearchSet(ss));
		if(stmt != null){
			stmt.close();
			stmt = null;
		}
	}
	
	/**
	 * 查询所有查询设置
	 * 
	 * @param con	连接对象
	 * @return	List<SearchSet>	查询设置列表
	 * @throws SQLException	SQL异常
	 */
	public List<SearchSet> getAllSearchSets(Connection con)throws SQLException{
		List<SearchSet> list = new ArrayList<SearchSet>();
		DAO_SearchSet dao = new DAO_SearchSet();
		Statement stmt = con.createStatement();
		log.debug("查询所有查询设置"+dao.getAllSearchSets());
		ResultSet rs = stmt.executeQuery(dao.getAllSearchSets());
		while(rs.next()){
			SearchSet ss = new SearchSet();
			ss.setCsearchName(rs.getString("cSearchName"));
			ss.setCwa(rs.getString("cWA"));
			ss.setCfactory(rs.getString("cFactory"));
			ss.setCdscFactory(rs.getString("cDscFactory"));
			ss.setCcarType(rs.getString("cCarType"));
			ss.setCdscCarType(rs.getString("cDscCarType"));
			ss.setCremark(rs.getString("cRemark"));
			list.add(ss);
		}
		if(stmt != null){
			stmt.close();
			stmt = null;
		}
		return list;
	}
	
	/**
	 * 通过序号查询查询设置
	 * 
	 * @param id	序号
	 * @param con	连接对象
	 * @return	SearchSet	查询设置列表
	 * @throws SQLException	SQL异常
	 */
	public SearchSet getSearchSetById(int id,Connection con)throws SQLException{
		SearchSet ss = new SearchSet();
		DAO_SearchSet dao = new DAO_SearchSet();
		Statement stmt = con.createStatement();
		log.debug("通过序号查询查询设置"+dao.getSearchSetById(id));
		ResultSet rs = stmt.executeQuery(dao.getSearchSetById(id));
		while(rs.next()){
			ss.setCsearchName(rs.getString("cSearchName"));
			ss.setCwa(rs.getString("cWA"));
			ss.setCfactory(rs.getString("cFactory"));
			ss.setCdscFactory(rs.getString("cDscFactory"));
			ss.setCcarType(rs.getString("cCarType"));
			ss.setCdscCarType(rs.getString("cDscCarType"));
			ss.setCremark(rs.getString("cRemark"));
		}
		if(stmt != null){
			stmt.close();
			stmt = null;
		}
		return ss;
	}
	
	/**
	 * 通过序号删除查询设置
	 * 
	 * @param id	序号
	 * @param con	连接对象
	 * @throws SQLException	SQL异常
	 */
	public void delSearchSetById(int id,Connection con)throws SQLException{
		DAO_SearchSet dao = new DAO_SearchSet();
		Statement stmt = con.createStatement();
		log.debug("通过序号删除查询设置"+dao.delSearchSetById(id));
		stmt.execute(dao.delSearchSetById(id));
		if(stmt != null){
			stmt.close();
			stmt = null;
		}
	}
	
	/**
	 * 通过序号更新查询设置
	 * 
	 * @param id	序号
	 * @param ss	查询设置对象
	 * @param con	连接对象
	 * @throws SQLException	SQL异常
	 */
	public void updateSearchSetById(int id,SearchSet ss,Connection con)throws SQLException{
		DAO_SearchSet dao = new DAO_SearchSet();
		Statement stmt = con.createStatement();
		log.debug("通过序号更新查询设置"+dao.updateSearchSetById(id,ss));
		stmt.execute(dao.updateSearchSetById(id,ss));
		if(stmt != null){
			stmt.close();
			stmt = null;
		}
	}
}
