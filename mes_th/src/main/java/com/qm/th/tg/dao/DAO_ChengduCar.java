package com.qm.th.tg.dao;

/**
 * 成都车DAO
 * 
 * @author YuanPeng
 *
 */
public class DAO_ChengduCar {

	/**
	 * 按照开始日期删除成都捷达
	 * 
	 * @param startDate	开始日期
	 * @return
	 */
	public String deleteChengduJettaByStartDate(String startDate){
		String sql = "delete from cardata where substring(cCarNo,6,1)='0'" +
			" and substring(ccarno,5,1)='7'" +
			" and convert(varchar(200),dwbegin,23)<=convert(varchar(200),'" + 
			startDate + "',23)";
		return sql;
	}
	
	/**
	 * 按照开始日期查询成都捷达
	 * 
	 * @param startDate	开始日期
	 * @return
	 */
	public String getChengduJettaByStartDate(String startDate){
		String sql = "select cCarNo,cVinCode,dWBegin from cardata" +
				" where substring(cCarNo,6,1)='0'" +
				" and substring(ccarno,5,1)='7'" +
				" and convert(varchar(200),dwbegin,23)<=convert(varchar(200),'" + 
			startDate + "',23) order by dWBegin,cSEQNo";
		return sql;
	}
	
}
