package order.payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dto.Orders;

public class Payment extends JFrame implements ActionListener{
	JPanel p_menu;
	PaymentPanel p_cash, p_credit, p_coupon;
	JButton bt_cash, bt_credit, bt_coupon;
	JLabel la_cash_in, la_cash_cost, la_cash_out;
	JLabel la_credit_cost, la_credit_number, la_credit_company;
	JLabel la_coupon_number;
	JTextField t_cash_in, t_cash_cost, t_cash_out;
	JTextField t_credit_cost, t_credit_number, t_credit_company;
	JTextField t_coupon_number;
	Orders dto; //�ֹ����� dto
	int price;//�����ݾ�
	JButton bt_cardRead;//ī������ �о���̱�
	
	public Payment(Orders dto, int price) {
		this.dto=dto;
		this.price=price;
		setLayout(new FlowLayout());
		
		//�� ��Ȳ�� �гε�
		p_menu = new JPanel();
		p_cash = new PaymentPanel(this, "cash");
		p_credit = new PaymentPanel(this, "credit");
		p_coupon = new PaymentPanel(this, "coupon");
		
		//�� ��ư��
		bt_cash = new JButton("����");
		bt_credit = new JButton("ī��");
		bt_coupon = new JButton("����");
		
		bt_cardRead = new JButton("ī��ܱ�");
		
		//�� �󺧵�
		la_cash_in = new JLabel("���� ��");
		la_cash_cost = new JLabel("�����ݾ�");
		la_cash_out = new JLabel("�Ž��� ��");
		
		la_credit_cost = new JLabel("�����ݾ�");
		la_credit_number = new JLabel("ī���ȣ");
		la_credit_company = new JLabel("ī���");
		
		la_coupon_number = new JLabel("������ȣ");
		
		//�� �ؽ�Ʈ�ʵ�
		t_cash_in = new JTextField(18);
		t_cash_cost = new JTextField(Integer.toString(price), 18);
		t_cash_out = new JTextField(18);

		t_credit_cost = new JTextField(Integer.toString(price), 18);
		t_credit_number = new JTextField(18);
		t_credit_company = new JTextField(18);
		
		t_coupon_number = new JTextField(20);
		
		//�����ݾ��� ���� ����
		t_cash_cost.setEditable(false);
		t_cash_out.setEditable(false);
		t_credit_cost.setEditable(false);
		
		//�гε� ũ�� Ȯ��
		p_menu.setPreferredSize(new Dimension(300,350));
		p_cash.setPreferredSize(new Dimension(300,350));
		p_credit.setPreferredSize(new Dimension(300,350));
		p_coupon.setPreferredSize(new Dimension(300,350));
		
		//�޴��г� ����
		p_menu.setLayout(new GridLayout(3, 1));
		p_menu.add(bt_cash);
		p_menu.add(bt_credit);
		p_menu.add(bt_coupon);
		
		//�����г� ����
		p_cash.p_center.add(la_cash_in);
		p_cash.p_center.add(t_cash_in);
		p_cash.p_center.add(la_cash_cost);
		p_cash.p_center.add(t_cash_cost);
		p_cash.p_center.add(la_cash_out);
		p_cash.p_center.add(t_cash_out);
		
		//ī���г� ����
		p_credit.p_center.add(la_credit_cost);
		p_credit.p_center.add(t_credit_cost);
		p_credit.p_center.add(la_credit_number);
		p_credit.p_center.add(t_credit_number);
		p_credit.p_center.add(la_credit_company);
		p_credit.p_center.add(t_credit_company);
		p_credit.p_center.add(bt_cardRead);
		
		//�����г� ����
		p_coupon.p_center.add(la_coupon_number);
		p_coupon.p_center.add(t_coupon_number);
		
		//������
		p_menu.setBackground(Color.BLACK);
		p_cash.setBackground(Color.CYAN);
		p_credit.setBackground(Color.PINK);
		p_coupon.setBackground(Color.RED);
		
		add(p_menu);
		add(p_cash);
		add(p_credit);
		add(p_coupon);
		
		//�� �г� ���� ����
		p_menu.setVisible(true);
		p_cash.setVisible(false);
		p_credit.setVisible(false);
		p_coupon.setVisible(false);
		
		
		//��ư�� ������ ����
		bt_cash.addActionListener(this);
		bt_credit.addActionListener(this);
		bt_coupon.addActionListener(this);
		bt_cardRead.addActionListener(this);
		
		//�ؽ�Ʈ�ʵ忡 ������ ����
		t_cash_in.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					int in=Integer.parseInt(t_cash_in.getText());
					int cost=Integer.parseInt(t_cash_cost.getText());
					t_cash_out.setText(Integer.toString(in-cost));
					t_cash_out.updateUI();
				}
			}
		});
		
		
		setSize(300,400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==bt_cash){
			p_menu.setVisible(false);
			p_cash.setVisible(true);
		}else if(obj==bt_credit){
			p_menu.setVisible(false);
			p_credit.setVisible(true);
		}else if(obj==bt_coupon){
			p_menu.setVisible(false);
			p_coupon.setVisible(true);
			couponReader();
		}else if(obj==bt_cardRead){
			cardReader();
		}
	}
	
	public void cardReader(){
		String cardNumber = "6258-****-****6048";
		String cardCompany = "����ī��";
		
		t_credit_number.setText(cardNumber);
		t_credit_company.setText(cardCompany);
	}
	
	public void couponReader(){
		Thread thread = new Thread(){
			public void run() {
				try {
					
					long data = (long)System.in.read();
					System.out.println(data);
					t_coupon_number.setText(Long.toString(data));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	
	
	public static void main(String[] args) {
		Orders dto = new Orders();
		new Payment(dto, 4900);

	}

}
