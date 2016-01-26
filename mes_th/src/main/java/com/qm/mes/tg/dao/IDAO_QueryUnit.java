package com.qm.mes.tg.dao;

public interface IDAO_QueryUnit {
	/**
	 * 生产路径查询
	 * 
	 * @param materielvalue
	 *            主物料标识规则值 <br>
	 * @return str_materielValue 主物料标识值<br>
	 *         dat_Date 过点时间<br>
	 *         str_date 特定格式时间<br>
	 *         STR_MATERIELNAME 物料标识规则名<br>
	 *         INT_GATHERID 采集点号<br>
	 *         INT_USERID 登录用户号
	 */
	String QueryProducePathByMaterielValue(String materielvalue);

	/**
	 * 生产单元完工查询
	 * 
	 * @param produnitId
	 *            生产单元id(可以包含其它查询条件，默认是当日完工)进行查询 <br>
	 * @param strDate
	 *            开始日期
	 * @param endDate
	 *            终止日期
	 * @return str_materielValue 主物料标识值<br>
	 *         dat_Date 过点时间
	 */
	String QueryProduceUnitComplete(int produnitId, String strDate,
			String endDate);

	/**
	 * 产品谱系查询<br>
	 * （查询结果树状显示，如果它的子物料仍是其它产品的主物料，将递归查出并显示） <br>
	 * 
	 * @param materielvalue
	 *            主物料标识规则号 <br>
	 * @return gr.str_materielValue 主物料标识规则值<br>
	 *         pr.str_materielValue 子物料标识规则值<br>
	 *         gr.DAT_DATE 过点时间<br>
	 *         str_date 物料格式的过点时间<br>
	 *         gr.int_gatherid 采集点id<br>
	 *         ga.str_name 采集点名<br>
	 *         pr.str_materielname 子物料标识值的物料标识规则名<br>
	 */
	String QueryProductRecordByMaterielValue(String materielvalue);

	/**
	 * 产品谱系查询是的主物料标识规则号的详细内容<br>
	 * （查询结果树状显示，如果它的子物料仍是其它产品的主物料，将递归查出并显示） <br>
	 * 
	 * @param materielvalue
	 *            主物料标识规则号 <br>
	 * @return str_materielValue 主物料标识规则值<br>
	 *         DAT_DATE 过点时间<br>
	 *         str_date 物料格式的过点时间<br>
	 *         int_gatherid 采集点id<br>
	 *         ga.str_name 采集点名<br>
	 *         gr.str_materielname 主物料标识值的物料标识规则名<br>
	 * 
	 */
	String QueryProductRecord_MaterialValue(String materialvalue);

	/**
	 * 在线产品查询<br>
	 * 查出当日通过起始采集点而没通过终止采集点的主物料的信息。<br>
	 * （查询条件：日期。结果将显示经过上线采集点并没有经过终止采集点的主物料的信息）
	 * 
	 * @param date
	 *            日期 <br>
	 * @param gatherid
	 *            采集点id
	 * @return gr.INT_ID 过点记录id<br>
	 *         gr.INT_GATHERID 采集点id<br>
	 *         gr.STR_MATERIELVALUE 主物料标识值<br>
	 *         gr.INT_USERID 用户<br>
	 *         gr.DAT_DATE 过点时间<br>
	 *         gr.STR_MATERIELNAME 物料标识规则名<br>
	 *         ga.str_name 采集点名<br>
	 * 
	 */
	String QueryOnlineProduct(String date, int gatherid);

	/**
	 * 查询从开始日期到结束日期之间的天数
	 * 
	 * @param date1
	 *            开始日期<br>
	 * @param date2
	 *            结束日期<br>
	 * @return
	 * @deprecated
	 */
	String QueryDays(String date1, String date2);

	/**
	 * 生产路径查询
	 * 
	 * @return str_materielValue 主物料标识值<br>
	 *         dat_Date 过点时间<br>
	 *         str_date 特定格式时间<br>
	 *         STR_MATERIELNAME 物料标识规则名<br>
	 *         INT_GATHERID 采集点号<br>
	 *         INT_USERID 登录用户号
	 * @deprecated 页面上不用此方法了
	 */
	String QueryAllProducePath();
	/**
	  * 徐嘉
	  * 生产单元完工查询中用来查首末状态	 
	  * @return sql
	  * @prama 生产单元
	  */
    String QueryAllstate(int pruduceid);
    /**
	  * 徐嘉
	  * 从指令表得到符合生产单元、指令状态、起止日期的信息
	  * @return sql
	  * @prama 生产单元、状态、日期
	  */
    String QueryAllinstruction(int produnitId, String wh,String strDate,String endDate);

}
