package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȥ���ݿ��в�ѯ������Ʒ��������Ʒ �����Ƿ���request���� ����ת��
		
		return "/jsp/index.jsp";
	}
}