<%@ page language="java" pageEncoding="utf-8"%>
 
<% 
	String []infos=request.getParameter("pagenolist").split(",");
	String []pagenos=request.getParameter("pagenos").split(",");
	
	if(session.getAttribute("pagenos")!=null)
		session.removeAttribute("pagenos");
	session.setAttribute("pagenos",pagenos);
	String info="";
	int index=1;
	for(int i=0;i<infos.length;i++){
		info+="("+index+")"+"."+"&nbsp;&nbsp;"+infos[i]+ "<br/>";
		index++;
	}
	response.getWriter().print("<font size=4>" + info + "</font>");
	
%>