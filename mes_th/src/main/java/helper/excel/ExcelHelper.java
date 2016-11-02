package helper.excel;

import java.io.BufferedInputStream;
import java.io.InputStream;

import helper.excel.impl.DataPersistenceServcie;
import helper.excel.impl.ExcelParserImpl;
import helper.excel.inters.IDataPersistenceService;
import helper.excel.inters.IExcelParser;

/**
 * Excel 解析工具类
 *
 * @author AjaxFan
 */
public final class ExcelHelper {
	private static IExcelParser excelParser = new ExcelParserImpl();
	private static IDataPersistenceService dataStore = new DataPersistenceServcie();

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
	public static int parse(InputStream in) throws Exception {
		return dataStore.storeData(excelParser.parseExcel(new BufferedInputStream(in)));
	}
}