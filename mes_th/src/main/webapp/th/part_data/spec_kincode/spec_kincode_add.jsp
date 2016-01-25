<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException" %>

<% 
     /** 页码 */
     String page_num = request.getParameter("page");
     /** 最大ID号 */
     String maxId = "1";

     Connection conn = null;
     Statement stmt = null;
     ResultSet rs = null;
     
     try{
         conn = new Conn_MES().getConn();
         stmt = conn.createStatement();
         rs = stmt.executeQuery("SELECT (ISNULL(MAX(ID), 0) + 1) as maxID FROM SPECIALKIN");
         
         if(rs.next()){
             maxId = rs.getString("maxID");
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
            <form action="spec_kincode_save.jsp" onsubmit="return checkForm(this)">
                <input type="hidden" name="page" value="<%=page_num%>">
                <table border=1 cellspacing=0 cellpadding=0 style="font-family: 宋体, Arial;font-size: 12px;">
                    <tr>
                        <td>ID</td>
                        <td>
                            <mes:inputbox colorstyle="" id="primaryID" name="primaryID" reSourceURL="../../../JarResource" readonly="true" value="<%=maxId%>"/>
                        </td>
                        <td width=50 align="right">KIN号</td>
                        <td>
                            <mes:inputbox colorstyle="box_red" id="ccarno" name="ccarno" reSourceURL="../../../JarResource" maxlength="9"/>
                        </td>
                    </tr>
                    <tr>
                        <td>有效日期</td>
                        <td>
                            <mes:calendar id="dwbegin" name="dwbegin" reSourceURL="../../../JarResource" haveTime="true"/>
                        </td>
                        <td width=50 align="right">备注</td>
                        <td>
                            <mes:inputbox colorstyle="box_red" id="remark" name="remark" reSourceURL="../../../JarResource"/>
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
                    if(checkEmpty(fobj)){
                        return checkNum(fobj) && checkDate(fobj);
                    }
                    return false;
                }
                
                /**
                 * 校验录入KIN号格式
                 */
                function checkNum(fobj){
                	// 制定数据格式： 9位数字
                	var regex = /[0-9]{9}$/;
                	// 文本框对象
                	var txt = fobj["ccarno"];
                	// 录入数据值
                	var val = txt.value;
                	// 数据录入标识符
                	var isCorrect = false;
                	
                	// 校验录入数据
                	if((isCorrect = regex.test(val)) == false){
                		alert("KIN号录入错误，请输入9位数字符");
                		txt.select();
                		txt.focus();
                	}
                	return isCorrect;
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
                 * 检查日期输入格式
                 */
                function checkDate(f){
                    /** 合理的日期格式 */
                    var regexp = /(^((19){1}|(20){1}))\d{2}-(([0-1]{1}[0-9]{1}))-([0-3]{1}[0-9]{1}) (([0-2]{1}[0-9]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})$)/;
                    /** 日期控件 */
                    var in_date = f["dwbegin"];
                    /** 是否符合规则 */
                    var isLegal = regexp.test(in_date.value);
                    
                    if(!isLegal){
                        alert("上线日期格式不正确!");
                        in_date.focus();
                    }
                    return isLegal;
                }
                
                /**
                 * 返回
                 */
                function backward(){
                	var page = "<%=page_num%>";
                	var url = "spec_kincode_search.jsp?page=" + page;
                	window.location.href = url;
                }
        -->
    </script>
</html>