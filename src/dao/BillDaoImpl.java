package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.Bill;
import model.Customer;
import model.Product;

public class BillDaoImpl implements BillDao {

	@Override
	public void add(Bill bill) throws SQLException {
		String sql = "INSERT INTO bill(quantity,price,product_id,buy_date,customer_id) VALUES(?,?,?,?,?)";
		Connection conn = JDBCConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setInt(1, bill.getQuantity());
		ps.setDouble(2, bill.getPrice());
		ps.setInt(3, bill.getProduct().getId());
		ps.setDate(4, new java.sql.Date(bill.getBuyDate().getTime()));
		ps.setInt(5, bill.getCustomer().getId());

		ps.executeUpdate();

		int quantityAfter = bill.getProduct().getQuantity() - bill.getQuantity();
		ProductDao productDao = new ProductDaoImpl();
		productDao.updateQuantity(bill.getProduct().getId(), quantityAfter);
	}

	@Override
	public List<Bill> findBuyDate(Date fromDate, Date toDate) {
		List<Bill> bills = new ArrayList<Bill>();
		try {
			String sql = "Select b.id, b.quantity, b.price , b. buy_date , b.product_id , b.customer_id , p.name "
					+ "from bill b "
					+ "INNER JOIN product p ON b.product_id = p.id "
					+ "INNER JOIN customer c ON b.customer_id = c.id "
					+ "WHERE b.buy_date >= ? AND b.buy_date <= ? "
					+ "ORDER BY b.buy_date ASC ";
			Connection connection = JDBCConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setDate(1, new java.sql.Date(fromDate.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(toDate.getTime()));

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Bill b = new Bill();
				b.setId(rs.getInt("id"));
				b.setQuantity(rs.getInt("quantity"));
				b.setPrice(rs.getDouble("price"));
				b.setBuyDate(rs.getDate("buy_date"));

				Product p = new Product();
				p.setId(rs.getInt("product_id"));
				p.setName(rs.getString("name"));
				b.setProduct(p);
				
				Customer c = new Customer();
				c.setId(rs.getInt("customer_id"));
				b.setCustomer(c);	
				bills.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bills;
	}
	@Override
	public void input(Bill bill) {
		Scanner sc = new Scanner(System.in);
		ProductDao productDao = new ProductDaoImpl();
		while (true) {
			System.out.println("Điền id sản phẩm muốn mua: ");
			int id = sc.nextInt();
			bill.setProduct(productDao.getOne(id));

			if (bill.getProduct() != null) {
				break;
			} else {
				System.out.println("Không có sản phẩm này. Chọn lại.");
			}
		}
		while (true) {
			System.out.print("Nhập số lượng muốn mua: ");
			bill.setQuantity(sc.nextInt());
			if (bill.getQuantity() > bill.getProduct().getQuantity()) {
				System.out.println("Không đủ số lượng");
			} else {
				break;
			}
		}
		bill.setPrice(bill.getProduct().getPrice());
		while (true) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				System.out.print("Nhập ngày mua: ");
				String date = new Scanner(System.in).nextLine();
				bill.setBuyDate(dateFormat.parse(date));
				break;
			} catch (ParseException ex) {
				System.out.println("Nhập lại ngày");
			}
		}
		CustomerDao customerDao = new CustomerDaoImpl();
		while (true) {
			System.out.print("Nhập số id khách hàng: ");
			int id = sc.nextInt();
			bill.setCustomer(customerDao.getOne(id));
			if (bill.getProduct() != null) {
				break;
			} else {
				System.out.println("Không có sản phẩm này. Chọn lại.");
			}
		}
	}
	@Override
	public void info(Bill bill) {
		System.out.println("----THÔNG TIN SẢN PHẨM----");
		System.out.println("ID: " + bill.getId());
		System.out.println("Số lượng: " + bill.getQuantity());
		System.out.println("Giá: " + bill.getPrice());
		System.out.println("Tổng giá: " + bill.getQuantity() * bill.getPrice());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("Ngày mua: " + dateFormat.format(bill.getBuyDate()));
	}

}
