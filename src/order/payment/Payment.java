package order.payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

	public Payment() {
		setLayout(new FlowLayout());
		
		//�� ��Ȳ�� �гε�
		p_menu = new JPanel();
		p_cash = new PaymentPanel(p_menu, "cash");
		p_credit = new PaymentPanel(p_menu, "credit");
		p_coupon = new PaymentPanel(p_menu, "coupon");
		
		//�� ��ư��
		bt_cash = new JButton("����");
		bt_credit = new JButton("ī��");
		bt_coupon = new JButton("����");
		
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
		t_cash_cost = new JTextField(18);
		t_cash_out = new JTextField(18);

		t_credit_cost = new JTextField(18);
		t_credit_number = new JTextField(18);
		t_credit_company = new JTextField(18);
		
		t_coupon_number = new JTextField(20);
		
		//�����ݾ��� ���� ����
		t_cash_cost.setEditable(false);
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
		
		bt_cash.addActionListener(this);
		bt_credit.addActionListener(this);
		bt_coupon.addActionListener(this);
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==bt_cash){
			p_menu.setVisible(false);
			p_cash.setVisible(true);
			p_credit.setVisible(false);
			p_coupon.setVisible(false);
		}else if(obj==bt_credit){
			p_menu.setVisible(false);
			p_cash.setVisible(false);
			p_credit.setVisible(true);
			p_coupon.setVisible(false);
		}else if(obj==bt_coupon){
			p_menu.setVisible(false);
			p_cash.setVisible(false);
			p_credit.setVisible(false);
			p_coupon.setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		new Payment();

	}

}
