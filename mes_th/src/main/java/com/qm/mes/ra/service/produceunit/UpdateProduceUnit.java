package com.qm.mes.ra.service.produceunit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.ra.bean.ProduceUnit;
import com.qm.mes.ra.factory.ProduceUnitFactory;

/**更改生产单元
 * @author 谢静天
 *
 */
public class UpdateProduceUnit extends AdapterService{
	private final Log log = LogFactory.getLog(UpdateProduceUnit.class);
	private Connection con=null;
	private String int_id=null;
	/**
	 * 生产单元名称
	 */
	private String Str_name=null;
	/**
	 * 生产单元编号
	 */
	private String Str_code=null;
	/**
	 * 锁定台份个数
	 */
	private String Int_instCount=null;
	/**
	 * 未上线指令状态
	 */
	private String Int_instructStateID=null;
    /**
     * 是否需要验证计划信息
     */
    private String Int_planIncorporate=null;
    private String int_materielruleid=null;
	public boolean checkParameter(IMessage message, String processid) {
		con=(Connection)message.getOtherParameter("con");
		int_id=message.getUserParameterValue("int_id");
		Str_name=message.getUserParameterValue("Str_name");
		Str_code=message.getUserParameterValue("Str_code");
		Int_instructStateID=message.getUserParameterValue("Int_instructStateID");
		Int_planIncorporate=message.getUserParameterValue("Int_planIncorporate");
		Int_instCount=message.getUserParameterValue("Int_instCount");
		int_materielruleid=message.getUserParameterValue("int_materielruleid");
		//输出log信息
	    String debug="状态名：Int_instructStateID:" +Int_instructStateID 
		+ " Int_planIncorporate:"+Int_planIncorporate+ " Int_instCount:"+Int_instCount+ "\n";
	    log.info("添加状态转换的参数: " + debug);
		if (Str_name == null) {
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
			ProduceUnit produceUnit=new ProduceUnit();
			produceUnit.setInt_id(Integer.parseInt(int_id));
			produceUnit.setStr_name(Str_name);
			produceUnit.setStr_code(Str_code);
			produceUnit.setInt_instructStateID(Integer.parseInt(Int_instructStateID));
			produceUnit.setInt_planIncorporate(Integer.parseInt(Int_planIncorporate));
			produceUnit.setInt_instCount(Integer.parseInt(Int_instCount));
			produceUnit.setInt_materielRuleid(Integer.parseInt(int_materielruleid));
			ProduceUnitFactory factory=new ProduceUnitFactory();
			factory.updateProduceUnit(produceUnit, con);
			log.debug( "添加更改单元工厂成功!");
		}
		catch (SQLException sqle) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
					.getId(), processid, new Date(), sqle));
			log.error("更改单元操作时,数据库异常"	+ sqle.toString());
			return ExecuteResult.fail;
		}
		catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error( "更改单元操作时,未知异常" + e.toString());
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}
	public void relesase() throws SQLException {
	    con = null;
	    Str_name=null;
	    Str_code=null;
	    Int_instructStateID=null;
	    Int_planIncorporate=null;
	    Int_instCount=null;
    }
}
