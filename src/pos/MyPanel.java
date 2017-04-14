package pos;

import java.awt.Choice;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyPanel extends JPanel{
	JTable table;
	Choice choice;
	JTextField t_search;
	public MyPanel() {
		table = new JTable();
		choice = new Choice();
		t_search =new JTextField(20);
		choice.add("¿Ã∏ß");
		choice.add("ID");
		
	}
}
