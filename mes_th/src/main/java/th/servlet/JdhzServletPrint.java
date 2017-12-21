package th.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.mes.th.assembly.entities.RequestParam;
import com.qm.mes.th.assembly.newprint.AssemblyNewPrintFacade;

/**
 * @author Administrator
 */
public class JdhzServletPrint extends HttpServlet {
	/** ���к� */
	private static final long serialVersionUID = 1L;

	/**
	 * ���ݴ���
	 */
	public  synchronized void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// �ӿͻ��˽��յ����������
		RequestParam requestParam = new RequestParam();
		requestParam.setRequestDate(request.getParameter("rq"));// ��������
		requestParam.setChassisNumber(request.getParameter("ch"));// ���̺�
		requestParam.setGroupId(request.getParameter("groupid"));// ��ӡ���
		requestParam.setJs(request.getParameter("js"));// ��ӡ���κ�

		// ����д�����ͻ���
		write2Client(response, new AssemblyNewPrintFacade().assemblyPrint(requestParam));
	}

	/**
	 * ����д�����ͻ���
	 * 
	 * @param response
	 * @param obj
	 */
	private void write2Client(HttpServletResponse response, Object obj) {
		// ������Ӧͷ
		response.setContentType("application/octet-stream");

		OutputStream streamOutPut = null;
		ObjectOutputStream objectOutput = null;

		try {
			streamOutPut = response.getOutputStream();
			objectOutput = new ObjectOutputStream(streamOutPut);

			objectOutput.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (objectOutput != null) {
				try {
					objectOutput.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					objectOutput = null;
				}
			}
			if (streamOutPut != null) {
				try {
					streamOutPut.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					streamOutPut = null;
				}
			}
		}
	}
}
