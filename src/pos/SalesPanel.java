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

public class SalesPanel extends MyPanel implements ActionListener{
	JPanel p_south,p_north;
	JScrollPane scroll;
	JLabel la_name;
	JButton bt_chart;
	DataController dataController;
	
	public SalesPanel() {
		p_south = new JPanel();
		p_north = new JPanel();
		table = new JTable(3,4);
		scroll =new JScrollPane(table);
		la_name =new JLabel("이름");

		bt_chart = new JButton("차트");

		dataController = new DataController(this);
		dataController.getList("sales");

		p_south.setPreferredSize(new Dimension(800, 70));
		setLayout(new BorderLayout());
		p_north.add(bt_chart);
		//버튼에 리스너 연결
		bt_chart.addActionListener(this);
	
		//테이블 초기 설정
		table.setModel(dataController.getDataModel());
		
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
		JOptionPane.showMessageDialog(this, "회원 계정 등록");
	}
}
