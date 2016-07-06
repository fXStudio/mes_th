import java.applet.Applet;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * 配置单打印
 * 
 * @author Ajaxfan
 */
public class JdApplet extends Applet {
    /***/
	private static final long serialVersionUID = 3007717280460840364L;

	// 重复打印，按日期，架号打印print_data中的数据，
    // 入口参数：rq 日期，ch：车号，js：架号，ls：打印次数（打印页数）groupid 组合id
    public void ppr(String rq, String ch, String js, String path, String groupid) {
        String urlbase = path + "/rePrint?";

        try {
            URL url = new URL(getCodeBase(), urlbase + "rq=" + rq + "&ch=" + ch + "&js=" + js + "&groupid=" + groupid);

            printAction((JasperPrint) JRLoader.loadObject(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "打印执行失败，更多信息请查看运行日志");
            e.printStackTrace();
        }
    }

    // 历史打印 按日期、架号，从cardata中读数据，然后写入print_data中
    // 入口参数：rq 日期，ch：车号，js：架号，ls：打印次数（打印页数）groupid 组合id
    public void ppHistory(String rq, String ch, String js, String path, String groupid) {
        String urlbase = path + "/historyPrint?";

        try {
            URL url = new URL(getCodeBase(), urlbase + "rq=" + rq + "&ch=" + ch + "&js=" + js + "&groupid=" + groupid);

            printAction((JasperPrint) JRLoader.loadObject(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "打印执行失败，更多信息请查看运行日志");
            e.printStackTrace();
        }
    }

    // 重复打印，用户打印两张以上的配货单时，从第二次之后执行此城程序
    // 入口参数：rq 日期，ch：车号，js：架号，ls：打印次数（打印页数）groupid 组合id
    public void ppm(String rq, String ch, String ls, String path, String groupid) {
        String urlbase = path + "/mjprint?";
        ch = ch.trim();

        try {
            for (int i = 0, temp = 1; i < Integer.valueOf(ls); i++, temp++) {
                URL url = new URL(getCodeBase(),
                        urlbase + "rq=" + rq + "&ch=" + ch + "&js=" + temp + "&groupid=" + groupid);

                printAction((JasperPrint) JRLoader.loadObject(url));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "打印执行失败，更多信息请查看运行日志");
            e.printStackTrace();

        }
    }

    // 入口参数：rq 日期，ch：车号，js：架号，ls：打印次数（打印页数）groupid 组合id
    public void pp(String rq, String ch, String ls, String path, String groupid, String pages) {
        String urlbase = path + "/jprint?";
        ch = ch.trim();

        List<JasperPrint> jaspers = new ArrayList<JasperPrint>();

        try {
            // 多辆份报表生成
            for (int i = 0, temp = 1; i < Integer.valueOf(ls); i++, temp++) {
                // 报表模板请求路径
                URL url = new URL(getCodeBase(), urlbase + "rq=" + rq + "&ch=" + ch + "&js=" + temp + "&groupid="
                        + groupid + "&isContinu=" + true);
                // 报表对象
                jaspers.addAll((List<JasperPrint>) JRLoader.loadObject(url));
            }

            // 打印多份，这样可以减少和数据库的交互
            for (int j = 0; j < Integer.valueOf(pages); j++) {
                for (JasperPrint jasper : jaspers) {
                    printAction(jasper);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "打印执行失败，更多信息请查看运行日志");
            e.printStackTrace();
        }
    }

    /**
     * 报表打印
     * 
     * @param jasperPrint
     *            报表对象
     */
    private void printAction(JasperPrint jasperPrint) throws Exception {
        JasperPrintManager.printReport(jasperPrint, false);
    }
}
