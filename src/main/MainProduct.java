package main;
import java.util.List;
import java.util.Scanner;
import dao.ProductDao;
import dao.ProductDaoImpl;
import model.Product;

public class MainProduct {
	public static void main(String[] args) {
		while (true) {
			System.out.println("1. Thêm sản phẩm");
			System.out.println("2. Sửa sản phẩm");
			System.out.println("3. Xóa sản phẩm");
			System.out.println("4. Tìm theo tên sản phẩm");
			System.out.println("5. Tìm theo giá sản phẩm");
			System.out.println("6. Tìm theo tên của loại");
			System.out.println("7. Tìm theo id sản phẩm");
			System.out.println("8. Thay đổi số lượng sản phẩm");
			System.out.println("9. Thoát");
			System.out.println("Mời bạn chọn:");

			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			if (choice == 1) {
				createProduct();
			} else if (choice == 2) {
				updateProduct();
			} else if (choice == 3) {
				deleteProduct();
			} else if (choice == 4) {
				searchByName();
			} else if (choice == 5) {
				searchByPrice();
			} else if (choice == 6) {
				searchByNameOfCategory();
			} else if (choice == 7) {
				getOne();
			} else if (choice == 8) {
				updateQuantity();
			} else if (choice == 9) {
				System.out.println("Thoát");
				break;
			} else {
				System.out.println("Chọn từ 1 đến 9");
			}
		}
	}

	public static void createProduct() {
		System.out.println("Nhập thông tin: ");

		ProductDao productDao = new ProductDaoImpl();
		Product product = new Product();
		productDao.input(product);

		try {
			productDao.add(product);
			System.out.println("Thành công");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateProduct() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần sửa: ");

		int id = sc.nextInt();
		Product product = new Product();
		product.setId(id);

		ProductDao productDao = new ProductDaoImpl();
		productDao.input(product);
		productDao.update(product);

		System.out.println("Thành công");
	}

	public static void deleteProduct() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id cần xóa: ");
		int id = sc.nextInt();

		ProductDao productDao = new ProductDaoImpl();
		try {
			productDao.delete(id);
			System.out.println("Thành công");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void searchByName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập tên sản phẩm cần tìm: ");
		String name = sc.nextLine();

		ProductDao productDao = new ProductDaoImpl();
		List<Product> productList = productDao.searchByName(name);

		if (productList.isEmpty()) {
			System.out.println("Không có dữ liệu");
		} else {
			System.out.println("Sản phẩm cần tìm theo tên: ");
			for (Product product : productList) {
				productDao.info(product);
			}
		}
	}

	public static void searchByPrice() {
		Scanner sc = new Scanner(System.in);
		ProductDao productDao = new ProductDaoImpl();

		System.out.print("Gia tu: ");
		int from = sc.nextInt();

		System.out.print("Den : ");
		int to = sc.nextInt();

		List<Product> productList = productDao.searchByPrice(from, to);
		for (Product product : productList)
			productDao.info(product);

	}

	public static void searchByNameOfCategory() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập tên loại cần tìm: ");
		String name = sc.nextLine();

		ProductDao productDao = new ProductDaoImpl();
		List<Product> productList = productDao.searchByNameOfCategory(name);

		if (productList.isEmpty()) {
			System.out.println("Không có dữ liệu");
		} else {
			System.out.println("Sản phẩm cần tìm tên theo loại: ");
			for (Product product : productList) {
				productDao.info(product);
			}
		}

	}

	public static Product getOne() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id của sản phẩm cần tìm: ");
		int id = sc.nextInt();

		ProductDao productDao = new ProductDaoImpl();
		Product product = productDao.getOne(id);

		if (product == null) {
			System.out.println("Không có dữ liệu");
		} else {
			System.out.println("Sản phầm cần tìm theo id");
			productDao.info(product);
		}
		return product;
	}

	public static void updateQuantity() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nhập id của sản phẩm cần sửa");
		int id = sc.nextInt();

		sc = new Scanner(System.in);
		System.out.println("Nhập số lượng của sản phẩm cần sửa: ");
		int amount = sc.nextInt();

		ProductDao productDao = new ProductDaoImpl();

		Product product = new Product();
		product.setId(id);
		product.setQuantity(amount);

		productDao.updateQuantity(id, amount);
		System.out.println("Thành công");
	}

}
