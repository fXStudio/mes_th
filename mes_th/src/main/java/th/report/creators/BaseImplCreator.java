package th.report.creators;

import java.sql.ResultSet;

import th.pz.bean.PrintSet;
import th.report.api.IJasperPrintCreator;
import th.report.api.IPrintDataSaver;
import th.report.api.IReportBaseInfoFacade;
import th.report.api.IReportDataSetFacade;
import th.report.facades.ReportBaseInfoFacade;
import th.report.facades.ReportDataSetFacade;
import th.report.persistens.PrintDataSaver;

abstract class BaseImplCreator implements IJasperPrintCreator {
    protected IReportBaseInfoFacade reportBaseInfoFacade = new ReportBaseInfoFacade();
    protected IReportDataSetFacade reportDataSetFacade = new ReportDataSetFacade();
    protected IPrintDataSaver printDataSaver = new PrintDataSaver();

    /**
     * 创建打印对象
     * 
     * @param rs
     * @return
     * @throws Exception
     */
    protected PrintSet createPrintSet(ResultSet rs) throws Exception {
        PrintSet printSet = new PrintSet();
        printSet.setId(rs.getInt("id"));
        printSet.setIPrintGroupId(rs.getInt("iprintGroupId"));
        printSet.setCCode(rs.getString("cCode"));
        printSet.setCTFASSName(rs.getString("cTfassName"));
        printSet.setCCarType(rs.getString("cCarType"));
        printSet.setNTFASSCount(rs.getInt("nTFassCount"));
        printSet.setIMESseq(rs.getInt("iMesSeq"));
        printSet.setCFactory(rs.getString("cFactory"));
        printSet.setCPrintMD(rs.getString("cPrintMd"));
        printSet.setCCarTypeDesc(rs.getString("ccartypedesc"));
        printSet.setCLastVin(rs.getString("clastvin"));
        printSet.setCVinRule(rs.getString("cVinRule"));

        return printSet;
    }
}
