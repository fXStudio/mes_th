package mes.tg.dao;

import mes.tg.bean.Gather;

public interface IDAO_Gather {

	/**
	 * 创建gather
	 * 
	 * @param gather
	 *            实体对象
	 * @return 创建gather的sql语句
	 */
	String saveGather(Gather gather);

	/**
	 * 通过序号查询gather
	 * 
	 * @param id
	 *            序号
	 * @return 查询序号的sql语句 <br>
	 *         int_id 采集点号 <br>
	 *         str_name 采集点名<br>
	 *         str_desc 描述信息<br>
	 *         int_produnitid 生产单元号<br>
	 *         int_materielruleid 物料号
	 */
	String getGatherById(int id);

	/**
	 * 通过序号删除gather
	 * 
	 * @param id
	 *            序号
	 * @return 删除的sql语句
	 */
	String delGatherById(int id);

	/**
	 * 更新gather对象，通过其id属性
	 * 
	 * @param gather
	 *            实体对象
	 * @return 更新的sql语句
	 */
	String updateGather(Gather gather);

	/**
	 * 查询全部gather对象
	 * 
	 * @return 查询全部的sql语句<br>
	 *         int_id 采集点号 <br>
	 *         str_name 采集点名<br>
	 *         str_desc 描述信息<br>
	 *         int_produnitid 生产单元号<br>
	 *         int_materielruleid 物料号
	 */
	String getAllGather();
	
    /**
	 * 倒叙查询全部gather对象
	 *
	 * @return 查询全部的sql语句<br>
	 *         int_id 采集点号 <br>
	 *         str_name 采集点名<br>
	 *         str_desc 描述信息<br>
	 *         int_produnitid 生产单元号<br>
	 *         int_materielruleid 物料号
	 */
	String getAllGatherDESC();

	/**
	 * 通过序号查询gather
	 * 
	 * @param str_name
	 *            采集点名
	 * @return 查询序号的sql语句
	 */
	String getGatherByName(String str_name);
	
	/**
	 * 检测采集点属性gather表中是否已有此物料标识规则号
	 * 
	 * @param materialId
	 *            主物料标识规则号 <br>
	 * @param gatherid
	 *            采集点号 <br>
	 * @return 顺序号个数
	 */
	String checkGatherItemCountByMaterialId(int gatherid, int materialId);

	//--------------------------------------------东阳项目添加----------------------------------------------------------------------
	
	/**
	 * 添加采集点和质量的关系
	 * @param g
	 * @param q
	 * @param order
	 * @return
	 */
	String saveGather_Q(int g,int q,int order);
	
    /**
     * 删除采集点和质量关系
     * @param g
     * @return
     */
    String delGather_Q(int g);
} 
