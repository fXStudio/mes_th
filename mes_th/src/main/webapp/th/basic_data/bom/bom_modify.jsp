<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.th.helpers.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>

<% 
	/** 主键ID */
	String id = request.getParameter("intId");
	/** 页码 */
	String page_num = request.getParameter("strPage");
	/** 天合零件号 */
	String tfass = "";
	/** 大众主零件号 */
	String vwmainpart = "";
	/** 大众子零件种类数量 */
	String vwsubparttype = "";
	/** 大众子零件号 */
	String vwsubpart = "";
	/** 大众子零件数量 */
	String vwsubpartquan = "";
	/** 方案号 */
	String qadno = "";
	/** 是否过度车型 */
	String istempcar = "";
	/** 备注 */
	String remark = "";
	
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	try{
		conn = new Conn_MES().getConn();
		stmt = conn.prepareStatement("SELECT * FROM PARTDATA WHERE ID = ?");
		stmt.setString(1, id);
		rs = stmt.executeQuery();
		
		if(rs.next()){
			tfass = rs.getString("cTFASS");
			vwmainpart = rs.getString("cVWMainPart");
			vwsubparttype = rs.getString("nVWSubPartType");
			vwsubpart = rs.getString("cVWSubPart");
			vwsubpartquan = rs.getString("nVWSubPartQuan");
			qadno = rs.getString("cQADNo");
			istempcar = rs.getString("cIsTempCar");
			remark = rs.getString("cRemark");
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
	        <form action="bom_update.jsp" onsubmit="return checkForm(this)">
	        	<input type="hidden" name="page" value="<%=page_num%>">
	        	<input type="hidden" name="intId" value="<%=id%>">
	            <table border = 0 class="th_table" cellspacing = 0 cellpadding = 0>
	                <tr>
	                    <td class="tbl_title">
	                        ID:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="" id="partdata_id" name="partdata_id" reSourceURL="../../../JarResource" readonly="true" value="<%=id%>"/>
	                    </td>
	                    <td class = "tbl_title">
	                        天合零件号:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="box_red" id="partdata_ctfass" name="partdata_ctfass" reSourceURL="../../../JarResource" value="<%=tfass%>"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td class = "tbl_title">
	                        大众主零件号:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="" id="primary" name="primary" reSourceURL="../../../JarResource" readonly="true" value="<%=vwmainpart%>"/>
	                       <input type="button" value=".." onclick="showDialog(this)" alt="00001" id="fawprimary"/>
	                    </td>
	                    <td class = "tbl_title">
	                        大众子零件种类数:
	                    </td>
	                    <td>
	                       <mes:inputbox colorstyle="box_red" id="partdata_nvwsubparttype" name="partdata_nvwsubparttype" reSourceURL="../../../JarResource" maxlength="4" value="<%=vwsubparttype%>"/>
	                    </td>
	                </tr>
	                <tr>
	                   <td class="tbl_title">
                            大众子零件号:
                       </td>
                       <td>
                           <mes:inputbox colorstyle="" id="sub" name="sub" reSourceURL="../../../JarResource" readonly="true" value="<%=vwsubpart%>"/>
                           <input type="button" value=".." onclick="showDialog(this)" alt="00002" id = "fawsub">
                       </td>
                       <td class="tbl_title">
                            大众子零件数量:
                       </td>
                       <td>
                           <mes:inputbox colorstyle="box_red" id="partdata_nvwsubpartquan" name="partdata_nvwsubpartquan" reSourceURL="../../../JarResource" maxlength="4" value="<%=vwsubpartquan%>"/>
                       </td>
	                </tr>
	                  <tr>
                        <td class="tbl_title">
                            方案号:
                        </td>
                        <td>
                           <mes:inputbox colorstyle="box_red" id="plan_no" name="plan_no" reSourceURL="../../../JarResource" value="<%=qadno%>"/>
                        </td>
                        <td colspan = 2 align="center">
                            <input type="checkbox" id="tempcar" name="tempcar" style="" onfocus="this.blur()" tabindex="100" <%="T".equalsIgnoreCase(istempcar) ? "checked" : ""%>/> 过度车型
                        </td>
                    </tr>
                    <tr>
                        <td class="tbl_title">
                            备注:
                        </td>
                        <td colspan=3>
                            <mes:inputbox colorstyle="box_red" id="remark" name="remark" reSourceURL="../../../JarResource" size="40" maxlength="60" value="<%=remark%>"/>
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
                 * 回退页面
                 */
                function backward(){
                	var eid = "<%=id%>";
                	var page = "<%=page_num%>";
                	var url = "bom_manage.jsp?eid=" + eid + "&page=" + page;
                	window.location.href = url;
                }
        -->
    </script>
</html>