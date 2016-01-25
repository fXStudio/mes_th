package common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Conn_MES
{

  public Conn_MES()
  {
  }  
  //Using weblogic JNDI DataSource Pool connection to connect to Oracle
  public java.sql.Connection getConn()
  {
     try
     {
       DataSource ds;
       Context ctx;
       ctx = new InitialContext();
       //下面的是用于Tomcat中
       ds = (DataSource)ctx.lookup("java:comp/env/mes_th");
       //下面的是用于Weblogic中
       //ds = (DataSource)ctx.lookup("soa_mes");
       //TODO 变更中间件的时候要记得修改这个连接池文件
       return ds.getConnection();
     }
     catch (Exception e) 
     {
       e.printStackTrace();
       return null;
     }
  }//end of private static Connection getConn


}//end of class Conn