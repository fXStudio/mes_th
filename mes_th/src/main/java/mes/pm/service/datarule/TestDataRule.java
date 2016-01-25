package mes.pm.service.datarule;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.MissingResourceException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import mes.framework.AdapterService;
import mes.framework.ExecuteResult;
import mes.framework.IMessage;
import mes.framework.ServiceException;
import mes.framework.ServiceExceptionType;
import mes.pm.factory.DataRuleFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据规则校验
 * @author Xujia
 * 
 */
public class TestDataRule extends AdapterService {
	/**
	 * 规则公式
	 */
	private String rule = null;
	/**
	 * int_id
	 */
	private String int_id = null;
	/**
	 * 参数
	 */
	private String args = null;
	/**
	 *  参数数量
	 */
	private String count = null;
	/**
	 * 名称
	 */
	private String name = null;

	BigDecimal result1 = new BigDecimal("0");

	DataRuleFactory factory = new DataRuleFactory();

	// 日志
	private final Log log = LogFactory.getLog(TestDataRule.class);

	@Override
	public boolean checkParameter(IMessage message, String processid) {
		// con = (Connection) message.getOtherParameter("con");
		args = message.getUserParameterValue("args"); // 参数组合
		rule = message.getUserParameterValue("rule"); // 规则公式
		int_id = message.getUserParameterValue("int_id");
		count = message.getUserParameterValue("count"); // 参数数量
		name = message.getUserParameterValue("name"); // 函数名

		// 输出log信息
		String debug = "规则公式：" + rule + " 参数为：" + args + " 参数个数为：" + count
				+ " name为" + name + " 序号为：" + int_id;
		log.debug("添加数据规则时用户提交的参数: " + debug);

		String[] newargs = args.split(";"); // 拆分成组
		String func = ""; // 函数名
		Double[] tt = new Double[Integer.parseInt(count)]; // 参数数组
		for (int j = 0; j < newargs.length; j++) {

			int size = newargs[j].lastIndexOf(","); // 拆分成变量名和数的形式
			String start = newargs[j].substring(0, size).trim(); // 取变量名
			String end = newargs[j].substring(size + 1, newargs[j].length())
					.trim(); // 取参数
			func = func + start + ",";
			tt[j] = Double.parseDouble(end);
			log.info("start:" + start + " end:" + end + " func:" + func
					+ "tt[j]=" + tt[j]);
			// rule=rule.replace(start,end); //将公式中的参数用实际输入的数替换
		}
		func = func.substring(0, func.length() - 1);
		log.info("fun:" + func);
		// log.info("最终公式为："+"\n"+"\n"+rule+"\n");
		Object obj = null;
		String script = "function " + name + "(" + func + ")" + "{ " + rule
				+ "}";
		log.info("script: " + script);
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");

		try {
			engine.eval(script);
			Invocable inv = (Invocable) engine;
			// String [] tt = {"3","1"};
			obj = inv.invokeFunction(name, tt);					
			// System.out.println("2***");
		} catch (ScriptException e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, "脚本异常:出现在"
							+ e.getLineNumber() + "行，第" + e.getColumnNumber()
							+ "列。异常原信息：" + e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("脚本异常，中断服务。");
			return false;
		} 
		catch(MissingResourceException a){
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, "参数命名和规则不匹配异常。问题参数名："
							+ a.getKey()
							+ "。", this.getId(),
					processid, new java.util.Date(), a));
			log.error("参数和规则不匹配异常，中断服务。");
			return false;
		}catch (NoSuchMethodException b) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, "缺少属性/方法异常。", this.getId(),
					processid, new java.util.Date(), b));
			log.error("缺少属性/方法异常，中断服务。");
			return false;
		}

		message.setOutputParameter("result", String.valueOf(obj));

		return true;
	}

	
	@Override
	public ExecuteResult doAdapterService(IMessage message, String processid)
			throws SQLException, Exception {
		try {

			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, "公式为：" + rule, this.getId(),
					processid, new Date(), null));

		} catch (Exception e) {
			message.addServiceException(new ServiceException(
					ServiceExceptionType.UNKNOWN, e.toString(), this.getId(),
					processid, new java.util.Date(), e));
			log.error("未知异常，中断服务。");
			return ExecuteResult.fail;
		}
		return ExecuteResult.sucess;
	}

	@Override
	public void relesase() throws SQLException {

		int_id = null;
		args = null;
		count = null;
		rule = null;
		// con = null;

	}

}
