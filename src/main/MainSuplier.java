package main;

import java.sql.SQLException;
import java.util.Scanner;
import dao.SupplierDao;
import dao.SupplierDaoImpl;
import model.Supplier;

public class MainSuplier {
	public static void main(String[] args) {
		while (true) {
			System.out.println("1. Tạo nhà cung cấp");
			System.out.println("2. Sửa nhà cung cấp");
			System.out.println("3. Xóa nhà cung cấp");
			System.out.println("4. Thoát");

			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			if (choice == 1) {
				createSuplier();
			} else if (choice == 2) {
				updateSuplier();
			} else if (choice == 3) {
				deleteSuplier();
			} else if (choice == 4) {
				break;
			} else {
				System.out.println("Nhập từ 1 đến 4");
			}
		}
	}

	public static void createSuplier() {
		System.out.println("Nhập thông tin ");
		SupplierDao supplierDao = new SupplierDaoImpl();
		Supplier supplier = new Supplier();
		supplierDao.input(supplier);
		try {
			supplierDao.add(supplier);
			System.out.println("Thành công");
		} catch (SQLException e) {
			System.out.println("Thất bại");
			e.printStackTrace();
		}
	}

	public static void updateSuplier() {
		Supplier supplier = new Supplier();

		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần sửa");
		int id = sc.nextInt();
		supplier.setId(id);

		SupplierDao supplierDao = new SupplierDaoImpl();
		supplierDao.input(supplier);

		supplierDao.update(supplier);

		System.out.println("Thành công");
	}

	public static void deleteSuplier() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần xóa");

		int id = sc.nextInt();
		SupplierDao supplierDao = new SupplierDaoImpl();
		try {
			supplierDao.delete(id);
			System.out.println("Thành công");
		} catch (SQLException e) {
			System.out.println("Thất bại");
			e.printStackTrace();
		}
	}
}
