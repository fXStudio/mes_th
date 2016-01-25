<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="common.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="th.bs.bean.NewCarData"%>
<%@ page import="mes.os.util.LinkQuery"%>

<% 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%    
	  /** 本页面不缓存 */
	  response.setHeader("Pragma", "No-cache");
	  response.setHeader("Cache-Control", "no-cache");
	  response.setDateHeader("Expires", 0);

	  /** BOM信息列表 */
      List<NewCarData> list = new ArrayList<NewCarData>();
		
	  /** 查询关键字 */
	  String info = request.getParameter("info") != null ? request.getParameter("info") : "";
		
	  /** 获得待显示的某一页数 */
	  String strPage = request.getParameter("page"); 
		
	  int PageSize = 30; //一页显示的记录数
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
      
      try{
          // 创建数据库连接
          conn = new Conn_MES().getConn();
          // 基础查询语句
          String strSql = "select b.ctfass, DBO.TRIMSUFFIX(cfilename) cfilename, ctype,";
                 strSql += " convert(varchar(20), usedtime, 120) usedtime, b.cRemark";
                 strSql += " from (select *  from fit_newcar where Usedtime is not null) a ";
                 strSql += " inner join ( select ctfass, cqadno, cremark from partData";
                 strSql += " group by ctfass, cqadno, cremark ) b on a.CQadno = b.cQADNo";
          
          // 按条件模糊查询
          if(!info.equals("")){
              ArrayList<String> colist = new ArrayList<String>(5);
              colist.add("ctfass".toLowerCase());
              
              info = info.trim();
              LinkQuery link = new LinkQuery();
              
              String condition = link.linkquery(info, colist, strSql, conn).toString();
              strSql = "SELECT * FROM (" + strSql + ") A WHERE " + condition;
          }
          strSql += " order by usedtime desc";
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
	        	 NewCarData pd = new NewCarData(); 
	             pd.setCtfass(rs.getString("ctfass"));
	             pd.setCfilename(rs.getString("cfilename"));
	             pd.setCremark(rs.getString("cRemark"));
	             pd.setCtype(rs.getString("ctype"));
	             pd.setUsedtime(rs.getString("usedtime"));
	             
	             list.add(pd);
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
        <title>BOM信息维护</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>cssfile/style.css">
        <script type="text/javascript" src="<%=basePath%>JarResource/META-INF/tag/taglib_common.js"></script>
    </head>
    <body>
        <div class="title"><strong><!-- InstanceBeginEditable name="标题2" -->新车型匹配记录查询<!-- InstanceEndEditable --></strong></div>
        <br>
        <div align="center">
            <!-- InstanceBeginEditable name="过滤" -->
            <form  name="form1" method="get" style="margin: 0px" action="newCarList.jsp">
                <table class="tbnoborder">
                    <tr>
                        <td class="tbnoborder">天合零件号查询:</td>
                        <td class="tdnoborder">
                            <input type=text value="<%=info%>" name="info" id="info" class="box1" size=20 onMouseOver="this.className='box3'" 
                                   onFocus="this.className='box3'" onMouseOut="this.className='box1'" onBlur="this.className='box1'"></td>
                        <td class="tdnoborder">
                            <mes:button id="s1" reSourceURL="../../../JarResource/" submit="true" value="查询"/>
                        </td>
                    </tr>
                </table>
            </form>
            <!-- InstanceEndEditable -->
            <mes:table reSourceURL="../../JarResource/" id="qulity_states">
                <mes:thead>
                    <mes:tr>
                        <mes:td width="30">序号</mes:td>
                        <mes:td width="120">天合零件号</mes:td>
                        <mes:td width="120">报文名</mes:td>
                        <mes:td width="60">报文类型</mes:td>
                        <mes:td width="160">操作时间</mes:td>
                        <mes:td width="200">零件描述</mes:td>
                    </mes:tr>
                </mes:thead>
                <mes:tbody iterator="<%=list.iterator()%>" type="th.bs.bean.NewCarData" varName="pd">
                    <mes:tr>
                        <mes:td><%=serialNumber++ + (intPage - 1)*PageSize%></mes:td>
                        <mes:td>${pd.ctfass}</mes:td>
                        <mes:td>${pd.cfilename}</mes:td>
                        <mes:td>${pd.ctype}</mes:td>
                        <mes:td>${pd.usedtime}</mes:td>
                        <mes:td>${pd.cremark}</mes:td>
                    </mes:tr>
                </mes:tbody>
                <mes:tfoot>
                   <mes:tr>
                     <mes:td colspan="100%" align="center">
                         <form  name="form" style="margin: 0px"  method="post" onSubmit="return checkinput(this)" action="newCarList.jsp">
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
    </script>
</html>