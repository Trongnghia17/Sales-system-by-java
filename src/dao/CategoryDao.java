package dao;

import java.sql.SQLException;
import java.util.List;

import model.Category;

public interface CategoryDao {

	void input(Category category);

	void info(Category category);

	void add(Category category) throws SQLException;

	void update(Category category);

	void delete(int id) throws SQLException;

	Category getOne(int id);
        List<Category> getAll();
}
