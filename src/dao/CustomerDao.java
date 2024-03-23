package dao;

import java.sql.SQLException;
import model.Customer;

public interface CustomerDao {
	void input(Customer customer);
	void info(Customer customer);
	void add(Customer customer) throws SQLException;
	void update(Customer customer);
	void delete(int id) throws SQLException;
	Customer getOne(int id);
}
