package th.bs.bom;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qm.mes.th.helper.Conn_MES;

/**
 * Excel读取，测试
 * 
 * @author AjaxFan
 * @date 2009-9-23
 */
public class ReadExcel {
	public static void readXml(File file) throws Exception {
		ThWorkbook dw = new ThWorkbook(file);
		ThSheet ds = dw.getSheet(0);

		// 从源表中取得指定列的数据集合
		// 天合零件号
		List<String> ctfass = ds.getColumnContents("cTFASS");
		// 天合零件数量
		List<String> ntfassnum = ds.getColumnContents("nTFASSNum");
		// 大众主零件号
		List<String> cvwmainpart = ds.getColumnContents("cVWMainPart");
		// 大众主零件数量
		List<String> cvwmainpartquan = ds.getColumnContents("cVWMainPartQuan");
		// 大众主零件种类数
		List<String> cvwmainparttype = ds.getColumnContents("cVWMainPartType");
		// 大众子零件号
		List<String> cvwsubpart = ds.getColumnContents("cVWSubPart");
		// 大众子零件数量
		List<String> nvwsubpartquan = ds.getColumnContents("nVWSubPartQuan");
		// 大众子零件种类数
		List<String> nvwsubparttype = ds.getColumnContents("nVWSubPartType");
		// 天合零件名称
		List<String> ctfassname = ds.getColumnContents("cTFASSName");
		// 天合零件类型数量
		List<String> ctfasstypeno = ds.getColumnContents("cTFASSTypeNo");
		// 方案号
		List<String> cqadno = ds.getColumnContents("cQADNo");
		// 是否过度车型
		List<String> cistempcar = ds.getColumnContents("cIsTempCar");
		// 备注
		List<String> cremark = ds.getColumnContents("cRemark");
		// 新车标志
		List<String> newCars = ds.getColumnContents("cIsNewCar");

		// 用bean组成一个虚拟表, bean的属性名代表列名
		List<ThBean> list = new ArrayList<ThBean>();
		int i = 1;
		try {
			for (; i < ds.getRows(); i++) {
				ThBean dbe = new ThBean();
				dbe.setCtfass(i < ctfass.size() ? ctfass.get(i) : null);
				dbe.setNTFASSNum(i < ntfassnum.size() ? ntfassnum.get(i) : null);
				dbe.setCVWMainPart(i < cvwmainpart.size() ? cvwmainpart.get(i) : null);
				dbe.setCVWMainPartQuan(i < cvwmainpartquan.size() ? cvwmainpartquan.get(i) : null);
				dbe.setCVWMainPartType(i < cvwmainparttype.size() ? cvwmainparttype.get(i) : null);
				dbe.setCVWSubPart(i < cvwsubpart.size() ? cvwsubpart.get(i) : null);
				dbe.setNVWSubPartQuan(i < nvwsubpartquan.size() ? nvwsubpartquan.get(i) : null);
				dbe.setNVWSubPartType(i < nvwsubparttype.size() ? nvwsubparttype.get(i) : null);
				dbe.setCTFASSName(i < ctfassname.size() ? ctfassname.get(i) : null);
				dbe.setCTFASSTypeNo(i < ctfasstypeno.size() ? ctfasstypeno.get(i) : null);
				dbe.setCQADNo(i < cqadno.size() ? cqadno.get(i) : null);
				dbe.setCIsTempCar(i < cistempcar.size() ? cistempcar.get(i) : null);
				dbe.setCRemark(i < cremark.size() ? cremark.get(i) : null);

				list.add(dbe);
			}
		} catch (Exception e) {
			throw new Exception("工作表末尾存在多余行");
		}
		saveData(list, newCars);
	}

	private static void saveData(List<ThBean> list, List<String> newCars) throws Exception {
		Connection conn = null;

		try {
			conn = new Conn_MES().getConn();
			conn.setAutoCommit(false);
			// 保存数据
			databaseHandle(list, conn);
			saveNewCars(list, newCars, conn);

			conn.commit();
		} finally {// resource cleanup
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					conn = null;
				}
			}
		}

	}

	/**
	 * 数据库操作
	 */
	private static void databaseHandle(List<ThBean> list, Connection conn) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String strSql = "INSERT INTO PARTDATA (cTFASS, nTFASSNum, cVWMainPart, cVWMainPartQuan, cVWMainPartType, cVWSubPart, nVWSubPartQuan, nVWSubPartType, cTFASSName, cTFASSTypeNo, cQADNo, cIsTempCar, cRemark) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(strSql);

			int i = 2;
			for (ThBean dbe : list) {
				if (!checkMaterial(conn, dbe, i++)) {
					return;
				}
				stmt.setString(1, dbe.getCtfass());
				stmt.setString(2, dbe.getNTFASSNum());
				stmt.setString(3, dbe.getCVWMainPart());
				stmt.setString(4, dbe.getCVWMainPartQuan());
				stmt.setString(5, dbe.getCVWMainPartType());
				stmt.setString(6, dbe.getCVWSubPart());
				stmt.setString(7, dbe.getNVWSubPartQuan());
				stmt.setString(8, dbe.getNVWSubPartType());
				stmt.setString(9, dbe.getCTFASSName());
				stmt.setString(10, dbe.getCTFASSTypeNo());
				stmt.setString(11, dbe.getCQADNo());
				stmt.setString(12, dbe.getCIsTempCar());
				stmt.setString(13, dbe.getCRemark());

				stmt.addBatch();
			}
			stmt.executeBatch();
		} finally {// resource cleanup
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
	}

	/**
	 * 新车数据
	 * 
	 * @param newCars
	 * @throws Exception
	 */
	private static void saveNewCars(List<ThBean> list, List<String> newCars, Connection conn) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("insert fit_newcar ( CQadno) values (?)");

			String qadno = null;
			for (int i = 1; i < newCars.size(); i++) {
				if ("y".equals(newCars.get(i).toLowerCase())) {
					String cqadno = list.get(i - 1).getCQADNo();

					if (!cqadno.equals(qadno)) {
						stmt.setString(1, qadno = cqadno);
						stmt.addBatch();
					}
				}
			}
			stmt.executeBatch();
		} finally {// resource cleanup
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
	}

	private static boolean checkMaterial(Connection conn, ThBean dbe, int index) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			if (checkMaiPart(conn, dbe, index)) {
				if ("0".equals(dbe.getNVWSubPartQuan())
						&& (dbe.getCVWSubPart() == null || "".equals(dbe.getCVWSubPart().trim()))) {
					return true;
				}
				stmt = conn.prepareStatement("select id from partlist where cPartNo = ? and cparttype='00002'");
				stmt.setString(1, dbe.getCVWSubPart());

				rs = stmt.executeQuery();

				if (rs.next()) {
					return true;
				}
				throw new Exception("第" + index + "行子物料信息录入错误");
			}
			return false;
		} finally {// resource cleanup
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
	}

	private static boolean checkMaiPart(Connection conn, ThBean dbe, int index) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("select id from partlist where cPartNo = ? and cparttype='00001'");
			stmt.setString(1, dbe.getCVWMainPart());

			rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			}
			throw new Exception("第" + index + "行主物料信息录入错误");
		} finally {// resource cleanup
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
	}
}
