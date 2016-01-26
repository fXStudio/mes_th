package com.qm.mes.os.service.mpsplan;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.DataBaseType;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.framework.dao.DAOFactory_UserManager;
import com.qm.mes.framework.dao.IDAO_UserManager;
import com.qm.mes.os.bean.*;
import com.qm.mes.os.factory.*;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
/**
 * 功能：导入主计划
 * @author 谢静天 2009-5-13
 *
 */
public class AddImportMPSPlan  extends AdapterService  {
	
	/**
	 * log  日志  
	 */
	private final Log log = LogFactory.getLog(AddImportMPSPlan.class);
	/**
	 * con <font color=red> 数据库连接</font>
	 */
	private Connection con = null;
	/**
	 * factory <font color=red>主计划工厂</font>
	 */
	private MPSPlanFactory factory= new MPSPlanFactory();
	/**
	 * user 用户的id
	 */
    private String user=null;
     /**
      *  in 输入流1
      */
	private InputStream in=null;
	/**
	 *  in2 输入流2
	 */
	private InputStream in2=null;
	/**
	 * stmt   向数据库发送sql语句
	 */
	private Statement stmt=null;
	/**
	 * 
	 * @param msn 异常消息
	 * @param message 消息对象
	 * @param processid 流程号
	 */
	 public void addException(String msn,IMessage message,String processid){
		 message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, 
							msn, this
							.getId(), processid,
					new java.util.Date(), null));
	 }
	 /**
	  * 
	  * @return false
	  */
	 public boolean gobackfalse(){
		 return false;
	 }
	public boolean checkParameter(IMessage message, String processid) {
		      con = (Connection) message.getOtherParameter("con");
		      user=message.getUserParameterValue("user");
	          in=(InputStream)message.getOtherParameter("in");
	          in2=(InputStream)message.getOtherParameter("in2");
	    try {
	    	  //创建工作薄
	          jxl.Workbook rwb = Workbook.getWorkbook(in);
	          //获取第一张Sheet表
	    	  Sheet  sheet = rwb.getSheet(0);
	    	  //为了只获取excel中的第二行数据
	    	  int num=1;
	    	  //从第二行开始即有数据的行 excel行数从0开始，第一行放的是列名
	    	  int row=1;
			  //获取Sheet表中所包含的总列数，
			  int columns=sheet.getColumns();
			  //　获取Sheet表中所包含的总行数，
			  int rownum=sheet.getRows();
			   //放excel中第二行数据。
			  List<String> list_excel = new ArrayList<String>();
			   //判断所有字段是否为空和类型是否匹配
			   //regex 为正整数的正则表达式
			  String  regex="0*[1-9]{1}[0-9]*";
			  while(row<rownum){
				  for(int i=0;i<columns;i++){
			           Cell cel=sheet.getCell(i,row);
			           String strc= cel.getContents().trim();
			           if(num==1){
			           list_excel.add(strc);
				        }
			           
			           switch(i){
			               case 0://计划日期
			        	       if(cel.getType()!=CellType.DATE){
			        	    	   addException("第"+(row+1)+"行\"计划日期\"格式不对", message, processid);
			        	    	   return gobackfalse();
				            	 }
			                     break;
			               case 1: //mps单位   
			            	   if(strc.equals("")||strc==null){
			            		   addException( "第"+(row+1)+"行\"MPS单位\"为空", message, processid);
			            		   return gobackfalse();
					             } 
			            	     break;
			               case 2: //物料名
			            	   if(strc.equals("")||strc==null){
			            		   addException("第"+(row+1)+"行\"物料名\"为空", message, processid);
			            		   return gobackfalse();
					              }
			            	      break;
			               case 3: //计划数量
			            	   if(!strc.matches(regex)){
			            		   addException("第"+(row+1)+"行\"计划数量\"应为正整数", message, processid);
			            		   return gobackfalse();
				            	  }
			            	   break;
			               case 4://预计库存
			            	   if(!strc.matches(regex)){
			            		   addException("第"+(row+1)+"行\"预计库存\"应为数字", message, processid);
			            		   return gobackfalse();
				            	  }
			            	   break;
			               case 5: //计划期类型
			            	   if(strc.equals("")||strc==null){
			            		   addException("第"+(row+1)+"行\"计划日期类型\"为空", message, processid);
			            		   return gobackfalse();
					              }
			            	   break;
			               case 6: //版本
			            	   if(strc.equals("")||strc==null){
			            		   addException("第"+(row+1)+"行\"版本\"为空", message, processid);
			            		   return gobackfalse();
					              }
			            	   break;
			               case 7: //合同号
			            	   if(strc.equals("")||strc==null){
			            		   addException("第"+(row+1)+"行\"合同号\"为空", message, processid);
			            		   return gobackfalse();
					              }
			            	   break;
			                 }
					     }
					   num++;
					   row++;
				   }// 匹配结束
			   
			   //按照第一行的内容与后面的进行对比，看计划日期是否符合要求    
			   row=2;
			   while(row<rownum){
				   for(int i=0;i<columns;i++){
			             Cell cel=sheet.getCell(i,row);
			             String strc= cel.getContents().trim();
			             if(i==0&&(!list_excel.get(0).trim().equals(strc.trim()))){
			            	 addException("第2行与"+(row+1)+"行计划日期不相等", message, processid);
			            	 return gobackfalse();
			             }
			         }  
				   row++;
			   } 
			   //替换相同的计划日期的主计划
			   String [] date=new String [3];
			   date =list_excel.get(0).split("/");
	           String startDate= date[2]+"-"+date[1]+"-"+date[0];
	           log.debug("计划日期:"+startDate);
	           //删除主计划通过计划日期
	           factory.deleteMPSPlanbystartDate(startDate, con);
	           in.close();
	           list_excel.clear() ;   
		} catch (Exception sqle) {
			sqle.getStackTrace();
			 return gobackfalse();
		}
		return true;
	}
	
	
	
	

	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
try {
	try {
		      //构建Workbook对象, 只读Workbook对象 直接从本地文件创建Workbook 从输入流创建Workbook
              jxl.Workbook rwb = Workbook.getWorkbook(in2);
    	      Sheet  sheet = rwb.getSheet(0);   //获取第一张Sheet表
    	      int row=1;
		      int columns=sheet.getColumns(); //获取Sheet表中所包含的总列数，
		      int rownum=sheet.getRows();  //　获取Sheet表中所包含的总行数，
			  List<String> list_excel = new ArrayList<String>();  // 存放excel第一行的内容
			  int  userId = Integer.parseInt(user);
	   	      IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
	   	      String sql = dao_UserManager.getSQL_SelectUserById(userId);
	   	      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	   	      ResultSet rs = stmt.executeQuery(sql);
              log.debug("通过ID查询用户: " + dao_UserManager.getSQL_SelectUserById(userId));
	   	      if(rs.next())
	   	        	user = rs.getString("CUSRNAME");
	   	      else
	   	        	user = null;
		     
		      while(row<rownum){
	        	     MPSPlan mpsplan=new MPSPlan();
	                //获取第一行的内容
		             for(int i=0;i<columns;i++){
	                 Cell cel=sheet.getCell(i,row);
	                 String strc= cel.getContents().trim();
	                 list_excel.add(strc);
	                  }
		             String [] date=new String [3];
		             date =list_excel.get(0).split("/");
	                 String startDate= date[2]+"-"+date[1]+"-"+date[0];
                     mpsplan.setStartDate((new SimpleDateFormat("yyyy-MM-dd")).parse(startDate));
                     mpsplan.setMpsUnit(list_excel.get(1));
                     mpsplan.setMaterielName(list_excel.get(2));
                     mpsplan.setPlanAmount(Integer.parseInt(list_excel.get(3)));
                     mpsplan.setIntendStorage(Integer.parseInt(list_excel.get(4)));
                     mpsplan.setPlanType(list_excel.get(5));
                     mpsplan.setVersion(list_excel.get(6));
                     mpsplan.setUserName(user);
                     mpsplan.setContractCode(list_excel.get(7));
                     factory.saveMPSPlan(mpsplan, con);
	                 row++;
	                 list_excel.clear();
		      }
	         in2.close();
           } catch (SQLException sqle) {
	         message.addServiceException(new ServiceException(
			ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
					.getId(), processid, new Date(), sqle));
	         return ExecuteResult.fail;
        }
        } catch (Exception e) {
            message.addServiceException(new ServiceException(
		     ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
		     processid, new java.util.Date(), e));
            return ExecuteResult.fail;
       }
            return ExecuteResult.sucess;
   }

@Override
     public void relesase() throws SQLException {
          con = null;
          in=null;
	      in2=null;
          user=null;
          if(stmt!=null)
    	   stmt.close();
    }
}
	    
