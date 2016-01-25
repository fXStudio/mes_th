<%@ page language="java" contentType="text/html;charset=GB2312"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<jsp:directive.page import="java.util.Calendar" />
<html> 
	<head>
		<title>MES系统--操作员登录</title>
		<link rel="stylesheet" type="text/css" href="cssfile/style.css">
		<script>
<!-- 
function checkinput(thisForm)
{
	if (thisForm.name.value==""){alert("请输入用户名");thisForm.name.focus();return false;}
	if (thisForm.pass.value==""){alert("请输入用户密码");thisForm.pass.focus();return false;}	
	return true;
}
-->
</script>
		<script type="text/javascript" src="calendar.js"></script>
		<script type="text/javascript" src="JarResource/META-INF/tag/taglib_common.js"></script>

	<style>
		.logonwindow
		{
		    MARGIN-TOP: 0;
		    FONT-WEIGHT: normal;
		    FONT-SIZE: 11pt;
		    MARGIN-LEFT: 0;
		    WIDTH: 350px;
		    COLOR: #1343bf;
		    FONT-FAMILY: Tahoma,Geneva, Arial;
		    HEIGHT: 185px
		}
	</style>
	</head>
	<body topmargin="0" leftmargin="0" rightmargin=0 bottommargin=0
		onLoad="document.frm1.name.focus();" background="" bgcolor="#FAFAFA"
		scroll="NO">
		<%
			Calendar c = Calendar.getInstance();
			int yy = c.get(Calendar.YEAR);
			int mm = c.get(Calendar.MONTH) + 1;
			int dd = c.get(Calendar.DAY_OF_MONTH);
			String dateStr = String.valueOf(yy);
			if (mm < 10)
				dateStr = dateStr + "-" + "0" + String.valueOf(mm);
			else
				dateStr = dateStr + "-" + String.valueOf(mm);

			if (dd < 10)
				dateStr = dateStr + "-" + "0" + String.valueOf(dd);
			else
				dateStr = dateStr + "-" + String.valueOf(dd);
		%>

		<form action="log_terminal.jsp" name="frm1" onSubmit="return checkinput(this)" method="get">
			<div id="logonwindow" class="logonwindow" align="center">
				<div id="first" class="first">
					登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录
				</div>
				<div id="second" class="second">
					日&nbsp;&nbsp;&nbsp;期&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="box1" name="dateInput" type="text" id="dateInput"
						size="25" maxlength="15" readonly value=<%=dateStr%>
						onclick="CalendarWebControl.show(this,this.value);"
						onMouseOver="this.className='box2'"
						onFocus="this.className='box2'" onMouseOut="this.className='box1'"
						onBlur="this.className='box1'" />
				</div>
				<div id="third" class="third">
					用户名&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="box1" type="text" name="name" value="" size="25"
						onMouseOver="this.className='box2'"
						onFocus="this.className='box2'" onMouseOut="this.className='box1'"
						onBlur="this.className='box1'">
				</div>
				<div id="fourth" class="fourth">
					密&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="box1" type="password" name="pass" size="25"
						maxlength="15" onMouseOver="this.className='box2'"
						onFocus="this.className='box2'" onMouseOut="this.className='box1'"
						onBlur="this.className='box1'">
				</div>
				<div id="fifth" class="fifth">
			
					 <mes:button id="button1" reSourceURL="JarResource/" submit="true" value="登 录"/>
				</div>
			</div>
		</form>

		
		<div id="indexfoot" class="foot">
		</div>
	</body>
</html>
<script type="text/javascript">
function resetPara()
{
	document.getElementsByName("name")[0].value="";
	document.getElementsByName("pass")[0].value="";
	document.getElementsByName("name")[0].focus();
}
function close()
{
    window.parent.opener=null;
    window.close();
    document.all.dateInput.focus();
}
</script>