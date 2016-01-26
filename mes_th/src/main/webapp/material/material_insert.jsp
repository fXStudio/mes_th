<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,com.qm.mes.system.elements.*" errorPage="" %>
<%@page import="com.qm.mes.framework.*"%>
<%@page import="com.qm.mes.system.dao.*" %>
<%@page import="com.qm.mes.system.factory.*" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES"/>
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
	java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
	List<IMaterialCharacter> listCharacter = new ArrayList<IMaterialCharacter>();
	List<IMaterialidentify> listIdentify = new ArrayList<IMaterialidentify>();
	rs=stmt.executeQuery(sqlType);
    while(rs.next()){ 
	   IMaterialType materialtype = factory.createElement();
       materialtype.setId(rs.getInt(3));
	   materialtype.setName(rs.getString(2));
	  map.put(new com.qm.mes.util.SelectMap(materialtype.getId(),materialtype.getName()),String.valueOf(materialtype.getId()));
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
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<div class="title">
		添加物料
</div>
<body>
<div align="center">
<form  name="form1"  action="material_adding.jsp" method="get" onSubmit="return checkinput()">
    <table class="tbnoborder" border="1">
      <tr>
        <td width="119">物料名：</td>
        <td width="266">
        <mes:inputbox name="element_name" size="36" id="element_name" maxlength="40" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
      </tr>
	   <tr>
        <td width="119">物料类型：</td>
        <td width="266">
         <mes:selectbox colorstyle="box_black" id="selectbox1" name="type_id" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value=""/>
		 </td>
      </tr>
	   <tr>
        <td width="119">物料特征：</td>
        <td width="266">
		 <%for(int i=0;i<listCharacter.size();i++){ 
		 IMaterialCharacter character=factory1.createElement();
		 character=(IMaterialCharacter)listCharacter.get(i);
		 %>
		<input name="character" type="checkbox" value="<%=character.getId() %>"><%=character.getName()%>
		<br/>
		<%} %>
		</td>
      </tr>
	   <tr>
        <td width="119">物料标识：</td>
        <td width="266">
		 <%for(int i=0;i<listIdentify.size();i++){ 
		 IMaterialidentify identify=factory2.createElement();
		 identify=(IMaterialidentify)listIdentify.get(i);
		 %>
		 <input name="identify" type="checkbox" value="<%=identify.getId() %>"><%=identify.getName()%>
		<br/>
		 <%} %>
        </td>
      </tr>
	   <tr>
        <td width="119">描述信息：</td>
        <td width="266">
        <mes:inputbox name="description" size="36" id="description" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
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
var re = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
if(form1.element_name.value==""||form1.type_id.value==""||form1.character.value==""||form1.identify.value=="")
	{
		alert("参数为空");
		return false;
	}else if(!re.test(document.getElementById("element_name").value))
    {
	    alert("物料名应由字母、数字和汉字组成!");
	    return false;
	}else{
	    return true;
	}

}
function resetPara(){
	document.getElementsByName("element_name")[0].focus();
}
</script>
</html>