package dao;

import java.util.List;

import domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

}
