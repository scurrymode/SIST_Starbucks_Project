package client;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JLabel;

import dto.Product;
import json.OrdersProtocol;

public class ClientThread extends Thread{
	ClientMain main;
	Vector<Product> product;
	String msg;
	
	//���� ���� ����
		Socket socket;
		int port = 7777;
		String host = "211.238.142.118";
		BufferedReader buffr;
		BufferedWriter buffw;
	
	public ClientThread(ClientMain main, Vector<Product> product) {
		this.main = main;
		this.product = product;
		
		//���� �ű�
		try {
			socket = new Socket(host, port);
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		send();
	}
	
	public void run() {
		while(true){
			listen();
		}
	}
	
	public void listen(){
		try {
			String data = buffr.readLine();
			
			if(data.equals("�ֹ��Ϸ�")){
				main.orders.p_center.removeAll();
				JLabel la = new JLabel("�ֹ��Ϸ�! â���� Ȯ�����ּ���");
				la.setFont(new Font("����", Font.BOLD, 30));
				main.orders.p_center.add(la);
				main.orders.p_center.updateUI();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void send(){
		for(int i=0; i<product.size();i++){
			int product_id = product.get(i).getProduct_id();
	
			int id=main.member.getMember_id();
			
			//�� �ش系���� ���� �� �ִ� json �������� �����
			OrdersProtocol op = new OrdersProtocol(product_id, id);
			StringBuffer sb = op.getProtocol();
			msg = sb.toString();
			
			try {
				buffw.write(msg+"\n");
				buffw.flush();
				System.out.println("�ֹ� �ֱ�");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
