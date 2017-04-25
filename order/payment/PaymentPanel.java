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
	//con�� �����ؼ� �ֹ����̺��� type�� ä���� ������ �Ѵ�.
	public void send(){
		
		
		PreparedStatement pstmt = null;
		try {
			for(int i=0; i<payment.orders_list.size();i++){
			StringBuffer sb = new StringBuffer();
			sb.append("insert into orders (product_id, orders_date, orders_emp_id, orders_client_id, orders_status, orders_payment_type, orders_type)");
			sb.append(" values(?,current_timestamp(),?,?,?,?,?)");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, payment.orders_list.get(i).getProduct_id());
			//pstmt.setTimestamp(2, "current_timestamp()");
			pstmt.setInt(2, payment.orders_list.get(i).getOrders_emp_id());
			pstmt.setInt(3, payment.orders_list.get(i).getOrders_client_id());
			pstmt.setString(4, "ready");
			pstmt.setString(5, orders_payment_type);
			pstmt.setString(6, "offline");
			
			int result = pstmt.executeUpdate();
			}
				JOptionPane.showMessageDialog(this, "1�� ���� �Ϸ�");
			
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
			//manager.disConnection(con);
			
			//�̰� ���߿� ������ �� �ϴ� ���ó������ �Ǵ°ɷ� ó���ع���~~!
			//Orders dto = new Orders();
			//new GoodsMain(dto);
			
			//����������� ���⼭ �ٽ� �Ѿߵ�~!
			System.exit(0);
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