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
 * 商品servlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 通过id查询单个商品
	 */
	public  String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取商品的id
		String pid = request.getParameter("pid");
		
		//2.调用service
		ProductService ps = new ProductServiceImpl();
		Product p=null;
		try {
			p = ps.getById(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3.将结果放入request域中，请求转发
		request.setAttribute("bean", p);
		
		
		
		//浏览记录
		
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
		//1.获取类别 当前页 设置一个pageSize
		String cid = request.getParameter("cid");
		int currPage =Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12 ;
		
		//2.调用service 返回值
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> bean=null;
		try {
			bean = ps.findByPage(currPage,pageSize,cid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//3.将结果放入request域中 请求转发
		request.setAttribute("pb", bean);
		return "/jsp/product_list.jsp";
	}
}
