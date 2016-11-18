package th.tg.dao;

/**
 * 焊装查询DAO类
 * 
 * @author YuanPeng
 *
 */
public class DAO_WeldingSearch {

	/**
	 * 通过开始结束时间查询焊装信息
	 * 
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return	cSEQNo	顺序号
	 * 			cCarNo	KIN号
	 * 			cQADNo	总成号
	 * 			iTFASSNum	总成数量
	 */
	public String getWeldingByTimeCarType(String startTime,String endTime){
		String sql = "select d.cSEQNo,d.cCarNo,p.cQADNo,p.iTFASSNum " +
			"from carData d,carData_D p " +
			"where d.cCarNo=p.iCarId " +
			"and d.dWBegin<=convert(varchar(100)," + startTime + ",20) " +
			"and d.dWBegin>=convert(varchar(100)," + endTime + ",20)";
		return sql;
	}
	
	/**
	 * 查询车辆数量
	 * 
	 * @return	
	 */
	public String getCount(){
		String sql = "select count(*) as count from carData group by cCarNo";
		return sql;
	}
	
	/**
	 * 按时间差查询总成
	 * 
	 * @return
	 */
	public String getPartsByStartTimeEndTime(String startTime,String endTime,String carType,int searchsetid){
		String sql = "select ITFASSNameId,max(cTFASSName) as name from cardata_d c right join  tfassname pn" +
		   " on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where cd.dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
			"and cd.dWBegin<=convert(varchar(100),'" + endTime + "',20)";
			if(searchsetid==3)
				sql = sql + " and substring(ccarno,5,1)<>'7' ";
		    sql = sql+" and substring(cd.cCarNo,6,1) in (" + carType +
			") group by ITFASSNameId";
		return sql;
	}
	
	/**
	 * 按时间差B8Q5查询总成
	 * 
	 * @return
	 */
	public String getPartsByStartTimeEndTime_B8Q5(String startTime,String endTime,String carType,String B8_Q5){
		String sql = "select ITFASSNameId,max(cTFASSName) as name from cardata_d c right join  tfassname pn" +
		   " on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where cd.dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
			"and cd.dWBegin<=convert(varchar(100),'" + endTime + "',20)" +
			" and substring(cSEQNo,1,2)='" + B8_Q5 +
		    "' and substring(cd.cCarNo,6,1) in (" + carType +
			") group by ITFASSNameId";
		return sql;
	}		
	
	/**
	 * 无条件查询
	 * 
	 * @return
	 */
	public String getParts(String carType,int searchsetid){
		String sql = "select ITFASSNameId,max(cTFASSName) as name from cardata_d c " +
			"right join  tfassname pn" +
			" on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where substring(cd.cCarNo,6,1) in (" + carType + ") ";
			if(searchsetid==3)
				sql = sql + " and substring(ccarno,5,1)<>'7' ";
		    sql = sql+
			" and dWBegin is not null and dABegin is null" +
			" group by ITFASSNameId";
		return sql;
	}
	
	/**
	 * 无条件查询
	 * 
	 * @return
	 */
	public String getParts_B8Q5(String carType,String B8_Q5){
		String sql = "select ITFASSNameId,max(cTFASSName) as name from cardata_d c " +
			"right join  tfassname pn" +
			" on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where substring(cd.cCarNo,6,1) in (" + carType +
			") and substring(cseqno,1,2)='" + B8_Q5 + "'" +
			" and dWBegin is not null and dABegin is null" +
			" group by ITFASSNameId";
		return sql;
	}
	
	/**
	 * 按顺序号差查询总成
	 * 
	 * @return
	 */
	public String getPartsByStartOrderEndOrder(String startOrder,String endOrder,String carType,int searchsetid){
		String sql = "select ITFASSNameId,max(cTFASSName) as name from cardata_d c right join  tfassname pn" +
		   " on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where dwbegin>=(select max(dwbegin) from cardata where cseqno='" + startOrder + "') " +
			"and dwbegin<=(select max(dwbegin) from cardata where cseqno='" + endOrder + "') " +
		    " and substring(cd.cCarNo,6,1) in (" + carType + ") ";
			if(searchsetid==3)
				sql = sql + " and substring(ccarno,5,1)<>'7' ";
		    sql = sql+
			" group by ITFASSNameId";
		return sql;
	}
	
	/**
	 * 按顺序号差查询总成
	 * 
	 * @return
	 */
	public String getPartsByStartOrderEndOrder_B8Q5(String startOrder,String endOrder,String carType,String B8Q5){
		String sql = "select ITFASSNameId,max(cTFASSName) as name from cardata_d c right join  tfassname pn" +
		   " on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where dwbegin>=(select max(dwbegin) from cardata where cseqno='" + startOrder + "') " +
			"and dwbegin<=(select max(dwbegin) from cardata where cseqno='" + endOrder + "')" +
		    " and substring(cd.cCarNo,6,1) in (" + carType +
		    ") and substring(cseqno,1,2)='" + B8Q5 +
		    "' and cseqno>='" + startOrder +
		    "' and cseqno<='" +endOrder +
			"' group by ITFASSNameId";
		return sql;
	}
	
	/**
	 * 按开始时间、数量查询总成
	 * 
	 * @return
	 */
	public String getPartsByStartTimeNum(String startTime,String num,String carType,int searchsetid){
		String sql = "select top " + num + " ITFASSNameId,max(cTFASSName) as name from cardata_d c " +
			"right join  tfassname pn" +
			" on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where cd.dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
		    " and substring(cd.cCarNo,6,1) in (" + carType + ") ";
			if(searchsetid==3)
				sql = sql + " and substring(ccarno,5,1)<>'7' ";
		    sql = sql+
			" group by ITFASSNameId";
		return sql;
	}
	
	/**
	 * 按开始时间、数量查询总成
	 * 
	 * @return
	 */
	public String getPartsByStartTimeNum_B8Q5(String startTime,String num,String carType,String B8_Q5){
		String sql = "select top " + num + " ITFASSNameId,max(cTFASSName) as name from cardata_d c " +
			"right join  tfassname pn" +
			" on pn.id = c.itfassnameid left join cardata cd on cd.cCarNo=c.iCarId " +
			"where cd.dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
		    " and substring(cd.cCarNo,6,1)in (" + carType +
		    ") and substring(cseqno,1,2)='" + B8_Q5 +
			"' group by ITFASSNameId";
		return sql;
	}
	
	/**
	 * 通过时间差查询统计数据
	 * 
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStatByStartTimeEndTime(String startTime,String endTime,String carType,int searchsetid){
		String sql = "select max(d.cQADNo) max_no,sum(d.iTFASSNum) sum_num from carData_d d " + 
			"right join cardata c on c.cCarNo=d.iCarId " +
			"where c.dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
			"and c.dWBegin<=convert(varchar(100),'" + endTime + "',20)";
			if(searchsetid==3)
				sql = sql + " and substring(ccarno,5,1)<>'7' ";
		    sql = sql+" and substring(c.cCarNo,6,1) in (" + carType +
			") group by cQADNo order by max_no";
		return sql;
	}
	
	/**
	 * 通过时间差查询统计数据
	 * 
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStatByStartTimeEndTime_B8Q5(String startTime,String endTime,String carType,String B8_Q5){
		String sql = "select max(d.cQADNo) max_no,sum(d.iTFASSNum) sum_num from carData_d d " + 
			"right join cardata c on c.cCarNo=d.iCarId " +
			"where c.dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
			"and c.dWBegin<=convert(varchar(100),'" + endTime + "',20)" +
			" and substring(cseqno,1,2)='" + B8_Q5 +
		    "' and substring(c.cCarNo,6,1) in (" + carType +
			") group by cQADNo ";
		return sql;
	}

	/**
	 * 无条件统计
	 * 
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStat(String carType,int searchsetid){
		String sql = "select max(d.cQADNo) max_no,sum(d.iTFASSNum) sum_num from carData_d d " + 
			"right join cardata c on c.cCarNo=d.iCarId " +
			"where c.dWBegin is not null " +
			"and c.dABegin is null" +
		    " and substring(c.cCarNo,6,1) in (" + carType + ") ";
			if(searchsetid==3)
				sql = sql + " and substring(ccarno,5,1)<>'7' ";
		    sql = sql+" group by cQADNo order by max_no";
		return sql;
	}

	/**
	 * 无条件统计
	 * 
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStat_B8Q5(String carType,String B8_Q5){
		String sql = "select max(d.cQADNo) max_no,sum(d.iTFASSNum) sum_num from carData_d d " + 
			"right join cardata c on c.cCarNo=d.iCarId " +
			"where c.dWBegin is not null " +
			"and c.dABegin is null" +
			" and substring(cseqno,1,2)='" + B8_Q5 + "'" +
		    " and substring(c.cCarNo,6,1) in (" + carType +
			") group by cQADNo ";
		return sql;
	}
	
	/**
	 * 通过顺序号差查询统计数据
	 * 
	 * @param startOrder	开始顺序号
	 * @param endOrder		结束顺序号
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStatByStartOrderEndOrder(String startOrder,String endOrder,String carType,int searchsetid){
		String sql = "select max(d.cQADNo) max_no,sum(d.iTFASSNum) sum_num from carData_d d " + 
			"right join cardata c on c.cCarNo=d.iCarId " +
			"where dwbegin>=(select max(dwbegin) from cardata where cseqno='" + startOrder + "') " +
			"and dwbegin<=(select max(dwbegin) from cardata where cseqno='" + endOrder + "')" +
			" and cseqno>='" + startOrder + "' and cseqno<='" + endOrder + "' " +
		    " and substring(c.cCarNo,6,1) in (" + carType + ") ";
			if(searchsetid==3)
				sql = sql + " and substring(ccarno,5,1)<>'7' ";
		    sql = sql+
			" group by cQADNo order by max_no";
		return sql;
	}
	
	/**
	 * 通过顺序号差查询统计数据
	 * 
	 * @param startOrder	开始顺序号
	 * @param endOrder		结束顺序号
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStatByStartOrderEndOrder_B8Q5(String startOrder,String endOrder,String carType,String B8_Q5){
		String sql = "select max(d.cQADNo) max_no,sum(d.iTFASSNum) sum_num from carData_d d " + 
			"right join cardata c on c.cCarNo=d.iCarId " +
			"where dwbegin>=(select max(dwbegin) from cardata where cseqno='" + startOrder + "') " +
			"and dwbegin<=(select max(dwbegin) from cardata where cseqno='" + endOrder + "') " +
		    " and substring(c.cCarNo,6,1) in (" + carType +
		    ") and substring(cseqno,1,2)='" + B8_Q5 +
		    "' and cseqno>='" + startOrder +
		    "' and cseqno<='" + endOrder +
			"' group by cQADNo ";
		return sql;
	}
	
	/**
	 * 通过开始时间、数量查询统计数据
	 * 
	 * @param startTime	开始时间
	 * @param num		数量
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStatByStartTimeNum(String startTime,String num,String carType,int searchsetid){
		String sql = "select max(cqadno) max_no,sum(itfassnum) as sum_num from cardata_d " +
				"where icarid in (select top " + num + " cCarno from cardata " +
				"where dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
				"and substring(cCarNo,6,1) in (" + carType + ") ";
				if(searchsetid==3)
					sql = sql + " and substring(ccarno,5,1)<>'7' ";
			    sql = sql+ " order by cseqno) group by cqadno order by max_no";
		
		return sql;
	}
	
	/**
	 * 通过开始时间、数量查询统计数据
	 * 
	 * @param startTime	开始时间
	 * @param num		数量
	 * @param carType	车辆类型
	 * @return
	 */
	public String getStatByStartTimeNum_B8Q5(String startTime,String num,String carType,String B8_Q5){
		String sql = "select max(cqadno) max_no,sum(itfassnum) as sum_num from cardata_d " +
				"where icarid in (select top " + num + " cCarno from cardata " +
				"where dWBegin>=convert(varchar(100),'" + startTime + "',20) " +
				" and substring(cseqno,1,2)='" + B8_Q5 +"' " +
				"and substring(cCarNo,6,1) in (" + carType + ") order by cseqno) group by cqadno";
		
		return sql;
	}
	
	/**
	 * 通过SQL条件查询总装查询
	 * 
	 * @param sql_temp1	SQL条件
	 * @return
	 */
	public String getACarsByCondition(String sql_temp1){
		String sql = "select cSEQNo_A,cVinCode,cCarNo,dWBegin,dABegin,dCp6Begin from carData" + sql_temp1 +
		" order by dABegin desc,cSEQNo_A desc";
		return sql;
	}
	
	/**
	 * 通过SQL条件查询总装统计
	 * 
	 * @param sql_temp1	SQL条件
	 * @return
	 */
	public String getAStatByCondition(String sql_temp1){
		String sql = "select max(cQADNo) seq,sum(iTFASSNum) num from cardata_d d " +
				"inner join cardata c on d.iCarId=c.cCarNo " + sql_temp1 +
				" group by cQADNo order by seq";
		return sql;
	}
	
	/**
	 * 通过车型、工厂KIS总装查询
	 * 
	 * @param carType	车型
	 * @param factory	工厂
	 * @return
	 */
	public String getKISAssemblyByCartype(String carType,String factory,String sql_temp){
		String sql = "select cSEQNo_A,substring(cvincode,7,2) cartype,cVinCode,cCarNo,dABegin,dCp6Begin " +
			"from carData where substring(cCarNo,6,1) in (" +
			carType + ") and substring(cSEQNo_A,1,2)='" + factory + 
			"' " + sql_temp +
			" order by dABegin desc,cSEQNo_A desc";
		return sql;
	}
	
	/**
	 * 通过车型、工厂KIS焊装查询
	 * 
	 * @param carType	车型
	 * @param factory	工厂
	 * @return
	 */
	public String getKISWeldingByCartype(String carType){
		String sql = "select cSEQNo,cVinCode,cCarNo,dWBegin from carData where substring(cCarNo,6,1) in (" +
			carType + ")" +
			" and DateDiff(hour,dWBegin,getdate())<24" + 
			" order by dwBegin desc,cSEQNo desc";
		return sql;
	}
	
	/**
	 * 通过条件查询零件
	 * 
	 * @param sql_temp1
	 * @return
	 */
	public String getpart(String sql_temp1){
		StringBuilder sb = new StringBuilder();
		sb.append(" select cqadno,  ctfassname, p.pageno,");
		sb.append(" p.dpcode + ' ' + p.dpdate +  ' ' + p.dpseqnum traceone,");
		sb.append(" p.partseq tracetwo, CONVERT(varchar(20),p.recorddate, 120) recorddate");
		sb.append(" from v_cardata_d d inner join v_cardata c on c.ccarno = d.icarid");
		sb.append(" inner join tfassname t on t.id = d.itfassnameid");
		sb.append(" left join v_pageno_part p on d.cqadno = p.partname and c.cvincode = p.vin");
		sb.append(" where ");
		sb.append(sql_temp1);
		
		return sb.toString();
	}
	
	/**
	 * 通过查询条件查询车
	 * 
	 * @param sql_temp1
	 * @return
	 */
	public String getcar(String sql_temp1){
		String sql = "select c.cSEQNo,c.cSEQNo_A,c.cVinCode,c.cCarNo,dWBegin,dABegin,dCp6Begin," +
				"(select max(cfilename) from productdata_w w where w." + sql_temp1 + ") cfilename_w," +
				"(select max(cfilename) from productdata_a a where a." + sql_temp1 + ") cfilename_a " +
				"from cardata c where c." + sql_temp1;
		return sql;
	}
}
