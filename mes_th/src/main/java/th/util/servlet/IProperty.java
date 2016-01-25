package th.util.servlet;

/**
 * 属性信息
 * 
 * @author GaoHF
 * @date 2010-3-17
 */
public interface IProperty {
    /** 服务器路径 */
    public static final String SERVER_DIR_PATH = "server_path";

    /** 本地路径 */
    public static final String LOCAL_DIR_PATH = "local_path";

    /** FIS路径 */
    public static final String FIS_DIR_PATH = "fis_path";

    /** 无异常文件路径 */
    public static final String LOCAL_SUCCESS_DIR_PATH = "success_path";

    /** 异常文件路径 */
    public static final String LOCAL_FAILURE_DIR_PATH = "failure_path";

    /** 备份文件路径 */
    public static final String LOCAL_BACKUP_DIR_PATH = "backup_path";
}
