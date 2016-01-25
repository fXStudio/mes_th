package mes.pm.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mes.framework.DataBaseType;
import mes.pm.bean.ExceptionRecord;
import mes.pm.dao.DAO_ExceptionRecord;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 异常记录工厂类
 * @author Xujia
 *
 */
public class ExceptionRecordFactory {
//	创建日志文件
	private final Log log = LogFactory.getLog(ExceptionRecordFactory.class);
	
	 //通过设备name获取类型名
	public List<String> getdevicetypeBydevicename(String devicename,Connection con)throws SQLException{
        DAO_ExceptionRecord dao = (DAO_ExceptionRecord)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_ExceptionRecord.class);      
        Statement stmt = con.createStatement();        
        log.debug("通过设备name获取类型名: "+dao.getdevicetypeBydevicename(devicename));	
        ResultSet rs=stmt.executeQuery(dao.getdevicetypeBydevicename(devicename));
        List<String> list=new ArrayList<String>();      
        while(rs.next()){
        	if(list.contains(rs.getString("str_statename")))
        	    continue;
              list.add(rs.getString("str_statename"));          
        }       
        log.debug("设备类型名: "+list);	
        if(stmt!=null){
     	   stmt.close();     	
        }
       if(con!=null){
    	   con=null;
       }
        return list;
	}
	
	/**  徐嘉
	 * 上报异常
	 * @param ExceptionRecord
	 * @param con
	 * @throws SQLException
	 */
	public void createExceptionRecord(ExceptionRecord exRecord,Connection con) throws SQLException{
		DAO_ExceptionRecord dao = (DAO_ExceptionRecord)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_ExceptionRecord.class);   
		Statement stmt = con.createStatement();
		log.debug("上报异常: "+dao.saveExceptionRecord(exRecord));
		stmt.execute(dao.saveExceptionRecord(exRecord));		
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
	}
	 /**  徐嘉
	 * 关闭异常
	 * @param ExceptionType
     * @param con  	
     *                 
	 */
	public void colseExceptionRecord(ExceptionRecord exRecord, Connection con)
			throws SQLException {
		DAO_ExceptionRecord dao = (DAO_ExceptionRecord)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_ExceptionRecord.class);   
		Statement stmt = con.createStatement();
		log.debug("关闭异常 "+dao.colseExceptionRecord(exRecord));
		stmt.execute(dao.colseExceptionRecord(exRecord));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	/**
	 * 通过ID查询异常记录	   
	 * @param id
	 *            设备号
	 * @param con
	 *            连接对象
	 * @return 通过ID查询出的指令对象
	 * @throws java.sql.SQLException
	 */
	public ExceptionRecord getExceptionRecordById(int id, Connection con)
			throws SQLException, ParseException {
		ExceptionRecord d = new ExceptionRecord();
		DAO_ExceptionRecord dao = (DAO_ExceptionRecord)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_ExceptionRecord.class);   
		Statement stmt = con.createStatement();
		log.debug("通过ID查询异常记录SQL：" + dao.getExceptionRecordById(id));
		ResultSet rs = stmt.executeQuery(dao.getExceptionRecordById(id));
		if (rs.next()) {
		    d.setDescription(rs.getString("Str_description"));
		    d.setDeviceid(rs.getInt("int_deviceid"));
			d.setDevicetypeid(rs.getInt("int_devicetypeid"));
			d.setClose(rs.getString("dat_close") == null ? null
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(rs.getString("dat_close")));
			d.setCloseuser(rs.getInt("int_closeuserid"));
			d.setExceptionCauseId(rs.getInt("int_exceptioncause"));
			d.setExceptionTypeId(rs.getInt("int_exceptiontype"));
			d.setId(rs.getInt("int_id"));
			d.setProduceUnitId(rs.getInt("int_produceunit"));
			d.setRediscription(rs.getString("str_rediscription"));
			d.setStart(rs.getString("dat_start") == null ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			          .parse(rs.getString("dat_start")));
			d.setState(rs.getInt("int_state"));
			d.setUserId(rs.getInt("int_userid"));
		}
		if (stmt != null)
			stmt.close();
		return d;
	}
	 /**  徐嘉
	 * 更新异常记录
	 * @param ExceptionType
     * @param con  	
     *                 
	 */
	public void updateExceptionRecord(ExceptionRecord exRecord, Connection con)
			throws SQLException {
		DAO_ExceptionRecord dao = (DAO_ExceptionRecord)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_ExceptionRecord.class);   
		Statement stmt = con.createStatement();
		log.debug("更新异常记录: "+dao.updateExceptionRecord(exRecord));
		stmt.execute(dao.updateExceptionRecord(exRecord));       
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
	}
	

}
