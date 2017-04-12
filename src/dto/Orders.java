package dto;

public class Orders {
	private int orders_id;
	private int product_id;
	private String orders_date;
	private int orders_emp_id;
	private int orders_client_id;
	private int orders_status;
	private int orders_payment_type;
	
	
	public int getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(int orders_id) {
		this.orders_id = orders_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getOrders_date() {
		return orders_date;
	}
	public void setOrders_date(String orders_date) {
		this.orders_date = orders_date;
	}
	public int getOrders_emp_id() {
		return orders_emp_id;
	}
	public void setOrders_emp_id(int orders_emp_id) {
		this.orders_emp_id = orders_emp_id;
	}
	public int getOrders_client_id() {
		return orders_client_id;
	}
	public void setOrders_client_id(int orders_client_id) {
		this.orders_client_id = orders_client_id;
	}
	public int getOrders_status() {
		return orders_status;
	}
	public void setOrders_status(int orders_status) {
		this.orders_status = orders_status;
	}
	public int getOrders_payment_type() {
		return orders_payment_type;
	}
	public void setOrders_payment_type(int orders_payment_type) {
		this.orders_payment_type = orders_payment_type;
	}
	
	
	

}
