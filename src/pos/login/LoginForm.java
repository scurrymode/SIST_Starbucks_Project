//로그인 화면을 담당할 클래스 정의
package pos.login;

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
import order.main.OrderMain;
import pos.AdminPage;

public class LoginForm extends JPanel implements ActionListener {
	PosWindow posWindow;
	JPanel container; // borderLayout 적용
	JPanel p_center; // grid 를 적용할
	JPanel p_south; // 남쪽에 버튼이 들어갈 예정
	JLabel la_id, la_pw;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_login;

	public LoginForm(PosWindow posWindow) {
		this.posWindow = posWindow;
		container = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		la_id = new JLabel("ID");
		la_pw = new JLabel("Password");
		t_id = new JTextField("", 15);
		t_pw = new JPasswordField("", 15);
		bt_login = new JButton("로그인");

		container.setLayout(new BorderLayout());

		p_center.setLayout(new GridLayout(2, 2));
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pw);
		p_center.add(t_pw);
		p_south.add(bt_login);
		
		
		container.add(p_center);
		container.add(p_south, BorderLayout.SOUTH);

		add(container);

		// 버튼 연결
		bt_login.addActionListener(this);
		

		setPreferredSize(new Dimension(400, 100));
		setBackground(Color.PINK);
	}

	public void joinCheck() {
		posWindow.setPage(1);
	}

	// 1.아이디가 존재하는 아이디인지 확인하기
	// db에 모든 아이디 값을 가져와서 지금 입력된 아이디와 비교

	// 2.존재하면 해당 아이디와 비번텍스트필드에 있는거랑 db의 값이 같은지 확인

	// 존재하면 해당 아이디값에 비밀번호를 확인해서 아이디와 비번이 둘다 일치하는지 확인하고 넘기기
	// 3.맞으면 넘기고 아니면 틀렸다고 오류메세지

	public void loginCheck() {
		if (t_id.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요");
			t_id.requestFocus();
			return;
		}

		char[] ch = t_pw.getPassword();
		String pass = new String(ch);
		if (pass.length() == 0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 정확히 입력해주세요");
			t_pw.requestFocus();
			return;
		}

		DBManager manager = DBManager.getInstance();
		Connection con = manager.getConnection();
		PreparedStatement pstmt;
		ResultSet rs;
		boolean emp_Login = false;
		boolean id_error = false;
		ArrayList<String> emp = new ArrayList<String>();
		ArrayList<String> list = new ArrayList<String>();
		String sql1 = "select emp_login_id from emp where emp_job not in ('manager')";
		
		try {
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emp.add(rs.getString("emp_login_id"));
			}
			sql1 = "select emp_login_id from emp";
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("emp_login_id"));
			}
			System.out.println(list.size());
			String id = t_id.getText();

			for (int i = 0; i < emp.size(); i++) {
				if (emp.get(i).equalsIgnoreCase(id)) {
					emp_Login = true;
				}
			}
			System.out.println();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equalsIgnoreCase(id)) {
					id_error = true;
				}
			}
			if(id_error){
				String sql2 = "select emp_login_pw from emp where emp_login_id='" + id + "'";
				
				pstmt = con.prepareStatement(sql2);
				rs = pstmt.executeQuery();
					
				char[] pw_array = t_pw.getPassword();
				String pw = new String(pw_array);
				rs.next();
				String db_pw = rs.getString("emp_login_pw");
				if (db_pw.equals(pw)) {
					JOptionPane.showMessageDialog(this, "로그인 완료! " + id + "님 환영합니다.");
					posWindow.id = id;
					// 클라이언트 메인 키기
					if(!emp_Login){
						posWindow.page[1].add(new AdminPage(posWindow));
						posWindow.setPage(1);
					}else if(emp_Login){
						posWindow.page[2].add(new OrderMain(posWindow));
						posWindow.setPage(2);
					}
				} else {
				JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다.");
				}
			}else{
				JOptionPane.showMessageDialog(this, "로그인정보를 확인해주세요");
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// String id = t_id.getText();
	// String pw = new String(t_pw.getPassword());// char[]캐릭터 배열로반환
	// if (id.equals("batman") && pw.equals("1234")) {
	// JOptionPane.showMessageDialog(this, "로그인 완료");
	//
	//
	// } else {
	// JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다");
	// }

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_login) {
			loginCheck();
		} 
	}

}
