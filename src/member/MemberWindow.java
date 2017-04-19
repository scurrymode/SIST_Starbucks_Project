package member;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

//�� ������� ũ�Ⱑ �����Ǿ����� �ʾƾ� �Ѵ�.
//(�α��� ���� ������ ������ �״��Ŀ���� ����) 
//��? ������ �ȿ� ������ �� �г��� �� ũ�⸦ �����ϰԵǹǷ� 
//�α��α���϶� �۰�, ���� ��ȭ�鿡���� ũ��!

public class MemberWindow extends JFrame {
	LoginForm loginForm;
	JoinForm joinForm;
	MemberPanel memberPanel;
	
	JPanel[] page = new JPanel[3];

	public MemberWindow() {
		setLayout(new FlowLayout());

		page[0] = new LoginForm(this);
		page[1] = new JoinForm(this);
		page[2] = new MemberPanel(this);
		

		add(page[0]);
		add(page[1]);
		add(page[2]);

		setPage(0);

		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// ������ �ȿ� � �г��� ������ �������ִ� �޼��带 �����غ���
	public void setPage(int index) { // �������� �ٸ��� �Ѱܹ����������� �Ű����� ���밡�ɼ����־���þ�
										// //�Ѱ��� index �� �ް� �������� �ƴϾ�
		for (int i = 0; i < page.length; i++) {
			if (i == index) {
				page[i].setVisible(true);

			} else {
				page[i].setVisible(false);
			}
		}
		pack();// ���빰�� ũ�⸸ŭ ������ũ�⸦ ���� (Loginform�� ����� ���⼭ ���������ʾҴµ� �������Բ��غ���
				// ���빰�� �����ϴ� ��)
		setLocationRelativeTo(null);// ȭ���߾�
	}

	public static void main(String[] args) {
		new MemberWindow();
	}

}