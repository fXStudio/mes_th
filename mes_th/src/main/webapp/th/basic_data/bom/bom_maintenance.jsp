<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>

<% 
     /** 当前页码 */
     String page_num = request.getParameter("intPage");
     /** 当前数据库中最大索引 */
     String maxId = "1";

     Connection conn = null;
     Statement stmt = null;
     ResultSet rs = null;
     
     try{
         conn = new Conn_MES().getConn();
         stmt = conn.createStatement();
         rs = stmt.executeQuery("SELECT (ISNULL(MAX(ID), 0) + 1) AS MAXID FROM PARTDATA");
         
         if(rs.next()){
             maxId = rs.getString("MAXID");
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
        <title>BOM维护</title>
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/th_style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
        <script type="text/javascript" src="js/core.js"></script>
    </head>
    <body>
        <div align="center">
            <div class="title"><strong><!-- InstanceBeginEditable name="标题" -->BOM维护<!-- InstanceEndEditable --></strong></div>
	        <form action="bom_save.jsp" onsubmit="return checkForm(this)">
	            <table border = 0 class="th_table" cellspacing = 0 cellpadding = 0>
	                <tr>
	                    <td class="tbl_title">
	                        ID:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="" id="partdata_id" name="partdata_id" reSourceURL="../../../JarResource" readonly="true" value="<%=maxId%>"/>
	                    </td>
	                    <td class = "tbl_title">
	                        天合零件号:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="box_red" id="partdata_ctfass" name="partdata_ctfass" reSourceURL="../../../JarResource"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td class = "tbl_title">
	                        大众主零件号:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="" id="primary" name="primary" reSourceURL="../../../JarResource" readonly="true"/>
	                       <input type="button" value=".." onclick="showDialog(this)" alt="00001" id="fawprimary"/>
	                    </td>
	                    <td class = "tbl_title">
	                        大众子零件种类数:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="box_red" id="partdata_nvwsubparttype" name="partdata_nvwsubparttype" reSourceURL="../../../JarResource" maxlength="4"/>
	                    </td>
	                </tr>
	                <tr>
	                   <td class="tbl_title">
                            大众子零件号:
                       </td>
                       <td>
                           <mes:inputbox colorstyle="" id="sub" name="sub" reSourceURL="../../../JarResource" readonly="true"/>
                           <input type="button" value=".." onclick="showDialog(this)" alt="00002" id = "fawsub">
                       </td>
                       <td class="tbl_title">
                            大众子零件数量:
                       </td>
                       <td>
                           <mes:inputbox colorstyle="box_red" id="partdata_nvwsubpartquan" name="partdata_nvwsubpartquan" reSourceURL="../../../JarResource" maxlength="4"/>
                       </td>
	                </tr>
	                  <tr>
                        <td class="tbl_title">
                            方案号:
                        </td>
                        <td>
                           <mes:inputbox colorstyle="box_red" id="plan_no" name="plan_no" reSourceURL="../../../JarResource"/>
                        </td>
                        <td colspan = 2 align="center">
                            <input type="checkbox" id="tempcar" name="tempcar" style="" onfocus="this.blur()" tabindex="100"/> 过度车型
                        </td>
                    </tr>
                    <tr>
                        <td class="tbl_title">
                            备注:
                        </td>
                        <td colspan=3>
                            <mes:inputbox colorstyle="box_red" id="remark" name="remark" reSourceURL="../../../JarResource" size="40" maxlength="60"/>
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
        <!-- 提示窗口 -->
        <div class="fore" id="fore" align="center">
          <div>
               <div id = "subwin" style="border:solid 2px #336699;width:1px;height:238px;">
                       <!-- 弹出窗口表单 -->
               </div>
               <div>
                   <input type="button" value="提交" onclick="apply()">
                  <input type="button" value="取消" onclick="closeDialog()">
              </div>
          </div>
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
                    var flg = checkEmpty(fobj);
                    if(flg){
                        flg = checkFormat(fobj);
                    }
                    return flg;  
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
                 *  校验文本格式
                 */
                function checkFormat(f){
                    // 格式
                    var regexp = /^[0-9]+$/;
                    // 要校验的文本名称 ["大众子零件种类数", "大众子零件数量"]
                    var elenames = ["partdata_nvwsubparttype", 
                                    "partdata_nvwsubpartquan"];
                   
                    for(var i =0; i<elenames.length; i++){
                        var name = elenames[i];
                        var inp = f[name];
                        
                        if(!inp.value.match(regexp)){
                            alert("只能输入数字");
                            inp.focus();
                            return false;
                        }
                    }
                    return true;
                }
                
                /**
                 * 页面回退
                 */
                function backward(){
                	var page = "<%=page_num%>";
                	var url = "bom_manage.jsp?page=" + page;
                	window.location.href = url;
                }
        -->
    </script>
</html>