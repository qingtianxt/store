package service.impl;

import java.io.InputStream;
import java.util.List;

import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import domain.Category;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import service.CategoryService;

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
}
