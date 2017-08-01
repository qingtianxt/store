package servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Cart;
import domain.CartItem;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import domain.User;
import service.OrderService;
import service.impl.OrderServiceImpl;
import utils.BeanFactory;
import utils.UUIDUtils;

/**
 *订单模块
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

  /**
   * 生成订单
   * @param request
   * @param response
   * @return
   * @throws ServletException
   * @throws IOException
   */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断用户是否登录
		User user =(User) request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("msg", "请先登录");
			return "/jsp/msg.jsp";
		}
		
		//1.封装数据
		Order order =new Order();
		//1.1 订单id
		order.setOid(UUIDUtils.getId());
		
		//1.2 订单时间
		order.setOrdertime(new Date());
		//1.3总金额
		Cart cart  = (Cart) request.getSession().getAttribute("cart");
		order.setTotal(cart.getTotal());
		//1.4订单的所有的订单项
		/*
		 * 现货区cart中items
		 * 遍历items 组装成orderItem
		 * 将orderitem添加到list（item）中 
		 */
		for (CartItem cartItem : cart.getItems()) {
			OrderItem oi= new OrderItem();
			
			//设置id
			oi.setItemid(UUIDUtils.getId());
			
			//设置购买数量
			oi.setCount(cartItem.getCount());
			//设置小计
			oi.setSubtotal(cartItem.getSubtotal());
			//设置product
			oi.setProduct(cartItem.getProduct());
		
			//设置order
			oi.setOrder(order);
			
			//添加到list中
			order.getItems().add(oi);
		}
		
		
		//1.5设置用户
		order.setUser(user);
		//2.调用service方法 添加订单
		OrderService os= (OrderService) BeanFactory.getBean("OrderService");
		try {
			os.add(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "500.jsp";
		}
		//3.将order放入request中，请求转发
		request.setAttribute("bean", order);
		//4.清空购物车
		request.getSession().removeAttribute("cart");
		
		return "/jsp/order_info.jsp";
	}
	/**
	 * 分页查询我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取当前页
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 3;
		//2.获取用户
		User user =(User) request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("msg","您还没有登录，请登录" );
		}
		//3.调用service 分页查询：currPage pageSize user 返回值：PageBean
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		PageBean<Order> bean = null;
		try {
			bean = os.findAllByPage(currPage,pageSize,user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//4.将PageBean放入request域中
		request.setAttribute("pb", bean);
		
		return "/jsp/order_list.jsp";
	}
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取uid
		String oid = request.getParameter("oid");
		System.out.println(oid);
		//调用service 通过oid 返回值 order
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = null;
		try {
			order = os.getById(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将order放入request域中
		request.setAttribute("bean", order);
		
		return "/jsp/order_info.jsp";
	}
}
