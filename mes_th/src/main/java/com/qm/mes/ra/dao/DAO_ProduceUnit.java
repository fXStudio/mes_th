package com.qm.mes.ra.dao;
import com.qm.mes.ra.bean.*;
/**
 * 
 * @author 谢静天
 *
 */
public interface DAO_ProduceUnit {
	
	/** 创建生产单元
	 * 谢静天
	 * param produceUnit;
	 */
	String saveProduceUnit(ProduceUnit produceUnit);
	/**
	 * 谢静天
	 * 得到所有的生产单元的信息
	 */
	String getAllProduceUnit();
	/**
	 * 谢静天
	 * 得到id号的生产单元的信息
	 */
   String getProduceUnitById(int id);
   /**
    * 
    * 谢静天
    * 更改生产单元信息
    * param  produceunit
    */
   String updateProduceUnit(ProduceUnit produceunit);
   /**
    * 
    * 通过id删除指定的生产单元
    * 逻辑删除
    * 谢静天
    */
   String deleteProduceUnitById(int id);
   /**
    * 通过名字查询相关的生产单元
    * 
    * 谢静天
    */
   String getProduceUnitByName(String name);
 /**
  * 通过生产单元的id查到生产单元的初始指令状态
  * 谢静天
  */
    String getInstructionstateIdByproduceunitid(int id);
    /**
	 * 谢静天
	 * 得到所有的生产单元的信息
	 * 查询结果包括被删除的生产单元。
	 */
	String getAllProduceUnitDESC();
	
	/**
	   * 通过物料标识规则号查询生产单元对象
	   * 
	   * @param materielurleid	物料标识规则号
	   * @return	生产单元数量
	   */
	  public String countProduceUnitByMateritelRuleID(int materielurleid);
	  
	  /**
	   * 倒叙查询所有生产单元的序号和名称
	   * 
	   * @return	倒叙查询所有生产单元的序号和名称的集合
	   */
	  public String getAllProdUnitIdNamesByDesc();
	  
	  //--------------------------------------------------东阳项目添加---------------------------------------------------------------
	  /**
	   * 根据父生产单元查找子生产单元
	   * @param id
	   * @return
	   */
	  String getCunitByid(int id);
	  
	  /** 删除子生产单元信息
	  * @param id
	  * @return
	  */
	  String delCunitByid(int id);
	  
	  /** 添加子生产单元信息
	  * @param id
	  * @param CunitId
	  * @return
	  */
	 String addCunit(int id, String CunitId);
}
