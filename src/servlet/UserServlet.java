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
 * 和用户相关的servlet
 */
public class UserServlet extends BaseServlet {
	
	public String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("userservlet中的add方法执行了");
		return null;
	}
	/**
	 * 跳转到 注册界面
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
	 * 用户注册
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//1 封装数据
		User user = new User();
		
		//注册自定义的转化器
		ConvertUtils.register(new MyConventer(), Date.class);
		BeanUtils.populate(user, req.getParameterMap());
		
		//1.1 设置用户id
		user.setUid(UUIDUtils.getId());
		
		//1.2 设置激活码
		user.setCode(UUIDUtils.getCode());
		
		//1.3密码加密
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2 调用service完成注册
		UserService s = new UserServiceImpl();
		s.regist(user);
		
		//3 页面请求转发
		req.setAttribute("msg", "用户注册已成功，请去邮箱激活");
		return "/jsp/msg.jsp";
	}
	/**
	 * 用户激活
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取激活码
			String code = req.getParameter("code");
		System.out.println(code);
		//调用service完成激活
			UserService s = new UserServiceImpl();
			User user =s.active(code);
			if(user==null){
				//通过激活码没有找到用户
				req.setAttribute("msg", "请重新激活");
				
			}else{
				//添加信息
				req.setAttribute("msg","激活成功" );
			}
			
		//请求转发到msg.jsp
		
		return "/jsp/msg.jsp";
	}
	/**
	 * 跳转到登陆页面
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
		//1.获取用户名和密码
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		password = MD5Utils.md5(password);
		
		//2.调用service完成登录操作
		UserService s = new UserServiceImpl();
		User user = s.login(username,password);
		
		//3.判断用户
		if(user==null){
			//用户名和密码不匹配
			req.setAttribute("msg", "用户名和密码不匹配");
			return "/jsp/login.jsp";
		}else{
			//继续判断用户的状态是否激活
			if(Constant.USER_IS_ACTIVE!=user.getState()){
				req.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}
		}
		//4.将user放入session域中
		req.getSession().setAttribute("user", user);
		resp.sendRedirect(req.getContextPath()+"/");//store
		
		return null;
	}
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//干掉session
			req.getSession().invalidate();
			
		//重定向
			resp.sendRedirect(req.getContextPath());
		
		//处理自动登录
		
		return null;
	}
}
