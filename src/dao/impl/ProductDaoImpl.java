package dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import dao.ProductDao;
import domain.Product;
import utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {
/**
 * ��ѯ����
 */
	@Override
	public List<Product> findNew() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate limit 9 ";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}
/**
 * ��ѯ����
 */
	@Override
	public List<Product> findHot() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot=1 order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}
	/**
	 * ͨ����Ʒid ��ȡ��Ʒ����
	 */
@Override
public Product getByPid(String pid) throws Exception {
	QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	String sql = "select * from product where pid=? limit 1";
	return qr.query(sql, new BeanHandler<>(Product.class),pid);

}
/**
 * ��ѯ��ǰҲ��Ҫչʾ������
 * @param currPage
 * @param pageSize
 * @param cid
 * @return
 * @throws Exception
 */
	@Override
	public List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where cid=? limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class),cid,(currPage-1)*pageSize,pageSize);
	}
	/**
	 * ��ѯ��ǰ����������
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getTotalCount(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql ="select count(*) from product where cid=?";
		return ((Long)qr.query(sql,new ScalarHandler(),cid)).intValue();
		//return ((long)qr.query(sql, new ScalarHandler(),cid)).intValue();
		//Сд��long�Ǵ����
	}
	/**
	 * ������Ʒ��cidδɾ�������ʱ��׼��
	 */
	@Override
	public void updateCid(String cid) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql ="update product set cid=null where cid=? ";
		qr.update(DataSourceUtils.getConnection(),sql,cid);
	}
	
	@Override
	public List<Product> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}
	/**
	 * �����Ʒ
	 * @throws SQLException 
	 */
	@Override
	public void add(Product p) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql= "insert into product values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, p.getPid(),p.getPname(),p.getMarket_price(),
				p.getShop_price(),p.getPimage(),p.getPdate(),
				p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCategory().getCid());
	}
	
}
