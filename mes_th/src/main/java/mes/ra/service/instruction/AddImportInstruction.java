package mes.ra.service.instruction;
/**功能 指令导入
 * author : 谢静天
 * 2009-3-7
 */

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import jxl.CellType;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.os.factory.WorkSchedleFactory;
import mes.ra.bean.Instruction;
import mes.ra.bean.ProduceUnit;
import mes.ra.bean.Version;
import mes.ra.factory.*;
import mes.framework.dao.*;

import mes.framework.DataBaseType;

import mes.tg.factory.*;

import mes.tg.bean.*;


public class AddImportInstruction  extends AdapterService {
	/**
	 * con 数据库连接 
	 */
	private Connection con = null;
    /**
     * sum 页面选中文本框的个数包括上传按钮
     */
    private String sum=null;
    /**
	 * in 输入流1
	 */
	private InputStream in=null;
	/**
	 * in2输入流2
	 */
	private InputStream in2=null;
	/**
	 * unitfactory 生产单元工厂
	 */
	private ProduceUnitFactory unitfactory=new ProduceUnitFactory();
	/**
	 * instructionFactory //指令工厂
	 */
	private  InstructionFactory instructionFactory=new InstructionFactory();
	/**
	 * historyfactory //指令历史工厂
	 */
	private  InstructionHistoryFactory historyfactory=new InstructionHistoryFactory();
	/**
	 * materielfactory //物料规则工厂
	 */
	private  MaterielRuleFactory materielfactory=new  MaterielRuleFactory();
	/**
	 * workschedlefactory 工作时刻表工厂
	 */
	private WorkSchedleFactory workschedlefactory = new WorkSchedleFactory();
	/**
	 * versioncode0 //先删除，同时指令的版本号不变
	 */
	private String versioncode0=null;
	
	/**
	 * replace_sign //替换
	 */
	private String replace_sign=null;
	/**
	 * autoOrder_sign //自动生成顺序号 
	 */
	private String autoOrder_sign=null;
	/**
	 * autoVersion_sign //自动生成版本号
	 */
	private String autoVersion_sign=null;
	/**
	 * userid  用户的id
	 */
	private String userid=null;
	/**
	 *  sqlplanall 计划预处理语句
	 */
    PreparedStatement sqlplanall=null;
    /**
     *   sqlinstruction 指令预处理语句
     */
    PreparedStatement sqlinstruction=null;
    /**
     *sqlinstructionexceptreplace 指令预处理除了当前的替换的指令内容。
     */
    PreparedStatement sqlinstructionexceptreplace=null;
	 /**
	 * produceid	//生产单元的id值 
	 */
	private  int produceid=0;
	/**
	 * produceDate 生产日期 如2009-06-23
	 */
	private   String produceDate=null;
	/**
	 * workOrder 班次
	 */
	private   String workOrder=null;
	/**
	 * log //日志
	 */
	private final Log log = LogFactory.getLog(AddImportInstruction.class);
	 /**  得到导入页面复选框值
	 * @param message
	 */
	public void apartParameter(IMessage message){
		//判断是否替换指令，是否生成顺序号，是否生成版本号
		switch(Integer.parseInt(sum)){
		   case 1:
			    replace_sign="0"; //替换
	    	    autoOrder_sign="0"; //自动生成顺序号
	    	    autoVersion_sign="0"; //自动生成版本号
	    	    break;
		   case 2:
			    replace_sign=message.getUserParameterValue("name"+1);
		    	if(Integer.parseInt(replace_sign)==1){
		    		replace_sign="1"; //替换
		    		autoOrder_sign="0"; //自动生成顺序号
		    		autoVersion_sign="0"; //自动生成版本号
		    		break;
		    	 }
		    	if(Integer.parseInt(replace_sign)==2){
		    		replace_sign="0"; //替换
		    		autoOrder_sign="1"; //自动生成顺序号
		    		autoVersion_sign="0"; //自动生成版本号
		    		break;
		    	 }
		    	if(Integer.parseInt(replace_sign)==3){
		    		replace_sign="0"; //替换
		    		autoOrder_sign="0"; //自动生成顺序号
		    		autoVersion_sign="1"; //自动生成版本号
		    		break;
		    	 }
		   case 3:
			    replace_sign=message.getUserParameterValue("name"+1);
    	        autoOrder_sign=message.getUserParameterValue("name"+2);
   		        if(Integer.parseInt(replace_sign)==1){
   			        if(Integer.parseInt(autoOrder_sign)==2){
   				      replace_sign="1"; //替换
   	    		      autoOrder_sign="1"; //自动生成顺序号
   	    		      autoVersion_sign="0"; //自动生成版本号
   	    		      break;
   			          }
   			       else{
   				      replace_sign="1"; //替换
   	    		      autoOrder_sign="0"; //自动生成顺序号
   	    		      autoVersion_sign="1"; //自动生成版本号
   	    		      break;
   			          }
	    	      }
   		       else{
   			       replace_sign="0"; //替换
   			       autoOrder_sign="1"; //自动生成顺序号
   			       autoVersion_sign="1"; //自动生成版本号
   			       break;
   		           }
		   case 4:
			    replace_sign="1"; //替换
	   		    autoOrder_sign="1"; //自动生成顺序号
	   		    autoVersion_sign="1"; //自动生成版本号
	   		    break;
		 }
	 }

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
	/** 核对excel中的内容是否为空
	 * @param colum 列
	 * @param strc 值
	 * @param row  行
	 * @param regex 正则表达式
	 * @param list_excel_instructionorder 放指令顺序号
	 * @param cel 单元格
	 * @param message 消息对象
	 * @param processid流程号
	 * @return
	 */
	public boolean checkmessage(int colum,String strc,int row,String regex, List<String>  list_excel_instructionorder,Cell cel,IMessage message,String processid){
	   try{
		  switch(colum){
               case 0: //生产单元名
     	            if(strc.equals("")||strc==null){
     	            	addException("第"+(row+1)+"行\"生产单元名\"为空", message, processid);
     	            	return gobackfalse();
			         }
			       if(unitfactory.checkProduceUnitByName(strc, con)==false) {
			    	    addException("第"+(row+1)+"行\"生产单元\"不存在", message, processid);
			    	    return  gobackfalse();
			        }
			        break;
    	       case 1:// 班组
			        if(strc.equals("")||strc==null){
			        	addException("第"+(row+1)+"行\"班组\"为空", message, processid);
			        	return gobackfalse();
			       }
			       break;
		       case 2:// 班次
			       if(strc.equals("")||strc==null){
			    	    addException("第"+(row+1)+"行\"班次\"为空", message, processid);
			    	    return gobackfalse();
			         }
			        break;
		       case 3:// 生产日期
			        if(cel.getType()!=CellType.DATE){
			        	 addException("第"+(row+1)+"行\"生产日期\"格式不对", message, processid);
			        	 return gobackfalse();
			         }
			      break;	
		       case 4://指令顺序号
			        if(Integer.parseInt(autoOrder_sign)==0){
					   if(!strc.matches(regex)){
						  addException("第"+(row+1)+"行\"指令顺序号\"应为正整数", message, processid);
     			         log.fatal("第"+(row+1)+"行\"指令顺序号或不为正整数\"为空");
     			         return gobackfalse();
					   }
     		            else{
     			           list_excel_instructionorder.add(strc);
     		              }
     	             }
			         break;
		        case 5://产品类别标识
			        if(strc.equals("")||strc==null){
			        	 addException("第"+(row+1)+"行\"产品类别标识\"为空", message, processid);
			        	  return gobackfalse();
			         }
			         break;
		        case 6:// 产品名称
			         if(strc.equals("")||strc==null){
			        	 addException("第"+(row+1)+"行\"产品名称\"为空", message, processid);
			        	 return  gobackfalse();
			         }
			         break;
		        case 7:// 产品标识
		             if(strc.equals("")||strc==null){
		            	 addException("第"+(row+1)+"行\"产品标识\"为空", message, processid);
		            	 return  gobackfalse();
			          }
			          break;
		        case 8://数量
			         if(!strc.matches(regex)){
			        	 addException("第"+(row+1)+"行\"数量\"应为正整数", message, processid);
			        	 return  gobackfalse();
			           }
			           break;
		         case 9:
			           break;
		         case 10:
			           break;
               }
		 }catch(Exception e){
			 e.getStackTrace();
			 addException("异常", message, processid);
			 return  gobackfalse();
		 }
		 return true;
	 }
	/**  核对生产单元，班次和生产日期是否相等。
     * @param rownum 行
     * @param columns 列
     * @param list_excel 存放第一行的内容
     * @param sheet 第一张表
     * @param message 消息对象
     * @param processid流程号
     * @return
     */
	public boolean checkproduceidworkorderproducedate(int rownum,int columns,List<String> list_excel,Sheet sheet,IMessage message,String processid){
		int row=2;
		while(row<rownum){
			for(int i=0;i<columns;i++){
		         Cell cel=sheet.getCell(i,row);
		         String strc= cel.getContents().trim();
		         switch(i){
					case 0: // 行生产单元值
						if(!list_excel.get(0).trim().equals(strc.trim())){
							addException("第2行与"+(row+1)+"行生产单元值不相等", message, processid);
							return gobackfalse();
						}
						break;

					case 2:// 行班次
						if(!list_excel.get(2).trim().equals(strc.trim())){
							addException("第2行与"+(row+1)+"行班次不相等", message, processid);
							return gobackfalse();
						}
						break;
					case 3:// 行生产日期
						if(!list_excel.get(3).trim().equals(strc.trim())){
							addException("第2行与"+(row+1)+"行生产日期不相等", message, processid);
							return	gobackfalse();
						}
						break;
					}
		        
		        }  
		row++;
		   }
		  return true;
	 }
	  /** 核对主物料值是否符合要求
     * @param rownum 行的个数
     * @param sheet  表
     * @param validate 物料规则
     * @param message 消息对象
     * @param processid流程号
     * @return
     */
	public boolean checkmateriel(int rownum,Sheet sheet,String validate ,IMessage message,String processid){
		 
		   ResultSet rs=null;
		   String sqlinstructionstring="select * from t_ra_instruction  where str_produceMarker=? and int_produnitid="+produceid+" and int_delete=0 ";
		   String sqlinstructionexceptreplacestring ="select * from t_ra_instruction where  str_produceMarker=? and int_produnitid="+produceid+" and int_delete=0 and "
		            +"str_versioncode in ("
                    +"select max(str_versioncode) from t_ra_instruction  where int_delete=0 and "
		            +"not(to_char(dat_produceDate,'yyyy-MM-dd')='"+produceDate+"'  and int_produnitid="+produceid+" and str_workOrder='"+workOrder+"' "
		            +")  group by dat_produceDate,int_produnitid,str_workOrder) ";

		   try{
		         sqlinstruction=con.prepareStatement(sqlinstructionstring);
		         sqlinstructionexceptreplace=con.prepareStatement(sqlinstructionexceptreplacestring);
		          //判断主物料值是否相等，相等报错 rownum为行数
		         for(int j=1;j<rownum;j++){
			           Cell cel=sheet.getCell(7,j);
	                   String strc1= cel.getContents().trim();
	                   if(!strc1.matches(validate)){
	                	   addException("第"+(j+1)+"行\"产品标识\"不符合主物料规则", message, processid);
	            	       log.fatal("第"+(j+1)+"行\"产品标识\"不符合主物料规则");
	            	       return  gobackfalse();
	                     }
			           for(int k=j+1;k<rownum;k++){ //与excel中的产品标识内容进行比较
		                    Cell ce2=sheet.getCell(7, k);
		                    String strc2=ce2.getContents().trim();
		                    if(strc1.equals(strc2)){
		                    	addException("第"+(j+1)+"行\"产品标识\"与本列其他项相等", message, processid);
		        	            log.fatal("第"+(j+1)+"行\"产品标识\"与本列其他项相等");
		        	            return  gobackfalse();
		                      }
			            }
			            //追加时判断到指令表中查看是否有相同的主物料
			          if(Integer.parseInt(replace_sign)==0){
				           sqlinstruction.setString(1, strc1);
					       rs=sqlinstruction.executeQuery();
					       if(rs.next()){
					    	   addException("第"+(j+1)+"行\"产品标识\"在指令系统中已经被使用了.请核实更改", message, processid);
					    	   return  gobackfalse();
					        }
			             }
			         else{
				            sqlinstructionexceptreplace.setString(1, strc1);
					        rs=sqlinstructionexceptreplace.executeQuery();
					        if(rs.next()){
					        	addException("第"+(j+1)+"行\"产品标识\"在指令系统中已经被使用了.请核实更改", message, processid);
					        	return  gobackfalse();
					         }
			             }
			  
			  
		        }
		   }catch(Exception e){
			   e.getStackTrace();
			   addException("异常", message, processid);
			   return  gobackfalse();
		   }
		return true;   
	 }

	/** 当选中替换时先删掉系统中的指令
	 * @param message
	 * @param processid
	 * @return
	 */
	public boolean replace_signinstruction(IMessage message,String processid){
		try{
		    if(Integer.parseInt(replace_sign)==1){
       	           if(!instructionFactory.checksaveVersion(produceid, produceDate,workOrder ,con)){
       		        //替换生成版本号时先把指令表中的内容放到指令历史表中在删除此表
       		              if(Integer.parseInt(autoVersion_sign)==1){
       			               //指令历史工厂
       				           InstructionHistoryFactory historyfactory=new InstructionHistoryFactory();
       				           List<Instruction> listinstruction = new ArrayList<Instruction>();
       				           //通过生产单元号，生产日期，班次获得指令信息
                  		       listinstruction= instructionFactory.getInstructionByProduceUnitProduceDateWorkorder(produceid, produceDate,workOrder, con);
                  	           Iterator<Instruction> iter=listinstruction.iterator();
                  		       while(iter.hasNext()){
                  			     Instruction instruction=new Instruction();
                  			     instruction=(Instruction)iter.next();
                  		         versioncode0=instruction.getVersionCode();
                  		         log.debug("创建历史指令版本号："+versioncode0);
                  			     historyfactory.saveInstruction(instruction, instruction.getVersionCode(), con);
                  			     log.info("创建历史指令成功");
                  		       }
                  		       instructionFactory.deleteInstructionByProduceUnitProduceDateWorkorder(produceid,produceDate,workOrder, con);
       			               log.info("删除该生产单元、生产日期的指令成功");
       		              }
       		             else
       		              {
       			              instructionFactory.deleteInstructionByProduceUnitProduceDateWorkorder(produceid,produceDate,workOrder, con);
       			              log.info("删除该生产单元、生产日期的指令成功");
       		              }
       	            }
       	          else
       	            {
       	        	 addException("生产已经开始或者有指令正处于编辑状态", message, processid);
         	         log.fatal("生产已经开始或者有指令正处于编辑状态");
         	        return gobackfalse();
       	            }
             }
		}catch(Exception e){
			e.getStackTrace();
			addException("异常", message, processid);
			return  gobackfalse();
		}
		return true;
	}
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection) message.getOtherParameter("con");
		userid=message.getUserParameterValue("userid");
		sum=message.getUserParameterValue("sum");
	    in=(InputStream)message.getOtherParameter("in");
	    in2=(InputStream)message.getOtherParameter("in2");
	    apartParameter(message);
	  
	    try {
	           jxl.Workbook rwb=Workbook.getWorkbook(in);
	    	   Sheet  sheet=rwb.getSheet(0); //获取第一张Sheet表
	    	   int row=1;//重第二行开始取数据。第一行为列名
			   int columns=sheet.getColumns(); //获取Sheet表中所包含的总列数，
			   int rownum=sheet.getRows();//　获取Sheet表中所包含的总行数，
			   List<String> list_excel = new ArrayList<String>();//放excel中第二行数据。
			   List<String> list_excel_instructionorder = new ArrayList<String>(); // 放excel中的指令顺序号
			   List<String> list_instructionorder = new ArrayList<String>(); // 放指令表中的order
			   String regex ="0*[1-9]{1}[0-9]*"; //正则表达式
			   int num=1; //只存放excle中的第二行数据
			  
			   if(columns!=11){
				   addException("该文件不是指令文件", message, processid);
				   return gobackfalse();
			   }
			   // 查看几个重要字段是否为空
			   while(row<rownum){
			     for(int i=0;i<columns;i++){
		             Cell cel=sheet.getCell(i,row);
		             String strc= cel.getContents().trim();
					 if(num==1){
						 list_excel.add(strc);
				       }
					 if(!checkmessage(i,strc,row,regex,list_excel_instructionorder,cel, message,processid))
		                 return false;
		           }
			       num++;
			       row++;
	            }
			   //判断指令顺序号是否有相等的 list_excel_instructionorder 放excel中的指令顺序号
			   if(Integer.parseInt(autoOrder_sign)==0){
				   for(int i=0;i<list_excel_instructionorder.size();i++){
					   for(int j=i+1;j<list_excel_instructionorder.size();j++){
						   if(list_excel_instructionorder.get(i).equals(list_excel_instructionorder.get(j))){
							   addException("第"+(i+2)+"行与第"+(j+2)+"行\"指令顺序号\"相等", message, processid);
						       log.fatal("第"+(i+2)+"行与第"+(j+2)+"行\"指令顺序号\"相等");
						       return gobackfalse();
						   }
					   }
				   }
         	    }
			   //提取出生产时间
			   String [] date=new String [3];
			   date=list_excel.get(3).split("/");
			   produceDate=date[2]+"-"+date[1]+"-"+date[0];
			   //班次信息
			   workOrder=list_excel.get(2);
			   //获取生产单元的id
			   ProduceUnit unit=new ProduceUnit();
			   unit=unitfactory.getProduceUnitByName(list_excel.get(0).trim(),con);
			   produceid=unit.getInt_id();
			   //获取物料规则
			   MaterielRule rule=new MaterielRule();
			   rule=materielfactory.findMaterielRule(unit.getInt_materielRuleid(),con);
			   String validate=rule.getValidate();
			   // 核对是否存在生产单元班次的工作时刻表
			   boolean ishasworkschedle=workschedlefactory.checkworkOrderProduce(produceid,workOrder,con);
			   if(!ishasworkschedle){
				   addException("没有该生产单元班次信息", message, processid);
				   return   gobackfalse();
				}
			   //追加不自动生成指令顺序号判断 list3中存放的是指令表中的顺序号
			   if(Integer.parseInt(replace_sign)==0){
				  if(Integer.parseInt(autoOrder_sign)==0){
				     list_instructionorder=instructionFactory.getallInstructionorder(produceid, produceDate,workOrder,con);
				     for(int i=0;i<list_excel_instructionorder.size();i++){
					      for(int j=0;j<list_instructionorder.size();j++){
						     if(list_excel_instructionorder.get(i).equals(list_instructionorder.get(j))){
						    	 addException("第"+(i+2)+"行\"指令顺序号\"在系统中已经存在", message, processid);
						    	 return  gobackfalse();
						       }
					       }
				       }
				  }
				   
			   }
			   //判断生产单元 班组 班次和生产日期是否相等和存在。
		       if(!checkproduceidworkorderproducedate(rownum,columns, list_excel, sheet, message, processid)){
		    	  return false;
		       }
		       //核对主物料值是否符合要求
			   if(!checkmateriel(rownum,sheet,validate,message,processid))
			    	return false;
	            //当选中替换时先删掉系统中的指令（生产单元和生产日期）
	           if(!replace_signinstruction(message, processid))
	        	   return false;
		       in.close();
		       list_excel.clear();
		       list_excel_instructionorder.clear();
		       list_instructionorder.clear();
		} catch (Exception sqle) {
			sqle.toString();
			addException("异常", message, processid);
			 return gobackfalse();
		}
	return true;
  }
	/**根据条件生成版本号
	 * @param first 初始版本号后两位数
	 * @param instruction 指令对象
	 * @param str_versioncode 指令历史表版本号+1
	 * @param str_versioncode1指令历史表版本号+2
	 * @return
	 */
	public String getversioncode(String first,Instruction instruction,String str_versioncode,String str_versioncode1){
		 String versioncode=null;  
		if(!first.equals("01")){
               if(Integer.parseInt(autoVersion_sign)==1){
        	         // 替换时生成版本
        	         if(Integer.parseInt(replace_sign)==1){
        		         instruction.setVersionCode(str_versioncode);
        		         versioncode=str_versioncode;
        		         log.debug("替换的指令版本号:"+str_versioncode);
        	           }
        	          // 追加时生成版本
        	        else{
        		         instruction.setVersionCode(str_versioncode1);
        		         versioncode=str_versioncode1;
        		         log.debug("追加的指令版本号:"+str_versioncode1);
        	           }
                 }
               else
               {     //当不生成版本时，不管替换还是追加都是一样版本号
            	        instruction.setVersionCode(str_versioncode);
        		        versioncode=str_versioncode;
               }
           }
          else
           {
    	       instruction.setVersionCode(str_versioncode1);
           }
		return versioncode;
	}
	/**对指令的主物料进行计划信息的添加（满足条件的情况下）
	 * @param instruction
	 * @param materiel
	 */
	public void  addinstructionplanmessage(Instruction instruction,String materiel){
		 
		 //查找相关的计划信息
		String  sqlplanstring="select p.*,to_char(p.Dat_produceDate,'yyyy-MM-dd') as producedate from t_os_plan p where str_versioncode in(select max(str_versioncode) from t_os_plan  where int_upload=1  and int_produnitid="+produceid+" group by int_produnitid,Dat_produceDate,str_workOrder"
			            +") and  str_producemarker=? ";
	try{
		  ResultSet rsprepare=null;
          sqlplanall=con.prepareStatement(sqlplanstring);
          sqlplanall.setString(1,materiel);
          rsprepare=sqlplanall.executeQuery();
          //最大版本且过了事实锁定的才能到补全指令的计划信息
          if(rsprepare.next()){
       	       long locked= workschedlefactory.getworkschedleadtime(rsprepare.getInt("int_produnitid"),rsprepare.getString("producedate"),rsprepare.getString("str_workorder"),con);
       	       if(locked==0){
       		   //如果有相同的主物料并且事实锁定则补全相关的计划信息
       		   instruction.setPlanDate(rsprepare.getDate("dat_planDate")); 
           	   instruction.setPlanOrder(rsprepare.getInt("int_planOrder"));
       	      }
          }
        else{
       	      instruction.setPlanDate(null); 
       	      instruction.setPlanOrder(0);
         }
	}catch(Exception e){
			e.getStackTrace();
	}
		
 }

	/**生成版本信息
	 * @param first
	 * @param str_versioncode
	 */
	public void  makeversion(String first,String str_versioncode){
		 Statement stmt=null;
		try{
	                		InstructionVersionFactory factory3=new  InstructionVersionFactory();
	                		Version version=new Version();
		                	version.setInt_delete(0);
		                	if(first.equals("01")){
		                		  version.setVersionCode(str_versioncode);
		                		  log.debug("当第一次生成指令版本号为："+str_versioncode);
		                	 }
		                  else{
		                		  
		                		  if(Integer.parseInt(replace_sign)==1){
		                			  version.setVersionCode(versioncode0);
			                		  log.debug("指令版本号："+versioncode0);
		                		   }
		                		  else{
		                		      version.setVersionCode(str_versioncode);  
		                		      log.debug("指令版本号："+str_versioncode);
		                		  }
		                	  }
		       		       
		       		        version.setProdunitid(produceid); 
	                		log.debug("生产单元号："+produceid);
		       		        String username=null;
		       		        int  userId = Integer.parseInt(userid);
		    	            IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
		    	            String sql = dao_UserManager.getSQL_SelectUserById(userId);
		    	            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		    	            log.debug("通过用户ID查询用户SQL语句："+sql);
		    	            ResultSet rs = stmt.executeQuery(sql);
		    	            if(rs.next()){
		    	        	   username = rs.getString("CUSRNAME");
		    	        	   log.debug("用户名："+username);
		    	            }
		    	           else{
		    	        	   username = null;
		    	        	  log.debug("用户名赋空值");
		    	            }
		       		       version.setUser(username);
		       		       if(versioncode0!=null||first.equals("01")){
		       		       factory3.saveVersion(version, con);
		       		       }
		       		       log.info("创建版本成功");
		       		      
		             
		}catch(Exception e){
			e.getStackTrace();
		}finally{
			try{
			 if(stmt!=null)
    		   stmt.close();
			}catch(Exception s){
				s.printStackTrace();
			}
		}
	    }
	
	/** 追加时生成版本把指令中的内容存放到指令历史表中然后更改指令版本号
	 * @param str_versioncode 指令历史表的版本号+1
	 * @param str_versioncode1指令历史表的版本号+2
	 */
	public void  addreplace_signautoVersion_sign(String str_versioncode,String str_versioncode1){
	 try{
		 List<Instruction> listinstruction = new ArrayList<Instruction>();
  		 listinstruction=instructionFactory.getInstructionByProduceUnitProduceDateWorkorder(produceid,produceDate,workOrder,con);
  	     Iterator<Instruction> iter=listinstruction.iterator();
  		 while(iter.hasNext()){
  			  Instruction instruction=new Instruction();
  			  instruction=(Instruction)iter.next();
  			  historyfactory.saveInstruction(instruction, str_versioncode, con);
  			  log.info("创建历史版本成功");
  			  instructionFactory.updateInstructionVersioncode(instruction, str_versioncode1, con);
  			  log.debug("更新指令版本号--版本号为："+str_versioncode1);
  		 }
	   }catch(Exception e){
			 e.getStackTrace();
	  }
  }
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
try {
	try {
              jxl.Workbook rwb = Workbook.getWorkbook(in2);//构建Workbook对象, 只读Workbook对象 直接从本地文件创建Workbook 从输入流创建Workbook
    	      Sheet  sheet = rwb.getSheet(0); //获取第一张Sheet表
    	      int row=1; // 重excel第一行开始去数据，0行为列名
		      int columns=sheet.getColumns(); //获取Sheet表中所包含的总列数，
		      int c=sheet.getRows(); //　获取Sheet表中所包含的总行数，
		      int mmm=1; //让系统只取一次版本号
		      int num=1; //自己生成顺序号
		      int banben=1; //让系统只生成一个版本
		      String str_versioncode=null; //  指令历史表中的版本号+1
		      String str_versioncode1=null; //  指令表中的版本号+2
		      String versioncode=null;//   版本号
		      String first="01"; //初始版本号的最后两位值
		      List<String> list_excel = new ArrayList<String>();   // 存放excel第一行的内容
		      int maxnum=instructionFactory.getInstructionMaxOrder(produceid,produceDate,workOrder,con);
	          while(row<c){
	        	   Instruction instruction=new Instruction();
	               //获取第一行的内容
		           for(int i=0;i<columns;i++){
	                    Cell cel=sheet.getCell(i,row);
	                    String strc= cel.getContents().trim();
	                    list_excel.add(strc);
	                }
	               String second=null;
	               if(mmm==1){
		                String code=historyfactory.checkcodebyproduceunitanddateWorkorder(produceid, produceDate,list_excel.get(2), con);
		                log.debug("指令版本号："+code);
		                if(!code.equals("")&&code!=null&&!code.equals("null"))
		                 {
				             int leng=code.length();
	  	                     String code1=code.substring(leng-2,leng-1);//十位
			    	         String code2=code.substring(leng-1,leng);  //	个位
			                 int gewei=Integer.parseInt(code2)+1;
			    	         if(gewei<=9){
			    	 	        first=code1+String.valueOf(gewei);
			    	          }
			               else{
			    	           int shiwei=Integer.parseInt(code1)+1;
			    	           first=String.valueOf(shiwei)+"0";
			    	          }
	                    }
		               if(Integer.parseInt(first)+1<=9){
    		               second="0"+String.valueOf((Integer.parseInt(first)+1));
    	                }
    	              else{
    		               second=String.valueOf((Integer.parseInt(first)+1));
    	                }
			          ProduceUnit produceunit=new ProduceUnit();
			          // 通过生产单元id获取生产单元名同时生成版本号
	                  produceunit=unitfactory.getProduceUnitbyId(produceid, con);
			          String str_name=produceunit.getStr_name();
			          String [] date=new String [3];
			          date=produceDate.split("-");
		              String namess=date[0]+date[1]+date[2];
		              str_versioncode=namess+str_name+list_excel.get(2)+first;
		              str_versioncode1=namess+str_name+list_excel.get(2)+second;
	                  mmm++;
	               }
	              instruction.setProdunitid(produceid);  //生产单元
	              instruction.setProduceDate((new SimpleDateFormat("yyyy-MM-dd")).parse(produceDate));//生产日期
	              log.debug("生产日期："+instruction.getProduceDate());
	              //根据条件生成版本号
	              if(mmm==2){
	            	  versioncode=getversioncode(first,instruction,str_versioncode,str_versioncode1);
	              }
	              else{
	            	  instruction.setVersionCode(versioncode);
	              }
	              //如果选中是替换的自动生成指令顺序号时
	             if(Integer.parseInt(replace_sign)==1){
	            	  if(Integer.parseInt(autoOrder_sign)==1){
	 	            	 instruction.setInstructionOrder(num);
	 	            	 log.debug("指令顺序号:"+num);
	 	            	 num++;
	 	              }
	            	  else{
	            		 instruction.setInstructionOrder(Integer.parseInt(list_excel.get(4)));
		 	             log.debug("指令顺序号:"+list_excel.get(4));
	            	  }
	             }
	              //如果选中是追加的自动生成指令顺序号时
	             if(Integer.parseInt(replace_sign)==0){
	            	      if(Integer.parseInt(autoOrder_sign)==1){
	            	    	  maxnum++;
	          	              instruction.setInstructionOrder(maxnum);
	          	              log.debug("指令顺序号:"+maxnum);
		 	               }
		            	  else{
		            		  instruction.setInstructionOrder(Integer.parseInt(list_excel.get(4)));
		            		  log.debug("指令顺序号:"+list_excel.get(4));
		            	   }
	              }
	             //对指令的主物料进行计划信息的添加（满足条件的情况下）
	             addinstructionplanmessage(instruction,list_excel.get(7));
	             instruction.setProduceType(list_excel.get(5).trim());
	             instruction.setProduceName(list_excel.get(6).trim());
	             instruction.setProduceMarker(list_excel.get(7).trim());
	             instruction.setWorkOrder(list_excel.get(2).trim());
	             instruction.setWorkTeam(list_excel.get(1).trim());
	             log.debug("产品类型："+list_excel.get(5)+"产品名称"+list_excel.get(6)+"；产品标识："+
	        		   list_excel.get(7)+"；班次："+list_excel.get(2)+"；班组："+list_excel.get(1));
	             if(list_excel.get(9)!=null&&!list_excel.get(9).equals(""))
	              {
	            	  String [] u=new String [3];
		              u=list_excel.get(9).split("/");
		              String begintime= u[2]+"-"+u[1]+"-"+u[0];
	                  instruction.setPlanBegin((new SimpleDateFormat("yyyy-MM-dd")).parse(begintime));
	                  log.debug("开始时间："+instruction.getPlanBegin());
	              }
	            else{
	            	  instruction.setPlanBegin(null);
		              log.debug("开始时间赋空值");
	            }
	            if(list_excel.get(10)!=null&&!list_excel.get(10).equals(""))
	            {
	            	  String [] u=new String [3];
		              u=list_excel.get(10).split("/");
		              String endtime= u[2]+"-"+u[1]+"-"+u[0];
	                  instruction.setPlanFinish((new SimpleDateFormat("yyyy-MM-dd")).parse(endtime));
	                  log.debug("结束时间："+instruction.getPlanFinish());
	            }
	            else{
	            	  instruction.setPlanFinish(null);
	                  log.debug("结束时间赋空值");
	            }
	            instruction.setCount(Integer.parseInt(list_excel.get(8)));
	            log.debug("数量："+list_excel.get(10));
	            int instructionstateid=unitfactory.getInstructionstateIdByproduceunitid(produceid,con);
	            instruction.setInstructStateID(instructionstateid);
	            instruction.setIssuance(0);
	            instruction.setStaError(0);
	            instruction.setDelete(0);
	            instruction.setEdit(0);
	            log.debug("指令状态号:"+instructionstateid+"；是否发布：否；是否错误：否；是否被删除：否；是否正在编辑：否");
	             //往指令表中添加数据
	            instructionFactory.saveInstruction(instruction,con);
	            log.info("创建指令成功");
	            if(first.equals("01")){
	            historyfactory.saveInstruction(instruction, str_versioncode, con);
	            log.debug("创建指令--版本号为："+str_versioncode);
	             }    
	            //生成版本
	           if(Integer.parseInt(autoVersion_sign)==1||first.equals("01")){
	               if(banben==1){
	        	      makeversion(first,str_versioncode);	
	        	      log.debug("创建版本成功");
		       	      banben++;
	               }
	           }
	           list_excel.clear(); 
	   		   row++;
	   		   
	         
	         }
	         //追加时生成版本把指令中的内容存放到指令历史表中然后更改指令版本号
	         if(!first.equals("01")){
         	    if(Integer.parseInt(replace_sign)==0){
         		     if(Integer.parseInt(autoVersion_sign)==1){
         			  addreplace_signautoVersion_sign(str_versioncode,str_versioncode1);
         		   }
         	     }
		      
	          }
    	  in2.close();
		
		
	} catch (SQLException sqle) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
						.getId(), processid, new Date(), sqle));
		log.error("数据库异常");
		return ExecuteResult.fail;
	}
} catch (Exception e) {
	message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
	log.error("未知异常");
	return ExecuteResult.fail;
}
  
return ExecuteResult.sucess;
}

@Override
public void relesase() throws SQLException {
	   if(con!=null){
	    con=null;
	    }
	    in=null;
		in2=null;
		produceid=0;
		workOrder=null;
		produceDate=null;
	    replace_sign=null;
		autoOrder_sign=null;
	    autoVersion_sign=null;
	    userid=null;
	    try{
	    	if(sqlinstruction!=null){
	         sqlinstruction.close();
	    	}
	    	if(sqlplanall!=null){
	    	 sqlplanall.close();
	    	}
		   if(sqlinstructionexceptreplace!=null){
			 sqlinstructionexceptreplace.close();
		   }
	    
	    }catch(Exception e){
	    	e.getStackTrace();
	    }

   }
}