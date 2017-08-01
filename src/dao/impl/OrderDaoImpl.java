package dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import net.sf.ehcache.search.expression.Or;
import utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {
/**
 * ����һ������
 */
	@Override
	public void add(Order order) throws Exception {
		QueryRunner qr = new QueryRunner();
		
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql,order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}
/**
 * ����һ��������
 */
	@Override
	public void addItem(OrderItem oi) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql ="insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql,oi.getItemid(),oi.getCount(),oi.getSubtotal(),oi.getProduct().getPid(),oi.getOrder().getOid());
	}
	/**
	 * ��ѯ�ҵĶ�����ҳ
	 */
	@Override
	public List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql ="select * from orders where uid=? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class),uid,(currPage-1)*pageSize,pageSize);
		//������������ ��װÿ�������Ķ������б�
		sql = "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?";
		for(Order order :list){
			//��ǰ������������������
			List<Map<String, Object>> mlist = qr.query(sql, new MapListHandler(),order.getOid());
			
			for (Map<String, Object> map : mlist) {
				//��װproduct����
				Product p = new Product();
				BeanUtils.populate(p, map);
				//��װorderItem
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);
				
				oi.setProduct(p);
				
				//��orderItem�������ӵ���Ӧ��order�����list������
				order.getItems().add(oi);
			}
			
		}
		
		return list;
	}
	/**
	 * ��ȡ�ҵĶ���������
	 */
	@Override
	public int getTotalCount(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql  ="select count(*) from orders where uid=?";
		return ((Long)qr.query(sql, new ScalarHandler(),uid)).intValue();
	}
	/**
	 * ͨ��oid��ѯ��������
	 */
	@Override
	public Order getById(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql  ="select * from orders where oid =?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class),oid);
		
		//��װorderItems
		sql = "select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(),oid);
		for (Map<String, Object> map : query) {
			//��װproduct
			Product product = new Product();
			BeanUtils.populate(product, map);
			//��װorderItem
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, map);
			oi.setProduct(product);
			//��orderItem���뵽order��items��
			order.getItems().add(oi);
		}
		
		return order;
	}

}