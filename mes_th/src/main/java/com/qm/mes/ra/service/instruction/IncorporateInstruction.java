package com.qm.mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.DataBaseType;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.ra.bean.*;
import com.qm.mes.ra.dao.DAO_Instruction_cache;
import com.qm.mes.ra.factory.*;
import com.qm.mes.system.dao.DAOFactoryAdapter;
/** 合并指令
 * @author XuJia
 */
public class IncorporateInstruction extends AdapterService { 
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/** 
	 * 合并数量
	 */
	private int sum;
	/** 
	 * 合并顺序号
	 */
	private int arr[] = null;
	/** 
	 * 生产日期
	 */
	private String str_date=null;
	/** 
	 * 班次
	 */
	private String workOrder=null;
	/** 
	 * 生产单元号
	 */
	private String ProduceUnitID=null;
	
	ResultSet rs[];
	ResultSet rs1 = null;
	Statement stmt = null;
	Statement stmt1 = null;	
	InstructionCacheFactory instructionCacheFactory =null;		
	ProduceUnitFactory produceunitfactory=null;
	/** 
	 * 生产单元对象
	 */
	ProduceUnit produceunit=null;
	int num = 0;
	int i;
	int number;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(IncorporateInstruction.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		try {			
			con = (Connection) message.getOtherParameter("con");
			stmt = con.createStatement();   //初始化
			String a = message.getUserParameterValue("arr");
			String[] strArray = a.split(",");							
			str_date=message.getUserParameterValue("str_date");
			workOrder=message.getUserParameterValue("workOrder");
			ProduceUnitID=message.getUserParameterValue("ProduceUnitID");		
            sum=strArray.length;
            // 输出log信息
			String debug = "数量：sum=" + sum + "\n" + "顺序号值：arr=" + arr + "\n";
			log.debug("添加流程服务时用户提交的参数: " + debug);
			
			DAO_Instruction_cache dao1 = (DAO_Instruction_cache) DAOFactoryAdapter
					.getInstance(DataBaseType.getDataBaseType(con),
							DAO_Instruction_cache.class);
			log.debug("通过生产单元号、生产日期、班次、指令顺序号查询指令SQL语句："+dao1.getInstructionByProdUnitDateWorkorderOrder(Integer.parseInt(ProduceUnitID),str_date,workOrder,Integer.parseInt(strArray[0])));
			// 通过生产单元号、生产日期、指令顺序号查询指令
			ResultSet rs0 = stmt.executeQuery(dao1.getInstructionByProdUnitDateWorkorderOrder(Integer.parseInt(ProduceUnitID),str_date,workOrder,Integer.parseInt(strArray[0])));
         	
			String producetype="";
			String workteam="";
			String workorder="";
			int planorder=0;
			String plandate="";
			boolean boolean1=true;
			
			//取单元的是否启动计划验证
			produceunitfactory = new ProduceUnitFactory();
			con = (Connection) message.getOtherParameter("con");
			//通过ID号得到生产单元信息
			produceunit=produceunitfactory.getProduceUnitbyId(Integer.parseInt(ProduceUnitID), con);
			log.info("通过生产单元号查询生产单元成功");
			int unit=produceunit.getInt_planIncorporate();
			//取比对值
			while (rs0.next()) {
				producetype = rs0.getString("str_producetype");
				workteam = rs0.getString("str_workteam");
				num = rs0.getInt("int_count");
				workorder = rs0.getString("str_workorder");
				plandate=rs0.getString("dat_plandate");
				planorder=rs0.getInt("int_planorder");	
				log.debug("生产类型："+producetype+"；班组："+workteam+"；数量:"+num+"；班次："+workorder+"；计划日期："+plandate+"；计划顺序号："+planorder);
			}
			
			rs = new ResultSet[strArray.length]; //定义长度
			
			//循环选要比较的值
			for (i = 1; i < strArray.length; i++) {
				rs[i] = stmt.executeQuery(dao1.getInstructionByProdUnitDateWorkorderOrder(Integer.parseInt(ProduceUnitID),str_date,workOrder,Integer.parseInt(strArray[i])));
			    if (rs[i].next()) {
					String producetype1 = rs[i].getString("str_producetype");
					String workteam1 = rs[i].getString("str_workteam");
					String workorder1=rs[i].getString("str_workorder");
					int planorder1=rs[i].getInt("int_planorder");
					String plandate1=rs[i].getString("dat_plandate");
					boolean boolean2=false;
					if(plandate== null&&plandate1==null){
					    boolean2=true;}
					else if(plandate==null||plandate1==null)
					    {boolean2=false;}
					else{
					    boolean2=(plandate.equals(plandate1));
					}
					if (unit==0||(boolean2&&(planorder==planorder1)))
					{
						//校验
						if ( producetype.equals(producetype1) && workteam.equals(workteam1)&& workorder.equals(workorder1)) 
						{
							// 数量累加						
							num += rs[i].getInt("int_count");						
							continue;
						} else
				        	{message.addServiceException(new ServiceException(
									ServiceExceptionType.PARAMETERLOST, "生产类别、班组、班次存在不同，不能合并",
									this.getId(), processid, new java.util.Date(),
									null));
				        	log.fatal("生产类别、班组、班次存在不同，不能合并");
				        	boolean1=false;
				        	break;
						}
					}					 
					else {
						message.addServiceException(new ServiceException(
								ServiceExceptionType.PARAMETERLOST, "计划日期和顺序不同",
								this.getId(), processid, new java.util.Date(),
								null));
						log.fatal("计划日期和顺序不同");
					    boolean1=false; 					   
					    break;
					}
			    }  //for结束 
					
			} 
			
			return boolean1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
		        	
				String a = message.getUserParameterValue("arr");
				String[] strArray = a.split(",");	
				// 将arry逆序排列（除了目标指令）
				 int[] int_arr = new int[strArray.length-1];
				 for(int i=0;i<strArray.length-1;i++){
				        int_arr[i] = Integer.parseInt(strArray[i+1]);
				    }
				int flag=0;
				int j=0;
				for(i=1;i<int_arr.length;i++){
					for(j=int_arr.length-1;j>=i;j--){
						if(int_arr[j]>int_arr[j-1]) {
							flag = int_arr[j-1]; 
							int_arr[j-1] = int_arr[j]; 
							int_arr[j] = flag; 
						}
					}
				}
				
                //将累加的数字更新到第一条数据中
				instructionCacheFactory = new InstructionCacheFactory();
				con = (Connection) message.getOtherParameter("con");
				//更新指令数量
				instructionCacheFactory.updateInstructionCacheNum(num,(Integer.parseInt(strArray[0])), con,str_date,(Integer.parseInt(ProduceUnitID)),workOrder);
				log.info("更新临时指令成功");
				
				
				for(int k=0;k<int_arr.length;k++){
					//删除
					instructionCacheFactory.deleteInstructionCacheByOrder(int_arr[k], con,str_date,(Integer.parseInt(ProduceUnitID)),workOrder);
					log.info("删除临时指令成功");
					//改顺序号
					instructionCacheFactory.MinusInstructionOrder(Integer.parseInt(ProduceUnitID), str_date,workOrder, int_arr[k], con);
					log.info("临时指令调序成功");
				}								
				return ExecuteResult.sucess;

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
			log.error("未知错误");
			return ExecuteResult.fail;
		} finally {
			if (rs1 != null)
				rs1.close();
			if (stmt != null)
				stmt.close();
		}
		
	}

	@Override
	public void relesase() throws SQLException {
		arr = null;
		con = null;

	}

}
