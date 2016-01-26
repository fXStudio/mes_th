<%@ page language="java" import="java.sql.Connection,java.util.*" contentType="text/html;charset=GBK"%>
<%@page	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%@ page import="org.apache.commons.fileupload.*,java.io.*" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.Statement"%>

<jsp:useBean id="Conn" scope="page" class="com.qm.th.helper.Conn_MES"/>
<%
    final  Log log = LogFactory.getLog("balance_importing.jsp");
	Connection con = null;
	try{
		con = Conn.getConn();
		// 创建文件处理工厂，它用于生成 FileItem 对象。
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		// 设置文件的缓存路径
		String tempdir =this.getServletContext().getRealPath("/upload/temp");    
		java.io.File d = new java.io.File(application.getRealPath("/trwbalance/update"));
		if(!d.exists()){
			d.mkdirs();
		}
		factory.setSizeThreshold(100*1024*1024); // 设置最多只允许在内存中存储的数据,单位:字节
		factory.setRepository(d); // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录(默认可以不用设置)

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置允许用户上传文件大小,单位:字节
		upload.setSizeMax(100*1024*1024);
		//上传文件,并解析出所有的表单字段，包括普通字段和文件字段
		List  items = upload.parseRequest(request); 
		//下面对每个字段进行处理，分普通字段和文件字段
		Iterator it = items.iterator();
		while(it.hasNext()){
			FileItem fileItem = (FileItem) it.next();
			//如果是普通字段
			if(fileItem.isFormField()){  //是普通的字段
				System.out.println(fileItem.getFieldName() + "   " + fileItem.getName() + "   " + new String(fileItem.getString().getBytes("iso8859-1"), "gbk"));
				fileItem.getFieldName();//得到字段name属性的值
				fileItem.getName();//得到file字段的文件名全路径名，如果不是file字段，为null
				fileItem.getString();//得到该字段的值,默认的编码格式
				fileItem.getString("GB2312");//指定编码格式
			}else{//文件字段
				System.out.println(fileItem.getFieldName() + "   " +
				fileItem.getName() + "   " +//得到file字段的文件名全路径名
				fileItem.isInMemory() + "    " +//用来判断FileItem类对象封装的主体内容是存储在内存中，还是存储在临时文件中，如果存储在内存中则返回true，否则返回false
				fileItem.getContentType() + "   " +//文件类型
				fileItem.getSize());          //文件大小

				//保存文件，其实就是把缓存里的数据写到目标路径下
				if(fileItem.getName()!=null && fileItem.getSize()!=0){
					File fullFile = new File(fileItem.getName());
					File newFile = new File(application.getRealPath("/trwbalance/update") + "/" + fullFile.getName());
					fileItem.write(newFile);
					System.out.println("上传文件路径："+application.getRealPath("/trwbalance/update")+ "/" + fullFile.getName());
					CallableStatement cstmt = con.prepareCall("{ call sp_cp7_inserttxt(?)}");
					cstmt.setString(1, application.getRealPath("/trwbalance/update") + "/" + fullFile.getName());
					cstmt.execute();
					/*
					String sql = "DELETE FROM cp7 WHERE (cvincode IN (SELECT DISTINCT cvincode FROM VIEW_cp7_tempdata))"
						+" insert into cp7 select * from VIEW_cp7_tempdata WHERE CPARTNO<>'3C0 400 035'"
						+" insert into cp7 (CCARNO,CVINCODE,DAENDTIME,CPARTNO,CPARTNAME,NUM)select CCARNO,CVINCODE,DAENDTIME,'3C0 400 035 L',CPARTNAME,NUM from VIEW_cp7_tempdata WHERE CPARTNO='3C0 400 035'"
						+" insert into cp7 (CCARNO,CVINCODE,DAENDTIME,CPARTNO,CPARTNAME,NUM)select CCARNO,CVINCODE,DAENDTIME,'3C0 400 035 R',CPARTNAME,NUM from VIEW_cp7_tempdata WHERE CPARTNO='3C0 400 035'"
						+" DELETE FROM cp7_productdata WHERE (cvincode IN (SELECT DISTINCT dbo.cp7.cvincode FROM dbo.cp7))"
						+" DELETE FROM cp7_cardata WHERE (cvincode IN (SELECT DISTINCT dbo.cp7.cvincode FROM dbo.cp7))"
						+" DELETE FROM cp7_err WHERE (cvincode IN (SELECT DISTINCT dbo.cp7.cvincode FROM dbo.cp7))"
						+" insert into cp7_productdata select *,num AS nusednum from cp7";
					Statement stmt = con.createStatement();
					log.debug("创建查询设置"+sql);
					stmt.execute(sql);
					*/
					cstmt = con.prepareCall("{ call sp_cp7_getvin}");
					cstmt.execute();
					/*
					if(stmt != null){
						stmt.close();
						stmt = null;
					}
					*/
					out.println("<script>alert(\"导入完毕\");</script>");
				}else{
				System.out.println("文件没有选择 或 文件内容为空");
				out.println("<script>alert(\"文件没有选择或文件内容为空\");</script>");
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
		log.error(e.toString());
	}finally{
		if(con!=null){
			con.close();
			con = null;
		}
	}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2 312"/> 
  </head>
  
  <body><br><br></body>
  <script type="text/javascript">
  function back(){
  	window.location.href = "balance_import.jsp";
  }back();
  
  </script>
</html>
