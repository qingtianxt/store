package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.ProductDaoImpl;
import domain.Category;
import domain.Product;
import service.CategoryService;
import service.ProductService;
import service.impl.CategoryServiceImpl;
import service.impl.ProductServiceImpl;

/**
 * 
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
/**
 * 和首页相关的servlet
 */ 
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//去数据库中查询最新商品和热门商品 将他们放入request域中 请求转发
		ProductService ps  = new ProductServiceImpl();
		
		//最新商品
		List<Product> newList=null;
		//热门商品
		List<Product> hotList=null;
		try {
			newList = ps.findNew();
			hotList = ps.findHot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//将两个list放入域中
		request.setAttribute("nlist", newList);
		request.setAttribute("hlist", hotList);
		return "/jsp/index.jsp";
	}
}
