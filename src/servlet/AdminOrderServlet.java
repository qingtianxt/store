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
 * ��̨����ģ��
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
/**
 * ��ѯ����
 * @param request
 * @param response
 * @return
 * @throws Exception 
 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//����state
		String state = request.getParameter("state");
		//����service
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<Order> list =os.findAllByState(state);
		
		//��list�������У�����ת��
		request.setAttribute("list", list);
		return "/admin/order/list.jsp";
	}
	/**
	 * ��ѯ��������
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//����oid
		String oid = request.getParameter("oid");
		//����service��ѯ�������� ����ֵ list<OrderItem>
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<OrderItem> items = os.getById(oid).getItems();
		//��listת����json д��
		//�ų�����д��ȥ������
		JsonConfig config = JsonUtil.configJson(new String[]{"class","itemid","order"});
		JSONArray json = JSONArray.fromObject(items,config);
		response.getWriter().println(json);
		return null;
	}
	/**
	 * �޸Ķ���״̬
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����oid��state
		String oid = request.getParameter("oid");
		String state = request.getParameter("state");
		
		//2.����service 
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);
		order.setState(2);
		os.updateOrder(order);
		
		//3.ҳ���ض���
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		return null;
	}
}
