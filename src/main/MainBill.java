package main;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import dao.BillDao;
import dao.BillDaoImpl;
import model.Bill;

public class MainBill {
	public static void main(String[] args) throws ParseException {
		while (true) {
			System.out.println("1. Mua sản phẩm");
			System.out.println("2. Tìm theo ngày mua");
			System.out.println("3. Thoát");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			if (choice == 1) {
				buyProduct();
			} else if (choice == 2) {
				find();
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Chọn từ 1 đến 3");
			}
		}
	}

	public static void buyProduct() {
		Bill b = new Bill();
		BillDao billDao = new BillDaoImpl();
		billDao.input(b);
		try {
			billDao.add(b);
			System.out.println("Thành công");
		} catch (SQLException e) {
			System.out.println("Thất bại");
			e.printStackTrace();
		}
	}

	public static void find() {
		Scanner sc = new Scanner(System.in);
		BillDao billDao = new BillDaoImpl();

		Date from = new Date();
		Date to = new Date();
		while (true) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				System.out.print("Nhập ngày bắt đầu: ");
				from = dateFormat.parse(sc.nextLine());
				break;
			} catch (ParseException ex) {
				System.out.println("Nhập lại ngày: ");
			}
		}
		while (true) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				System.out.print("Nhập ngày kết thúc: ");
				to = dateFormat.parse(sc.nextLine());
				break;
			} catch (ParseException ex) {
				System.out.println("Nhập lại ngày: ");
			}
		}
		List<Bill> bills = billDao.findBuyDate(from, to);
		for (Bill bill : bills)
			billDao.info(bill);
	}

}
