<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="db.Terminal"%>
<%@ page import="common.Conn_MES"%>
<%@page import="java.text.SimpleDateFormat"%>
 
<% 
		
        String output = "";
        String start = request.getParameter("start");
        
        String limit = request.getParameter("limit");
        
        int index = Integer.parseInt(start);
        int pageSize = Integer.parseInt(limit);
		String unlinecode=(String)session.getAttribute("unlinecode");	
		String kincode=(String)session.getAttribute("kincode");	
		String partcode=(String)session.getAttribute("partcode");	
		String seqnum=(String)session.getAttribute("seqnum");	
		String dpcode=(String)session.getAttribute("dpcode");	
		String dpdate=(String)session.getAttribute("dpdate");	
		String dpseqnum=(String)session.getAttribute("dpseqnum");	
		String stateid=(String)session.getAttribute("stateid");
		String condition=" where 0=0 and cVinCode like '%"+unlinecode+"%' "
		                 +"and cKinNo like '%"+kincode+"%' "
		                 +"and cTFAss like '%"+partcode+"%' "
		                 +"and partseq like '%"+seqnum+"%' "
		                 +"and dpcode like '%"+dpcode+"%' "
		                 +"and dpdate like '%"+dpdate+"%' "
		                 +"and dpseqnum like '%"+dpseqnum+"%' ";
		
		
        System.out.println(condition);
        
        String tablename_A="";
		String tablename_B="";
		String tablename_C="";
		String tablename_D="";
		String tablename_E="";
		if(stateid.equals("1")){
			tablename_A="print_data";
			tablename_B="pageno_part";
			tablename_C="pageno_state";
			tablename_D="car_pageno";
			tablename_E="car_state";
		}else{
			tablename_A="history_print_data";
			tablename_B="history_pageno_part";
			tablename_C="history_pageno_state";
			tablename_D="history_car_pageno";
			tablename_E="history_car_state";
		}
        
        int rows=new Terminal().getAllFilterCount(condition,tablename_A,tablename_B,tablename_C,tablename_D,tablename_E);
        
	   	int total=rows;
	   	Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con= new Conn_MES().getConn();
            
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
	        String sql="select * from"
	                       +"(select cVinCode,cKinNo,isnull(cTFAss,'') as cTFAss,isnull(partseq,'') as partseq,isnull(dpcode,'') as dpcode,isnull(dpdate,'') as dpdate,"
	                       +"isnull(dpseqnum,'') as dpseqnum,cPageNo,dPrintTime,ps.recorddate as psrecorddate,"
	                       +"ps.emp,cs.recorddate as csrecorddate,cp.car,cp.doorno,cp.outrecorddate "
	                       +"from "+tablename_A+" pr "
	                       +"left join "+tablename_B+" pp  "
	                       +"on pr.cVincode=pp.vin and pr.cPageNo=pp.pageno "
	                       +"left join "+tablename_C+" ps on pp.pageno=ps.pageno "+
	                       "left join "+tablename_D+" cp on pr.CpageNo=cp.pageno "
	                       +"left join "+tablename_E+" cs on cp.car=cs.car) t "+condition;
            
            System.out.println(sql);
            
            rs = stmt.executeQuery(sql);
       		
            output = "{total:"+total+",data:[";
            int aa = ((pageSize + index > total) ? total : (pageSize + index));

            if(index>0){
                rs.absolute(index);
            }
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String seqcode_temp="";
            for (int i=index; i < aa; i++) {
                if(rs.next()) {
					seqcode_temp=rs.getString("dpcode")+" "+rs.getString("dpdate")+" "+rs.getString("dpseqnum");
					
					output += "{unlinecode:'" + rs.getString("cVinCode")+ "',kincode:'" + rs.getString("cKinNo")+ "',partcode:'" + rs.getString("cTFAss")+ "',seqcode:'" + seqcode_temp+ "',pageno:'" + rs.getString("cPageNo")+ "',printdate:'" + format.format(rs.getTimestamp("dPrintTime")) + "',psrecorddate:'"+(rs.getTimestamp("psrecorddate")==null?"":(format.format(rs.getTimestamp("psrecorddate"))))+"',psemp:'"+(rs.getString("emp")==null?"":rs.getString("emp"))+"',csrecorddate:'"+(rs.getTimestamp("csrecorddate")==null?"":(format.format(rs.getTimestamp("csrecorddate"))))+"',car:'"+(rs.getString("car")==null?"":rs.getString("car"))+"',doorno:'"+(rs.getString("doorno")==null?"":rs.getString("doorno"))+"',outrecorddate:'"+(rs.getTimestamp("outrecorddate")==null?"":(format.format(rs.getTimestamp("outrecorddate"))))+"'}";
                    if (i != aa - 1) {
                        output += ",";
                    }

                }
            }
            output += "]}";
            
            System.out.println(output);
            
            response.getWriter().write(output);

        } catch (Exception e) {
            out.print(e);
        } finally {

        	if(rs != null){
	        	 try{
	        		 rs.close();
	        	 }catch(Exception e){
	        		 e.printStackTrace();
	        	 }finally{
	        		 rs = null;
	        	 }
	         }
	         if(stmt != null){
	        	 try{
	        		 stmt.close();
	        	 }catch(Exception e){
	        		 e.printStackTrace();
	        	 }finally{
	        		 stmt = null;
	        	 }
	         }
	         if(con != null){
	        	 try{
	        		 con.close();
	        	 }catch(Exception e){
	        		 e.printStackTrace();
	        	 }finally{
	        		 con = null;
	        	 }
	         }

        }
%>