package th.tg.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import th.tg.bean.ChengduCar;
import th.tg.dao.DAO_ChengduCar;

/**
 * 成都车工厂
 * 
 * @author YuanPeng
 *
 */
public class ChengduCarFactory {

	//日志
	private final Log log = LogFactory.getLog(ChengduCarFactory.class);
	
	/**
	 * 按照开始日期删除成都捷达
	 * 
	 * @param startDate
	 * @param con
	 * @throws SQLException
	 */
	public void deleteChengduJettaByStartDate(String startDate,Connection con)
	throws SQLException{
		DAO_ChengduCar dao = new DAO_ChengduCar();
		Statement stmt = con.createStatement();
		log.debug("按照开始日期删除成都捷达:"+dao.deleteChengduJettaByStartDate(startDate));
		stmt.execute(dao.deleteChengduJettaByStartDate(startDate));
		if(stmt != null){
			stmt.close();
			stmt = null;
		}
	}
	/**
	 * 按照开始日期查询成都捷达
	 * 
	 * @param startDate
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<ChengduCar> getChengduJettaByStartDate(String startDate,Connection con)throws SQLException{
		List<ChengduCar> list = new ArrayList<ChengduCar>();
		DAO_ChengduCar dao = new DAO_ChengduCar();
		Statement stmt = con.createStatement();
		log.debug("按照开始日期查询成都捷达:"+dao.getChengduJettaByStartDate(startDate));
		ResultSet rs = stmt.executeQuery(dao.getChengduJettaByStartDate(startDate));
		while(rs.next()){
			ChengduCar cdc = new ChengduCar();
			cdc.setCCarNo(rs.getString("cCarNo"));
			cdc.setCVinCode(rs.getString("cVinCode"));
			cdc.setDWBegin(rs.getString("dWBegin"));
			list.add(cdc);
		}
		if(stmt != null){
			stmt.close();
			stmt = null;
		}
		return list;
	}
}
