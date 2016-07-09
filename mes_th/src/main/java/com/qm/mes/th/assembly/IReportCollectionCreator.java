package com.qm.mes.th.assembly;

import java.util.List;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * 报表生成工具的工厂类
 * 
 * @author Ajaxfan
 */
public interface IReportCollectionCreator {
    public List<JasperPrint> getJasperPrintCollection(String code, List<IReportOrder> orderList);
}
