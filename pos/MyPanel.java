package pos;

import java.awt.Choice;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyPanel extends JPanel{
	JTable table;
	Choice choice;
	JTextField t_search;
	JScrollPane scroll;
	JPanel p_south,p_north;
	DataModel model;
	DataController dataController;
	public MyPanel() {
		table = new JTable();
		choice = new Choice();
		t_search =new JTextField(20);
		scroll =new JScrollPane(table);
		p_south = new JPanel();
		p_north = new JPanel();
		choice.add("�̸�");
		choice.add("ID");
	}
}
