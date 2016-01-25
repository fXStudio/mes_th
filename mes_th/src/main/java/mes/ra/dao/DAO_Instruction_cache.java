/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mes.ra.dao;

import mes.ra.bean.Instruction;

/**
 *
 * @author YuanPeng
 */
public interface DAO_Instruction_cache {
    /**
    * 创建指令的sql语句
    *
    * @param instruction
    * @return
    */
    public String saveInstructionCache(Instruction instruction);

    /**
     * 通过序号查出指令的sql语句
     *
     * @param id
     * @return T_RA_INSTRUCTION中该ID的所有字段
     */
    String getInstructionCacheById(int id);

    /**
     * 通过序号删除指令的sql语句
     *
     *@param Int_produnitid
     *@param str_date
     *@param workOrder
     *@param order
     *@return 删除T_RA_INSTRUCTION中该order的记录
     */
    String delInstructionCacheByProduceUnitDateWorkorderOrder(int Int_produnitid, String str_date,String workOrder,int order);

    /**
     * 对指令进行更新操作的sql语句
     *
     * @param instruction
     * @return 更新T_RA_INSTRUCTION中该ID的记录
     */
    String updateInstructionCache(Instruction instruction);

     /**
     * 查询T_RA_INSTRUCTION中记录个数
     * @return T_RA_INSTRUCTION中记录个数
     */
    public String getInstructionCacheCount();

    /**
     * 查询T_RA_INSTRUCTION中所有记录
     *
     * @return 返回T_RA_INSTRUCTION中所有记录
     */
    public String getAllInstructionCache();
    
    /**
     * 查询T_RA_INSTRUCTION中所有记录按顺序排序
     *
     * @return 返回T_RA_INSTRUCTION中所有记录按顺序排序
     */
    public String getAllInstructionCacheByOrder();

    /**
     * 查找当前日期和生产单元的比该顺序号小的顺序的指令
     * 
     * @param Int_produnitid
     * 			生产单元ID
     * @param str_date
     * 			生产日期
     * * @param workOrder
     * 			班次
     * @param Int_instructOrder
     * 			作业指令顺序号
     * @return 作业指令对象
     */
    public String getByOrderMinus(int Int_produnitid, String str_date,String workOrder,int Int_instructOrder);

    /**
     * 查找当前日期和生产单元的比该顺序号大的顺序的指令
     * 
     * @param Int_produnitid
     * 			生产单元ID
     * @param str_date
     * 			生产日期
     * @param workOrder
     * 			班次
     * @param Int_instructOrder
     * 			作业指令顺序号
     * @return 作业指令对象
     */
    public String getByOrderPlus(int Int_produnitid, String str_date,String workOrder,int Int_instructOrder);

    /**
     * 通过生产单元号、生产日期、指令顺序号发布指令的sql语句
     *
     * @param Int_produnitid
     * 			生产单元号
     * @param str_date
     * 		生产日期
     * @param workOrder
     * 			班次
     * @param order
     * 		指令顺序号
     * @return 发布T_RA_INSTRUCTION中该生产单元号、生产日期、指令顺序号的记录
     */
    public String IssuanceByProduceUnitDateWorkorderOrder(int Int_produnitid, String str_date,String workOrder,int order);
    
    /**
     * 根据生产时间和生产单元班次查看相关的指令。
     *  @param  Str_produceUnit,String str_date,workOrder
     * @return  返回相对应的指令
     */
  public  String getInstructionCacheByProduceUnitProduceDateOrder(int Int_produnitid,String str_date,String workOrder);

  /**
   * 删除指令临时表中所有数据
   *
   * @return
   */
  public String DeleteInstructionCache();
   
  /**
   * 通过生产单元号、生产日期查询记录数量
   * 
   * @param ProduceUnitID
   * 			生产单元号
   * @param str_date
   * 			生产日期
   * @param workOrder
   * 			班次
   * @return 返回通过生产单元号、生产日期查询记录的数量
   */
   public String getCountByProUnitDateOrder(int ProduceUnitID, String str_date,String workOrder);
   
   /**
    * 删除该生产单元该班次所有临时指令
    * 
    * @param ProduceUnitID
    * 			生产单元号
    * @return 
    */
   public String DeleteInstructionCacheByProdUnitIdproducedateWorkorder(int ProduceUnitID,String str_date,String workOrder);
   
   /**
    * 通过生产单元号、生产日期、指令顺序号查询指令
    * 
    * @param ProduceUnitID
    * 			生产单元号
    * @param str_date
    * 			生产日期
    * @param workOrder
    * 			班次
    * @param Int_instructOrder
    * 				指令顺序号
    * @return 作业指令对象
    */
   public String getInstructionByProdUnitDateWorkorderOrder(int ProduceUnitID,String str_date,String workOrder,int Int_instructOrder);

   /**
    * 该生产日期、生产单元、大于该指令顺序号的指令顺序号减1
    * 
    *  @param  ProduceUnitID
    *  		生产单元号
    *  @param  str_date
    *  		生产日期号
    *  @param  workOrder
    *  		班次
    *  @param  order
    *  		指令顺序号
    * @return  返回sql
    */
    public  String MinusInstructionOrder(int ProduceUnitID,String str_date,String workOrder,int order);
   
    /**
     * 发布该生产单元、生产日期的所有指令
     * 
     * @param Int_produnitid
     * @param str_date
     * @param workOrder
     * @return
     */
    public String IssuanceAllByProduceUnitDateWorkorder(int Int_produnitid, String str_date,String workOrder);
   
    /**
     * 查询临时表最大顺序号
     * 
     * @param int_produnitid
     * @param str_date
     * @param workOrder
     * @return
     */
    public String getInstructionMaxOrder(int int_produnitid,String str_date,String workOrder);
    
    /**
 	 * 判断该生产单元、生产日期、产品标识的指令数量
 	 * 
 	 * @param produnitid
 	 * @param str_date
 	 * @param workOrder
 	 * @param marker
 	 * @return
 	 */
	public String getCountByProUnitDateWorkorderMarker(int  produnitid,String str_date,String workOrder,String marker);
    
	/**
 	 * 查询该生产单元、生产日期、产品标识的指令
 	 * 
 	 * @param produnitid
 	 * @param str_date
 	 * @param workOrder
 	 * @param marker
 	 * @return
 	 */
 	public String getInstructionByProUnitDateWorkorderMarker(int  produnitid,String str_date,String workOrder,String marker);
    

    /**
    * 查出指定指令号对应的id
    * 徐嘉
    *  @param  int_order,str_date,ProduceUnitID,workOrder
    * @return  返回sql
    */
    public  String selectInstructionCacheid(int int_order,String str_date,int ProduceUnitID,String workOrder);   

    
    /**
     * 查出指定指令号的数量
     * 徐嘉
     *
     *  @param  int_order,str_date,ProduceUnitID,workOrder
     * @return  返回sql
     */
     public  String selectInstructionCacheNum(int int_order,String str_date,int ProduceUnitID,String workOrder);
   	 /**
      * 更改指定指令号的数量
      * 徐嘉
      *
      *  @param  int_count,int_order
      * @return  返回sql
      */
      public  String updateInstructionCacheNum(int int_count,int int_order,String str_date,int ProduceUnitID,String workOrder);

      /**
       * 通过序号删除指令的sql语句
       * 徐嘉
       * @param order
       * @return 删除T_RA_INSTRUCTION中该order的记录（物理删除）
       */
      String deleteInstructionCacheByOrder(int order,String str_date,int ProduceUnitID,String workOrder);
      /**
       * 增加指定指令号
       * 徐嘉
       *
       *  @param  order
       * @return  返回sql
       */
       public  String updateInstructionCacheOrder(int order,String str_date,int ProduceUnitID,String workOrder);
       /**
        * 插入符合要求的指令
        * 徐嘉
        *
        *  @param  order,order1,count,str
        * @return  返回sql
        */
        public  String insertInstructionCache(int order1,String str,int count,int order);
        
        /**
         * 通过生产单元查询产品标识
         * 
         * @param ProduceUnitID
         * @return
         */
        public String selectproducemarker(int ProduceUnitID);

        
        /***
         *  谢静天查询编辑空间里面主物料的值 和生产单元
         */
        public String getInstructionbymateriel(String materiel,int int_produnitid);
}
