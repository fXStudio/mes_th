package com.qm.mes.th.assembly.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import com.qm.mes.th.assembly.IReportCollectionProducer;
import com.qm.mes.th.assembly.IReportOrder;
import com.qm.mes.th.assembly.helper.JasperTemplateLoader;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import th.pz.bean.JConfigure;

/**
 * 创建简单报表
 * 
 * @author Ajaxfan
 */
class MutiplePageAccemblyCollectionProducer implements IReportCollectionProducer {
	/**
	 * 打印报表的生产方法
	 * 
	 * @param orderList
	 * @return
	 */
	@Override
	public List<JasperPrint> product(List<IReportOrder> orderList) {
		List<JasperPrint> list = new ArrayList<JasperPrint>();

		try {
			for (IReportOrder order : orderList) {
				list.add(createMainJasperPrint(order));
				list.addAll(createTraceJasperPrints(order));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public JasperPrint createMainJasperPrint(IReportOrder order) throws Exception {
		// 报表参数
		Map<String, Object> parameters = new HashMap<String, Object>();

		// 定义datasource;
		parameters.put("js", String.valueOf(order.getReportBaseInfo().getCarno()));
		parameters.put("zrq", order.getRequestParam().getRequestDate());
		parameters.put("ch", order.getReportBaseInfo().getCarno());
		parameters.put("tm", order.getReportBaseInfo().getChassisNumber());
		parameters.put("mc", order.getPrintSet().getCTFASSName());
		parameters.put("id", order.getPrintSet().getId());
		parameters.put("SUBREPORT_DIR", JasperTemplateLoader.BASE_PATH);

		// 数据拆分
		parameters.put("dataSource", getSubList(order.getDatas(), 0, 8));
		parameters.put("dataSource1", getSubList(order.getDatas(), 8, 16));
		parameters.put("dataSource2", getSubList(order.getDatas(), 16, 24));
		parameters.put("totalDatasource", createTotalDataList(order.getDatas()));

		return createJasperPrints(order.getPrintSet().getCPrintMD(), parameters);
	}

	/**
	 * 生成数据汇总
	 * 
	 * @param datas
	 * @return
	 */
	private Object createTotalDataList(List<JConfigure> datas) {
		Map<String, Integer> map = new TreeMap<String, Integer>();

		// 数据分类汇总
		for (JConfigure obj : datas) {
			// 记录的数量
			int count = 0;

			// 如果分类信息存在，则记录数量递增
			if (map.containsKey(obj.getCQADNo())) {
				count = map.get(obj.getCQADNo());
			}
			map.put(obj.getCQADNo(), ++count);
		}
		// 统计信息列表
		List<DynaBean> list = new ArrayList<DynaBean>();

		for (String key : map.keySet()) {
			DynaBean bean = new LazyDynaBean();
			bean.set("itemTotal", key + ":  " + map.get(key));

			list.add(bean);
		}
		return list;
	}

	/**
	 * 追溯表单
	 * 
	 * @param reportBaseInfo
	 * @param printSet
	 * @param requestParam
	 * @param dataset
	 * @return
	 * @throws Exception
	 */
	private List<JasperPrint> createTraceJasperPrints(IReportOrder order) throws Exception {
		// 要打印的报表集合
		List<JasperPrint> list = new ArrayList<JasperPrint>();

		// 只有满足特定编码的零件才能打印追溯单
		if ("1".equals(order.getPrintSet().getCCode())) {
			// 报表参数
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("js", String.valueOf(order.getReportBaseInfo().getCarno()));
			parameters.put("zrq", order.getRequestParam().getRequestDate());
			parameters.put("mc", order.getPrintSet().getCTFASSName());
			parameters.put("SUBREPORT_DIR", JasperTemplateLoader.BASE_PATH);

			for (int i = 0, n = 0; i < 2; i++) {// 追溯单每种零件一共两张单子
				for (int j = 1; j <= 2; j++) {// 每个单子打印两组数据
					parameters.put("dataSource" + j, getSubList(order.getDatas(), n * 6, (++n) * 6));
				}
				JasperPrint print = null;
				if (order.getPrintSet().getCPrintMD().contains("ca3")) {
					print = createJasperPrints("new_qnfxpzs_ca3.jasper", parameters);
				} else {// 因为追溯单是一种类型两件，分开两张单子打印，所有需要做数据拆分，而后生成打印报表
					print = createJasperPrints("new_qnfxpzs.jasper", parameters);
				}
				// 追溯单不能重复打印
				print.setProperty("repeat", "false");

				list.add(print);
			}
		}
		return list;
	}

	/**
	 * 获取子数据集和
	 * 
	 * @param configures
	 * @param percent
	 *            第几份
	 * @param all
	 *            总份数
	 * @return
	 */
	private List<JConfigure> getSubList(List<JConfigure> configures, int start, int end) {
		int size = configures.size();// 数据集合的大小
		end = Math.min(size, end);// 切分集合的结束索引

		if (size >= start) {
			return configures.subList(start, end);
		}
		return Collections.emptyList();
	}

	/**
	 * @param conn
	 * @param printSet
	 * @param reportBaseInfo
	 * @param dataset
	 * @param requestParam
	 * @return
	 */
	private JasperPrint createJasperPrints(String printMd, Map<String, Object> parameters) throws Exception {
		return JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(JasperTemplateLoader.load(printMd)),
		        parameters, new JREmptyDataSource());
	}
}
