//로그인 화면을 담당할 클래스 정의
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

import db.DBManager;

public class LoginForm extends JPanel implements ActionListener {
	MemberWindow memberWindow;
	JPanel container; // borderLayout 적용
	JPanel p_center; // grid 를 적용할
	JPanel p_south; // 남쪽에 버튼이 들어갈 예정
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
		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");

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

		// 버튼 연결
		bt_login.addActionListener(this);
		bt_join.addActionListener(this);

		setPreferredSize(new Dimension(400, 100));
		setBackground(Color.PINK);
	}

	public void joinCheck() {
		memberWindow.setPage(1);
	}

	// 1.아이디가 존재하는 아이디인지 확인하기
	// db에 모든 아이디 값을 가져와서 지금 입력된 아이디와 비교

	// 2.존재하면 해당 아이디와 비번텍스트필드에 있는거랑 db의 값이 같은지 확인

	// 존재하면 해당 아이디값에 비밀번호를 확인해서 아이디와 비번이 둘다 일치하는지 확인하고 넘기기
	// 3.맞으면 넘기고 아니면 틀렸다고 오류메세지

	public void loginCheck() {
		DBManager manager = DBManager.getInstance();
		Connection con = manager.getConnection();
		PreparedStatement pstmt;
		ResultSet rs;

		ArrayList<String> list = new ArrayList<String>();

		String sql1 = "select member_login_id from member";

		try {
			pstmt = con.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("member_login_id"));
			}

			String id = t_id.getText();

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(id)) {
					String sql2 = "select member_login_pw from member where member_login_id='" + id + "'";

					try {
						pstmt = con.prepareStatement(sql2);
						rs = pstmt.executeQuery();

						char[] pw_array = t_pw.getPassword();
						String pw = new String(pw_array);

						rs.next();
						String db_pw = rs.getString("member_login_pw");

						if (db_pw.equals(pw)) {
							JOptionPane.showMessageDialog(this, "로그인 완료");
							memberWindow.setPage(2);
							break;

						} else {
							JOptionPane.showMessageDialog(this, "로그인 실패");
							break;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(this, "로그인 실패");
					break;
				}
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
		} else if (obj == bt_join) {
			joinCheck();
		}
	}

}
