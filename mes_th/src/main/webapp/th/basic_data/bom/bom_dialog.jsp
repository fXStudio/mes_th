<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="com.qm.mes.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="mes.os.util.LinkQuery"%>

<% 
    //本页面不缓存
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    
    /** 零件种类 (主/子) */
    String parttype = request.getParameter("parttype");
    /** 查询关键字 */
	String info = request.getParameter("info") != null ? request.getParameter("info") : "";
    
    int PageSize = 6;// 一页显示的记录数
    int RowCount = 0;// 记录总数
    int PageCount = 0;// 总页数
    int intPage;// 待显示页码
    int count = 1;// 序号
    
    // 获得待显示的某一页数
    String strPage = request.getParameter("page");  
    if(strPage == null || !strPage.matches("^\\d*$")){
        intPage = 1;
    }else{
        intPage = Integer.parseInt(strPage);
        if(intPage < 1) { intPage = 1; }
    }
%>

<html>
    <head>
        <title>主/子件信息</title>
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
    </head>
    <body>
    	<div align="center">
	        <table class="th_table" border=0 cellspacing=0 cellpadding=0>
	            <thead>
		            <tr>
		                <td width=30>序号</td>
		                <td width=120>零件号</td>
		                <td width=130>零件类型</td>
		                <td width=120>备注</td>
		            </tr>
	            </thead>
	            <tbody>
	            <% 
	                 Connection conn = null;
	                 Statement stmt = null;
	                 ResultSet rs = null;
	                 
	                 try{
	                     conn = new Conn_MES().getConn();
	                     String strSql = "SELECT CPARTNO, CTFASSTYPE, CREMARK FROM PARTLIST WHERE CPARTTYPE = '" + parttype + "'";
	                     // 按条件模糊查询
	                     if(!"".equals(info.trim())){
	                         ArrayList<String> colist = new ArrayList<String>(5);
	                         colist.add("CPARTNO".toLowerCase());
	                         
	                         info = info.trim();
	                         LinkQuery link = new LinkQuery();
	                         
	                         String condition = link.linkquery(info, colist, strSql, conn).toString();
	                         strSql = "SELECT * FROM (" + strSql + ") A WHERE " + condition;
	                     }
	                     strSql += " ORDER BY CPARTNO";
	                     stmt = conn.createStatement(
	                                 ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                 ResultSet.CONCUR_READ_ONLY);
	                     rs = stmt.executeQuery(strSql);
	                     rs.last();// 游标置于行尾
	                     RowCount = rs.getRow();//获得总记录数
	                     PageCount = (RowCount + PageSize - 1) / PageSize;//总页数
							
	                     if(intPage > PageCount) { intPage = PageCount; }// 是否已经到达最后一页
	                     if(PageCount>0){
	                           //将记录指针定位到下一显示位置
	                           rs.absolute((intPage-1) * PageSize + 1);
	                           while(count <= PageSize && !rs.isAfterLast()){
	                                 out.println("<tr onmouseover=\"mousehover(this)\" onmouseout=\"mouseout(this)\" onclick=\"mouseclick(this)\" ondblclick=\"mousedblclick(this)\">");
	                                     /** 序号 */
	                                     out.println("<td>");
	                                         out.println((intPage - 1) * PageSize + count++);
	                                     out.println("</td>");
	                                     
	                                     /** 零件号 */
	                                     out.println("<td>");
	                                         out.println(rs.getString("CPARTNO"));
	                                     out.println("</td>");
	                                     
	                                     /** 零件类型 */
	                                     out.println("<td>");
	                                         out.println(rs.getString("CTFASSTYPE"));
	                                     out.println("</td>");
	                                     
	                                     /** 备注 */
	                                     out.println("<td>");
	                                         out.println(rs.getString("CREMARK"));
	                                     out.println("</td>");
	                                 out.println("</tr>");
	                                 //将游标向下移动一位
	                                 rs.next();
	                           }
	                     }
	                 }catch(Exception e){
	                     e.printStackTrace();
	                 }finally{
	                     // resource release
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
	                 // 行数量不足，以空白补齐
	                 if(count < PageSize){
	                     // colspan:表格列数   28:单行高度
	                     out.println("<tr><td colspan=4 height=" + ((PageSize - count + 1) * 30) + ">&nbsp</td></tr>");
	                 }
	            %>
	            </tbody>
	            <tfoot>
		            <tr>
			            <td colspan="100%" align="center">
			                <form style="margin:0px" onSubmit="return checkinput(this)">
				                 共<%=RowCount%>条  第<%=intPage%>页  共<%=PageCount%>页  
				                 <input type="hidden" name="parttype" value="<%=parttype%>">
				                 <%
				                     if(PageCount > 1){
					                     out.println("跳转到第");
					                     out.print("<input size=\"3\" name=\"page\" value=");%><%=intPage%><%out.println(">");
					                     out.println("页");
					                     out.println("<img src=\"images/page-first.gif\" onclick=\"chagePage(1)\"/>");   
					                     out.println("<img src=\"images/page-prev.gif\"  onclick=\"chagePage(" + (intPage - 1) + ")\"/>");     
					                     out.println("<img src=\"images/page-next.gif\" onclick=\"chagePage(" + (intPage + 1) + ")\"/>");
					                     out.println("<img src=\"images/page-last.gif\" onclick=\"chagePage(" + PageCount + ")\"/>");
				                     }
				                 %>
			                </form>
			            </td>
		            </tr>
	            </tfoot>
	        </table>
	        <!-- InstanceBeginEditable name="过滤" -->
            <form name="form1" method="get" style="margin: 0px" onsubmit="return search(this)">
                <table class="tbnoborder">
                    <tr>
                        <td class="tbnoborder">按零件号查询:</td>
                        <td class="tdnoborder">
                            <input type=text value="<%=info%>" name="info" id="info" class="box1" size=20 onMouseOver="this.className='box3'" 
                                   onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'"></td>
                    </tr>
                </table>
            </form>
            <!-- InstanceEndEditable -->
        </div>
    <body>
</html>