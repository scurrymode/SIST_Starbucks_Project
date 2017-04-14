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

public class AdminPage extends JFrame{
	
	JPanel p_home;
	JScrollPane scroll_home,scroll_emp,scroll_member,scroll_sales,scroll_goods;
	JTabbedPane tabbedPane;
	public AdminPage() {
		tabbedPane = new JTabbedPane();
		p_home = new JPanel();
		
		tabbedPane.addTab("Ȩ",p_home);
		tabbedPane.addTab("��������",new EmpPanel() );
		tabbedPane.addTab("ȸ������",new MemberPanel());
		tabbedPane.addTab("�������",new SalesPanel());
		tabbedPane.addTab("������",new GoodsPanel());
		tabbedPane.addTab("�����ǰ���",new RecipePanel());
		add(tabbedPane);
		setVisible(true);
		setSize(800,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new AdminPage();
	}
}
