package com.qm.mes.system.services.materialtype;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.qm.mes.framework.AdapterService;
import com.qm.mes.framework.ExecuteResult;
import com.qm.mes.framework.IMessage;
import com.qm.mes.framework.ServiceException;
import com.qm.mes.framework.ServiceExceptionType;
import com.qm.mes.system.factory.FactoryAdapter;
import com.qm.mes.system.factory.IMaterialTypeFactory;

/**
 * @author 于丽达
 * 
 */

public class Service_DeleteMaterialType extends AdapterService {
	// 获得连接
	private Connection con = null;

	// 获得物料类型配置工厂
	private IMaterialTypeFactory factory;

	// 物料类型名,在元素表中
	private String element_name = null;

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		element_name = message.getUserParameterValue("element_name");
		System.out.println("类中： " + "element_name = " + element_name);
		if (element_name == null) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.PARAMETERLOST, "输入参数为空", this.getId(),
					processid, new java.util.Date(), null));
			return false;
		}
		return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				factory = (IMaterialTypeFactory) FactoryAdapter
						.getFactoryInstance(IMaterialTypeFactory.class
								.getName());
				factory.deleteElement(new Integer(message
						.getUserParameterValue("element_name")), con);
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), null));
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
		element_name = null;
		con = null;
	}

}