package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Cart;
import domain.CartItem;
import domain.Product;
import service.ProductService;
import utils.BeanFactory;

/**
 * ���ﳵģ��
 */
public class CartServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	public Cart getCart(HttpServletRequest request){
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//�жϹ��ﳵ�Ƿ�Ϊ��
		if(cart==null){
			//����һ��cart
			cart = new Cart();
			//��ӵ�session
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
		
	}
	
	/**
	 * ��ӵ����ﳵ
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//1.��ȡpid������
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		new BeanFactory();
		//2.ͨ��pid������productService ͨ��pid��ȡһ����Ʒ
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		Product product = ps.getById(pid);
		
		//3.��װ��CartItem
		CartItem cartItem = new CartItem(product,count);
		
		//4.��ӵ����ﳵ
		Cart cart = getCart(request);
		cart.add2Cart(cartItem);
		
		
		
		//5.�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	/**
	 * �ӹ��ﳵ���Ƴ����ﳵ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//��ȡ��Ʒ��pid
		String pid = request.getParameter("pid");
		
		//���ù��ﳵ��remove����
		getCart(request).removeFromCart(pid);
		
		
		//�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;	
	}
	/**
	 * ��չ��ﳵ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//��ȡ ���ﳵ  ���
		getCart(request).clearCart();
		
		
		//�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;	
	}
}
