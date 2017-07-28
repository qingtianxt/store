package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import constant.Constant;
import domain.User;
import myconventer.MyConventer;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.MD5Utils;
import utils.UUIDUtils;

/**
 * ���û���ص�servlet
 */
public class UserServlet extends BaseServlet {
	
	public String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("userservlet�е�add����ִ����");
		return null;
	}
	/**
	 * ��ת�� ע�����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		return "/jsp/register.jsp";
	}
	/**
	 * �û�ע��
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1 ��װ����
		User user = new User();
		
		//ע���Զ����ת����
		ConvertUtils.register(new MyConventer(), Date.class);
		BeanUtils.populate(user, req.getParameterMap());
		
		//1.1 �����û�id
		user.setUid(UUIDUtils.getId());
		
		//1.2 ���ü�����
		user.setCode(UUIDUtils.getCode());
		
		//1.3�������
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2 ����service���ע��
		UserService s = new UserServiceImpl();
		s.regist(user);
		
		//3 ҳ������ת��
		req.setAttribute("msg", "�û�ע���ѳɹ�����ȥ���伤��");
		return "/jsp/msg.jsp";
	}
	/**
	 * �û�����
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//��ȡ������
			String code = req.getParameter("code");
		System.out.println(code);
		//����service��ɼ���
			UserService s = new UserServiceImpl();
			User user =s.active(code);
			if(user==null){
				//ͨ��������û���ҵ��û�
				req.setAttribute("msg", "�����¼���");
				
			}else{
				//�����Ϣ
				req.setAttribute("msg","����ɹ�" );
			}
			
		//����ת����msg.jsp
		
		return "/jsp/msg.jsp";
	}
	/**
	 * ��ת����½ҳ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		return "/jsp/login.jsp";
	}
	
	public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1.��ȡ�û���������
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		password = MD5Utils.md5(password);
		
		//2.����service��ɵ�¼����
		UserService s = new UserServiceImpl();
		User user = s.login(username,password);
		
		//3.�ж��û�
		if(user==null){
			//�û��������벻ƥ��
			req.setAttribute("msg", "�û��������벻ƥ��");
			return "/jsp/login.jsp";
		}else{
			//�����ж��û���״̬�Ƿ񼤻�
			if(Constant.USER_IS_ACTIVE!=user.getState()){
				req.setAttribute("msg", "�û�δ����");
				return "/jsp/login.jsp";
			}
		}
		//4.��user����session����
		req.getSession().setAttribute("user", user);
		resp.sendRedirect(req.getContextPath()+"/");//store
		
		return null;
	}
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//�ɵ�session
			req.getSession().invalidate();
			
		//�ض���
			resp.sendRedirect(req.getContextPath());
		
		//�����Զ���¼
		
		return null;
	}
}
