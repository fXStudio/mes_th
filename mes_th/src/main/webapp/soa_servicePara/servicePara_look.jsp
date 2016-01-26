<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES" />
<%@ page import="com.qm.mes.framework.dao.*"%>
<%@ page import="com.qm.mes.framework.DataBaseType"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<script type="text/javascript" src="../JarResource/META-INF/tag/taglib_common.js"></script>
	</head>
	<%
		//获取参数
        String intpage=request.getParameter("intPage");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String info = request.getParameter("info");
		info = info==null?"":info;
        String eid = null;
	    eid=request.getParameter("eid");
		String serviceid = request.getParameter("serviceid");
		String paratype = request.getParameter("paratype");
		String paraname = request.getParameter("paraname");
		String servicename = request.getParameter("servicename");
		String paradesc = "";
		if (serviceid == null || paratype == null || paraname == null){
	%>
	<script type="text/javascript">
	alert("参数为空"); window.location.href="servicePara_manage.jsp";
</script>
	<%return;}
				try {
				//获取连接
				conn = Conn.getConn();
				stmt = conn.createStatement();
    			String sql = "";
				IDAO_Core dao = DAOFactory_Core.getInstance(DataBaseType
					.getDataBaseType(conn));
				sql = dao.getSQL_QueryServicePara(Integer
					.parseInt(serviceid), paratype, paraname);

				rs = stmt.executeQuery(sql);

				if (rs.next()) {
				paraname = rs.getString(1);
				paratype = rs.getString(2);
				paradesc = rs.getString(3);

				} else {
		%>
		<script>
<!-- 
alert("没有此服务！");window.location.href='servicePara_manage.jsp';
 -->
</script>
		<%
					if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
					}
		%>
	<body>
		<div class="title">
			查看服务参数信息
		</div>
		<br>
		<div align="center">

			<table width="526" class="tdnoborder">
			    <tr>
					<td width="125">
						服务名：
					</td>
					<td width="389">
						<input type="text" readonly name="servicename" class="box1"
							value="<%=servicename%>" size=50 onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>
				<tr>
					<td width="125">
						参数名：
					</td>
					<td width="389">
						<input type="text" readonly name="paraname" class="box1"
							value="<%=paraname%>" size=50 onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>

				<tr>
					<td>
						参数类型：
					</td>
					<td>
						<input type="text" readonly name="paratype" class="box1"
							value="<%=paratype%>" size=50 onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>

				<tr>
					<td>
						参数描述：
					</td>
					<td>
						<input type="text" readonly name="paradesc" class="box1"
							value="<%=paradesc%>" size=50 onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'" />
					</td>
				</tr>
			</table>
			<table class="tbnoborder">
				<tr>
					<td class="tdnoborder" colspan="2">
					<!-- 
						<span class="btn2_mouseout"
							onMouseOver="this.className='btn2_mouseover'"
							onMouseOut="this.className='btn2_mouseout'"> 
							<input class="btn2" type="button" value="返 回"	onclick="history.go(-1)">
						</span>
						 -->
						<mes:button id="button1" reSourceURL="../JarResource/"	onclick="func()" value="返 回"/>
					</td>
				</tr>
			</table>
		</div>
	</body>
	<script>
		<!--
			function func(){
				var pageIndex = <%=intpage%>;
				var int_id='<%=eid%>';		
				window.location.href = 'servicePara_manage.jsp?page='+ pageIndex+'&eid='+int_id+ '&info=<%=info%>';							
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









