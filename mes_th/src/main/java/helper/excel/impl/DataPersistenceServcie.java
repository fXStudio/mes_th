package helper.excel.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import common.Conn_MES;
import helper.excel.entities.FATHBean;
import helper.excel.inters.IDataPersistenceService;

/**
 * 
 * @author Ajaxfan
 */
public class DataPersistenceServcie implements IDataPersistenceService {
	private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	/**
	 * 
	 */
	@Override
	public int storeData(List<FATHBean> list) throws Exception {
		int count = 0;

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = new Conn_MES().getConn();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(toSql());

			// 数据变更方法
			for (FATHBean bean : list) {
				stmt.setString(1, bean.getChassi());
				stmt.setObject(2, toDateTime(bean.getCp5adate(), bean.getCp5atime()));
				stmt.setString(3, bean.getSeq());
				stmt.setString(4, bean.getKnr().replaceAll("-", ""));

				stmt.addBatch();
			}
			count = stmt.executeUpdate();
			conn.commit();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} finally {
					stmt = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} finally {
					conn = null;
				}
			}
		}
		return count;
	}

	/**
	 * @param date
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	private Timestamp toDateTime(Date date, Date time) throws ParseException {
		return new Timestamp(df.parse(
		        new SimpleDateFormat("yyyy/MM/dd").format(date) + " " + new SimpleDateFormat("HH:mm").format(time))
		        .getTime());
	}

	/**
	 * @return 变更语句
	 */
	private String toSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(" Update cardata ");
		sb.append(" set cvincode = ?, dabegin = ?, cseqno_a = ? ");
		sb.append(" where substring(ccarno, 3, 8) = ? ");

		return sb.toString();
	}
}
