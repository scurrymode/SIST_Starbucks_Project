package order.payment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DBManager;
import dto.Orders;
import goods.GoodsMain;

public class PaymentPanel extends JPanel implements ActionListener{
	Payment payment;
	JButton bt_back, bt_payment_complete;
	JPanel p_south, p_center;
	String orders_payment_type;
	DBManager manager;
	Connection con;
	
	
	public PaymentPanel(Payment payment, String type) {
		this.payment=payment;
		this.orders_payment_type=type;
		
		setLayout(new BorderLayout());
		
		p_south=new JPanel();
		p_center = new JPanel();
		bt_back = new JButton("�ڷ�");
		bt_payment_complete = new JButton("�����Ϸ�");
		
		p_south.add(bt_back);
		p_south.add(bt_payment_complete);
		
		add(p_south, BorderLayout.SOUTH);
		add(p_center);
		
		bt_back.addActionListener(this);
		bt_payment_complete.addActionListener(this);
		
		//�����۾���������!
		init();
		
	}
	
	public void init(){
		//�������
		manager = DBManager.getInstance();
		con=manager.getConnection();
		
	}
	
	//dto�� �ֹ� �����ڿ��� ������
	//con�� �����ؼ� �ֹ����̺� type�� ä���� ������ �Ѵ�.
	public void send(){
		StringBuffer sb = new StringBuffer();
		sb.append("insert into orders (product_id, orders_date, orders_emp_id, orders_client_id, orders_status, orders_payment_type, orders_type)");
		sb.append(" values(?,?,?,?,?,?,?)");
		
		PreparedStatement pstmt = null;
		try {
			pstmt=con.prepareStatement(sb.toString());
			//pstmt.setInt(1, payment.dto.getOrders_id());
			pstmt.setInt(1, payment.dto.getProduct_id());
			pstmt.setTimestamp(2, payment.dto.getOrders_date());
			pstmt.setInt(3, payment.dto.getOrders_emp_id());
			pstmt.setInt(4, payment.dto.getOrders_client_id());
			pstmt.setString(5, payment.dto.getOrders_status());
			pstmt.setString(6, orders_payment_type);
			pstmt.setString(7, "offline");
			
			int result = pstmt.executeUpdate();
			
			JOptionPane.showMessageDialog(this, result+"�� ���� �Ϸ�");
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "��������");
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			manager.disConnection(con);
			
			//�̰� ���߿� ������ �� �ϴ� ���ó������ �Ǵ°ɷ� ó���ع���~~!
			Orders dto = new Orders();
			new GoodsMain(dto);
			
			//����������� ���⼭ �ٽ� �Ѿߵ�~!
			//System.exit(0);
		}
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		 if(obj==bt_back){
			 PaymentPanel.this.setVisible(false);
			 payment.p_menu.setVisible(true);
		}else if(obj==bt_payment_complete){
			send();
		}
	}

}
