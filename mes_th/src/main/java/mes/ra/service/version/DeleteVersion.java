package mes.ra.service.version;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.*;
import mes.ra.bean.*;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.ra.factory.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**删除版本
 * @author 包金旭
 *
 */
public class DeleteVersion extends AdapterService {
	private Connection con=null;
	/**
	 * 版本号
	 */
	private String int_id=null;
	private final Log log = LogFactory.getLog(DeleteVersion.class);
	public boolean checkParameter(IMessage message, String processid) {
		 try{
			con = (Connection) message.getOtherParameter("con");
			int_id=message.getUserParameterValue("int_id");
			InstructionVersionFactory factory=new InstructionVersionFactory();
			Version version=new Version();
			version =factory.getversionById(Integer.parseInt(int_id), con);
			String str_versioncode= version.getVersionCode();
			//先删除指令历史表 再删除指令版本表
			List<Instruction> list = new ArrayList<Instruction>();
			Instruction instruction = new Instruction();
			InstructionHistoryFactory historyFactory=new InstructionHistoryFactory();
			list= historyFactory.getInstructionbyversioncode(str_versioncode, con);
			instruction =list.get(0);
			String date=null;
			date=(new SimpleDateFormat("yyyy-MM-dd")).format( instruction.getProduceDate());
			Calendar calendar=Calendar.getInstance();
	        calendar.setTime(new Date());
	        int hour=calendar.get(Calendar.HOUR_OF_DAY);
	        String year=String.valueOf(calendar.get(Calendar.YEAR));
	        String month=String.valueOf(calendar.get(Calendar.MONDAY)+1);
	        if(calendar.get(Calendar.MONDAY)+1<10){
	        	month="0"+month;
	        }
	        String day=null;
	        String str_date=null;
	        if(hour>=8)
	        {
	        	day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	      	   	if(calendar.get(Calendar.DAY_OF_MONTH)<10){
	      		   day="0"+day;
	      	   	}
	      	   	str_date=year+"-"+month+"-"+day;
	        }
	        else
	        { 
	      	   day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)-1);
	      	   str_date=year+"-"+month+"-"+day;
	        }
	        if(date.equals(str_date)){
	        		message.addServiceException(new ServiceException(
							ServiceExceptionType.PARAMETERLOST, "不能删除当天的指令版本", this.getId(),
							processid, new java.util.Date(), null));
	        		log.error("不能删除当天的指令版本");
					return false;
	        }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
			
		if (int_id== null) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
						processid, new java.util.Date(), null));
				log.error("输入参数为空");
				return false;
		}
		return true;
	 }
	 

	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
	try {
		try {
			 InstructionVersionFactory factory=new InstructionVersionFactory();
			 Version version=new Version();
			 version =factory.getversionById(Integer.parseInt(int_id), con);
			 String str_versioncode= version.getVersionCode();
			 //先删除指令历史表 再删除指令版本表
			 InstructionHistoryFactory historyFactory=new InstructionHistoryFactory();
			 historyFactory.deleteInstructionhistoryBycode(str_versioncode, con);
			 factory.delVersion(Integer.parseInt(int_id), con);
			 log.debug( "创建工厂成功!");
		} catch (SQLException sqle) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
					.getId(), processid, new Date(), sqle));
			log.error("删除版本操作时,数据库异常"	+ sqle.toString());
			return ExecuteResult.fail;
		}
	} catch (Exception e) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
				processid, new java.util.Date(), e));
		log.error( "删除版本操作时,未知异常" + e.toString());
		return ExecuteResult.fail;
	}
	return ExecuteResult.sucess;
}
	 
	 @Override
	   public void relesase() throws SQLException {
	   	    con = null;
	        int_id=null;
	   	
	   }
}
