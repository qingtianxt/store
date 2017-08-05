package servlet;

import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import service.CategoryService;
import utils.BeanFactory;
import utils.UUIDUtils;

/**
 * ��̨�������ģ��
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
/**
 * չʾ���еķ���
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public String findAll(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		//1.����categoryservice ��ѯ���еķ�����Ϣ ����ֵ list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
				
		//2.��list����request���У�����ת��
		request.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}
	/**
	 * ��ת�����ҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		return "/admin/category/add.jsp";
	}
	
	public String add(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		//����cname
		String cname = request.getParameter("cname");
		
		//��װcategory 
		Category c = new Category();
		c.setCid(UUIDUtils.getId());
		c.setCname(cname);
		//����service�����Ӳ���
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		cs.add(c);
		
		//�ض��� ��ѯ���з���
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}
	/**
	 * ͨ��id��ȡ������Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getById(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		//����cid
		String cid = request.getParameter("cid");
		
		//����service��ɲ�ѯ���� ����ֵcategory
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		Category c = cs.getById(cid);
		
		//��category����request���С�����ת��/admin/category/edit.jsp
		request.setAttribute("bean", c);
		
		return "/admin/category/edit.jsp";
	}
	/**
	 * ���·�����Ϣ����
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String update(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		//1.��ȡcid cname
		//2.��װ����
		Category c = new Category();
		c.setCid(request.getParameter("cid"));
		c.setCname(request.getParameter("cname"));
		
		//����service ��ɸ��²���
		CategoryService cs =(CategoryService) BeanFactory.getBean("CategoryService");
		cs.update(c);
		
		//�ض��� ��ѯ����
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	public String delete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		//1.��ȡcid
		String cid = request.getParameter("cid");
		//2 ����service����
		CategoryService  cs = (CategoryService) BeanFactory.getBean("CategoryService");
		cs.delete(cid);
		//3�ض���
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	
}
