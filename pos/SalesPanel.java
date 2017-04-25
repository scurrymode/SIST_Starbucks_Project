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

public class SalesPanel extends MyPanel implements ActionListener,TableModelListener{
	
	JLabel la_name;
	JButton bt_chart;

	public SalesPanel() {
		la_name =new JLabel("�̸�");
		bt_chart = new JButton("��Ʈ");
		dataController = new DataController(this);
		dataController.getList("sales");

		p_south.setPreferredSize(new Dimension(800, 70));
		setLayout(new BorderLayout());
		p_north.add(bt_chart);
		//��ư�� ������ ����
		bt_chart.addActionListener(this);
	
		//���̺� �ʱ� ����
		model =(DataModel) dataController.getDataModel();
		model.addTableModelListener(this);
		
		table.setModel(model);
		
		add(p_north,BorderLayout.NORTH);
		add(scroll);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_chart){
			showChart();
		}
	}
	public void showChart(){
		JOptionPane.showMessageDialog(this, "ȸ�� ���� ���");
	}

	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int col = e.getColumn();
		dataController.editTable(model,e,"sales");
	}
	
}
