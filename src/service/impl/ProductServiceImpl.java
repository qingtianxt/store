package service.impl;

import java.util.List;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import domain.PageBean;
import domain.Product;
import service.ProductService;
import utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

/**
 * 查询最新
 */
	@Override
	public List<Product> findNew() throws Exception {
		ProductDao dao = new ProductDaoImpl();
		
		return dao.findNew();
	}
/**
 * 查询热门
 */
	@Override
	public List<Product> findHot() throws Exception {
		ProductDao dao = new ProductDaoImpl();
		
		return dao.findHot();
	}
	@Override
	public Product getById(String pid) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		
		return dao.getByPid(pid);
	}
	/**
	 * 按类别分类查询商品
	 */
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		
		//当前页数据
		List<Product> list =dao.findByPage(currPage,pageSize,cid);
		//总条数
		int totalCount = dao.getTotalCount(cid);
		
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	@Override
	public List<Product> findAll() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		
		return pd.findAll();
	}
	/**
	 * 添加商品
	 */
	@Override
	public void add(Product p) throws Exception {
		// TODO Auto-generated method stub
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		pdao.add(p);
	}
}
