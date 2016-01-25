package th.tg.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import th.tg.bean.CP7CarData;
import th.tg.dao.DAO_CP7Search;

/**
 * QAD车型总成查询工厂
 * 
 * @author YuanPeng
 *
 */
public class CP7SearchFactory {
	//日志
	private final Log log = LogFactory.getLog(CP7SearchFactory.class);

	/**
	 * 通过时间差统计QAD车辆总成信息
	 * 
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param con			连接对象
	 * @return	List<CP7CarData>
	 * @throws SQLException	SQL异常
	 */
	public List<CP7CarData> getCarDataStatByStartTimeEndTime(String startTime,String endTime,Connection con)
			throws SQLException{
		List<CP7CarData> list = new ArrayList<CP7CarData>();
		DAO_CP7Search dao = new DAO_CP7Search();
		Statement stmt = con.createStatement();
		log.debug("通过时间差统计QAD车辆总成信息SQL:"+dao.getCarDataStatByStartTimeEndTime(startTime, endTime));
		ResultSet rs = stmt.executeQuery(dao.getCarDataStatByStartTimeEndTime(startTime, endTime));
		while(rs.next()){
			CP7CarData cp7 = new CP7CarData();
			cp7.setPart_no(rs.getString("cTfass"));
			cp7.setCartype(rs.getString("cCarType"));
			cp7.setNum(rs.getFloat("nNum"));
			list.add(cp7);
		}
		if(stmt!=null){
			stmt.close();
			stmt = null;
		}
		return list;
	}
	
	/**
	 * 通过时间差统计大众车辆零件信息
	 * 
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param con			连接对象
	 * @return	List<CP7CarData>
	 * @throws SQLException	SQL异常
	 */
	public List<CP7CarData> getProductDataStatByStartTimeEndTime(String startTime,String endTime,Connection con)
			throws SQLException{
		List<CP7CarData> list = new ArrayList<CP7CarData>();
		DAO_CP7Search dao = new DAO_CP7Search();
		Statement stmt = con.createStatement();
		log.debug("通过时间差统计大众车辆零件信息SQL:"+dao.getProductDataStatByStartTimeEndTime(startTime, endTime));
		ResultSet rs = stmt.executeQuery(dao.getProductDataStatByStartTimeEndTime(startTime, endTime));
		while(rs.next()){
			CP7CarData cp7 = new CP7CarData();
			cp7.setPart_no(rs.getString("cpartno"));
			cp7.setPart_name(rs.getString("cpartname"));
			cp7.setNum(rs.getFloat("nNum"));
			list.add(cp7);
		}
		if(stmt!=null){
			stmt.close();
			stmt = null;
		}
		return list;
	}
	
	/**
	 * 通过时间差统计未匹配散件信息
	 * 
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param con			连接对象
	 * @return	List<CP7CarData>
	 * @throws SQLException	SQL异常
	 */
	public List<CP7CarData> getErrStatByStartTimeEndTime(String startTime,String endTime,Connection con)
			throws SQLException{
		List<CP7CarData> list = new ArrayList<CP7CarData>();
		DAO_CP7Search dao = new DAO_CP7Search();
		Statement stmt = con.createStatement();
		log.debug("通过时间差统计未匹配散件信息SQL:"+dao.getErrStatByStartTimeEndTime(startTime, endTime));
		ResultSet rs = stmt.executeQuery(dao.getErrStatByStartTimeEndTime(startTime, endTime));
		while(rs.next()){
			CP7CarData cp7 = new CP7CarData();
			cp7.setPart_no(rs.getString("cpartno"));
			cp7.setPart_name(rs.getString("cpartname"));
			cp7.setNum(rs.getFloat("nNum"));
			list.add(cp7);
		}
		if(stmt!=null){
			stmt.close();
			stmt = null;
		}
		return list;
	}
}
