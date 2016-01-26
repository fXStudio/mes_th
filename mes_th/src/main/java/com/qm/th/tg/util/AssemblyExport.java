package com.qm.th.tg.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.Number;

/**
 * 总装导出
 * 
 * @author YuanPeng
 *
 */
public class AssemblyExport {
	public static synchronized void createExcelFile(ResultSet rs,
			OutputStream os,String type,Connection con) throws Exception {
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		//创建Excel工作表
		WritableSheet sheet = null;
		if(type.equals("1")){
			sheet = wwb.createSheet("总装查询导出", 0);
		}else{
			sheet = wwb.createSheet("总装统计导出", 0);
		}
		 ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount(), row = 0;
			if (true) {
				if(type.equals("1")){
		            cols=6;
					sheet.addCell(new Label(0, row, "顺序号"));
					sheet.addCell(new Label(1, row, "VIN"));
					sheet.addCell(new Label(2, row, "订单号"));
					sheet.addCell(new Label(3, row, "焊装上线时间"));
					sheet.addCell(new Label(4, row, "总装上线时间"));
					sheet.addCell(new Label(5, row, "CP6上线时间"));
					sheet.setColumnView(0, 8); // 设置列的宽度
					sheet.setColumnView(1, 20); // 设置列的宽度
					sheet.setColumnView(2, 13); // 设置列的宽度
					sheet.setColumnView(3, 23); // 设置列的宽度
					sheet.setColumnView(4, 23); // 设置列的宽度
					sheet.setColumnView(5, 23); // 设置列的宽度
				}else{
		            cols=2;
					sheet.addCell(new Label(0, row, "零件号"));
					sheet.addCell(new Label(1, row, "数量"));
					sheet.setColumnView(0, 20); // 设置列的宽度
					sheet.setColumnView(1, 8); // 设置列的宽度
				}
					row++;
			}
			while (rs.next()) {
				for (int i=0;i<cols;i++){
					if(type.equals("1")){
						switch(i){
						case 0://顺序号
		    				sheet.addCell(new Number(i, row,rs.getInt("cSEQNo_A")));
		    				break;
						case 1://VIN
							sheet.addCell(new Label(i,row,rs.getString("cVinCode")));
							break;
						case 2://订单号
							sheet.addCell(new Number(i,row,rs.getInt("cCarNo")));
							break;
						case 3://总装上线时间
							sheet.addCell(new Label(i,row,rs.getString("dWBegin")));
							break;
						case 4://焊装上线时间
							sheet.addCell(new Label(i,row,rs.getString("dABegin")));
							break;
						case 5://CP6上线时间
							sheet.addCell(new Label(i,row,rs.getString("dCp6Begin")));
							break;
						}
					}else{
						switch(i){
						case 0://顺序号
							sheet.addCell(new Label(i, row,rs.getString("seq")));
		    				break;
						case 1://数量
							sheet.addCell(new Number(i,row,rs.getInt("num")));
							break;
						}
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
