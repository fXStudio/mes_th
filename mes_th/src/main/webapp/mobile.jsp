<%@ page language="java" contentType="text/html;charset=GB2312"%>
<%@ page import="java.util.Calendar"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
	<head>
		<title>东阳MES系统--终端登录</title>
		<link rel="stylesheet" type="text/css" href="cssfile/style.css">
		<link rel="stylesheet" type="text/css" href="cssfile/dy_mobile.css">
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
		<script language="javascript">
		      var CalendarWebControl = new atCalendarControl();
		</script>
	</head>
	<body topmargin="0" leftmargin="0" rightmargin=0 bottommargin=0 background="" bgcolor="#FAFAFA" 
	      scroll="NO" onLoad="document.frm1.name.focus();">
		<%
		    Calendar c = Calendar.getInstance();
		    int yy = c.get(Calendar.YEAR);
		    int mm = c.get(Calendar.MONTH) + 1;
		    int dd = c.get(Calendar.DAY_OF_MONTH);
		    String dateStr = String.valueOf(yy);
		    if (mm < 10){
		        dateStr = dateStr + "-" + "0" + String.valueOf(mm);
		    }else{
		        dateStr = dateStr + "-" + String.valueOf(mm);
		    }

		    if (dd < 10){
		        dateStr = dateStr + "-" + "0" + String.valueOf(dd);
		    }else{
		        dateStr = dateStr + "-" + String.valueOf(dd);
		    }
		%>
		<form action="mobile_log.jsp" name = "frm1" onSubmit="return checkinput(this)" method="post">
		    <input type="hidden" name="dateInput" id="dateInput" value="<%=dateStr%>">
			<div style="background:url('images/zd.gif') no-repeat;" class="container">
			    <div id="third" style="position:relative;top:70px;left:80px;" class="item">
                    用户名
                    <input class="mobilebox1" type="text" name="name" size="12" maxlength="15">
                </div>
                <div id="fourth" style="position:relative;top:90px;left:80px" class="item">
                    密&nbsp;&nbsp;码
                    <input class="mobilebox1" type="password" name="pass" size="12" maxlength="15">
                </div>
                <div id="second" style="position:relative;top:115px;left:80px" class="item">
                     日&nbsp;&nbsp;期 <span style="color:yellow;font-style:italic;text-align:center;width:100px;"><%=dateStr%></span>
                </div>
                 <div id="fifth" class="item" style="position:relative;top:132px;text-align:right;width:300px;">
	                <!-- 
		                <span class="btn_mouseout"
		                    onMouseOver="this.className='btn_mouseover'"
		                    onmouseout="this.className='btn_mouseout'">
		                    <input class="btn1" type=submit value="登 录" name=submit>
		                </span>
	                 -->
	                <input type="submit" value="GO" class="normal_btn" style="background-image: url('images/b23.gif');">
                </div>
            </div>
		</form>
	</body>
	<script type="text/javascript">
		function resetPara()
		{
			document.getElementsByName("name")[0].value="";
			document.getElementsByName("pass")[0].value="";
			document.getElementsByName("name")[0].focus();
		}
		function close()
		{
		    window.parent.opener = null;
		    window.close();
		    document.all.dateInput.focus();
		}
	</script>
</html>