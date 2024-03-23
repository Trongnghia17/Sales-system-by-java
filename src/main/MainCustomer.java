package main;

import java.sql.SQLException;
import java.util.Scanner;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import dao.SupplierDao;
import dao.SupplierDaoImpl;
import model.Customer;
import model.Supplier;

public class MainCustomer {
	public static void main(String[] args) {
		while (true) {
			System.out.println("1. Tạo khách hàng:");
			System.out.println("2. Sửa khách hàng");
			System.out.println("3. Xóa khách hàng");
			System.out.println("4. Thoát");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			if (choice == 1) {
				createCustomer();
			}else if (choice == 2) {
				updateCustomer();
			}else if (choice == 3) {
				deleteCustomer();
			} else if (choice == 4) {
				break;
			} else {
				System.out.println("Nhập từ 1 đến 4");
			}
		}
	}

	public static void createCustomer() {
		System.out.println("Nhập thông tin ");
		CustomerDao customerDao = new CustomerDaoImpl();
		Customer customer = new Customer();
		customerDao.input(customer);
		try {
			customerDao.add(customer);
			System.out.println("Thành công");
		} catch (SQLException e) {
			System.out.println("Thất bại");
			e.printStackTrace();
		}
	}
	public static void updateCustomer() {
		Customer customer = new Customer();

		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần sửa");
		int id = sc.nextInt();
		customer.setId(id);

		CustomerDao customerDao = new CustomerDaoImpl();
		customerDao.input(customer);

		customerDao.update(customer);

		System.out.println("Thành công");
	}

	public static void deleteCustomer() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần xóa");

		int id = sc.nextInt();
		CustomerDao customerDao = new CustomerDaoImpl();
		try {
			customerDao.delete(id);
			System.out.println("Thành công");
		} catch (SQLException e) {
			System.out.println("Thất bại");
			e.printStackTrace();
		}
	}

	
}
