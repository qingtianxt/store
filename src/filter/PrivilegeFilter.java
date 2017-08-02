package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;

public class PrivilegeFilter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		//ǿת
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		//ҵ���߼�
		//��session�л�ȡuser �ж�user�Ƿ�Ϊ�գ�Ϊ�գ�����ת��
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "û��Ȩ�ޣ����ȵ�¼");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
		//����
		arg2.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}
	
}
