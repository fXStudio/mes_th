package com.qm.mes.os.util;

import java.io.OutputStream;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.text.SimpleDateFormat;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;


public class MPSPlanExport {
	public static synchronized void createExcelFile(ResultSet rs,
			OutputStream os) throws Exception {
		//Method 2：将WritableWorkbook直接写入到输出流
	jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
	//创建Excel工作表
	WritableSheet sheet = wwb.createSheet("主作业计划", 0);
	
	    ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount(), row = 0;
            cols=10;
		if (true) {
			
		   sheet.addCell(new Label(0, row, "主计划序号"));
			sheet.addCell(new Label(1, row, "计划日期"));
			sheet.addCell(new Label(2, row, "MPS单位"));
			sheet.addCell(new Label(3, row, "物料名"));
			sheet.addCell(new Label(4, row, "计划数量"));
			sheet.addCell(new Label(5, row, "预计库存"));
			sheet.addCell(new Label(6, row, "计划期类型"));
			sheet.addCell(new Label(7, row, "版本"));
			sheet.addCell(new Label(8, row, "制定人"));
			sheet.addCell(new Label(9, row, "合同号"));
	
			/**for (int i = 1; i <= cols; i++) {
				sheet.addCell(new Label(i - 1, row, metaData.getColumnName(i)));
			}
			
			*/
			row++;
		}
       boolean f=true;
		while (rs.next()) {
			for (int i = 1; i <= cols; i++) {
				f=true;
			
			    if(i==2){
			    	
			    	sheet.addCell(new Label(i - 1, row, (new SimpleDateFormat("yyyy-MM-dd"))
			    			.format(rs.getDate(i))));
			    	f=false;
			    }
             
				if(f){
				sheet.addCell(new Label(i - 1, row, rs.getString(i)));
				
				}
			}
			row++;
		}
		
	   
	if (rs != null)
		rs.close();
	wwb.write();
	wwb.close();
}

}
