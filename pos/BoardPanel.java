package pos;

<<<<<<< HEAD
import java.awt.Dimension;
=======
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
<<<<<<< HEAD
import javax.swing.JFrame;
=======
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

<<<<<<< HEAD
import board.BoardTableModel;
import dto.Board;

public class BoardPanel extends JPanel{
	JPanel p_main;
	JTable table;
	JScrollPane scroll;
	JButton bt_back;
	Vector<Board> board_list = new Vector<Board>();
	
	public BoardPanel() {
		p_main = new JPanel();
=======
import board.BoardCount;
import board.BoardMain;
import board.BoardTableModel;
import dto.Board;
import pos.login.PosWindow;

public class BoardPanel extends JPanel implements ActionListener{
	JPanel p_main;
	JTable table;
	JScrollPane scroll;
	JButton bt_back,bt_insert;
	Vector<Board> board_list = new Vector<Board>();
	PosWindow posWindow;
	
	public BoardPanel(PosWindow posWindow) {
		this.posWindow = posWindow;
		p_main = new JPanel();
		setTable();
		bt_insert =  new JButton("�Խñ� ����");
		scroll.setPreferredSize(new Dimension(800-20, 500-20));
		p_main.setPreferredSize(new Dimension(800-20, 500-20));
		p_main.add(scroll);
		add(p_main);		
		add(bt_insert,BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(300*2+200, 600));
		setVisible(true);
		setBackground(Color.WHITE);
		
		bt_insert.addActionListener(this);
	}
	
	public void setTable(){
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
		BoardTableModel model = new BoardTableModel(board_list);
		
		table = new JTable(model);
		scroll = new JScrollPane(table);
<<<<<<< HEAD
		
		p_main.add(scroll);
		add(p_main);
=======
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//��ȸ�� �ø��� �ؾ��Ѵ�.DB��
				int row =table.getSelectedRow();
				board_list.get(row).setBoard_count(board_list.get(row).getBoard_count()+1);
				showContent(row);
<<<<<<< HEAD
			}
		});
		
		//setSize(300*2, 400*2);
		//setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//setPreferredSize(new Dimension(800, 800));
=======
				new BoardCount(board_list.get(row));
			}
		});
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
	}
	
	public void showContent(int row){
		p_main.removeAll();
<<<<<<< HEAD
		
		String str ="�Խù�ȣ: "+board_list.get(row).getBoard_id();
		str+="����: "+ board_list.get(row).getBoard_title();
		str+="�ۼ���: "+ board_list.get(row).getBoard_emp_name();
		str+="��ȸ��: "+ board_list.get(row).getBoard_count();
		JLabel la_top = new JLabel(str);		
		String content = board_list.get(row).getBoard_contents();
		JTextArea area = new JTextArea(content);
=======
		Font font = new Font("����", Font.BOLD, 15);
		Font font_title = new Font("����", Font.BOLD, 35);
		JPanel p_title = new JPanel();
		JPanel p_info = new JPanel();
		JLabel la_title_value = new JLabel(board_list.get(row).getBoard_title());
		JLabel la_writer = new JLabel("�ۼ���: ");
		JLabel la_writer_value = new JLabel(board_list.get(row).getBoard_emp_name()+" |");
		JLabel la_count = new JLabel("��ȸ��: ");
		JLabel la_count_value = new JLabel(Integer.toString(board_list.get(row).getBoard_count()));
		p_title.setPreferredSize(new Dimension(600, 40));
		la_title_value.setFont(font_title);
		la_writer.setFont(font);
		la_writer_value.setFont(font);
		la_count.setFont(font);
		la_count_value.setFont(font);
		p_title.add(la_title_value);
		p_info.add(la_writer);
		p_info.add(la_writer_value);
		p_info.add(la_count);
		p_info.add(la_count_value);
	
		String content = board_list.get(row).getBoard_contents();
		JTextArea area = new JTextArea(30,50);
		area.setText(content);
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
		JScrollPane scroll = new JScrollPane(area);
		
		bt_back = new JButton("�ڷ�");
		bt_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
				BoardPanel.this.p_main.removeAll();
				BoardPanel.this.p_main.add(BoardPanel.this.scroll);
=======
				BoardPanel.this.setPreferredSize(new Dimension(300*2+100, 700));
				BoardPanel.this.p_main.removeAll();
				//���̺��� ���� �׸��� �ؾ��Ѵ�.
				BoardPanel.this.setTable();
				BoardPanel.this.p_main.add(BoardPanel.this.scroll);
				BoardPanel.this.add(p_main);
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
				BoardPanel.this.p_main.updateUI();
			}
		});
		
<<<<<<< HEAD
		p_main.add(la_top);
=======
		p_main.add(p_title);
		p_main.add(p_info);
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
		p_main.add(scroll);
		p_main.add(bt_back);
		p_main.updateUI();
	}

<<<<<<< HEAD

=======
	public void actionPerformed(ActionEvent e) {
		new AddBoard(posWindow, this);
	}
>>>>>>> 456fedb90b686eed7c5d4f83c2f5241b144630ea
}
