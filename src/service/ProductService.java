package service;

import java.util.List;

import domain.PageBean;
import domain.Product;

public interface ProductService {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getById(String pid)throws Exception;

	PageBean<Product> findByPage(int currPage, int pageSize, String cid)throws Exception;

	List<Product> findAll()throws Exception;

	void add(Product p)throws Exception;
	
}
