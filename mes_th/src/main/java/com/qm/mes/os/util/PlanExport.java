package com.qm.mes.os.util;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.qm.mes.ra.dao.DAO_ProduceUnit;
import com.qm.mes.ra.dao.DAO_ProduceUnitForOracle;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import java.sql.*;
public class PlanExport {
	public static synchronized void createExcelFile(ResultSet rs,
			OutputStream os,Connection con) throws Exception {
		//Method 2：将WritableWorkbook直接写入到输出流
	jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
	//创建Excel工作表
	WritableSheet sheet = wwb.createSheet("作业计划", 0);
	HashMap<Integer, String> m = new   HashMap<Integer, String>(); 
	DAO_ProduceUnit dao_produceunit =new DAO_ProduceUnitForOracle() ;
    String producesql=dao_produceunit.getAllProduceUnit();
   Statement stat1=con.createStatement();
    
  ResultSet Unit = stat1.executeQuery(producesql);
   //ProduceUnitFactory unit=new ProduceUnitFactory();
   // ResultSet Unit= unit.getAllProduceUnit(con);
    while(Unit.next()){
  
    m.put(Unit.getInt("int_id"),(String)Unit.getString("str_name"));
    }
	    ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount(), row = 0;
            cols=15;
		if (true) {
			
			//sheet.addCell(new Label(0, row, "编号"));
			
			sheet.addCell(new Label(10, row, "计划日期"));
			sheet.addCell(new Label(3, row, "生产日期"));
			sheet.addCell(new Label(12, row, "订单号"));
			sheet.addCell(new Label(9, row, "批次号"));
			sheet.addCell(new Label(5, row, "产品类别标识"));
			sheet.addCell(new Label(6, row, "产品名称"));
			sheet.addCell(new Label(7, row, "产品标识"));
			sheet.addCell(new Label(0, row, "生产单元名"));
			sheet.addCell(new Label(1, row, "班组"));
			sheet.addCell(new Label(2, row, "班次"));
			sheet.addCell(new Label(8, row, "数量"));
			sheet.addCell(new Label(13, row, "版本号"));
			sheet.addCell(new Label(14, row, "发布"));
			sheet.addCell(new Label(4, row, "计划顺序号"));
			sheet.addCell(new Label(11, row, "备注"));
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
				//计划日期
			     if(i==11){
			    	// sheet.addCell(new Label(i - 1, row, (new SimpleDateFormat("yyyy-MM-dd"))
				    //			.format(rs.getDate(i))));
			    	 DateTime dt = new DateTime(i-1,row,(new SimpleDateFormat("yyyy-MM-dd"))
				    	.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(rs.getDate(i).getTime()+1* 24 * 60 * 60 * 1000))));
			    	 sheet.addCell(dt);
			    	 f=false; 
			     }
			     //生产日期
			    if(i==4){
			    	
			    //	sheet.addCell(new Label(i - 1, row, (new SimpleDateFormat("yyyy-MM-dd"))
			    //			.format(rs.getDate(i))));
			    	DateTime dt = new DateTime(i-1,row,(new SimpleDateFormat("yyyy-MM-dd"))
					    	.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(rs.getDate(i).getTime()+1* 24 * 60 * 60 * 1000))));
				    sheet.addCell(dt);
			    	f=false;
			    }
			    //生产单元
                if(i==1){
                	int produceid=rs.getInt(i);
    				String name=m.get(produceid);
    				sheet.addCell(new Label(i - 1, row, name));
    				f=false;
                }
                //发布
                if(i==15){
                	int upload=rs.getInt(i);
                	sheet.addCell(new Label(i - 1, row, upload==1?"发布":"未发布"));
                	f=false;
                }
				if(f){
				sheet.addCell(new Label(i - 1, row, rs.getString(i)==null?"":rs.getString(i)));
				
				}
			}
			row++;
		}
		
	   
	if (rs != null)
		rs.close();
	if(stat1!=null){
		stat1.close();
	}
	if(con!=null){
		con=null;
	}
	wwb.write();
	wwb.close();
}


}
