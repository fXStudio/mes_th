package mes.ra.service.version;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import mes.ra.bean.*;
import mes.ra.factory.*;
import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**添加版本
 * @author 包金旭
 *
 */
public class AddVersion extends AdapterService {
	private Connection con=null;
	/**
	 * 版本号
	 */
	private String str_versioncode=null;
	/**
	 * 生产单元
	 */
	private String str_produceunit=null;
	/**
	 * 版本创建人
	 */
	private String str_user=null;
	/**
	 * 生产时间
	 */
	private String Dat_produceDate=null;
	private final Log log = LogFactory.getLog(AddVersion.class);
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		str_produceunit=message.getUserParameterValue("str_produceunit");
		str_user=message.getUserParameterValue("str_user");
		Dat_produceDate=message.getUserParameterValue("Dat_produceDate");
		//输出log信息
		String debug="str_produceunit:" + str_produceunit 
			+ "str_user:"+str_user+ "Dat_produceDate"+Dat_produceDate+ "\n";
		    log.info("添加状态转换的参数: " + debug);
		if (str_produceunit== null||Dat_produceDate==null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			return false;
		}
		return true; 
     }
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
try {
	try {
		  String code=null;
		  String first="01";
		  String second=null;
		
		  if(!code.equals("")){
			  int leng=code.length();
			  //十位
			  String code1=code.substring(leng-2,leng-1);
		      //个位
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
		produceunit=producefactory.getProduceUnitbyId(Integer.parseInt(str_produceunit), con);
		String str_name=produceunit.getStr_name();
		//目的是为了分离出时间如20090403
		String [] u1=new String [3];
	    u1 =Dat_produceDate.split("-");
	    String ss= u1[0]+u1[1]+u1[2];
		String s =ss+str_name+first;
		String str_versioncode1=ss+str_name+second;
		//生成版本号
		str_versioncode=s;
		version.setInt_delete(0);
		version.setVersionCode(str_versioncode);
		version.setProdunitid(Integer.parseInt(str_produceunit));
		version.setUser(str_user);
		InstructionVersionFactory factory=new  InstructionVersionFactory();
		InstructionFactory instructionfactory=new InstructionFactory ();
		factory.saveVersion(version, con);
		Iterator<Instruction> iter2=list2.iterator();
		//更新指令版本
		while(iter2.hasNext())
		{ 
			Instruction instruction=(Instruction)iter2.next();
		    instructionfactory.updateInstructionVersioncode(instruction, str_versioncode1, con);
		 }
		 InstructionHistoryFactory history=new InstructionHistoryFactory();
		 Iterator<Instruction> iter=list.iterator();
		 //插入指令版本历史表
		 while(iter.hasNext())
		 { 
			 Instruction instruction=(Instruction)iter.next();
			 history.saveInstruction(instruction,str_versioncode, con);  
		 }
		     
		} catch (SQLException sqle) {
			message.addServiceException(new ServiceException(
				ServiceExceptionType.UNKNOWN, sqle.toString(), this.getId(),
				processid, new java.util.Date(), sqle));
			log.error("添加版本操作时,数据库异常"	+ sqle.toString());
			return ExecuteResult.fail;
		}
	} catch (Exception e) {
		message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
		log.error( "添加版本操作时,未知异常" + e.toString());
		return ExecuteResult.fail;
	}
	return ExecuteResult.sucess;
}

   @Override
   public void relesase() throws SQLException {
   	 con = null;
   	 str_versioncode=null;
 	//生产单元
 	 str_produceunit=null;
 	//版本创建人
 	 str_user=null;
   	
   }
}
