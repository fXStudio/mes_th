package com.qm.mes.os.service.plan;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import java.sql.*;

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
import com.qm.mes.ra.bean.ProduceUnit;
import com.qm.mes.ra.factory.ProduceUnitFactory;
import com.qm.mes.tg.bean.MaterielRule;
import com.qm.mes.tg.factory.MaterielRuleFactory;


/**功能：导入作业计划
 * 
 * @author 谢静天
 * 
 */

public class AddImportPlan extends AdapterService {

	/**
	 * log 日志
	 */
	private final Log log = LogFactory.getLog(AddImportPlan.class);
	
	/**
	 * con 数据库连接
	 */
	private Connection con = null;

	/**
	 * stmt 向数据库发送sql语句
	 */
	private Statement stmt = null;
	
	/**
	 * factory // 计划工厂
	 */
	private PlanFactory factory = new PlanFactory();
	
	/**
	 * unitfactory // 生产单元工厂
	 */
	private ProduceUnitFactory unitfactory = new ProduceUnitFactory();
	
	/**
	 * versionfactory // 计划版本工厂
	 */
	private VersionFactory versionfactory = new VersionFactory();
	
	/**
	 * workschedlefactory // 工作时刻表工厂
	 */
	private WorkSchedleFactory workschedlefactory = new WorkSchedleFactory();
	
	/**
	 * sum // 导入页面 选中的个数，提交算一个。
	 */
	private String sum = null;
	
	/**
	 * code // 最大的版本号通过生产单元生产日期和班次得到
	 */
	private String code = null;
	
	/**
	 * replace // 替换replace 
	 */
	private String replace = null;
	
	/**
	 * automakeplanorder // 自动生成顺序号
	 */
	private String automakeplanorder = null;
	
	/**
	 * automakeversioncode // 自动生成版本号
	 */
	private String automakeversioncode = null;
	
	/**
	 * in // 输入流1
	 */
	private InputStream in = null;
	
	/**
	 * in2 // 输入流2
	 */
	private InputStream in2 = null;
	
	/**
	 * user 用户id
	 */
	private String user = null;
	
	/**
	 * produceid // 生产单元的id值
	 */
	private int produceid = 0;
	/**
	 *  生产日期 为字符串形式如2009-06-24 
	 */
	private String produceDate=null;
	/**
	 *workOrder 班次  
	 */
	private String workOrder=null;
	
	/**
	 * maxnum // 当追加自动生成顺序号时用 标识最大的顺序号
	 */
	private int maxnum = 0;

	
	 /**
	 * sqlplanall 计划表中所有计划的预处理
	 */
	private  PreparedStatement  sqlplannow=null;
	
	
	
    /**
     * @param message //分离出,替换，生成顺序号，生成版本的值.
     */

    public void apartParameter(IMessage message){
    	switch (Integer.parseInt(sum)) {
		 case 1:
			  replace="0";
			  automakeplanorder="0";
			  automakeversioncode="0";
			  break;
		 case 2:
			  replace=message.getUserParameterValue("num"+1);
			  if(Integer.parseInt(replace)==1){
				 replace="1";
				 automakeplanorder="0";
				 automakeversioncode="0";
				 break;
			  } 
			  if(Integer.parseInt(replace)==2){
				replace="0";
				automakeplanorder="1";
				automakeversioncode="0";
				break;
			  }
			  if(Integer.parseInt(replace)==3){
                replace="0";
				automakeplanorder="0";
				automakeversioncode="1";
				break;
			  }
		 case 3:
			  replace=message.getUserParameterValue("num"+1);
			  automakeplanorder=message.getUserParameterValue("num"+2);
			  if(Integer.parseInt(replace)==1){
				if(Integer.parseInt(automakeplanorder)==2){
					replace="1";
					automakeplanorder="1";
					automakeversioncode="0";

				  }
				else
				  {
					replace="1";
					automakeplanorder="0";
					automakeversioncode ="1";
				  }
			  }
			  else{
				replace="0";
			    automakeplanorder="1";
				automakeversioncode="1";
			  }
			  break;
		 case 4:
			 replace="1";
			 automakeplanorder="1";
			 automakeversioncode="1";
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
    /**判断excel中的内容是否为空，是否符合格式
     * @param colum excel中第几列
     * @param strc 当前单元格中的内容
     * @param row 行
     * @param regex正则表达式
     * @param list_excel_order存放excel中的顺序号
     * @param cel单元格对象
     * @param message消息对象
     * @param processid流程号
     * @return  
     */
    public boolean checkmessage(int colum,String strc,int row,String regex, List<String>  list_excel_order,Cell cel,IMessage message,String processid){
		  try{
		switch(colum){
					       case 0:// 生产单元名
						        if(strc.equals("")||strc==null){
						        addException("第"+(row+1)+"行\"生产单元名\"为空", message, processid);
						        return gobackfalse();
						         }
						        if(!unitfactory.checkProduceUnitByName(strc, con)){
							    addException("第"+(row+1) + "行\"生产单元\"不存在", message, processid);
							    return gobackfalse();
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
					       case 4:// 计划顺序号
						        if(Integer.parseInt(automakeplanorder)==0){
							       if(!strc.matches(regex)){
							    	  addException("第"+(row+1)+"行\"计划顺序号\"应为正整数", message, processid);
							    	  return gobackfalse();
							    }
							   else{
								list_excel_order.add(strc);
							     }
						       }
						       break;
					       case 5:// 产品类别标识
						        if(strc.equals("")||strc==null){
						        addException("第"+(row+1)+"行\"产品类别标识\"为空", message, processid);
						        return gobackfalse(); 
						        }
						        break;
					       case 6:// 产品名称
						        if(strc.equals("")||strc==null){
						        addException("第"+(row+1)+"行\"产品名称\"为空", message, processid);
						        return gobackfalse();
						        }
						        break;
					       case 7:// 产品标识
						        if(strc.equals("")||strc==null){
						        addException("第"+(row+1)+"行\"产品标识\"为空", message, processid);
						        return gobackfalse();
						        }
						        break;
					       case 8:// 数量
						        if(!strc.matches(regex)){
						        addException("第"+(row+1)+"行\"数量\"应为正整数", message, processid);
						        return gobackfalse();
						       }
						       break;
					       case 9:// 计划批次号
						        if(!strc.matches(regex)){
						        addException("第"+(row+1)+"行\"计划批次号\"应为正整数", message, processid);
						        return gobackfalse();
						        }
						        break;
					       case 10:// 计划日期
						        if(cel.getType()!=CellType.DATE) {
						        addException("第"+(row+1)+"行\"计划日期\"格式不对", message, processid);
						        return gobackfalse();
						        }
						       break;
					    }
		  }catch(Exception e){
			  e.getStackTrace();
			  return false;
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
			for (int i=0;i<columns;i++){
				Cell cel=sheet.getCell(i, row);
				String strc=cel.getContents().trim();
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
						 return gobackfalse();
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
     * @param size   计划个数
     * @param sheet  表
     * @param validate 物料规则
     * @param message 消息对象
     * @param processid流程号
     * @return
     */
    public boolean checkmateriel(int rownum,int size,Sheet sheet,String validate ,IMessage message,String processid){
		   try{
		    ResultSet rs=null;
			String sqlplanstring="select * from t_os_plan where str_versioncode =(select max(str_versioncode)  from t_os_plan where to_char(dat_produceDate,'yyyy-MM-dd')=?  and int_produnitid=? and str_workOrder=? and str_versioncode!='temp') " 
					+" and str_producemarker=? ";
            sqlplannow=con.prepareStatement(sqlplanstring); 
       
			for (int j=1;j<rownum;j++){
				 Cell cel=sheet.getCell(7, j);
				 String strc1 = cel.getContents().trim();
				 if(!strc1.matches(validate)){
					addException("第"+(j+1)+"行\"产品标识\"不符合主物料规则", message, processid);
					return gobackfalse();
				   }
				for(int k =j+1;k<rownum;k++){ // 与excel中的产品标识内容进行比较
					Cell ce2=sheet.getCell(7, k);
					String strc2=ce2.getContents().trim();
					if(strc1.equals(strc2)){
					addException("第"+(k)+"行\"产品标识\"与第"+(j)+"行相等", message, processid);
					return gobackfalse();
					}
				  }
				 // 追加时判断作业计划表中查看是否有相同的主物料  或者替换当时之前没有任何内容（生产单元，班次和生产日期）
				 if(Integer.parseInt(replace)==0){
					 sqlplannow.setString(1,produceDate);
					 sqlplannow.setInt(2,produceid);
					 sqlplannow.setString(3,workOrder);
					 sqlplannow.setString(4,strc1);
					 rs=sqlplannow.executeQuery();
					 if(rs.next()){
						addException("第"+(j+1)+"行\"产品标识\"在您要追加的版本中已经被使用了.请核实更改", message, processid);
						return gobackfalse();
					 }
				  }
				
			}
		   }catch(Exception e){
			   e.getStackTrace();
			   return false;
		   }
           return true;
		
	}
    public boolean checkParameter(IMessage message, String processid) {
		      con=(Connection) message.getOtherParameter("con");
		      user=message.getUserParameterValue("user");
		      sum=message.getUserParameterValue("sum");
		      in=(InputStream) message.getOtherParameter("in");
		      in2=(InputStream) message.getOtherParameter("in2");
		      String debug="user:"+user+" sum:"+sum+" in:"+in+"  in2:"+in2+ "\n";
              log.info("导入计划的参数: " + debug);
		      apartParameter(message);
		      try {
			        jxl.Workbook rwb=Workbook.getWorkbook(in); // excel 是从0行0列开始
			        Sheet sheet=rwb.getSheet(0);// 获取第一张Sheet表
			        int num=1; // 只放excel中的第2行的数据即内容
			        int row=1; // 行号第0行为列标题所以从1开始
			        int columns=sheet.getColumns();// 获取Sheet表中所包含的总列数，
			        int rownum=sheet.getRows(); // 获取Sheet表中所包含的总行数，
			        List<String> list_excel=new ArrayList<String>(); // 放excel中第二行数据。
			        List<Plan> list_plan=new ArrayList<Plan>(); // 放作业计划用于追加时判断主物料值
			        List<String> list_excel_order=new ArrayList<String>();  // 放excel中的计划顺序号
			        List<Integer> list_plan_order=new ArrayList<Integer>(); // 放作业计划表中的order
			        String regex="0*[1-9]{1}[0-9]*";//正则表达式
			        if(columns!=12){
						   addException("该文件不是计划文件", message, processid);
						   return gobackfalse();
					   }
			        // （1）判断所有字段是否为空和类型是否匹配
			        while(row<rownum){
				       for(int i=0;i<columns;i++){
					      Cell cel=sheet.getCell(i, row);
					      String strc=cel.getContents().trim();
					      if(num==1){ //只放excel中的第2行的数据即内容
						     list_excel.add(strc);
					       }
					      if(!checkmessage(i, strc, row, regex,list_excel_order, cel, message, processid)){
					    	return false;
					      }
				      }
				      num++;
				      row++;
			       } // （1） 判断所有字段是否为空和类型是否匹配 结束
			      //(2)不自动生成顺序号时判断excel中作业计划顺序号是否有相等的 list_excel_order放excel中的作业计划顺序号
			       if(Integer.parseInt(automakeplanorder)==0){
				         for(int i=0;i<list_excel_order.size();i++){
					       for(int j=i+1;j<list_excel_order.size();j++){
						     if(list_excel_order.get(i).equals(list_excel_order.get(j))){
						    	 addException("第"+(i+2)+"行与第"+(j+2)+"行\"作业计划号顺序号\"相等", message, processid);
						    	 return gobackfalse();
						       }
					        }
				          }
			           }// (2) 结束
			       //(3) 判断生产单元 班组 班次和生产日期是否相等和存在。
			       if(!checkproduceidworkorderproducedate(rownum,columns, list_excel, sheet, message, processid)){
			    	  return false;
			       }
			       // 提取出生产时间
			       String[] date = new String[3];
			       date = list_excel.get(3).split("/");
			       produceDate = date[2]+"-"+date[1]+"-"+date[0];
			       //班次信息
			       workOrder=list_excel.get(2);
			       // 获取生产单元的id和物料规则
			       ProduceUnit unit=new ProduceUnit();
			       unit=unitfactory.getProduceUnitByName(list_excel.get(0).trim(),con);
			       produceid = unit.getInt_id();
			       MaterielRuleFactory materiel = new MaterielRuleFactory();
			       MaterielRule rule=new MaterielRule();
			       rule=materiel.findMaterielRule(unit.getInt_materielRuleid(), con);
			       // 物料规则 正则表达式
			       String validate=rule.getValidate();
			       // 核对是否存在生产单元班次的工作时刻表
			       boolean ishasworkschedle=workschedlefactory.checkworkOrderProduce(produceid,workOrder, con);
			       if(!ishasworkschedle){
			    	   addException("没有该生产单元班次工作时刻表", message, processid);
			    	   return gobackfalse();
			        }
	                // 查看该生产单元,生产日期的班次计划是否在编辑.
			       list_plan =factory.getPlanbyProdateProidWorder(produceDate,produceid, workOrder, con);
                   if(list_plan.size()!=0&&list_plan.get(0).getVersioncode().equals("temp")){
                	   addException("该生产单元,生产日期的班次正在编辑.", message, processid);
                	   return gobackfalse();
			        }	
			          // 判断是否过了事实锁定
			       long time=0;
			         time=workschedlefactory.getworkschedleadtime(produceid,produceDate, list_excel.get(2), con);
			       if(time==0){
			    	   addException("该生产单元,生产日期的班次计划已经过了事实锁定期.", message, processid);
			    	   return gobackfalse();
			        }
			        // 获得最大的顺序号
			      list_plan_order=factory.getPlanOrderbyProdateProidWorder(produceDate, produceid, list_excel.get(2), con);
			      if(list_plan_order.size()!=0){
				  maxnum=list_plan_order.get(list_plan_order.size()-1);
			      }
			      //追加不自动生成作业计划顺序号判断 list3中存放的是作业计划表中的顺序号
			      if(Integer.parseInt(replace)==0){
				    if(Integer.parseInt(automakeplanorder)==0){
					  for(int i=0;i<list_excel_order.size();i++){
						for(int j=0;j<list_plan_order.size();j++){
							if(Integer.parseInt(list_excel_order.get(i))==list_plan_order.get(j)){
								addException("第"+(i+2)+"行\"计划顺序号\"在系统中已经存在", message, processid);
								return gobackfalse();
						      }
						   }
					   }
				    }
			      } 
			      //核对主物料值是否符合要求
			    if(!checkmateriel(rownum, list_plan.size(), sheet, validate , message,processid))
			    	return false;
			    in.close(); 
			    list_excel.clear();
		} catch (Exception sqle) {

			sqle.printStackTrace();
			return false;
		}finally{
			try{
			  if(sqlplannow!=null)
				  sqlplannow.close();
			  
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}
   
	/**对满足条件的计划做相关操作
	 * @param upload 发布标识
	 * @param str_versioncode 为获取的版本号加1新生成的版本号
	 * @param plan 计划对象
	 */
	public void doonce(int upload,String str_versioncode,Plan plan){
		try{
			List<Plan> planbycode=new ArrayList<Plan>();
		if(Integer.parseInt(replace)==0){
			// 追加不生成版本 什么都不用做，如果是发布时就要把追加不生成版本变成追加生成版本
			if(Integer.parseInt(automakeversioncode)==1||upload==1){
				planbycode=factory.getPlanbyversioncord(code,con);
				Iterator<Plan> iter=planbycode.iterator();
				while(iter.hasNext()){
					Plan plan1=(Plan)iter.next();
					plan1.setVersioncode(str_versioncode);
					factory.savePlan(plan1, con);
				  }
			  }
		   }
		else{// 替换不生成版本,或者替换生成版本都要把原来的版本删除掉， 同时到版本表中删除记录
			if(code!=null){
				factory.deletePlanbyversioncode(code, con);
				if(Integer.parseInt(automakeversioncode)==1||upload==1){
				versionfactory.deleteVersionbyversioncode(code, con);
				}
			}
		}
		if(upload==1||Integer.parseInt(automakeversioncode)==1){
			Version version=new Version();
			version.setVersionCode(plan.getVersioncode());
			version.setUser(user);
			versionfactory.saveVersion(version, con);
		}
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {//获取用户的名
				int userId=Integer.parseInt(user);
				IDAO_UserManager dao_UserManager = DAOFactory_UserManager
						.getInstance(DataBaseType.getDataBaseType(con));
				 
				String sql=dao_UserManager.getSQL_SelectUserById(userId);
				stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
					user=rs.getString("CUSRNAME");
				else
					user=null;
				// 构建Workbook对象, 只读Workbook对象 直接从本地文件创建Workbook 从输入流创建Workbook
				jxl.Workbook rwb=Workbook.getWorkbook(in2);
				Sheet sheet=rwb.getSheet(0);// 获取第一张Sheet表
				int row=1;//行号第0行为列标题所以从1开始
				int columns=sheet.getColumns();// 获取Sheet表中所包含的总列数，
				int rownum=sheet.getRows();// 获取Sheet表中所包含的总行数，
				List<String> list_excel=new ArrayList<String>();// 存放excel第一行的内容
				List<Plan> listmaxplan=new ArrayList<Plan>();// 获取最大的版本号计划信息
				String first="01";
				int excuteonetime = 1;// 让系统只取一次版本号
				String str_versioncode = null;// 在取出的版本号上加1生成的新版本号
				int upload=0;// 发布标识
				int firstnum=1;//让doonce函数只执行一次
				int num=1;//计划顺序号
				while(row<rownum){
					Plan plan=new Plan();
					// 获取第一行的内容
					for (int i=0;i<columns;i++){
						Cell cel=sheet.getCell(i,row);
						String strc=cel.getContents().trim();
						list_excel.add(strc);
					}
					// 提取计划日期
					String[] plandate=new String[3];
					plandate=list_excel.get(10).split("/");
					String planDate=plandate[2]+"-"+plandate[1]+ "-"+plandate[0];
					// 让系统只取一次版本号
					if(excuteonetime==1){
                       listmaxplan=factory.getmaxPlanexcepttemp(produceDate,produceid, workOrder, con);
						if(listmaxplan.size()!=0){
							Plan plannew=new Plan();
							plannew=listmaxplan.get(0);//获取一个计划对象
							upload=plannew.getUpload();// 获取发布标识
							code=plannew.getVersioncode();//获取版本号
							if(!code.equals("")&&code!= null){
								int leng=code.length();
								String code1=code.substring(leng-2,leng-1);// 十位
								String code2 = code.substring(leng-1,leng);// 个位
								int gewei=Integer.parseInt(code2)+1;
								if(gewei<=9){
									first = code1 + String.valueOf(gewei);
								}else{
									int shiwei = Integer.parseInt(code1) + 1;
									first = String.valueOf(shiwei) + "0";
								}
							 }
						  }
						String str_name=list_excel.get(0).trim();//生产单元名
						String[] name=new String[3];
						name=produceDate.split("-");//生产日期去掉"-"
						String namess=name[0]+name[1]+name[2];
						str_versioncode=namess+str_name+workOrder+first;//获取版本号
						excuteonetime++;
					}
					if(upload==1){// 当作业计划已经发布时必须生成新版本
						plan.setVersioncode(str_versioncode);
					 }else{
						if(!first.equals("01")){//不生成版本
							if(Integer.parseInt(automakeversioncode)==0){
									plan.setVersioncode(code);//设置版本号
								}//生成版本
								else {
									plan.setVersioncode(str_versioncode);//设置版本号
								}
						}else {
							plan.setVersioncode(str_versioncode);//设置版本号
						}
					}
					plan.setProdunitid(produceid);//设置生产单元
					plan.setWorkTeam(list_excel.get(1));//设置班组
					plan.setWorkOrder(workOrder);//设置班次
					plan.setPlanDate((new SimpleDateFormat("yyyy-MM-dd")).parse(planDate));//设置计划日期
					plan.setProduceDate((new SimpleDateFormat("yyyy-MM-dd")).parse(produceDate));//设置生产日期
					plan.setOrderFormId(" ");//设置订单号
					plan.setProduceType(list_excel.get(5));//设置产品类别标识
					plan.setProduceName(list_excel.get(6));//设置产品名称
					plan.setProduceMarker(list_excel.get(7));//设置产品标识
					plan.setAmount(Integer.parseInt(list_excel.get(8)));//设置数量
					plan.setPlanGroupId(Integer.parseInt(list_excel.get(9)));//设置计划批次号
					if(Integer.parseInt(replace)==1){//如果选中是替换的自动生成计划顺序号时
						if(Integer.parseInt(automakeplanorder)==1){
							plan.setPlanOrder(num);//设置计划顺序号
							num++;
						} else {
							plan.setPlanOrder(Integer.parseInt(list_excel.get(4)));//设置计划顺序号
						}
					}
					else{	// 如果选中是追加的自动生成计划顺序号时
						if(Integer.parseInt(automakeplanorder)==1){
							maxnum=maxnum+1;
							plan.setPlanOrder(maxnum);//设置计划顺序号
						}else{
							plan.setPlanOrder(Integer.parseInt(list_excel.get(4)));//设置计划顺序号
						}
					}
					plan.setUpload(0);//设置发布标识
					plan.setDescription(list_excel.get(11));//设置描述信息
					if(firstnum==1){
						 doonce(upload,str_versioncode,plan);
						firstnum++;
					}
					factory.savePlan(plan, con);//保存计划记录
					row++;//行加一
					list_excel.clear();//清空excel的一行数据
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
		con=null;
		sum=null;
		code=null;
		replace=null;
		automakeplanorder=null;
		in=null;
		in2=null;
		automakeversioncode=null;
		user=null;
		produceid=0;
		workOrder=null;
		produceDate=null;
		maxnum=0;
		if(stmt!= null)
		  stmt.close();
	}

}
