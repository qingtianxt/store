package dao;

import java.util.List;

import domain.Order;
import domain.OrderItem;

public interface OrderDao {

	void add(Order order)throws Exception;

	void addItem(OrderItem oi) throws Exception;

	List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception;

	int getTotalCount(String uid) throws Exception;

	Order getById(String oid) throws Exception;

}