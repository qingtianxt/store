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
 * 后台商品管理
 */
public class AdminProductServlet extends BaseServlet {
	
	
	public String findAll(HttpServletRequest request,HttpServletResponse response)
	{
		//1.调用service 查询所有 返回一个list
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list =null;
		try {
			list = ps.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2.将list放入request域中 请求转发
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}
	public String addUI(HttpServletRequest request,HttpServletResponse response)
	{
		//查询所有的分类 返回值list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将list放入request
		request.setAttribute("clist", clist);
		return "/admin/product/add.jsp";
	}
}
