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
 * ����ҳ��ص�servlet
 */ 
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȥ���ݿ��в�ѯ������Ʒ��������Ʒ �����Ƿ���request���� ����ת��
		ProductService ps  = new ProductServiceImpl();
		
		//������Ʒ
		List<Product> newList=null;
		//������Ʒ
		List<Product> hotList=null;
		try {
			newList = ps.findNew();
			hotList = ps.findHot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//������list��������
		request.setAttribute("nlist", newList);
		request.setAttribute("hlist", hotList);
		return "/jsp/index.jsp";
	}
}
