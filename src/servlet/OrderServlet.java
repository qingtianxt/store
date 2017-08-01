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
 *����ģ��
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

  /**
   * ���ɶ���
   * @param request
   * @param response
   * @return
   * @throws ServletException
   * @throws IOException
   */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�ж��û��Ƿ��¼
		User user =(User) request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("msg", "���ȵ�¼");
			return "/jsp/msg.jsp";
		}
		
		//1.��װ����
		Order order =new Order();
		//1.1 ����id
		order.setOid(UUIDUtils.getId());
		
		//1.2 ����ʱ��
		order.setOrdertime(new Date());
		//1.3�ܽ��
		Cart cart  = (Cart) request.getSession().getAttribute("cart");
		order.setTotal(cart.getTotal());
		//1.4���������еĶ�����
		/*
		 * �ֻ���cart��items
		 * ����items ��װ��orderItem
		 * ��orderitem��ӵ�list��item���� 
		 */
		for (CartItem cartItem : cart.getItems()) {
			OrderItem oi= new OrderItem();
			
			//����id
			oi.setItemid(UUIDUtils.getId());
			
			//���ù�������
			oi.setCount(cartItem.getCount());
			//����С��
			oi.setSubtotal(cartItem.getSubtotal());
			//����product
			oi.setProduct(cartItem.getProduct());
		
			//����order
			oi.setOrder(order);
			
			//��ӵ�list��
			order.getItems().add(oi);
		}
		
		
		//1.5�����û�
		order.setUser(user);
		//2.����service���� ��Ӷ���
		OrderService os= (OrderService) BeanFactory.getBean("OrderService");
		try {
			os.add(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "500.jsp";
		}
		//3.��order����request�У�����ת��
		request.setAttribute("bean", order);
		//4.��չ��ﳵ
		request.getSession().removeAttribute("cart");
		
		return "/jsp/order_info.jsp";
	}
	/**
	 * ��ҳ��ѯ�ҵĶ���
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ��ǰҳ
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 3;
		//2.��ȡ�û�
		User user =(User) request.getSession().getAttribute("user");
		if(user==null){
			request.setAttribute("msg","����û�е�¼�����¼" );
		}
		//3.����service ��ҳ��ѯ��currPage pageSize user ����ֵ��PageBean
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		PageBean<Order> bean = null;
		try {
			bean = os.findAllByPage(currPage,pageSize,user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//4.��PageBean����request����
		request.setAttribute("pb", bean);
		
		return "/jsp/order_list.jsp";
	}
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡuid
		String oid = request.getParameter("oid");
		System.out.println(oid);
		//����service ͨ��oid ����ֵ order
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = null;
		try {
			order = os.getById(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��order����request����
		request.setAttribute("bean", order);
		
		return "/jsp/order_info.jsp";
	}
}
