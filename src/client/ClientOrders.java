package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dto.Product;

public class ClientOrders extends JFrame implements ActionListener{
	ClientMain main;
	JPanel p_north, p_center, p_south;
	JButton bt_orders_delete, bt_orders_send;
	Vector<Product> orders_list = new Vector<Product>();
	JScrollPane scroll;
	JLabel la_sum;
	
	
	
	public ClientOrders(ClientMain main) {
		this.main=main;
		
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		scroll = new JScrollPane(p_north);
		
		scroll.setPreferredSize(new Dimension(300*2, 300));
		p_center.setPreferredSize(new Dimension(300*2,150*2));
		p_south.setPreferredSize(new Dimension(300*2,50*2));
		
		bt_orders_delete = new JButton("�����ϱ�");
		bt_orders_send = new JButton("�ֹ��ϱ�");
		
		la_sum = new JLabel("�հ� �ݾ�");
		
		p_south.add(la_sum);
		p_south.add(bt_orders_delete);
		p_south.add(bt_orders_send);
		
		//��ǰ ��� �÷��α�
		init();
				
		add(scroll, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);		
		
		//��ư�� ������ ����
		bt_orders_delete.addActionListener(this);
		bt_orders_send.addActionListener(this);
		
		setSize(300*2,400*2);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//���Ӱ� ���ÿ� ������ ��ǰ ������ ��η� �� ����
	public void init(){
		for(int i=0;i<main.product_list.size();i++){
			ClientMenuPanel cmp = new ClientMenuPanel(this, main.product_list.get(i));
			p_north.add(cmp);
		}	
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("������ ĳġ");
		Object obj = e.getSource();
		
		//�ֹ��ϱ⸦ ���������, 
		if(obj == bt_orders_send){
			//�󺧿� ������ �͵��� product�� �����;��Ѵ�.
			ClientThread thread = new ClientThread(main, orders_list);
			thread.start();
			
		}else if (obj == bt_orders_delete){
			System.out.println("����");
			orders_list.removeAllElements();
			la_sum.setText("�հ�ݾ�");
			p_center.removeAll();
			p_center.updateUI();
		}		
	}
}
