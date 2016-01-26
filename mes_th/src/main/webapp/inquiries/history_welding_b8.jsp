<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<%@page import="com.qm.th.tg.dao.*,com.qm.th.tg.factory.*"%>
<%@page import="com.qm.th.tg.bean.*,com.qm.mes.ra.util.*"%>
<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<%@page import="com.qm.mes.framework.*,com.qm.mes.framework.dao.*,com.qm.mes.system.dao.*"%>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
	<!-- InstanceBegin template="/Templates/new_view.dwt.jsp" codeOutsideHTMLIsLocked="true" -->

	<!-- InstanceBeginEditable name="获得连接" -->
	<jsp:directive.page import="java.util.List" />
	<jsp:directive.page import="java.util.ArrayList" />
	<jsp:useBean id="Conn" scope="page" class="com.qm.th.helpers.Conn_MES" />
	<script language="JavaScript" type="text/javascript"
		src="../My97DatePicker/WdatePicker.js"></script>
	<!-- InstanceEndEditable -->
	<!-- InstanceBeginEditable name="获得过滤" -->
	<%@page
		import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
	<%   
    final  Log log = LogFactory.getLog("history_welding_b8.jsp");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;  
    List<Part> list_part = new ArrayList<Part>();//总成集合
    WeldingSearchFactory factory_ws = new WeldingSearchFactory();//焊装查询工厂
    List<Welding_Stat> list_ws = new ArrayList<Welding_Stat>();//焊装统计集合
    SearchSetFactory factory_ss = new SearchSetFactory();//查询设置工厂
    SearchSet ss = new SearchSet();//查询设置对象
    String condition_One = null;//条件一
    String condition_Two = null;//条件二
    int radio_value = 0;//单选按钮值
    int searchsetid = 8;//查询设置序号
	String sql=null;
	String sql_part = "";
	String sql_ws = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	int table_width = 0;
	try{
	    //获取连接
	    con=Conn.getConn();
	    //searchsetid = Integer.parseInt(request.getParameter("searchsetid"));
	    ss = factory_ss.getSearchSetById(searchsetid,con);
	    radio_value = request.getParameter("radio1")==null?1:Integer.parseInt(request.getParameter("radio1"));
	    log.debug("RADIO选项："+radio_value);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		DAO_WeldingSearch dao = new DAO_WeldingSearch();
		String sql_temp1 = "";
		String sql_temp2 = "";
		String sql_temp3 = "";
		String sql_temp4 = "";
	    switch(radio_value){
	    	case 1:
	    	condition_One = request.getParameter("d11")==null?"":request.getParameter("d11");
	    	condition_Two = request.getParameter("d12")==null?"":request.getParameter("d12");
			list_part = factory_ws.getHistoryPartsByStartTimeEndTime_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02",con);
			sql_part = dao.getHistoryPartsByStartTimeEndTime_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02");
			list_ws = factory_ws.getHistoryStatByStartTimeEndTime_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02",con);
			sql_ws = dao.getHistoryStatByStartTimeEndTime_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02");
			sql_temp3 = "where cx.dWBegin>=convert(varchar(100),'" + condition_One + "',20) ";
			sql_temp3 = sql_temp3 + "and cx.dWBegin<=convert(varchar(100),'" + condition_Two + "',20)";
	    	break;
	    	case 2:
	    	condition_One = request.getParameter("startOrder")==""?"0":request.getParameter("startOrder");
	    	condition_Two = request.getParameter("endOrder")==""?"0":request.getParameter("endOrder");
			list_part = factory_ws.getHistoryPartsByStartOrderEndOrder_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02",con);
			sql_part = dao.getHistoryPartsByStartOrderEndOrder_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02");
			list_ws = factory_ws.getHistoryStatByStartOrderEndOrder_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02",con);
			sql_ws = dao.getHistoryStatByStartOrderEndOrder_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02");
			sql_temp3 = "where dwbegin>=(select max(dwbegin) from cardata where cseqno='" + condition_One + "') " ;
			sql_temp3 = sql_temp3 + " and dwbegin<=(select max(dwbegin) from cardata where cseqno='" + condition_Two + "') ";
			sql_temp3 = sql_temp3 + " and cseqno>='" + condition_One + "' and cseqno<='" + condition_Two + "' ";
	    	break;
	    	
	    	case 3:
	    	condition_One = request.getParameter("d13");
	    	condition_Two = request.getParameter("num")==""?"0":request.getParameter("num");
			list_part = factory_ws.getHistoryPartsByStartTimeNum_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02",con);
			sql_part = dao.getHistoryPartsByStartTimeNum_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02");
			list_ws = factory_ws.getHistoryStatByStartTimeNum_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02",con);
			sql_ws = dao.getHistoryStatByStartTimeNum_B8Q5(condition_One,condition_Two,ss.getCcarType(),"02");
			sql_temp3 = "where cx.dWBegin>=convert(varchar(100),'" + condition_One + "',20) ";
			sql_temp4 = " top "+condition_Two+" ";
	    	break;
	    	
	    }
	    System.out.println(request.getParameter("d11")+"***********" +request.getParameter("d12"));
	    if(radio_value==1&&request.getParameter("d11")==null&&request.getParameter("d12")==null
	    ||radio_value==1&&request.getParameter("d11").equals("")&&request.getParameter("d12").equals("")
	    ||radio_value==2&&request.getParameter("startOrder")==null&&request.getParameter("endOrder")==null
	    ||radio_value==2&&request.getParameter("startOrder").equals("")&&request.getParameter("endOrder").equals("")
	    ||radio_value==3&&request.getParameter("d13")==null&&request.getParameter("num")==null
	    ||radio_value==3&&request.getParameter("d13").equals("")&&request.getParameter("num").equals("")){
	    	list_part = factory_ws.getHistoryParts_B8Q5(ss.getCcarType(),"02",con);
	    	sql_part = dao.getHistoryParts_B8Q5(ss.getCcarType(),"02");
	    	list_ws = factory_ws.getHistoryStat_B8Q5(ss.getCcarType(),"02",con);
			sql_ws = dao.getHistoryStat_B8Q5(ss.getCcarType(),"02");
	    	sql_temp3 = "where cx.dWBegin is not null and cx.dABegin is null "; 
		}
	    sql_temp3 = sql_temp3 + " and substring(cx.cCarNo,6,1)=" + ss.getCcarType();
		for(int i=0;i<list_part.size();i++){
			sql_temp1 = sql_temp1 + "max(aa."+list_part.get(i).getName().trim()+") as '"+list_part.get(i).getName().trim()+"',";
			sql_temp1 = sql_temp1 + "max(aa."+list_part.get(i).getName().trim()+"数量) as '"+list_part.get(i).getName().trim()+"数量'";
			sql_temp2 = sql_temp2 + "case when itfassnameid="+list_part.get(i).getId()+" then cQadno else null end as '"+list_part.get(i).getName().trim()+"',";
			sql_temp2 = sql_temp2 + "case when itfassnameid="+list_part.get(i).getId()+" then iTFASSNum else null end as '"+list_part.get(i).getName().trim()+"数量',";
			if(i != list_part.size()-1){
				sql_temp1 = sql_temp1 + ",";
			}
		}
	//	sql = "select * from cardata cd left join(";
		sql = "select"+sql_temp4+" max(cSEQNo) as seq,max(cCarNo) as kin,max(dWBegin) as dwbegin,max(WUpTime) as wuptime";
		if(!sql_temp1.equals(""))
			sql = sql + ",";
		sql = sql + sql_temp1;
		sql = sql +  " from(select ";
		sql = sql + sql_temp2;
		sql = sql + "* from(select c.*,pn.*,cx.cSEQNo,cx.cCarNo,cx.dWBegin,WUpTime from history_cardata_d c "; 
		sql = sql + "right join tfassname pn on pn.id = c.itfassnameid ";
		sql = sql + "inner join history_cardata cx on cx.cCarNo =c.icarid ";
		sql = sql + " and substring(cseqno,1,2)='02'";
		sql = sql + sql_temp3;
	//	sql = sql + "and cx.dWBegin<=convert(varchar(100)," + startTime + ",20) ";
	//	sql = sql + "and cx.dWBegin>=convert(varchar(100)," + endTime + ",20)";
		sql = sql + ")a)aa group by aa.icarid order by dwbegin,seq";

		log.info("sql:  " + sql);
		table_width = 350+155*list_part.size();
	%>
	<!-- InstanceEndEditable -->
	<%
		rs = stmt.executeQuery(sql);
	%>
	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<!-- InstanceBeginEditable name="标题" -->
		<title>查询设置</title>
		<!-- InstanceEndEditable -->
		<meta http-equiv="Content-Language" content="zh-cn">
		<style>
.head2 {
	height: 22px;
	color: ffff00;
}
</style>
		<!-- InstanceBeginEditable name="head" -->
		<!-- InstanceEndEditable -->
	</head>
	<body background="../images/background.jpg">
		<!-- 引用通用脚本 -->
		<script type="text/javascript"
			src="../JarResource/META-INF/tag/taglib_common.js"></script>

		<div class="title">
			<strong>
				<!-- InstanceBeginEditable name="标题2" -->B8焊装查询统计<!-- InstanceEndEditable -->
			</strong>
		</div>
		<br>
		<div align="center">
			<!-- InstanceBeginEditable name="内容1" -->

			<form action="history_welding_b8.jsp">
				<table>
					<tr>
						<td>
							<input type="radio" name="radio1" value="1">
							开始时间
							<input id="d11" name="d11" type="text"
								value="<%=radio_value != 1 || condition_One == null ? ""
						: condition_One%>" />
							<img
								onclick="WdatePicker({el:'d11',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								src="../My97DatePicker/skin/datePicker.gif" width="16"
								height="22" align="absmiddle">
							--结束时间
							<input id="d12" name="d12" type="text"
								value="<%=radio_value != 1 || condition_Two == null ? ""
						: condition_Two%>" />
							<img
								onclick="WdatePicker({el:'d12',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								src="../My97DatePicker/skin/datePicker.gif" width="16"
								height="22" align="absmiddle">
						</td>
					</tr>
					<tr>
						<td>
							<input type="radio" name="radio1" value="2">
							开始顺序号
							<input name="startOrder"
								value="<%=radio_value != 2 || condition_One == null ? ""
						: condition_One%>">
							--结束顺序号
							<input name="endOrder"
								value="<%=radio_value != 2 || condition_Two == null ? ""
						: condition_Two%>">
							<mes:button id="s1" reSourceURL="../JarResource/" submit="true"
								value="查询" />
						</td>
					</tr>
					<tr>
						<td>
							<input type="radio" name="radio1" value="3">
							开始时间
							<input id="d13" name="d13" type="text"
								value="<%=radio_value != 3 || condition_One == null ? ""
						: condition_One%>" />
							<img
								onclick="WdatePicker({el:'d13',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								src="../My97DatePicker/skin/datePicker.gif" width="16"
								height="22" align="absmiddle">
							--数量
							<input name="num"
								value="<%=radio_value != 3 || condition_Two == null ? ""
						: condition_Two%>">
						</td>
					</tr>
				</table>
				<input type="hidden" name="searchsetid" value="<%=searchsetid%>">
				<mes:button id="s2" reSourceURL="../JarResource/" value="导出查询"
					onclick="xls_select()" />
				<mes:button id="s3" reSourceURL="../JarResource/" value="导出统计"
					onclick="xls_stat()" />
			</form>
		</div>
		<div align="center">
			<style>
td {
	border-width: 1pt;
	border-style: solid
}
</style>
			<style>
tr {
	border-width: 1pt;
	border-style: solid
}
</style>
			<p></p>
			<p></p>
			<table border="0" cellspacing="0" cellpadding="0" id="table1" width="<%=table_width %>">
				<tr>
					<td align="center" width="60">
						顺序号
					</td>
					<td align="center" width="90">
						KIN
					</td>
					<%
						for (int i = 0; i < list_part.size(); i++) {
					%>
					<td align="center" width="120">
						<%=list_part.get(i).getName()%>
					</td>
					<td align="center" width="20">
						<%//list_part.get(i).getName()%>
						数量
					</td>
					<%
						}
					%>
					<td align="center" width="180">
						上线时间
					</td>
					<td align="center" width="40">
						上线次数
					</td>
				</tr>
				<%
					rs.isBeforeFirst();
					while (rs.next()) {
				%>
				<tr>
					<td align="center"><%=rs.getString("seq") == null ? "-" : rs
							.getString("seq")%></td>
					<td align="center"><%=rs.getString("kin") == null ? "-" : rs
							.getString("kin")%></td>
					<%
						for (int i = 0; i < list_part.size(); i++) {
					%>
					<td align="center">
						<%=rs.getString(list_part.get(i).getName()
										.trim()) == null ? "-" : rs
								.getString(list_part.get(i).getName().trim())%>
					</td>
					<td align="center">
						<%=rs.getString(list_part.get(i).getName()
										.trim()
										+ "数量") == null ? "-" : rs
								.getString(list_part.get(i).getName().trim()
										+ "数量")%>
					</td>
					<%
						}
					%>
					<td align="center"><%=rs.getString("dwbegin") == null ? "-" : 
					sdf.format(sdf.parse(rs.getString("dwbegin")))%></td>
				
				<td align="center"><%=rs.getString("wuptime") == null ? "0" : 
				rs.getString("wuptime")%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
		<br>
		<div align="center">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
						零件号
					</td>
					<td align="center">
						数量
					</td>
				</tr>
				<%
					for (int i = 0; i < list_ws.size(); i++) {
						if(list_ws.get(i).getNo() !=null ){
				%>
				<tr>
					<td align="center"><%=list_ws.get(i).getNo()%></td>
					<td align="center"><%=list_ws.get(i).getNum()%></td>
				</tr>
				<%
						}
					}
				%>
			</table>
		</div>
	</body>

	<%
		//释放资源

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}
	%>
	<!-- InstanceBeginEditable name="script" -->
	<script type="text/javascript">
function check_radio(){
	document.getElementsByName("radio1")[<%=radio_value - 1%>].checked = true;
}check_radio();
function xls_select(){
	window.location.href="welding_select_xls.jsp?sql=<%=sql%>&sql_part=<%=sql_part%>&downloadname=焊装查询&partnum=<%=list_part.size()%>";
}
function xls_stat(){
	window.location.href="welding_select_xls.jsp?sql=<%=sql_ws%>&sql_part=<%=sql_part%>&downloadname=焊装统计";
}

function computeNum(col1,col2,colors){
	var table = document.getElementById('table1');
	var rows = table.rows;
	
	var curname = "";
	var currows = new Array();
	var n=0;
	for(var i=1;i<rows.length;i++){
		 
		var value = rows[i].cells[col1].innerHTML;
		if(curname == ""){
			curname = value;
		}
		if(curname != value){
			n++;
			curname = value;
			var num = currows.length;
			while(currows.length>0){
				var td = currows.pop();
				td.innerHTML = num;
				td.style.backgroundColor=colors[n%2];
			}
		}
		if(rows[i].cells[col2].innerHTML != "- "){
			currows.push(rows[i].cells[col2]);
		}
	}
	n++;
	var num = currows.length;
	while(currows.length>0){
		var td = currows.pop();
		td.innerHTML = num;
		td.style.backgroundColor=colors[n%2];
	}
	
	
	
}
var part_count = <%=list_part.size()%>;
for(var i=1;i<=part_count;i++){
	
	computeNum(i*2,i*2+1,['#87CEFA','#AFEEEE']);
}

</script>
	<!-- InstanceEndEditable -->
	<!-- InstanceEnd -->
</html>