<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.qm.th.terminal.PrintData"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>

<% 
	/***/
	String id = request.getParameter("id");
	/***/
	List<PrintData>	list = new ArrayList<PrintData>();

	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	String preIndentify = (String)session.getAttribute("maxIndentify");
	String curIndentify = "";

	try{
		conn = new Conn_MES().getConn();
		StringBuffer str = new StringBuffer();
		str.append(" select  top 30 *");
		str.append(" from (");
		str.append(" SELECT distinct CASE PS.CCODE WHEN '1' THEN 'B8/Q5方向盘/气囊'");
		str.append(" ELSE PS.CCARTYPEDESC END AS NAME,");
		str.append(" CONVERT(VARCHAR,DAY(PD.CREMARK))+'-'+CONVERT(VARCHAR,PD.ICARNO) AS CODE,");
		str.append(" PD.CPAGENO,cp.outpnostate,CP.CAR,CP.DOORNO+'号门' AS DOORNO,CP.RECORDDATE,CS.CARSTATE,CS.OUTEMP");
		str.append(" FROM");
		str.append(" PRINTSET PS INNER JOIN PRINT_DATA PD");
		str.append(" ON PS.ID=PD.IPRINTGROUPID");
		str.append(" INNER JOIN CAR_PAGENO CP");
		str.append(" ON PD.CPAGENO=CP.PAGENO");
		str.append(" LEFT JOIN CAR_STATE CS");
		str.append(" ON CP.CAR=CS.CAR");
		str.append(" ) A");
		str.append(" where cpageno > ? and datediff(hh,RECORDDATE,getdate())<=24 and CARSTATE=1 and outemp is null and outpnostate is null");
		str.append(" ORDER BY cpageno");
		stmt = conn.prepareStatement(str.toString());
		stmt.setString(1, id != null ? id : "0");
		rs = stmt.executeQuery();
		
		while(rs.next()){
			PrintData pd = new PrintData();
			pd.setId(rs.getString("cPageNo"));
			pd.setName(rs.getString("name"));
			pd.setCode(rs.getString("code"));
			pd.setCar(rs.getString("car"));
			pd.setDoorno(rs.getString("doorno"));
			
			if(curIndentify.compareTo(rs.getString("cPageNo")) < 0){
				curIndentify = rs.getString("cPageNo").trim();
			}
			list.add(pd);
		}
		if(!"".equals(curIndentify) && !curIndentify.equals(preIndentify)){
			session.setAttribute("maxIndentify", curIndentify);
		}else{
			list.clear();
		}
		out.println(net.sf.json.JSONArray.fromObject(list));
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				stmt = null;
			}
		}
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				stmt = null;
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
%>