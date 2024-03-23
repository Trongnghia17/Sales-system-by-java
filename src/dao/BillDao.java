//DataAccessObject (DAO): là một interface định nghĩa các phương thức trừu tượng việc triển khai truy cập dữ liệu cơ bản cho BusinessObject để cho phép truy cập vào nguồn dữ liệu (DataSource).
package dao;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.Bill;

public interface BillDao {

	void add(Bill bill) throws SQLException;

	List<Bill> findBuyDate(Date fromDate, Date toDate);

	void input(Bill bill);

	void info(Bill bill);

}
