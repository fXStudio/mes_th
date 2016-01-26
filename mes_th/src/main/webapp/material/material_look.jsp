<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<%@page import="com.qm.mes.system.factory.*"%>
<%@page import="com.qm.mes.system.elements.*"%>
<%@page import="com.qm.mes.system.dao.*"%>
<%@page import="com.qm.mes.framework.*"%>
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.util.ArrayList" />
<html>
	<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES" />
	<%
		String element_name = request.getParameter("element_name");
		if (element_name == null) {
			out
			.println("<script>alert(\"参数为空\"); window.location.href=\"material_view.jsp\";</script>");
			return;
		}
	%>
	<%
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {//获取连接
			conn = Conn.getConn();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
	%>
	<%
			IMaterialFactory factory;
			IMaterialTypeFactory factoryType;
			IMaterialCharacterFactory factoryCharacter;
			IMaterialidentifyFactory factoryIdentify;

			factory = (IMaterialFactory) FactoryAdapter
			.getFactoryInstance(IMaterialFactory.class.getName());
			factoryType = (IMaterialTypeFactory) FactoryAdapter
			.getFactoryInstance(IMaterialTypeFactory.class
					.getName());
			factoryCharacter = (IMaterialCharacterFactory) FactoryAdapter
			.getFactoryInstance(IMaterialCharacterFactory.class
					.getName());
			factoryIdentify = (IMaterialidentifyFactory) FactoryAdapter
			.getFactoryInstance(IMaterialidentifyFactory.class
					.getName());

			IDAO_MaterialCharacter daoCharacter = (IDAO_MaterialCharacter) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(conn),
					IDAO_MaterialCharacter.class);
			IDAO_MaterialIdentify daoIdentify = (IDAO_MaterialIdentify) DAOFactoryAdapter
			.getInstance(DataBaseType.getDataBaseType(conn),
					IDAO_MaterialIdentify.class);
			IMaterial newType = factory.queryElement(new Integer(
			element_name), conn);
			if (newType == null) {
				out
				.println("<script>alert(\"没有查询结果！\");window.location.href='material_view.jsp';</script>");
			}

			String sqlHaveCharacter = daoCharacter
			.getSQL_queryCharactersById(newType.getId());
			String sqlHaveIdentify = daoIdentify
			.getSQL_queryIdentifyById(newType.getId());
			List<IMaterialCharacter> listHaveCharacter = new ArrayList<IMaterialCharacter>();
			List<IMaterialidentify> listHaveIdentify = new ArrayList<IMaterialidentify>();
			IMaterialType type = factoryType.queryElement(newType
			.getMaterialTypeId(), conn);
			rs = stmt.executeQuery(sqlHaveCharacter);
			while (rs.next()) {
				IMaterialCharacter cha = factoryCharacter.createElement();
				cha.setId(rs.getInt(1));
				cha.setName(rs.getString(2));
				listHaveCharacter.add(cha);
			}
			rs = stmt.executeQuery(sqlHaveIdentify);
			while (rs.next()) {
				IMaterialidentify imid = factoryIdentify.createElement();
				imid.setId(rs.getInt(1));
				imid.setName(rs.getString(2));
				listHaveIdentify.add(imid);
			}
	%>
	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<title>物料信息</title>
	</head>
	<body>
		<div class="title">
			查看物料信息
		</div>
		<br>
		<div align="center">
			<table class="tbnoborder">
				<tr>
					<td width="180">
						元素号 - 物料名：
					</td>
					<td width="266">
						<input readonly size="50" type="text"
							value="<%=newType.getId()%> - <%=newType.getName()%>"
							class="box1" onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'">
					</td>
				</tr>
				<tr>
					<td>
						描述信息：
					</td>
					<td>
						<input readonly size="50" type="text"
							value="<%=newType.getDescription()==null?"":newType.getDescription()%>" class="box1"
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'">
					</td>
				</tr>
				<tr>
					<td>
						修改时间：
					</td>
					<td>
						<input readonly size="50" type="text"
							value="<%=newType.getUpdateDateTime()%>" class="box1"
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'">
					</td>
				</tr>
				<tr>
					<td>
						操作用户号：
					</td>
					<td>
						<input readonly size="50" type="text"
							value="<%=newType.getUpdateUserId()%>" class="box1"
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'">
					</td>
				</tr>
				<tr><td height="10"></td></tr>
				<tr>
					<td>
						物料类型号 - 类型名：
					</td>
					<td>
						<input readonly size="50" type="text"
							value="<%=newType.getMaterialTypeId()%> - <%=type.getName()%>"
							class="box1" onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'">
					</td>
				</tr>
				<tr><td height="10"></td></tr>
				<tr>
					<td>
						物料特征号 - 特征名：
					</td>
					<td>
						<%
									for (int j = 0; j < listHaveCharacter.size(); j++) {
									IMaterialCharacter ich = factoryCharacter.createElement();
									ich = (IMaterialCharacter) listHaveCharacter.get(j);
						%>
						<input readonly size="50" type="text"
							value="<%=ich.getId()%> - <%=ich.getName()%>" class="box1"
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'"><br>
						<%
						}
						%>
					</td>
				</tr>
				<tr><td height="10"></td></tr>
				<tr>
					<td>
						物料标识号 - 标识名：
					</td>
					<td>
						<%
									for (int j = 0; j < listHaveIdentify.size(); j++) {
									IMaterialidentify ide = factoryIdentify.createElement();
									ide = (IMaterialidentify) listHaveIdentify.get(j);
						%>

						<input readonly size="50" type="text"
							value="<%=ide.getId()%> - <%=ide.getName()%>" class="box1"
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'"><br>
						<%
						}
						%>
					</td>
				</tr>
				<tr><td height="10"></td></tr>
				<tr>
					<td>
						版本号：
					</td>
					<td>
						<input readonly size="50" type="text"
							value="<%=newType.getVersion()%>" class="box1"
							onMouseOver="this.className='box3'"
							onFocus="this.className='box3'"
							onMouseOut="this.className='box1'" onBlur="this.className='box1'">
					</td>
				</tr>
			</table>
			<table class="tbnoborder">
				<tr>
					<td class="tdnoborder" colspan="2">
						<span class="btn2_mouseout"
							onMouseOver="this.className='btn2_mouseover'"
							onMouseOut="this.className='btn2_mouseout'"> <input
								class="btn2" type="button" value="返 回"
								onClick="history.go(-1)"> </span>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<%
		//释放资源
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	} catch (Exception e) {
		if (conn != null)
			conn.close();
		throw e;
	}
%>









