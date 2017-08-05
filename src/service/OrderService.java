package service;

import java.util.List;

import domain.Order;
import domain.PageBean;
import domain.User;

public interface OrderService {

	void add(Order order) throws Exception;

	PageBean<Order> findAllByPage(int currPage, int pageSize, User user)throws Exception;

	Order getById(String oid)throws Exception;
	
	
	void updateOrder(Order order)throws Exception;

	List<Order> findAllByState(String state) throws Exception;
	
}
