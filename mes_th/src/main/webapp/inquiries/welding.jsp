<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<%@page import="th.tg.dao.*,th.tg.factory.*"%>
<%@page import="th.tg.bean.*,mes.ra.util.*"%>
<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<%@page import="mes.framework.*,mes.framework.dao.*,mes.system.dao.*"%>

<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
	<!-- InstanceBegin template="/Templates/new_view.dwt.jsp" codeOutsideHTMLIsLocked="true" -->

	<!-- InstanceBeginEditable name="�������" -->
	<jsp:directive.page import="java.util.List" />
	<jsp:directive.page import="java.util.ArrayList" />
	<jsp:useBean id="Conn" scope="page" class="common.Conn_MES" />
	<script language="JavaScript" type="text/javascript"
		src="../My97DatePicker/WdatePicker.js"></script>
	<!-- InstanceEndEditable -->
	<!-- InstanceBeginEditable name="��ù���" -->
	<%@page
		import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
	<%   
    final  Log log = LogFactory.getLog("welding_manage.jsp");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;  
    List<Part> list_part = new ArrayList<Part>();//�ܳɼ���
    WeldingSearchFactory factory_ws = new WeldingSearchFactory();//��װ��ѯ����
    List<Welding_Stat> list_ws = new ArrayList<Welding_Stat>();//��װͳ�Ƽ���
    SearchSetFactory factory_ss = new SearchSetFactory();//��ѯ���ù���
    SearchSet ss = new SearchSet();//��ѯ���ö���
    String condition_One = null;//����һ
    String condition_Two = null;//������
    int radio_value = 0;//��ѡ��ťֵ
    int searchsetid = 0;//��ѯ�������
	String sql=null;
	String sql_part = "";
	String sql_ws = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	int table_width = 0;
	try{
	    //��ȡ����
	    con=Conn.getConn();
	    searchsetid = Integer.parseInt(request.getParameter("searchsetid"));
	    ss = factory_ss.getSearchSetById(searchsetid,con);
	    radio_value = request.getParameter("radio1")==null?1:Integer.parseInt(request.getParameter("radio1"));
	    log.debug("RADIOѡ�"+radio_value);
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
			list_part = factory_ws.getPartsByStartTimeEndTime(condition_One,condition_Two,ss.getCcarType(),searchsetid,con);
			sql_part = dao.getPartsByStartTimeEndTime(condition_One,condition_Two,ss.getCcarType(),searchsetid);
			list_ws = factory_ws.getStatByStartTimeEndTime(condition_One,condition_Two,ss.getCcarType(),searchsetid,con);
			sql_ws = dao.getStatByStartTimeEndTime(condition_One,condition_Two,ss.getCcarType(),searchsetid);
			sql_temp3 = "where cx.dWBegin>=convert(varchar(100),'" + condition_One + "',20) ";
			sql_temp3 = sql_temp3 + "and cx.dWBegin<=convert(varchar(100),'" + condition_Two + "',20)";
	    	break;
	    	case 2:
	    	condition_One = request.getParameter("startOrder")==""?"0":request.getParameter("startOrder");
	    	condition_Two = request.getParameter("endOrder")==""?"0":request.getParameter("endOrder");
			list_part = factory_ws.getPartsByStartOrderEndOrder(condition_One,condition_Two,ss.getCcarType(),searchsetid,con);
			sql_part = dao.getPartsByStartOrderEndOrder(condition_One,condition_Two,ss.getCcarType(),searchsetid);
			list_ws = factory_ws.getStatByStartOrderEndOrder(condition_One,condition_Two,ss.getCcarType(),searchsetid,con);
			sql_ws = dao.getStatByStartOrderEndOrder(condition_One,condition_Two,ss.getCcarType(),searchsetid);
			sql_temp3 = "where dwbegin>=(select max(dwbegin) from cardata where cseqno='" + condition_One + "') " ;
			sql_temp3 = sql_temp3 + " and dwbegin<=(select max(dwbegin) from cardata where cseqno='" + condition_Two + "') ";
			sql_temp3 = sql_temp3 + " and cseqno>='" + condition_One + "' and cseqno<='" + condition_Two + "' ";
	    	break;
	    	
	    	case 3:
	    	condition_One = request.getParameter("d13");
	    	condition_Two = request.getParameter("num")==""?"0":request.getParameter("num");
			list_part = factory_ws.getPartsByStartTimeNum(condition_One,condition_Two,ss.getCcarType(),searchsetid,con);
			sql_part = dao.getPartsByStartTimeNum(condition_One,condition_Two,ss.getCcarType(),searchsetid);
			list_ws = factory_ws.getStatByStartTimeNum(condition_One,condition_Two,ss.getCcarType(),searchsetid,con);
			sql_ws = dao.getStatByStartTimeNum(condition_One,condition_Two,ss.getCcarType(),searchsetid);
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
	    	list_part = factory_ws.getParts(ss.getCcarType(),searchsetid,con);
	    	sql_part = dao.getParts(ss.getCcarType(),searchsetid);
	    	list_ws = factory_ws.getStat(ss.getCcarType(),searchsetid,con);
			sql_ws = dao.getStat(ss.getCcarType(),searchsetid);
	    	sql_temp3 = "where cx.dWBegin is not null and cx.dABegin is null and cx.dCp6Begin is null "; 
		}
	    sql_temp3 = sql_temp3 + " and substring(cx.cCarNo,6,1) in(" + ss.getCcarType() + ")";

	    if(!"".equals(ss.getCfactory()))
	   	   sql_temp3 = sql_temp3 + " and substring(cx.cSEQNo, 1, 2) in (" + ss.getCfactory() + ") ";
		
	    for(int i=0;i<list_part.size();i++){
			sql_temp1 = sql_temp1 + "max(aa."+list_part.get(i).getName().trim()+") as '"+list_part.get(i).getName().trim()+"',";
			sql_temp1 = sql_temp1 + "max(aa."+list_part.get(i).getName().trim()+"����) as '"+list_part.get(i).getName().trim()+"����'";
			sql_temp2 = sql_temp2 + "case when itfassnameid="+list_part.get(i).getId()+" then cQadno else null end as '"+list_part.get(i).getName().trim()+"',";
			sql_temp2 = sql_temp2 + "case when itfassnameid="+list_part.get(i).getId()+" then iTFASSNum else null end as '"+list_part.get(i).getName().trim()+"����',";
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
		sql = sql + "* from(select c.*,pn.*,cx.cSEQNo,cx.cCarNo,cx.dWBegin,WUpTime from cardata_d c "; 
		sql = sql + "right join tfassname pn on pn.id = c.itfassnameid ";
		sql = sql + "inner join cardata cx on cx.cCarNo =c.icarid ";
		sql = sql + sql_temp3;
	//	if(searchsetid==3)
	//		sql = sql + " and substring(ccarno,5,1)<>'7' ";
	//	sql = sql + "and cx.dWBegin<=convert(varchar(100)," + startTime + ",20) ";
	//	sql = sql + "and cx.dWBegin>=convert(varchar(100)," + endTime + ",20)";
		sql = sql + ")a)aa group by aa.icarid order by dwbegin";
	//	sql = sql + ")cc on cd.id = cc.icarid";

		
		table_width = 350+155*list_part.size();
	%>
	<!-- InstanceEndEditable -->
	<%
		rs = stmt.executeQuery(sql);log.info("sql:  " + sql);
	%>
	<head>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">
		<!-- InstanceBeginEditable name="����" -->
		<title>��ѯ����</title>
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
		<!-- ����ͨ�ýű� -->
		<script type="text/javascript"
			src="../JarResource/META-INF/tag/taglib_common.js"></script>

		<div class="title">
			<strong>
				<!-- InstanceBeginEditable name="����2" --><%=ss.getCsearchName()%><!-- InstanceEndEditable -->
			</strong>
		</div>
		<br>
		<div align="center">
			<!-- InstanceBeginEditable name="����1" -->

			<form action="welding.jsp" id="form1" name="form1">
				<table>
					<tr>
						<td>
							<input type="radio" name="radio1" value="1">
							��ʼʱ��
							<input id="d11" name="d11" type="text"
								value="<%=radio_value != 1 || condition_One == null ? ""
						: condition_One%>" />
							<img
								onclick="WdatePicker({el:'d11',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								src="../My97DatePicker/skin/datePicker.gif" width="16"
								height="22" align="absmiddle">
							--����ʱ��
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
							��ʼ˳���
							<input name="startOrder"
								value="<%=radio_value != 2 || condition_One == null ? ""
						: condition_One%>">
							--����˳���
							<input name="endOrder"
								value="<%=radio_value != 2 || condition_Two == null ? ""
						: condition_Two%>">
							<mes:button id="s1" reSourceURL="../JarResource/" submit="true"
								value="��ѯ" />
						</td>
					</tr>
					<tr>
						<td>
							<input type="radio" name="radio1" value="3">
							��ʼʱ��
							<input id="d13" name="d13" type="text"
								value="<%=radio_value != 3 || condition_One == null ? ""
						: condition_One%>" />
							<img
								onclick="WdatePicker({el:'d13',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								src="../My97DatePicker/skin/datePicker.gif" width="16"
								height="22" align="absmiddle">
							--����
							<input name="num"
								value="<%=radio_value != 3 || condition_Two == null ? ""
						: condition_Two%>">
						</td>
					</tr>
					<input type="hidden" value="<%=sql%>" name="sql" id="sql">
					<input type="hidden" value="<%=sql_part%>" name="sql_part" id="sql_part">
					<input type="hidden" value="��װ��ѯ" name="downloadname" id="downloadname">
					<input type="hidden" value="<%=list_part.size()%>" name="partnum" id="partnum">
				</table>
				<input type="hidden" name="searchsetid" value="<%=searchsetid%>">
				<mes:button id="s2" reSourceURL="../JarResource/" value="������ѯ"
					onclick="xls_select()" />
				<mes:button id="s3" reSourceURL="../JarResource/" value="����ͳ��"
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
						˳���
					</td>
					<td align="center" width="90">
						KIN
					</td>
					<%
						for (int i = 0; i < list_part.size(); i++) {
					%>
					<td align="left" width="190">
						<%=list_part.get(i).getName()%>
					</td>
					<td align="center" width="20">
						<%//list_part.get(i).getName()%>
						����
					</td>
					<%
						}
					%>
					<td align="center" width="180">
						����ʱ��
					</td>
					<td align="center" width="40">
						���ߴ���
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
					<td align="left">
						<%=rs.getString(list_part.get(i).getName()
										.trim()) == null ? "-" : rs
								.getString(list_part.get(i).getName().trim())%>
					</td>
					<td align="center">
						<%=rs.getString(list_part.get(i).getName()
										.trim()
										+ "����") == null ? "-" : rs
								.getString(list_part.get(i).getName().trim()
										+ "����")%>
					</td>
					<%
						}
					%>
					<td align="center"><%=rs.getString("dwbegin") == null ? "-" : 
					sdf.format(sdf.parse(rs.getString("dwbegin")))%></td>
				
				<td align="center"><%=rs.getString("wuptime") == null ? "0" : rs
							.getString("wuptime")%></td>
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
						�����
					</td>
					<td align="center">
						����
					</td>
				</tr>
				<%
					for (int i = 0; i < list_ws.size(); i++) {
						if(list_ws.get(i).getNo() !=null ){
				%>
				<tr>
					<td align="left"><%=list_ws.get(i).getNo()%></td>
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
		//�ͷ���Դ

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
function select(){
	document.form1.action="welding.jsp";
	document.form1.method="get";	
	document.form1.submit();
}
function xls_select(){
	document.form1.action="welding_select_xls.jsp";
	document.form1.method="post";
	document.form1.submit();
}
function xls_stat(){
	window.location.href="welding_select_xls.jsp?sql=<%=sql_ws%>&sql_part=<%=sql_part%>&downloadname=��װͳ��";
}

function computeNum(col1,col2,colors){
	var table = document.getElementById('table1');
	var rows = table.rows;
	
	var curname = "";
	var currows = new Array();
	var n=0;
	for(var i=1;i<rows.length;i++){
		 
		var value = rows[i].cells[col1].innerHTML;
		//��һ�β���
		if(curname == ""){
			curname = value;
		}
		//����ȵ�ʱ��
		if(curname != value){
			n++;
			curname = value;
			//��ȡ�����е�Ԫ����������Ԫ����ͬ���ģ�
			var num = currows.length;
			//ѭ������ÿһ���Ѿ��洢�ĵ�Ԫ�����
			while(currows.length>0){
				//���������������ݼ��١�
				var td = currows.pop();
				td.innerHTML = num;
				td.style.backgroundColor=colors[n%2];
			}
		}
		//����������
		if(rows[i].cells[col2].innerHTML != "- "){
			//��������뵥Ԫ�����
			currows.push(rows[i].cells[col2]);
		}
	}
	n++;
	//��ȡ�����е�Ԫ����������Ԫ����ͬ���ģ�
	var num = currows.length;
	//ѭ������ÿһ���Ѿ��洢�ĵ�Ԫ�����
	while(currows.length>0){
		//���������������ݼ��١�
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