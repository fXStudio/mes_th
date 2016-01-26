<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,com.qm.mes.system.elements.*" errorPage="" %>
<html>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="com.qm.mes.system.factory.*" %>
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); %>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
<%
    Connection con=null;
    Statement stmt=null;
    ResultSet rs=null;
    try{
    con = Conn.getConn();
    stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    IMaterialidentifyFactory factory;
    factory = (IMaterialidentifyFactory) FactoryAdapter.getFactoryInstance(IMaterialidentifyFactory.class.getName());
	IMaterialidentify midentify= factory.queryElement(new Integer(request.getParameter("element_name")).intValue(), con);
	String str_codelength = String.valueOf(midentify.getCodeLength());
	String str_description = midentify.getDescription()==null?"":midentify.getDescription();
 %>
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>修改物料标识</title>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<div class="title">
		修改物料标识
</div>
<body>
<div align="center">
<form  name="form1"  action="materialidentify_updating.jsp" method="get" onSubmit="return checkinput()">
    <table class="tbnoborder">
      <tr>
        <td width="119">物料标识名：</td>
        <td width="266">
        <mes:inputbox name="element_name" size="36" id="element_name" reSourceURL="../JarResource/" colorstyle="box_black"  value="<%=midentify.getName()%>" readonly="true"/></td>
      </tr>
	  <tr>
        <td width="119">物料标识长度：</td>
        <td width="266">
        <mes:inputbox name="codelength" value="<%=str_codelength%>" size="36" id="codelength" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
      </tr>
	   <tr>
        <td width="119">描述信息：</td>
        <td width="266">
        <mes:inputbox name="description" value="<%=str_description%>" size="36" id="description" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
      </tr>
    </table>
    <br>
    <table width="384" class="tbnoborder">
      <tr>
        <td class="tdnoborder" width="123" ><span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
          <input class="btn2"  type="submit" name="Submit" value="提交"/>
        </span></td>
        <td  class="tdnoborder" width="126" ><span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
          <input class="btn2"  type="reset" name="button"  onClick="resetPara()" value="重置"/>
        </span></td>
        <td class="tdnoborder" width="119" ><span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
	 onMouseOut="this.className='btn2_mouseout'" >
          <input class="btn2"  type="button" name="button2"  onClick="window.location.href='materialidentify_view.jsp'" value="返回"/>
        </span></td>
      </tr>
    </table>
	</form>
 </div>
</body>
<%
    //释放资源
	if(rs!=null)rs.close();
	if(stmt!=null)stmt.close();
	if(con!=null)con.close();
	}catch(Exception e)
	{
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(con!=null)con .close();
		throw e;
	}
 %>
<script type="text/javascript">
function checkinput(){
var re = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
var codelengthre = /^[0-9]+$/;
if(form1.element_name.value==""||form1.codelength.value=="")
	{
		alert("参数为空");
		return false;
	}else if(!codelengthre.test(document.getElementById("codelength").value)||form1.codelength.value<1||form1.codelength.value>50){
	    alert("标识码长度范围:1 - 50");
	    return false;
	}else if(!re.test(document.getElementById("element_name").value))
    {
	    alert("物料标识名应由字母、数字和汉字组成!");
	    return false;
	}else
		return true;

}
function resetPara(){
	document.getElementsByName("element_name")[0].focus();
}
</script>
</html>