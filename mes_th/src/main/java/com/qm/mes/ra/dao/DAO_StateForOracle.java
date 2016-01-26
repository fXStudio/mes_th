package com.qm.mes.ra.dao;

import com.qm.mes.ra.bean.State;


public class DAO_StateForOracle implements DAO_State {
	/**
	 * 创建state
	 * 徐嘉
	 * @param state
	 * @return 创建state的sql语句
	 */
	public String saveState(State state) {
		String sql = "insert into t_ra_state(int_id,str_statename,str_style,int_delete,str_styledesc) "
				+ "values(seq_ra_State.nextval,'"
				+state.getStateName()
				+"','"+state.getStyle()+"',"+state.getDelete()+",'"+state.getStyledesc()+"')";
	   
		return sql;
	}

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
	public String getStateById(int id) {
		String sql = "select int_id,str_statename,str_style,int_delete,str_styledesc from t_ra_state "
				+ "where int_id = " + id + " order by int_id";
		return sql;
	}

	/**
	 * 通过序号删除gather
	 *  徐嘉
	 * @param id
	 * @return 删除state的sql语句
	 */
	public String delStateById(int id) {
		String sql = "delete from t_ra_State where int_id=" + id;
		return sql;
	}

	/**
	 * 更新state对象，通过其id属性
	 *  徐嘉
	 * @param state
	 * @return 更新state的sql语句
	 */
	public String updateState(State state) {
		String sql = "update t_ra_state set str_statename ='"
				+state.getStateName() + "' , str_style = '"+ state.getStyle()
				+ "',str_styledesc ='" + state.getStyledesc()
				+ "', int_delete = " +state.getDelete() + " where int_id = "
				+ state.getId();
		return sql;
	}

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
	public String getAllState() {
		String sql = "select int_id,str_statename,str_style,int_delete,str_styledesc"
				+ " from t_ra_state ";
		
		return sql;
	}

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
	public String getAllStateDESC() {
		String sql = "select int_id,str_statename,str_style,int_delete,str_styledesc  "
				+ "from t_ra_state where int_delete <> 1 order by int_id DEsc";
		return sql;
	}

	/**
	 * 通过名称查询state
	 *  徐嘉
	 * @param stateName
	 * @return 查询特定规则的sql语句
	 */
	public String getStateByName(String stateName) {
		String sql = "select int_id,str_statename,str_style,int_delete,str_styledesc from t_ra_state"
				+ " where str_statename='" + stateName +"'order by int_id";
		
		return sql;
	}

	/**
	 * 验证单元是否已经创建
	 *  徐嘉
	 * @param id
	 * @return 顺序号个数
	 */
	public String checkProduceUnitById(int id) {
		String sql = "select count(*) from  t_ra_state a, t_ra_produceunit b"
			+ " where a.int_id=" + id
			+ " and a.int_id = b.int_instructstateid and b.int_delete=0";
	return sql;

	}
	/**
	 * 验证状态是否已经创建
	 *  徐嘉
	 * @param id
	 * @return 顺序号个数
	 */
	public String checkStateById1(int id) {
		String sql = "select count(*) from  t_ra_state a, t_ra_stateconversion b"
			+ " where a.int_id=" + id
			+ " and a.int_id = b.int_fromstate";
	return sql;

	}
	/**
	 * 检测state表中是否有此状态号的关联
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	public String checkStateById2(int id){
		String sql = "select count(*) from  t_ra_state a, t_ra_stateconversion b"
			+ " where a.int_id=" + id
			+ " and a.int_id = b.int_tostate";
	return sql;

	}

	/**
	 * 验证状态名称为name的状态是否已经创建
	 *  徐嘉
	 * @param statename
	 * @return 顺序号个数
	 */
	public String checkStateByName(String StateName) {
		String sql = "select count(*) from  t_ra_state " + " where str_name="
				+ StateName ;
		return sql;

	}

	
	
	
	
	
	
	/**
	 * 查看所有的状态装换关系
	 * 
	 * 谢静天
	 */
	public String   getAllStateConversion(){
		String sql="select a.int_id ,a.int_Fromstate,a.int_tostate ,b.STR_STATENAME as from1,c.STR_STATENAME as to1  from t_ra_stateconversion a"
			+" left join t_ra_state b on b.int_id = a.INT_FROMSTATE"
            +" left join t_ra_state c on c.int_id = a.INT_toSTATE  order by a.int_id asc";
		return sql;
	}
	/**
	 * 通过采集点的id查看状态装换关系
	 * 
	 * 谢静天
	 */
	public String getgatherStateRule(int int_gatherid){
		String sql="select a.int_id ,a.int_Fromstate,a.int_tostate ,b.STR_STATENAME as from1,c.STR_STATENAME as to1  from t_ra_stateconversion a"
			+" left join t_ra_state b on b.int_id = a.INT_FROMSTATE"
			+" left join t_ra_state c on c.int_id = a.INT_toSTATE "
		    +"where a.int_id in ( select  int_stateconversionid  from t_ra_gatherstaterule where  int_gatherid="+int_gatherid
		    +" )";
		
		return sql;
		
	}
	
	/**
	 *插入采集点状态规则
	 * 
	 * 谢静天
	 */
	public String saveGatherStateRule(int int_gatherid,int Stateconversionid,int defaultgo){
		String sql="insert into t_ra_gatherstaterule(int_id,int_gatherid,int_stateconversionid,defaultexcute)"
			+" values(seq_ra_gatherstaterule.nextval,"
			+ int_gatherid 
			+","
			+ Stateconversionid 
			+","
			+ defaultgo
			+")";
		
		return sql;
		
		
		
	}
	/**
	 *通过采集点的id来查询与采集点的有关的状态的id号
	 * 
	 * 谢静天
	 */
	public String getstateIdbygatherid(int int_gatherid){
		String sql="select sc.int_fromstate ,sc.int_tostate,gsr.defaultexcute,g.startgo,g.compel from t_ra_gatherstaterule gsr,t_ra_stateconversion sc,t_tg_gather g"
			+" where gsr.int_stateconversionid=sc.int_id and gsr.int_gatherid=g.int_id and gsr.int_gatherid="+int_gatherid;
		
		
		return sql;
	}
	/**
	 * 通过采集点的id查看默认状态装换关系号
	 * 
	 * 谢静天
	 */
	public String getconversionidBy(int int_gatherid){
		String sql="select int_stateconversionid from t_ra_gatherstaterule where defaultexcute=1 and int_gatherid="+int_gatherid;
		return sql;
	}
	/**
	 * 先删除采集点的规则
	 * 
	 * @param int_gatherid 采集点的id
	 *  谢静天
	 * @return 查询序号的sql语句
	 */
	public  String delgatherstaterule(int int_gatherid){
		String sql="delete t_ra_gatherstaterule where int_gatherid="+int_gatherid;
		return sql;
	}
}
