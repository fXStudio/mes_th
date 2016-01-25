<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,mes.system.elements.*" errorPage="" %>
<html>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="mes.system.dao.*" %>
<%@page import="mes.system.factory.*" %>
<%@page import="mes.framework.*"%>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0); %>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
<%
    Connection con=null;
    Statement stmt = null;
    ResultSet rs = null;
    String identhave="";
    String charachave = "";
    try{
    con = Conn.getConn();
    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
   
    IMaterialFactory factory;
    IMaterialCharacterFactory factoryCharacter;
	IMaterialidentifyFactory factoryIdentify;
	IMaterialTypeFactory factoryType;
  
    factory = (IMaterialFactory) FactoryAdapter.getFactoryInstance(IMaterialFactory.class.getName());
    factoryCharacter = (IMaterialCharacterFactory) FactoryAdapter.getFactoryInstance(IMaterialCharacterFactory.class.getName());
	factoryIdentify = (IMaterialidentifyFactory) FactoryAdapter.getFactoryInstance(IMaterialidentifyFactory.class.getName());
    factoryType = (IMaterialTypeFactory) FactoryAdapter.getFactoryInstance(IMaterialTypeFactory.class.getName());
	
	IDAO_MaterialType daoType = (IDAO_MaterialType) DAOFactoryAdapter.getInstance(DataBaseType.getDataBaseType(con),IDAO_MaterialType.class);
	IDAO_MaterialCharacter daoCharacter = (IDAO_MaterialCharacter) DAOFactoryAdapter.getInstance(DataBaseType.getDataBaseType(con),IDAO_MaterialCharacter.class);
    IDAO_MaterialIdentify daoIdentify = (IDAO_MaterialIdentify) DAOFactoryAdapter.getInstance(DataBaseType.getDataBaseType(con),IDAO_MaterialIdentify.class);

	IMaterial material= factory.queryElement(new Integer(request.getParameter("element_name")).intValue(), con);
	IMaterialType type= factoryType.queryElement(material.getMaterialTypeId(), con);

	String sqlAllCharacter = daoCharacter.getSQL_queryElementAll();
	String sqlAllIdentify = daoIdentify.getSQL_queryElementAll();
	String sqlHaveCharacter = daoCharacter.getSQL_queryCharactersById(material.getId());
	String sqlHaveIdentify = daoIdentify.getSQL_queryIdentifyById(material.getId());
	String sqlOherType = daoType.getSQL_queryElementOtherType(type.getId());
	
	java.util.HashMap<Comparable,String> map = new java.util.HashMap<Comparable,String>();
	map.put(new mes.util.SelectMap(type.getId(),type.getName()),String.valueOf(type.getId()));
	List<IMaterialCharacter> listHaveCharacter = new ArrayList<IMaterialCharacter>();
	List<IMaterialCharacter> listAllCharacter = new ArrayList<IMaterialCharacter>();
	List<IMaterialidentify> listHaveIdentify = new ArrayList<IMaterialidentify>();
	List<IMaterialidentify> listAllIdentify = new ArrayList<IMaterialidentify>();

	rs=stmt.executeQuery(sqlOherType);
	while(rs.next()){
	  IMaterialType materialType = factoryType.createElement();
	  materialType.setId(rs.getInt(3));
	  materialType.setName(rs.getString(2));
	  map.put(new mes.util.SelectMap(materialType.getId(),materialType.getName()),String.valueOf(materialType.getId()));
	}
	rs = stmt.executeQuery(sqlHaveCharacter);
	while(rs.next()){
	   IMaterialCharacter cha = factoryCharacter.createElement();
	   cha.setId(rs.getInt(1));
	   cha.setName(rs.getString(2));
	   charachave = charachave + ":" +cha.getId();
	   listHaveCharacter.add(cha);
	}
	rs = stmt.executeQuery(sqlAllCharacter);
	while(rs.next()){
	   IMaterialCharacter ch = factoryCharacter.createElement();
	   ch.setId(rs.getInt(3));
	   ch.setName(rs.getString(2));
	   listAllCharacter.add(ch);
	}
	rs = stmt.executeQuery(sqlHaveIdentify);
	while(rs.next()){
	   IMaterialidentify imid = factoryIdentify.createElement();
	   imid.setId(rs.getInt(1));
	   imid.setName(rs.getString(2));
	   identhave = identhave + ":" + imid.getId();
	   listHaveIdentify.add(imid);
	}  
	rs = stmt.executeQuery(sqlAllIdentify);
	while(rs.next()){
	   IMaterialidentify iden = factoryIdentify.createElement();
	   iden.setId(rs.getInt(3));
	   iden.setName(rs.getString(2));
	   listAllIdentify.add(iden);
	}
	String str_description = material.getDescription()==null?"":material.getDescription();
 %>
 
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>修改物料</title>
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
</head>
<div class="title">
		修改物料
</div>
<body>
<div align="center">
<form  name="form1"  action="material_updating.jsp" method="get" onSubmit="return checkinput()">
    <table class="tbnoborder" border="1">
      <tr>
        <td align="center" width="119">物料名：</td>
        <td width="266">
        <mes:inputbox name="element_name" value="<%=material.getName()%>" size="36" id="element_name" reSourceURL="../JarResource/" colorstyle="box_black"  readonly="true"/></td>
      </tr>
      <tr>
        <td align="center" width="119">物料类型：</td>
        <td width="266">
         <mes:selectbox colorstyle="box_black" id="selectbox1" name="type_id" map="<%=map%>" reSourceURL="../JarResource/" size="36" maxlength="30" readonly="false" selectSize="6" value="<%=type.getName() %>"/>
		</td>
      </tr>
	  <tr>
        <td align="center" width="119">物料特征：</td>
        <td width="266">
		 <%for(int i=0;i<listAllCharacter.size();i++){ 
		      IMaterialCharacter character=factoryCharacter.createElement();
		      character=(IMaterialCharacter)listAllCharacter.get(i);
		      String str_out="";
		      for(int j=0;j<listHaveCharacter.size();j++){
		         IMaterialCharacter ich = factoryCharacter.createElement();
		         ich = (IMaterialCharacter)listHaveCharacter.get(j);
		         if(character.getId()==ich.getId())
		            str_out = "checked";
		      }
		 %>
		<input name="character" type="checkbox" value="<%=character.getId() %>" <% out.write(str_out); %>><%=character.getName()%>
		<br/>
		<%
		}
		%>		</td>
      </tr>
	  <tr>
        <td align="center" width="119">物料标识：</td>
        <td width="266">
		 <%for(int i=0;i<listAllIdentify.size();i++){ 
		     IMaterialidentify identify=factoryIdentify.createElement();
		     identify=(IMaterialidentify)listAllIdentify.get(i);
		     String str_outiden="";
		     for(int j=0;j<listHaveIdentify.size();j++){
		         IMaterialidentify ide = factoryIdentify.createElement();
		         ide = (IMaterialidentify)listHaveIdentify.get(j);
		         if(identify.getId()==ide.getId())
		            str_outiden = "checked";
		      } 
		 %>
		 <input name="identify" type="checkbox" value="<%=identify.getId() %>" <%  out.write(str_outiden); %>><%=identify.getName()%>
		 <br/>
		 <%   
		 }
		 %>	    </td>
      </tr>
      <tr>
        <td align="center" width="119">描述信息：</td>
        <td width="266">
        <mes:inputbox name="description" value="<%=str_description%>" size="36" id="description" reSourceURL="../JarResource/" colorstyle="box_black" /></td>
      </tr>
    </table>
     <input type="hidden" name="charachave" value="<%=charachave%>" />
     <input type="hidden" name="identhave" value="<%=identhave%>" />
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
if(form1.element_name.value==""||form1.type_id.value==""||form1.character.value==""||form1.identify.value=="")
	{
		alert("参数为空");
		return false;
	}else if(!re.test(document.getElementById("element_name").value))
    {
	    alert("物料名应由字母、数字和汉字组成!");
	    return false;
	}else
		return true;

}
function resetPara(){
	document.getElementsByName("element_name")[0].focus();
}
</script>
</html>