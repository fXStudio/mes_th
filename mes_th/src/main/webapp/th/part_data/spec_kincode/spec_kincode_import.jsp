<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" language="java"%>
<%@taglib uri="http://www.faw-qm.com.cn/mes" prefix="mes"%>
<html>
    <head>
        <title>特殊KIN号批量导入</title>
        <link rel="stylesheet" type="text/css" href="../../../cssfile/style.css">
        <script type="text/javascript" src="../../../JarResource/META-INF/tag/taglib_common.js"></script>
        <style>
            .mac_table td{
                border-right: 1px solid #C1DAD7;
                border-bottom: 1px solid #C1DAD7;
                border-top: 1px solid #C1DAD7;
                border-left: 1px solid #C1DAD7;
                background: #fff; //
                font-size: 11px;
                padding: 6px 6px 6px 12px;
                color: #4f6b72;
                font-family: 宋体, Arial;
                font-size: 12px;
            }
        </style>
    </head>
    <body>
        <div class="title"><strong><!-- InstanceBeginEditable name="标题2" -->特殊KIN号批量导入<!-- InstanceEndEditable --></strong></div>
        <div align="center">
            <div id="tip" style="height:20px;text-align:center;font:13px;color:red;"></div>
            <%
                   Object obj = session.getAttribute("mes_msg");
                   if(obj != null){
                       String msg = (String)obj;
                       out.println("<script>var tip_obj=document.getElementById(\"tip\");tip_obj.innerHTML=\"" + msg + "\"</script>");
                   } else {
                	   response.sendRedirect("./spec_kincode_search.jsp");
                   }
                   session.removeAttribute("mes_msg");
            %>
            <form name="form1" id="form1" action="upload.jsp" enctype="multipart/form-data" method="post" onsubmit="return check(this)">
                <table cellspacing=0 cellpadding=0 border=0 class="mac_table">
                    <tr>
                        <td>
                                上传文件: 
                        </td>
                        <td>
                            <input type="file" name="ofile1" size="15"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2 align="center">
                            <mes:button id="qulityMsg_modified" reSourceURL="../../../JarResource" value="上传" submit="true"/>&nbsp;
                            <mes:button id="qulityMsg_reset" reSourceURL="../../../JarResource" value="重置" submit="false"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div class="div_normal">
                    注：文件应为Excel格式. <a  href="./specialKin.xls">下载模板文件</a><br>
            </div>
        </div>
    </body>
    <script type="text/javascript" language="javascript">
       <!--
           /*
            * form表单校验
            *
            * @param obj 表单对象
            * @author gaohf
            * @date 2010-06-10
            */
           function check(form_obj){
               var upload_obj = form_obj["ofile1"];
               var cur_value = upload_obj.value;
               var regex = /.*\.xls$/i;// 判断文件类型是否为excel
               var tip = document.getElementById("tip");
               
               var isExcel = cur_value.match(regex) != null;
               if(!isExcel){
                    tip.innerHTML = "文件格式不正确";
               }
               return isExcel;
           }
       -->
    </script>
</html>