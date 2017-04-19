package member;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import db.DBManager;

public class JoinForm extends JPanel implements ActionListener {

	MemberWindow memberWindow;
	JLabel label[] = new JLabel[11]; // Label �迭�� ����
	JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
	JLabel log, id_la, pw_la, nickname_la, name_la, gender_la, phone_la, phone_la1, phone_la2, birth_la, birth_la1;
	JTextField id_t, nickname_t, name_t, phone_t2, phone_t3, birth_t;
	JPasswordField pw_t;
	JButton id_bt, bt_trans, bt_cancel;
	ButtonGroup gender;
	JRadioButton female, male;
	Choice phone_t1;

	Member member;

	DBManager manager = DBManager.getInstance();
	Connection con;
	PreparedStatement pstmt; // ��ü�� �ϳ�
	ResultSet rs;

	public JoinForm(MemberWindow memberWindow) {
		this.memberWindow = memberWindow;
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();

		// title
		log = new JLabel("**********ȸ������**********");
		p1.add(log);
		add(p1);

		// ID
		id_la = new JLabel("ID : ");
		p2.add(id_la);
		id_t = new JTextField("", 15);
		p2.add(id_t);
		id_bt = new JButton("���̵� �ߺ� Ȯ��");
		p2.add(id_bt);
		add(p2);

		// Password
		pw_la = new JLabel("Password : ");
		p3.add(pw_la);
		pw_t = new JPasswordField("", 15);
		p3.add(pw_t);
		add(p3);

		// �̸�
		name_la = new JLabel("�̸� : ");
		p4.add(name_la);
		name_t = new JTextField("", 15);
		p4.add(name_t);
		add(p4);

		// �г���
		nickname_la = new JLabel("�г��� : ");
		p5.add(nickname_la);
		nickname_t = new JTextField("", 15);
		p5.add(nickname_t);
		add(p5);

		// ����
		gender_la = new JLabel("���� : ");
		p6.add(gender_la);
		gender = new ButtonGroup();
		female = new JRadioButton("����", true);
		male = new JRadioButton("����", true);
		gender.add(female);
		gender.add(male);
		p6.add(female);
		p6.add(male);
		add(p6);

		// ����ó
		phone_la = new JLabel("�ڵ��� ��ȣ : ");
		p7.add(phone_la);
		phone_t1 = new Choice();

		phone_t1.add("010");
		phone_t1.add("011");
		phone_t1.add("016");
		phone_t1.add("017");
		phone_t1.add("018");
		phone_t1.add("019");
		p7.add(phone_t1);
		phone_la1 = new JLabel(" - ");
		p7.add(phone_la1);
		phone_t2 = new JTextField("", 5);
		p7.add(phone_t2);
		phone_la2 = new JLabel(" - ");
		p7.add(phone_la2);
		phone_t3 = new JTextField("", 5);
		p7.add(phone_t3);
		add(p7);

		// �������
		birth_la = new JLabel("�������");
		p8.add(birth_la);
		birth_t = new JTextField("", 15);
		p8.add(birth_t);
		birth_la1 = new JLabel("ex) 940621 ");
		add(p8);

		// ����, ��ҹ�ư
		bt_trans = new JButton("����");
		bt_cancel = new JButton("���");
		p9.add(bt_trans);
		p9.add(bt_cancel);
		add(p9);

		bt_trans.addActionListener(this);
		bt_cancel.addActionListener(this);
		id_bt.addActionListener(this);

		setLayout(new GridLayout(15, 1));
		setVisible(false); // ���ʿ� �������

		init();

		con = manager.getConnection();
	}

	public void trans() {
		member = new Member();
		member.setMember_login_id(id_t.getText());
		member.setMember_login_pw(pw_t.getText());
		member.setMember_name(name_t.getText());
		member.setMember_nickname(nickname_t.getText());

		if (female.isSelected()) {
			member.setMember_gender(female.getText());
		} else if (male.isSelected()) {
			member.setMember_gender(male.getText());

		}
		member.setMember_phone(phone_t1.getSelectedItem() + "-" + phone_t2.getText() + "-" + phone_t3.getText());
		member.setMember_birth(birth_t.getText());

		String sql = "insert into member (member_login_id, member_login_pw, member_name, member_nickname, member_gender , member_phone, member_birth, member_coupon) values(?,?,?,?,?,?,?,?)";

		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMember_login_id());
			pstmt.setString(2, member.getMember_login_pw());
			pstmt.setString(3, member.getMember_name());
			pstmt.setString(4, member.getMember_nickname());
			pstmt.setString(5, member.getMember_gender());
			pstmt.setString(6, member.getMember_phone());
			pstmt.setString(7, member.getMember_birth());
			pstmt.setString(8, member.getMember_coupon());

			int result = pstmt.executeUpdate();

			if (result != 0) {
				System.out.println("����");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void cancel() {
		id_t.setText("");
		pw_t.setText("");
		name_t.setText("");
		nickname_t.setText("");
		phone_t1.select(0);
		phone_t2.setText("");
		phone_t3.setText("");
		birth_t.setText("");

	}

	public void id_over_ck() {
		boolean flag = true;
		boolean flag2 = true;
		ArrayList<String> list = new ArrayList<String>();
		String sql = "select member_login_id from member";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
			int cnt = 0;
			while (rs.next()) {
				list.add(rs.getString(1));
				cnt++;
				System.out.println(cnt);
				System.out.println(list.get(cnt - 1));
			}

			String id = id_t.getText();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
				if (list.get(i).equals(id)) {
					flag = false;

				} else if (id.equals("")) {
					flag2 = false;
				}
			}
			if (flag == false && flag2 == true) {
				JOptionPane.showMessageDialog(this, "�ߺ��� ���̵� ���� �մϴ�.");
			} else if (flag == true && flag2 == true) {
				JOptionPane.showMessageDialog(this, "��� ������ ���̵��Դϴ�.");
			} else if (flag2 == false) {
				JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���.");
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean join_valCheck() {
		boolean re = true;
		if (id_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "���ʼ��� ���̵� �Է����ּ���.");
			re = false;
		} else if (pw_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "���ʼ��� ��й�ȣ�� �Է����ּ���.");
			re = false;
		} else if (name_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "���ʼ��� �̸��� �Է����ּ���.");
			re = false;
		} else if (nickname_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "���ʼ��� �г����� �Է����ּ���.");
			re = false;
		} else if (phone_t2.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "���ʼ��� ����ó�� �Է����ּ���.");
			re = false;
		} else if (phone_t3.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "���ʼ��� ����ó�� �Է����ּ���.");
			re = false;
		} else if (birth_t.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "���ʼ��� ��������� �Է����ּ���.");
			re = false;
		}

		return re;
	}

	// DB���� Connection ���� ����
	private void init() {
		con = manager.getConnection();

	}

	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == bt_trans) {
			if (join_valCheck())
				trans();
		} else if (obj == bt_cancel) {
			cancel();
		} else if (obj == id_bt) {
			id_over_ck();
		}
	}

}
