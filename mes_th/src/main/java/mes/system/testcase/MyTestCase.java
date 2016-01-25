package mes.system.testcase;

import java.sql.Connection;

public class MyTestCase {

	protected Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = java.sql.DriverManager
					.getConnection(
							"jdbc:oracle:thin:@10.22.110.10:1521:soa",
							"mes", "mes");
			return con;
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args){
		System.out.println(new  MyTestCase().getConnection());
	}
}
