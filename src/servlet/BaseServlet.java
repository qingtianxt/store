package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ͨ�õ�servlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		try{
			//1.��ȡ����
			Class clazz = this.getClass();
//			System.out.println(this);//�����this˭���þ���˭
			
			//2.��ȡ����ķ���
			String m = arg0.getParameter("method");
//			System.out.println(m);
			if(m==null){
				m="index";
			}
			//3.��ȡ��������
			Method method = clazz.getMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			
			//4.�÷���ִ��
			String s =(String) method.invoke(this, arg0,arg1);
			
			//5.�ж�s�Ƿ�Ϊ��
			if(s!=null){
				arg0.getRequestDispatcher(s).forward(arg0, arg1);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	public String index(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		return null;
	}
}
