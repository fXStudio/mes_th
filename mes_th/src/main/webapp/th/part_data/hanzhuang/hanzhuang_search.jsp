<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBk"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.qm.th.ps.bean.CarData"%>

<% 
     /** 本页面不缓存 */
     response.setHeader("Pragma", "No-cache");
     response.setHeader("Cache-Control", "no-cache");
     response.setDateHeader("Expires", 0);
     
     /** 条件组名 */
     String rad = request.getParameter("rad");
     
     /** 开始日期 */
     String startDate = request.getParameter("startdate");
     
     /** 结束日期 */
     String endDate = request.getParameter("enddate");
     
     /** KIN号 */
     String kincode = request.getParameter("kincode");
    
     /** BOM信息列表 */
     List<CarData> list = new ArrayList<CarData>();
      
     /** 修改项目ID */
     String eid = request.getParameter("eid");
      
     /** 查询关键字 */
     String info = request.getParameter("info") != null ? request.getParameter("info") : "";
      
     /** 获得待显示的某一页数 */
     String strPage = request.getParameter("page"); 

     rad = rad == null ? "" : rad;
     startDate = startDate == null ? "" : startDate;
     endDate = endDate == null ? "" : endDate;
     kincode = kincode == null ? "" : kincode;
     
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
     Statement  stmt = null;
     ResultSet rs = null;

     try{
         // 创建数据库连接
         conn = new Conn_MES().getConn();
         StringBuilder strSql = new StringBuilder("SELECT ID, CCARNO, CSEQNO, WUPTIME, DWBEGIN FROM CARDATA");
         
         if(rad != null && !"".equals(rad.trim())){
             if("row_time".equalsIgnoreCase(rad)){
                 if(startDate != null && !"".equals(startDate)){
                     strSql.append(" where datediff(minute, dWBegin, '");
                     strSql.append(startDate);
                     strSql.append("') < 0");
                 }
                 if(endDate != null && !"".equals(endDate)){
                     if(strSql.indexOf("where") != -1){
                         strSql.append(" and ");
                     }else{
                         strSql.append(" where ");
                     }
                     strSql.append(" datediff(minute, dWBegin, '");
                     strSql.append(endDate);
                     strSql.append("') > 0");
                 }
             }else if("row_kin".equals(rad)){
                 if(kincode!= null && !"".equals(kincode)){
	                 strSql.append(" WHERE CCARNO like '");
	                 strSql.append(kincode);
	                 strSql.append("%'");
                 }
             }
         }
         stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
         rs = stmt.executeQuery(strSql.toString());
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
                CarData ca = new CarData(); 
                ca.setId(rs.getInt("ID"));// 主键
                ca.setCcarno(rs.getString("CCARNO"));// KIN号
                ca.setCseqno(rs.getString("CSEQNO"));// 焊装顺序号
                ca.setWuptime(rs.getInt("WUPTIME"));// 焊装上线次数
                ca.setDwbegin(rs.getString("DWBEGIN"));// 焊装上线时间
                
                list.add(ca);
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
        <title>焊装重复上线查询</title>
        <meta http-equiv=content-type content="text/html;charset=GBK">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <link rel="stylesheet" type="text/css" href="../../../cssfile/th_style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
        <script type="text/javascript" src="js/core.js"></script>
    </head>
    <body>
        <div align="center">
            <div class="title"><strong><!-- InstanceBeginEditable name="标题" -->焊装重复上线查询<!-- InstanceEndEditable --></strong></div>
            <form onsubmit="return check(this)">
                <table style="font-size:10pt;">
                    <tr>
                        <td>
                            <input type="radio" name="rad" value="row_time" checked>
                        </td>
                        <td>
                            开始时间:
                        </td>
                        <td>
                             <mes:calendar id="startdate" name="startdate" reSourceURL="../../../JarResource/" showDescripbe="false" haveTime="true" value="<%=startDate%>"/>
                        </td>
                        <td></td>
                        <td>
                            结束时间:
                        </td>
                        <td>
                             <mes:calendar id="enddate" name="enddate" reSourceURL="../../../JarResource/" showDescripbe="false" haveTime="true" value="<%=endDate%>"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="radio" name="rad" value="row_kin">
                        </td>
                        <td>
                            KIN:
                        </td>
                        <td colspan="3">
                             <mes:inputbox colorstyle="red" id="kincode" name="kincode" reSourceURL="../../../JarResource/" size="30" value="<%=kincode%>"/>
                        </td>
                        <td>
                             <mes:button id="c_submit" reSourceURL="../../../JarResource/" value="查询" submit="true"/>
                        </td>
                    </tr>
                </table>
            </form>
            <hr width="550px" />
            <br>
            <mes:table reSourceURL="../../../JarResource/" id="qulity_states">
                <mes:thead>
                    <mes:tr>
                        <mes:td width="30">序号</mes:td>
                        <mes:td width="160">KIN号</mes:td>
                        <mes:td width="160">焊装顺序号</mes:td>
                        <mes:td width="120">焊装上线次数</mes:td>
                        <mes:td width="160">焊装上线时间</mes:td>
                    </mes:tr>
                </mes:thead>
                <mes:tbody iterator="<%=list.iterator()%>" type="th.ps.bean.CarData" varName="ca">
                    <mes:tr id = "tr${ca.id}">
                        <mes:td><%=serialNumber++ + (intPage - 1)*PageSize%></mes:td>
                        <mes:td>${ca.ccarno}</mes:td>
                        <mes:td>${ca.cseqno}</mes:td>
                        <mes:td>${ca.wuptime}</mes:td>
                        <mes:td>${ca.dwbegin}</mes:td>
                    </mes:tr>
                </mes:tbody>
                <mes:tfoot>
                   <mes:tr>
                     <mes:td colspan="100%" align="center">
                         <form id="tform" name="form" style="margin: 0px"  method="post" onSubmit="return checkinput(this)" action="hanzhuang_search.jsp">
                               <input type="hidden" value="<%=info%>" name="info" >
                               <input type="hidden" value="<%=startDate%>" name="startdate" >
                               <input type="hidden" value="<%=endDate%>" name="enddate" >
                               <input type="hidden" value="<%=kincode%>" name="kincode" >
                               <input type="hidden" value="<%=rad%>" name="rad" >
                               
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
           if(eid!=-1 && document.getElementById('tr'+eid)!=null){
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
        * 默认选中 radioButton
        */
       var selRadio = function(){
            // radio button 对象数组
            var rads = document.getElementsByName("rad");
            // 选中的radio button名
            var selName = "<%=rad%>";
            
            // 根据名字选中相应的radio button
            for(var i=0; i<rads.length; i++){
                var rad = rads[i];
                if(selName && rad["value"] == selName){
                    rad["checked"] = "checked";
                    break;
                }
            }
       }();
       
       /**
        * 校验表单
        */
       function check(formObj){
            /** 开始日期 */
            var startdate = document.getElementById("startdate");
            
            // 校验开始日期格式
            if(checkDate(startdate)){
                // 结束日期
                var enddate = document.getElementById("enddate");
                // 校验结束日期
                return checkDate(enddate);
            }
            return false;
       }
       
       /**
        * 检查日期输入格式
        */
       function checkDate(cal){
           /** 合理的日期格式 */
           var regexp = /(^((19){1}|(20){1}))\d{2}-(([0-1]{1}[0-9]{1}))-([0-3]{1}[0-9]{1}) (([0-2]{1}[0-9]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})$)/;
           /** 输入是否合法 */
           var isLegal = true;
           
           // 如果文文本框为空，则不做校验
           if(cal.value){
	           /** 是否符合规则 */
	           isLegal = regexp.test(cal.value);
	           
	           if(!isLegal){
	               alert("上线日期格式不正确!");
	               cal.value = "";
	               cal.focus();
	           }
           }
           return isLegal;
       }
    </script>
</html>