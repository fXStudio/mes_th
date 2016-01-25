<%@ page contentType="text/html;charset=GBK" language="java" pageEncoding="GBK"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="org.apache.commons.fileupload.FileUploadException"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="th.bs.bom.ReadExcel;"%>

<%
    int max_memory_size = 2048;// 缓存容量
    
    String property = "java.io.tmpdir";// 临时目录
    String tmp_path = System.getProperty(property);// windows临时目录
    
    File file = null;

    // Check that we have a file upload request
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

    // Create a factory for disk-based file items
    DiskFileItemFactory factory = new DiskFileItemFactory(max_memory_size, new File(tmp_path));

    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);

    List<FileItem> items = new ArrayList<FileItem>();
    if (isMultipart) {
        // Parse the request
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    // 遍历form表单元素
    for (FileItem item : items) {
        // 处理上传元素
        if (!item.isFormField()) {
            try {
                String file_name = item.getName();
                int lastIndex = file_name.lastIndexOf("\\");

                if (lastIndex > 0){
	                file_name = file_name.substring(lastIndex);
                }
                file = new File(tmp_path + "\\" + file_name);
            	
                item.write(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 是否添加成功
    String msg = "添加成功";

    // 导入装配料号与配置信息
    if (file != null){
        try{
            ReadExcel.readXml(file);
        }catch(Exception e){
            msg = e.getMessage();
        }
    }
    session.setAttribute("mes_msg", msg);
    response.sendRedirect("bomImport.jsp");
%>