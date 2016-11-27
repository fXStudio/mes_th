package helper.excel;

import java.io.BufferedInputStream;
import java.io.InputStream;

import helper.excel.inters.IExcelParser;
import helper.excel.process.IEFITExcelParserImpl;
import helper.excel.process.SpecialKinExcelParserImpl;

/**
 * Excel 解析工具类
 *
 * @author AjaxFan
 */
public final class ExcelHelper {
	private static IExcelParser iefitExcelParser = new IEFITExcelParserImpl();
	private static IExcelParser specKincodeExcelParser = new SpecialKinExcelParserImpl();

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
		return iefitExcelParser.parseExcel(new BufferedInputStream(in));
	}
	
	/**
	 * 数据解析并持久化
	 *
	 * @param in
	 * @return
	 */
	public static String parse(InputStream in, String type) throws Exception {
		return specKincodeExcelParser.parseExcel(new BufferedInputStream(in));
	}
}