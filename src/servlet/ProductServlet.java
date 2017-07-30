package servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.PageBean;
import domain.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;
import utils.CookUtils;

/**
 * ��Ʒservlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * ͨ��id��ѯ������Ʒ
	 */
	public  String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ��Ʒ��id
		String pid = request.getParameter("pid");
		
		//2.����service
		ProductService ps = new ProductServiceImpl();
		Product p=null;
		try {
			p = ps.getById(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3.���������request���У�����ת��
		request.setAttribute("bean", p);
		
		
		
		//�����¼
		
		Cookie c = CookUtils.getCookieByName("ids",request.getCookies());
		String ids="";
		if(c==null){
			ids=pid;
		}
		else{
			ids=c.getValue();
			String c1[]= ids.split("-");
			List<String> aslist = Arrays.asList(ids);
			LinkedList<String> list = new LinkedList<>(aslist);
			if(list.contains(pid)){
				list.remove(pid);
				list.addFirst(pid);
			}else{
				if(list.size()>2){
					list.removeLast();
				}
				list.addFirst(pid);
			}
			for (String s : list) {
				ids+=s+"-";
			}
			ids=ids.substring(0,ids.length()-1);
		}
		c=new Cookie("ids", ids);
		c.setMaxAge(3600);
		c.setPath(request.getContextPath()+"/");
		response.addCookie(c);
		
		
		return "/jsp/product_info.jsp";
	}
	
	public  String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ��� ��ǰҳ ����һ��pageSize
		String cid = request.getParameter("cid");
		int currPage =Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12 ;
		
		//2.����service ����ֵ
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> bean=null;
		try {
			bean = ps.findByPage(currPage,pageSize,cid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//3.���������request���� ����ת��
		request.setAttribute("pb", bean);
		return "/jsp/product_list.jsp";
	}
}
