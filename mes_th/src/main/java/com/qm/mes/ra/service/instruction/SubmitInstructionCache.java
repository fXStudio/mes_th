package com.qm.mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import com.qm.mes.ra.bean.Instruction;
import com.qm.mes.ra.bean.ProduceUnit;
import com.qm.mes.ra.bean.Version;
import com.qm.mes.ra.factory.InstructionCacheFactory;
import com.qm.mes.ra.factory.InstructionFactory;
import com.qm.mes.ra.factory.InstructionHistoryFactory;
import com.qm.mes.ra.factory.InstructionVersionFactory;
import com.qm.mes.ra.factory.ProduceUnitFactory;

/**
 * 提交作业指令临时表
 *
 * @author YuanPeng
 */

public class SubmitInstructionCache extends AdapterService { 
    /**
     * 数据库连接对象
     */
    Connection con = null;
    /**
     * 按生产单元和生产日期在临时表中读取的指令对象集合
     */
    private List<Instruction> list =new ArrayList<Instruction>();
    /**
     * 指令工厂
     */
    private InstructionFactory instructionFactory = null;
    /**
     * 临时指令工厂
     */
    private InstructionCacheFactory instructionCacheFactory = null;

    /**
     * 生产单元号
     */
    private int ProduceUnitID ;
    /**
     * 字符串型日期
     */
    private String str_date = null;
    /**
     * 是否保存版本
     */
    private String str_saveversion = null;
    /**
     * 非锁定的首个顺序号
     */
    private int UnlockStartOrder;
    /**
     * 版本对象
     */
    private Version version = new Version();
    /**
     * 版本号
     */
    private String str_versioncode = null;
    /**
     * 用户ID
     */
    private int userId ;
    /**
     * 用户名
     */
    private String username = null;
    /**
     *班次
     */
    private String workOrder=null;
	/**
	 * 日志
	 */
	private final Log log = LogFactory.getLog(SubmitInstructionCache.class);
    
    /**
     * 垃圾回收
     *
     * @throws java.sql.SQLException
     */
    @Override
    public void relesase() throws SQLException {
        con = null;
    }

    /**
     * 检查参数
     *
     * @param message
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return boolean值
     *              返回boolean值以显示成功与否
     */
    @Override
    public boolean checkParameter(IMessage message, String processid) {
    	try {
	    	con = (Connection) message.getOtherParameter("con");
	        ProduceUnitID = Integer.parseInt(message.getUserParameterValue("str_ProduceUnitID"));
	        workOrder=message.getUserParameterValue("workOrder");
	        str_date = message.getUserParameterValue("str_date");
	        str_saveversion = message.getUserParameterValue("saveversion");
	        UnlockStartOrder = Integer.parseInt(message.getUserParameterValue("str_UnlockStartOrder"));
	        userId = Integer.parseInt(message.getUserParameterValue("userid"));
	        log.debug("生产单元号："+ProduceUnitID+"；生产日期："+str_date+"；是否保存版本："+str_saveversion+
	        		"；非锁定首顺序号："+UnlockStartOrder+"；用户号："+userId);
	        IDAO_UserManager dao_UserManager = DAOFactory_UserManager.getInstance(DataBaseType.getDataBaseType(con));
	        String sql = dao_UserManager.getSQL_SelectUserById(userId);
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	        log.debug("通过用户号查询用户对象SQL语句："+sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        if(rs.next()){
	        	username = rs.getString("CUSRNAME");
	        	log.debug("用户名："+username);
	        }
	        else{
	        	username = null;
	        	log.debug("用户名为空");
	        }
	        try {
				version.setVersionDatime((new SimpleDateFormat("yyyy-MM-dd").parse(str_date)));
				log.debug("版本日期："+version.getVersionDatime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    } catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常");
			return false;
		}
        return true;
    }

    /**
     * 执行服务
     *
     * @param message
     *              使用IMessage传递相关属性
     * @param processid
     *              流程ID
     * @return ExecuteResult
     *                  执行结果
     * @throws java.sql.SQLException
     *                          抛出SQL异常
     * @throws java.lang.Exception
     *                          抛出其他异常
     */
    @Override
    public ExecuteResult doAdapterService(IMessage message, String processid) throws SQLException, Exception {
        try {
			try {
				
				
				
				
				
				instructionFactory = new InstructionFactory();
				instructionCacheFactory = new InstructionCacheFactory();
				//通过生产单元号、生产日期、顺序号大于首个非编辑号来更改int_delete字段值为1
				instructionFactory.DeleteInstructionByProUnitDateworkOrderUnlockOrder(ProduceUnitID,str_date,workOrder,UnlockStartOrder,con);
				log.info("删除该生产单元、生产日期、未锁定顺序号指令成功");
				//根据生产时间和生产单元查看相关的指令。 
				list = instructionCacheFactory.getInstructionCacheByProduceUnitProduceDateOrder(ProduceUnitID, str_date,workOrder, con);
				
				log.info("查询该生产单元、生产日期的临时指令成功");
                for(Instruction in:list){
                	instructionFactory.saveInstruction(in, con);
                	
                }
                log.info("创建未锁定指令成功");
                //删除临时表中该生产单元所有数据
                instructionCacheFactory.DeleteInstructionCacheByProdUnitIdproducedateWorkorder(ProduceUnitID,str_date,workOrder,con);
               
                if(str_saveversion.equals("true")){
                	String code=null;
                	String first="01";
                	String second=null;
                	InstructionHistoryFactory historyfactory=new InstructionHistoryFactory();
                	// 查找最大的版本号
                	code = historyfactory.checkcodebyproduceunitanddateWorkorder(ProduceUnitID,str_date,workOrder, con);
                		if(!code.equals("")){
                			int leng=code.length();
                			//十位
                			String code1=code.substring(leng-2,leng-1);
                			//	个位
                			String code2=code.substring(leng-1,leng);
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
                		ProduceUnitFactory producefactory=new  ProduceUnitFactory();
                		ProduceUnit produceunit=new ProduceUnit();
                		List<Instruction> list = new ArrayList<Instruction>();
                		List<Instruction> list2 = new ArrayList<Instruction>();
                		Version version=new Version();
                		//得到生产单元id的生产单元名为创建版本做准备
                		produceunit=producefactory.getProduceUnitbyId(ProduceUnitID, con);
                		String str_name=produceunit.getStr_name();
                		//目的是为了分离出时间如20090403
                		String [] u1=new String [3];
                		u1 =str_date.split("-");
                		String ss= u1[0]+u1[1]+u1[2];
                		String s =ss+str_name+workOrder+first;
                		String str_versioncode1=ss+str_name+workOrder+second;
                		message.setOutputParameter("saveversion", s);
                		//生成版本号
                		str_versioncode=s;
                		version.setInt_delete(0);
                		version.setVersionCode(str_versioncode);
                		version.setProdunitid(ProduceUnitID);
                		version.setUser(username);
                		InstructionVersionFactory factory=new  InstructionVersionFactory();
                		InstructionFactory instructionfactory=new InstructionFactory ();
                		factory.saveVersion(version, con);
                		//根据生产时间和生产单元查看相关的指令
                		list2=instructionfactory.getInstructionByProduceUnitProduceDateWorkorder(ProduceUnitID,str_date,workOrder, con);
                		Iterator<Instruction> iter2=list2.iterator();
                		//更新指令版本
                		while(iter2.hasNext())
                		{ 
                			Instruction instruction=(Instruction)iter2.next();
                			//更新指令版本,当创建新的版本时调用,更新指令表中的版本字段
                			instructionfactory.updateInstructionVersioncode(instruction, str_versioncode1, con);
                			    	  
                		}
                		//根据生产时间和生产单元查看相关的指令
                		list=instructionfactory.getInstructionByProduceUnitProduceDateWorkorder(ProduceUnitID,str_date, workOrder,con);
                		InstructionHistoryFactory history=new InstructionHistoryFactory();
                		Iterator<Instruction> iter=list.iterator();
                		//插入指令版本历史表
                		while(iter.hasNext())
                		{ 
                			Instruction instruction=(Instruction)iter.next();
                			history.saveInstruction(instruction,str_versioncode, con);		   
                		}
					}
                else
                	   message.setOutputParameter("saveversion", "no");
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
}
