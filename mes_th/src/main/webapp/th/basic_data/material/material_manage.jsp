<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.qm.th.bs.bean.PartList"%>
<%@ page import="com.qm.mes.os.util.LinkQuery"%>

<% 
	 /** 本页面不缓存 */
	 response.setHeader("Pragma", "No-cache");
	 response.setHeader("Cache-Control", "no-cache");
	 response.setDateHeader("Expires", 0);
	
	 /** BOM信息列表 */
	 List<PartList> list = new ArrayList<PartList>();
	  
	 /** 修改项目ID */
	 String eid = request.getParameter("eid");
	  
	 /** 查询关键字 */
	 String info = request.getParameter("info") != null ? request.getParameter("info") : "";
	  
	 /** 获得待显示的某一页数 */
	 String strPage = request.getParameter("page"); 
	  
	 int PageSize = 10; //一页显示的记录数
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
     PreparedStatement  stmt = null;
     ResultSet rs = null;

     try{
         // 创建数据库连接
         conn = new Conn_MES().getConn();
         // 基础查询语句
         String strSql = new StringBuilder()
         .append(" SELECT A.ID, A.CPARTNO, CASE WHEN A.CPARTTYPE = '00001' THEN '主件' ELSE '子件' END AS CPARTTYPE,")
         .append(" ISNULL(B.CTFASSNAME, '') AS CTFASSNAME, A.CREMARK")
         .append(" FROM PARTLIST A LEFT OUTER JOIN TFASSNAME B ON A.ITFASSNAMEID = B.ID").toString();
         // 按条件模糊查询
         if(!info.equals("")){
             ArrayList<String> colist = new ArrayList<String>(5);
             colist.add("CPARTNO".toLowerCase());
             colist.add("CPARTTYPE".toLowerCase());
             colist.add("TFASSNAME".toLowerCase());
             
             info = info.trim();
             LinkQuery link = new LinkQuery();
             
             String condition = link.linkquery(info, colist, strSql, conn).toString();
             strSql = "SELECT * FROM (" + strSql.toString() + ") A WHERE " + condition;
         }
         stmt = conn.prepareStatement(strSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
                PartList pl = new PartList(); 
                pl.setId(rs.getInt("ID"));// 主键
                pl.setCpartno(rs.getString("CPARTNO"));// 零件号
                pl.setCparttype(rs.getString("CPARTTYPE"));// 零件类型
                pl.setCtfassname(rs.getString("CTFASSNAME"));// 天合零件名
                pl.setRemark(rs.getString("CREMARK"));// 备注
                
                list.add(pl);
                record_count++;
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
%>

<html>
    <head>
        <title>物料维护</title>
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
    </head>
    <body>
        <div class="title"><strong><!-- InstanceBeginEditable name="标题2" -->物料维护<!-- InstanceEndEditable --></strong></div>
        <br>
        <div align="center">
            <mes:table reSourceURL="../../../JarResource/" id="qulity_states">
                <mes:thead>
                    <mes:tr>
                        <mes:td width="30">序号</mes:td>
                        <mes:td width="160">零件编码</mes:td>
                        <mes:td width="160">天合总成名称</mes:td>
                        <mes:td width="120">零件类别</mes:td>
                        <mes:td width="30">更改</mes:td>
                        <mes:td width="30">删除</mes:td>
                    </mes:tr>
                </mes:thead>
                <mes:tbody iterator="<%=list.iterator()%>" type="th.bs.bean.PartList" varName="pl">
                    <mes:tr id = "tr${pl.id}">
                        <mes:td><%=serialNumber++ + (intPage - 1)*PageSize%></mes:td>
                        <mes:td>${pl.cpartno}</mes:td>
                        <mes:td>${pl.ctfassname}</mes:td>
                        <mes:td>${pl.cparttype}</mes:td>
                        <mes:td>
                            <a href="javascript:window.location.href='material_modify.jsp?id=${pl.id}&page=<%=intPage%>'">更改</a>
                        </mes:td>
                        <mes:td>
                            <a href="javascript:if(confirm('真的要删除这条记录吗？')){ window.location.href='material_del.jsp?page=<%=intPage%>&intId=${pl.id}'; }">删除</a>
                        </mes:td>
                    </mes:tr>
                </mes:tbody>
                <mes:tfoot>
                   <mes:tr>
                     <mes:td colspan="100%" align="center">
                         <form  name="form" style="margin: 0px"  method="post" onSubmit="return checkinput(this)" action="material_manage.jsp">
                               <input type="hidden" value="<%=info%>" name="info" >   
                                   共<%=RowCount%>条  第<%=intPage%>页   共<%=PageCount%>页  
                                <%
                                    if(PageCount > 1){
                                        out.println("跳转到第");
                                        out.print("<input size=\"2\"  name=\"page\" value=");%><%=intPage%><%out.println(">");
                                        out.println("页");
                                    }
                                   
                                    if(intPage > 1){    
                                        out.println("<a href=\"?page=1&info=" + info + "\">第一页</a>");   
                                        out.println("<a href=\"?page=" + (intPage-1)+"&info=" + info + "\">上一页</a>");     
                                    }
                                    
                                    if(intPage<PageCount){
                                        out.println("<a href=\"?page=" + (intPage+1)+"&info=" + info + "\">下一页</a>");
                                        out.println("<a href=\"?page=" + (PageCount)+"&info=" + info + "\">最后一页</a>");
                                    }
                                %>
                                <a href="material_maintenance.jsp?intPage=<%=intPage%>">【添加新记录】</a>
                           </form>
                      </mes:td>
                   </mes:tr>  
                </mes:tfoot> 
            </mes:table>
            <!-- InstanceBeginEditable name="过滤" -->
            <form  name="form1" method="get" style="margin: 0px" action="material_manage.jsp">
                <table class="tbnoborder">
                    <tr>
                        <td class="tbnoborder">关键字查询:</td>
                        <td class="tdnoborder">
                            <input type=text value="<%=info%>" name="info" id="info" class="box1" size=20 onMouseOver="this.className='box3'" 
                                   onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'"></td>
                        <td class="tdnoborder">
                            <mes:button id="s1" reSourceURL="../../../JarResource/" submit="true" value="查询"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div class="div_normal">
                    <div class="div_normal">
                        关键字为：<a class="div_red">零件编码 天合总成名称 零件类别</a><br>
                        参数为空的时候查询所有信息，查询条件以<a class="div_red">"," </a>分开。<br>
                    </div>
            </div>
            <!-- InstanceEndEditable -->
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
           if(eid!=-1 && document.getElementById('tr'+eid)!=null){
                document.getElementById('tr'+eid).click();
           }       
       }
       selecttr();
    </script>
</html>