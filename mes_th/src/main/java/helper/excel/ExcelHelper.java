package helper.excel;

import java.io.BufferedInputStream;
import java.io.InputStream;

import helper.excel.inters.IExcelParser;
import helper.excel.process.ExcelParserImpl;

/**
 * Excel 解析工具类
 *
 * @author AjaxFan
 */
public final class ExcelHelper {
	private static IExcelParser excelParser = new ExcelParserImpl();

	/**
	 * 工具类是不可被实例化的
	 */
	private ExcelHelper() {
	}

	/**
	 * 数据解析并持久化
	 *
	 * @param in
	 * @return
	 */
	public static String parse(InputStream in) throws Exception {
		return excelParser.parseExcel(new BufferedInputStream(in));
	}
}