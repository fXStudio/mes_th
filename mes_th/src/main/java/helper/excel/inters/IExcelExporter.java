package helper.excel.inters;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Excel 文件导出接口
 */
public interface IExcelExporter {
	/**
	 * 导出文件
	 *
	 * @param out
	 *            输出文件流
	 */
	public void export(OutputStream out) throws IOException;
}
