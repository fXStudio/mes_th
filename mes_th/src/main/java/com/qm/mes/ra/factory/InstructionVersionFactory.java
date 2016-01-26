package com.qm.mes.ra.factory;
/**
 * author : 谢静天
 */
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.DataBaseType;
import com.qm.mes.ra.bean.*;
import com.qm.mes.ra.dao.DAO_InstructionVersion;
import com.qm.mes.system.dao.DAOFactoryAdapter;
public class InstructionVersionFactory {
	private final Log log = LogFactory.getLog(InstructionVersionFactory.class);
	
	/**通过版本号查出指令版本
	 * author : 谢静天
	 */
	public Version getversionById(int int_id,Connection con)throws SQLException{
        DAO_InstructionVersion dao_Instructionversion = (DAO_InstructionVersion)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_InstructionVersion .class);
        Statement stmt = con.createStatement();
        log.debug("通过版本号查出指令版本"+dao_Instructionversion.getVersionByid(int_id));
     ResultSet rs= stmt.executeQuery(dao_Instructionversion.getVersionByid(int_id));
       Version version=new Version();
       if(rs.next()){
    	   version.setId(rs.getInt("int_id"));
    	   version.setVersionDatime(rs.getDate("Datime_versionDatime"));
    	   version.setUser(rs.getString("Str_user"));
    	   version.setProdunitid(rs.getInt("int_produnitid"));
    	   version.setVersionCode(rs.getString("Str_versionCode"));
    	   version.setDescription(rs.getString("str_description"));
       }
       if(stmt!=null){
    	   stmt.close();
    	   stmt=null;
       }
       return version;
    }
     
	/**创建指令版本
	 * author : 谢静天
	 */
	public void saveVersion(Version version,Connection con)throws SQLException{
          DAO_InstructionVersion dao_Instructionversion = (DAO_InstructionVersion)DAOFactoryAdapter.getInstance(
                  DataBaseType.getDataBaseType(con),DAO_InstructionVersion .class);
          Statement stmt = con.createStatement();
          log.debug("创建指令版本"+dao_Instructionversion.saveVersion(version));
          stmt.executeUpdate(dao_Instructionversion.saveVersion(version));
          if(stmt!=null){
       	   stmt.close();
       	   stmt=null;
          }
          
      }
	  
	/**删除指令版本
	 * author : 谢静天
	 */
	public void delVersion(int int_id,Connection con)throws SQLException{
        DAO_InstructionVersion dao_Instructionversion = (DAO_InstructionVersion)DAOFactoryAdapter.getInstance(
                DataBaseType.getDataBaseType(con),DAO_InstructionVersion .class);
        Statement stmt = con.createStatement();
        log.debug("删除指令版本"+dao_Instructionversion.delVersionById(int_id));
        stmt.executeUpdate(dao_Instructionversion.delVersionById(int_id));
        if(stmt!=null){
        	   stmt.close();
        	   stmt=null;
           } 
	}
	
	/**根据版本号改版本备注信息
	 * @author： 包金旭
	 *
	 */
	public void upversiondescriptionbyvcode(String versioncode,String description,Connection con)throws SQLException{
		DAO_InstructionVersion dao_version = (DAO_InstructionVersion) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), DAO_InstructionVersion.class);
          Statement stmt = con.createStatement();
          stmt.executeUpdate(dao_version.upversiondescriptionbyvcode(versioncode, description));
          if(stmt!=null){
  			stmt.close();
  		}
	}
	
}

