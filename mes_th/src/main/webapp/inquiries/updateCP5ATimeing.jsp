<%@ page language="java" import="java.sql.*" contentType="text/html;charset=gb2312"%>
<%@ page import="java.sql.*" %>
<%@ page import ="mes.framework.DataBaseType"%>
<%@page	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<jsp:useBean id="Conn" scope="page" class="common.Conn_MES"/>
	
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
<meta http-equiv="Content-Language" content="zh-cn">
<%	response.setHeader("Pragma","No-cache");  
   	response.setHeader("Cache-Control","no-cache");  
  	response.setDateHeader("Expires", 0);

 %>
<%
	final  Log log = LogFactory.getLog("updateCP5ATime.jsp");
	request.setCharacterEncoding("gb2312");
		
	String time = request.getParameter("time");
	String vin = request.getParameter("vin");
	String kin = request.getParameter("kin");
	String seqno = request.getParameter("seqno");
	int count = 0;
	//sql语句
	String sql_check="";
	log.debug("vin:" + vin);
	log.debug("要更新的总装时间：" + time);
  
	Connection con = null;
	try {
	    con = Conn.getConn();
		ResultSet rs = null;
		ResultSet rs_part = null;
		
		if(vin!=null){
			sql_check = "select count(*) from cardata where ccarno='" + kin + "'";
			rs = con.createStatement().executeQuery(sql_check);
			if(rs.next()){
				count = rs.getInt(1);
			}
			if(count != 1 ){
%>
				<script>
					<!-- 
					alert("输入的数据有误,请重新输入!");
					window.location.href='updateCP5ATime.jsp?d11=<%=time%>&vin=<%=vin%>';
					 -->
				</script>
<%
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("update cardata set dABegin=");
			sb.append(time == null || time.trim().length() == 0 ? null : "'" + time + "'");
			if(vin != null && !"".equals(vin.trim())){
				sb.append(", cvincode = '");
				sb.append(vin);
				sb.append("'");
			}
			if(seqno != null && !"".equals(seqno.trim())){
				sb.append(", cseqno_a = '");
				sb.append(seqno);
				sb.append("'");
			}
			sb.append(" where ccarno='");
			sb.append(kin);
			sb.append("'");
			
			con.createStatement().execute(sb.toString());
		}
	} catch (Exception e){
		e.printStackTrace();
	}finally{
	    if(con!=null)
			con.close();
	}
%>
<<script type="text/javascript">
<!--
function back(){
	alert("更新成功！");
	window.location.href='updateCP5ATime.jsp?d11=<%=time%>&vin=<%=vin%>&seqno=<%=seqno%>&kin=<%=kin%>';
}back();
//-->
</script>
