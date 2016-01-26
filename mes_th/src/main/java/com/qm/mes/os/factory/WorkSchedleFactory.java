package com.qm.mes.os.factory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.os.bean.*;
import com.qm.mes.os.dao.*;
import com.qm.mes.system.dao.DAOFactoryAdapter;
public class WorkSchedleFactory {
	private final Log log = LogFactory.getLog(WorkSchedleFactory.class);
	
	/**添加工作时刻表 
	 * @param workschedle
	 * @param con
	 * @throws SQLException
	 * * author : 包金旭
	 */
	public void saveWorkSchedle(WorkSchedle workschedle,Connection con)throws SQLException{
        DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_WorkSchedle.class);
        Statement stmt = con.createStatement();
        log.debug("添加工作时刻表"+dao_workschedle.saveWorkSchedle(workschedle) );
        stmt.executeUpdate(dao_workschedle.saveWorkSchedle(workschedle));
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
	public void deleteWorkSchedleById(int id, Connection con) throws SQLException {
		DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_WorkSchedle.class);
		Statement stmt = con.createStatement();
		log.debug("通过序号删除工作时刻表"+dao_workschedle.deleteWorkSchedleById(id));
		stmt.execute(dao_workschedle.deleteWorkSchedleById(id));
		if (stmt != null) {
			stmt.close();
		}
		
		
	}
	
	/** 包金旭
	 * 通过序号获得工作时刻表
	 * @param id
	 * @param con
	 * @throws SQLException 
	 */
	public WorkSchedle getWorkSchedlebyId(int int_id,Connection con) throws SQLException{
    	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle.class);
		Statement stmt = con.createStatement();
		WorkSchedle workschedle=new WorkSchedle();
		log.debug("通过序号获得工作时刻表"+dao_workschedle.getWorkSchedleById(int_id) );
		ResultSet rs=stmt.executeQuery(dao_workschedle.getWorkSchedleById(int_id));
		while(rs.next()){
			workschedle.setId(rs.getInt("INT_ID"));
			workschedle.setProdunitid(rs.getInt("INT_PRODUNITID"));
			workschedle.setWorkOrder(rs.getString("STR_WORKORDER"));
			workschedle.setWorkSchedle(rs.getString("STR_WORKSCHEDLE"));
			workschedle.setAdvanceTime(rs.getString("STR_ADVANCETIME"));
		}
		if(stmt!=null){
			stmt.close();
		}
		
		return workschedle;
    }
	/**包金旭
	 * 修改工作时刻表
	 * @param workschedle
	 * @param con
	 * @throws SQLException
	 */
	public void  updateWorkSchedle(WorkSchedle workschedle,Connection con) throws SQLException{
	  	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle.class);
		Statement stmt = con.createStatement();
		log.debug("修改工作时刻表"+dao_workschedle.updateWorkSchedle(workschedle));
		stmt.executeUpdate(dao_workschedle.updateWorkSchedle(workschedle));
		if(stmt!=null){
			stmt.close();
		}
		
	  }
	
    /**查询所有的生产单元号，验证创建时生产单元，班组，班次唯一
	 * author 包金旭
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getProdunitid(Connection con) throws SQLException{
    	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle .class);
		Statement stmt = con.createStatement();
		log.debug("查询所有的生产单元号，验证创建时生产单元，班组，班次唯一"+dao_workschedle.getprodunitid());
		ResultSet rs=stmt.executeQuery(dao_workschedle.getprodunitid());
		
		List<String>  id=new ArrayList<String>();
		while(rs.next()){
			String produnitid=rs.getString("int_produnitid").trim();
			id.add(produnitid);
		}
		if(stmt!=null){
			stmt.close();
		}
		
    	return id;
    	
    }
    
    /**查询所有的班次，验证创建时生产单元，班组，班次唯一
	 * author 包金旭
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getWorkOrder(Connection con) throws SQLException{
    	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con),DAO_WorkSchedle .class);
		Statement stmt = con.createStatement();
		log.debug("查询所有的班次，验证创建时生产单元，班组，班次唯一"+dao_workschedle.getworkOrder());
		ResultSet rs=stmt.executeQuery(dao_workschedle.getworkOrder());
		List<String>  order=new ArrayList<String>();
		while(rs.next()){
			String workorder=rs.getString("str_workOrder").trim();
			order.add(workorder);
		}
		if(stmt!=null){
			stmt.close();
		}
		
    	return order;
    }
    
    /**除去本ID查询获得工作时刻表中生产单元号用于更改判断
	 * author : 包金旭
     * @param int_id
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getProdunitidById(int int_id,Connection con) throws SQLException{
    	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle .class);
		Statement stmt = con.createStatement();
		log.debug("除去本ID查询获得工作时刻表中生产单元号用于更改判断"+dao_workschedle.getprodunitidById(int_id));
		ResultSet rs=stmt.executeQuery(dao_workschedle.getprodunitidById(int_id));
		
		List<String>  id=new ArrayList<String>();
		while(rs.next()){
			String produnitid=rs.getString("int_produnitid").trim();
			id.add(produnitid);
		}
		if(stmt!=null){
			stmt.close();
			
		}
		
    	return id;
    }
    
    /**除去本ID查询获得工作时刻表中班次用于更改判断
	 * author : 包金旭
     * @param int_id
     * @param con
     * @return
     * @throws SQLException
     */
    public List<String> getWorkOrderById(int int_id,Connection con) throws SQLException{
    	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle .class);
		Statement stmt = con.createStatement();
		log.debug("除去本ID查询获得工作时刻表中班次用于更改判断"+dao_workschedle.getworkOrderById(int_id));
		ResultSet rs=stmt.executeQuery(dao_workschedle.getworkOrderById(int_id));
		
		List<String>  order=new ArrayList<String>();
		while(rs.next()){
			String workorder=rs.getString("str_workOrder").trim();
			order.add(workorder);
		}
		if(stmt!=null){
			stmt.close();
		}
		
    	return order;
    }
    
	/**通过ID查询生产单元班次用于删除工作时刻表做判断
	 * author : 包金旭
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public WorkSchedle getProdunitidOrderById(int id,Connection con)throws SQLException{
		DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle.class);
		Statement stmt = con.createStatement();
		WorkSchedle workschedle=new WorkSchedle();
		log.debug("通过ID查询生产单元班次用于删除工作时刻表做判断"+dao_workschedle.getProdunitidOrderById(id));
		ResultSet rs=stmt.executeQuery(dao_workschedle.getProdunitidOrderById(id));
		
		while(rs.next()){
			workschedle.setProdunitid(rs.getInt("INT_PRODUNITID"));
			workschedle.setWorkOrder(rs.getString("STR_WORKORDER"));
		}
		if(stmt!=null){
			stmt.close();
		}
		
		return workschedle;
    }

	/**通过生产单元的id来获取班次信息 谢静天
	 * @param id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<String> getworkOrderByprodunitid(int id,Connection con)throws SQLException{
        DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_WorkSchedle.class);
        Statement stmt = con.createStatement();
        log.debug("通过生产单元的id来获取班次信息"+dao_workschedle.getworkOrderByprodunitid(id));
        ResultSet rs=stmt.executeQuery(dao_workschedle.getworkOrderByprodunitid(id));
        
        List<String> list=new ArrayList<String>();
      
        while(rs.next()){
        	if(list.contains(rs.getString("str_workOrder")))
        	    continue;
              list.add(rs.getString("str_workOrder"));
        }
        if(stmt!=null){
     	   stmt.close();
        }
        
        return list;
	}
	
	/**通过生产单元班次来查询开工时间和提前期  谢静天
	 * @param produnitid
	 * @param dat_producedate
	 * @param workorder
	 * @param con
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public long getworkschedleadtime(int produnitid,String dat_producedate,String workorder,Connection con)throws SQLException,ParseException{
        DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_WorkSchedle.class);
        Statement stmt = con.createStatement();
        //log.debug("通过生产单元班次来查询开工时间和提前期"+dao_workschedle.getworkschedleadtime(produnitid, workorder));
        ResultSet rs=stmt.executeQuery(dao_workschedle.getworkschedleadtime(produnitid, workorder));
        String[] a=new String[2];
        String[] b=new String[3];
        String workSchedle="";
        String advanceTime="";
      
        if(rs.next()){
        	workSchedle=rs.getString("str_workSchedle");
        	advanceTime=rs.getString("str_advanceTime");
	    }
        Calendar calendar=Calendar.getInstance();
        //获取生产时间
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dat_producedate));
        a=workSchedle.split(":");
        b=advanceTime.split(":");
        long timedate= calendar.getTimeInMillis()+(Integer.parseInt(a[0])*60+Integer.parseInt(a[1]))*60*1000;
        int  hour=Integer.parseInt(b[0])*24+Integer.parseInt(b[1]);
        int minunit=Integer.parseInt(b[2]);
        long timeschedle=hour*60*60*1000+minunit*60*1000;
        //当前时间
        calendar.setTime(new java.util.Date());
        long nowtime=calendar.getTimeInMillis();
        // 当生产时间-工作时刻表小于系统时间的话表示已经过事实锁定期
        long t= timedate-timeschedle;
        if(t<=nowtime){
        	t=0;
        }
        if(stmt!=null){
      	   stmt.close();
        }
       
	    return t; 
	}

	/**核对是否存在生产单元班次的工作时刻表  谢静天
	 * @param produnitid
	 * @param workorder
	 * @param con
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public boolean checkworkOrderProduce(int produnitid,String workorder,Connection con)throws SQLException,ParseException{
        DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_WorkSchedle.class);
        Statement stmt = con.createStatement();
        log.debug("核对是否存在生产单元班次的工作时刻表"+dao_workschedle.getworkschedleadtime(produnitid,  workorder));
        ResultSet rs=stmt.executeQuery(dao_workschedle.getworkschedleadtime(produnitid,  workorder));
        boolean f=false;
	    if(rs.next()){
	    	f=true;
	    }
	    if(stmt!=null){
	    	stmt.close();
	    }
	   
	    return f;
	
	}
	
	/**通过生产单元班次查询工作时刻表用于删除班次做判断
	 * author : 包金旭
	 * @param Produnitid
	 * @param Order
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public boolean getSchedleByProdunitidOrder(int Produnitid,String Order,Connection con)throws SQLException{
		DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle.class);
		Statement stmt = con.createStatement();
		boolean f=false;
		log.debug("通过生产单元班次查询工作时刻表用于删除班次做判断"+dao_workschedle.getSchedleByProdunitidOrder(Produnitid, Order));
		ResultSet rs=stmt.executeQuery(dao_workschedle.getSchedleByProdunitidOrder(Produnitid, Order));
		if(rs.next()){
		     f=true;
			}
		if(stmt!=null){
			stmt.close();
		}
		
		return f;
    }
	
	/**通过生产单元查询开工时间
	 * author : 包金旭
	 * @param str_produceunit
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<String> getWorkSchedelByProuunitid(int str_produceunit,Connection con) throws SQLException{
    	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle .class);
		Statement stmt = con.createStatement();
		log.debug("通过生产单元查询开工时间"+dao_workschedle.getWorkSchedelByProuunitid(str_produceunit));
		ResultSet rs=stmt.executeQuery(dao_workschedle.getWorkSchedelByProuunitid(str_produceunit));
		List<String> sch=new ArrayList<String>();
		while(rs.next()){
			String workschedel=rs.getString("str_workschedle");
			sch.add(workschedel);
		}
		if(stmt!=null){
			stmt.close();
		}
		
    	return sch;
    	
    }
	
	/**除本ID通过生产单元查询开工时间
	 * author : 包金旭
	 * @param str_produceunit
	 * @param int_id
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<String> getWorkSchedelByProuunitidAndID(int str_produceunit,int int_id,Connection con) throws SQLException{
    	DAO_WorkSchedle dao_workschedle = (DAO_WorkSchedle) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_WorkSchedle .class);
		Statement stmt = con.createStatement();
		log.debug("除本ID通过生产单元查询开工时间"+dao_workschedle.getWorkSchedelByProuunitidAndID(str_produceunit,int_id));
		ResultSet rs=stmt.executeQuery(dao_workschedle.getWorkSchedelByProuunitidAndID(str_produceunit,int_id));
		List<String> sch=new ArrayList<String>();
		while(rs.next()){
			String workschedel=rs.getString("str_workschedle");
			sch.add(workschedel);
		}
		if(stmt!=null){
			stmt.close();
		}
		
    	return sch;
    	
    }
	
	
}
