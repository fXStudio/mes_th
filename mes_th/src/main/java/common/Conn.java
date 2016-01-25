package common;

import javax.naming.*;
import javax.sql.*;

public class Conn
{
  public Conn()
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
       //下面的是用于Weblogic中
       //ds = (DataSource)ctx.lookup("mes_framework");
       //System.out.println("java:comp/env/mes_framework");
       //下面的是用于Tomcat中
       ds = (DataSource)ctx.lookup("java:comp/env/mes_th");
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