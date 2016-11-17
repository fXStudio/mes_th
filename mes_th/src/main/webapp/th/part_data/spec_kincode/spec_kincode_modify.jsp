<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.SimpleDateFormat"%>

<% 
     /** 主键ID */
     String id = request.getParameter("intId");
     /** 页码 */
     String page_num = request.getParameter("page");
     /** KIN号 */
     String ccarno = "";
     /** 焊装上线时间 */
     String cenabled = "";
     /** 焊装顺序号 */
     String remark = "";
     /** 特殊KIN号ID */
     String kID = "";
     
     Connection conn = null;
     Statement stmt = null;
     ResultSet rs = null;
     
     try{
         conn = new Conn_MES().getConn();
         stmt = conn.createStatement();
         rs = stmt.executeQuery("SELECT ID, CCARNO, cenabled, CREMARK FROM SPECIALKIN WHERE ID = " + id);
         
         if(rs.next()){
        	 kID = rs.getString("ID");
        	 ccarno= rs.getString("CCARNO");
        	 cenabled = rs.getString("cenabled");
        	 remark = rs.getString("CREMARK");
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
        <title>特殊KIN号维护</title>
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/th_style.css">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
        <script type="text/javascript" src="Dialog.js"></script>
    </head>
    <body>
         <div align="center">
            <div class="title"><strong><!-- InstanceBeginEditable name="标题" -->特殊KIN号维护<!-- InstanceEndEditable --></strong></div>
            <form action="spec_kincode_update.jsp" onsubmit="return checkForm(this)">
                <input type="hidden" name="page" value="<%=page_num%>">
                <input type="hidden" name="intId" value="<%=id%>">
                <table border=1 cellspacing=0 cellpadding=0 style="font-family: 宋体, Arial;font-size: 12px;">
                    <tr>
                        <td>ID</td>
                        <td>
                            <mes:inputbox colorstyle="" id="primaryID" name="primaryID" reSourceURL="../../../JarResource" readonly="true" value="<%=kID%>"/>
                        </td>
                        <td width=50 align="right">KIN号</td>
                        <td>
                            <mes:inputbox colorstyle="" id="ccarno" name="ccarno" reSourceURL="../../../JarResource" readonly="true" value="<%=ccarno%>"/>
                        </td>
                    </tr>
                    <tr>
                        <td>是否监控</td>
                        <td>
                        	<input type="checkbox" id="cenabled" name="cenabled" <%="1".equals(cenabled) ? "checked=checked" : ""%>/>
                        </td>
                        <td width=50 align="right">备注</td>
                        <td>
                            <mes:inputbox colorstyle="box_red" id="remark" name="remark" reSourceURL="../../../JarResource" value="<%=remark%>"/>
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
                	var url = "spec_kincode_search.jsp?page=" + page + "&eid=" + eid;
                	window.location.href = url;
                }
        -->
    </script>
</html>