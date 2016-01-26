package com.qm.th.servlet;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.th.beans.JConfigure;
import com.qm.th.helpers.Conn_MES;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author Administrator
 */
public class HistoryPrint extends HttpServlet {
	/** */
	private static final long serialVersionUID = 1L;
	/** */
	private SimpleDateFormat date_fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 数据处理
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("application/octet-stream");

		String rq = request.getParameter("rq");// 日期
		String ch = request.getParameter("ch");// 车号
		String js = request.getParameter("js");// 架号
		String groupId = request.getParameter("groupid");// 组合ID
		String insert_sql = "";
		String update_sql = ""; // 更新printSetvin
		String dh = ""; // 单号
		String dysj = ""; // 打印时间
		String vincode = ""; // vin码
		String sxh = ""; // 顺序号
		String kinh = ""; // kin号
		String tFass = ""; // 天合零件号
		String tFassName = ""; // 天合总成名称
		String carType = ""; // 车型代码
		String seq_A = ""; // 总装顺序号
		String factoryNo = ""; // 工厂号；
		String printMd = ""; // 打印模版
		String sqlWhere = ""; // where
		String pageNo = "0"; // print_data.cpageNo单号
		String printSetCode = "";// printSet.cCode
		String printTitle = ""; // 配置单头
		String abegin = "";
		String lastVin = "";
		String cVinRule = "";
		String vinType = "";
		String tempVin = "";
		String tempVinLst = ""; //

		int index = 0;
		int tFassCount = 0; // 每页打印数量
		int oldVinLst = 0;
		int newVinLst = 0;
		int lenTempVin = 0;
		int cjs = 0; // 车架数
		int zcid = 0; // 天合总成名称id
		int printDsCount = 0; // 相同groupid的记录数

		List<List<JConfigure>> superlist = new ArrayList<List<JConfigure>>();
		Map<String, Object> parameters = new HashMap<String, Object>(); // ireport
		// 传参数用
		String printTm[];

		// 重定义架号前日期
		ch = rq.substring(9);

		Connection con = null;

		PreparedStatement printSet_stmt = null;
		Statement stmt = null;
		Statement insert_stmt = null;
		PreparedStatement insert_c = null;
		PreparedStatement v_stmt = null;
		Statement ps_stmt = null;
		ResultSet rs = null;
		ResultSet rs_c = null;
		ResultSet v_rs = null;
		ResultSet printSet_rs = null;

		try {
			con = new Conn_MES().getConn();
			// 开启事务控制
			con.setAutoCommit(false);

			try {
				// 定义数组
				printSet_stmt = con.prepareStatement("SELECT COUNT(*) FROM printSet WHERE iPrintGroupId = ?");
				printSet_stmt.setString(1, groupId);
				printSet_rs = printSet_stmt.executeQuery();

				if (printSet_rs.next()) {
					printDsCount = printSet_rs.getInt(1);
				}
			} finally {
				if (printSet_rs != null) {
					try {
						printSet_rs.close();
					} finally {
						printSet_rs = null;
					}
				}
				if (printSet_stmt != null) {
					try {
						printSet_stmt.close();
					} finally {
						printSet_stmt = null;
					}
				}
			}
			// 创建缓存数组
			String[] printId = new String[printDsCount];
			printTm = new String[printDsCount];

			printSet_stmt = con.prepareStatement("SELECT id, cCode FROM printSet WHERE iPrintGroupId = ? ORDER BY id");
			printSet_stmt.setString(1, groupId);
			printSet_rs = printSet_stmt.executeQuery();

			int iiPrintId = 0;

			while (printSet_rs.next()) {
				// 重复循环 资源浪费 用于判断 方向盘，气囊在同一张单子上的打印
				printId[iiPrintId] = printSet_rs.getString("id");
				printSetCode = printSet_rs.getString("cCode");
				superlist.add(new ArrayList<JConfigure>());

				// 过滤打印设置完的数据
				v_stmt = con.prepareStatement("SELECT * FROM printSet WHERE id = ?");
				v_stmt.setString(1, printId[iiPrintId]);
				v_rs = v_stmt.executeQuery();

				if (v_rs.next()) {
					tFassName = v_rs.getString("ctfassname");
					carType = v_rs.getString("cCarType");
					tFassCount = v_rs.getInt("ntfassCount");
					factoryNo = v_rs.getString("cFactory").trim();
					printMd = v_rs.getString("cPrintMd").trim();
					printTitle = v_rs.getString("ccartypedesc");
					cVinRule = v_rs.getString("cvinrule");

					// 取架数
					cjs = Integer.valueOf(js);
					// 取天合总称id
					try {
						insert_c = con.prepareStatement("SELECT id FROM tfassname WHERE ctfassname = ?");
						insert_c.setString(1, tFassName);
						rs_c = insert_c.executeQuery();

						if (rs_c.next()) {
							zcid = rs_c.getInt(1);
						}
					} finally {
						if (rs_c != null) {
							try {
								rs_c.close();
							} finally {
								rs_c = null;
							}
						}
						if (insert_c != null) {
							try {
								insert_c.close();
							} finally {
								insert_c = null;
							}
						}
					}
					// 取最大单号 print_data.cpageno
					try {
						insert_c = con
								.prepareStatement("SELECT * FROM print_data WHERE iCarNo = ? AND cRemark = ? AND iPrintGroupId = ? ORDER BY inum");
						insert_c.setInt(1, cjs);
						insert_c.setString(2, rq);
						insert_c.setString(3, printId[iiPrintId]);
						rs_c = insert_c.executeQuery();

						if (rs_c.next()) {
							pageNo = rs_c.getString("cpageno");
							seq_A = rs_c.getString("cseqno");
							lastVin = rs_c.getString("cvincode");
						}
					} finally {
						if (rs_c != null) {
							try {
								rs_c.close();
							} finally {
								rs_c = null;
							}
						}
						if (insert_c != null) {
							try {
								insert_c.close();
							} finally {
								insert_c = null;
							}
						}
					}
					dh = pageNo;
					printTm[iiPrintId] = dh;

					// 时间 dabegin
					try {
						insert_c = con.prepareStatement("SELECT dabegin FROM cardata WHERE cvinCode = ?");
						insert_c.setString(1, lastVin);
						rs_c = insert_c.executeQuery();

						if (rs_c.next()) {
							abegin = rs_c.getString("dabegin");
						}
					} finally {
						if (rs_c != null) {
							try {
								rs_c.close();
							} finally {
								rs_c = null;
							}
						}
						if (insert_c != null) {
							try {
								insert_c.close();
							} finally {
								insert_c = null;
							}
						}
					}
					// end 时间dabegin
					HashMap<String, String> hmVin = new HashMap<String, String>();

					sqlWhere = " select top "
							+ tFassCount
							+ " c.cSEQNo_A,c.cVinCode,c.cCarType,cQADNo,sc.ITFASSNameId,sc.iTFASSNum,c.cCarNo,ks.ccode from carData c left outer join carData_D sc"
							+ " on c.ccarno=sc.icarid and itfassnameid="
							+ zcid
							+ " left join TFASSName t on sc.itfassnameid=t.id left join kinset ks on substring(c.ccarno,6,1)=ks.csubkin "
							+ " where ((dabegin = '" + abegin + "' and c.cSEQNo_A>='" + seq_A + "')"
							+ " or (dabegin> '" + abegin + "'))";

					if (carType != null && !carType.equals("")) {
						carType = "'" + carType + "'";
						carType = carType.replace(",", "','");
						sqlWhere = sqlWhere + "and substring(c.ccarno,6,1) in (" + carType + ")";
					}

					if (factoryNo != null && !factoryNo.equals("")) {
						factoryNo = "'" + factoryNo + "'";
						factoryNo = factoryNo.replace(",", "','");
						sqlWhere = sqlWhere + " and (subString(c.cSEQNo_A,1,2) in(" + factoryNo + ")) ";
					}

					if (cVinRule != null && !cVinRule.equals("")) {
						cVinRule = "'" + cVinRule + "'";
						cVinRule = cVinRule.replace(",", "','");
						sqlWhere = sqlWhere + " and (subString(c.cVinCode,7,2) in(" + cVinRule + ")) ";
					}
					sqlWhere = sqlWhere + " order by c.cSEQNo_A, c.dabegin";

					try {
						insert_stmt = con.createStatement();
						stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

						rs = stmt.executeQuery(sqlWhere);
						index = 0;
						int signDelete = 0; // 删除前标志
						while (rs.next()) {
							if (signDelete == 0) {
								insert_sql = "delete  print_data where  iCarNo='" + cjs + "' and cRemark='" + rq
										+ "' and iPrintGroupId=" + printId[iiPrintId];
								insert_stmt.executeUpdate(insert_sql);
								signDelete = 1;
							}

							vincode = rs.getString("cVinCode");// vin
							sxh = rs.getString("cSEQNo_A");// 顺序号
							zcid = rs.getInt("ITFASSNameId");// 总成id
							dysj = date_fmt.format(GregorianCalendar.getInstance().getTime()); // 打印时间
							kinh = rs.getString("cCarNo"); // kin号
							tFass = rs.getString("cQADNo"); // 天合零件号
							if (tFass == null || tFass.equals("")) {
								tFass = "";
							}
							vinType = vincode.substring(6, 8);
							tempVin = hmVin.get(vinType);
							if (tempVin == null || tempVin.trim().equals("")) {
								// 应提示用户处理表printsetvin;
								hmVin.put(vinType, vincode);

								/** ********打印设置*************** */
								JConfigure jc = new JConfigure();
								jc.setCQADNo(rs.getString("cQADNo"));
								jc.setCSEQNo_A(rs.getString("cSEQNo_A"));
								jc.setCVinCode(rs.getString("cVinCode"));
								jc.setCCarNo(rs.getString("cCarNo"));
								if (printSetCode.equals("1") && tFassName.trim().equals("方向盘")) {
									jc.setCCarType("FXP");
								} else if (printSetCode.equals("1") && tFassName.trim().equals("安全气囊")) {
									jc.setCCarType("AQQN");
								} else {
									jc.setCCarType(rs.getString("ccode").trim());
								}
								// 从0开始， 对应数据库iNum
								index++;
								jc.setIndex(index);
								superlist.get(iiPrintId).add(jc);

								// 打印表插入
								insert_sql = " insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) "
										+ " values('"
										+ printId[iiPrintId]
										+ "','"
										+ dh
										+ "','"
										+ dysj
										+ "',"
										+ cjs
										+ ","
										+ index
										+ ",'"
										+ vincode
										+ "','"
										+ sxh
										+ "','"
										+ tFass
										+ "','"
										+ zcid
										+ "','"
										+ ch
										+ "','" + rq + "','" + kinh + "')";
								ps_stmt = con.createStatement();
								try {
									ps_stmt.executeUpdate(insert_sql);
								} catch (Exception eUpdatePrintSet) {
									System.out.println("print_data插入" + eUpdatePrintSet.toString());
								} finally {
									if (ps_stmt != null)
										ps_stmt.close();
								}
								/** ********打印设置结束 ********* */

							} else {
								oldVinLst = Integer.valueOf(tempVin.substring(11)); // vin后六位
								newVinLst = Integer.valueOf(vincode.substring(11)); // cardata中vin后6位

								// 初始值为0
								if (oldVinLst == 0) {
									oldVinLst = newVinLst;
								} else {
									oldVinLst = oldVinLst + 1;
								}

								// 如果不连续
								if (newVinLst != oldVinLst) {
									// vin始终不连续，或 小于一页能打印的最大数量时
									while (newVinLst > oldVinLst && index < tFassCount) {
										/** ********打印设置*************** */
										JConfigure jc = new JConfigure();
										jc.setCQADNo("");
										jc.setCSEQNo_A("");
										jc.setCVinCode("");
										jc.setCCarNo("");
										jc.setCCarType("");
										// 从0开始， 对应数据库iNum
										index++;
										jc.setIndex(index);
										superlist.get(iiPrintId).add(jc);

										// 打印表插入
										insert_sql = " insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) "
												+ " values('"
												+ printId[iiPrintId]
												+ "','"
												+ dh
												+ "','"
												+ dysj
												+ "',"
												+ cjs + "," + index + ",' ',' ',' ',' ','" + ch + "','" + rq + "',' ')";
										ps_stmt = con.createStatement();
										try {
											ps_stmt.executeUpdate(insert_sql);
										} catch (Exception eUpdatePrintSet) {
											System.out.println("print_data插入" + eUpdatePrintSet.toString());
										} finally {
											if (ps_stmt != null)
												ps_stmt.close();
										}
										/** ********打印设置结束 ********* */
										/** ********printsetvin**************** */
										tempVinLst = String.valueOf(oldVinLst);
										lenTempVin = tempVinLst.length();
										for (int kk = 0; kk < (6 - lenTempVin); kk++) {
											tempVinLst = "0" + tempVinLst;
										}
										tempVin = tempVin.substring(0, 11) + tempVinLst;
										hmVin.put(tempVin.substring(6, 8), tempVin);

										// 最后vin相等时
										// 更新printsetvin
										update_sql = "update printsetvin set clastvin='" + tempVin
												+ "' where printid='" + printId[iiPrintId] + "' and ctype='"
												+ tempVin.substring(6, 8) + "'";
										ps_stmt = con.createStatement();
										try {
											ps_stmt.executeUpdate(update_sql);
										} catch (Exception eUpdatePrintSet) {
											System.out.println("print_data插入" + eUpdatePrintSet.toString());
										} finally {
											if (ps_stmt != null)
												ps_stmt.close();
										}
										/** ***************printsetvinEnd************* */
										oldVinLst = oldVinLst + 1;

									}// end while vin不连续

									/** ***打印设置开始*** */
									JConfigure jc = new JConfigure();
									jc.setCQADNo(rs.getString("cQADNo"));
									jc.setCSEQNo_A(rs.getString("cSEQNo_A"));
									jc.setCVinCode(rs.getString("cVinCode"));
									jc.setCCarNo(rs.getString("cCarNo"));
									if (printSetCode.equals("1") && tFassName.trim().equals("方向盘")) {
										jc.setCCarType("FXP");
									} else if (printSetCode.equals("1") && tFassName.trim().equals("安全气囊")) {
										jc.setCCarType("AQQN");
									} else {
										jc.setCCarType(rs.getString("ccode").trim());
									}
									// 从0开始， 对应数据库iNum
									index++;
									if (index > tFassCount) {
										break;
									}
									jc.setIndex(index);
									superlist.get(iiPrintId).add(jc);

									// 打印表插入
									insert_sql = " insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) "
											+ " values('"
											+ printId[iiPrintId]
											+ "','"
											+ dh
											+ "','"
											+ dysj
											+ "',"
											+ cjs
											+ ","
											+ index
											+ ",'"
											+ vincode
											+ "','"
											+ sxh
											+ "','"
											+ tFass
											+ "','"
											+ zcid
											+ "','"
											+ ch
											+ "','" + rq + "','" + kinh + "')";
									ps_stmt = con.createStatement();
									try {
										ps_stmt.executeUpdate(insert_sql);
									} catch (Exception eUpdatePrintSet) {
										System.out.println("print_data插入" + eUpdatePrintSet.toString());
									} finally {
										if (ps_stmt != null)
											ps_stmt.close();
									}
									/** ************打印设置结束************* */
									/** ********printsetvin**************** */
									tempVinLst = String.valueOf(oldVinLst);
									lenTempVin = tempVinLst.length();
									for (int kk = 0; kk < (6 - lenTempVin); kk++) {
										tempVinLst = "0" + tempVinLst;
									}
									tempVin = tempVin.substring(0, 11) + tempVinLst;
									hmVin.put(tempVin.substring(6, 8), tempVin);

									// 最后vin相等时
									// 更新printsetvin
									update_sql = "update printsetvin set clastvin='" + tempVin + "' where printid='"
											+ printId[iiPrintId] + "' and ctype='" + tempVin.substring(6, 8) + "'";
									ps_stmt = con.createStatement();
									try {
										ps_stmt.executeUpdate(update_sql);
									} catch (Exception eUpdatePrintSet) {
										System.out.println("print_data插入" + eUpdatePrintSet.toString());
									} finally {
										if (ps_stmt != null)
											ps_stmt.close();
									}
									/** ***************printsetvinEnd************* */
								}
								// 连续
								else {
									hmVin.put(vincode.substring(6, 8), vincode);

									/** ***打印设置开始*** */
									JConfigure jc = new JConfigure();
									jc.setCQADNo(rs.getString("cQADNo"));
									jc.setCSEQNo_A(rs.getString("cSEQNo_A"));
									jc.setCVinCode(rs.getString("cVinCode"));
									jc.setCCarNo(rs.getString("cCarNo"));
									if (printSetCode.equals("1") && tFassName.trim().equals("方向盘")) {
										jc.setCCarType("FXP");
									} else if (printSetCode.equals("1") && tFassName.trim().equals("安全气囊")) {
										jc.setCCarType("AQQN");
									} else {
										jc.setCCarType(rs.getString("ccode").trim());
									}
									// 从0开始， 对应数据库iNum
									index++;
									jc.setIndex(index);
									// list.add(jc);
									superlist.get(iiPrintId).add(jc);

									// 打印表插入
									insert_sql = " insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) "
											+ " values('"
											+ printId[iiPrintId]
											+ "','"
											+ dh
											+ "','"
											+ dysj
											+ "',"
											+ cjs
											+ ","
											+ index
											+ ",'"
											+ vincode
											+ "','"
											+ sxh
											+ "','"
											+ tFass
											+ "','"
											+ zcid
											+ "','"
											+ ch
											+ "','" + rq + "','" + kinh + "')";
									ps_stmt = con.createStatement();
									try {
										ps_stmt.executeUpdate(insert_sql);
									} catch (Exception eUpdatePrintSet) {
										System.out.println("print_data插入" + eUpdatePrintSet.toString());
									} finally {
										if (ps_stmt != null)
											ps_stmt.close();
									}
									/** ************打印设置结束************* */
								}

							}
							// end while
							// end判断vin是否连续
							if (index == tFassCount) {
								break;
							}
						}
					} catch (Exception eGetJc) {
						System.out.println(eGetJc.toString());
					} finally {
						if (rs != null)
							rs.close();
						if (stmt != null)
							stmt.close();
					}
				} else {
					System.out.println("************printSet表中没有id为" + printId[iiPrintId] + "的数据");
				}
				iiPrintId++;
			}// end while of printId[]
			iiPrintId--;

			ServletContext context = this.getServletConfig().getServletContext();
			JasperPrint jasperPrint = null;
			String temp = printMd; // 打印模版在PrintSet表中 冗余存储。

			if (iiPrintId == 0) {
				File reportFile = new File(context.getRealPath(temp));
				File reportFile1 = new File(context.getRealPath("ireport"));

				// 定义datasource;
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("js", String.valueOf(cjs));
				parameters.put("zrq", rq);
				parameters.put("ch", ch);
				parameters.put("tm", dh);
				parameters.put("mc", printTitle);
				parameters.put("id", Integer.valueOf(printId[iiPrintId]));
				parameters.put("x_sub", reportFile1.getPath() + "\\");
				parameters.put("xdir", reportFile1.getPath() + "\\");

				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

				if (superlist.get(iiPrintId).size() > 0) {
					jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
							new JRBeanCollectionDataSource(superlist.get(iiPrintId)));
				} else {
					jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				}
			}
			// 方向盘 安全气囊混打
			else if (printSetCode.equals("1")) {
				// 方向盘、安全气囊数据集合
				List<JConfigure> fxpqnList = new ArrayList<JConfigure>();
				// 组织数据集合
				for (int iitFassCount = 0; iitFassCount < superlist.get(0).size(); iitFassCount++) {
					for (int iifxp = 0; iifxp <= iiPrintId; iifxp++) {
						fxpqnList.add(superlist.get(iifxp).get(iitFassCount));
					}
				}
				File reportFile = new File(context.getRealPath(temp));
				File reportFile1 = new File(context.getRealPath("ireport"));
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("js", String.valueOf(cjs));
				parameters.put("zrq", rq);
				parameters.put("ch", ch);
				parameters.put("mc", printTitle);
				parameters.put("id", Integer.valueOf(printId[iiPrintId]));// 打印项ID
				parameters.put("x_sub", reportFile1.getPath() + "\\");// 子目录
				parameters.put("xdir", reportFile1.getPath() + "\\");// 子报表路径
				parameters.put("tm", dh);// 底盘号

				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

				if (fxpqnList.size() > 0) {
					jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
							new JRBeanCollectionDataSource(fxpqnList));
				} else {
					jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				}
			} else {
				try {
					printSet_stmt = con.prepareStatement("SELECT * FROM printSet WHERE iPrintGroupId = ? ORDER BY id");
					printSet_stmt.setString(1, groupId);
					printSet_rs = printSet_stmt.executeQuery();

					for (int iiList = 0; printSet_rs.next();) {
						parameters.put(printSet_rs.getString("cCode"), new JRBeanCollectionDataSource(superlist
								.get(iiList++)));
						parameters.put("mc" + String.valueOf(iiList), printSet_rs.getString("ccartypedesc"));
						parameters.put("id" + String.valueOf(iiList), printSet_rs.getInt("id"));
						parameters.put("tm" + String.valueOf(iiList), printSet_rs.getString("cremark").trim());
					}
				} finally {
					if (printSet_rs != null) {
						try {
							printSet_rs.close();
						} finally {
							printSet_rs = null;
						}
					}
					if (printSet_stmt != null) {
						try {
							printSet_stmt.close();
						} finally {
							printSet_stmt = null;
						}
					}
				}
				File reportFile = new File(context.getRealPath(temp));
				File reportFile1 = new File(context.getRealPath("ireport"));

				parameters.put("REPORT_CONNECTION", con);
				parameters.put("js", String.valueOf(cjs));
				parameters.put("zrq", rq);
				parameters.put("ch", ch);
				parameters.put("SUBREPORT_DIR", reportFile1.getPath() + "\\");

				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
			}
			ServletOutputStream ouputStream = response.getOutputStream();

			ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
			oos.writeObject(jasperPrint);

			oos.flush();
			oos.close();

			ouputStream.flush();
			ouputStream.close();

			// 提交事务
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
