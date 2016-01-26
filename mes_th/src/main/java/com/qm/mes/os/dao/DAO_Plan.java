package com.qm.mes.os.dao;
import com.qm.mes.os.bean.*;
/**
 * 
 * @author  谢静天
 *
 */

public interface DAO_Plan {
	/** 
	* 创建作业计划  谢静天
      */
	 String savePlan(Plan plan);
     /** 
  	* 按照生产单元，生产日期，和班次查找作业计划 最大的版本信息 谢静天
      */
	 String getPlanbyProdateProidWorder(String produceDate,int produnitid,String workOrder );
    /** 
    * 通过版本号得到作业计划  谢静天
      */
	 String getPlanbyversioncord(String versioncode);
      /** 
      * 删除指定版本号的作业计划  谢静天
       */
	  String deletePlanbyversioncode(String versioncode);
      /** 
      * 通过版本int_id  得到版本计划信息  谢静天
      */
	  String getPlanbyversionid(int int_id);
      /** 
      * 查询一段时间内的作业计划 谢静天
      */
	  String getplanbybystarttimeandendtimeproduceunitoverendtime(int produnitid,String workOrder,String overtime,String endtime);
      /** 
      * 修改指定id值的计划  谢静天
      */
	  String updatePlanbyid(Plan plan);
      /** 
      * 得到指定id的计划   谢静天
      */
	  String getplanbyid(int id );
      /** 
      * 删除生产单元，生产日期，和班次  顺序号的作业计划 谢静天
      */ 
	  String deleteplanbyPlanOrder(String produceDate,int produnitid,String workOrder,int planorder);
      /** 
      * 调整指令顺序方法  谢静天
      */ 
	  String updatePlanOrder(String produceDate,int produnitid,String workOrder,int planorder);
       /** 
       * 下移或者上移 计划顺序通过计划的id  和互换的顺序号谢静天
       */
	  String down_or_upPlanOrderbyplanid(int id,int nextorder);
	
      /** 
      * 撤销编辑计划 谢静天
      */ 
	  String disfrockplan(String produceDate,int produnitid,String workOrder);
      /** 
      * 提交编辑计划 谢静天
      */ 
	  String submitplan(String produceDate,int produnitid,String workOrder,String versioncode);
      /** 
      *查询提交的版本号 谢静天
      */
	  String getversioncodewhensubmit(String produceDate,int produnitid,String workOrder);
      /** 
      * 发布作业时只能看到最大的版本并且版本号不为temp 谢静天
      */ 
	  String getmaxPlanexcepttemp(String produceDate,int produnitid,String workOrder);
      /** 
       * 发布作业计划 谢静天
       */
	  String uploadplan(String produceDate,int produnitid,String workOrder);
      /** 
       * 取消作业计划发布 谢静天
      */
    String  canceluploadplan(String produceDate,int produnitid,String workOrder);
     /** 
      * 查询不同生产日期生产单元班次的最大版本信息 谢静天
     */
    String geteverydaymaxversioncodeplan();
     /** 
      * 替换时不核对被替掉内容的作业计划  谢静天
      */
    String geteverydaymaxversioncodeplanexceptnow(String produceDate,int produnitid,String workOrder);
     /** 
      * 查询不同生产日期生产单元班次的最大版本信息 中是否有要查找的主物料值 谢静天
      */
    String getmaterielfromeditplan(String produceDate,int produnitid,String workOrder,String materiel);
      /** 
     * 编辑页面查找记录 版本号用temp  谢静天
      */ 
    String getplancontainstemp(String produceDate,int produnitid,String workOrder);
     /** 
     * 核对是否存在order的计划顺序号 谢静天
     */
    String checkplanorder(String produceDate,int produnitid,String workOrder,int order);

    /**通过id查询生产单元班次用于检查工作时刻表删除条件
	 * author : 包金旭
	 */
	String getAllPlanByProdunitidOrder(int Produnitid,String Order);
	/** 
	* 计划生成指令时 没发布的计划调度员看不见。谢静天
      */
	String getmaxPlanexcepttempupload(String produceDate,int produnitid,String workOrder);
	/**
	 * @author 谢静天
	 * 在计划发布时做主物料验证，发布的最大版本的生产单元主物料是否重复。
	 *
	 */
	String getplanbymaterielproid_upload_andmaxversion(String materiel,int produnitid);

}
