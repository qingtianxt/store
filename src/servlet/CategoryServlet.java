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
 * ��ѯ���е�servlet
 * @param request
 * @param response
 * @return
 * @throws ServletException
 * @throws IOException
 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.����categoryService ��ѯ���еķ��࣬����ֵlist
//		CategoryService cs = new CategoryServiceImpl();
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		//2.������ֵ����request����
//		request.setAttribute("clist", clist);
		String json = JsonUtil.list2json(clist);
		//3.д��ȥ
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(json);
		return null;
	}

}
