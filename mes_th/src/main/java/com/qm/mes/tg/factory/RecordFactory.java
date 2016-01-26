package com.qm.mes.tg.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.system.dao.DAOFactoryAdapter;
import com.qm.mes.tg.bean.GatherRecord;
import com.qm.mes.tg.bean.MaterielRule;
import com.qm.mes.tg.bean.PedigreeRecord;
import com.qm.mes.tg.dao.IDAO_Record;

public class RecordFactory {
	
	//日志
	private final Log log = LogFactory.getLog(RecordFactory.class);
	
	/**
	 * 采集数据保存
	 * @param gr 过点记录对象
	 * @param prs 采集的数据列表，第一位是主物料的值，依次向下是子物料的值
	 * @param mrs 验证规则列表，第一位是主物料的验证规则，依次向下是子物料的验证规则
	 * @param con 
	 * @throws SQLException 
	 */
	public synchronized void saveRecord(GatherRecord gr,List<String> prs,List<MaterielRule> mrs,Connection con) throws SQLException{
		IDAO_Record dao_record = (IDAO_Record) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Record.class);
		
		Statement stmt = con.createStatement();
		//gr.setMaterielValue(prs.get(0));
		//gr.setMaterielValue(mrs.get(0).getName());
		//查询是过点信息是否存在	
		//by cp
		
//		System.out.println(dao_record.getGatherRecordId(gr));
//		System.out.println(gr.getMaterielValue());
		ResultSet rs=null;
		
		/*
		ResultSet rs = stmt.executeQuery(dao_record.getGatherRecordId(gr));
		//如果存在过点信息，抛出异常,程序结束
		if(rs.next()){
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}
			throw new SQLException("数据已存在");
		}
		end by cp*/
		boolean isReleaseLock = false;//事务标识
		try {
			//检测是否启用事务，如果事务没有启动则启动事务并将标识设为true
			if (con.getAutoCommit()) { 
				isReleaseLock = true;
				con.setAutoCommit(false);
			}
			//记录过点信息
			log.debug("创建记录过点信息SQL语句："+dao_record.saveGatherRecord(gr));
			stmt.execute(dao_record.saveGatherRecord(gr));
			//取得刚刚存入过点信息的id
			log.debug("通过过点记录对象取得过点记录号SQL语句："+dao_record.getGatherRecordId(gr));
			rs = stmt.executeQuery(dao_record.getGatherRecordId(gr));
			rs.next();
			int id = rs.getInt("int_id");
			//将谱系记录存入谱系表中
			for(int i=1;i<prs.size();i++){
				String[] i_v = prs.get(i).split(":");
				log.debug("将谱系记录存入谱系表中SQL语句："+dao_record.savePedigreeRecord(id,i_v[1], mrs.get(Integer.parseInt(i_v[0]) + 1).getName()));
				stmt.execute(dao_record.savePedigreeRecord(id,i_v[1], mrs.get(Integer.parseInt(i_v[0]) + 1).getName()));
			}
			//检测我们是否启用事务，如果事务被我们启用，将提交
			if(isReleaseLock){
				con.commit();
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(stmt!=null){
				stmt.close();
				rs=null;
			}
			//检测我们是否启用事务
			if (isReleaseLock){
				con.setAutoCommit(true);
			}
		}
	}
	/**
	 * 作者:谢静天
	 * 更新采集点主物料的值
	 * @param GatherRecord
	 * @param con
	 * @throws SQLException
	 * 在gatherRecord_updating.jsp 页面用到
	 * gatherRecord_editing.jsp 页面用到
	 */
	public synchronized void upDateRecord(GatherRecord gr,Connection con)  throws SQLException{
		Statement stmt = null;
		try{
		IDAO_Record dao_update = (IDAO_Record) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Record.class);
		stmt = con.createStatement();
		log.debug("更新采集点主物料值SQL语句："+dao_update.upDateGatherRecord(gr.getId(), gr.getMaterielValue()));
		stmt.execute(dao_update.upDateGatherRecord(gr.getId(), gr.getMaterielValue()));		
		}
		catch(SQLException e){
			throw e;
		}finally{
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}
		}
	}
	/**
	 * 作者:谢静天
	 * 更新采集点谱系子物料的值
	 * @param GatherRecord
	 * @param con
	 * @throws SQLException
	 * 在gatherRecord_updating.jsp 页面用到
	 * gatherRecord_editing.jsp 页面用到
	 */
	public synchronized void upDatePedigreeRecord(PedigreeRecord pr,Connection con) throws SQLException{
		try{
		IDAO_Record dao_updatePedigreeRecord = (IDAO_Record) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Record.class);
		Statement stmt = con.createStatement();
		log.debug("更新采集点谱系子物料值SQL语句："+dao_updatePedigreeRecord.upDatePedigreeRecord(pr.getId(), pr.getMaterielValue()));
		stmt.execute(dao_updatePedigreeRecord.upDatePedigreeRecord(pr.getId(), pr.getMaterielValue()));
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		}
		catch(SQLException e){
			throw e;
		}
	}
	  
    /**
	 *   修改谱系历史记录
	 * 谢静天 
	 * @param gatherrecordid
	 *           原来记录id
	 * @param   原来记录的值
	 *  @param  添加的原因
	 * @param  用户的id
	 * @param con
	 *  @throws SQLException
	 *  
	 */
	
	public synchronized void  savePEDIGREEHISTORY(int origid,String origvalue,String cause,String userid,Connection con) throws SQLException{
		try{
			IDAO_Record dao_savePEDIGREEHISTORY = (IDAO_Record) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), IDAO_Record.class);
			Statement stmt = con.createStatement();
			log.debug("修改谱系历史记录SQL语句："+dao_savePEDIGREEHISTORY.savePEDIGREEHISTORY(origid, origvalue, cause, userid));
			stmt.execute(dao_savePEDIGREEHISTORY.savePEDIGREEHISTORY(origid, origvalue, cause, userid));
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}	
		}
			catch(SQLException e){
				throw e;
			}
	}
	
	/**
     * 查询物料标识规则名在谱系表中的数量
     * 
     * @author YuanPeng
     * @param MaterielRuleName 物料标识规则名
     * @return 数量
     */
    public synchronized int countByMaterielRuleName(String MaterielRuleName , Connection con){
    	int count = 0;
    	try{
			IDAO_Record dao_record = (IDAO_Record) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), IDAO_Record.class);
			Statement stmt = con.createStatement();
			log.debug("查询物料标识规则名在谱系表中的数量SQL语句："+dao_record.countByMaterielRuleName(MaterielRuleName));
			ResultSet rs = stmt.executeQuery(dao_record.countByMaterielRuleName(MaterielRuleName));
			if(rs.next()){
				count = rs.getInt(1);
				log.debug("查询物料标识规则名在谱系表中的数量为："+count);
			}
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}
    	}
			catch(Exception e){
				e.printStackTrace();
			}
			return count;
    }
    
    /**
     * 查询物料标识规则名在过点记录表中的数量
     * 
     * @author YuanPeng
     * @param MaterielRuleName 物料标识规则名
     * @return 数量
     */
    public synchronized int countGatherReByMaterielRuleName(String MaterielRuleName , Connection con){
    	int count = 0;
    	try{
			IDAO_Record dao_record = (IDAO_Record) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), IDAO_Record.class);
			Statement stmt = con.createStatement();
			log.debug("查询物料标识规则名在过点记录表中的数量"+dao_record.countGatherReByMaterielRuleName(MaterielRuleName));
			ResultSet rs = stmt.executeQuery(dao_record.countGatherReByMaterielRuleName(MaterielRuleName));
			if(rs.next()){
				count = rs.getInt(1);
				log.debug("查询物料标识规则名在过点记录表中的数量为："+count);
			}
			if(stmt!=null){
				stmt.close();
				stmt=null;
			}
    	}
			catch(Exception e){
				e.printStackTrace();
			}
			return count;
    }
    
    /**
     * 	通过ID查询谱系对象
     * @author YuanPeng
     * @param id
     * @return	谱系对象
     */
    public PedigreeRecord getPedigreeRecordById(int id,Connection con){
    	PedigreeRecord pedigreeRecord = null;
    	Statement stmt = null;
    	try{
    		pedigreeRecord = new PedigreeRecord();
			IDAO_Record dao_record = (IDAO_Record) DAOFactoryAdapter.getInstance(
					DataBaseType.getDataBaseType(con), IDAO_Record.class);
			stmt = con.createStatement();
			log.debug("通过过点记录号查询过点记录对象SQL语句:"+dao_record.getPedigreeRecordById(id));
			ResultSet rs = stmt.executeQuery(dao_record.getPedigreeRecordById(id));
			if(rs.next()){
				pedigreeRecord.setId(rs.getInt("INT_ID"));
				pedigreeRecord.setGatherRecordId(rs.getInt("INT_GATHERRECORDID"));
				pedigreeRecord.setMaterielValue(rs.getString("STR_MATERIELVALUE"));
				pedigreeRecord.setMaterielName(rs.getString("STR_MATERIELNAME"));
				log.debug("谱系号："+rs.getInt("INT_ID")+"；过点记录号："+rs.getInt("INT_GATHERRECORDID")+"；物料值："+rs.getString("STR_MATERIELVALUE")+"；物料名："+rs.getString("STR_MATERIELNAME"));
			}
			
    	}
			catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(stmt!=null)stmt.close();
				}catch(SQLException sqle){
					sqle.printStackTrace();}
			}
			return pedigreeRecord;
    }
	
}
