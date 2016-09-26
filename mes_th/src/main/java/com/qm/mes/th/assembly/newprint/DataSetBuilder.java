package com.qm.mes.th.assembly.newprint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qm.mes.th.assembly.IReportDataSetBuilder;
import com.qm.mes.th.assembly.entities.ReportBaseInfo;
import com.qm.mes.th.assembly.entities.RequestParam;

import th.pz.bean.JConfigure;
import th.pz.bean.PrintSet;

/**
 * 数据集构建工具
 * 
 * @author Ajaxfan
 */
class DataSetBuilder implements IReportDataSetBuilder {
	private Connection conn;
	private PrintSet printSet;
	private ReportBaseInfo reportBaseInfo;
	private List<JConfigure> list;
	private String queryExpression;
	private RequestParam requestParam;

	/** 系统日志工具 */
	private Logger logger = Logger.getLogger(DataSetBuilder.class);

	/**
	 * 
	 * @param conn
	 * @param printSet
	 * @param reportBaseInfo
	 */
	public DataSetBuilder(Connection conn, PrintSet printSet, ReportBaseInfo reportBaseInfo,
	        RequestParam requestParam) {
		this.conn = conn;
		this.printSet = printSet;
		this.reportBaseInfo = reportBaseInfo;
		this.requestParam = requestParam;

		list = new ArrayList<JConfigure>();
	}

	/**
	 * 查询表达式
	 */
	public void buildQueryExpression() {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT top ");
		sb.append(printSet.getNTFASSCount());
		sb.append(" c.cSEQNo_A, c.cVinCode, c.cCarType, ISNULL(cQADNo, '') cQADNo, sc.ITFASSNameId, sc.iTFASSNum, c.cCarNo, ks.ccode");
		sb.append(" FROM carData c LEFT JOIN carData_D sc");
		sb.append(" ON c.ccarno = sc.icarid AND itfassnameid = ");
		sb.append(reportBaseInfo.getTfassId());
		sb.append(" LEFT JOIN TFASSName t ON sc.itfassnameid = t.id ");
		sb.append(" LEFT JOIN kinset ks ON substring(c.ccarno,6,1) = ks.csubkin");
		sb.append(" WHERE ((dabegin = '");
		sb.append(reportBaseInfo.getDabegin());
		sb.append("' AND c.cSEQNo_A>'");
		sb.append(reportBaseInfo.getDaseqa());
		sb.append("') or (dabegin> '");
		sb.append(reportBaseInfo.getDabegin());
		sb.append("'))");

		if (printSet.getCCarType() != null && !printSet.getCCarType().equals("")) {
			String carType = "'" + printSet.getCCarType() + "'";
			carType = carType.replace(",", "','");
			sb.append(" AND substring(c.ccarno,6,1) in (" + carType + ")");
		}

		if (printSet.getCFactory() != null && !printSet.getCFactory().equals("")) {
			String factoryNo = "'" + printSet.getCFactory() + "'";
			factoryNo = factoryNo.replace(",", "','");
			sb.append(" AND (subString(c.cSEQNo_A,1,2) in(" + factoryNo + ")) ");
		}
		if (printSet.getCVinRule() != null && !"".equals(printSet.getCVinRule().trim())) {
			String cvinRule = "'" + printSet.getCVinRule() + "'";
			cvinRule = cvinRule.replace(",", "','");
			sb.append(" and (subString(c.cVinCode,7,2) in(" + cvinRule + ")) ");
		}
		sb.append(" ORDER BY c.dabegin, c.cSEQNo_A");

		logger.debug(sb.toString());

		queryExpression = sb.toString();
	}

	/**
	 * 构建数据集合
	 */
	public void buildBusinessDataSet() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(queryExpression);
			rs = stmt.executeQuery();

			// 当前打印的页数
			int pageNumber = Integer.valueOf(requestParam.getJs());
			// 没打印一份数量都应该递减
			int count = printSet.getNPerTimeCount() - (--pageNumber) * printSet.getNTFASSCount();
			count = Math.min(count, printSet.getNTFASSCount());

			// 填充数据集合
			for (int i = 1; i <= count; i++) {
				JConfigure obj = new JConfigure(i);
				obj.setChassisNumber(reportBaseInfo.getChassisNumber());
				obj.setPrintSetId(printSet.getIPrintGroupId());
				obj.setJs(reportBaseInfo.getCarno());

				if (rs.next()) {
					obj.setCQADNo(rs.getString("cQADNo"));// 天合零件号
					obj.setCSEQNo_A(rs.getString("cSEQNo_A"));// 总装顺序号
					obj.setCVinCode(setLastVin(rs.getString("cVinCode")));// VIN码
					obj.setCCarNo(rs.getString("cCarNo"));// kin号
					obj.setCCarType(rs.getString("ccode"));// 车型
					obj.setTfassId(rs.getInt("ITFASSNameId"));
				}
				list.add(obj);
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
	 * 获得最近的Vin码
	 * 
	 * @param obj
	 * @return
	 */
	private String setLastVin(String curVin) {
		// 辆份不足的时候，VIN可能为空
		if (curVin == null || curVin.trim().length() == 0) {
			return null;
		}
		// 按VIN7,8位进行分类，存储对应的VIN信息
		reportBaseInfo.putVinMap2CarType(curVin.substring(6, 8), curVin);
		// 最后答应的VIN码
		printSet.setCLastVin(curVin);

		return curVin;
	}

	/*
	 * 数据集合
	 */
	public List<JConfigure> getDataSet() {
		return list;
	}
}
