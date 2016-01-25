<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@page import="th.tg.dao.*,th.tg.factory.*" %>
<%@page import="th.tg.bean.*,mes.ra.util.*" %>
<%@page import="java.util.*,java.text.SimpleDateFormat" %>
<%@page	import="mes.framework.*,mes.framework.dao.*,mes.system.dao.*"%>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html><!-- InstanceBegin template="/Templates/new_view.dwt.jsp" codeOutsideHTMLIsLocked="true" -->

<!-- InstanceBeginEditable name="获得连接" -->
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES"/>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="获得过滤" -->
<%@page import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%
    String info="";
	info=request.getParameter("info");
	System.out.println("info:"+info);
	if (info==null)
		info="";
%>
<!-- InstanceEndEditable -->
<%   String eid =null;
    eid= request.getParameter("eid");
    final  Log log = LogFactory.getLog("searchset_manage.jsp");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;  
    
    int PageSize; //一页显示的记录数
	int RowCount; //记录总数
	int PageCount=0; //总页数
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
	<!-- InstanceBeginEditable name="设置SQL语句" -->
		<%
		 DAO_SearchSet dao = new DAO_SearchSet();
			
		if(info.equals("")||info==null){
			sql = dao.getAllSearchSets();
			sql = "select * from (" + sql +") a ";
		}else{
		 	
		   ArrayList<String> colist = new ArrayList<String>(5);
			colist.add("id".toLowerCase());
			colist.add("cSearchName".toLowerCase());
			colist.add("cWA".toLowerCase());
			colist.add("cFactory".toLowerCase());
			colist.add("cDscFactory".toLowerCase());
			colist.add("cCarType".toLowerCase());
			colist.add("cDscCarType".toLowerCase());
			colist.add("cRemark".toLowerCase());
			
		    info=info.trim();
		  sql = dao.getAllSearchSets();
		    LinkQuery link=new LinkQuery();
		    
		   StringBuffer wh= link.linkquery(info,colist,sql,conn);
		   sql = "select * from (" + sql +") a where " + wh.toString(); 
		}
		  sql=sql+"order by id asc";
			log.info("查询全部查询设置对象 的sql语句:  " + sql);
		List<SearchSet> list_SearchSet = new ArrayList<SearchSet>();
	%>
	<!-- InstanceEndEditable -->
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
			<!-- InstanceBeginEditable name="设置输出内容" -->
			<%	
			  SearchSet ss = new SearchSet();
			ss.setId(rs.getInt("id"));
			ss.setCsearchName(rs.getString("cSearchName"));
			ss.setCwa(rs.getString("cWA"));
			ss.setCfactory(rs.getString("cFactory"));
			ss.setCdscFactory(rs.getString("cDscFactory"));
			ss.setCcarType(rs.getString("cCarType"));
			ss.setCdscCarType(rs.getString("cDscCarType"));
			ss.setCremark(rs.getString("cRemark"));
			list_SearchSet.add(ss);
			%>
				<!-- InstanceEndEditable -->
			<%
				i++;
		   		rs.next();
	      }
	}
	%>				
<head>
<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
<!-- InstanceBeginEditable name="标题" -->
		<title>查询设置</title>
<!-- InstanceEndEditable -->
<meta http-equiv="Content-Language" content="zh-cn">
<style>
.head2{	height:22px;color:ffff00;
}
</style>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>

<body background="../images/background.jpg">
<!-- 引用通用脚本 -->
<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>

<div class="title"><strong><!-- InstanceBeginEditable name="标题2" -->查询设置管理<!-- InstanceEndEditable --></strong></div>
	<br>
	<div align="center"><!-- InstanceBeginEditable name="内容1" -->
<mes:table reSourceURL="../JarResource/" id="table1">
	<mes:thead>

   <mes:tr>  
		<td width="30">序号</td>
		<td width="150">查询设置名称</td>
		<td width="36">焊总装</td>
		<td width="58">工厂代码</td>
		<td width="80">工厂名称</td>
		<td width="60">车型代码</td>
		<td width="80">车型名称</td>
		<td width="80">备注</td>
		<td width="36">查看</td>
		<td width="36">更改</td>
		<td width="36">删除</td>
				
   </mes:tr>
</mes:thead>
<mes:tbody iterator="<%=list_SearchSet.iterator()%>" type="th.tg.bean.SearchSet" varName="ss">
<mes:tr id = "tr${ss.id}"> 
<%
      //SearchSet ss = (SearchSet)pageContext.getAttribute("ss");
   %>
    
		<mes:td>${ss.id}</mes:td>
		<mes:td>${ss.csearchName}</mes:td>
		<mes:td>${ss.cwa}</mes:td>
		<mes:td>${ss.cfactory}</mes:td>
		<mes:td>${ss.cdscFactory}</mes:td>
		<mes:td>${ss.ccarType}</mes:td>
		<mes:td>${ss.cdscCarType}</mes:td>
		<mes:td>${ss.cremark}</mes:td>
		<mes:td ><a href='searchset_view.jsp?int_id=${ss.id }&intPage=<%=intPage %>&info=<%=info%>'>查看</a></mes:td>
		<mes:td ><a href='searchset_update.jsp?int_id=${ss.id }&intPage=<%=intPage %>&info=<%=info%>'>更改</a></mes:td>
		<mes:td ><a href="#" title="点击将删除这条记录！" onClick="javascript:if(confirm('真的要删除这条记录吗？')) window.location.href='searchset_deleting.jsp?int_id=${ss.id }&intPage=<%=intPage %>&info=<%=info%>';return false;">删除</a></mes:td>


  </mes:tr>
  </mes:tbody>
<mes:tfoot>
   <mes:tr>
     <mes:td colspan="100%" align="center">
     <form  name="form" style="margin: 0px"  method="post" onSubmit="return checkinput(this)" action="searchset_manage.jsp">
	   <input type="hidden" value="<%=info%>" name="info" >	  
	  
		共<%=RowCount%>条  第<%=intPage%>页   共<%=PageCount%>页 	
		<%if(PageCount>1){
		out.println("跳转到第");
		out.print("<input size=\"1\"  name=\"page\" value=");%><%=intPage%><%out.println(">");
		out.println("页");
		}%>	
		<%if(intPage>1){    
		out.println("<a href=\"?page=1&info="+info+"\">第一页</a>");	
		out.println("<a href=\"?page="+(intPage-1)+"&info="+info+"\">上一页</a>");		
		}%>	 
		<%if(intPage<PageCount){
		out.println("<a href=\"?page="+(intPage+1)+"&info="+info+"\">下一页</a>");
		out.println("<a href=\"?page="+(PageCount)+"&info="+info+"\">最后一页</a>");
		}%>

       <a href='searchset_insert.jsp?intPage=<%=intPage %>&info=<%=info%>'>【添加新记录】</a>
       </form>
	  </mes:td>
   </mes:tr>  
   </mes:tfoot>  
</mes:table>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="过滤" -->
<form  name="form1" method="get" style="margin: 0px"  action="searchset_manage.jsp">
<table class="tbnoborder">
<tr>
<td class="tbnoborder">关键字查询:</td>
<td class="tdnoborder">
<input type=text value="<%=info %>" name="info" id="info" class="box1" size=20 onMouseOver="this.className='box3'" 
onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'"></td>
<td class="tdnoborder">
<mes:button id="s1" reSourceURL="../JarResource/" submit="true" value="查询"/>
</td>
</tr>
</table>
</form>
<div class="div_normal">
	关键字为：<a class="div_red">11<a class="div_red">"," </a>分开。<br>
<!-- InstanceEndEditable -->
</div>	
</body>

<%
		//释放资源
	
		}catch(Exception e)
		{		
			e.printStackTrace();
		}finally {			
			if(stmt!=null)stmt.close();
			if(conn!=null)conn .close();
			}
		
	%>	
<!-- InstanceBeginEditable name="script" -->
<script type="text/javascript">
function checkinput(thisform){
        var re =  /^[0-9]+$/;
		var i=0;
		var qm;
		var mm = document.getElementsByName("method");
		for(i=0;i<mm.length;i++)
			if(mm[i].checked==true)
				qm = mm[i].value;
				
		if((qm=="ById")&&(isNaN(thisform.process_info.value)==true)){
			alert('代码只允许输入数字！');
			thisform.process_info.value="";
			thisform.process_info.focus();
			return false;
		}
		if(<%=PageCount%>>1)
		if(!re.test(document.getElementsByName("page")[0].value ))
    {   alert("跳转页数应由正整数组成!");
	    document.getElementsByName("page")[0].value="";
	    return false;		
		}return true;
	}
	function selecttr(){
		var eid = '<%=eid==null?"-1":eid%>';
		
		if(eid!=-1){
			document.getElementById('tr'+eid).click();
		}		
	}selecttr();
</script>
<!-- InstanceEndEditable -->
<!-- InstanceEnd --></html>