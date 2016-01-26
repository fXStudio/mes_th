package com.qm.mes.tg.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.system.dao.DAOFactoryAdapter;
import com.qm.mes.tg.bean.MaterielRule;
import com.qm.mes.tg.dao.IDAO_MaterielRule;

public class MaterielRuleFactory {
	
	//日志
	private final Log log = LogFactory.getLog(MaterielRuleFactory.class);

	/**
	 * 通过采集点序号，查出该采集点物料的全部验证规则，主物料规则在第一位，子物料规则依次排序
	 * 
	 * @param id
	 *            采集点序号
	 * @return 规则列表
	 * @throws SQLException
	 */
	public List<MaterielRule> getListByGid(int id, Connection con)
			throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			IDAO_MaterielRule dao_materielRule = (IDAO_MaterielRule) DAOFactoryAdapter
					.getInstance(DataBaseType.getDataBaseType(con),
							IDAO_MaterielRule.class);
			stmt = con.createStatement();
			List<MaterielRule> list = new ArrayList<MaterielRule>();
			log.debug("通过序号查询主物料标识规则SQL语句："+dao_materielRule.gerMainRuleByGid(id));
			// 查询主物料的验证规则
			rs = stmt.executeQuery(dao_materielRule.gerMainRuleByGid(id));
			if (rs.next()) {
				MaterielRule mr = new MaterielRule();
				mr.setId(rs.getInt("int_id"));
				mr.setName(rs.getString("str_name"));
				mr.setValidate(rs.getString("str_validateclass"));
				log.debug("主物料验证规则--主物料验证规则号："+mr.getId()+"；主物料验证规则名："+mr.getName()
						+"；主物料验证规则字符串:"+mr.getValidate());
				list.add(mr);
			} else {
				log.fatal("主物料验证规则不存在");
				
				// 如果主物料验证规则不存在将抛出异常
				throw new SQLException("主物料验证规则不存在");
			}
			log.debug("通过序号查询子物料标识规则SQL语句："+dao_materielRule.getAttributeByGid(id));
			// 查询子物料的验证规则
			rs = stmt.executeQuery(dao_materielRule.getAttributeByGid(id));
			log.debug("子物料的验证规则--");
			while (rs.next()) {
				MaterielRule mr = new MaterielRule();
				mr.setId(rs.getInt("int_id"));
				mr.setName(rs.getString("str_name"));
				mr.setValidate(rs.getString("str_validateclass"));
				log.debug("子物料的验证规则号："+rs.getInt("int_id")+"；子物料的验证规则名："+
						rs.getString("str_name")+"；子物料的验证规则字符串："+rs.getString("str_validateclass"));
				list.add(mr);
			}
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		}

	}

	/**
	 * 创建materielRule
	 * 
	 * @param materielRule
	 * @param con
	 * @throws SQLException
	 */
	public void saveMaterielRule(MaterielRule materielRule, Connection con)
			throws SQLException {
		IDAO_MaterielRule dao = (IDAO_MaterielRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterielRule.class);
		Statement stmt = con.createStatement();
		log.debug("创建物料标识规则SQL语句："+dao.saveMainRule(materielRule));
		stmt.execute(dao.saveMainRule(materielRule));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 修改materielRule
	 * 
	 * @param materielRule
	 * @param con
	 * @throws SQLException
	 */
	public void updateMaterielRule(MaterielRule materielRule, Connection con)
			throws SQLException {
		IDAO_MaterielRule dao = (IDAO_MaterielRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterielRule.class);
		Statement stmt = con.createStatement();
		log.debug("修改物料标识规则SQL语句："+dao.updateMainRule(materielRule));
		stmt.execute(dao.updateMainRule(materielRule));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 删除materielRule
	 * 
	 * @param id
	 * @param con
	 * @throws SQLException
	 */
	public void deleteMaterielRule(int id, Connection con) throws SQLException {
		IDAO_MaterielRule dao = (IDAO_MaterielRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterielRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过物料标识规则号删除物料标识规则SQL语句："+dao.deleteMainRule(id));
		stmt.execute(dao.deleteMainRule(id));
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}

	/**
	 * 查看单个物料标识规则的工厂
	 * 
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public MaterielRule findMaterielRule(int id, Connection con)
			throws SQLException {
		IDAO_MaterielRule dao = (IDAO_MaterielRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterielRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过物料标识规则号查询物料标识规则SQL语句："+dao.findMainRule(id));
		ResultSet rs = stmt.executeQuery(dao.findMainRule(id));
		MaterielRule materielRule =  null;
		if(rs.next()){
			materielRule = new MaterielRule();
			materielRule.setId(rs.getInt("int_id"));
			materielRule.setName(rs.getString("str_name"));
			materielRule.setValidate(rs.getString("str_validateclass"));
			materielRule.setDesc(rs.getString("str_desc"));
			log.debug("物料的验证规则号："+rs.getInt("int_id")+"；物料的验证规则名："+rs.getString("str_name")+
					"；物料的验证规则字符串："+rs.getString("str_validateclass")+"；物料标识规则描述："+rs.getString("str_desc"));
			
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return materielRule;
	}

	/**
	 * 查询所有物料标识规则的工厂
	 * 
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<MaterielRule> selectMaterielRule(Connection con)
			throws SQLException {
		IDAO_MaterielRule dao = (IDAO_MaterielRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterielRule.class);
		List<MaterielRule> list = new ArrayList<MaterielRule>();
		Statement stmt = con.createStatement();
		log.debug("查询所有物料标识规则SQL语句:"+dao.selectMainRule());
		ResultSet rs = stmt.executeQuery(dao.selectMainRule());
		log.debug("查询所有物料标识规则列表---");
		while (rs.next()) {
			MaterielRule m = new MaterielRule();
			m.setId(rs.getInt("int_id"));
			m.setName(rs.getString("str_name"));
			m.setValidate(rs.getString("str_validateclass"));
			log.debug("物料的验证规则号："+rs.getInt("int_id")+"；物料的验证规则名："+rs.getString("str_name")+
					"；物料的验证规则字符串："+rs.getString("str_validateclass"));
			list.add(m);
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return list;
	}
	
	/**
	 * 查看单个物料标识规则的工厂
	 * 
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	//还没用到
	public MaterielRule findMaterielRuleByName(String name, Connection con)
			throws SQLException {
		IDAO_MaterielRule dao = (IDAO_MaterielRule) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_MaterielRule.class);
		Statement stmt = con.createStatement();
		log.debug("通过物料标识规则名查询物料标识规则SQL语句:"+dao.findMainRuleByName(name));
		ResultSet rs = stmt.executeQuery(dao.findMainRuleByName(name));
		MaterielRule materielRule =  null;
		if(rs.next()){
			materielRule = new MaterielRule();
			materielRule.setId(rs.getInt("int_id"));
			materielRule.setName(rs.getString("str_name"));
			materielRule.setValidate(rs.getString("str_validateclass"));
			log.debug("物料的验证规则号："+rs.getInt("int_id")+"；物料的验证规则名："+rs.getString("str_name")+
					"；物料的验证规则字符串："+rs.getString("str_validateclass"));
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		return materielRule;
	}
}
