<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>

<% 
	 /** 主键ID */
	 String id = request.getParameter("id");
	 /** 页码 */
	 String page_num = request.getParameter("page");
     // 天合总成名称
     Map<Comparable, String> fassname = new HashMap<Comparable, String>();
     // 物料类别
     Map<Comparable, String> partType = new HashMap<Comparable, String>();
     partType.put("主物料", "00001");
     partType.put("子物料", "00002");

     Connection conn = null;
     Statement stmt = null;
     ResultSet rs = null;
     
     try{
         conn = new Conn_MES().getConn();
         stmt = conn.createStatement();
         rs = stmt.executeQuery("SELECT id, cTFASSName FROM TFASSNAME");
         
         while(rs.next()){
             fassname.put(rs.getString("cTFASSName"), rs.getString("id"));    
         }
     }catch(Exception e){
         e.printStackTrace();
     }finally{
         // resource release
         if(rs != null){
             try{
                 rs.close();
             }catch(SQLException e){
                 e.printStackTrace();
             }finally{
                 rs = null;
             }
         }
         if(stmt != null){
             try{
                 stmt.close();
             }catch(SQLException e){
                 e.printStackTrace();
             }finally{
                 stmt = null;
             }
         }
         if(conn != null){
             try{
                 conn.close();
             }catch(SQLException e){
                 e.printStackTrace();
             }finally{
                 conn = null;
             }
         }
     }
%>

<html>
    <head>
        <title>物料维护</title>
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/th_style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
        <script type="text/javascript" src="Dialog.js"></script>
    </head>
    <body>
    	<% 
    	      // 零件编码
    	      String partcode = "";
    	      // 零件类别
    	      String parttype = "";
    	      // 天合总成名称
    	      String thname = "";
    	      // 备注
    	      String remark = "";
    	      
    	      // 获得修改数据
    	      StringBuilder subSql = new StringBuilder();
    	      subSql.append(" SELECT PARTLIST.CPARTNO, ");
    	      subSql.append(" CASE WHEN PARTLIST.CPARTTYPE = '00001' ");
    	      subSql.append("      THEN '主物料' ");
    	      subSql.append("      ELSE '子物料' ");
    	      subSql.append("      END AS CPARTTYPE, ");
    	      subSql.append(" ISNULL(TFASSNAME.CTFASSNAME, '')  AS CTFASSNAME, ");
    	      subSql.append(" PARTLIST.CREMARK ");
    	      subSql.append(" FROM PARTLIST LEFT JOIN TFASSNAME ON PARTLIST.ITFASSNAMEID = TFASSNAME.ID ");
    	      subSql.append(" WHERE PARTLIST.ID = ");
    	      subSql.append(id);
    	      
    	      try{
    	    	  conn = new Conn_MES().getConn();
    	    	  stmt = conn.createStatement();
    	    	  rs = stmt.executeQuery(subSql.toString());
    	    	  
    	    	  if(rs.next()){
    	    		  partcode = rs.getString("CPARTNO");
    	    		  parttype = rs.getString("CPARTTYPE");
    	    		  thname = rs.getString("CTFASSNAME");
    	    		  remark = rs.getString("CREMARK");
    	    	  }
    	      }catch(Exception e){
    	    	  e.printStackTrace();
    	      }finally{
    	    	  if(rs != null){
    	    		  try{
    	    			  rs.close();
    	    		  }catch(SQLException e){
    	    			  e.printStackTrace();
    	    		  }finally{
    	    			  rs = null;
    	    		  }
    	    	  }
    	    	  if(stmt != null){
    	    		  try{
    	    			  stmt.close();
    	    		  }catch(SQLException e){
    	    			  e.printStackTrace();
    	    		  }finally{
    	    			  stmt = null;
    	    		  }
    	    	  }
    	    	  if(conn != null){
    	    		  try{
    	    			  conn.close();
    	    		  }catch(SQLException e){
    	    			  e.printStackTrace();
    	    		  }finally{
    	    			  conn = null;
    	    		  }
    	    	  }
    	      }
    	
    	%>
        <div align="center">
            <div class="title"><strong><!-- InstanceBeginEditable name="标题" -->物料维护<!-- InstanceEndEditable --></strong></div>
            <form action="marerial_update.jsp" onsubmit="return checkForm(this)">
            	<input type="hidden" name="intId" value="<%=id%>"/>
            	<input type="hidden" name="page" value="<%=page_num%>"/>
            	
                <table border=0 cellspacing=0 cellpadding=0 class="th_table">
                    <tr>
                        <td>零件编码: </td>
                        <td>
                            <mes:inputbox colorstyle="box_red" id="partcode" name="partcode" reSourceURL="../../../JarResource" value="<%=partcode%>"/>
                        </td>
                        <td>零件类别: </td>
                        <td>
                            <mes:selectbox colorstyle="" id="parttype"  map="<%=partType%>" name="parttype" reSourceURL="../../../JarResource" value="<%=parttype%>"/>
                        </td>       
                    </tr>
                    <tr>
                        <td>天合总成名称: </td>
                        <td colspan=3>
                            <mes:selectbox colorstyle="" id="fassname"  map="<%=fassname%>" name="fassname" reSourceURL="../../../JarResource" value="<%=thname%>"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            备注:
                        </td>
                        <td colspan=3>
                            <mes:inputbox colorstyle="box_red" id="remark" name="remark" reSourceURL="../../../JarResource" size="40" value="<%=remark%>"/>
                        </td>                    
                    </tr>
                    <tr>
                        <td colspan = 4 align="center">
                            <mes:button id="c_save" reSourceURL="../../../JarResource" value="提交" submit="true"/>
                            <mes:button id="c_clean" reSourceURL="../../../JarResource" value="清除" submit="false"/>
                            <mes:button id="c_back" reSourceURL="../../../JarResource" value="返回" onclick="backward()"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
    <script>
        <!--
                /**
                 *  校验Form元素
                 *
                 *  @param fobj 表单对象
                 */
                function checkForm(fobj){
                    return checkEmpty(fobj);
                }
                
                /**
                 * 检查文本框不能为空
                 *
                 * @return false:存在异常  true:正常 
                 */
                function checkEmpty(f){
                    var inputs = f.getElementsByTagName("input");
                    for(var i=0; i<inputs.length; i++){
                        var ipt = inputs[i];
                        if(ipt["type"] == "text"){
                            if(!ipt["value"]){
                                alert("文本框不能为空!");
                                ipt.focus();
                                return false;
                            }
                        }
                    }
                    return true;
                }
                
                /**
                 * 返回
                 */
                function backward(){
                	var eid = "<%=id%>";
                	var page = "<%=page_num%>";
                	var url = "material_manage.jsp?eid=" + eid + "&page=" + page;
                	window.location.href = url;
                }
        -->
    </script>
</html>