<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<html>
	<!-- InstanceBegin template="/Templates/管理页面模版1.dwt.jsp" codeOutsideHTMLIsLocked="true" -->
	<!-- InstanceBeginEditable name="获得连接对象" -->
	<jsp:directive.page import="java.util.List" />
	<jsp:directive.page import="java.util.ArrayList" />
	<jsp:directive.page import="com.qm.mes.beans.Role" />
	<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES" />
	<%@page import="com.qm.mes.framework.dao.*,com.qm.mes.framework.DataBaseType"%>
	<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
	<%@page import="com.qm.mes.ra.util.*"%>
	<!-- InstanceEndEditable -->
	<%	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");%>
	<!-- InstanceBeginEditable name="设置SQL语句" -->
	<% String eid = null;//页面回选中用
	eid=request.getParameter("eid");
	ArrayList<String> colist = new ArrayList<String>(2);
    String user_rolerank="";
	user_rolerank=(String)session.getAttribute("user_rolerank");	
	if(user_rolerank==null)
		user_rolerank="";
	
	String info="";
	info=request.getParameter("info");
	if(info==null)
		info="";		
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	try{
    conn=Conn.getConn();
	stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	String enabled="";
	String sql="";    
	IDAO_UserManager dao = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(conn));
	//查询
	if(info==null||info.equals("")){
			sql= dao.getSQL_AllRoleNameNoForRank(user_rolerank);
			sql = "select * from (" + sql +") a";
		}
		else{					
			//选择要查询的字段			
			colist.add("nroleno".toLowerCase());
			colist.add("crolename".toLowerCase());					
			info=info.trim();			  
			sql= dao.getSQL_AllRoleNameNoForRank(user_rolerank);
			//模糊查询		
		   LinkQuery link=new LinkQuery();		    
		   StringBuffer wh= link.linkquery(info,colist,sql,conn);
		   sql = "select * from (" + sql +") a where " + wh.toString(); 		  	     
		}	 	
		sql=sql+" order by nroleno";
	
	List<Role> list=new ArrayList<Role>();
	rs=stmt.executeQuery(sql);
	rs.last();
    
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
		intPage=1;
	else{
		intPage=Integer.parseInt(strPage);
		if(intPage<1) intPage =1;
	} 
   		%>
	<!-- InstanceEndEditable -->
	<%
		//获得总记录数
		RowCount=rs.getRow();
		//总页数
		PageCount = (RowCount+PageSize-1) / PageSize;
		if(intPage>PageCount) intPage = PageCount;

		if(PageCount>0){
		    rs.absolute((intPage-1)*PageSize + 1);//将记录指针定位到下一显示位置
		    i = 0;
		    while(i<PageSize && !rs.isAfterLast()){%>
	<!-- InstanceBeginEditable name="设置输出内容" -->
	<%
				Role role=new Role();
				role.setRole_no(rs.getString(1));
				role.setRole_name(rs.getString(2));
				role.setCenabled(rs.getString(3));
				enabled=role.getCenabled();
				list.add(role);
			    i++;
			    rs.next();
			}
		}
		%>
	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<meta http-equiv="Content-Language" content="zh-cn" />
		<style>
.head2{	height:22px;color:ffff00;
}
</style>
		<!-- InstanceBeginEditable name="标题1" -->
		<title>角色管理</title>
		<!-- InstanceEndEditable -->
	</head>
	<body background="../images/background.jpg">
		<!-- 引用通用脚本 -->
		<script type="text/javascript"
			src="../JarResource/META-INF/tag/taglib_common.js"></script>
		<div align="center">
			<strong>角色管理</strong>
		</div>
		<br>
		<div align="center">
			<mes:table reSourceURL="../JarResource/" id="table1">
				<!-- InstanceBeginEditable name="表头" -->
				<mes:thead>
					<mes:tr>
						<td width="83">
							角色号
						</td>
						<td width="380">
							角色名
						</td>
						<td width="60">
							查看
						</td>
						<td width="60">
							更新
						</td>
						<td width="60">
							删除
						</td>
					</mes:tr>
				</mes:thead>
				<mes:tbody iterator="<%=list.iterator() %>" type="mes.beans.Role"
					varName="role">
					<mes:tr id="tr${role.role_no}">
						<%if(list.equals("")){%>
						<mes:td colspan="100%" align="center">无查询结果</mes:td>
						<%}else{
			  %>
						<!-- InstanceEndEditable   output.toString() -->

						<mes:td>${role.role_no}</mes:td>
						<mes:td>${role.role_name}</mes:td>
						<mes:td>
							<a
								href='role_look.jsp?roleno=${role.role_no }&intPage=<%=intPage %>&info=<%=info%>'>查看</a>
						</mes:td>
						<mes:td>
							<a
								href='role_update.jsp?roleno=${role.role_no }&intPage=<%=intPage %>&info=<%=info%>'>更新</a>
						</mes:td>
						<mes:td>
							<% if(enabled.equals("1")){%>
							<a href="#" title="点击将删除这条记录！"
								onClick="javascript:if(confirm('真的要删除这条纪录吗?')) window.location.href='role_deleteing.jsp?roleno=${role.role_no }&intPage=<%=intPage %>&info=<%=info%>';return false;">删除</a>
							<%}else{
					out.write("-");
				}
		       %>
						</mes:td>
						<%} %>

					</mes:tr>
				</mes:tbody>
				<mes:tfoot>
					<mes:tr>
						<mes:td colspan="100%" align="center">
							<form name="form" style="margin: 0px" method="get"
								onSubmit="return checkinput(this)" action="role_manage.jsp">
								<input type="hidden" value="<%=info%>" name="info">

								共
								<%=RowCount%>
								条 第
								<%=intPage%>
								页 共
								<%=PageCount%>
								页
								<%if(PageCount>1){
		 out.println("跳转到第");
		 out.print("<input size=\"1\"  name=\"page\" value=");%>
								<%=intPage%>
								<%out.println(">");
		 out.println("页");
		 }%>
								<%if(intPage>1){    
			out.println("<a href=\"#\" onclick=\"location='?page=1&info="+info+"'\">第一页</a>");	
			out.println("<a href=\"#\" onclick=\"location='?page="+(intPage-1)+"&info="+info+"'\">上一页</a>");		
		}%>
								<%if(intPage<PageCount){
			out.println("<a href=\"#\" onclick=\"location='?page="+(intPage+1)+"&info="+info+"'\">下一页</a>");
			out.println("<a href=\"#\" onclick=\"location='?page="+PageCount+"&info="+info+"'\">最后一页</a>");
		}%>
								<!-- InstanceBeginEditable name="添加新记录" -->
								<a href='role_add.jsp?intPage=<%=intPage %>&info=<%=info%>'>【添加新记录】</a>
								<!-- InstanceEndEditable -->
							</form>
						</mes:td>
					</mes:tr>
				</mes:tfoot>
			</mes:table>
			<!-- InstanceBeginEditable name="过滤" -->
			<form name="form1" method="post" style="margin: 0px"
				action="role_manage.jsp">
				<table class="tbnoborder">
					<tr>
						<td class="tbnoborder">
							关键字查询：&nbsp;&nbsp;
						</td>
						<td class="tdnoborder">
							<input type=text value="<%=info%>" name="info" id="info"
								class="box1" size=10 onMouseOver="this.className='box3'"
								onFocus="this.className='box3'"
								onMouseOut="this.className='box1'"
								onBlur="this.className='box1'">
						</td>
						<td class="tdnoborder">
							<mes:button id="s1" reSourceURL="../JarResource/" submit="true"
								value="查询" />
						</td>
					</tr>
				</table>
			</form>
			<div style="color:ff0000;font-size:9pt">
				参数为空的时候查询所有信息，查询条件以"," 分开。
			</div>		
			<script>
	function checkinput(thisform){
		var re =  /^[0-9]+$/;
		var i=0;
		var qm;
		var mm = document.getElementsByName("method");
		for(i=0;i<mm.length;i++)
			if(mm[i].checked==true)
				qm = mm[i].value;
				
		if((qm=="ById")&&(isNaN(thisform.info.value)==true)){
			alert('代码只允许输入数字！');
			thisform.info.value="";
			thisform.info.focus();
			return false;
		}
		if(<%=PageCount%>>1)
		if(!re.test(document.getElementsByName("page")[0].value ))
     {  alert("跳转页数应由正整数组成!");
	    document.getElementsByName("page")[0].value="";
	    return false;
    }	
		 return true;	    
	}
	function selecttr(){
		var eid = '<%=eid==null?"-1":eid%>';		
		if(eid!=-1){
			document.getElementById('tr'+eid).click();
		}		
	}selecttr();
</script>

			<!-- InstanceEndEditable -->
		</div>
	</body>
	<!-- InstanceEnd -->
</html>
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
