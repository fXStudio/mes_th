<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.qm.th.bs.bean.SpecPart"%>
<%@ page import="com.qm.mes.os.util.LinkQuery"%>

<% 
     /** 本页面不缓存 */
     response.setHeader("Pragma", "No-cache");
     response.setHeader("Cache-Control", "no-cache");
     response.setDateHeader("Expires", 0);
    
     /** BOM信息列表 */
     List<SpecPart> list = new ArrayList<SpecPart>();
      
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
         String strSql = "SELECT ID, CPART, CCONVERTPART, CREMARK FROM SPECPART";
         // 按条件模糊查询
         if(!info.equals("")){
             ArrayList<String> colist = new ArrayList<String>(5);
             colist.add("CPART".toLowerCase());
             colist.add("CCONVERTPART".toLowerCase());
             
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
                SpecPart sp = new SpecPart(); 
                sp.setId(rs.getInt("ID"));// 主键ID
                sp.setCpart(rs.getString("CPART"));// 零件
                sp.setCconvertpart(rs.getString("CCONVERTPART"));// 转换零件
                sp.setRemark(rs.getString("CREMARK"));// 备注
                
                list.add(sp);
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
                        <mes:td width="160">零件</mes:td>
                        <mes:td width="160">转换零件</mes:td>
                        <mes:td width="160">备注</mes:td>
                        <mes:td width="30">更改</mes:td>
                        <mes:td width="30">删除</mes:td>
                    </mes:tr>
                </mes:thead>
                <mes:tbody iterator="<%=list.iterator()%>" type="th.bs.bean.SpecPart" varName="sp">
                    <mes:tr id = "tr${sp.id}">
                        <mes:td><%=serialNumber++ + (intPage - 1)*PageSize%></mes:td>
                        <mes:td>${sp.cpart}</mes:td>
                        <mes:td>${sp.cconvertpart}</mes:td>
                        <mes:td>${sp.remark}</mes:td>
                        <mes:td>
                            <a href="javascript:window.location.href='spec_modify.jsp?page=<%=intPage%>&intId=${sp.id}'">更改</a>
                        </mes:td>
                        <mes:td>
                            <a href="javascript:if(confirm('真的要删除这条记录吗？')){ window.location.href='spec_del.jsp?page=<%=intPage%>&intId=${sp.id}'; }">删除</a>
                        </mes:td>
                    </mes:tr>
                </mes:tbody>
                <mes:tfoot>
                   <mes:tr>
                     <mes:td colspan="100%" align="center">
                         <form  name="form" style="margin: 0px"  method="post" onSubmit="return checkinput(this)" action="spec_manage.jsp">
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
                                <a href="spec_maintenance.jsp?page=<%=intPage%>">【添加新记录】</a>
                           </form>
                      </mes:td>
                   </mes:tr>  
                </mes:tfoot> 
            </mes:table>
            <!-- InstanceBeginEditable name="过滤" -->
            <form  name="form1" method="get" style="margin: 0px" action="spec_manage.jsp">
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
                        关键字为：<a class="div_red">零件 转换零件</a><br>
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