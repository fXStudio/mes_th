package com.qm.mes.th.assembly.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qm.mes.th.assembly.IReportCollectionProducer;
import com.qm.mes.th.assembly.IReportOrder;
import com.qm.mes.th.assembly.helper.JasperTemplateLoader;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import th.pz.bean.JConfigure;

/**
 * 创建简单报表
 * 
 * @author Ajaxfan
 */
class SimpleAccemblyCollectionProducer implements IReportCollectionProducer {
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
                list.add(createJasperPrint(order));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 创建打印模板
     * 
     * @param printOrder
     * @return
     * @throws JRException
     */
    protected JasperPrint createJasperPrint(IReportOrder order) throws JRException {
        // 报表参数模型
        Map<String, Object> parameters = new HashMap<String, Object>();

        // 定义datasource;
        parameters.put("js", String.valueOf(order.getReportBaseInfo().getCarno()));
        parameters.put("zrq", order.getRequestParam().getRequestDate());
        parameters.put("ch", order.getReportBaseInfo().getCarno());
        parameters.put("tm", order.getReportBaseInfo().getChassisNumber());
        parameters.put("mc", order.getPrintSet().getCCarTypeDesc());
        parameters.put("id", order.getPrintSet().getId());
        parameters.put("SUBREPORT_DIR", JasperTemplateLoader.BASE_PATH);

        // 生成报表模板
        JasperReport jasperReport = (JasperReport) JRLoader
                .loadObject(JasperTemplateLoader.load(order.getPrintSet().getCPrintMD()));

        // 生成并返回打印对象
        return createJasperPrint(jasperReport, parameters, order.getDatas());

    }

    private JasperPrint createJasperPrint(JasperReport jasperReport, Map<String, Object> parameters,
            List<JConfigure> datas) throws JRException {
        if (datas.size() > 0) {
            return JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(datas));
        }
        return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
    }
}
