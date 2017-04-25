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
		tabbedPane.addTab("权",p_home);
		tabbedPane.addTab("流盔包府",new EmpPanel() );
		tabbedPane.addTab("雀盔包府",new MemberPanel());
		tabbedPane.addTab("概免包府",new SalesPanel());
		tabbedPane.addTab("犁绊包府",new GoodsPanel());
		tabbedPane.addTab("饭矫乔包府",new RecipePanel());
		tabbedPane.addTab("饭矫乔包府",new BoardPanel());
		add(tabbedPane);
		
		setVisible(true);
		setSize(800,800);
		
	}
	
}