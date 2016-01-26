<%@ page language="java" import="java.sql.*,com.qm.mes.system.elements.*,com.qm.mes.framework.*" contentType="text/html;charset=gb2312"%>
<%@page import="com.qm.mes.system.dao.*" %>
<%@page import="com.qm.mes.system.factory.*" %>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%
    String type_info="";
	type_info=request.getParameter("type_info");
	if (type_info==null)
		type_info="";
%>
<%
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;  
    int PageSize; //一页显示的记录数
	int RowCount; //记录总数
	int PageCount; //总页数
	int intPage; //待显示页码
	String strPage;
	int i=0;
	PageSize=10;
	//获得待显示的某一页数
	strPage=request.getParameter("page");	
	if(strPage==null)
	{
	    intPage=1;
	}
	else
	{
	    intPage=Integer.parseInt(strPage);
	    if(intPage<1) intPage =1;
	} 
	try{
	    //获取连接
	    conn=Conn.getConn();
		stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		String sql=null;
	%>
		<%
		IMaterialFactory factory;
		factory = (IMaterialFactory) FactoryAdapter.getFactoryInstance(IMaterialFactory.class.getName());
		IDAO_Material dao = (IDAO_Material) DAOFactoryAdapter.getInstance(DataBaseType.getDataBaseType(conn),IDAO_Material.class);
		sql=dao.getSQL_queryElementAll(type_info);
		List<IMaterial> list = new ArrayList<IMaterial>();
	%>
	<%
		rs=stmt.executeQuery(sql);
		rs.last();
		//获得总记录数
		RowCount=rs.getRow();
		//总页数
		PageCount = (RowCount+PageSize-1) / PageSize;
		if(intPage>PageCount) intPage = PageCount;
		if(PageCount>0)
		{
		    //将记录指针定位到下一显示位置
		    rs.absolute((intPage-1)*PageSize + 1);
		    i = 0;
		    while(i<PageSize && !rs.isAfterLast())
		    {%>
			<%	
			   IMaterial material = factory.createElement();
				material.setId(rs.getInt(3));
				material.setName(rs.getString(2));
				material.setDiscard(rs.getBoolean(4));
				list.add(material);
				%>
			<%
				i++;
		   		rs.next();
	      }
	}
	%>				
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<title>物料</title>
<meta http-equiv="Content-Language" content="zh-cn">
<style>
.head2{	height:22px;color:ffff00;
}
</style>
</head>

<body background="../images/background.jpg">
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>

<div class="title"><strong>物料</strong></div>
	<br>
	<div align="center">
<mes:table reSourceURL="../JarResource/" id="table1">
	<mes:thead>

   <mes:tr>  
		<td width="83">元素号</td>
		<td width="260">物料名</td>
		<td width="120">是否弃用</td>
		<td width="60">查看</td>
		<td width="60">更改</td>
		<td width="60">删除</td>
				
   </mes:tr>
</mes:thead>
<mes:tbody iterator="<%=list.iterator()%>" type="mes.system.elements.IMaterial" varName="material">
<mes:tr>   

		<mes:td >${material.id }</mes:td>
		<mes:td >${material.name }</mes:td>
		<mes:td>${material.discard}</mes:td>
		<mes:td ><a href='material_look.jsp?element_name=${material.id }'>查看</a></mes:td>
		<mes:td ><a href='material_update.jsp?element_name=${material.id }'>更改</a></mes:td>
		<mes:td >
		<%
		IMaterial ima = (IMaterial)pageContext.getAttribute("material");
		if(ima.isDiscard()==false){
		 %>
		<a href="#" title="点击将删除这条记录！" onClick="javascript:if(confirm('真的要删除这条记录吗？')) window.location.href='material_deleting.jsp?element_name=${material.id }';return false;">删除</a>
		<%}else{ %>
		--
		<%} %>
		</mes:td>


  </mes:tr>
  </mes:tbody>
<mes:tfoot>
   <mes:tr>
     <mes:td colspan="100%" align="center">
	  
		共<%=RowCount%>条  第<%=intPage%>页   共<%=PageCount%>页 	
		<%if(intPage>1){    
		out.println("<a href=\"material_view.jsp?page=1&type_info="+type_info+"\">第一页</a>");	
		out.println("<a href=\"material_view.jsp?page="+(intPage-1)+"&type_info="+type_info+"\">上一页</a>");		
		}%>	 
		<%if(intPage<PageCount){
		out.println("<a href=\"material_view.jsp?page="+(intPage+1)+"&type_info="+type_info+"\">下一页</a>");
		out.println("<a href=\"material_view.jsp?page="+(PageCount)+"&type_info="+type_info+"\">最后一页</a>");
		}%>

       <a href='material_insert.jsp'>【添加新记录】</a>
	  </mes:td>
   </mes:tr>  
   </mes:tfoot>  
</mes:table>
<form  name="form1" method="post" onSubmit="return checkinput(this)" action="material_view.jsp">
<table class="tbnoborder">
<tr>
<td class="tbnoborder">按物料名查询&nbsp;&nbsp;</td>
<td class="tdnoborder">
<input type=text value="<%=type_info %>" name="type_info" id="type_info" class="box1" size=10 onMouseOver="this.className='box3'" 
onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'"></td>
<td class="tdnoborder"><span class="btn2_mouseout" onMouseOver="this.className='btn2_mouseover'"
onMouseOut ="this.className='btn2_mouseout'" >
  <input class="btn2" type=submit  name=s1 value=查询 >
</span></td>
</tr>
</table>
</form>
<div style="color:ff0000;font-size:9pt">参数为空的时候查询所有信息。</div>
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
		if(conn!=null)conn.close();
		throw e;
	}
%>
</html>