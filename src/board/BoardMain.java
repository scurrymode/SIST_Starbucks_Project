package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import dto.Board;

public class BoardMain extends JFrame{
	JPanel p_main;
	JTable table;
	JScrollPane scroll;
	JButton bt_back;
	Vector<Board> board_list = new Vector<Board>();
	
	public BoardMain() {
		p_main = new JPanel();
		BoardTableModel model = new BoardTableModel(board_list);
		
		table = new JTable(model);
		scroll = new JScrollPane(table);
		
		p_main.add(scroll);
		add(p_main);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//조회수 올리기 해야한다.DB에
				int row =table.getSelectedRow();
				board_list.get(row).setBoard_count(board_list.get(row).getBoard_count()+1);
				showContent(row);
			}
		});
		
		setSize(300*2, 400*2);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//setPreferredSize(new Dimension(300*2, 400*2));
	}
	
	public void showContent(int row){
		p_main.removeAll();
		
		String str ="게시번호: "+board_list.get(row).getBoard_id();
		str+="제목: "+ board_list.get(row).getBoard_title();
		str+="작성자: "+ board_list.get(row).getBoard_emp_name();
		str+="조회수: "+ board_list.get(row).getBoard_count();
		JLabel la_top = new JLabel(str);		
		String content = board_list.get(row).getBoard_contents();
		JTextArea area = new JTextArea(content);
		JScrollPane scroll = new JScrollPane(area);
		
		bt_back = new JButton("뒤로");
		bt_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardMain.this.p_main.removeAll();
				BoardMain.this.p_main.add(BoardMain.this.scroll);
				BoardMain.this.p_main.updateUI();
			}
		});
		
		p_main.add(la_top);
		p_main.add(scroll);
		p_main.add(bt_back);
		p_main.updateUI();
	}


}
