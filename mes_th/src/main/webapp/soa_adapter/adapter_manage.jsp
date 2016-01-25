<%@ page language="java" import="java.sql.*"
	contentType="text/html;charset=gb2312"%>
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="mes.beans.Adapter" />
<jsp:useBean id="Conn" scope="page" class="com.qm.mes.th.helper.Conn_MES" />
<%@ page import="mes.framework.*"%>
<%@ page import="mes.framework.forjsp.soa.*"%>
<%@ page import="mes.framework.dao.IDAO_Core"%>
<%@ page import="mes.framework.dao.DAOFactory_Core"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<%@page import="mes.ra.util.*"%>
<%	response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	%>
<%
	/* 
	 * 时间：2007-7-2
	 * 作者：于丽达
	 * 业务描述：适配器管理主页面
	 */

    response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
    Connection conn=null;
    Statement stmt=null;
    ResultSet rs=null;
    IDAO_Core dao=null;
    DataServer_SOA ds=null;
    String eid = null;
	eid=request.getParameter("eid");
	ArrayList<String> colist = new ArrayList<String>(3);
	int PageCount=0; //总页数
    try
    {
		//获取连接
	   	conn=Conn.getConn();
	   	dao=DAOFactory_Core.getInstance(DataBaseType.getDataBaseType(conn));;
	   	ds=new DataServer_SOA(conn);
		stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		int PageSize; //一页显示的记录数
		int RowCount; //记录总数
		int intPage=0; //待显示页码
		String strPage;	
		int i=0;
		PageSize=10;
		//获得待显示的某一页数
		strPage=request.getParameter("page");
		if(strPage==null|| strPage.equals(""))
		{    intPage=1;
		}
		else
		{    intPage=Integer.parseInt(strPage);
		    if(intPage<1) intPage =1;
		} 
		String info=request.getParameter("info");
		if(info==null)
		     info="";
		String sql="";
		//关键字查询用
		if(info==null||info.equals("")){
				sql=dao. getSQL_AllAdapterInfos();
				sql = "select * from (" + sql +") a ";
		}
		else{					
			//选择要查询的字段			
			colist.add("ciparameter".toLowerCase());
			colist.add("cprocessname".toLowerCase());	
			colist.add("cservername".toLowerCase());				
			info=info.trim();			  
				sql=dao. getSQL_AllAdapterInfos();
			//模糊查询		
		   LinkQuery link=new LinkQuery();		    
		   StringBuffer wh= link.linkquery(info,colist,sql,conn);
		   sql = "select * from (" + sql +") a where " + wh.toString(); 
			// sql = dao.getStateByName(info);		     
		}			   
	    sql=sql+" order by nprocessid,cialiasname";
		List<Adapter> list=new ArrayList<Adapter>();
		rs=stmt.executeQuery(sql);
		rs.last();
		//获得总记录数
		RowCount=rs.getRow();
		
		//总页数
		PageCount = (RowCount+PageSize-1) / PageSize;
		if(intPage>PageCount) intPage = PageCount;
		if(PageCount>0)
		{
		
		    //将记录指针定位到下一显示位置
		    rs.absolute((intPage-1)*PageSize + 1);
		    i = 0;
		    while(i<PageSize && !rs.isAfterLast())
		    {
				Adapter adapter=new Adapter();
				adapter.setProcessid(rs.getString(1));
				adapter.setServeraliasname(rs.getString(2));
				adapter.setParametername(rs.getString(3));
				
				adapter.setProcessname(ds.getProcessName_ProcessID(adapter.getProcessid()));
				if(adapter.getProcessname()==null)adapter.setProcessname("");
				adapter.setServerid(ds.getServerID_Processid_ServerAlias(adapter.getProcessid(),adapter.getServeraliasname()));
				if(adapter.getServerid()==null)adapter.setServerid("");
				if(adapter.getServerid()!=null&&!adapter.getServerid().trim().equals("")) adapter.setServername(ds.getServerName_ServerID(adapter.getServerid()));
				if(adapter.getServername()==null)adapter.setServername("");
				list.add(adapter);
			i++;
	   		rs.next();
	      }
	}
	%>
<html>
	<head>
		<style>
.head2{	height:22px;color:ffff00;}
</style>
		<link rel="stylesheet" type="text/css" href="../cssfile/style.css">

		<meta http-equiv="Content-Language" content="zh-cn" />

	</head>
	<body background="../images/background.jpg">
		<!-- 引用通用脚本 -->
		<script type="text/javascript"
			src="../JarResource/META-INF/tag/taglib_common.js"></script>
		<div align="center">
			<strong>适配器管理</strong>
		</div>
		<br>
		<div align="center">
			<mes:table reSourceURL="../JarResource/" id="table1">
				<mes:thead>
					<mes:tr>
						<td width="140">
							流程号-名
						</td>
						<td width="400">
							服务号-名（别名）
						</td>
						<td width="120">
							输入的参数名
						</td>
						<td width="53">
							查看
						</td>
						<td width="53">
							更新
						</td>
						<td width="53">
							删除
						</td>
					</mes:tr>
				</mes:thead>
				<mes:tbody iterator="<%=list.iterator() %>" type="mes.beans.Adapter"
					varName="adapter">
					<mes:tr id="tr${adapter.processid}-${adapter.serverid}-${adapter.parametername}">

						<mes:td>${adapter.processid}-${adapter.processname}</mes:td>
						<mes:td>${adapter.serverid}-${adapter.servername}(${adapter.serveraliasname})</mes:td>
						<mes:td>${adapter.parametername}</mes:td>
						<mes:td>
							<a
								href='adapter_look.jsp?processid=${adapter.processid}&eid=${adapter.processid}-${adapter.serverid}-${adapter.parametername}&intPage=<%=intPage %>&info=<%=info%>'>查看</a>
						</mes:td>
						<mes:td>
							<a
								href="adapter_update.jsp?processid=${adapter.processid}&aliasname=${adapter.serveraliasname}&parameter=${adapter.parametername}&eid=${adapter.processid}-${adapter.serverid}-${adapter.parametername}&intPage=<%=intPage %>&info=<%=info%>">更新</a>
						</mes:td>
						<mes:td>
							<a href="#" title="点击将删除这条记录！"
								onClick="javascript:if(confirm('真的要删除这条记录吗？')) window.location.href='adapter_deleting.jsp?processid=${adapter.processid}&eid=${adapter.processid}-${adapter.serverid}-${adapter.parametername}&aliasname=${adapter.serveraliasname}&parameter=${adapter.parametername}&intPage=<%=intPage %>&info=<%=info%>';return false;">删除</a>
						</mes:td>
					</mes:tr>
				</mes:tbody>
				<mes:tfoot>
					<mes:tr>
						<mes:td colspan="100%" align="center">
							<form name="form" style="margin: 0px" method="post"
								onSubmit="return checkinput(this)" action="adapter_manage.jsp">
                             <input type="hidden" value="<%=info%>" name="info">

								共
								<%=RowCount%>
								条 第
								<%=intPage%>
								页 共
								<%=PageCount%>
								页
								<%if(PageCount>1){
		   out.println("跳转到第");
		   out.print("<input size=\"1\"  name=\"page\" value=");%>
								<%=intPage%>
								<%out.println(">");
		   out.println("页");
		   }%>
								<%if(intPage>1){    
		out.println("<a href=\"?page=1&info="+info+"\">第一页</a>");	
		out.println("<a href=\"?page="+(intPage-1)+"&info="+info+"\">上一页</a>");		
		}%>
								<%if(intPage<PageCount){
		out.println("<a href=\"?page="+(intPage+1)+"&info="+info+"\">下一页</a>");
		out.println("<a href=\"?page="+(PageCount)+"&info="+info+"\">最后一页</a>");
		}%>

								<a href='adapter_add.jsp?intPage=<%=intPage %>&eid=${adapter.processid}-${adapter.serverid}-${adapter.parametername}&info=<%=info%>'>【添加新记录】</a>
							</form>
						</mes:td>
					</mes:tr>
				</mes:tfoot>
			</mes:table>
           <form  name="form1" method="post"  style="margin: 0px" action="adapter_manage.jsp">
			<table class="tbnoborder">
					<tr>
						<td class="tbnoborder">
							关键字查询：&nbsp;&nbsp;
						</td>
						<td class="tdnoborder">
							<input type=text value="<%=info%>" name="info" id="info"
								class="box1" size=10 onMouseOver="this.className='box3'"
								onFocus="this.className='box3'"
								onMouseOut="this.className='box1'"
								onBlur="this.className='box1'">
						</td>
						<td class="tdnoborder">
							<mes:button id="s1" reSourceURL="../JarResource/" submit="true"
								value="查询" />
						</td>
					</tr>
				</table>
				</form>	
			<div style="color:ff0000;font-size:9pt">
				关键字：流程名、服务名、参数名
			</div>			
			<div style="color:ff0000;font-size:9pt">
				参数为空的时候查询所有信息，查询条件以"," 分开。
			</div>
		</div>
	</body>
	<%
		//释放资源
	
		}catch(Exception e)
		{		
			e.printStackTrace();
		}finally {			
			if(stmt!=null)stmt.close();
			if(conn!=null)conn .close();
			}
		
	%>
	<script type="text/javascript">
function checkinput(thisform){
        var re =  /^[0-9]+$/;
		if(<%=PageCount%>>1)
		if(!re.test(document.getElementsByName("page")[0].value ))
     {
	    alert("跳转页数应由正整数组成!");
	    document.getElementsByName("page")[0].value="";
	    return false;
    }	
    return true;
	}
	function selecttr(){
		var eid = '<%=eid==null?"-1":eid%>';		
		if(eid!=-1){
			document.getElementById('tr'+eid).click();
		}		
	}selecttr();
</script>
</html>
