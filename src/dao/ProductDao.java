package dao;

import java.util.List;
import model.Product;

public interface ProductDao {
	void input(Product product);

	void info(Product product);

	void add(Product product) throws Exception;

	void update(Product product);

	void delete(int id) throws Exception;

	List<Product> searchByName(String name);

	List<Product> searchByPrice(int fromPrice, int toPrice);

	List<Product> searchByNameOfCategory(String nameOfCategory);

	Product getOne(int id);

	void updateQuantity(int id, int amount);

}
