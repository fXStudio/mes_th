package helper.excel.inters;

import java.io.InputStream;

/**
 * Excel文件解析接口文件
 */
public interface IExcelParser {
	public String parseExcel(InputStream in) throws Exception;
}
