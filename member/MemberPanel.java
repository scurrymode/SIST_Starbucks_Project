package member;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class MemberPanel extends JPanel {
	MemberWindow member;

	public MemberPanel(MemberWindow member) {
		this.member = member;
		setLayout(new BorderLayout());

		setVisible(false); // ���ʿ� �������
		// setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(900, 700));

	}

}
