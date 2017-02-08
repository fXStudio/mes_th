<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="db.Terminal"%>
<%@ page import="common.Conn_MES"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
 
<% 
        String output = "";
        String start = request.getParameter("start");
        
        String limit = request.getParameter("limit");
        
        int index = Integer.parseInt(start);
        int pageSize = Integer.parseInt(limit);
        
        Map map = request.getParameterMap();      
		
		//分组,不包含指定类型时退出
		int groupByNumber=0;
		while(true){
			String filterType="filter["+groupByNumber+"][data][type]";
			
			if(map.containsKey(filterType)==false){
				break;
			}
		    groupByNumber++;
			
		}

		String condition=" where 0=0 ";
		
		for(int i=0;i<groupByNumber;i++){
		
			
			String type="filter["+i+"][data][type]";
			
			String []filtertype=(String[])map.get(type);
			
			String field="filter["+i+"][field]";
			
			String []filterfield=(String[])map.get(field);
			
			String value="filter["+i+"][data][value]";
			
			String []filtervalue=(String[])map.get(value);
			
			if(filtertype[0].equals("string")){
			
				condition+=" and "+filterfield[0]+" like '%"+filtervalue[0]+"%'";
			}
			if(filtertype[0].equals("numeric")){
			
				String comparison="filter["+i+"][data][comparison]";
			
				String []filtercomparison=(String[])map.get(comparison);
			
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
        
        int rows=new Terminal().getFilterCPageNoSeaCount(condition);
	   	int total=rows;
	   	Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con= new Conn_MES().getConn();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql="SELECT PD.CPAGENO,P.cdescrip, P.CCARTYPEDESC AS NAME,"
            		   +" CONVERT(VARCHAR,DAY(PD.CREMARK)) + '-' + CONVERT(VARCHAR,PD.ICARNO) AS CODE,"
            		   +" CP.CAR, PD.dPrintTime as printdate, CP.outpnostate, "
                       +" ps.recorddate as partdate, cs.recorddate as cardate, "
                       +" cs.outrecorddate as outdate,PS.pagenostate "
                       +" FROM PRINTSET P"
                       +" INNER JOIN (select IPRINTGROUPID, CPAGENO, CREMARK, ICARNO, dPrintTime from PRINT_DATA where datediff(HH, dPrintTime, getdate())<=24 GROUP BY "
                       +" IPRINTGROUPID, CPAGENO, CREMARK, ICARNO, dPrintTime) PD ON P.ID = PD.IPRINTGROUPID"
                       +" LEFT JOIN pageno_state ps on pd.cpageno = ps.pageno"
                       +" LEFT JOIN (select * from CAR_PAGENO where outpnostate is null) CP ON PD.CPAGENO = CP.PAGENO"
                       +" LEFT join (select car, outrecorddate,recorddate from car_state where outrecorddate is null) cs on cp.car = cs.car"
                       +condition
                       +" order by CPAGENO DESC";
            
            System.out.println(sql);
            
            rs = stmt.executeQuery(sql);
            
            output = "{total:"+total+",data:[";
            int aa = ((pageSize + index > total) ? total : (pageSize + index));

            if(index>0){
                rs.absolute(index);
            }
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String partdate="";
			String cardate="";
			String outdate="";
			String car="";
			String cdescrip="";
			String pagenostate="";
            for (int i=index; i < aa; i++) {
                if(rs.next()) {
                	partdate=rs.getTimestamp("partdate")==null?"":format.format(rs.getTimestamp("partdate"));
                	cardate=rs.getTimestamp("cardate")==null?"":format.format(rs.getTimestamp("cardate"));		
                	outdate=rs.getTimestamp("outdate")==null?"":format.format(rs.getTimestamp("outdate"));	
                	pagenostate=rs.getString("pagenostate")==null?"":rs.getString("pagenostate");
			car=rs.getString("car")==null?"":rs.getString("car");
			cdescrip=rs.getString("cdescrip")==null?"":rs.getString("cdescrip");
                    output += "{cpageno:'" + rs.getString("cpageno")+ "',code:'" + rs.getString("code")+ "',car:'" +car+ "',cdescrip:'" +cdescrip+ "',printdate:'" + format.format(rs.getTimestamp("printdate")) + "',partdate:'"+partdate+"',cardate:'"+cardate+"',outdate:'"+outdate+"',name:'" + rs.getString("name")+ "',pagenostate:'"+pagenostate+"'}";
                    if (i != aa - 1) {
                        output += ",";
                    }
                    partdate="";
                    cardate="";
				    outdate="";
			        car="";
				    cdescrip="";
				    pagenostate="";
                }
            }
if(output.endsWith(","))
output=output.substring(0,output.length()-1);

            output += "]}";
            
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