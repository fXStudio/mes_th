/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.ra.dao;

import java.util.List;

import com.qm.mes.ra.bean.Instruction;

/**
 *
 * @author YuanPeng
 */
public interface DAO_Instruction {

	  /**
     * 通过序号查出指令的sql语句
     *袁鹏
     * @param id
     * @return T_RA_INSTRUCTION中该ID的所有字段
     */
    String getInstructionById(int id);

    /**袁鹏
     * 对指令进行更新操作的sql语句
     *
     * @param instruction
     * @return 更新T_RA_INSTRUCTION中该ID的记录
     */
    String updateInstruction(Instruction instruction);

    /**袁鹏
     * 查找比该顺序号小的顺序的指令
     * 
     *@param ProduceUnitID
     *		生产单元
     *@param str_date
     *		生产日期
     * @param Int_instructOrder
     *                      指令顺序
     * @return 比该顺序号小的顺序的指令
     */
   
    public String getByOrderMinus(int ProduceUnitID, String str_date,String workOrder,int Int_instructOrder);

    /**袁鹏
     * 查找比该顺序号大的顺序的指令
     *
     *@param ProduceUnitID
     *		生产单元
     *@param str_date
     *		生产日期
     * @param Int_instructOrder
     *                      指令顺序
     * @return 比该顺序号小的顺序的指令
     */
    public String getByOrderPlus(int ProduceUnitID, String str_date,String workOrder,int Int_instructOrder);

   /**袁鹏
   * 通过生产单元号、生产日期、状态号查询作业指令
   *
   * @param Int_produnitid
   *            生产单元号
   * @param str_date
   *            生产日期
   * @param stateid
   *            状态号
   * @return 通过生产单元号、生产日期、状态号查询的作业指令
   */
    public String getInstructionByProUnitProDateStateOrder(int Int_produnitid,String str_date,String workOrder,int stateid);

    /**袁鹏
     * 通过作业指令序号更改Int_edit为1
     *
     * @param id
     *      作业指令序号
     * @return 通过作业指令序号更改Int_edit为1
     */
    public String editInstructionById(int id);

    /**袁鹏
     * 通过作业指令序号更改Int_edit为0
     *
     * @param id
     *      作业指令序号
     * @return 通过作业指令序号更改Int_edit为0
     */
    public String uneditInstructionById(int id);

/** 袁鹏
    * 创建指令
    *
    * @param instruction 
    * @return
    */
public String saveInstruction(Instruction instruction);

/**
* 通过生产单元号、生产日期、顺序号大于首个非编辑号来更改int_delete字段值为1
*  袁鹏
* @param ProduceUnitID
* @param str_date
* @param UnlockStartOrder
* @return
*/
public String DeleteInstructionByProUnitDateworkOrderUnlockOrder(int ProduceUnitID, String str_date, String workOrder,int UnlockStartOrder);
/**
* 通过生产单元号、生产日期查询记录数量
*  袁鹏
* @param ProduceUnitID
* 			生产单元号
* @param str_date
* 			生产日期
* @return 返回通过生产单元号、生产日期查询记录的数量
*/
public String getCountByProUnitDateOrder(int ProduceUnitID, String str_date,String str_workOrder);

/**
* 将指定ID的指令更改为已发布状态
* 
* @param id
* 		作业指令序号
* @return
*/
public String issuanceInstruction(int id );

/**
 * 通过生产单元号和生产日期查询未被删除的指令
 * 
 * @param produnitid
 * 			生产单元号
 * @param str_date
 * 			生产日期
 * @return 指令集合
 */
	public String getInstructionsByProdunitDateOrder(int  produnitid,String str_date,String str_workorder);
    
	/**
	 * 判断该生产单元、生产日期、产品标识的指令数量
	 * 
	 * @param produnitid
	 * @param str_date
	 * @param marker
	 * @return
	 */
	public String getCountByProUnitDateWorkorderMarker(int  produnitid,String str_date,String workOrder,String marker);
	
	/**
	* 通过生产单元号、生产日期查询记录
	* 袁鹏
	* @param ProduceUnitID
	* 			生产单元号
	* @param str_date
	* 			生产日期
	* @param List<Integer> list_state 状态集合
	* @return 返回通过生产单元号、生产日期查询记录
	*/
	public String getCountByProUnitDateState(int ProduceUnitID,List<Integer> list_state);
   
    
    
    
    /**
     * 修改指令状态
     * 谢静天
     * @param structStateID 状态id
     *  @param staError 状态跳转的异常标识
     *  @param producemarker 产品标识
     *  produnitid 生产单元的id
     * @return  返回于该生产单元相对应的指令
     */
    String updateInstructState(int structStateID,int staError,String producemarker,int produnitid);
    /**
     * 查看单个指令状态
     * 谢静天
     * 
     *  @param producemarker 产品标识
     *  produnitid 生产单元的id
     * @return  返回相对应的指令
     */
    String checkstr_produceMarker(String str_produceMarker,int produnitid);
    /**
     * 指令状态出现异常时要保存出现的现在
     * 谢静天
     * 
     *  @param producemarker 产品标识
     *   @param userId用户id
     *    @param Int_instructionStateID状态id
     *     @param produceUnit生产单元
     * @return  返回相对应的指令
     */
    String savet_ra_Instructionexception(int Int_instructionStateID,int userId,String produceMarker,int INT_GATHERID);
    /**
     * 查看产品标识如果页面输入的主物料值在指令表中看是否存在。
     * 谢静天
     * 
     *  @param producemarker 产品标识
     * @return  返回相对应的指令
     */
    
    String checkmaterielValue(String producemarker);
    /**
     * 根据生产时间和生产单元和班次查看相关的指令。
     * 谢静天
     * 
     *  @param  int_produnitid,String str_date
     * @return  返回相对应的指令
     */
    
    
    String getInstructionByProduceUnitProduceDateWorkorder(int int_produnitid,String str_date,String workOrder);
    String getInstructionByProduceUnitProduceDate(int int_produnitid,String str_date);
    /**
     * 更新指令版本
     * 谢静天
     * 
     * 
     *
     */
    
    String updateInstructionVersioncode(Instruction instuction,String str_versioncode);
    /**
     * 核对是否可以进行版本保存
     * 谢静天
     * 
     *  
     *
     */
    String checksaveVersion(int int_produnitid,String str_date,String workOrder);
    /**
     * 根据生产时间和生产单元和恢复状态查看相关的指令进行版本恢复
     * 谢静天
     * 
     *  
     *
     */
    String comebackVersion(int int_produnitid,String str_date,int int_stateid);
    
    /**
     * 根据生产时间和生产单元删除指令
     * 谢静天
     * 
     * 
     *
     */
    String deleteInstructionByProduceUnitProduceDateWorkorder(int int_produnitid,String str_date,String workOrder);
    
    
    /**
     * 通过采集点id和系统时间查询相关的指令。
     * 谢静天
     */
    String getGatherwork(int gatherid);
 
    /**
     * 查找指令最大的顺序号
     * 进行指令追加
     * 谢静天
     */
    String getInstructionMaxOrder(int int_produnitid,String str_date,String workOrder);
    /**
     * 通过从开始时间到结束时间及生产单元查询相关的指令
     * 谢静天
     */
   String getInstructionByStartAndEndProduceunitOrder(int produceid,String starttime,String endtime,String workorder);
   /**
    * 查看生产单元和生产日期的主物料值
    * 用于指令导入时判断
    * 谢静天
    */
   String getStr_produceMarkerbyproduceUnitandproduceDate(int produceid,String str_date);
  /**
   *  谢静天
   *  核对主物料标识规则
   *  指令导入时用
   */
   String checkinstructionmateriel(int produnitid);
   /**
    * 谢静天
    * 生产单元和生产时间
    * 通过主物料的值查看指令是否处于编辑状态。
    */
   String checkinstructionedit(String producemarker ,int produnitid,String str_date);
   
   /**
    * 查看生产单元和生产日期的主物料值
    * 用于指令导入时判断
    * 
    */
   public  String getInstructionsbyproduceUnit(int produceid);
   
   /**
    * 查询当前生产单元、非当前生产日期的指令对象
    * 
    * @param produnitid
    * @param str_date
    * @return
    */
   public String getInstructionsByProdunitOtherProdDate(int produnitid,String str_date);

    /**得到指令表中所有的指令 谢静天
     * @return
     */
    String getallInstruction();
    
   /**查找指定主物料的指令 谢静天
    * @param materile
    * @return
    */
    public String getInstructionbymaterile(String materile,int produceid);
   /**
    * 徐嘉
    * 导出语句
    * 为了兼容两个数据库
    */
   public String export(int produceid,String starttime,String endtime,String workorder);
   
    
   /**
    * 谢静天核对生产单元是否可以删除条件
    */
   public String checkproducedelete(int produceid,String date);
   
   /**
    * 核对指令表中是否有指令用于删除
    * 包金旭
    * 根据生产单元，生产日期及班次
    *  @param  instuction
    *
    */
   public String checkAllInstructionByProdunitidDateWorkorder(int int_produnitid,String str_date,String workOrder);
   
}

