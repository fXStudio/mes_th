package mes.ra.dao;

import mes.ra.bean.State;

public interface DAO_State {
	/**
	 * 创建state
	 *  徐嘉
	 * @param state
	 * @return 创建state的sql语句
	 */
	String saveState(State state);

	/**
	 * 通过序号查询state
	 *  徐嘉
	 * @param id
	 * @return 查询state的sql语句 <br>
	 *         int_id 序号 <br>
	 *         Str_statename 状态名<br>
	 *         Str_style 样式<br>
	 *         Int_delete 删除标识<br>
	 *         Str_styledesc 样式描述<br>
	 */
	String getStateById(int id);

	/**
	 * 通过序号删除gather
	 *  徐嘉
	 * @param id
	 * @return 删除state的sql语句
	 */
	String delStateById(int id);

	/**
	 * 更新state对象，通过其id属性
	 *  徐嘉
	 * @param state
	 * @return 更新state的sql语句
	 */
	String updateState(State state);

	/**
	 * 查询全部state对象
	 *  徐嘉
	 * @return 查询全部state的sql语句<br>
	 *         int_id 序号 <br>
	 *         Str_statename 状态名<br>
	 *         Str_style 样式<br>
	 *         Int_delete 删除标识<br>
	 *         Str_styledesc 样式描述<br>
	 */
	String getAllState();

    /**
	 * 倒序查询全部state对象
	 * 徐嘉
	 * @return 倒序查询全部state的sql语句<br>
	 *         int_id 序号 <br>
	 *         Str_statename 状态名<br>
	 *         Str_style 样式<br>
	 *         Int_delete 删除标识<br>
	 *         Str_styledesc 样式描述<br>
	 */
	public String getAllStateDESC();
	
	/**
	 * 通过名称查询state
	 *  徐嘉
	 * @param stateName
	 * @return 查询特定规则的sql语句
	 */
	String getStateByName(String StateName);

	/**
	 * 检测produceunit表中是否有此状态号的关联
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	String checkProduceUnitById(int id);
	
	/**
	 * 检测state表中是否有此状态号的关联
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	String checkStateById1(int id);
	/**
	 * 检测state表中是否有此状态号的关联
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	String checkStateById2(int id);

	/**
	 * 验证状态名称为name的状态是否已经创建
	 *  徐嘉
	 * @param statename
	 * @return 顺序号个数
	 */
	String checkStateByName(String StateName);


	
	
	
	
	
	/**
	 * 查看所有的状态装换关系
	 * 
	 * 谢静天
	 */
	String getAllStateConversion();
	
	/**
	 *创建采集点状态规则
	 * 
	 * 谢静天
	 */
	String saveGatherStateRule(int int_gatherid,int Stateconversionid,int defaultgo);
	/**
	 *通过采集点的id来查询与采集点的有关的状态的id号
	 * 
	 * 谢静天
	 */
	String getstateIdbygatherid(int int_gatherid);
	/**
	 * 通过采集点的id查看状态装换关系
	 * 
	 * 谢静天
	 */
	String getgatherStateRule(int int_gatherid);
	/**
	 * 通过采集点的id查看默认状态装换关系号
	 * 
	 * 谢静天
	 */
	String getconversionidBy(int int_gatherid);
	/**
	 * 先删除采集点的规则
	 * 
	 * @param int_gatherid 采集点的id
	 *  谢静天
	 * @return 查询序号的sql语句
	 */
	  String delgatherstaterule(int int_gatherid);
	
}
