<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="com.qm.th.bs.bean.SpecKin"%>

<% 
	/** 本页面不缓存 */
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	/** 修改项目ID */
    String eid = request.getParameter("eid");
	/** 获得待显示的某一页数 */
    String strPage = request.getParameter("page");
	
    int PageSize = 5; //一页显示的记录数
    int RowCount = 0; //记录总数
    int PageCount = 0; //总页数
    int intPage; //待显示页码
    int record_count = 0;
    int serialNumber = 1;
     
    if(strPage == null){
       intPage = 1;
    }else{
       intPage = Integer.parseInt(strPage);
       if(intPage < 1) { intPage = 1; }
    }
	
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	List<SpecKin> list = new ArrayList<SpecKin>();
	
	try{
		StringBuilder strSql  = new StringBuilder();
		strSql.append(" SELECT A.ID, A.CVINCODE, A.CCARNO, A.DWBEGIN, A.DABEGIN ");
		strSql.append(" FROM CARDATA A INNER JOIN SPECIALKIN B ");
		strSql.append(" ON A.CCARNO = B.CCARNO AND DATEDIFF(DAY, B.DTODATE, GETDATE()) < 0 ");
		
		conn = new Conn_MES().getConn();
		stmt = conn.prepareStatement(strSql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery();
		rs.last();// 游标置于行尾
        RowCount = rs.getRow();//获得总记录数
        PageCount = (RowCount + PageSize - 1) / PageSize;//总页数
        
        if(intPage > PageCount){ 
            intPage = PageCount;
        }
        if(PageCount > 0){
            //将记录指针定位到下一显示位置
            rs.absolute((intPage - 1) * PageSize + 1);
            while(record_count < PageSize && !rs.isAfterLast()){
            	SpecKin sk = new SpecKin();
            	sk.setId(rs.getString("ID"));
    			sk.setKin(rs.getString("CCARNO"));
    			sk.setVin(rs.getString("CVINCODE"));
    			sk.setDwbegin(rs.getString("DWBEGIN"));
    			sk.setDabegin(rs.getString("DABEGIN"));
    			
    			list.add(sk);
               record_count++;
               rs.next();
            }
        }
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				rs = null;
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

<html>
    <head>
        <title>特殊KIN号提示</title>
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <link rel="stylesheet" type="text/css" href="../../cssfile/style.css">
        <link rel="stylesheet" type="text/css" href="../../cssfile/th_style.css">
        <script type="text/javascript" src="../../JarResource/META-INF/tag/taglib_common.js"></script>
    </head>
    <body>
        <div align="center">
            <div class="title"><strong><!-- InstanceBeginEditable name="标题" -->特殊KIN号提示<!-- InstanceEndEditable --></strong></div>
            <mes:table reSourceURL="../../JarResource/" id="spec_states">
                <mes:thead>
                    <mes:tr>
                        <mes:td width="30">序号</mes:td>
                        <mes:td width="120">KIN号</mes:td>
                        <mes:td width="120">VIN号</mes:td>
                        <mes:td width="160">总装上线时间</mes:td>
                        <mes:td width="160">焊装上线时间</mes:td>
                    </mes:tr>
                </mes:thead>
                <mes:tbody iterator="<%=list.iterator()%>" type="th.bs.bean.SpecKin" varName="sk">
                    <mes:tr id = "tr${sk.id}">
                        <mes:td><%=serialNumber++ + (intPage - 1)*PageSize%></mes:td>
                        <mes:td>${sk.kin}</mes:td>
                        <mes:td>${sk.vin}</mes:td>
                        <mes:td>${sk.dwbegin}</mes:td>
                        <mes:td>${sk.dabegin}</mes:td>
                    </mes:tr>
                </mes:tbody>
                <mes:tfoot>
                   <mes:tr>
                     <mes:td colspan="100%" align="center">
                         <form id="tform" name="form" style="margin: 0px"  method="post" onSubmit="return checkinput(this)" action="specKin.jsp">
                                   共<%=RowCount%>条  第<%=intPage%>页   共<%=PageCount%>页  
                                <%
                                    if(PageCount > 1){
                                        out.println("跳转到第");
                                        out.print("<input size=\"2\"  name=\"page\" value=");%><%=intPage%><%out.println(">");
                                        out.println("页");
                                    }
                                    if(intPage > 1){    
                                        out.println("<a href=\"firstPage\" onclick=\"return turnPage(1)\">第一页</a>");   
                                        out.println("<a href=\"prePage\" onclick=\"return turnPage(" + (intPage-1) + ")\">上一页</a>");     
                                    }
                                    if(intPage<PageCount){
                                        out.println("<a href=\"nextPage\" onclick=\"return turnPage(" + (intPage + 1) + ")\">下一页</a>");
                                        out.println("<a href=\"lastpage\" onclick=\"return turnPage(" + (PageCount) + ")\">最后一页</a>");
                                    }
                                %>
                           </form>
                      </mes:td>
                   </mes:tr>  
                </mes:tfoot> 
            </mes:table>
        </div>
    </body>
   <script type="text/javascript">
       /**
        * 表单校验
        */
       function checkinput(thisform){
            var re =  /^[0-9]+$/;
            var i = 0;
            var qm;
            var mm = document.getElementsByName("method");
            for(i = 0; i < mm.length; i++){
                if(mm[i].checked == true){
                    qm = mm[i].value;
                }
            }   
            if((qm == "ById") && (isNaN(thisform.process_info.value) == true)){
                alert('代码只允许输入数字！');
                thisform.process_info.value = "";
                thisform.process_info.focus();
                return false;
            }
            if(<%=PageCount%> > 1)
            if(!re.test(document.getElementsByName("page")[0].value )){   
                alert("跳转页数应由正整数组成!");
                document.getElementsByName("page")[0].value = "";
                return false;       
            }
            return true;
       }
       
       /**
        * 回选功能
        */
       function selecttr(){
           var eid = '<%=eid==null?"-1":eid%>';
           if(eid !=- 1 && document.getElementById('tr'+eid) != null){
                document.getElementById('tr'+eid).click();
           }       
       }
       selecttr();
       
       /**
        *  翻页处理
        */
       function turnPage(pageNumber){
            /** form对象 */
            var obj = document.getElementById("tform");
            
            if(obj){
                var url = obj["action"];
                obj["action"] = url + "?page=" + pageNumber;
                obj.submit();
            }
            return false;
       }
       
       /**
        * 刷新页面
        */
       var refresh = function(){
       		setInterval(function(){
       			var pagenumger = "<%=intPage%>";
       			var eid = '<%=eid==null?"-1":eid%>';
       			window.location.href = "specKin.jsp?page=" + pagenumger + "&eid=" + eid;
       		}, 10 * 1000);
       }();
    </script>
</html>