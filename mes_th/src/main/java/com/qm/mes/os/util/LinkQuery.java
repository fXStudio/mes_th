package com.qm.mes.os.util;

import java.util.*;
import java.sql.*;

/*
 * 谢静天 2009-5-5
 * 
 * */
public class LinkQuery {

	/**
	 * sql语句生成函数
	 * 
	 * @param info
	 *            过滤条件，多值情况以<font color="red"><B>","</B></font>分割，<font color="red">多个过滤值之间应用<B>“与”</B>运算</font>
	 * @param colist
	 *            参与过滤的字段名
	 * @param sql
	 *            原sql语句
	 * @param con
	 * @return 返回应用了模糊过滤条件的sql语句
	 * @throws SQLException
	 */
	public StringBuffer linkquery(String info, ArrayList<String> colist,
			String sql, Connection con) throws SQLException {
		//查询源sql结果集结构使用的对象
		Statement stmt = null;
		//组合各个值得过滤条件，并返回使用的对象
		StringBuffer where = new StringBuffer("1=1");
		try {
			stmt = con.createStatement();
			// 值数组
			String[] vals = info.split(",");
			
			for(int i=0;i<vals.length;i++)
				vals[i] = vals[i].trim();
			
			// 得到原型类型数据
			ResultSet rs = stmt.executeQuery("select * from (" + sql + ") a where 1=2");
			ResultSetMetaData meta = rs.getMetaData();
			String colname = null;
			// 遍历每一个过滤值，多个过滤值之间应用“与”运算
			for (String v : vals) {
				// 由于wh内应用or运算符，所以首条件应用永假值
				String wh = "( 1=2 ";
				for (int c = 1; c <= meta.getColumnCount(); c++) {
					// 获得字段名
					colname = meta.getColumnName(c);
					// 若字段名不参与过滤运算，则执行下一次循环
					if (colist.contains(colname.toLowerCase()) == false)
						continue;
					// 组合过滤条件，wh值例如：(1=2 or username like '%zz%' or password
					// like '%zz%'
					wh = wh + " or " + colname + " like '%" + v + "%' ";
				}
				// 补充闭括号，wh值例如：(1=2 or username like '%zz%' or password like
				// '%zz%' )
				wh = wh + ")";
				// 将组合好的单值过滤条件加入到整个过滤语句中
				// where值例如：1=1 and ( 1=2 or INT_ID like '%上线%')and( 1=2 or
				// INT_ID like '%17%')
				where.append(" and " + wh);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return where;
	}
}
