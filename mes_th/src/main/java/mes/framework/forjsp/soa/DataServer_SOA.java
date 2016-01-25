package mes.framework.forjsp.soa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 访问数据层抽象类
 * @author 吕智 2007-7-2
 */


public class DataServer_SOA 
{		
	private Connection conn=null;
	
	public DataServer_SOA(Connection conn)
	{
		this.conn=conn;
	}
	
	//根据流程号获取流程名
	public String getProcessName_ProcessID(String processid) throws Exception
	{
		String processname="";
		
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		try
		{
			if(processid==null||processid.trim().equals("")) throw new Exception("实参为空！");
			sql="select cprocessname from process_statement where nprocessid="+processid+"";
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				processname=rs.getString(1);	
			}
			return processname;
		}
		catch(Exception e)
		{
			System.out.println("DataServer_SOA类getProcessName_ProcessID(String processid)方法抛出异常"+e);
			throw e;
		}finally{
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
		}
	}
	
	//根据服务号获取服务名
	public String getServerName_ServerID(String serverid) throws Exception
	{
		String servername="";
		
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		try
		{
			if(serverid==null||serverid.trim().equals("")) throw new Exception("实参为空！");
			sql="select cservername from server_statement where nserverid="+serverid+"";
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				servername=rs.getString(1);	
			}
			return servername;
		}
		catch(Exception e)
		{
			System.out.println("DataServer_SOA类getServerName_ServerID(String serverid)方法抛出异常"+e);
			throw e;
		}finally{
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
		}
	}
	
	//根据流程号，服务别名获取服务号
	public String getServerID_Processid_ServerAlias(String processid,String serveralias) throws Exception
	{
		
		String serverid="";
		
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		try
		{
			if(processid==null||processid.trim().equals("")) throw new Exception("实参流程号为空！");
			if(serveralias==null||serveralias.trim().equals("")) throw new Exception("实参服务别名为空！");
			sql="select nserverid from process_servers where nprocessid="+processid+" and caliasname='"+serveralias+"'";
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				serverid=rs.getString(1);	
			}
			return serverid;
		}
		catch(Exception e)
		{
			System.out.println("DataServer_SOA类getServerID_Processid_ServerAlias(String processid,String serveralias)方法抛出异常"+e);
			throw e;
		}finally{
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
		}
	}
	
	//根据数据来源号，获取数据来源描述
	public String getSourceDescription_SourceID(String sourceid) throws Exception
	{
		
		String sourcedescription="";
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		try
		{
			if(sourceid==null||sourceid.trim().equals("")) throw new Exception("实参为空");
			sql="select cdescription from source_statement where nsourceid="+sourceid+"";
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				sourcedescription=rs.getString(1);	
			}
			return sourcedescription;
		}
		catch(Exception e)
		{
			System.out.println("DataServer_SOA类getSourceDescription_SourceID(String sourceid)方法抛出异常"+e);
			throw e;
		}finally{
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
		}
	}	
}