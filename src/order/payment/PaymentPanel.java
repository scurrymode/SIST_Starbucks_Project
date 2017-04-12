package order.payment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PaymentPanel extends JPanel implements ActionListener{
	JButton bt_back, bt_payment_complete;
	JPanel p_menu, p_south, p_center;
	String orders_payment_type;
	
	public PaymentPanel(JPanel p_menu, String type) {
		this.p_menu=p_menu;
		this.orders_payment_type=type;
		
		setLayout(new BorderLayout());
		
		p_south=new JPanel();
		p_center = new JPanel();
		
		bt_back = new JButton("뒤로");
		bt_payment_complete = new JButton("결제완료");
		
		p_south.add(bt_back);
		p_south.add(bt_payment_complete);
		
		add(p_south, BorderLayout.SOUTH);
		add(p_center);
		
		bt_back.addActionListener(this);
		bt_payment_complete.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		 if(obj==bt_back){
			 PaymentPanel.this.setVisible(false);
			 p_menu.setVisible(true);
		}else if(obj==bt_payment_complete){
			System.exit(0);
		}
		
	}

}
