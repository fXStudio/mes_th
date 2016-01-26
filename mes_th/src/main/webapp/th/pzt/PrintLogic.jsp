<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="com.qm.th.mdorder.ConfigOrderHandler"%>
<%@ page import="com.qm.th.mdorder.bean.COrderEntity"%>

<%
	// 取前一页面日期
	String jspRq = (String)request.getAttribute("rq");

	// 打印配置工具类
	ConfigOrderHandler coHandler = new ConfigOrderHandler(jspRq);

	// 要打印的数据列表
	List<COrderEntity> list = coHandler.execute();

	for(COrderEntity entity : list) {
		out.write("<tr>");	
			out.write("<td>" + entity.getGroupId() + "</td>");
			out.write("<td>" + entity.getDescript() + "</td>");
			out.write("<td>");
				out.write("历史架号<input name='oldjh" + entity.getGroupId() + "' id='oldjh" + entity.getGroupId() + "' size='3' value='0'/>");
				out.write("<input type='button' value='历史打印' onclick='printOld(" + entity.getGroupId() + ")'/>");
				out.write("<input type='button' value='重新打印' onclick='reprint(" + entity.getGroupId() + ")'/>");
			out.write("</td>");
			out.write("<td>" + entity.getMaxCarno() + "</td>");
			out.write("<td >");
				out.write("<label ><strong><font color='#ff0000' size='3' face='黑体'>" + entity.getMinPartCount() + "</strong></label>");
			out.write("</td>");
			out.write("<td>");
				if(entity.getPrintRadio() == 1){
					out.write(entity.getTFassCount() + "辆份");
				}
				if(entity.getPrintRadio() == 2){
					out.write(entity.getTFassCount() * 2 + "辆份");	
				}
				if(entity.getPrintRadio() == 3){
					out.write(entity.getPerTimeCount() + "辆份");	
				}
			out.write("</td>");
			out.write("<td><input type='checkbox' name='checkBox" + entity.getGroupId() + "'   id='checkBox" + entity.getGroupId() + "'  disabled='true'");
			
			if(entity.getAuto().equals("1")){
				out.write("checked");
			}
			out.println("/> 自动打印</td>");
			out.write("<td><input type='button' name='button" + entity.getGroupId() + "' id='button" + entity.getGroupId() + "' value='提交' onclick='openApp(" + entity.getGroupId() + "," + entity.getPrintRadio() + "," + 1 + "," + entity.getPages() + "," + entity.getMinPartCount() + "," + entity.getPerTimeRow() + "," + (entity.isContinue() ? 1 : 0) + ")'/></label></td>");
		out.write("</tr>");
		
		// 输出自动打印脚本
		if(entity.getOpenApp() != null) { 
			String str = (String)request.getAttribute("openApp");
				   str = str == null ? entity.getOpenApp() : str + entity.getOpenApp();
			
			// 设置自动打印脚本
			request.setAttribute("openApp", str);
		}
	}
%>