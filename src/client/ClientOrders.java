package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientOrders extends JFrame implements ActionListener{
	ClientMain main;
	JPanel p_north, p_center, p_south;
	JButton bt_orders_send;
	Vector<JButton> bt_list = new Vector<JButton>();
	
	
	public ClientOrders(ClientMain main) {
		this.main=main;
		
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		p_north.setPreferredSize(new Dimension(300, 200));
		p_center.setPreferredSize(new Dimension(300,150));
		p_south.setPreferredSize(new Dimension(300,50));
		
		bt_orders_send = new JButton("주문하기");
		
		p_south.add(bt_orders_send);
		
		//제품 목록 올려두기
		init();
				
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);		
		
		bt_orders_send.addActionListener(this);
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void init(){
		for(int i=0;i<main.product_list.size();i++){
			JButton bt = new JButton(main.product_list.get(i).getProduct_name());
			p_north.add(bt);
			bt.addActionListener(this);
			bt_list.add(bt);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("누른건 캐치");
		System.out.println(bt_list.get(0));
		Object obj = e.getSource();
		if(obj == bt_list.get(0)){
			System.out.println("누른건 캐치");
			
			//일단 아메리카노 누르면 그냥 주문 들어가게 해놨다.
			ClientThread thread = new ClientThread(main, main.product_list.get(0));
			thread.start();
			
		}
		
		
	}
	
	
	
	

}
