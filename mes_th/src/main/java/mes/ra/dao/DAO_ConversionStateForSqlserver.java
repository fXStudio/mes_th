package mes.ra.dao;

import mes.ra.bean.ConversionState;
public class DAO_ConversionStateForSqlserver extends DAO_ConversionStateForOracle{
	/**
	 * 创建Conversionstate
	 * 
	 * @param conversionstate
	 * @return 创建conversionstate的sql语句
	 */
	public String saveConversionState(ConversionState ConversionState) {
		String sql = "insert into t_ra_stateconversion(int_fromstate,int_tostate,str_desc) "
			+ "values('"
			+ ConversionState.getFromstate()
			+ "','" + ConversionState.getTostate()+ "','" + ConversionState.getDesc() + "')";
	return sql;
	}
}
