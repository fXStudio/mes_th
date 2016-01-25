package mes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;

public class ResultSetToExcel {

	public static synchronized void createExcelFile(ResultSet rs,
			OutputStream os) throws Exception {
		//Method 2：将WritableWorkbook直接写入到输出流
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		//创建Excel工作表 该方法需要两个参数，一个是工作表的名称，另一个是工作表在工作薄中的位置
		WritableSheet sheet = wwb.createSheet("data1", 0);
		if (rs != null)
			writetosheet(sheet, rs);
		wwb.write();
		wwb.close();
	}

	private static void writetosheet(WritableSheet sheet, ResultSet rs)
			throws Exception {
		ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount(), row = 0;

		if (true) {
			for (int i = 1; i <= cols; i++) {
				sheet.addCell(new Label(i - 1, row, metaData.getColumnName(i)));
			}
			row++;
		}

		while (rs.next()) {
			for (int i = 1; i <= cols; i++) {
				sheet.addCell(new Label(i - 1, row, rs.getString(i)));
			}
			row++;
		}
		rs.close();
	}

	public static Connection getConnection() {
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			return java.sql.DriverManager
					.getConnection(
							"jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=pubs",
							"sa", "sa");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 在main中测试一下链接是否成功
	 * @param args
	 */
	public static void main(String[] args) {
		Connection con = ResultSetToExcel.getConnection();
		try {
			ResultSet rs = con.createStatement().executeQuery(
					"select * from employee");

			OutputStream os;
			os = new FileOutputStream(new File("tab.xls"), false);
			ResultSetToExcel.createExcelFile(rs, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
