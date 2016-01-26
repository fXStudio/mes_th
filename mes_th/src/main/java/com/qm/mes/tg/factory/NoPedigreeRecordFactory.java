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
import com.qm.mes.tg.bean.NoPedigreeRecord;
import com.qm.mes.tg.dao.IDAO_NoPedigreeRecord;

public class NoPedigreeRecordFactory {

	//日志
	private final Log log = LogFactory.getLog(NoPedigreeRecordFactory.class);
	
	/**
	 * 创建NoPedigreeRecord
	 * @param NoPedigreeRecord
	 * @param con
	 * @throws SQLException
	 */
	public void saveNoPedigreeRecord (NoPedigreeRecord npgr,Connection con) 
	throws SQLException {
		IDAO_NoPedigreeRecord dao__NoPedigreeRecord  = (IDAO_NoPedigreeRecord ) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				IDAO_NoPedigreeRecord.class);
      Statement stmt = con.createStatement();
      log.debug("创建非谱系记录的SQL语句："+dao__NoPedigreeRecord.saveNoPedigreeRecord(npgr));
     stmt.execute(dao__NoPedigreeRecord.saveNoPedigreeRecord(npgr));
    if (stmt != null) {
	stmt.close();
	stmt = null;
    }
	}
	
	/**
	 * 得到NoPedigreeRecord
	 * @param 通过id
	 * @param con
	 * @throws SQLException
	 */
	public NoPedigreeRecord getNoPedigreeRecordById(int id,Connection con) 
	throws SQLException{
		IDAO_NoPedigreeRecord dao__NoPedigreeRecord  = (IDAO_NoPedigreeRecord ) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				IDAO_NoPedigreeRecord.class);
      Statement stmt = con.createStatement();
      log.debug("通过序号查询非谱系记录的SQL语句："+dao__NoPedigreeRecord.getNoPedigreeRecordById(id));
ResultSet rs = stmt.executeQuery(dao__NoPedigreeRecord.getNoPedigreeRecordById(id));
NoPedigreeRecord gi = null;
	log.debug("通过序号查询非谱系记录--");
if (rs.next()) {
	gi = new  NoPedigreeRecord();
	gi.setId(rs.getInt("int_id"));
	gi.setGatherRecordId(rs.getInt("int_gatherrecord_id"));
	gi.setValue(rs.getString("str_value"));
	gi.setGatheritemextname(rs.getString("str_gatheritemextname"));
	log.debug("非谱系记录号："+rs.getInt("int_id")+"；过点记录号："+rs.getInt("int_gatherrecord_id")+
			"；非谱系扩展属性值："+rs.getString("str_value")+"；采集点扩展属性名："+rs.getString("str_gatheritemextname"));
   }
if(stmt!=null){
	stmt.close();
	stmt=null;
}
return gi;
	}
	/**
	 * 得到谱系查询的属性名和值
	 * @param 通过主物料值 materielvalue
	 * @param con
	 * @throws SQLException
	 * 在productRecord.jsp 页面用到
	 */
	public  String  getNoPedigreeRecordStrnameandvalue(String materielvalue,Connection con) 
	throws SQLException{
         IDAO_NoPedigreeRecord dao__NoPedigreeRecord  = (IDAO_NoPedigreeRecord ) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				IDAO_NoPedigreeRecord.class);
      Statement stmt = con.createStatement();
      log.debug("通过物料值查询非谱系记录属性名和值的SQL语句："+dao__NoPedigreeRecord.getNoPedigreeRecordByMaterielValue(materielvalue));
      ResultSet rs = stmt.executeQuery(dao__NoPedigreeRecord.getNoPedigreeRecordByMaterielValue(materielvalue));
      String output1="";
      log.debug("通过物料值查询非谱系记录属性名和值---");
      while(rs.next()){
 
      output1+=rs.getString("str_gatheritemextname");
      output1+=":";
      output1+=rs.getString("str_value"); 
      output1+="  ";
      log.debug("采集点扩展属性名："+rs.getString("str_gatheritemextname")+";非谱系扩展属性值:"+rs.getString("str_value"));
     }
     if(rs!=null){
       rs.close();
       rs=null;
       }
    if(stmt!=null){
    stmt.close();
    stmt=null;
      }
     return output1;
   }
	
    //如果主物料唯一那么就不用List列表。
	/**
	 * 得到NoPedigreeRecord列表属性
	 * @param 通过主物料值 materielvalue
	 * @param con
	 * @throws SQLException
	 * 在gatherRecord_edit.jsp 用到
	 */
	public  List<NoPedigreeRecord> getNoPedigreeRecordBygatherid(int id,Connection con) 
	throws SQLException{
		List<NoPedigreeRecord> list = new ArrayList<NoPedigreeRecord>();
		IDAO_NoPedigreeRecord dao__NoPedigreeRecord  = (IDAO_NoPedigreeRecord ) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				IDAO_NoPedigreeRecord.class);
      Statement stmt = con.createStatement();
      log.debug("通过序号得到非谱系记录列表"+dao__NoPedigreeRecord.getNoPedigreeRecordBygatherid(id));
      ResultSet rs = stmt.executeQuery(dao__NoPedigreeRecord.getNoPedigreeRecordBygatherid(id));
      NoPedigreeRecord gi = null;
      log.debug("通过序号得到非谱系记录---");
     while(rs.next()) {
	gi = new  NoPedigreeRecord();
	gi.setId(rs.getInt("int_id"));
	gi.setGatherRecordId(rs.getInt("int_gatherrecord_id"));
	gi.setValue(rs.getString("str_value"));
	gi.setGatheritemextname(rs.getString("str_gatheritemextname"));
	log.debug("非谱系记录号："+rs.getInt("int_id")+"；过点记录号："+rs.getInt("int_gatherrecord_id")+"采集点扩展属性名："
			+rs.getString("str_gatheritemextname")+";非谱系扩展属性值:"+rs.getString("str_value"));
	list.add(gi);
            }
        if(rs!=null){
	       rs.close();
	        rs=null;
            }
        if(stmt!=null){
	     stmt.close();
	     stmt=null;
              }
          return list;
    }
	public  List<NoPedigreeRecord> getNoPedigreeRecordByMaterielValue(String materilvalue,Connection con) 
	throws SQLException{
		List<NoPedigreeRecord> list = new ArrayList<NoPedigreeRecord>();
		IDAO_NoPedigreeRecord dao__NoPedigreeRecord  = (IDAO_NoPedigreeRecord ) DAOFactoryAdapter
		.getInstance(DataBaseType.getDataBaseType(con),
				IDAO_NoPedigreeRecord.class);
      Statement stmt = con.createStatement();
      log.debug("通过物料值查询非谱系记录SQL语句："+dao__NoPedigreeRecord.getNoPedigreeRecordByMaterielValue(materilvalue));
  ResultSet rs = stmt.executeQuery(dao__NoPedigreeRecord.getNoPedigreeRecordByMaterielValue(materilvalue));
   NoPedigreeRecord gi = null;
   log.debug("通过物料值查询非谱系记录列表---");
     while(rs.next()) {
	gi = new  NoPedigreeRecord();
	gi.setId(rs.getInt("int_id"));
	gi.setGatherRecordId(rs.getInt("int_gatherrecord_id"));
	gi.setValue(rs.getString("str_value"));
	gi.setGatheritemextname(rs.getString("str_gatheritemextname"));
	log.debug("非谱系记录号："+rs.getInt("int_id")+"；过点记录号："+rs.getInt("int_gatherrecord_id")+"采集点扩展属性名："
			+rs.getString("str_gatheritemextname")+";非谱系扩展属性值:"+rs.getString("str_value"));
	list.add(gi);
            }
        if(rs!=null){
	       rs.close();
	        rs=null;
            }
        if(stmt!=null){
	     stmt.close();
	     stmt=null;
              }
          return list;
    }
	
	
	/**
	 * 更新非谱系记录
	 * @param NoPedigreeRecord
	 * @param con
	 * @throws SQLException
	 * 在gatherRecord_updating.jsp 页面用到
	 * gatherRecord_editing.jsp 页面用到
	 */

	 public void updateNoPedigreeRecord(NoPedigreeRecord npgr,Connection con)throws SQLException {
				IDAO_NoPedigreeRecord dao__NoPedigreeRecord  = (IDAO_NoPedigreeRecord ) DAOFactoryAdapter
				.getInstance(DataBaseType.getDataBaseType(con),
						IDAO_NoPedigreeRecord.class);
		      Statement stmt = con.createStatement();
		      log.debug("更新非谱系记录SQL语句："+dao__NoPedigreeRecord.updateNoPedigreeRecord(npgr));
		    stmt.execute(dao__NoPedigreeRecord.updateNoPedigreeRecord(npgr));
		    if (stmt != null) {
			stmt.close();
			stmt = null;
		    }
	 }
	
}


