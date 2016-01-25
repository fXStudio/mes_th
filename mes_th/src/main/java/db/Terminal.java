package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.qm.mes.th.helper.Conn_MES;


/**
 * 
 * @author RenJian
 */
public class Terminal {

	public int getRows(Connection con, String cPageNo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int rows = 0;
		String sql = "SELECT COUNT(*) FROM PAGENO_PART WHERE PAGENO = '"
				+ cPageNo + "'";

		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				rows = rs.getInt(1);
			}
			con.commit();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public int getStateExist(Connection con, String cPageNo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int rows = 0;
		String sql = "SELECT COUNT(*) FROM PAGENO_state WHERE PAGENO = '"
				+ cPageNo + "'";

		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				rows = rs.getInt(1);
			}
			con.commit();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public ArrayList<TerminalBean> getCTFAssList(Connection con, String cPageNo) {
		PreparedStatement psm = null;
		ResultSet rs = null;

		ArrayList<TerminalBean> list = new ArrayList<TerminalBean>();
		String cCode = getCCode(con, cPageNo);
		String sql = "";
		if (!"1".equals(cCode)) {
			sql = "SELECT CTFASS,CVINCODE FROM PRINT_DATA WHERE CPAGENO ='"
					+ cPageNo + "'";
		} else {
			// 如:100618070001
			String strOne = (Integer.parseInt(cPageNo.substring(0, 8)) + 1)
					+ "";

			String strTwo = cPageNo.substring(8, cPageNo.length());
			sql = "SELECT CTFASS,CVINCODE FROM PRINT_DATA WHERE CPAGENO in('"
					+ cPageNo + "','" + (strOne + strTwo) + "')";
		}

		try {
			psm = con.prepareStatement(sql+" order by inum");
			rs = psm.executeQuery();
			while (rs.next()) {
				TerminalBean ter = new TerminalBean();
				ter.setCtfass(rs.getString("cTFAss"));
				ter.setCvincode(rs.getString("CVINCODE"));
				list.add(ter);
				ter = null;
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(psm);
		}
		return list;
	}

	public boolean insertPageNo_Part(Connection con, String partname,
			String partno, String pageno, String emp, String vin) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO PAGENO_PART(PARTNAME,PARTSEQ,PAGENO,RECORDDATE,EMP,VIN) "
				+ "VALUES(?,?,?,getdate(),?,?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, partname);
			psm.setString(2, partno);
			psm.setString(3, pageno);
			psm.setString(4, emp);
			psm.setString(5, vin);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertPartName_Code(Connection con, String partname,
			String code) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO PARTNAME_CODE(PARTNAME,CODE) "
				+ "VALUES(?,?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, partname);
			psm.setString(2, code);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertCar(Connection con, String car) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO car_info " + "VALUES(?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean updateDoorNum(Connection con, String doornum, String car) {
		PreparedStatement psm = null;

		String sql = "UPDATE CAR_PAGENO SET DOORNO = ? "
				+ "WHERE ID = (SELECT MAX(ID) FROM CAR_PAGENO WHERE CAR = ?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, doornum);
			psm.setString(2, car);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean updatePartNo(Connection con, String partseq, String pageno) {
		PreparedStatement psm = null;

		String dpcode = partseq.substring(0, 6);
		String dpdate = partseq.substring(7, 13);
		String dpseqnum = partseq.substring(14, partseq.length());

		String sql = "UPDATE PAGENO_PART SET dpcode = ?,dpdate=?,dpseqnum=? "
				+ "WHERE ID = (SELECT MAX(ID) FROM PAGENO_PART WHERE PAGENO = ?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, dpcode);
			psm.setString(2, dpdate);
			psm.setString(3, dpseqnum);
			psm.setString(4, pageno);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean updateCarOut(Connection con, String outemp, String recorddate) {
		PreparedStatement psm = null;
		String sql = "UPDATE CAR_PAGENO SET OUTRECORDDATE = getdate(),OUTEMP='"
				+ outemp + "'" + "WHERE datediff(second,'" + recorddate
				+ "',recorddate)=0";
		try {
			psm = con.prepareStatement(sql);
			psm.executeUpdate();
			psm.close();
			sql = "update car_pageno set outpnostate=1 where datediff(second,'"
					+ recorddate + "',recorddate)=0";
			psm = con.prepareStatement(sql);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean deleteCarDestrop(Connection con, String car) {
		PreparedStatement psm = null;

		String sql = "delete from car_pageno WHERE CAR = ? and sequence=(select max(sequence) from car_pageno where car=?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.setString(2, car);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertPageNo_State(Connection con, String pageno, String emp) {
		String sql = "INSERT INTO PAGENO_STATE(PAGENO,PAGENOSTATE,RECORDDATE,EMP) "
				+ "VALUES(?,?,getdate(),?)";
		PreparedStatement psm = null;
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, pageno);
			psm.setString(2, String.valueOf(1));
			psm.setString(3, emp);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertCarPNo(Connection con, String car, String pageno,
			String emp) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO CAR_PAGENO(CAR,PAGENO,RECORDDATE,EMP) "
				+ "VALUES(?,?,getdate(),?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.setString(2, pageno);
			psm.setString(3, emp);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);

		}
		return false;
	}

	public boolean insertCarInfo(Connection con, String car, String[] infos,
			String doorno, String emp) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO CAR_PAGENO(CAR,PAGENO,RECORDDATE,EMP,DOORNO,sequence) "
				+ "VALUES(?,?,getdate(),?,?,?)";
		try {
			int seq=getCarSequenceRows(con,car)+1;
			for (String pageno : infos) {
				psm = con.prepareStatement(sql);
				psm.setString(1, car);
				psm.setString(2, pageno);
				psm.setString(3, emp);
				psm.setString(4, doorno);
				psm.setInt(5, seq);
				psm.executeUpdate();
				con.commit();
				this.close(psm);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

	public int getCarSequenceRows(Connection con, String car) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int rows = 0;
		String sql = "SELECT max(sequence) FROM CAR_PAGENO WHERE car = '" + car
				+ "' and outpnostate=1";

		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				rows = rs.getInt(1);
			}
			con.commit();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public boolean deleteCancelOne(Connection con, String car) {
		PreparedStatement psm = null;

		String sql = "DELETE FROM CAR_PAGENO " + "WHERE ID=("
				+ "select top 1 ID " + "FROM CAR_PAGENO "
				+ "WHERE CAR=? order by RECORDDATE desc" + ")";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean deleteOne(Connection con, String pageno) {
		PreparedStatement psm = null;

		String sql = "DELETE FROM pageno_part " + "WHERE ID=("
				+ "select top 1 ID " + "FROM pageno_part "
				+ "WHERE pageno=? order by RECORDDATE desc" + ")";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, pageno);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean deleteAll(Connection con, String pageno) {
		PreparedStatement psm = null;

		String sql = "DELETE FROM pageno_part WHERE pageno=?";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, pageno);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean deleteCancelFull(Connection con, String car) {
		PreparedStatement psm = null;

		String sql = "DELETE FROM CAR_PAGENO WHERE CAR=?";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public String getPageNoState(Connection con, String cPageNo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String state = "0";
		String sql = "SELECT PAGENOSTATE FROM PAGENO_STATE WHERE PAGENO = '"
				+ cPageNo + "'";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				state = rs.getString(1);
			}
			con.commit();
			return state;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return "";
	}

	public String getPartNameCode(Connection con, String code) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String partname = "";
		String sql = "SELECT PARTNAME FROM PARTNAME_CODE WHERE CODE = '" + code
				+ "'";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				partname = rs.getString(1);
			}
			con.commit();
			return partname;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return "";
	}

	public String getCCode(Connection con, String pageno) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String cCode = "";
		String sql = "select cCode from printset where id =("
				+ "select distinct iPrintGroupId from print_data "
				+ "where cPageNo ='" + pageno + "')";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				cCode = rs.getString(1);
			}
			con.commit();
			return cCode;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return "";
	}

	public Date getPrintDate(Connection con, String cPageNo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		Date printdate = null;
		String sql = "SELECT DPRINTTIME FROM PRINT_DATA WHERE CPAGENO = '"
				+ cPageNo + "'";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				printdate = rs.getDate(1);
			}
			con.commit();
			return printdate;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}

		return null;
	}

	public int getPageNoCount(Connection con, String car) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "SELECT COUNT(*) FROM CAR_PAGENO WHERE CAR = '" + car
				+ "'";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public int getFilterCount(String condition) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "select count(*) as cou from " + "("
				+ "select ps.pageno,ps.recorddate as psdate,ps.emp as psemp,"
				+ "cp.recorddate as cpdate,cp.emp as cpemp,"
				+ "cs.car as cscar,"
				+ "cs.recorddate as csdate,cs.emp as csemp "
				+ "from pageno_state ps " + "inner join car_pageno cp "
				+ "on ps.pageno=cp.pageno " + "inner join car_state cs "
				+ "on cp.car=cs.car " + condition + ") t";
		try {
			con = new Conn_MES().getConn();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
			this.close(con);
		}
		return -1;
	}

	public int getFilterCPageNoSeaCount(String condition) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "select count(*) as cou from("
				+ " SELECT distinct PD.CPAGENO,"
				+ " CONVERT(VARCHAR,DAY(PD.CREMARK))+'-'+CONVERT(VARCHAR,PD.ICARNO) AS CODE,"
				+ " CP.CAR,PD.dPrintTime as printdate,CP.outpnostate,"
				+ " ps.recorddate as partdate,cs.recorddate as cardate,"
				+ " cs.outrecorddate as outdate,pst.pagenostate"
				+ " FROM PRINT_DATA PD"
				+ " left JOIN pageno_state ps on pd.cpageno=ps.pageno"
				+ " left JOIN CAR_PAGENO CP ON PD.CPAGENO=CP.PAGENO"
				+ " left join car_state cs on cp.car=cs.car"
				+ " left join pageno_state pst on pd.cpageno=pst.pageno"
				+ ") t " + condition;
		System.out.println(sql);
		try {
			con = new Conn_MES().getConn();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
			this.close(con);
		}
		return -1;
	}

	public int getDailFilterCount(String condition) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "select count(*) from pageno_part" + condition;
		try {
			con = new Conn_MES().getConn();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
			this.close(con);
		}
		return -1;
	}

	public int getCarDailFilterCount(String condition) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "select count(*) from car_pageno" + condition;
		try {
			con = new Conn_MES().getConn();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
			this.close(con);
		}
		return -1;
	}

	public int getCarFilterCount(String condition) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "select count(*) from car_pageno" + condition;
		try {
			con = new Conn_MES().getConn();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
			this.close(con);
		}
		return -1;
	}

	public int getPageNoExist(Connection con, String cPageNo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "SELECT COUNT(*) FROM CAR_PAGENO WHERE PAGENO='" + cPageNo
				+ "'";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public int getCarExist(Connection con, String car) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "SELECT COUNT(*) FROM CAR_STATE WHERE CAR = '" + car + "'";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public int getCarSearchExist(Connection con, String car) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "SELECT COUNT(*) FROM CAR_STATE WHERE CAR = '" + car + "'";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public boolean insertCar_State(Connection con, String car, String emp) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO CAR_STATE(CAR,CARSTATE,RECORDDATE,EMP) "
				+ "VALUES(?,?,getdate(),?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.setString(2, String.valueOf(1));
			psm.setString(3, emp);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean deleteCar_State(Connection con, String car) {
		PreparedStatement psm = null;

		String sql = "delete from car_state where car=?";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertOSOut(Connection con, String car, String osout) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO OS_OUT(CAR,OSOUT) " + "VALUES(?,?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.setString(2, osout);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertOSIn(Connection con, String car, String osin) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO OS_IN(CAR,OSIN) " + "VALUES(?,?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.setString(2, osin);
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertOSOut_State(Connection con, String car) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO OSOUT_STATE(CAR,OSOUTSTATE) " + "VALUES(?,?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.setString(2, String.valueOf(1));
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public boolean insertOSIn_State(Connection con, String car) {
		PreparedStatement psm = null;

		String sql = "INSERT INTO OSIN_STATE(CAR,OSINSTATE) " + "VALUES(?,?)";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, car);
			psm.setString(2, String.valueOf(1));
			psm.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(psm);
		}
		return false;
	}

	public int getAllFilterCount(String condition, String tablename_A,
			String tablename_B, String tablename_C, String tablename_D,
			String tablename_E) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int num = 0;
		String sql = "select count(*) as cou from"
				+ "(select cVinCode,cKinNo,isnull(cTFAss,'') as cTFAss,isnull(partseq,'') as partseq,isnull(dpcode,'') as dpcode,isnull(dpdate,'') as dpdate,"
				+ "isnull(dpseqnum,'') as dpseqnum,cPageNo,dPrintTime,ps.recorddate as psrecorddate,"
				+ "ps.emp,cs.recorddate as csrecorddate,cp.car,cp.doorno,cp.outrecorddate "
				+ "from " + tablename_A + " pr " + "left join " + tablename_B
				+ " pp  " + "on pr.cVincode=pp.vin and pr.cPageNo=pp.pageno "
				+ "left join " + tablename_C + " ps on pp.pageno=ps.pageno "
				+ "left join " + tablename_D + " cp on pr.CpageNo=cp.pageno "
				+ "left join " + tablename_E + " cs on cp.car=cs.car) t "
				+ condition;
		try {
			con = new Conn_MES().getConn();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			con.commit();
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
			this.close(con);
		}
		return -1;
	}

	public int getSeqRows(Connection con, String seq) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int rows = 0;
		String sql = "SELECT COUNT(*) FROM PAGENO_PART WHERE DPCODE + ' ' + DPDATE + ' ' + DPSEQNUM='"
				+ seq + "'";

		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				rows = rs.getInt(1);
			}
			con.commit();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	public int getTSeqRows(Connection con, String seq) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		int rows = 0;
		String sql = "SELECT COUNT(*) FROM PAGENO_PART WHERE PARTSEQ='" + seq + "'";

		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				rows = rs.getInt(1);
			}
			con.commit();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs);
			this.close(pstm);
		}
		return -1;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param con
	 *            数据库连接对象
	 */
	protected void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}

	/**
	 * 关闭命令操作
	 * 
	 * @param pstm
	 *            命令操作对象
	 */
	protected void close(PreparedStatement pstm) {
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				pstm = null;
			}
		}
	}

	/**
	 * 关闭记录集
	 * 
	 * @param rs
	 *            记录集对象
	 */
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
	}
}
