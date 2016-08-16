package com.qm.mes.th.assembly.newprint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import com.qm.mes.th.assembly.IReportBaseInfoBuilder;
import com.qm.mes.th.assembly.entities.ReportBaseInfo;
import com.qm.mes.th.assembly.entities.RequestParam;

import th.pz.bean.PrintSet;

/**
 * 报表基本信息
 * 
 * @author Ajaxfan
 */
class BaseInfoBuilder implements IReportBaseInfoBuilder {
	private Connection conn;
	private PrintSet printSet;
	private RequestParam requestParam;
	private ReportBaseInfo reportBaseInfo;

	/**
	 * 构造函数
	 * 
	 * @param conn
	 * @param printSet
	 * @param requestParam
	 */
	public BaseInfoBuilder(Connection conn, PrintSet printSet, RequestParam requestParam) {
		this.conn = conn;
		this.printSet = printSet;
		this.requestParam = requestParam;

		this.reportBaseInfo = new ReportBaseInfo();
	}

	/**
	 * 构建总装信息
	 */
	public void buildDAInfo() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
			        "SELECT dabegin, cseqno_a FROM cardata WHERE cvincode = ? AND dabegin is not null");
			stmt.setString(1, printSet.getCLastVin());

			rs = stmt.executeQuery();
			if (rs.next()) {
				reportBaseInfo.setDabegin(rs.getString("dabegin"));// 总装上线时间
				reportBaseInfo.setDaseqa(rs.getString("cseqno_a"));// 总装顺序号
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
		}
	}

	/**
	 * 构建架号信息
	 */
	public void buildMaxCarNo() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT max(iCarNo) FROM print_data WHERE iPrintGroupId= ? AND cremark = ?");
			stmt.setInt(1, printSet.getId());
			stmt.setString(2, requestParam.getRequestDate());

			rs = stmt.executeQuery();
			if (rs.next()) {
				reportBaseInfo.setCarno(rs.getInt(1) + 1);
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
		}
	}

	/**
	 * 构建零件ID
	 */
	public void buildTFassId() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT id FROM tfassname WHERE ctfassname = ?");
			stmt.setString(1, printSet.getCTFASSName());

			rs = stmt.executeQuery();
			if (rs.next()) {
				reportBaseInfo.setTfassId(rs.getString("id"));
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
		}
	}

	/**
	 * 构建单号
	 */
	public void buildMaxPageNo() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT MAX(cpageNo) FROM print_data WHERE iPrintGroupId = ? AND cremark = ?");
			stmt.setInt(1, printSet.getId());
			stmt.setString(2, requestParam.getRequestDate());

			rs = stmt.executeQuery();
			if (rs.next()) {
				String pageNo = rs.getString(1);

				if (pageNo != null && pageNo.trim().length() <= 12) {
					reportBaseInfo.setPageNo(Integer.valueOf(pageNo.substring(8)));
				}
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
		}
	}

	/**
	 * 构建底盘号
	 */
	public void buildChassisNumber() {
		StringBuilder chassisNumber = new StringBuilder();
		chassisNumber.append(requestParam.getRequestDate().replaceAll("-", "").substring(2));
		chassisNumber.append(new DecimalFormat("00").format(printSet.getId()));
		chassisNumber.append(new DecimalFormat("0000").format(reportBaseInfo.getPageNo() + 1));

		reportBaseInfo.setChassisNumber(chassisNumber.toString());
	}

	/**
	 * 构建Vin和车型的对应关系
	 */
	public void buildVinAndCarTypePair() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT ctype, clastvin FROM printSetVin WHERE printid = ?");
			stmt.setInt(1, printSet.getId());

			rs = stmt.executeQuery();
			while (rs.next()) {
				reportBaseInfo.putVinMap2CarType(rs.getString("ctype"), rs.getString("clastvin"));
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
		}
	}

	/**
	 */
	public ReportBaseInfo getReportBaseInfo() {
		return reportBaseInfo;
	}
}
