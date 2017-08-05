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
 * ��ѯ����
 */
	@Override
	public List<Product> findNew() throws Exception {
		ProductDao dao = new ProductDaoImpl();
		
		return dao.findNew();
	}
/**
 * ��ѯ����
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
	 * ���������ѯ��Ʒ
	 */
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		
		//��ǰҳ����
		List<Product> list =dao.findByPage(currPage,pageSize,cid);
		//������
		int totalCount = dao.getTotalCount(cid);
		
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	@Override
	public List<Product> findAll() throws Exception {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		
		return pd.findAll();
	}
	/**
	 * �����Ʒ
	 */
	@Override
	public void add(Product p) throws Exception {
		// TODO Auto-generated method stub
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		pdao.add(p);
	}
}
