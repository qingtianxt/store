package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Order;
import domain.OrderItem;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import service.OrderService;
import utils.BeanFactory;
import utils.JsonUtil;

/**
 * 后台订单模块
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
/**
 * 查询订单
 * @param request
 * @param response
 * @return
 * @throws Exception 
 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接受state
		String state = request.getParameter("state");
		//调用service
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<Order> list =os.findAllByState(state);
		
		//将list放入域中，请求转发
		request.setAttribute("list", list);
		return "/admin/order/list.jsp";
	}
	/**
	 * 查询订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//接受oid
		String oid = request.getParameter("oid");
		//调用service查询订单详情 返回值 list<OrderItem>
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<OrderItem> items = os.getById(oid).getItems();
		//将list转换成json 写回
		//排除不用写回去的数据
		JsonConfig config = JsonUtil.configJson(new String[]{"class","itemid","order"});
		JSONArray json = JSONArray.fromObject(items,config);
		response.getWriter().println(json);
		return null;
	}
	/**
	 * 修改订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受oid和state
		String oid = request.getParameter("oid");
		String state = request.getParameter("state");
		
		//2.调用service 
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);
		order.setState(2);
		os.updateOrder(order);
		
		//3.页面重定向
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		return null;
	}
}
