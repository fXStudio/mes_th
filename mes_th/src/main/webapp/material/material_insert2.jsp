<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,mes.system.elements.*" errorPage="" %>
<%@page import="mes.framework.*"%>
<%@page import="mes.system.dao.*" %>
<%@page import="mes.system.factory.*" %>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<html>
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); 
    Connection conn=null;
    Statement stmt=null;
    ResultSet rs=null;
   try{
	    //获取连接
	conn=Conn.getConn();
	stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    IMaterialTypeFactory factory;
    IMaterialCharacterFactory factory1;
	IMaterialidentifyFactory factory2;
	factory = (IMaterialTypeFactory) FactoryAdapter.getFactoryInstance(IMaterialTypeFactory.class.getName());
	factory1 = (IMaterialCharacterFactory) FactoryAdapter.getFactoryInstance(IMaterialCharacterFactory.class.getName());
	factory2 = (IMaterialidentifyFactory) FactoryAdapter.getFactoryInstance(IMaterialidentifyFactory.class.getName());
	IDAO_MaterialType dao = (IDAO_MaterialType) DAOFactoryAdapter.getInstance(DataBaseType.getDataBaseType(conn),IDAO_MaterialType.class);
	IDAO_MaterialCharacter dao1 = (IDAO_MaterialCharacter) DAOFactoryAdapter.getInstance(DataBaseType.getDataBaseType(conn),IDAO_MaterialCharacter.class);
    IDAO_MaterialIdentify dao2 = (IDAO_MaterialIdentify) DAOFactoryAdapter.getInstance(DataBaseType.getDataBaseType(conn),IDAO_MaterialIdentify.class);
	String sqlType = dao.getSQL_queryElementAll();
	String sqlCharacter = dao1.getSQL_queryElementAll();
	String sqlIdentify = dao2.getSQL_queryElementAll();
	List<IMaterialType> listType = new ArrayList<IMaterialType>();
	List<IMaterialCharacter> listCharacter = new ArrayList<IMaterialCharacter>();
	List<IMaterialidentify> listIdentify = new ArrayList<IMaterialidentify>();
	rs=stmt.executeQuery(sqlType);
    while(rs.next()){ 
	   IMaterialType materialtype = factory.createElement();
       materialtype.setId(rs.getInt(3));
	   materialtype.setName(rs.getString(2));
	   listType.add(materialtype);
    }
	rs = stmt.executeQuery(sqlCharacter);
	while(rs.next()){
	   IMaterialCharacter character = factory1.createElement();
	   character.setId(rs.getInt(3));
	   character.setName(rs.getString(2));
	   listCharacter.add(character);
	}
	rs = stmt.executeQuery(sqlIdentify);
	while(rs.next()){
	   IMaterialidentify identify = factory2.createElement();
	   identify.setId(rs.getInt(3));
	   identify.setName(rs.getString(2));
	   listIdentify.add(identify);
	}
%>
 
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>添加物料</title>
<style type="text/css">
<!--
#Layer1{
	position:absolute;
	left:100px;
	top:40px;
	width:284px;
	height:180px;
	z-index:1;
	overflow:auto;
}
-->
.bottonStyle{
filter:alpha(opacity=100);
-moz-opacity:1;
opacity:1;
}
</style>
</head>
<div class="title">
		添加物料
</div>
<body>

<div id="Layer1">
<table>
  <tr>
    <td>
	<%for(int i=0;i<listCharacter.size();i++){ 
		 IMaterialCharacter character=factory1.createElement();
		 character=(IMaterialCharacter)listCharacter.get(i);
		 %>
		<input name="character" type="checkbox" value="<%=character.getId() %>"><%=character.getName()%>
		<%} %>
	</td>
  </tr> 
</table>
</div>
<div align="center">
<form  name="form1"  action="material_adding.jsp" method="get" onSubmit="return checkinput()">
    <table width="468">
      <tr>
        <td width="119">物料名：</td>
        <td width="266"><input name="element_name" size="50" type="text" id="element_name"  class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'"></td>
      </tr>
	   <tr><td height="10"></td></tr>
	  <tr>
        <td width="119">物料类型：</td>
        <td width="266">
		 <select name="type_id" style="width:300">
		 <%for(int i=0;i<listType.size();i++){ 
		 IMaterialType type=factory.createElement();
		 type=(IMaterialType)listType.get(i);
		 %>
		 <option value=<%=type.getId() %>> <%=type.getName()%> </option>
		 <%} %>
  </select></td>
      </tr>
	  <tr><td height="10"></td></tr>
	 <!--  <div id="sbody">  -->
	   <tr>
        <td width="119">物料特征：</td>
        <td width="266">
		  <input type="button" value="选择物料特征" onclick = "btnclick()" />
		</td>
      </tr>
	   <tr><td height="10"></td></tr>
	   <tr>
        <td width="119">物料标识：</td>
        <td width="266">
          <input type="button" name="Submit2" value="选择物料标识" onclick = "btnclick2()">
        </td>
      </tr>
	 <!--  </div>  -->
	   <tr><td height="10"></td></tr>
	   <tr>
        <td width="119">描述信息：</td>
        <td width="266"><input name="description" size="50" type="text" id="description"  class="box1" onMouseOver="this.className='box3'" onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'"></td>
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
          <input class="btn2"  type="button" name="button2"  onClick="window.location.href='material_view.jsp'" value="返回"/>
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
	if(conn!=null)conn.close();
	}catch(Exception e)
	{
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(conn!=null)conn .close();
		throw e;
	}
 %>
<script type="text/javascript">
function checkinput(){
if(form1.element_name.value==""||form1.type_id.value==""||form1.character.value==""||form1.identify.value=="")
	{
		alert("参数为空");
		return false;
	}
	else{
	    return true;
	}

}
function resetPara(){
	document.getElementsByName("element_name")[0].focus();
}

function btnclick(){
	var s1 = document.getElementById("style1");
	if(s1.style.display=='none'){
		s1.style.display='block';
		s1.className="bottonStyle";
	}else
		s1.style.display='none';
	
}
</script>
</html>