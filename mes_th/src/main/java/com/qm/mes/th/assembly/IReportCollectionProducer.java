package com.qm.mes.th.assembly;

import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * 打印报表的创建工具
 * 
 * @author Ajaxfan
 */
public interface IReportCollectionProducer {
    /**
     * 生产打印报表集合
     * 
     * @param orderList
     * @return
     */
    public List<JasperPrint> product(List<IReportOrder> orderList);
}
