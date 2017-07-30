package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import service.CategoryService;
import service.impl.CategoryServiceImpl;
import utils.BeanFactory;
import utils.JsonUtil;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
/**
 * 查询所有的servlet
 * @param request
 * @param response
 * @return
 * @throws ServletException
 * @throws IOException
 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.调用categoryService 查询所有的分类，返回值list
//		CategoryService cs = new CategoryServiceImpl();
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		//2.将返回值放入request域中
//		request.setAttribute("clist", clist);
		String json = JsonUtil.list2json(clist);
		//3.写会去
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(json);
		return null;
	}

}
