package dao;

import java.sql.SQLException;
import java.util.List;
import model.Supplier;

public interface SupplierDao {
	void input(Supplier supplier);

	void info(Supplier supplier);

	void add(Supplier supplier) throws SQLException;

	void update(Supplier supplier);

	void delete(int id) throws SQLException;
	
	Supplier getOne(int id);

        List<Supplier> getAll();
}
