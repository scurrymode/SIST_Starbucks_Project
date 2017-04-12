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
		
		//각 상황별 패널들
		p_menu = new JPanel();
		p_cash = new PaymentPanel(p_menu, "cash");
		p_credit = new PaymentPanel(p_menu, "credit");
		p_coupon = new PaymentPanel(p_menu, "coupon");
		
		//각 버튼들
		bt_cash = new JButton("현금");
		bt_credit = new JButton("카드");
		bt_coupon = new JButton("쿠폰");
		
		//각 라벨들
		la_cash_in = new JLabel("받은 돈");
		la_cash_cost = new JLabel("결제금액");
		la_cash_out = new JLabel("거스름 돈");
		
		la_credit_cost = new JLabel("결제금액");
		la_credit_number = new JLabel("카드번호");
		la_credit_company = new JLabel("카드사");
		
		la_coupon_number = new JLabel("쿠폰번호");
		
		//각 텍스트필드
		t_cash_in = new JTextField(18);
		t_cash_cost = new JTextField(18);
		t_cash_out = new JTextField(18);

		t_credit_cost = new JTextField(18);
		t_credit_number = new JTextField(18);
		t_credit_company = new JTextField(18);
		
		t_coupon_number = new JTextField(20);
		
		//결제금액은 변경 금지
		t_cash_cost.setEditable(false);
		t_credit_cost.setEditable(false);
		
		//패널들 크기 확정
		p_menu.setPreferredSize(new Dimension(300,350));
		p_cash.setPreferredSize(new Dimension(300,350));
		p_credit.setPreferredSize(new Dimension(300,350));
		p_coupon.setPreferredSize(new Dimension(300,350));
		
		
		//메뉴패널 설정
		p_menu.setLayout(new GridLayout(3, 1));
		p_menu.add(bt_cash);
		p_menu.add(bt_credit);
		p_menu.add(bt_coupon);
		
		//현금패널 설정
		p_cash.p_center.add(la_cash_in);
		p_cash.p_center.add(t_cash_in);
		p_cash.p_center.add(la_cash_cost);
		p_cash.p_center.add(t_cash_cost);
		p_cash.p_center.add(la_cash_out);
		p_cash.p_center.add(t_cash_out);
		
		//카드패널 설정
		p_credit.p_center.add(la_credit_cost);
		p_credit.p_center.add(t_credit_cost);
		p_credit.p_center.add(la_credit_number);
		p_credit.p_center.add(t_credit_number);
		p_credit.p_center.add(la_credit_company);
		p_credit.p_center.add(t_credit_company);
		
		//쿠폰패널 설정
		p_coupon.p_center.add(la_coupon_number);
		p_coupon.p_center.add(t_coupon_number);
		
		
		//색깔설정
		p_menu.setBackground(Color.BLACK);
		p_cash.setBackground(Color.CYAN);
		p_credit.setBackground(Color.PINK);
		p_coupon.setBackground(Color.RED);
		
		add(p_menu);
		add(p_cash);
		add(p_credit);
		add(p_coupon);
		
		//각 패널 노출 여부
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
