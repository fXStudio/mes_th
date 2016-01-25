/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mes.ra.dao;

import java.text.SimpleDateFormat;
import mes.ra.bean.Instruction;

/**
 *
 * @author YuanPeng
 */
public class DAO_Instruction_cacheForSqlserver extends DAO_Instruction_cacheForOracle{
	/**
	    * 创建指令的sql语句
	    *
	    * @param instruction
	    * @return
	    */
	public String saveInstructionCache(Instruction instruction) {
        String sql = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql1 = "insert into t_ra_Instruction_cache(Int_produnitid," +
                "Dat_produceDate,Str_versionCode,Int_instructOrder,Dat_planDate" +
                ",Int_planOrder,Str_produceType,Str_produceName,Str_produceMarker," +
                "Str_workOrder,Str_workTeam,Tim_planBegin,Tim_planFinish,Int_count," +
                "Int_instructStateID,Int_issuance,Int_StaError) "
				+ "values("
				+ instruction.getProdunitid() + ","
				+ (instruction.getProduceDate().equals("")||instruction.getProduceDate().equals("")?"''":"convert(datetime,'"+ sdf.format(instruction.getProduceDate())+ "',20)") + "," +
				(instruction.getVersionCode()==null||instruction.getVersionCode().equals("")?"''":("'"+instruction.getVersionCode().toString()) + "'") + ","
				+ instruction.getInstructionOrder() + ","
				+(instruction.getPlanDate()==null||instruction.getPlanDate().toString().equals("")
                ?"''":"convert(datetime,'"+ sdf.format(instruction.getPlanDate())+ "',20)") + ","
				+ instruction.getPlanOrder() + ","
				+ (instruction.getProduceType()==null||instruction.getProduceType().equals("")?"":("'"+instruction.getProduceType()))+"'" + ",";
       String sql3 =
				(instruction.getProduceName()==null||instruction.getProduceName().equals("")?"":("'"+instruction.getProduceName()+"'") )+ ","
				+ (instruction.getProduceMarker()==null||instruction.getProduceMarker().equals("")?"''":("'"+instruction.getProduceMarker()+"'")) + ","
				+ (instruction.getWorkOrder()==null||instruction.getWorkOrder().equals("")?"":("'"+instruction.getWorkOrder()+"'") )+ ","
				+ (instruction.getWorkTeam()==null||instruction.getWorkTeam().equals("")?"":("'"+instruction.getWorkTeam()+"'") )+ ","
				+ (instruction.getPlanBegin()==null||instruction.getPlanBegin().toString().equals("")
                ?null:"convert(datetime,'"+ sdf.format(instruction.getPlanBegin())+ "',20)") + "," ;
        String sql4 =
				(instruction.getPlanFinish()==null||instruction.getPlanFinish().toString().equals("")
                ?"''":"convert(datetime,'"+ sdf.format(instruction.getPlanFinish())+ "',20)")+ ","
				+ instruction.getCount() + ","
				+ instruction.getInstructStateID() + ","
                + instruction.getIssuance() + ","
                + instruction.getStaError()
				+ ")";
        sql = sql1 + sql3 + sql4;
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
       String sql = "delete from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+
       " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and str_workorder='"+workOrder+
       "'  and Int_instructOrder = " + order;
        return sql;
    }
    /**
     * 对指令进行更新操作的sql语句
     *
     * @param instruction
     * @return 更新T_RA_INSTRUCTION中该ID的记录
     */
    public String updateInstructionCache(Instruction instruction) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "update t_ra_Instruction_cache set Int_produnitid = " + instruction.getProdunitid()
				+ " , Dat_produceDate = " +"convert(datetime,'"+ instruction.getProduceDate()+ "',20)"
				+ " , Str_versionCode = '" + instruction.getVersionCode()
				+ "' , Int_instructOrder = " + instruction.getInstructionOrder()
                + " , Dat_planDate = " +(instruction.getPlanDate()==null||instruction.getPlanDate().toString().equals("null")?"''":"convert(datetime,'"+ sdf.format(instruction.getPlanDate())+ "',20)")
				+ " , Int_planOrder = " + instruction.getPlanOrder()
				+ " , Str_produceType = '" + instruction.getProduceType()
                + "' , Str_produceName = '" + instruction.getProduceName()
				+ "' , Str_produceMarker = '" + instruction.getProduceMarker()
				+ "' , Str_workOrder = '" + instruction.getWorkOrder()
                + "' , Str_workTeam = '" + instruction.getWorkTeam()
				+ "' , Tim_planBegin = " + (instruction.getPlanBegin()==null||instruction.getPlanBegin().toString().equals("null")?"''":"convert(datetime,'"+ sdf.format(instruction.getPlanBegin())+ "',20)")
				+ " , Tim_planFinish = " + (instruction.getPlanFinish()==null||instruction.getPlanFinish().toString().equals("null")?"''":"convert(datetime,'"+ sdf.format(instruction.getPlanFinish())+ "',20)")
                + " , Int_count = " + instruction.getCount()
				+ " , Int_instructStateID = " + instruction.getInstructStateID()
                + " , Int_issuance = " + instruction.getIssuance()
                + " , Int_StaError = " + instruction.getStaError()
				+ " where int_id = " + instruction.getId();
        return sql;
    }
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
    public String getByOrderMinus(int Int_produnitid, String str_date,String workOrder,int Int_instructOrder) {
        String sql = "select * from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+
        	" and str_workorder="+workOrder+
        	" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
			+ "',20) and int_instructorder <" + Int_instructOrder + " order by int_instructorder desc";
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
        String sql = "select * from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+
        " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and str_workOrder="+workOrder+
        "  and int_instructorder >" + Int_instructOrder + " order by int_instructorder";
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
        String sql = "update t_ra_Instruction_cache set Int_issuance = 1 where Int_produnitid="+Int_produnitid+" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and str_workorder='"+workOrder+"' and Int_instructOrder = " + order;
        return sql;
    }
    /**
     * 根据生产时间和生产单元班次查看相关的指令。
     *  @param  Str_produceUnit,String str_date,workOrder
     * @return  返回相对应的指令
     */
    public String getInstructionCacheByProduceUnitProduceDateOrder(int Int_produnitid, String str_date,String workOrder) {
        String sql="select * from t_ra_Instruction_cache where Int_produnitid="+Int_produnitid+" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and str_workorder="+workOrder+"order by int_instructorder";
	  return sql;
    }

    /**
     * 通过生产单元号、生产日期查询记录数量
     * 
     * @param ProduceUnitID
     * 			生产单元号
     * @param str_date
     * 			生产日期
     * @return 返回通过生产单元号、生产日期查询记录的数量
     */
    public String getCountByProUnitDateOrder(int ProduceUnitID, String str_date,String workOrder){
    	String sql = "select count(*) from t_ra_instruction_cache where Int_produnitid=" + ProduceUnitID +
    		" and str_workorder=" + workOrder +
    		" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date+ "',20)";
    	return sql;
    }
    
    /**
     * 通过生产单元号、生产日期、指令顺序号查询指令
     * 
     * @param ProduceUnitID
     * 			生产单元号
     * @param str_date
     * 			生产日期
     * @param Int_instructOrder
     * 				指令顺序号
     * @return 作业指令对象
     */
    public String getInstructionByProdUnitDateWorkorderOrder(int ProduceUnitID,String str_date,String workOrder,int Int_instructOrder){
    	String sql = "select * from t_ra_Instruction_cache where Int_produnitid="+ProduceUnitID+" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and str_workorder='"+workOrder+"'  and Int_instructOrder = " + Int_instructOrder;
    	return sql;
    }
    
    /**
     * 该生产日期、生产单元、大于该指令顺序号的指令顺序号减1
     * 
     *  @param  ProduceUnitID
     *  		生产单元号
     *  @param  str_date
     *  		生产日期号
     *  @param  order
     *  		指令顺序号
     * @return  返回sql
     */
     public  String MinusInstructionOrder(int ProduceUnitID,String str_date,String workOrder,int order) {
     String sql="update t_ra_instruction_cache set int_instructorder=int_instructorder-1 where int_instructorder>"+order+" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and str_workorder='"+workOrder+"' and int_produnitid="+ProduceUnitID ;
     return sql;
 }
     
     /**
      * 发布该生产单元、生产日期的所有指令
      * 
      * @param Int_produnitid
      * @param str_date
      * @return
      */
     public String IssuanceAllByProduceUnitDateWorkorder(int Int_produnitid, String str_date,String workOrder) {
         String sql = "update t_ra_Instruction_cache set Int_issuance = 1 where Int_produnitid="+Int_produnitid+" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and str_workorder='"+workOrder+"' ";
         return sql;
     }
     
     /**
      * 查询临时表最大顺序号
      * 
      * @param int_produnitid
      * @param str_date
      * @return
      */
     public String getInstructionMaxOrder(int int_produnitid,String str_date,String workOrder){
    	 String sql = "select max(int_instructorder) from t_ra_Instruction_cache where int_produnitid="+int_produnitid+" and str_workOrder='"+workOrder+"' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20)";
    	 return sql;
     }
     
     /**
 	 * 判断该生产单元、生产日期、产品标识的指令数量
 	 * 
 	 * @param produnitid
 	 * @param str_date
 	 * @param marker
 	 * @return
 	 */
 	public String getCountByProUnitDateMarker(int  produnitid,String str_date,String workOrder,String marker){
 		String sql = "select count(*) from t_ra_instruction_cache where convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and int_produnitid="+produnitid +" and str_workorder='"+workOrder +"' and Str_produceMarker='"+marker+"'";
 		return sql;
 	}
     
 	/**
 	 * 查询该生产单元、生产日期、产品标识的指令
 	 * 
 	 * @param produnitid
 	 * @param str_date
 	 * @param marker
 	 * @return
 	 */
 	public String getInstructionByProUnitDateWorkorderMarker(int  produnitid,String str_date,String workOrder,String marker){
 		String sql = "select * from t_ra_instruction_cache where convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and int_produnitid="+produnitid +" and str_workorder='"+workOrder +"' and Str_produceMarker='"+marker+"'";
 		return sql;
 	}
     
    
     /**
      * 查出指定指令号对应的id
      * 徐嘉
      *
      *  @param  int_order
      * @return  返回sql
      */
      public  String selectInstructionCacheid(int int_order,String str_date,int ProduceUnitID,String workOrder){
    	  String sql="select int_id from t_ra_instruction_cache where int_instructorder="+int_order+" and str_workOrder='"+workOrder+"' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and int_produnitid="+ProduceUnitID;
       return sql;
      }
      
     /**
      * 查出指定指令号的数量
      * 徐嘉
      *
      *  @param  int_order
      * @return  返回sql
      */
      public  String selectInstructionCacheNum(int int_order,String str_date,int ProduceUnitID,String workOrder){
    	  String sql="select int_count from t_ra_instruction_cache where int_instructorder="+int_order +" and str_workOrder='"+workOrder +"' and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
				+ "',20) and int_produnitid="+ProduceUnitID;
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
    	   String sql="update t_ra_instruction_cache set int_count="+int_count+" where Int_instructOrder ="+int_order +" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date
				+ "',20) and str_workorder ='"+workOrder +"' and int_produnitid="+ProduceUnitID ;
        return sql;
       }
       /**
        * 通过序号删除指令的sql语句
        * 徐嘉
        * @param order
        * @return 删除T_RA_INSTRUCTION中该order的记录（物理删除）
        */
       public String deleteInstructionCacheByOrder(int order,String str_date,int ProduceUnitID,String workOrder){
    	   String sql= "delete from t_ra_instruction_cache where int_instructOrder = "+ order+
    	   " and convert(datetime,Dat_produceDate,20)=convert(datetime,'"+ str_date
    	   		+ "',20) and str_workOrder = '"+ workOrder+
    	   "' and int_produnitid="+ProduceUnitID ;
        return sql;
    	     }
       /**
        * 增加指定指令号
        * 徐嘉
         *  @param  order
        * @return  返回sql
        */
        public  String updateInstructionCacheOrder(int order,String str_date,int ProduceUnitID,String workOrder) {
        String sql="update t_ra_instruction_cache set int_instructorder=int_instructorder+1 where " +
        		"int_instructorder="+order+" and convert(datetime,Dat_produceDate,20)=convert(datetime,'"
				+ str_date+ "',20) and str_workorder='"+workOrder+"' int_produnitid="+ProduceUnitID ;
        return sql;
    }
        public  String insertInstructionCache(int order1,String str,int count,int order) {
       	 String sql="insert into t_ra_instruction_cache(int_produnitid,dat_producedate,str_versioncode,int_instructorder,dat_plandate,int_planorder,str_producetype,str_producename,str_producemarker,str_workorder,str_workteam ,tim_planbegin,tim_planfinish,int_count,int_instructstateid,int_issuance,int_staerror) select int_produnitid,dat_producedate,str_versioncode,"+order1+",dat_plandate,int_planorder,str_producetype,str_producename,'"+str+"',str_workorder,str_workteam ,tim_planbegin,tim_planfinish,"+count+",int_instructstateid,int_issuance,int_staerror from t_ra_instruction_cache where Int_instructOrder ="+order;
   		return sql;
   	}
        /**
         * 删除该生产单元所有临时指令
         * 
         * @param ProduceUnitID
         * 			生产单元号
         * @return 
         */
        public String DeleteInstructionCacheByProdUnitIdWorkorder(int ProduceUnitID,String workOrder){
            String sql = "delete from T_RA_INSTRUCTION_CACHE where Int_produnitid=" + ProduceUnitID+ " and str_workorder=" + workOrder;
            return sql;
        }
        
        

}
