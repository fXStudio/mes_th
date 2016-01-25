package mes.pm.service.distributionaccept;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.factory.DistributionAcceptFactory;
import mes.util.SerializeAdapter;

/**
 * 处理配送确认单
 *
 * @author YuanPeng
 * 别名   TransactDisAccept
 */
public class TransactDistributionAccept extends AdapterService {
    /**
     * 数据库连接对象
     */
    private Connection con;
    /**
     * 把并行数据变成串行数据的寄存器
     */
	private SerializeAdapter sa = null;
    /**
     * 整型数组
     */
    int int_array[] ;
    /**
     * 选中的数组长度
     */
    private int array_length ;
    /**
     * 响应用户
     */
    private int userid;
	//日志
	private final Log log = LogFactory.getLog(TransactDistributionAccept.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		con = (Connection) message.getOtherParameter("con");
        sa = new SerializeAdapter();
                //获取数组长度
                array_length = Integer.parseInt(message.getUserParameterValue("arr_length"));
                int_array = new int[array_length];
                userid = Integer.parseInt(message.getUserParameterValue("userid"));
        try {
            //将字符串转换成整型数组类型
            int_array = (int[]) sa.toObject(message.getUserParameterValue("str_array"));
            
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        for(int i =0 ;i<array_length;i++){
        	String debug = "";
        	if(i!=0||i!=array_length)debug+="；";
        	debug+="第"+i+"个序号为："+int_array[i];
        	log.debug(debug);
        }
        return true;
	}

	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
				DistributionAcceptFactory factory = new DistributionAcceptFactory();
                for(int j:int_array){
                	factory.transactDistributionAccept(j,userid, con);
                	log.info("序号为:"+j+"的确认单已被处理完毕");
                }
                log.info("通过序号处理配送确认单成功");
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

	@Override
	public void relesase() throws SQLException {
		con = null;
	}

}

