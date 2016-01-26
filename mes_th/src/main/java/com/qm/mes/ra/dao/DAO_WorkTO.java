package com.qm.mes.ra.dao;

import com.qm.mes.ra.bean.WorkTO;

public interface DAO_WorkTO {
	
	/**创建班组班次记录表
	 * author : 包金旭
	 */
	String saveWorkTO(WorkTO workto);
	
	/**通过ID获得班组班次记录表
	 * author : 包金旭
	 */
	String getWorkTOById(int id);
	
	/**获得所有班组班次记录表
	 * author : 包金旭
	 */
	String getAllWorkTO();
	
	/**修改班组班次记录表
	 * author : 包金旭
	 */
	String updateWorkTO(WorkTO  workto);
	
	/**通过ID删除班组班次记录表
	 * author : 包金旭
	 */
	String deleteWorkTOById(int id);

	/**获得班组班次记录表中生产单元号
	 * author : 包金旭
	 */
	
	String getprodunitid();
	
	
	
	/**获得班组班次记录表中班次
	 * author : 包金旭
	 */
	String getworkOrder();
	
	/**除去本ID查询获得班组班次记录表中生产单元号用于更改判断
	 * author : 包金旭
	 */
	String getprodunitidById(int id);
	
	
	
	/**除去本ID查询获得班组班次记录表中班次用于更改判断
	 * author : 包金旭
	 */
	String getworkOrderById(int id);
	
	/**通过生产单元的id来获取班组班次信息 谢静天
	 * @param id
	 * @return
	 */
	String getworkOrderByprodunitid(int id);
	
	
	
}








