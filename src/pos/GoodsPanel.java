package pos;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GoodsPanel extends MyPanel implements ActionListener{
	JPanel p_south,p_north;
	JTextField t_search;
	JScrollPane scroll;
	JLabel la_name;
	JButton bt_search,bt_reg,bt_edit;
	DataController dataController;
	
	public GoodsPanel() {
		p_south = new JPanel();
		p_north = new JPanel();
		t_search = new JTextField(20);
		scroll =new JScrollPane(table);
		la_name =new JLabel("�̸�");
		bt_reg = new JButton("��� �߰�");
		dataController = new DataController(this);
		dataController.getList("goods");
		
		p_south.setPreferredSize(new Dimension(800, 70));
		setLayout(new BorderLayout());
		//p_north.add(bt_reg);
		p_north.add(bt_reg);
		
		//��ư�� ������ ����
		bt_reg.addActionListener(this);
		//���̺� �ʱ� ����
		table.setModel(dataController.getDataModel());
		
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		add(p_south,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_reg){
			regist();
		}
	}
	public void regist(){
		//System.out.println(dataController.data.get(1).get(1));
		JOptionPane.showMessageDialog(this, "ȸ�� ���� ���");
	}
}
