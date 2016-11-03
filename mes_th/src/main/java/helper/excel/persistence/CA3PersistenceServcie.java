package helper.excel.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import common.Conn_MES;
import helper.excel.entities.FATHBean;
import helper.excel.inters.IDataPersistenceService;

/**
 * CA3处理
 * 
 * @author Ajaxfan
 */
public final class CA3PersistenceServcie extends AbstractPersistence implements IDataPersistenceService {
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
				stmt.setString(3, getSeq(bean.getSeq()));
				stmt.setString(4, bean.getKnr().replaceAll("-", ""));

				stmt.addBatch();
			}
			count = stmt.executeBatch().length;
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
	 * @return 变更语句
	 */
	protected String toSql() {
		return " EXEC CA3_DATAHANDLE  ?, ?, ?, ? ";
	}

	@Override
	protected String getPrefix() {
		return "07";
	}
}
