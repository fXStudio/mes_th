package mes.ra.service.instruction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import mes.framework.AdapterService;
import mes.framework.DataBaseType;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.ra.dao.DAO_Instruction_cache;
import mes.ra.factory.InstructionCacheFactory;
import mes.system.dao.DAOFactoryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**拆分指令
 * @author XuJia
 */

public class BreakInstruction extends AdapterService {
	/**
	 *获得连接
	 */ 
	private Connection con = null;
	/**
	 *分得的第一个数据
	 */
	private String count1= null;
	/**
	 *总数
	 */
	private String count= null;
	/**
	 *产生指令的标识号
	 */
	private String Str_produceMarker = null;
	/**
	 *目标指令的顺序号
	 */
	private String order = null;
	/**
	 *生产日期
	 */
	private String str_date=null;
	/**
	 *生产单元序号
	 */
	private String ProduceUnitID=null;
	/**
	 *班次
	 */
	private String workOrder=null;

	ResultSet rs=null;
	ResultSet rs1 = null;
	Statement stmt = null;
	int num = 0;
	int i;
	int d;
	int number;

	private final Log log = LogFactory.getLog(BreakInstruction.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {

		try {
			str_date=message.getUserParameterValue("str_date");
			workOrder=message.getUserParameterValue("workOrder");
			ProduceUnitID=message.getUserParameterValue("ProduceUnitID");
			con = (Connection) message.getOtherParameter("con"); 
			stmt = con.createStatement();   //初始化
			count1 = message.getUserParameterValue("count1"); //分得的第一个数据
			count = message.getUserParameterValue("count");   //总数
			Str_produceMarker = message.getUserParameterValue("Str_produceMarker");  //产生指令的标识号
			order = message.getUserParameterValue("order");  //目标指令的顺序号
			d=(Integer.parseInt(count))-(Integer.parseInt(count1));  //计算出产生指令的数量
			
			// 输出log信息
			String debug = "数量1：count1=" + count1 + "\n" + "标识：Str_produceMarker=" + Str_produceMarker + "\n";
			log.debug("添加流程服务时用户提交的参数: " + debug);
							
			return true;		
		} catch (SQLException e) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			e.printStackTrace();
			return true;
		}
	}
	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {
			try {
												
				int max=0;
				stmt = con.createStatement(); 
				DAO_Instruction_cache dao = (DAO_Instruction_cache) DAOFactoryAdapter.getInstance(
				  DataBaseType.getDataBaseType(con), DAO_Instruction_cache.class);		
				// 更新选定指令的数量				
				InstructionCacheFactory instructionCacheFactory = new InstructionCacheFactory();
				con = (Connection) message.getOtherParameter("con");
				instructionCacheFactory.updateInstructionCacheNum((Integer.parseInt(count1)),(Integer.parseInt(order)), con,str_date,(Integer.parseInt(ProduceUnitID)),workOrder);
				// 查找最大顺序号				
				String sql1=dao.getInstructionMaxOrder((Integer.parseInt(ProduceUnitID)),str_date,workOrder);
				log.debug("查找指令最大顺序号SQL语句："+sql1);
				rs1=stmt.executeQuery(sql1);
				if(rs1.next()){
					max=rs1.getInt(1);
					log.debug("最大顺序号为："+max);
				}
				// 将后面的指令号加1
				for(i=max;i>=(Integer.parseInt(order)+1);i--){  
					String sql4=dao.selectInstructionCacheid(i,str_date,(Integer.parseInt(ProduceUnitID)),workOrder);
					log.debug("通过顺序号、生产单元、生产日期、班次查询临时指令SQL语句："+sql4);
					rs=stmt.executeQuery(sql4);
					if (rs.next())
						instructionCacheFactory.updateInstructionCacheOrder(i, con,str_date,(Integer.parseInt(ProduceUnitID)),workOrder);
					log.info("更新临时指令顺序号成功");
				} 
				
				// 插入分解后的指令
				instructionCacheFactory.insertInstructionCache((Integer.parseInt(order)+1),Str_produceMarker,d,(Integer.parseInt(order)), con);
				log.info("创建临时指令成功");
				return ExecuteResult.sucess;
				
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
		} finally {
			if (rs1 != null)
				rs1.close();
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
		}

	@Override
	public void relesase() throws SQLException {
		order = null;
		count1 = null;
		Str_produceMarker=null;
		con = null;

	}

}
