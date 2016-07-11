package th.report.helper;

import java.io.InputStream;

public final class JasperTemplateLoader {
	private static final String BASE_PATH = "/th/report/resources/";

	public static synchronized InputStream load(String fileName) {
		return JasperTemplateLoader.class.getResourceAsStream(BASE_PATH + fileName.trim());
	}
}
