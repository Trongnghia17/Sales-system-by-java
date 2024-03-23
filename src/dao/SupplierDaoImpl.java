package dao;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;

public class SupplierDaoImpl implements SupplierDao {

	@Override
	public void input(Supplier supplier) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập tên: ");
		supplier.setName(sc.nextLine());
	}
	@Override
	public void info(Supplier supplier) {
		System.out.println("----THÔNG TIN NHÀ CUNG CẤP----");
		System.out.println("ID: " +  supplier.getId());
		System.out.println("Tên : " + supplier.getName());
	}
	@Override
	public void add(Supplier supplier)throws SQLException {
		String sql = "INSERT INTO supplier(name) VALUES(?)";
		Connection conn = JDBCConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, supplier.getName());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			int id = rs.getInt(1);
			supplier.setId(id);
		}
	}

	@Override
	public void update(Supplier supplier) {
		try {
			String sql = "UPDATE supplier SET name = ? WHERE id = ?";
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, supplier.getName());
			ps.setInt(2, supplier.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM supplier WHERE id = ?";
		Connection conn = JDBCConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
	}

	@Override
	public Supplier getOne(int id) {
		try {
			String sql = "SELECT * FROM supplier WHERE id = ?";
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Supplier c = new Supplier();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				return c;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM supplier";
            Connection conn = JDBCConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }

   


}
