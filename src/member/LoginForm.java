//�α��� ȭ���� ����� Ŭ���� ����
package member;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import client.ClientMain;
import db.DBManager;

public class LoginForm extends JPanel implements ActionListener {
	MemberWindow memberWindow;
	JPanel container;
	JPanel p_north;
	JPanel p_center;
	JPanel p_south; // ���ʿ� ��ư�� �� ����
	JLabel la_north, la_center1, la_center2;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_login, bt_join;
	Canvas can_center;
	BufferedImage image = null;

	public LoginForm(MemberWindow memberWindow) {
		this.memberWindow = memberWindow;
		container = new JPanel();

		// north
		p_north = new JPanel();
		p_north.setBackground(Color.BLACK);
		la_north = new JLabel("�α���");
		la_north.setForeground(Color.WHITE);

		// center
		p_center = new JPanel();
		p_center.setBackground(Color.WHITE);
		la_center1 = new JLabel("�ȳ��ϼ���. ��Ÿ�����Դϴ�.", JLabel.CENTER);
		la_center1.setForeground(new Color(156, 136, 86));
		la_center1.setFont(new Font("����", Font.BOLD, 20));

		la_center2 = new JLabel("<html>���� ��Ÿ���� ������ ȸ���� �ǽø� ���� ���� ���� <br> ������ ���� �پ��� �����带 ������ �� �ֽ��ϴ�.<html>",
				JLabel.CENTER);
		la_center2.setFont(new Font("����", Font.PLAIN, 13));

		// �̹��� url ������
		try {
			URL url = this.getClass().getResource("/main_login_cup01_img.png");
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		can_center = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage((Image) image, 178, 0, 42, 90, this);

			}
		};

		// can_center.setPreferredSize(new Dimension(135, 135));

		t_id = new JTextField("ID�Է�", 20);
		t_id.addMouseListener(new MyMouseListener());
		t_id.setPreferredSize(new Dimension(380, 35));
		t_pw = new JPasswordField("PW�Է�", 20);
		t_pw.setEchoChar((char)0);
		t_pw.addMouseListener(new MyMouseListener());
		t_pw.setPreferredSize(new Dimension(380, 35));

		// south
		p_south = new JPanel();
		p_south.setBackground(Color.WHITE);

		bt_login = new JButton("�α����ϱ�");
		bt_login.setForeground(Color.WHITE);
		bt_login.setBackground(new Color(123, 109, 100));
		bt_join = new JButton("ȸ������");
		bt_join.setForeground(new Color(123, 109, 100));
		bt_join.setBackground(Color.WHITE);
		bt_join.setBorderPainted(false);

		// add
		p_north.add(la_north);

		p_center.add(can_center);
		p_center.add(la_center1);
		p_center.add(la_center2);
		p_center.add(t_id);
		p_center.add(t_pw);

		p_south.add(bt_login);
		p_south.add(bt_join);

		add(container);

		// �� component ��ġ����
		container.setLayout(new BorderLayout());
		container.add(p_north, BorderLayout.NORTH);
		container.add(p_center, BorderLayout.CENTER);
		container.add(p_south, BorderLayout.SOUTH);

		p_north.setPreferredSize(new Dimension(400, 55));
		p_center.setPreferredSize(new Dimension(400, 465));
		p_south.setPreferredSize(new Dimension(400, 130));

		p_north.setLayout(null);
		la_north.setBounds(10, 0, 100, 55);

		p_center.setLayout(null);
		can_center.setBounds(0, 55, 400, 150);
		la_center1.setBounds(0, 200, 400, 30);
		la_center2.setBounds(0, 240, 400, 50);
		t_id.setBounds(10, 340, 380, 40);
		t_pw.setBounds(10, 390, 380, 40);

		p_south.setLayout(null);
		bt_login.setBounds(10, 0, 380, 40);
		bt_join.setBounds(10, 55, 380, 20);

		// ��ư ����
		bt_login.addActionListener(this);
		bt_join.addActionListener(this);

		setPreferredSize(new Dimension(400, 650));
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
		if (t_id.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���");
			t_id.requestFocus();
			return;
		}

		char[] ch = t_pw.getPassword();
		String pass = new String(ch);
		if (pass.length() == 0) {

			JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է����ּ���");

			t_pw.requestFocus();
			return;
		}

		DBManager manager = DBManager.getInstance();
		Connection con = manager.getConnection();
		PreparedStatement pstmt;
		ResultSet rs;
		boolean flag_Login = false;

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
				if (list.get(i).equalsIgnoreCase(id)) {
					flag_Login = true;
				}
			}
			if (flag_Login) {
				String sql2 = "select member_login_pw from member where member_login_id='" + id + "'";
				try {
					pstmt = con.prepareStatement(sql2);
					rs = pstmt.executeQuery();

					char[] pw_array = t_pw.getPassword();
					String pw = new String(pw_array);

					rs.next();
					String db_pw = rs.getString("member_login_pw");

					// System.out.println("����� " + db_pw);

					if (db_pw.equals(pw)) {
						JOptionPane.showMessageDialog(this, "�α��� �Ϸ�! " + id + "�� ȯ���մϴ�.");
						memberWindow.id = id;
						// Ŭ���̾�Ʈ ���� Ű��
						memberWindow.page[2].add(new ClientMain(memberWindow));
						memberWindow.setPage(2);
					} else {
						JOptionPane.showMessageDialog(this, "�α��� ������ ��ġ���� �ʽ��ϴ�. ���̵� ��й�ȣ�� Ȯ�� �� �ٽ� �Է��� �ּ���");

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				System.out.println("���ⱺ ���̵���?");
				JOptionPane.showMessageDialog(this, "�α��� ������ �ùٸ��� �ʽ��ϴ�.");
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

	class MyMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == t_id) {
				t_id.setText("");
			} else if (e.getSource() == t_pw) {
				t_pw.setEchoChar('��');
				t_pw.setText("");
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_login) {
			loginCheck();
		} else if (obj == bt_join) {
			joinCheck();
		}
	}

}
