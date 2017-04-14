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
		
		tabbedPane.addTab("权",p_home);
		tabbedPane.addTab("流盔包府",new EmpPanel() );
		tabbedPane.addTab("雀盔包府",new MemberPanel());
		tabbedPane.addTab("概免包府",new SalesPanel());
		tabbedPane.addTab("犁绊包府",new GoodsPanel());
		tabbedPane.addTab("饭矫乔包府",new RecipePanel());
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
