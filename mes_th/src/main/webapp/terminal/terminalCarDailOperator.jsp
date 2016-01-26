<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.qm.th.terminal.Terminal"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
 
<% 
        String output = "";
        String start = request.getParameter("start");
        if(start==null)
        	return;
        String limit = request.getParameter("limit");
        
        int index = Integer.parseInt(start);
        
        int pageSize = Integer.parseInt(limit);
        
        Map map=request.getParameterMap();      
        
       // Iterator it = map.entrySet().iterator(); 
       
       // while(it.hasNext()){
        
		 //   	Map.Entry entry=(Map.Entry)it.next();
			    //Map.Entry可以看成是一种特殊的Map,与Map不同的是Map.Entry只能含有一对键-值
		 //	    String key=(String)entry.getKey();
		 //	    String[] value=(String[])entry.getValue();
		 //	    System.out.println("key:"+key+"   value:"+value[0]);
	  //}
		
		//分组,不包含指定类型时退出
		int groupByNumber=0;
		while(true){
			String filterType="filter["+groupByNumber+"][data][type]";
			if(map.containsKey(filterType)==false){
				break;
			}
		    groupByNumber++;
		}
		
		System.out.println("groupByNumber:"+groupByNumber);
		
		String car=request.getParameter("uid");
		String condition=" where 0=0 and car='"+car+"' and outpnostate is null";
		
		for(int i=0;i<groupByNumber;i++){
		
			
			String type="filter["+i+"][data][type]";
			
			String []filtertype=(String[])map.get(type);
			
			System.out.println(filtertype[0]);
			
			String field="filter["+i+"][field]";
			
			String []filterfield=(String[])map.get(field);
			
			System.out.println(filterfield[0]);
			
			String value="filter["+i+"][data][value]";
			
			String []filtervalue=(String[])map.get(value);
			
			System.out.println(filtervalue[0]);
			
			
			
			if(filtertype[0].equals("string")){
			
				condition+=" and "+filterfield[0]+" like '%"+filtervalue[0]+"%'";
			}
			if(filtertype[0].equals("numeric")){
			
				String comparison="filter["+i+"][data][comparison]";
			
				String []filtercomparison=(String[])map.get(comparison);
				
				System.out.println(filtercomparison[0]);
			
				if(filtercomparison[0].equals("lt")){
					filtercomparison[0]="<";
				}
				if(filtercomparison[0].equals("gt")){
					filtercomparison[0]=">";
				}
				if(filtercomparison[0].equals("eq")){
					filtercomparison[0]="=";
				}
			
				condition+=" and "+filterfield[0]+""+filtercomparison[0]+""+filtervalue[0];
			}
			if(filtertype[0].equals("list")){
			
				String invalue="";
				
				String[] filterValue=filtervalue[0].split(",");
				
				for(int j=0;j<filterValue.length;j++){
				
					invalue+="'"+filterValue[j]+"',";
				}
				
			
				condition+=" and "+filterfield[0]+" in ("+invalue.substring(0,invalue.length()-1)+")";
			}
			if(filtertype[0].equals("boolean")){
				
				if(filtervalue[0].equals("true")){
					filtervalue[0]="1";
				}else{
				    filtervalue[0]="0";
				}
				
				condition+=" and "+filterfield[0]+"="+filtervalue[0];
			}
			
			if(filtertype[0].equals("date")){
			
				String comparison="filter["+i+"][data][comparison]";
			
				String []filtercomparison=(String[])map.get(comparison);
				
				System.out.println(filtercomparison[0]);
			
				if(filtercomparison[0].equals("lt")){
					filtercomparison[0]="<";
				}
				if(filtercomparison[0].equals("gt")){
					filtercomparison[0]=">";
				}
				if(filtercomparison[0].equals("eq")){
					filtercomparison[0]="=";
				}
			
				condition+=" and "+filterfield[0]+""+filtercomparison[0]+"'"+filtervalue[0]+"'";
			}
		}
		
		
        System.out.println(condition);
        
        int rows = new Terminal().getCarDailFilterCount(condition);
	   	int total = rows;
	   	
	   	Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            con= new Conn_MES().getConn();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql="select pageno, doorno,recorddate, emp from car_pageno " + condition;
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
			
            //if(rs.last()){
            	//int rowcount = rs.getRow();
            	//index = index > rowcount ? rowcount - 1 : index;
            	//rs.beforeFirst();
            //}
			
            output = "{total:"+total+",data:[";
            int aa = ((pageSize + index > total) ? total : (pageSize + index));

            if(index > 0){
                rs.absolute(index);
            }
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = index; i < aa; i++) {
                if(rs.next()) {
                    output += "{pageno:'" + rs.getString("pageno")+ "',doorno:'" + (rs.getString("doorno")==null? "" : rs.getString("doorno"))+"',recorddate:'"+format.format(rs.getTimestamp("recorddate"))+"',emp:'"+rs.getString("emp")+"'}";
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