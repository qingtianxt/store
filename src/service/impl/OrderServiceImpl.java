package service.impl;

import java.sql.SQLException;
import java.util.List;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import domain.User;
import service.OrderService;
import utils.BeanFactory;
import utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void add(Order order) throws Exception{
		//1.��������
		try {
			DataSourceUtils.startTransaction();
			
			OrderDao od= (OrderDao) BeanFactory.getBean("OrderDao");
			//2.��order�������һ������
			od.add(order);
			//int i=1/0;
			//3.��orderitem�����һ������
			for(OrderItem oi:order.getItems()){
				od.addItem(oi);
			}
			//4.������
			DataSourceUtils.commitAndClose();
			
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
/**
 * ��ҳ��ѯ����
 */
	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		
		//��ѯ��ǰҳ����
		List<Order> list = od.findAllByPage(currPage,pageSize,user.getUid());
		//��ѯ������
		int totalCount = od.getTotalCount(user.getUid());
		return new PageBean<>(list,currPage,pageSize,totalCount);
	}
	/**
	 * �鿴��������
	 */
	@Override
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		Order order =od.getById(oid);
		return order;
	}
	
	@Override
	public void updateOrder(Order order) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}
	
}
