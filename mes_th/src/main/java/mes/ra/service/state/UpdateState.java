package mes.ra.service.state;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.ra.bean.State;
import mes.ra.factory.StateFactory;

/**
 * 修改指令状态信息
 * 
 * @author xujia
 * 
 */
public class UpdateState extends AdapterService {
	/**
	 * 获得连接
	 */
	private Connection con = null;
	/**
	 * 状态名
	 */
	private String string_statename = null;
	/**
	 * 样式
	 */
	private String string_style = null;	 
	/**
	 * 样式描述
	 */
	private String string_styledesc= null;
	private String int_id=null;
	StateFactory factory = new StateFactory();
	private final Log log = LogFactory.getLog(UpdateState.class);
	@Override
	public boolean checkParameter(IMessage message, String processid) {
		
		con = (Connection) message.getOtherParameter("con");
		string_statename = message.getUserParameterValue("string_statename").trim();
		string_styledesc = message.getUserParameterValue("string_styledesc");
		int_id=message.getUserParameterValue("int_id");
		//输出log信息
	    String debug="状态名：string_statename:" + string_statename 
		+ "  样式代码：string_style:"+string_styledesc+ "\n";
	    log.info("更新状态传的参数为: " + debug);
		string_style = message.getUserParameterValue("string_style");
		if (string_statename == null || string_style == null) {
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
				State state = new State();
				state.setId(Integer.parseInt(int_id));
				state.setStateName(string_statename);
				state.setStyledesc(string_styledesc);
				state.setStyle(string_style);
				state.setDelete(0);
				factory.updateState(state, con);
				log.debug( "创建工厂成功");
				
		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error( "添加状态操作时,未知异常" + e.toString());
			
			
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {
		string_statename = null;
		string_style = null;
		con = null;

	}

}
