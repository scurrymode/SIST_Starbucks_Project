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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class EmpPanel extends MyPanel implements ActionListener, TableModelListener{
	JLabel la_name;
	JButton bt_search,bt_reg,bt_edit;
	public EmpPanel() {
		la_name =new JLabel("�̸�");
		bt_search = new JButton("�˻�");
		bt_reg = new JButton("���� ���� �߰�");
		//bt_edit = new JButton("���� ���� ����");
		dataController = new DataController(this);
		dataController.getList("emp");
		
		p_south.setPreferredSize(new Dimension(800, 70));
		setLayout(new BorderLayout());
		p_north.add(bt_reg);
		//p_north.add(bt_edit);
		p_south.add(choice);
		p_south.add(t_search);
		p_south.add(bt_search);
		
		
		//��ư�� ������ ����
		//bt_edit.addActionListener(this);
		bt_reg.addActionListener(this);
		bt_search.addActionListener(this);
		//table.getModel().addTableModelListener(this);
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		//���̺� �ʱ� ����
		table.setModel(model);
		
		add(p_north,BorderLayout.NORTH);
		add(scroll);
		add(p_south,BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_reg){
			regist();
		}else if(obj == bt_search){
			search();
		}
	}
	public void regist(){
		new InsertFrame(dataController);
	}
	public void search(){
		JOptionPane.showMessageDialog(this, "�˻��ҰԿ�");
		dataController.SearchEmp();
	}

	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"emp");
	}
}
