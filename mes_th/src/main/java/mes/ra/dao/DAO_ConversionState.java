package mes.ra.dao;

import mes.ra.bean.ConversionState;

public interface DAO_ConversionState {
	/**
	 * 查询状态转换信息
	 * 
	 * @return sql语句<br>
	 *         int_id 序号 <br>
	 *         INT_FROMSTATE 原状态号<br>
	 *         INT_TOSTATE 跳转状态号<br>
	 *         STR_DESC 描述信息<br>
	 */
	String getAllConversionState();
	/**
	 * 创建Conversionstate
	 * 
	 * @param conversionstate
	 * @return 创建conversionstate的sql语句
	 */
	String saveConversionState(ConversionState ConversionState);

	/**
	 * 通过序号查询conversionstate
	 * 
	 * @param id
	 * @return 查询conversionstate的sql语句 <br>
	 *         int_id 序号 <br>
	 *         INT_FROMSTATE 原状态号<br>
	 *         INT_TOSTATE 跳转状态号<br>
	 *         STR_DESC 描述信息<br>
	 */
	String getConversionStateById(int id);

	/**
	 * 通过序号删除conversion
	 * 
	 * @param id
	 * @return 删除conversionstate的sql语句
	 */
	String delConversionStateById(int id);

	/**
	 * 更新conversionstate对象，通过其id
	 * 
	 * @param conversionstate
	 * @return 更新conversionstate的sql语句
	 */
	String updateConversionState(ConversionState ConversionState);
	/**
	 * 通过描述查询conversionstate
	 * 
	 * @param str_desc
	 * @return 查询特定规则的sql语句
	 */
	 String getConversionStateByDesc(String str_desc);

	 /**
	  * 通过生产单元ID查询状态规则
	  * 
	  * @param ProduceUnitId 生产单元号
	  * @return
	  */
	 public String getConversionStateByProdUnitId(int ProduceUnitId);


}
