package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import model.Customer;

public class CustomerDaoImpl implements CustomerDao{
    @Override
    public void add(Customer customer) throws SQLException {
    if (customer.getName() == null || customer.getName().isEmpty() || customer.getPhone() == 0) {
        // Xử lý đầu vào không hợp lệ
        return;
    }
    String sql = "INSERT INTO customer(name, phone) VALUES(?, ?)";
    try (Connection conn = JDBCConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, customer.getName());
        ps.setInt(2, customer.getPhone());
        ps.executeUpdate();

    } catch (SQLException e) {
        // Xử lý ngoại lệ một cách thích hợp (ghi log hoặc ném lại)
        e.printStackTrace();
    }
}
// Các phương thức khác có thể được cải tiến tương tự dựa trên các xem xét.
	@Override
	public void input(Customer customer) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập tên khách hàng: ");
		customer.setName(sc.nextLine());	
		System.out.println("Nhập số điện thoại khách hàng: ");
		customer.setPhone(sc.nextInt());
	}

	@Override
	public void info(Customer customer) {
		System.out.println("----THÔNG TIN KHÁCH HÀNG----");
		System.out.println("ID: " + customer.getId());
		System.out.println("Tên: " + customer.getName());
		System.out.println("SĐT: " + customer.getPhone());
		
	}

	@Override
	public void update(Customer customer) {
		try {
			String sql = "UPDATE customer SET name = ?, phone = ? WHERE id = ?";
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, customer.getName());
			ps.setInt(2, customer.getId());
			ps.setInt(3, customer.getPhone());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM customer WHERE id = ?";
		Connection conn = JDBCConnection.getConnection();

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);

		ps.executeUpdate();
	}

	@Override
	public Customer getOne(int id) {
		try {
			String sql = "SELECT * FROM customer WHERE customer.id = ?";
			Connection conn = JDBCConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getInt("id"));
				
				return c;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
