package com.qm.mes.os.dao;
import com.qm.mes.os.bean.*;
public interface DAO_WorkSchedle {

	/**创建工作时刻表
	 * author : 包金旭
	 */
	String saveWorkSchedle(WorkSchedle  workSchedle);
	
	/**获得所有工作时刻表
	 * author : 包金旭
	 */
	String getAllWorkSchedle();
	
	/**通过ID删除工作时刻表
	 * author : 包金旭
	 */
	String deleteWorkSchedleById(int id);
	
	/**通过ID获得工作时刻表
	 * author : 包金旭
	 */
	String getWorkSchedleById(int id);
	
	/**修改工作时刻表
	 * author : 包金旭
	 */
	String updateWorkSchedle(WorkSchedle  workSchedle);
	
	/**获得工作时刻表中生产单元号
	 * author : 包金旭
	 */
	String getprodunitid();
	/**获得工作时刻表中班次
	 * author : 包金旭
	 */
	String getworkOrder();
	
	/**除去本ID查询获得工作时刻表中生产单元号用于更改判断
	 * author : 包金旭
	 */
	String getprodunitidById(int id);
	
	/**除去本ID查询获得工作时刻表中班次用于更改判断
	 * author : 包金旭
	 */
	String getworkOrderById(int id);
	
	/**获得所有提前期
	 * author : 包金旭
	 */
	String getAllAdvanceTime();
	
	/**通过ID查询生产单元班次用于判断删除条件
	 * author : 包金旭
	 */
	String getProdunitidOrderById(int id);
	
	 /**通过生产单元班次查询工作时刻表用于删除班次做判断
	 * author : 包金旭
	 */
	String getSchedleByProdunitidOrder(int Produnitid,String Order);
	
	/**
	 *通过生产单元的id来获取班次信息 谢静天
	 */
	String getworkOrderByprodunitid(int id);
	//
	/**
	 *通过生产单元班次来查询开工时间和提前期  谢静天
	 */
	String getworkschedleadtime(int produnitid,String workorder);
	/**通过生产单元查询开工时间
	 * author : 包金旭
	 */
	String getWorkSchedelByProuunitid(int str_produceunit);
	
	/**除本ID通过生产单元查询开工时间
	 * author : 包金旭
	 */
	String getWorkSchedelByProuunitidAndID(int str_produceunit,int int_id);
	

	
}
