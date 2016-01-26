package com.qm.th.tg.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;

public class ErrExport {
	public static synchronized void createExcelFile(ResultSet rs,
			OutputStream os,String type,Connection con) throws Exception {
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		//创建Excel工作表
		WritableSheet sheet = null;
			sheet = wwb.createSheet("未匹配散件信息统计", 0);
		 ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount(), row = 0;
			if (true) {
		        cols=3;
				sheet.addCell(new Label(0, row, "未匹配散件"));
				sheet.addCell(new Label(1, row, "散件名称"));
				sheet.addCell(new Label(2, row, "数量"));
				sheet.setColumnView(0, 25); // 设置列的宽度
				sheet.setColumnView(1, 25); // 设置列的宽度
				sheet.setColumnView(2, 8); // 设置列的宽度
				row++;
			}
			while (rs.next()) {
				for (int i=0;i<cols;i++){
					switch(i){
					case 0://日期
						sheet.addCell(new Label(i, row,rs.getString("cpartno")));
		   				break;
					case 1://未匹配散件
						sheet.addCell(new Label(i,row,rs.getString("cpartname")));
						break;
					case 2://散件名称
						sheet.addCell(new Number(i,row,rs.getInt("nNum")));
						break;
					}
			  }
				row++;
			}
		
		if(con!=null){
			con=null;
		}
		wwb.write();
		wwb.close();
	}
}
