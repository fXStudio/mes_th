package helper.excel.impl;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import helper.excel.inters.IExcelExporter;

/**
 * 文件的导出操作
 */
public class ExcelExporterImpl implements IExcelExporter {
    /** 模板文件路径 */
    private static final String FILE_PAHT = "/helper/excel/resources/template.xls";

    /**
     * 导出模板Excel文件
     * @param out
     */
    @Override
    public synchronized void export(OutputStream out) throws IOException {
        new POIFSFileSystem(this.getClass().getResourceAsStream(FILE_PAHT)).writeFilesystem(out);
    }
}
