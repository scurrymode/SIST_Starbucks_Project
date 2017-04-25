package order.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import db.DBManager;
import dto.Orders;
import goods.GoodsMain;

public class IndividualOrder extends JPanel implements ActionListener{
	OrdersListMain main;
	Orders order;//�����ڿ��� �Ѱܹ��� dto
	JLabel la_order_west, la_order_center;
	JButton bt_finish;
	String status = "�Ϸ�";
	
	public IndividualOrder(OrdersListMain main, Orders order) {//�Ű������� �ֹ� dto�ޱ�
		setLayout(new BorderLayout());
		this.main = main;
		this.order = order;
		
		la_order_west = new JLabel(order.getProduct_name(), JLabel.CENTER);
		la_order_center = new JLabel(Integer.toString(order.getOrders_client_id()) +", " + order.getOrders_id(), JLabel.CENTER);
		bt_finish = new JButton("�Ϸ�");
		
		la_order_west.setFont(new Font("����", Font.BOLD, 15));
		la_order_west.setPreferredSize(new Dimension(300, 30));
		la_order_west.setOpaque(true);
		la_order_west.setBackground(Color.yellow);
		
		la_order_center.setFont(new Font("����", Font.BOLD, 15));
		la_order_center.setPreferredSize(new Dimension(100, 30));
		
		bt_finish.setPreferredSize(new Dimension(100, 30));
		
		add(la_order_west, BorderLayout.WEST);
		add(la_order_center);
		add(bt_finish, BorderLayout.EAST);
		
		bt_finish.addActionListener(this);
		
		setVisible(true);
		setPreferredSize(new Dimension(500, 30));
		setBackground(Color.pink);

	}
	
	public void ordersFinish() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String orders_sql = "update orders set orders_status = ? where orders_id=?";
		
		try {
			pstmt = main.con.prepareStatement(orders_sql);
			
			pstmt.setString(1, status);
			pstmt.setInt(2, order.getOrders_id());
			
			int result = pstmt.executeUpdate();
			
			if (result == 1) {
				System.out.println("������Ʈ �Ϸ�");
				
				insertSales();
				//goodsmainȣ�� -->new?
				GoodsMain gm = new GoodsMain(order);
				gm.updateGoods();
				
				//orderlistmain�� getFinishȣ���ϱ�
				main.getFinish(order.getOrders_client_id());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void insertSales() {
		//���� ���̺� insert�ϱ�
		PreparedStatement pstmt = null;
		String sales_sql = "insert into sales (orders_id, product_id, sales_date, sales_emp_id, sales_client_id, sales_status, sales_payment_type, sales_type) values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pstmt = main.con.prepareStatement(sales_sql);
			
			pstmt.setInt(1, order.getOrders_id());
			pstmt.setInt(2, order.getProduct_id());
			pstmt.setTimestamp(3, order.getOrders_date());
			pstmt.setInt(4, order.getOrders_emp_id());
			pstmt.setInt(5, order.getOrders_client_id());
			pstmt.setString(6, status);
			pstmt.setString(7, order.getOrders_payment_type());
			pstmt.setString(8, order.getOrders_type());
			
			int result = pstmt.executeUpdate();
			
			if (result == 1) {
				System.out.println("insert ����");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		ordersFinish();
	}
}
