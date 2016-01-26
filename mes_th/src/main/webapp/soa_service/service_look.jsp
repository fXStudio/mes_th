 <%@ page language="java" import="java.sql.*,java.util.List,java.util.ArrayList" contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES" />
<%@page import="com.qm.mes.framework.dao.*"%>
<%@ page import="com.qm.mes.framework.DataBaseType"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
	<%
		//获取参数
        String intpage=request.getParameter("intPage");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String serviceid = request.getParameter("serviceid");
		String servicename = "";
		String classname = "";
		String description = "";
		String namespace = "";
        String info = request.getParameter("info");
		info = info==null?"":info;
		if (serviceid == null) {
	%>
	<script type="text/javascript">
	alert("参数为空"); window.location.href="service_manage.jsp";
</script>
	<%
		return;
		}
		try {
			//获取连接
			conn = Conn.getConn();
			stmt = conn.createStatement();
			String sql = "";
			IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
				.getDataBaseType(conn));
			sql = dao.getSQL_QueryServiceForServiceid(Integer
				.parseInt(serviceid));
		    List<String> list = new ArrayList<String>();
			rs = stmt.executeQuery(sql);

				if (rs.next()) {
					servicename = rs.getString(2);
					classname = rs.getString(3);
					description = rs.getString(4);
					namespace = rs.getString(5);
					list.add(servicename);
				} else {
		%>
		<script>
<!-- 
alert("没有此服务！");window.location.href='service_manage.jsp';
 -->
</script>
		<%
			}
		%>

	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
	</head>
	<body>
		<div class="title">
			查看服务定义信息
		</div>
		<br>
		<div align="center">

			<table class="tdnoborder" width="526">
				<tr>
					<td width="125">
						服务号：
					</td>
					<td width="389">
						<input type="text" readonly name="serviceid" class="box1"
							value="<%=serviceid%>" size=50
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>

				<tr>
					<td>
						服务名：
					</td>
					<td>
						<input type="text" readonly name="servicename" class="box1"
							value="<%=servicename%>" size=50
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>

				<tr>
					<td>
						对应类名：
					</td>
					<td>
						<input type="text" readonly name="classname" class="box1"
							value="<%=classname%>" size=50
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>

				<tr>
					<td>
						业务描述：
					</td>
					<td>
						<input type="text" readonly name="description" class="box1"
							value="<%=description%>" size=50
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>

				<tr>
					<td>
						命名空间：
					</td>
					<td>
						<input type="text" readonly name="namespace" class="box1"
							value="<%=namespace%>" size=50
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>
			</table>
			<br>
	<table class="tbnoborder" width="560">
   <%
        String sqlParas = "";
	    for(int i=0;i<list.size();i++){
        	sqlParas=dao.getSQL_QueryAllServiceParas(list.get(i),"ByService");	
    	    rs=stmt.executeQuery(sqlParas);
	        while (rs.next())
	        {
	        %>
   <tr>
   <td width="180">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拥有服务参数名：&nbsp;&nbsp;</td>
	<td>
	<a href="#" onclick="window.location.href='../soa_servicePara/servicePara_manage.jsp?info=<%=rs.getString(2) %>&method=ByService';return false;"><%=rs.getString(3) %></a>   
    </td>
   </tr>
     <%
	        }
	    }
     %>
  </table>
			
			<br>
			<table class="tbnoborder">
				<tr>
					<td class="tdnoborder" colspan="2">
						<mes:button id="button1" reSourceURL="../JarResource/" onclick="func()" value="返 回"/>
					</td>
				</tr>
			</table>
		</div>
	</body>
	<script>
		<!--
			function func(){
				var pageIndex = <%=intpage%>;
				var int_id=<%=serviceid%>;		
				window.location.href = 'service_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';		
			}
		-->
	</script>
</html>





<%
	}catch(Exception e)
	{
		throw e;
	}finally{
		//释放资源
		if(rs!=null)rs.close();
		if(stmt!=null)stmt.close();
		if(conn!=null)conn.close();
	}
%>









