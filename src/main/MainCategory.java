package main;

import java.sql.SQLException;
import java.util.Scanner;

import dao.CategoryDao;
import dao.CategoryDaoImpl;
import model.Category;

public class MainCategory {
	public static void main(String[] args) {
		while (true) {
			System.out.println("1. Tạo loại");
			System.out.println("2. Sửa loại");
			System.out.println("3. Xóa loại");
			System.out.println("4. Thoát");

			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			if (choice == 1) {
				createCategory();
			} else if (choice == 2) {
				updateCategory();
			} else if (choice == 3) {
				deleteCategory();
			} else if (choice == 4) {
				break;
			} else {
				System.out.println("Nhập từ 1 đến 4");
			}
		}
	}

	public static void createCategory() {
		System.out.println("Nhập thông tin ");
		CategoryDao categoryDao = new CategoryDaoImpl();

		Category category = new Category();

		categoryDao.input(category);
		try {
			categoryDao.add(category);
			System.out.println("Thành công");
		} catch (SQLException e) {
			System.out.println("Thất bại");
			e.printStackTrace();
		}
	}

	public static void updateCategory() {
		Category category = new Category();

		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần sửa");
		int id = sc.nextInt();
		category.setId(id);

		CategoryDao categoryDao = new CategoryDaoImpl();
		categoryDao.input(category);

		categoryDao.update(category);

		System.out.println("Thành công");
	}

	public static void deleteCategory() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần xóa");

		int id = sc.nextInt();
		CategoryDao categoryDao = new CategoryDaoImpl();
		try {
			categoryDao.delete(id);
			System.out.println("Thành công");
		} catch (SQLException e) {
			System.out.println("Thất bại");
			e.printStackTrace();
		}
	}

}
