//�α��� ȭ���� ����� Ŭ���� ����
package member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ClientMain;
import db.DBManager;
import pos.AdminPage;

public class LoginForm extends JPanel implements ActionListener {
	MemberWindow memberWindow;
	JPanel container; // borderLayout ����
	JPanel p_center; // grid �� ������
	JPanel p_south; // ���ʿ� ��ư�� �� ����
	JLabel la_id, la_pw;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_login, bt_join;

	public LoginForm(MemberWindow memberWindow) {
		this.memberWindow = memberWindow;
		container = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		la_id = new JLabel("ID");
		la_pw = new JLabel("Password");
		t_id = new JTextField("", 15);
		t_pw = new JPasswordField("", 15);
		bt_login = new JButton("�α���");
		bt_join = new JButton("ȸ������");

		container.setLayout(new BorderLayout());

		p_center.setLayout(new GridLayout(2, 2));
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pw);
		p_center.add(t_pw);
		p_south.add(bt_login);
		p_south.add(bt_join);

		container.add(p_center);
		container.add(p_south, BorderLayout.SOUTH);

		add(container);

		// ��ư ����
		bt_login.addActionListener(this);
		bt_join.addActionListener(this);

		setPreferredSize(new Dimension(400, 100));
		setBackground(Color.PINK);
	}

	public void joinCheck() {
		memberWindow.setPage(1);
	}

	// 1.���̵� �����ϴ� ���̵����� Ȯ���ϱ�
	// db�� ��� ���̵� ���� �����ͼ� ���� �Էµ� ���̵�� ��

	// 2.�����ϸ� �ش� ���̵�� ����ؽ�Ʈ�ʵ忡 �ִ°Ŷ� db�� ���� ������ Ȯ��

	// �����ϸ� �ش� ���̵𰪿� ��й�ȣ�� Ȯ���ؼ� ���̵�� ����� �Ѵ� ��ġ�ϴ��� Ȯ���ϰ� �ѱ��
	// 3.������ �ѱ�� �ƴϸ� Ʋ�ȴٰ� �����޼���

	public void loginCheck() {
		DBManager manager = DBManager.getInstance();
		Connection con = manager.getConnection();
		PreparedStatement pstmt;
		ResultSet rs;
		boolean flag_Login = false;
		boolean admin_Login = false;

		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> adminList = new ArrayList<String>();
		String sql1 = "select member_login_id from member";

		try {
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getString("member_login_id"));
			}
			
			String id = t_id.getText();
			char[] pw_array2 = t_pw.getPassword();
			String pw2 = new String(pw_array2);
			for (int i=0; i <list.size(); i++) {
				if (list.get(i).equalsIgnoreCase(id)){
					flag_Login =true;
				}
			}
			if(id.equalsIgnoreCase("admin")){
				String sql = "select emp_login_id from emp where emp_job='manager'";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()){
					adminList.add(rs.getString("emp_login_id"));
					for(int i=0;i<adminList.size();i++){
						if(adminList.get(i).equalsIgnoreCase(id)){
							admin_Login=true;
						}
					}
				}
			}
			if(id.equals("")){
				JOptionPane.showMessageDialog(this, "ID�� �Է����ּ���");
			}
			else if(pw2.equals("")){
				JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է����ּ���");
			}else if(admin_Login){
					String sql = "select emp_login_pw from emp where emp_login_id='"+id+"'";
					pstmt = con.prepareStatement(sql);
					rs=pstmt.executeQuery();
					char[] pw_array = t_pw.getPassword();
					String pw = new String(pw_array);
					rs.next();
					String db_pw = rs.getString("emp_login_pw");
					System.out.println("������ ����� "+db_pw);
					 	if (db_pw.equals(pw)) {
						JOptionPane.showMessageDialog(this, "�α��� �Ϸ�! �����ڴ� ȯ���մϴ�.");
						//Ŭ���̾�Ʈ ���� Ű��
						memberWindow.page[3].add(new AdminPage(memberWindow));							
						memberWindow.setPage(3);
					}else{
						JOptionPane.showMessageDialog(this, "�α��� ����");
					}
			}else if(flag_Login){
					String sql2 = "select member_login_pw from member where member_login_id='" + id + "'";
					try {
						pstmt = con.prepareStatement(sql2);
						rs = pstmt.executeQuery();
						char[] pw_array = t_pw.getPassword();
						String pw = new String(pw_array);
						rs.next();
						String db_pw = rs.getString("member_login_pw");
						
						System.out.println("����� "+db_pw);
							if (db_pw.equals(pw)) {
							JOptionPane.showMessageDialog(this, "�α��� �Ϸ�! "+id+"�� ȯ���մϴ�.");
							memberWindow.id=id;
							//Ŭ���̾�Ʈ ���� Ű��
							memberWindow.page[2].add(new ClientMain(memberWindow));							
							memberWindow.setPage(2);
						}else{
							JOptionPane.showMessageDialog(this, "�α��� ����");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(this, "�α��� ����");
				}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// String id = t_id.getText();
	// String pw = new String(t_pw.getPassword());// char[]ĳ���� �迭�ι�ȯ
	// if (id.equals("batman") && pw.equals("1234")) {
	// JOptionPane.showMessageDialog(this, "�α��� �Ϸ�");
	//
	//
	// } else {
	// JOptionPane.showMessageDialog(this, "�α��� ������ �ùٸ��� �ʽ��ϴ�");
	// }

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_login) {
			loginCheck();
		} else if (obj == bt_join) {
			joinCheck();
		}
	}

}
