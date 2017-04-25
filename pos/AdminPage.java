package pos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import member.MemberWindow;
import pos.login.PosWindow;

public class AdminPage extends JPanel{
	
	JPanel p_home;
	JTabbedPane tabbedPane;
	PosWindow posWindow;
	public AdminPage(PosWindow posWindow) {
		this.posWindow = posWindow;
		tabbedPane = new JTabbedPane();
		p_home = new JPanel();
		tabbedPane.addTab("Ȩ",p_home);
		tabbedPane.addTab("��������",new EmpPanel() );
		tabbedPane.addTab("ȸ������",new MemberPanel());
		tabbedPane.addTab("�������",new SalesPanel());
		tabbedPane.addTab("������",new GoodsPanel());
		tabbedPane.addTab("�����ǰ���",new RecipePanel());
		tabbedPane.addTab("�����ǰ���",new BoardPanel());
		add(tabbedPane);
		
		setVisible(true);
		setSize(800,800);
		
	}
	
}