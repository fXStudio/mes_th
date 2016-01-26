/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.ra.dao;

import java.text.SimpleDateFormat;

import com.qm.mes.ra.bean.Instruction;

/**
 *
 * @author YuanPeng
 */
public class DAO_Instruction_cacheForOracle implements DAO_Instruction_cache{
	 /**
	    * 创建指令的sql语句
	    *
	    * @param instruction
	    * @return
	    */
    public String saveInstructionCache(Instruction instruction) {
        String sql = null;

        String sql1 = "insert into t_ra_Instruction_cache(int_id,Int_produnitid," +
                "Dat_produceDate,Str_versionCode,Int_instructOrder,Dat_planDate" +
                ",Int_planOrder,Str_produceType,Str_produceName,Str_produceMarker," +
                "Str_workOrder,Str_workTeam,Tim_planBegin,Tim_planFinish,Int_count," +
                "Int_instructStateID,Int_issuance,Int_StaError) "
				+ "values(seq_RA_INSTRUCTION_CACHE.nextval,"
				+ instruction.getProdunitid() + ","
				+ ("to_date('"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getProduceDate())+
                "','yyyy-mm-dd hh24:mi:ss')") + "," +
				(instruction.getVersionCode()==null||instruction.getVersionCode().equals("null")?"''":("'"+instruction.getVersionCode().toString()) + "'") + ","
				+ instruction.getInstructionOrder() + ","
				+(instruction.getPlanDate()==null||instruction.getPlanDate().toString().equals("null")
                ?"''":"to_date('"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getPlanDate())+
                "','yyyy-mm-dd hh24:mi:ss')") + ","
				+ instruction.getPlanOrder() + ","
				+ (instruction.getProduceType()==null||instruction.getProduceType().equals("null")?"":("'"+instruction.getProduceType()))+"'" + ",";
       String sql3 =
				(instruction.getProduceName()==null||instruction.getProduceName().equals("null")?"":("'"+instruction.getProduceName()+"'") )+ ","
				+ (instruction.getProduceMarker()==null||instruction.getProduceMarker().equals("null")?"''":("'"+instruction.getProduceMarker()+"'")) + ","
				+ (instruction.getWorkOrder()==null||instruction.getWorkOrder().equals("null")?"":("'"+instruction.getWorkOrder()+"'") )+ ","
				+ (instruction.getWorkTeam()==null||instruction.getWorkTeam().equals("null")?"":("'"+instruction.getWorkTeam()+"'") )+ ","
				+ (instruction.getPlanBegin()==null||instruction.getPlanBegin().toString().equals("null")
                ?null:("to_date('"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getPlanBegin())+
                "','yyyy-mm-dd hh24:mi:ss')")) + ",";
        String sql4 =
				(instruction.getPlanFinish()==null||instruction.getPlanFinish().toString().equals("null")
                ?"''":("to_date('"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getPlanFinish())+
                "','yyyy-mm-dd hh24:mi:ss')")) + ","
				+ instruction.getCount() + ","
				+ instruction.getInstructStateID() + ","
                + instruction.getIssuance() + ","
                + instruction.getStaError()
				+ ")";
        sql = sql1 + sql3 + sql4;
        return sql;
    }
    /**
     * 通过序号查出指令的sql语句
     *
     * @param id
     * @return T_RA_INSTRUCTION中该ID的所有字段
     */
    public String getInstructionCacheById(int id) {
        String sql = "select * from t_ra_Instruction_cache where int_id = " + id;
        return sql;
    }
    /**
     * 通过序号删除指令的sql语句
     *
     *@param Int_produnitid
     *@param str_date
     *@param workOrder
     *@param order
     *@return 删除T_RA_INSTRUCTION中该order的记录
     */
    public String delInstructionCacheByProduceUnitDateWorkorderOrder(int Int_produnitid, String str_date,String workOrder,int order) {
       String sql = "delete from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workorder='"+workOrder+"' and Int_instructOrder = " + order;
        return sql;
    }
    /**
     * 对指令进行更新操作的sql语句
     *
     * @param instruction
     * @return 更新T_RA_INSTRUCTION中该ID的记录
     */
    public String updateInstructionCache(Instruction instruction) {
        String a = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getProduceDate());
        String sql = "update t_ra_Instruction_cache set Int_produnitid = " + instruction.getProdunitid()
				+ " , Dat_produceDate = " +"to_date('"+a+"','yyyy-mm-dd hh24:mi:ss')"
				+ " , Str_versionCode = '" + instruction.getVersionCode()
				+ "' , Int_instructOrder = " + instruction.getInstructionOrder()
                + " , Dat_planDate = " +(instruction.getPlanDate()==null||instruction.getPlanDate().toString().equals("null")?"''":("to_date('"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getPlanDate())+"','yyyy-mm-dd hh24:mi:ss')"))
				+ " , Int_planOrder = " + instruction.getPlanOrder()
				+ " , Str_produceType = '" + instruction.getProduceType()
                + "' , Str_produceName = '" + instruction.getProduceName()
				+ "' , Str_produceMarker = '" + instruction.getProduceMarker()
				+ "' , Str_workOrder = '" + instruction.getWorkOrder()
                + "' , Str_workTeam = '" + instruction.getWorkTeam()
				+ "' , Tim_planBegin = " + (instruction.getPlanBegin()==null||instruction.getPlanBegin().toString().equals("null")?"''":("to_date('"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getPlanBegin())+"','yyyy-mm-dd hh24:mi:ss')"))
				+ " , Tim_planFinish = " + (instruction.getPlanFinish()==null||instruction.getPlanFinish().toString().equals("null")?"''":("to_date('"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(instruction.getPlanFinish())+"','yyyy-mm-dd hh24:mi:ss')"))
                + " , Int_count = " + instruction.getCount()
				+ " , Int_instructStateID = " + instruction.getInstructStateID()
                + " , Int_issuance = " + instruction.getIssuance()
                + " , Int_StaError = " + instruction.getStaError()
				+ " where int_id = " + instruction.getId();
        return sql;
    }
    /**
     * 查询T_RA_INSTRUCTION中记录个数
     * @return T_RA_INSTRUCTION中记录个数
     */
    public String getInstructionCacheCount() {
        String sql = "select count(*) from t_ra_Instruction_cache";
        return sql;
    }
    /**
     * 查询T_RA_INSTRUCTION中所有记录
     *
     * @return 返回T_RA_INSTRUCTION中所有记录
     */
    public String getAllInstructionCache() {
        String sql = "select * from t_ra_Instruction_cache order by int_id";
        return sql;
    }
    /**
     * 查找当前日期和生产单元的比该顺序号小的顺序的指令
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
    public String getByOrderMinus(int Int_produnitid, String str_date,String workOrder,int Int_instructOrder) {
        String sql = "select * from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workorder='"+workOrder+"' and int_instructorder <" + Int_instructOrder + " order by int_instructorder desc";
        return sql;
    }
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
    public String getByOrderPlus(int Int_produnitid, String str_date,String workOrder,int Int_instructOrder){
        String sql = "select * from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workOrder='"+workOrder+"' and int_instructorder >" + Int_instructOrder + " order by int_instructorder";
        return sql;
    }
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
    public String IssuanceByProduceUnitDateWorkorderOrder(int Int_produnitid, String str_date,String workOrder,int order) {
        String sql = "update t_ra_Instruction_cache set Int_issuance = 1 where Int_produnitid="+Int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workorder='"+workOrder+"'  and Int_instructOrder = " + order;
        return sql;
    }
    /**
     * 根据生产时间和生产单元班次查看相关的指令。
     *  @param  Str_produceUnit,String str_date,workOrder
     * @return  返回相对应的指令
     */
    public String getInstructionCacheByProduceUnitProduceDateOrder(int Int_produnitid, String str_date,String workOrder) {
        String sql="select * from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workorder='"+workOrder+"' order by int_instructorder";
	  return sql;
    }
    /**
     * 查询T_RA_INSTRUCTION中所有记录按顺序排序
     *
     * @return 返回T_RA_INSTRUCTION中所有记录按顺序排序
     */
    public String getAllInstructionCacheByOrder() {
        String sql = "select * from t_ra_Instruction_cache order by Int_instructOrder";
        return sql;
    }
    /**
     * 删除指令临时表中所有数据
     *
     * @return
     */
    public String DeleteInstructionCache(){
        String sql = "delete from T_RA_INSTRUCTION_CACHE";
        return sql;
    }

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
    public String getCountByProUnitDateOrder(int ProduceUnitID, String str_date,String workOrder){
    	String sql = "select count(*) from t_ra_instruction_cache where Int_produnitid=" + ProduceUnitID +" and str_workorder='" + workOrder +"'and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'";
    	return sql;
    }
    
    /**
     * 删除该生产单元所有临时指令
     * 
     * @param ProduceUnitID
     * 			生产单元号
     * @return 
     */
    public String DeleteInstructionCacheByProdUnitIdproducedateWorkorder(int ProduceUnitID,String str_date,String workOrder ){
        String sql = "delete from T_RA_INSTRUCTION_CACHE where Int_produnitid=" + ProduceUnitID+ " and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workorder='"+workOrder+"'";
        return sql;
    }
    
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
    public String getInstructionByProdUnitDateWorkorderOrder(int ProduceUnitID,String str_date,String workOrder,int Int_instructOrder){
    	String sql = "select * from t_ra_Instruction_cache where Int_produnitid="+ProduceUnitID+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workorder='"+workOrder+"' and Int_instructOrder = " + Int_instructOrder;
    	return sql;
    }
    
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
     public  String MinusInstructionOrder(int ProduceUnitID,String str_date,String workOrder,int order) {
     String sql="update t_ra_instruction_cache set int_instructorder=int_instructorder-1 where int_instructorder>"+order+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'"+"  and str_workorder='"+workOrder+"' and int_produnitid="+ProduceUnitID ;
     return sql;
 }
     
     /**
      * 发布该生产单元、生产日期的所有指令
      * 
      * @param Int_produnitid
      * @param str_date
      * @param workOrder
      * @return
      */
     public String IssuanceAllByProduceUnitDateWorkorder(int Int_produnitid, String str_date,String workOrder) {
         String sql = "update t_ra_Instruction_cache set Int_issuance = 1 where Int_produnitid="+Int_produnitid+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and str_workorder='"+workOrder+"'";
         return sql;
     }
     
     /**
      * 查询临时表最大顺序号
      * 
      * @param int_produnitid
      * @param str_date
      * @param workOrder
      * @return
      */
     public String getInstructionMaxOrder(int int_produnitid,String str_date,String workOrder){
    	 String sql = "select max(int_instructorder) from t_ra_Instruction_cache where int_produnitid="+int_produnitid+"  and str_workOrder='"+workOrder+"' and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'  ";
    	 return sql;
     }
     
     /**
 	 * 判断该生产单元、生产日期、产品标识的指令数量
 	 * 
 	 * @param produnitid
 	 * @param str_date
 	 * @param workOrder
 	 * @param marker
 	 * @return
 	 */
 	public String getCountByProUnitDateWorkorderMarker(int  produnitid,String str_date,String workOrder,String marker){
 		String sql = "select count(*) from t_ra_instruction_cache where to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and int_produnitid="+produnitid +" and str_workOrder='"+workOrder +"' and Str_produceMarker='"+marker+"'";
 		return sql;
 	}
     
 	/**
 	 * 查询该生产单元、生产日期、产品标识的指令
 	 * 
 	 * @param produnitid
 	 * @param str_date
 	 * @param workOrder
 	 * @param marker
 	 * @return
 	 */
 	public String getInstructionByProUnitDateWorkorderMarker(int  produnitid,String str_date,String workOrder,String marker){
 		String sql = "select * from t_ra_instruction_cache where to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"' and int_produnitid="+produnitid +" and str_workorder='"+workOrder +"' and Str_produceMarker='"+marker+"'";
 		return sql;
 	}
     
 	/**
     * 查出指定指令号对应的id
     * 徐嘉
     *  @param  int_order,str_date,ProduceUnitID,workOrder
     * @return  返回sql
     */
      public  String selectInstructionCacheid(int int_order,String str_date,int ProduceUnitID,String workOrder){
    	  String sql="select int_id from t_ra_instruction_cache where int_instructorder="+int_order+" and str_workorder='"+workOrder+"' and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'"+"and int_produnitid="+ProduceUnitID;
       return sql;
      }

     /**
      * 查出指定指令号的数量
      * 徐嘉
      *
      *  @param  int_order,str_date,ProduceUnitID,workOrder
      * @return  返回sql
      */
      public  String selectInstructionCacheNum(int int_order,String str_date,int ProduceUnitID,String workOrder){
    	  String sql="select int_count from t_ra_instruction_cache where int_instructorder="+int_order +"  and str_workorder='"+workOrder +"' and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'"+"and int_produnitid="+ProduceUnitID;
    	  return sql;
      }
     /**
       * 更改指定指令号的数量
       * 徐嘉
       *
       *  @param  int_count,int_order
       *   @return  返回sql
       */
       public  String updateInstructionCacheNum(int int_count,int int_order,String str_date,int ProduceUnitID,String workOrder){
    	   String sql="update t_ra_instruction_cache set int_count="+int_count+" where Int_instructOrder ="+int_order +" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'"+" and str_workorder ='"+workOrder +"' and int_produnitid="+ProduceUnitID ;
        return sql;
       }
       /**
        * 通过序号删除指令的sql语句
        * 徐嘉
        * @param order
        * @return 删除T_RA_INSTRUCTION中该order的记录（物理删除）
        */
       public String deleteInstructionCacheByOrder(int order,String str_date,int ProduceUnitID,String workOrder){
    	   String sql= "delete from t_ra_instruction_cache where int_instructOrder = "+ order+" and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'"+"  and str_workorder = '"+ workOrder+"'  and int_produnitid="+ProduceUnitID ;
        return sql;
    	     }
       /**
        * 增加指定指令号
        * 徐嘉
         *  @param  order
        * @return  返回sql
        */
        public  String updateInstructionCacheOrder(int order,String str_date,int ProduceUnitID,String workOrder) {
        String sql="update t_ra_instruction_cache set int_instructorder=int_instructorder+1 where int_instructorder="+order+"  and str_workOrder='"+workOrder+"' and to_char(Dat_produceDate,'yyyy-MM-dd')='"+str_date+"'"+"and int_produnitid="+ProduceUnitID ;
        return sql;
    }
        /**
         * 插入符合要求的指令
         * 徐嘉
         *  @param  order,order1,count,str
         * @return  返回sql
         */
         public  String insertInstructionCache(int order1,String str,int count,int order) {
        	 String sql="insert into t_ra_instruction_cache(int_id,int_produnitid,dat_producedate,str_versioncode,int_instructorder,dat_plandate,int_planorder,str_producetype,str_producename,str_producemarker,str_workorder,str_workteam ,tim_planbegin,tim_planfinish,int_count,int_instructstateid,int_issuance,int_staerror) select seq_RA_INSTRUCTION.nextval,int_produnitid,dat_producedate,str_versioncode,"+order1+",dat_plandate,int_planorder,str_producetype,str_producename,'"+str+"',str_workorder,str_workteam ,tim_planbegin,tim_planfinish,"+count+",int_instructstateid,int_issuance,int_staerror from t_ra_instruction_cache where Int_instructOrder ="+order;
    		return sql;
    	}
         
         /**
          * 通过生产单元查询产品标识
          * 
          * @param ProduceUnitID
          * @return
          */
         public String selectproducemarker(int ProduceUnitID){
         	String sql = "select str_producemarker from t_ra_instruction_cache where int_produnitid = "+ProduceUnitID;
         	return sql;
         	
         }
         /***
          *  谢静天查询编辑空间里面主物料的值
          */
         public String getInstructionbymateriel(String materiel,int int_produnitid){
        	 String sql = "select * from t_ra_instruction_cache where str_producemarker ='"+materiel+"'and int_produnitid="+int_produnitid+" ";
          	return sql; 
        	 
         }
         


}
