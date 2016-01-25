package mes.ra.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mes.framework.DataBaseType;
import mes.ra.bean.*;
import mes.ra.dao.*;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WorkTOFactory {
	//日志
	private final Log log = LogFactory.getLog(WorkTOFactory.class);
	
	/**
	 * 添加班组班次记录
	 * 包金旭
	 * @param workto
	 * @param con
	 * @throws SQLException
	 */
	public void saveWorkTO(WorkTO workto,Connection con)throws SQLException{
        DAO_WorkTO dao_workto = (DAO_WorkTO)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_WorkTO.class);
        Statement stmt = con.createStatement();
        log.debug("添加班组班次记录SQL语句："+dao_workto.saveWorkTO(workto));
        stmt.executeUpdate(dao_workto.saveWorkTO(workto));
        if(stmt!=null){
     	   stmt.close();
        }
    }
	
	/** 包金旭
	 * 通过序号获得班组班次记录
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public WorkTO getWorkTObyId(int int_id,Connection con) throws SQLException{
    	DAO_WorkTO dao_workto = (DAO_WorkTO) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_WorkTO.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号获得班次记录SQL语句："+dao_workto.getWorkTOById(int_id));
		WorkTO workto=new WorkTO();
		ResultSet rs=stmt.executeQuery(dao_workto.getWorkTOById(int_id));
		log.debug("通过序号获得班次记录列表---");
		while(rs.next()){
			workto.setId(rs.getInt(1));
			workto.setProdunitid(rs.getInt(2));
			workto.setWorkOrder(rs.getString(3));
			log.debug("序号："+rs.getInt(1)+"；生产单元号"+rs.getInt(2)+"；班次："+rs.getString(3));	
		}
		if(stmt!=null){
			stmt.close();
		}
		return workto;
    }
	
	/**
	 * 包金旭
	 * 修改工作时刻表
	 * @param workto
	 * @param con
	 * @throws SQLException
	 */
	public void  updateWorkTO(WorkTO workto,Connection con) throws SQLException{
	  	DAO_WorkTO dao_workto = (DAO_WorkTO) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_WorkTO.class);
		Statement stmt = con.createStatement();
		log.debug("修改工作时刻表SQL语句："+dao_workto.updateWorkTO(workto));
		stmt.executeUpdate(dao_workto.updateWorkTO(workto));
		if(stmt!=null){
			stmt.close();
		}
	  }
	
	
	/** 包金旭
	 * 通过序号删除工作时刻表
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public void deleteWorkTOById(int id, Connection con) throws SQLException {
		DAO_WorkTO dao_workto = (DAO_WorkTO) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_WorkTO.class);
		Statement stmt = con.createStatement();
		stmt.execute(dao_workto.deleteWorkTOById(id));
		log.debug("通过通过序号删除工作时刻表SQL语句："+dao_workto.deleteWorkTOById(id));
		
		if (stmt != null) {
			stmt.close();
		}
	}	
    /**
     * 查询所有的生产单元号，验证创建时生产单元，班组，班次唯一 包金旭
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getProdunitid(Connection con) throws SQLException{
    	DAO_WorkTO dao_workto = (DAO_WorkTO) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkTO .class);
		Statement stmt = con.createStatement();
		log.debug("查询所有的生产单元号SQL语句："+dao_workto.getprodunitid());
		ResultSet rs=stmt.executeQuery(dao_workto.getprodunitid());
		List<String>  id=new ArrayList<String>();
		while(rs.next()){
			String produnitid=rs.getString("int_produnitid").trim();
			log.debug("生产单元号："+rs.getString("int_produnitid"));
			id.add(produnitid);
		}
		if(stmt!=null){
			stmt.close();
		}
    	return id;
    }

    /**
     * * 查询所有的班次，验证创建时生产单元，班组，班次唯一
	 * author 包金旭
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getWorkOrder(Connection con) throws SQLException{
    	DAO_WorkTO dao_workto = (DAO_WorkTO) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_WorkTO .class);
		Statement stmt = con.createStatement();
		log.debug("查询所有的班次SQL语句："+dao_workto.getworkOrder());
		ResultSet rs=stmt.executeQuery(dao_workto.getworkOrder());
		List<String>  order=new ArrayList<String>();
		while(rs.next()){
			String workorder=rs.getString("str_workOrder").trim();
			log.debug("班次："+rs.getString("str_workOrder"));
			order.add(workorder);
		}
		if(stmt!=null){
			stmt.close();
		}
    	return order;
    }
    /**
     * * 除去本ID查询获得工作时刻表中生产单元号用于更改判断
	 * author : 包金旭
     * @param int_id
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getProdunitidById(int int_id,Connection con) throws SQLException{
    	DAO_WorkTO dao_workto = (DAO_WorkTO) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkTO .class);
		Statement stmt = con.createStatement();
		log.debug("除去本ID查询获得工作时刻表中生产单元号SQL语句："+dao_workto.getprodunitidById(int_id));
		ResultSet rs=stmt.executeQuery(dao_workto.getprodunitidById(int_id));
		List<String>  id=new ArrayList<String>();
		while(rs.next()){
			String produnitid=rs.getString("int_produnitid").trim();
			log.debug("生产单元号："+rs.getString("int_produnitid"));
			id.add(produnitid);
		}
		if(stmt!=null){
			stmt.close();
		}
    	return id;
    }
    /**
     * 除去本ID查询获得工作时刻表中班次用于更改判断
	 * author : 包金旭
     * @param int_id
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getWorkOrderById(int int_id,Connection con) throws SQLException{
    	DAO_WorkTO dao_workto = (DAO_WorkTO) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_WorkTO.class);
		Statement stmt = con.createStatement();
		log.debug("除去本ID查询获得工作时刻表中班次SQL语句："+dao_workto.getworkOrderById(int_id));
		ResultSet rs=stmt.executeQuery(dao_workto.getworkOrderById(int_id));
		List<String>  order=new ArrayList<String>();
		while(rs.next()){
			String workorder=rs.getString("str_workOrder").trim();
			log.debug("班次："+rs.getString("str_workOrder"));
			order.add(workorder);
		}
		if(stmt!=null){
			stmt.close();
		}
    	return order;
    }
	/**通过生产单元的id来获取班次信息 谢静天
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<String> getworkOrderByprodunitid(int id,Connection con)throws SQLException{
		DAO_WorkTO dao_workto = (DAO_WorkTO)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_WorkTO.class);
       Statement stmt = con.createStatement();
       log.debug("通过生产单元的id来获取班次SQL语句："+dao_workto.getworkOrderByprodunitid(id));
       ResultSet rs=stmt.executeQuery(dao_workto.getworkOrderByprodunitid(id));
        List<String> list=new ArrayList<String>();
      
        while(rs.next()){
        	if(list.contains(rs.getString("str_workOrder")))
        	    continue;
        	
        	log.debug("班次："+rs.getString("str_workOrder"));
        	list.add(rs.getString("str_workOrder"));    
        } 
       if(stmt!=null){
     	   stmt.close();
        }
        return list;
	}
   
    
   
	
	
}
