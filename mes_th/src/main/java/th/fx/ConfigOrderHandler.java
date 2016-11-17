package th.fx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Conn_MES;
import th.fx.bean.COrderEntity;

/**
 * 配置单打印后台处理服务
 * 
 * @author Ajaxfan
 */
public class ConfigOrderHandler {
	/** 车号 */
	private static final String ch = " 1";

	/** 页面请求参数 */
	private String jspRq;

	/** 最小打印零件数量 */
	private int minPartCount = 9999;

	/**
	 * 构造函数
	 * 
	 * @param jspRq
	 */
	public ConfigOrderHandler(String jspRq) {
		this.jspRq = jspRq;
	}

	/**
	 * 运行服务，处理数据
	 */
	public synchronized List<COrderEntity> execute() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// 要打印的实体结果集合
		List<COrderEntity> list = new ArrayList<COrderEntity>();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT MAX(id) id, iPrintGroupId, cDescrip, nPerTimeCount,");
		sb.append(" nTFASSCount, cfactory, cCarType, ceiling(CAST(nPerTimeCount as float)/nTFASSCount) cPrintRadio,");
		sb.append(" cAuto, npage, cLastVin, cvinrule");
		sb.append(" FROM printset");
		sb.append(" GROUP BY iPrintGroupId, cDescrip,nPerTimeCount,");
		sb.append(" nTFASSCount, cfactory, cCarType, cPrintRadio, cAuto, npage, cLastVin, cvinrule");

		try {
			conn = new Conn_MES().getConn();
			stmt = conn.prepareStatement(sb.toString());

			rs = stmt.executeQuery();

			// 遍历数据集合
			while (rs.next()) {
				// 配置单项目
				COrderEntity entity = new COrderEntity();

				entity.setPrintSetId(rs.getString("id"));// 打印配置Id
				entity.setGroupId(rs.getString("iPrintGroupId"));// 组号
				entity.setDescript(rs.getString("cDescrip"));// 描述信息
				entity.setPerTimeCount(rs.getInt("nPerTimeCount"));// 每次打印数
				entity.setTFassCount(rs.getInt("nTFASSCount"));// 每份零件数
				entity.setFactoryNo(rs.getString("cfactory"));// 工厂
				entity.setCarType(rs.getString("cCarType"));// 车型
				entity.setPrintRadio(rs.getInt("cPrintRadio"));// 打印次数
				// 是否自动打印 1: 自动打印 0: 不自动打印
				entity.setAuto(rs.getString("cAuto"));
				entity.setPages(rs.getInt("npage"));// 打印份数
				entity.setLastVin(rs.getString("cLastVin"));// 最后的vin码
				entity.setCvinrule(rs.getString("cvinrule"));// vin规则

				// 处理零件信息
				handleCardata(conn, entity);

				// 填充配置单
				list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					conn = null;
				}
			}
		}
		return list;
	}

	/**
	 * 查询最后打印的Vin码对应的车辆信息（总装上线时间，总装顺序号)
	 * 
	 * @param conn
	 */
	private void handleCardata(Connection conn, COrderEntity entity) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
					"SELECT dabegin, cseqno_a FROM cardata WHERE cvincode = ? AND dabegin is not null");
			stmt.setString(1, entity.getLastVin());

			rs = stmt.executeQuery();

			if (rs.next()) {
				entity.setDabegin(rs.getString("dabegin"));// 总装上线时间
				entity.setSeq_a(rs.getString("cseqno_a"));// 总装顺序号
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		// 打印记录表
		handlePrintSet(conn, entity);
	}

	/**
	 * 从打印记录表中读取最后打印数据的基本配置信息
	 * 
	 * @throws Exception
	 */
	private void handlePrintSet(Connection conn, COrderEntity entity) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// hmvin最后vin号
		Map<String, String> hmVin = new HashMap<String, String>();

		try {
			stmt = conn.prepareStatement("SELECT ctype, clastvin FROM printSetVin WHERE printid = ?");
			stmt.setString(1, entity.getPrintSetId());

			rs = stmt.executeQuery();

			while (rs.next()) {
				String ctype = rs.getString("ctype");

				if (ctype != null && !ctype.equals("")) {
					hmVin.put(ctype, rs.getString("clastvin"));
				}
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		// 判断vin是否连续
		handleVinSeries(conn, entity, hmVin);
	}

	/**
	 * 检测Vin是否连续
	 * 
	 * @param conn
	 * @param entity
	 * @throws Exception
	 */
	private void handleVinSeries(Connection conn, COrderEntity entity, Map<String, String> hmVin) throws Exception {
		// Vin不连续检测
		int perTimeRow = entity.getPerTimeCount();

		// 设置参数
		entity.setPerTimeRow(perTimeRow);

		// 查询语句
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT").append(" c.cVinCode, c.dabegin");
		sql.append(" FROM carData c");
		sql.append(" WHERE ((dabegin = ? AND c.cSEQNo_A > ?)").append(" OR (dabegin > ?))");

		// 车型
		String carType = entity.getCarType();

		if (carType != null && !carType.equals("")) {
			carType = "'" + carType + "'";
			carType = carType.replace(",", "','");

			sql.append("AND SUBSTRING(c.ccarno, 6, 1) IN (" + carType + ")");
		}

		// 工厂编码
		String factoryNo = entity.getFactoryNo();

		if (factoryNo != null && !factoryNo.equals("")) {
			factoryNo = "'" + factoryNo + "'";
			factoryNo = factoryNo.replace(",", "','");

			sql.append(" AND (SUBSTRING(c.cSEQNo_A, 1, 2) IN(" + factoryNo + ")) ");
		}

		// VIN规则
		String cVinRule = entity.getCvinrule();

		if (cVinRule != null && !cVinRule.equals("")) {
			cVinRule = "'" + cVinRule + "'";
			cVinRule = cVinRule.replace(",", "','");

			sql.append(" AND (SUBSTRING(c.cVinCode,7,2) IN(" + cVinRule + ")) ");
		}
		// 用总装上线时间和总装顺序号排序
		sql.append(" order by c.dabegin, c.cSEQNo_A");

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, entity.getDabegin());// 总装上线时间
			stmt.setString(2, entity.getSeq_a());// 总装顺序号
			stmt.setString(3, entity.getDabegin());// 总装上线时间

			rs = stmt.executeQuery();

			for (int i = 0; i < perTimeRow && rs.next(); i++) {
				String vinCode = rs.getString("cVinCode");// Vin码
				String vinType = vinCode.substring(6, 8);// Vin车型
				String tempVin = hmVin.get(vinType);// 通过Vin车型标志，关联出最后打印的Vin码

				// 如果当前车型对应的Vin记录不存在，则将当前的Vin关联该车型
				if (tempVin != null && !"".equals(tempVin)) {
					int oldVinLst = Integer.valueOf(tempVin.substring(11)); // vin后六位
					int newVinLst = Integer.valueOf(vinCode.substring(11)); // cardata中vin后6位

					// 如果是连续的序列号，那么其做差的结果应该等于1
					if ((newVinLst - oldVinLst) != 1) {
						entity.setContinue(false);

						break;
					}
				}
				hmVin.put(vinType, vinCode);
			}
			rs.last();
			entity.setPartCount(rs.getRow());

			if (rs.getRow() > 0) {
				rs.absolute(rs.getRow() - 1);
				entity.setDabegin(timeDiff(rs.getTimestamp("dabegin")));
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		// 记录打印数据
		handlePritdata(conn, entity);
	}

	/**
	 * 查询打印的车辆最大Kin号
	 * 
	 * @param conn
	 * @param entity
	 * @throws Exception
	 */
	private void handlePritdata(Connection conn, COrderEntity entity) throws Exception {
		int partCount = entity.getPartCount();// 当前要打印的林间数量

		// 设置打印数量
		entity.setMinPartCount(minPartCount > partCount ? partCount : minPartCount);

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
					"SELECT ISNULL(MAX(iCarNo), 0) FROM print_Data WHERE cremark = ? AND iPrintGroupId = ?");
			stmt.setString(1, jspRq);
			stmt.setString(2, entity.getPrintSetId());

			rs = stmt.executeQuery();

			if (rs.next()) {
				entity.setMaxCarno(rs.getString(1));
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
		}
		// 组织自动打印的脚本表达式
		orgnizeOpenappExpression(entity);
	}

	/**
	 * 组织自动打印的脚本表达式
	 * 
	 * @param entity
	 */
	private void orgnizeOpenappExpression(COrderEntity entity) {
		// 当前设置的每项打印次数
		int perTimeRow = entity.getPerTimeRow();
		// 最小打印数量
		int minPartCount = entity.getMinPartCount();

		// 判断自动打印标志，只有设置为自动打印的时候，这里才会生成这个串
		if (entity.getAuto().equals("1") && minPartCount >= perTimeRow) {
			StringBuilder openApp = new StringBuilder();
			openApp.append("openApp(").append(entity.getGroupId()).append(",");
			openApp.append(entity.getPrintRadio()).append(",").append(ch).append(",");
			openApp.append(entity.getPages()).append(",").append(minPartCount).append(",");
			openApp.append(perTimeRow).append(",").append(entity.isContinue() ? "1" : "0").append(");");

			// 设置自动打印的调取串
			entity.setOpenApp(openApp.toString());
		}
	}

	private String timeDiff(Timestamp lasttime) {
		Calendar cal = java.util.GregorianCalendar.getInstance();
		long diff = cal.getTimeInMillis() - lasttime.getTime();

		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = diff / dd;
		Long hour = (diff - day * dd) / hh;
		Long minute = (diff - day * dd - hour * hh) / mi;
		Long second = (diff - day * dd - hour * hh - minute * mi) / ss;

		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day + "天");
		}
		if (hour > 0) {
			sb.append(hour + "小时");
		}
		if (minute > 0) {
			sb.append(minute + "分");
		}
		if (second > 0) {
			sb.append(second + "秒");
		}
		return sb.toString();
	}
}
