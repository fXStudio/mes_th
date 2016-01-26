package com.qm.mes.tg.service.noPedigreeRecord;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.tg.bean.NoPedigreeRecord;
import com.qm.mes.tg.factory.NoPedigreeRecordFactory;
import com.qm.mes.util.SerializeAdapter;
/**
 * 作者:谢静天
 * 更新采集点主物料的值
 * @param GatherRecord
 * @param con
 * @throws SQLException
 * 在gatherRecord_updating.jsp 页面用到
 * gatherRecord_editing.jsp 页面用到
 */
public class UpDateNoPedigreeRecord extends AdapterService{
	private Connection con = null;
	// 过点记录表的id
	private String int_gatherrecord_id = null;
	// 非谱系表的属性值
	private String str_value = null;
	// 非谱系表中的属性名
	private String str_gatherextname= null;
	//属性的个数
	private String attr_count = null;
	HashMap<String, String> attr_val = new HashMap<String, String>();
	SerializeAdapter sa = new SerializeAdapter();
	//日志
	private final Log log = LogFactory.getLog(UpDateNoPedigreeRecord.class);
	
	public boolean checkParameter(IMessage message, String processid) {

		con = (Connection) message.getOtherParameter("con");
		int_gatherrecord_id =message.getUserParameterValue("gatherrecordid");
		
		attr_count =message.getUserParameterValue("attr_count");
	
		try {
			attr_val=(HashMap<String, String>)sa.toObject(message.getUserParameterValue("str_attr_val2"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//输出log信息
	    String debug="过点记录号：" + int_gatherrecord_id + "；"+"采集点属性个数："+attr_count;
	    if(Integer.parseInt(attr_count)!=0)debug+=";采集点属性：\n";
	    for(int j=1;j<=Integer.parseInt(attr_count);j++){
	    	debug+="非谱系记录值："+attr_val.get("str_value"+ j)+";";
	    	debug+="采集点扩展属性名："+attr_val.get("str_name"+ j);
	    	if(j!=Integer.parseInt(attr_count))debug+=";\n";
	    }
	    log.debug("添加非谱系记录时用户提交的参数: " + debug);
	
		
		if (attr_count==null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			log.fatal("过点记录号、非谱系记录数量中有为空参数，退出服务。");
			return false;
		}
		

		return true;
	}
	
	public ExecuteResult doAdapterService(IMessage message, String processid)
	throws SQLException, Exception {
try {
	try {
		for (int i = 1; i <= Integer.parseInt(attr_count); i++) {
		
			 NoPedigreeRecord  nopedigreerecord = new  NoPedigreeRecord();
			 nopedigreerecord.setId(new Integer(attr_val.get("int_"+i)));
			 nopedigreerecord.setValue(attr_val.get("str_value"+ i));
			 nopedigreerecord.setGatheritemextname(attr_val.get("str_name"+ i));
		     NoPedigreeRecordFactory factory = new  NoPedigreeRecordFactory();
		  if(nopedigreerecord.getValue()!=null&&!nopedigreerecord.getValue().equals("")){
		     factory.updateNoPedigreeRecord( nopedigreerecord , con);
		  }
			log.info("更新非谱系记录服务成功！");
		}
	} catch (SQLException sqle) {
		message.addServiceException(new ServiceException(
				ServiceExceptionType.DATABASEERROR, "xie数据库操作异常", this.getId(), processid, new Date(), sqle));
		log.error("数据库异常，中断服务。");
		return ExecuteResult.fail;
	}
} catch (Exception e) {
	message.addServiceException(new ServiceException(
			ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
			processid, new java.util.Date(), e));
	log.error("未知异常，中断服务。");
	return ExecuteResult.fail;
}
return ExecuteResult.sucess;
}
	
	
	public void relesase() throws SQLException {
		 con = null;
	    int_gatherrecord_id = null;
	    str_value = null;
	    str_gatherextname = null;
		
	}
}
