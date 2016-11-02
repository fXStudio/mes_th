package helper.excel.base;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.LittleEndian;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import helper.excel.inters.IExcelParser;

/**
 * ExcelParser的抽象基类
 *
 * @author Administrator
 */
public abstract class BaseExcelParser implements IExcelParser {
    /** Excel 2003头签名信息 */
    private static final long _signature = 0xE11AB1A1E011CFD0L;

    /** Userful Offset */
    private static final int _signature_offset = 0;

    /**
     * 获得单元格的值
     *
     * @param cell
     *            单元格对象
     * @return
     */
    protected final String getCellValue(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf((long)cell.getNumericCellValue());
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BLANK:
            case Cell.CELL_TYPE_BOOLEAN:
            case Cell.CELL_TYPE_ERROR:
            case Cell.CELL_TYPE_FORMULA:
            }
        }
        return null;
    }

    /**
     * 创建Excel文件对象
     *
     * @param in
     * @return
     */
    protected final Workbook createWorkbook(InputStream in) throws IOException {
        // 判断文件的头信息，校验文件是否为Excel 2003格式
        if (isExcel2003HeaderBlock(in)) {
            return new HSSFWorkbook(in, false);
        }
        return new XSSFWorkbook(in);
    }

    /**
     * 检查文件头的数字签名信息
     *
     * @param in 文件流对象
     * @return
     * @throws IOException
     */
    private boolean isExcel2003HeaderBlock(InputStream in) throws IOException {
        // 校验文件签名
        return LittleEndian.getLong(readFirst512(in), _signature_offset) == _signature;
    }

    /**
     * 判断文件头信息，检查文件是否为2003格式
     *
     * @param stream
     * @return
     * @throws IOException
     */
    private byte[] readFirst512(InputStream stream) throws IOException {
        // 用来最前的512字节的头信息
        byte[] data = new byte[512];
        // 设置流mark位置，以便后面的流回滚
        stream.mark(0);
        // 读取头信息
        IOUtils.readFully(stream, data);
        // 回滚IO流
        stream.reset();

        return data;
    }
}