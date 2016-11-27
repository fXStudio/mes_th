package helper.excel.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import common.Conn_MES;
import helper.excel.entities.SpecialKinBean;
import helper.excel.inters.IDataPersistenceService;

/**
 * 特殊Kin号批量导入
 * @author Ajaxfan
 *
 */
public class SpecialKinPersistence implements IDataPersistenceService<SpecialKinBean> {

	/**
	 * 导入数据
	 */
	@Override
	public int storeData(List<SpecialKinBean> list) throws Exception {
		int count = 0;

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = new Conn_MES().getConn();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("DELETE specialKin WHERE ccarno = ?;INSERT INTO specialKin (ccarno, cenabled, cremark) VALUES (?,?,?)");

			// 数据变更方法
			for (SpecialKinBean bean : list) {
				stmt.setString(1, bean.getKincode());
				stmt.setString(2, bean.getKincode());
				stmt.setString(3, bean.getEnabled());
				stmt.setString(4, bean.getRemark());

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
}
