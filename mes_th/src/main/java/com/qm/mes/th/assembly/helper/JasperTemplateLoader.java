package com.qm.mes.th.assembly.helper;

import java.io.InputStream;

public final class JasperTemplateLoader {
    public static final String BASE_PATH = "/com/qm/mes/th/assembly/resources/";

    public static synchronized InputStream load(String fileName) {
        return JasperTemplateLoader.class.getResourceAsStream(BASE_PATH + fileName.trim());
    }
}
