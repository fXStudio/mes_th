package th.tg.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.Number;

/**
 * 焊装导出
 * 
 * @author YuanPeng
 *
 */
public class WeldingExport {
	public static synchronized void createExcelFile(ResultSet rs,ResultSet rs_part,
			OutputStream os,String partnum,String type,Connection con) throws Exception {//铺线字体（宋体 10号 蓝色）
		WritableFont blueFont = new jxl.write.WritableFont(
			      WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD);
		blueFont.setColour(Colour.BLUE);
		//标题样式
		jxl.write.WritableCellFormat blueFormat = new jxl.write.WritableCellFormat(
				blueFont);
		//铺线字体（宋体 10号 蓝色）
		WritableFont greenFont = new jxl.write.WritableFont(
			      WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD);
		blueFont.setColour(Colour.GREEN);
		//标题样式
		jxl.write.WritableCellFormat greenFormat = new jxl.write.WritableCellFormat(
				greenFont);
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		//创建Excel工作表
		WritableSheet sheet = wwb.createSheet("焊装查询导出", 0);
		 ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount(), row = 0;
			if (true) {
				if(type.equals("1")){
					int count = 2;
					sheet.addCell(new Label(0, row, "顺序号"));
					sheet.addCell(new Label(1, row, "KIN"));
					sheet.setColumnView(0, 8); // 设置列的宽度
					sheet.setColumnView(1, 14); // 设置列的宽度
					while(rs_part.next()){
						sheet.setColumnView(count, 20); // 设置列的宽度
						sheet.addCell(new Label(count++, row, rs_part.getString("name")));
						sheet.setColumnView(count, 13); // 设置列的宽度
						sheet.addCell(new Label(count++, row, rs_part.getString("name")+"数量"));
					}
					sheet.addCell(new Label(cols-2, row, "焊装上线时间"));
					sheet.setColumnView(cols-2, 22); // 设置列的宽度
				}else{
					sheet.addCell(new Label(0, row, "零件号"));
					sheet.addCell(new Label(1, row, "数量"));
					sheet.setColumnView(0, 18); // 设置列的宽度
					sheet.setColumnView(1, 8); // 设置列的宽度
				}
				row++;
			}
			rs_part.beforeFirst();
			if(type.equals("1")){
				while (rs.next()) {
					int count2 = 2;
					sheet.addCell(new Number(0, row,rs.getInt("seq")));
					sheet.addCell(new Number(1, row,rs.getInt("kin")));
					while(rs_part.next()){
						sheet.addCell(new Label(count2++, row,rs.getString(rs_part.getString("name"))));
						
						sheet.addCell(new Number(count2++, row,rs.getInt(rs_part.getString("name")+"数量")));
					}
					rs_part.beforeFirst();
					sheet.addCell(new Label(cols-2, row,rs.getString("dwbegin")));
					row++;
				}
				int c =1;
				rs_part.afterLast();
				cols = sheet.getColumns();//列数
				int rows = sheet.getRows();//行数
			for(c=1;c<=Integer.parseInt(partnum);c++){
				int col = c*2+1;
				String curname = "";
				int length = 0;
				int n= 0;
				String value = "";
				for(int i=1;i<rows;i++){
					 
					Cell cell = sheet.getCell(col-1,i);
					value = cell.getContents();
					//第一次操作
					if(curname == ""){
						curname = value;
					}
					//不相等的时候
					if(!curname.equals(value)){
						int length_total = length;
						n++;
						curname = value;
						//循环访问每一个已经存储的单元格对象
						while(length>0){
							//弹出对象，数组内容减少。
							if(length_total == length)
								for(int k=1;k<=length;k++)
									sheet.addCell(new Number(col,i-k,length));
							length--;
						}
					}
					//相等情况操作
					if(curname.equals(value)){
						length++;
						
					}
				}
				int length_total = length;
				n++;
				curname = value;
				//循环访问每一个已经存储的单元格对象
				while(length>0){
					//弹出对象，数组内容减少。
					if(length_total == length)
						for(int k=1;k<=length;k++)
							sheet.addCell(new Number(col,rows-k,length));
					length--;
				}
			}
				
				
			}else{
				while (rs.next()) {
					sheet.addCell(new Label(0, row,rs.getString("max_no")));
					sheet.addCell(new Number(1, row,rs.getInt("sum_num")));
					row++;
				}
			}
		if(con!=null){
			con=null;
		}
		wwb.write();
		wwb.close();
	}

}