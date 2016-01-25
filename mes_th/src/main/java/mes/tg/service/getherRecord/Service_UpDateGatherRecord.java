package mes.tg.service.getherRecord;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.tg.bean.*;
import mes.tg.factory.RecordFactory;
import mes.util.SerializeAdapter;
/**
 * 作者:谢静天
 * 更新采集点主物料的值
 * @param GatherRecord
 * @param con
 * @throws SQLException
 * 在gatherRecord_updating.jsp 页面用到
 * 
 */
public class Service_UpDateGatherRecord extends AdapterService {
	private Connection con = null;
	   //过点记录的id
	private String int_id;
	//主物料的值
	private String str_materielvalue;
	 //子物料的个数
	private String sub_account;
	HashMap<String, String> attr_val = new HashMap<String, String>();
	SerializeAdapter sa = new SerializeAdapter();
	//日志
	private final Log log = LogFactory.getLog(Service_UpDatePedigreeRecord.class);

	public boolean checkParameter(IMessage message, String processid) {
	
		con = (Connection) message.getOtherParameter("con");
		int_id = message.getUserParameterValue("int_id");
		str_materielvalue = message.getUserParameterValue("str_materielvalue");
		sub_account = message.getUserParameterValue("sub_account");
		
		try {
			//将参数转换为Map类型
			attr_val = (HashMap<String, String>) sa.toObject(message
					.getUserParameterValue("str_attr_val"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ee) {
			ee.printStackTrace();
		}
		
		if (int_id == null || str_materielvalue == null) {
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
				GatherRecord GatherRecord = new GatherRecord();
				GatherRecord.setId(new Integer(int_id));
				GatherRecord.setMaterielValue(str_materielvalue);
				
				RecordFactory factory = new RecordFactory();
				factory.upDateRecord(GatherRecord, con);
				log.info("更新过点记录成功");
				//将Map中参数取出并更新谱系记录
				for (int i = 1; i <= Integer.parseInt(sub_account); i++) {
					PedigreeRecord PedigreeRecord = new PedigreeRecord();
					PedigreeRecord.setMaterielValue(attr_val.get("subMateriel"
							+ i));
					PedigreeRecord.setId(new Integer(attr_val.get("valuesid"
							+ i)));
					log.debug("子物料值："+PedigreeRecord.getMaterielValue()+"；谱系记录号："+PedigreeRecord.getId());
					RecordFactory factory1 = new RecordFactory();
					factory1.upDatePedigreeRecord(PedigreeRecord, con);
					log.info("更新谱系记录成功");
				}
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

	public void relesase() throws SQLException {
		con = null;
		int_id = null;
		str_materielvalue = null;

	}

}
