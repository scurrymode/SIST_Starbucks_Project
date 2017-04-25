package order.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.DBManager;
import dto.Orders;

public class CallMain extends JFrame{
	DBManager manager;
	Connection con;
	Vector<String> callList = new Vector<String>(6);
	JPanel container;
	JPanel[] panel = new JPanel[6];
	JLabel[] label = new JLabel[6];
	
	public CallMain() {
		container = new JPanel();
		
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			label[i] = new JLabel("");
			label[i].setFont(new Font("����", Font.PLAIN, 20));
			panel[i].add(label[i]);
			panel[i].setBackground(Color.pink);
			panel[i].setPreferredSize(new Dimension(100, 100));
			panel[i].setBounds((i+10)* 100, 0, 100, 100);
			container.add(panel[i]);
		}

		label[0].setFont(new Font("����", Font.BOLD, 30));
		
		add(new JLabel("ȣ���"), BorderLayout.NORTH);
		add(container);
		setSize(400, 300);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void callMember(Vector<Orders> orderList) {
		/*
		 * �ٵ�..?? ���� Ŭ���̾�Ʈ�� �������� �ֹ��ϸ� 
		 * orders_type�� �¶����̸� �г��� ȣ�� �����ؼ�
		 * ���������̸� client_id�� ȣ��
		*/
		String sql = null;
		
		Vector<String> backupList = new Vector<String>();
		/*
		 * �׻� ù��° �гο� �� ȣ���ϱ�
		 * ����Ʈ�� ����ִ� ������ ȣ���� ��ĭ�� ������ �ѱ�� --> �����
		*/
		
		if(orderList.get(0).getOrders_type().equals("offline")) {
			//���� ����ϵ�
			//label[0].setText(orderList.get(0).getOrders_client_id() + "�� ���� ���Խ��ϴ�.");
			//������ �迭�� ������ �����
			
			backupList.add(Integer.toString(orderList.get(0).getOrders_client_id()));
			
		} else if(orderList.get(0).getOrders_type().equals("online")) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			sql = "select m.member_login_id from member m, orders o where m.member_id = o.orders_client_id and o.orders_client_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, orderList.get(0).getOrders_client_id());
				
				rs = pstmt.executeQuery();
				
				String member_id = null;
				System.out.println(orderList.get(0).getOrders_client_id());
				while(rs.next()) {
					//��� �α��� ���̵� �޾ƿͼ� ù��° ���̵� �޾Ƽ� backuplist�� �޾Ƶα�
					member_id = rs.getString("m.member_login_id");
				}
				
				backupList.add(member_id);
				System.out.println(member_id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		for (int i = 0; i < callList.size(); i++) {
			backupList.add(callList.get(i));
		}
		
		callList.removeAll(callList);

		callList.addAll(backupList);
		
		System.out.println(callList.size());
		
		for (int i = 0; i < label.length; i++) {
			if(i >= backupList.size()) {
				label[i].setText("");
			}
			else {
				label[i].setText(backupList.get(i));
			}
		}
		
		backupList.removeAll(backupList);
	}
	/*
	public static void main(String[] args) {
		new CallMain();
	}*/

}
