//�α��� ȭ���� ����� Ŭ���� ����
package pos.login;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import client.ClientMain;
import db.DBManager;
import order.main.OrderMain;
import pos.AdminPage;

public class LoginForm extends JPanel implements ActionListener ,FocusListener{
	PosWindow posWindow;
	JPanel container;
	JPanel p_north;
	JPanel p_center;
	JPanel p_south; // ���ʿ� ��ư�� �� ����
	JLabel la_north, la_center1, la_center2;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_login;
	Canvas can_center;
	BufferedImage image = null;

	public LoginForm(PosWindow posWindow) {
		this.posWindow = posWindow;
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

		la_center2 = new JLabel("<html>�������̵�� �α��� ���ּ���<html>",
				JLabel.CENTER);
		la_center2.setFont(new Font("����", Font.PLAIN, 13));
		
		// �̹��� url ������
		try {
			URL url = new URL("http://localhost:9090/data/main_login_cup01_img.png");
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
		t_id.setPreferredSize(new Dimension(380, 35));
		t_pw = new JPasswordField("PW�Է�", 20);
		t_pw.setEchoChar((char) 0);
		t_pw.setPreferredSize(new Dimension(380, 35));

		// south
		p_south = new JPanel();

	
		t_id = new JTextField("jangs", 15);
		t_pw = new JPasswordField("1234", 15);
		bt_login = new JButton("�α���");
		p_south.setBackground(Color.WHITE);


		bt_login = new JButton("�α����ϱ�");
		bt_login.setForeground(Color.WHITE);
		bt_login.setBackground(new Color(123, 109, 100));
		
	

		// add
		p_north.add(la_north);

		p_center.add(can_center);
		p_center.add(la_center1);
		p_center.add(la_center2);
		p_center.add(t_id);
		p_center.add(t_pw);

		p_south.add(bt_login);
	

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
	

		// ��ư ����
		bt_login.addActionListener(this);
	

		setPreferredSize(new Dimension(400, 650));

		setPreferredSize(new Dimension(400, 100));
		//setBackground(Color.PINK);

		// �̺�Ʈ ����

		t_id.addFocusListener(this);
		t_pw.addFocusListener(this);
		t_pw.setEchoChar((char) 0);

	}

	public void joinCheck() {
		posWindow.setPage(1);
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
			JOptionPane.showMessageDialog(this, "��й�ȣ�� ��Ȯ�� �Է����ּ���");
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
					JOptionPane.showMessageDialog(this, "�α��� �Ϸ�! " + id + "�� ȯ���մϴ�.");
					posWindow.id = id;
					// Ŭ���̾�Ʈ ���� Ű��
					if(!emp_Login){
						posWindow.page[1].add(new AdminPage(posWindow));
						posWindow.setPage(1);
					}else if(emp_Login){
						posWindow.page[2].add(new OrderMain(posWindow));
						posWindow.setPage(2);
					}
				} else {
				JOptionPane.showMessageDialog(this, "�α��� ������ �ùٸ��� �ʽ��ϴ�.");
				}
			}else{
				JOptionPane.showMessageDialog(this, "�α��������� Ȯ�����ּ���");
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
		} 
	}
	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		if (obj == t_id) {
			if (t_id.getText().equals("ID�Է�")) {
				t_id.setText("");
				t_id.setForeground(Color.BLACK);
			}
		} else if (obj == t_pw) {
			t_pw.setEchoChar('*');
			if (t_pw.getText().equals("PW�Է�")) {
				t_pw.setText("");
				t_pw.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if (obj == t_id) {
			if (t_id.getText().isEmpty()) {
				t_id.setForeground(Color.GRAY);
				t_id.setText("ID�Է�");
			}
		} else if (obj == t_pw) {
			if (t_pw.getText().isEmpty()) {
				t_pw.setForeground(Color.GRAY);
				t_pw.setEchoChar((char) 0);
				t_pw.setText("PW�Է�");
			}
		}
	}
	
	
}
