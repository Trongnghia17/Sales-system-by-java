package model;

public class Customer {
	private int id;
	private String name;
	private int phone;
	private Product product;

	public Customer() {

	}

	public Customer(int id, String name, int phone, Product product) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.product = product;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
