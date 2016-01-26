<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.qm.th.helpers.Conn_MES"%>
<%@ page import="com.qm.th.beans.SpecKin"%>

<%@ taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>

<% 
	/** 本页面不缓存 */
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	
	/** 序号 */
	int serialNumber = 1;
	
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	List<SpecKin> list = new ArrayList<SpecKin>();
	
	try{
		StringBuilder strSql  = new StringBuilder();
		strSql.append(" SELECT A.ID, A.CVINCODE, A.CCARNO,");
		strSql.append(" CONVERT(char, A.DWBEGIN, 120) AS DWBEGIN, CONVERT(char, A.DABEGIN, 120) AS DABEGIN");
		strSql.append(" FROM CARDATA A INNER JOIN SPECIALKIN B ");
		strSql.append(" ON A.CCARNO LIKE B.CCARNO + '%' AND DATEDIFF(DAY, B.DTODATE, GETDATE()) < 0");
		
		conn = new Conn_MES().getConn();
		stmt = conn.prepareStatement(strSql.toString());
		rs = stmt.executeQuery();
		
        while(rs.next()){
        	SpecKin sk = new SpecKin();
        	sk.setId(rs.getString("ID"));
			sk.setKin(rs.getString("CCARNO"));
			sk.setVin(rs.getString("CVINCODE"));
			sk.setDwbegin(rs.getString("DWBEGIN"));
			sk.setDabegin(rs.getString("DABEGIN"));
			
			list.add(sk);
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
            <br>
            <mes:button id="refresh" reSourceURL="../../JarResource/" onclick="refresh()" submit="false" value="刷新"/>
            <mes:table reSourceURL="../../JarResource/" id="spec_states" width="100%">
                <mes:thead>
                    <mes:tr>
                        <mes:td width="60">序号</mes:td>
                        <mes:td width="120">KIN号</mes:td>
                        <mes:td width="120">VIN号</mes:td>
                        <mes:td width="160">焊装上线时间</mes:td>
                        <mes:td width="160">总装上线时间</mes:td>
                    </mes:tr>
                </mes:thead>
                <mes:tbody iterator="<%=list.iterator()%>" type="th.bs.bean.SpecKin" varName="sk">
                    <mes:tr id = "tr${sk.id}">
                        <mes:td><%=serialNumber++%></mes:td>
                        <mes:td>${sk.kin}</mes:td>
                        <mes:td>${sk.vin}</mes:td>
                        <mes:td>${sk.dwbegin}</mes:td>
                        <mes:td>${sk.dabegin}</mes:td>
                    </mes:tr>
                </mes:tbody>
            </mes:table>
        </div>
    </body>
   <script type="text/javascript">
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
     	   window.location.href = "specKin.jsp";
       };
    </script>
</html>