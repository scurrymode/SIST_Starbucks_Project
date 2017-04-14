package client;

import java.io.IOException;

import dto.Product;

public class ClientThread extends Thread{
	ClientMain main;
	Product product;
	String msg;
	
	public ClientThread(ClientMain main, Product product) {
		this.main = main;
		this.product = product;
		
		int product_id = product.getProduct_id();
		int product_category_id=product.getProduct_category_id();
		String product_name = product.getProduct_name();
		int product_price = product.getProduct_price();
		
		msg = "msg:orders&id:"+main.login_id+"&product_id:"+product_id+"&time:";

	}
	public void run() {
		try {
			main.buffw.write(msg+"\n");
			main.buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
