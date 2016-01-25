package mes.system.services.materialtype;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.system.elements.IMaterialType;
import mes.system.factory.FactoryAdapter;
import mes.system.factory.IMaterialTypeFactory;

/**
 * 
 * @author 于丽达
 * 
 */

public class Service_AddMaterialType extends AdapterService {
	// 获得连接
	private Connection con = null;

	// 获得物料类型配置工厂
	private IMaterialTypeFactory factory;

	// 物料类型名,在元素表中
	private String element_name = null;

	// 父物料号
	private String parent_id = null;

	// 描述信息
	private String description = null;

	// 操作用户
	private String userid = null;

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
		element_name = message.getUserParameterValue("element_name");
		parent_id = message.getUserParameterValue("parent_id");
		description = message.getUserParameterValue("description");
		userid = message.getUserParameterValue("userid");
		System.out
				.println("类中： " + "element_name = " + element_name + "  "
						+ "parent_id = " + parent_id + "  description = "
						+ description);
		if (element_name == null || userid == null) {
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
			Statement stmt = null;
			try {
				factory = (IMaterialTypeFactory) FactoryAdapter
						.getFactoryInstance(IMaterialTypeFactory.class
								.getName());
				stmt = con.createStatement();
				IMaterialType newType = factory.createElement();
				newType.setParentId(new Integer(message
						.getUserParameterValue("parent_id")));
				newType.setName(message.getUserParameterValue("element_name"));
				newType.setDescription(message
						.getUserParameterValue("description"));
				newType.setUpdateUserId(new Integer(message
						.getUserParameterValue("userid")));
				stmt.close();
				factory.save(newType, con);
			} catch (SQLException sqle) {
				message.addServiceException(new ServiceException(
						ServiceExceptionType.DATABASEERROR, "数据库操作异常", this
								.getId(), processid, new Date(), null));
				return ExecuteResult.fail;
			} finally {
				if (stmt != null)
					stmt.close();
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
		parent_id = null;
		userid = null;
		description = null;
		con = null;
	}

}
