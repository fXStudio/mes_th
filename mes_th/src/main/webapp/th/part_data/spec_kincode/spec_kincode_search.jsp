<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@ page import="com.qm.th.helper.Conn_MES"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.qm.th.ps.bean.CarData"%>
<%@ page import="com.qm.mes.os.util.LinkQuery"%>

<%    
      /** 本页面不缓存 */
      response.setHeader("Pragma", "No-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setDateHeader("Expires", 0);

      /** BOM信息列表 */
      List<CarData> list = new ArrayList<CarData>();
        
      /** 修改项目ID */
      String eid = request.getParameter("eid");
        
      /** 查询关键字 */
      String info = request.getParameter("info") != null ? request.getParameter("info") : "";
      
      int serialNumber = 1;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try{
          // 基础查询语句
    	  StringBuilder strSql = new StringBuilder();
    	  strSql.append(" SELECT B.ID, B.CCARNO, A.CSEQNO, CONVERT(CHAR, A.DWBEGIN, 120) AS DWBEGIN,")
                .append(" CONVERT(CHAR, A.DABEGIN, 120) AS DABEGIN, B.CREMARK, B.DTODATE")
    	        .append(" FROM CARDATA A RIGHT JOIN SPECIALKIN B")
    	        .append("      ON A.CCARNO LIKE B.CCARNO + '%'")
    	        .append(" WHERE DATEDIFF(DAY, B.DTODATE, GETDATE()) < 0");

          // 创建数据库连接
          conn = new Conn_MES().getConn();
          // 按条件模糊查询
          if(!info.equals("")){
              ArrayList<String> colist = new ArrayList<String>(5);
              colist.add("CCARNO".toLowerCase());
              colist.add("CSEQNO".toLowerCase());
              
              info = info.trim();
              LinkQuery link = new LinkQuery();
              
              String condition = link.linkquery(info, colist, strSql.toString(), conn).toString();
              String subQuery = strSql.toString();
              strSql.setLength(0);
              strSql.append("SELECT * FROM (")
            		.append(subQuery)
              		.append(") A WHERE ")
              		.append(condition);
          }
          stmt = conn.prepareStatement(strSql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          rs = stmt.executeQuery();
          
          while(rs.next()){
                 CarData cd = new CarData(); 
                 cd.setId(rs.getInt("ID"));// 主键
                 cd.setCcarno(rs.getString("CCARNO"));// KIN号
                 cd.setCseqno(rs.getString("CSEQNO"));// 焊装顺序号
                 cd.setDwbegin(rs.getString("DWBEGIN"));// 焊装上线时间
                 cd.setCremark(rs.getString("CREMARK"));// 备注
                 cd.setDtodate(rs.getString("DTODATE"));// 有效时间
                 cd.setDabegin(rs.getString("DABEGIN"));// 总装上线时间
                 
                 list.add(cd);
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
        <title>特殊KIN号查询</title>
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
    </head>
    <body>
        <div class="title"><strong><!-- InstanceBeginEditable name="标题2" -->特殊KIN号查询<!-- InstanceEndEditable --></strong></div>
        <br>
        <div align="center">
            <mes:table reSourceURL="../../../JarResource/" id="qulity_states">
                <mes:thead>
                    <mes:tr>
                        <mes:td width="30">序号</mes:td>
                        <mes:td width="120">KIN</mes:td>
                        <mes:td width="90">焊装顺序号</mes:td>
                        <mes:td width="160">焊装上线时间</mes:td>
                        <mes:td width="160">总装上线时间</mes:td>
                        <mes:td width="160">有效时间</mes:td>
                        <mes:td width="120">备注</mes:td>
                        <mes:td width="30">更改</mes:td>
                        <mes:td width="30">删除</mes:td>
                    </mes:tr>
                </mes:thead>
                <mes:tbody iterator="<%=list.iterator()%>" type="th.ps.bean.CarData" varName="cd">
                    <mes:tr id = "tr${cd.id}">
                        <mes:td><%=serialNumber++%></mes:td>
                        <mes:td>${cd.ccarno}</mes:td>
                        <mes:td>${cd.cseqno}</mes:td>
                        <mes:td>${cd.dwbegin}</mes:td>
                        <mes:td>${cd.dabegin}</mes:td>
                        <mes:td>${cd.dtodate}</mes:td>
                        <mes:td>${cd.cremark}</mes:td>
                        <mes:td>
                            <a href="javascript:window.location.href='spec_kincode_modify.jsp?intId=${cd.id}'">更改</a>
                        </mes:td>
                        <mes:td>
                            <a href="javascript:if(confirm('真的要删除这条记录吗？')){ window.location.href='spec_kincode_del.jsp?kinCode=${cd.ccarno}'; }">删除</a>
                        </mes:td>
                    </mes:tr>
                </mes:tbody>
                <mes:tfoot>
                   <mes:tr>
                     <mes:td colspan="100%" align="center">
                         <form  name="form" style="margin: 0px"  method="get" onSubmit="return checkinput(this)" action="spec_kincode_search.jsp">
                              <a href="spec_kincode_add.jsp">【添加新记录】</a>
                         </form>
                      </mes:td>
                   </mes:tr>  
                </mes:tfoot> 
            </mes:table>
            <!-- InstanceBeginEditable name="过滤" -->
            <form  name="form1" method="get" style="margin: 0px" action="spec_kincode_search.jsp">
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
                        关键字为：<a class="div_red">KIN 焊装顺序号</a><br>
                        参数为空的时候查询所有信息，查询条件以<a class="div_red">"," </a>分开。<br>
                    </div>
            </div>
            <!-- InstanceEndEditable -->
        </div>
    </body>
    <script type="text/javascript">
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