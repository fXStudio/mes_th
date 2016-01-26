package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.Gather;

public class IDAO_GatherForOracle implements IDAO_Gather {
	/**
	 * 通过序号删除gather
	 * 
	 * @param id
	 *            序号
	 * @return 删除的sql语句
	 */
	public String delGatherById(int id) {
		String sql = "delete from t_tg_Gather where int_id=" + id;
		return sql;
	}

	/**
	 * 查询全部gather对象
	 * 
	 * @return 查询全部的sql语句
	 */
	public String getAllGather() {
		String sql="select g.*,p.str_name as producename,r.str_name as rulename"
    +" from t_tg_Gather g inner join t_ra_produceunit p on g.int_produnitid=p.int_id"
    +" inner join t_tg_materielrule r on g.int_materielruleid=r.int_id   ";
		return sql;
	}

    /**
	 * 倒叙查询全部gather对象
	 *
	 * @return 查询全部的sql语句
	 */
	public String getAllGatherDESC() {
		String sql = "select int_id,str_name,str_desc,int_produnitid,int_materielruleid "
				+ "from t_tg_Gather order by int_id desc";
		return sql;
	}

	/**
	 * 通过序号查询gather
	 * 
	 * @param id
	 *            序号
	 * @return 查询序号的sql语句
	 */
	public String getGatherById(int id) {
		String sql = "select int_id,str_name,str_desc,int_produnitid,int_materielruleid,startgo,compel from t_tg_Gather "
				+ "where int_id = " + id + " order by int_id";
		return sql;
	}

	/**
	 * 通过序号查询gather
	 * 
	 * @param str_name
	 *            采集点名
	 * @return 查询序号的sql语句
	 */
	public String getGatherByName(String str_name) {
		String sql = "select int_id,str_name,str_desc,int_produnitid,int_materielruleid from t_tg_Gather "
				+ "where str_name = '" + str_name + "' order by int_id";
		return sql;
	}

	/**
	 * 创建gather
	 * 谢静天修改增加了startgo,compel两个字段
	 * @param gather
	 *            实体对象
	 * @return 创建gather的sql语句
	 */
	public String saveGather(Gather gather) {
		String sql = "insert into t_tg_Gather(int_id,str_name,str_desc,int_produnitid,int_materielruleid,startgo,compel) "
				+ "values(seq_tg_Gather.nextval,'"
				+ gather.getName()
				+ "','"
				+ gather.getDesc()
				+ "',"
				+ gather.getProdunitId()
				+ ","
				+ gather.getMaterielruleId() 
				+","
				+ gather.getStartgo()
		        +","
		        +gather.getCompel()
				+ ")";
		
		return sql;
	}

	/**
	 * 更新gather对象，通过其id属性
	 * 
	 * @param gather
	 *            实体对象
	 * @return 更新的sql语句
	 * 谢静天修改增加了startgo,compel两个字段
	 */
	public String updateGather(Gather gather) {
		String sql = "update t_tg_Gather set str_name = '" + gather.getName()
				+ "' , str_desc = '" + gather.getDesc()
				+ "' , int_produnitid = " + gather.getProdunitId()
				+ " , int_materielruleid = " + gather.getMaterielruleId()
				+",startgo="+gather.getStartgo()
				+",compel="+gather.getCompel()
				+ " where int_id = " + gather.getId();
		return sql;
	}

	public String checkGatherItemCountByMaterialId(int gatherid, int materialId) {
		String sql = "select count(*) from  t_tg_Gather "
				+ " where INT_MATERIELRULEID=" + materialId + " and int_id="
				+ gatherid + "";
		return sql;
	}
	
	//--------------------------------------------------东阳项目添加---------------------------------------------------------------
	
	public String saveGather_Q(int g,int q,int order){
		 String sql = "insert into T_QM_R_GATHER_QUALITYSTATES(int_id,INT_GATHER_ID,INT_QUALITYSTATES_ID,int_order) "
				+ "values(seq_PM_DEVICE_UNIT.nextval,"
				+g+","+q+","+order+")";	   
		return sql;
	}
	
	public String delGather_Q(int g){
		String sql = "delete from T_QM_R_GATHER_QUALITYSTATES where int_gather_id=" + g;
		return sql;		
	}
}
