<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% 
	 	String unlinecode=request.getParameter("unlinecode");
        if(unlinecode==null)
        	unlinecode="";
        if(session.getAttribute("unlinecode")!=null)
        	session.removeAttribute("unlinecode");
        session.setAttribute("unlinecode",unlinecode);
        String kincode=request.getParameter("kincode");
        if(kincode==null)
        	kincode="";
        if(session.getAttribute("kincode")!=null)
        	session.removeAttribute("kincode");
        session.setAttribute("kincode",kincode);
        String partcode=request.getParameter("partcode");
        if(partcode==null)
        	partcode="";
        if(session.getAttribute("partcode")!=null)
        	session.removeAttribute("partcode");
        session.setAttribute("partcode",partcode);
        String seqnum=request.getParameter("seqnum");
        if(seqnum==null)
        	seqnum="";
        if(session.getAttribute("seqnum")!=null)
        	session.removeAttribute("seqnum");
        session.setAttribute("seqnum",seqnum);
        String dpcode=request.getParameter("dpcode");
        if(dpcode==null)
        	dpcode="";
        if(session.getAttribute("dpcode")!=null)
        	session.removeAttribute("dpcode");
        session.setAttribute("dpcode",dpcode);
        String dpdate=request.getParameter("dpdate");
        if(dpdate==null)
        	dpdate="";
        if(session.getAttribute("dpdate")!=null)
        	session.removeAttribute("dpdate");
        session.setAttribute("dpdate",dpdate);
        String dpseqnum=request.getParameter("dpseqnum");
        if(dpseqnum==null)
        	dpseqnum="";
        if(session.getAttribute("dpseqnum")!=null)
        	session.removeAttribute("dpseqnum");
        session.setAttribute("dpseqnum",dpseqnum);
        String stateid=request.getParameter("stateid");
        if(session.getAttribute("stateid")!=null)
        	session.removeAttribute("stateid");
        session.setAttribute("stateid",stateid);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/userManager.css" />
	
		
	<script type="text/javascript" src="../ext-base.js"></script>
	<script type="text/javascript" src="../ext-all.js"></script>
	<script type="text/javascript" src="../ext-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="js/menu/EditableItem.js"></script>
	<script type="text/javascript" src="js/menu/RangeMenu.js"></script>
	
	<script type="text/javascript" src="js/grid/GridFilters.js"></script>
	<script type="text/javascript" src="js/grid/filter/Filter.js"></script>
	<script type="text/javascript" src="js/grid/filter/StringFilter.js"></script>
	<script type="text/javascript" src="js/grid/filter/DateFilter.js"></script>
	<script type="text/javascript" src="js/grid/filter/ListFilter.js"></script>
	<script type="text/javascript" src="js/grid/filter/NumericFilter.js"></script>
	<script type="text/javascript" src="js/grid/filter/BooleanFilter.js"></script>
	
	<script type="text/javascript" src="terminalAllSeaManager.js" defer="defer"></script>
	 
  </head>
  
  <body>
	<div id="searchManager"></div>
  </body>
</html>
