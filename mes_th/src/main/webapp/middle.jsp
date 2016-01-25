<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<%
	String cssFile =(String)session.getAttribute("file");
 	if(cssFile==null||cssFile.trim().equals(""))
 		cssFile="blue";
%>
<link href="cssfile/<%=cssFile%>.css" rel="stylesheet" type="text/css">
</head>
<body class="middle">

<div style="width:100%; height:35%;" ></div><!--创建该层是为了让下一层的图片居中显示 -->
<div id="arrow" style="width:100%; height:*;" >
<img id="icon_arrow" src="images/close_left.gif" onclick="showORhide()" style="cursor:hand; " >
</div>
</body>
<script language="javascript">
function showORhide()
{   
//	alert(parent.document);
	var left = parent.document.getElementById('left');
	if(left.cols=='230,10,*')//left is the id of frameset
	{
		left.cols='0,10,*';
		icon_arrow.src="images/open_left.gif"
	}
	else
	{
		left.cols='230,10,*';
		icon_arrow.src="images/close_left.gif";
	}

}

</script>
</html>
