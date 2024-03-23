package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Category;
import model.Product;
import model.Supplier;

public class ProductDaoImpl implements ProductDao {

	@Override
	public void input(Product product) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập tên: ");
		product.setName(sc.nextLine());

		System.out.println("Nhập số lượng: ");
		product.setQuantity(sc.nextInt());

		System.out.println("Nhâp giá: ");
		product.setPrice(sc.nextDouble());

		while (true) {
			System.out.println("Enter category_id: ");
			CategoryDao categoryDao = new CategoryDaoImpl();
			Category c = categoryDao.getOne(sc.nextInt());
			product.setCategory(c);
			if (product.getCategory() != null) {
				break;
			}
		}
		while (true) {
			System.out.println("Enter supplier_id: ");
			SupplierDao supplierDao = new SupplierDaoImpl();
			Supplier supplier = supplierDao.getOne(sc.nextInt());
		

			product.setSupplier(supplier);

			if (product.getSupplier() != null) {
				break;
			}
		}
	}

	@Override
	public void info(Product product) {
		System.out.println("----THÔNG TIN SẢN PHẨM----");
		System.out.println("ID: " + product.getId());
		System.out.println("Tên : " + product.getName());
		System.out.println("Số lượng: " + product.getQuantity());
		System.out.println("Giá: " + product.getPrice());
		if (product.getCategory() != null) {
			CategoryDao categoryDao = new CategoryDaoImpl();
			categoryDao.info(product.getCategory());
		}
		if (product.getSupplier() != null) {
			SupplierDao supplierDao = new SupplierDaoImpl();
			supplierDao.info(product.getSupplier());
		}
	}

	@Override
	public void add(Product product) throws Exception {
		String sql = "INSERT INTO product(name,quantity,price,category_id,supplier_id) VALUES(?,?,?,?,?)";
		Connection conn = JDBCConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, product.getName());
		ps.setInt(2, product.getQuantity());
		ps.setDouble(3, product.getPrice());
		ps.setInt(4, product.getCategory().getId());
		ps.setInt(5, product.getSupplier().getId());

		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			int id = rs.getInt(1);
			product.setId(id);
		}
	}

	@Override
	public void update(Product product) {
		try {
			String sql = "UPDATE product SET name = ?, quantity = ?, price = ?, category_id = ?, supplier_id = ? WHERE id = ?";
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, product.getName());
			ps.setInt(2, product.getQuantity());
			ps.setDouble(3, product.getPrice());
			ps.setInt(4, product.getCategory().getId());
			ps.setInt(5, product.getSupplier().getId());
			ps.setInt(6, product.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) throws Exception {
		String sql = "DELETE FROM product WHERE id = ?";
		Connection conn = JDBCConnection.getConnection();

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);

		ps.executeUpdate();
	}

	@Override
	public List<Product> searchByName(String name) {
	    List<Product> list = new ArrayList<Product>();

	    try {
	        // Use regular expression pattern to handle multiple spaces
	        String sql = "SELECT pr.id, pr.name, pr.quantity, pr.price, pr.supplier_id, pr.category_id, ct.name as 'c_name', sp.name as 'p_name' "
	                + "FROM product AS pr "
	                + "INNER JOIN category AS ct ON ct.id = pr.category_id "
	                + "INNER JOIN supplier AS sp ON sp.id = pr.supplier_id "
	                + "WHERE pr.name LIKE ? ";

	        Connection conn = JDBCConnection.getConnection();

	     // Tách tên đầu vào thành các từ riêng biệt
	        String[] words = name.split("\\s+");
	        // StringBuilder để xây dựng mẫu
	        StringBuilder patternBuilder = new StringBuilder();
	        // Duyệt qua từng từ
	        for (String word : words) {
	            patternBuilder.append("%").append(word).append("%");
	        }

	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, patternBuilder.toString());

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Product p = new Product();

	            p.setId(rs.getInt("id"));
	            p.setName(rs.getString("name"));
	            p.setQuantity(rs.getInt("quantity"));
	            p.setPrice(rs.getDouble("price"));

	            Category c = new Category();
	            c.setId(rs.getInt("category_id"));
	            c.setName(rs.getString("c_name"));

	            p.setCategory(c);

	            Supplier supplier = new Supplier();
	            supplier.setId(rs.getInt("supplier_id"));
	            supplier.setName(rs.getString("p_name"));

	            p.setSupplier(supplier);

	            list.add(p);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}



	@Override
	public List<Product> searchByPrice(int fromPrice, int toPrice) {
		List<Product> list = new ArrayList<>();
		try {
			String sql = "SELECT pr.id, pr.name, pr.quantity, pr.price, pr.supplier_id, pr.category_id, ct.name as 'c_name', sp.name as 'p_name' "
	                + "FROM product AS pr "
	                + "INNER JOIN category AS ct ON ct.id = pr.category_id "
	                + "INNER JOIN supplier AS sp ON sp.id = pr.supplier_id "
	                + "WHERE pr.price >= ? AND pr.price <= ? ";

			Connection conn = JDBCConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, fromPrice);
			ps.setInt(2, toPrice);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();

				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setQuantity(rs.getInt("quantity"));
				p.setPrice(rs.getDouble("price"));

				Category c = new Category();
				c.setId(rs.getInt("category_id"));
				c.setName(rs.getString("c_name"));
				p.setCategory(c);
				
				Supplier supplier = new Supplier();
	            supplier.setId(rs.getInt("supplier_id"));
	            supplier.setName(rs.getString("p_name"));
	            
	            p.setSupplier(supplier);

				list.add(p);
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public List<Product> searchByNameOfCategory(String nameOfCategory) {
		List<Product> list = new ArrayList<>();
		try {
			String sql = "SELECT pr.id, pr.name, pr.quantity, pr.price, pr.supplier_id, pr.category_id, ct.name as 'c_name', sp.name as 'p_name' "
					+ "FROM product AS pr "
					+ "INNER JOIN category AS ct ON ct.id = pr.category_id "
					+ "INNER JOIN supplier AS sp ON sp.id = pr.supplier_id "
					+ "WHERE ct.name LIKE ?";

			Connection conn = JDBCConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + nameOfCategory + "%");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();

				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setQuantity(rs.getInt("quantity"));
				p.setPrice(rs.getDouble("price"));

				Category c = new Category();
				c.setId(rs.getInt("category_id"));
				c.setName(rs.getString("c_name"));
				p.setCategory(c);
				
				Supplier supplier = new Supplier();
	            supplier.setId(rs.getInt("supplier_id"));
	            supplier.setName(rs.getString("p_name"));
	            
	            p.setSupplier(supplier);

				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Product getOne(int id) {
		try {
			String sql = "SELECT * FROM product WHERE product.id = ?";
			Connection conn = JDBCConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setQuantity(rs.getInt("quantity"));
				p.setPrice(rs.getDouble("price"));

				Category c = new Category();
				c.setId(rs.getInt("category_id"));

				p.setCategory(c);
				return p;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateQuantity(int id, int amount) {
		try {
			String sql = "UPDATE product SET quantity = ? WHERE product.id = ?";

			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, amount);
			ps.setInt(2, id);

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
