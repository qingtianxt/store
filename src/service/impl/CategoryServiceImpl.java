package service.impl;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import dao.CategoryDao;
import dao.ProductDao;
import dao.impl.CategoryDaoImpl;
import domain.Category;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import service.CategoryService;
import utils.BeanFactory;
import utils.DataSourceUtils;

public class CategoryServiceImpl implements CategoryService{
/**
 * ��ѯ���еķ���
 */
	@Override
	public List<Category> findAll() throws Exception {
		//1.�������������
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		//2.��ȡָ���Ļ���
		
		Cache cache = cm.getCache("categoryCache");
		
		//3.ͨ�������ȡ���� ��cache����һ��map����
		Element element = cache.get("clist");
		
		List<Category> list = null;
		
		//4.�ж�����
		if(element==null){
			//�����ݿ��л�ȡ
			CategoryDao cd = new CategoryDaoImpl();
			list =cd.findAll();
			
			//��list���뻺��
			cache.put(new Element("clist", list));
			System.out.println("������û�����ݣ��Ѵ����ݿ��л�ȡ");
			
		}
		else{
			//ֱ�ӷ���
			list = (List<Category>) element.getObjectValue();
			
			System.out.println("������������");
		}
		
		return list;
	}
	public static void main(String[] args) {
		InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
		System.out.println(is);
	}
	/**
	 * ��ӷ���
	 */
	@Override
	public void add(Category c) throws Exception {
		// TODO Auto-generated method stub
		//��ʱ��ȡdao
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao"); 
		cd.add(c);
		//���»���
		//1.�������������
				CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
				
				//2.��ȡָ���Ļ���
				
				Cache cache = cm.getCache("categoryCache");
				
				//��ջ���
				cache.remove("clist");
	}
	/**
	 * ͨ��cid��ȡһ���������
	 */
	@Override
	public Category getById(String cid) throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.getById(cid);
	}
	
	/**
	 * ���·���
	 */
	@Override
	public void update(Category c) throws Exception {
		// TODO Auto-generated method stub
		//����dao
		CategoryDao cd =(CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.update(c);
		//��ջ���
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache = cm.getCache("categoryCache");
		cache.remove("clist");
	}
	
	@Override
	public void delete(String cid) throws Exception{
			
		try {
			//1.��������
			DataSourceUtils.startTransaction();
			
			//2.������Ʒ
			ProductDao pd =(ProductDao) BeanFactory.getBean("ProductDao");
			pd.updateCid(cid);
			//3.ɾ������
			CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
			cd.delete(cid);
			//4.�������
			DataSourceUtils.commitAndClose();
			
			//��ջ���
			CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
			Cache cache = cm.getCache("categoryCache");
			cache.remove("clist");
			
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
	}
}
