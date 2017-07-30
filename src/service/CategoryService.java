package service;

import java.util.List;

import domain.Category;

public interface CategoryService {

	List<Category> findAll() throws Exception;
	
}
