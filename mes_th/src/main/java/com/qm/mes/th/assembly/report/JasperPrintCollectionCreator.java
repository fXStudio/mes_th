package com.qm.mes.th.assembly.report;

import java.util.List;

import com.qm.mes.th.assembly.IReportCollectionCreator;
import com.qm.mes.th.assembly.IReportOrder;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * 打印报表工程
 * 
 * @author Ajaxfan
 */
public class JasperPrintCollectionCreator implements IReportCollectionCreator {
    /**
     * 根据需要的报表类型，将订单交派由指定的生产者进行生产
     * 
     * @param collectionType
     *            需要的报表集合类型
     * @param orderList
     *            订单信息
     * @return
     */
    @Override
    public List<JasperPrint> getJasperPrintCollection(String code, List<IReportOrder> orderList) {
        if ("0".equals(code)) {// 简单报表（即所有数据只打在一个页内）
            return new SimpleAccemblyCollectionProducer().product(orderList);
        } else if ("1".equals(code) || "2".equals(orderList)) {// 多页报表（即打印分为配置单和追溯单）
            return new MutiplePageAccemblyCollectionProducer().product(orderList);
        }
        // 多列报表（即一个单子上有多个零件信息）
        return new MutipleColumnAssemblyCollectionProducer().product(orderList);
    }
}
