package servlet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import domain.Product;
import service.CategoryService;
import service.ProductService;
import utils.BeanFactory;

/**
 * ��̨��Ʒ����
 */
public class AdminProductServlet extends BaseServlet {
	
	
	public String findAll(HttpServletRequest request,HttpServletResponse response)
	{
		//1.����service ��ѯ���� ����һ��list
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list =null;
		try {
			list = ps.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2.��list����request���� ����ת��
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}
	public String addUI(HttpServletRequest request,HttpServletResponse response)
	{
		//��ѯ���еķ��� ����ֵlist
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//��list����request
		request.setAttribute("clist", clist);
		return "/admin/product/add.jsp";
	}
}
