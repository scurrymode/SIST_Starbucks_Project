package reservation;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.DBManager;
import dto.Member;

public class ReservationMain extends JFrame implements ActionListener, ItemListener{
	JPanel p_north, p_center, p_cal, p_south;
	JPanel p_south_east, p_south_center;
	Choice choice;
	String room="1�� ���͵��";
	JButton bt_prev, bt_next, bt_show;
	JLabel la;
	
	boolean flag;
	URL[] url_small = new URL[3];
	URL nowURL;
	String selectRoom;
	
	
	DBManager manager = DBManager.getInstance();
	Connection con=manager.getConnection();
	
	//ȸ�� ����
	Member dto;
	
	//�����
	int roomNum=1;
	
	//�޷� ����
	Calendar cal;
	int year;
	int month;
	int date;
	
	//������Ȳ �迭
	int[][] reservationStatus={
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0}
	};

	public ReservationMain(Member dto) {
		this.dto=dto;
		p_north = new JPanel();
		p_north.setLayout(new BorderLayout());
		
		p_center = new JPanel();
		p_cal = new JPanel();
		p_south = new JPanel();
		p_south_east = new JPanel();
		p_south_center = new JPanel();
		
		p_south.setLayout(new BorderLayout());
		p_south.setPreferredSize(new Dimension(600, 700));
		//�����г�
		choice = new Choice();
		bt_show = new JButton("�̹��� ����");
		bt_prev = new JButton("<");
		la = new JLabel("");
		la.setFont(new Font("���", Font.BOLD, 20));
		bt_next = new JButton(">");
		
		choice.setPreferredSize(new Dimension(100, 50));
		p_cal.setPreferredSize(new Dimension(100, 50));
		p_south_east.setPreferredSize(new Dimension(150, 600));
		
		p_south.setVisible(false);
		
		p_south.add(p_south_east, BorderLayout.EAST);
		p_south.add(p_south_center);
		
		p_cal.add(bt_prev);
		p_cal.add(la);
		p_cal.add(bt_next);
			
		/*choice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object obj = e.getItem();
				String str = obj.toString();
				//��Ʈ������ ���ڸ� �����ϱ�
				ReservationMain.this.roomNum=Integer.parseInt(str.replaceAll("[^0-9]",""));
				p_center.removeAll();
				attachPanel();
			}
		});*/
		choice.add("���͵�� ����");
		choice.add("1�� ���͵��");
		choice.add("2�� ���͵��");
		
		//choice.select(1);
		
		p_north.add(choice, BorderLayout.WEST);
		p_north.add(p_cal, BorderLayout.CENTER);
		p_north.add(bt_show, BorderLayout.EAST);
		p_north.add(p_south, BorderLayout.SOUTH);
		
		add(p_north, BorderLayout.NORTH);
		
		//���Ϳ� �޷� �г� ���̱�
		init();
		
		add(p_center);
		p_north.setPreferredSize(new Dimension(600, 50));
		
		choice.addItemListener(this);
		//��ư ������ ���̱�
		bt_show.addActionListener(this);
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);
		
		setSize(300*2, 400*2);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	//�޷� ���� ���̱�
	public void init(){
		getCalendar();
		la.setText(year+"�� "+(month+1)+"��");
		attachPanel();
	}
	
	//���� ���� ��������!
	public void getCalendar(){
		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		date = cal.get(Calendar.DATE);
	}
	
	//������ ������
		public void query(){
			//rs�϶��� 0���� ����� �ȵǴϱ�~~!! ���⼭ �ʱ�ȭ�� �ѹ� �����~!
			for(int j=0; j<31;j++){
				for(int i=0;i<8;i++){
					reservationStatus[j][i]=0; //�ƴϸ� 0���� �ٽ� �������ߵ�~
				}
			}		
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "select * from reservation where reservation_month="+(month+1)+" and reservation_room_num="+roomNum;
			try {
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					for(int j=0;j<31;j++){
						if(rs.getInt("reservation_date")==j+1){
							for(int i=0;i<8;i++){								
								if(rs.getInt("reservation_start_time") == i+10){
									if(rs.getString("reservation_member_login_id").equalsIgnoreCase(dto.getMember_login_id())){
										reservationStatus[j][i]=2; //���� �� �����̸�,
									}else {
										reservationStatus[j][i]=1; //�ٸ� ����� �� �����̸�,
									}
									
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs!=null){
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(pstmt!=null){
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
	public void attachPanel(){
		query();
		cal.set(year, month, 1);
		int first = cal.get(Calendar.DAY_OF_WEEK);
		
		cal.set(year, month+1, 0);
		int last = cal.get(Calendar.DATE);
		
		
		int num=1;
		for(int i=0; i<42; i++){
			if(i>=first-1&&num<=last){
				ResCalendar resCalendar = new ResCalendar(this, num);
				p_center.add(resCalendar);
				num++;
				
			} else {
				EmptyCalendar emptyCalendar = new EmptyCalendar();
				p_center.add(emptyCalendar);
			}			
		}
		p_center.updateUI();
	
	}
	
	public void showImage() {
		/*flag�� true�̸� �̹����� �������� �ִ� ���� 
		 * false�̸� �̹����� �Ⱥ������� �ִ� ����*/
		if(choice.getSelectedIndex() != 1 && choice.getSelectedIndex() != 2) {
			JOptionPane.showMessageDialog(this, "���� ������ �ּ���!");
		}
		else {
			if(flag) {
				flag = false;
				
				p_south.updateUI();
				p_south.setVisible(false);
				p_north.setPreferredSize(new Dimension(600, 50));
				
			}
			else {
				flag = true;
				
				p_south.updateUI();
				p_south.setVisible(true);
				p_north.setPreferredSize(new Dimension(600, 750));
			}
 		}
		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_prev){
			month--;
			if(month<0){
				month=11;
				year--;
			}
		} else if(obj == bt_next){
			month++;
			if(month>11){
				month=0;
				year++;
			}
		} else if(obj == bt_show) {
			showImage();
		} 
		la.setText(year+"�� "+(month+1)+"��");
		la.updateUI();
		p_center.removeAll();
		attachPanel();
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(choice.getSelectedIndex() != 0) {
			selectRoom = choice.getSelectedItem();
			//smallList.removeAll(smallList);
			p_south_east.removeAll();
			p_south_center.removeAll();
			try {
				if(selectRoom.equals("1�� ���͵��")) {
					for(int i = 0; i < url_small.length; i++) {
						url_small[i] = new URL("http://localhost:9090/data/reserve1-"+ (i + 1)+".jpg");
						System.out.println(url_small[i].toString());
						
						CreateSmallCan small = new CreateSmallCan(url_small[i], this);
						p_south_east.add(small);
						System.out.println("CreateSmallCan ���� ��" + url_small[i].toString());
						//smallList.add(small);
					}
					
				} else if(selectRoom.equals("2�� ���͵��")) {
					for(int i = 0; i < url_small.length; i++) {
						url_small[i] = new URL("http://localhost:9090/data/reserve2-"+ (i + 1)+".jpg");
						System.out.println(url_small[i].toString());
						
						CreateSmallCan small = new CreateSmallCan(url_small[i], this);
						p_south_east.add(small);
						//smallList.add(small);
					}
				}
				
				Object obj = e.getItem();
				String str = obj.toString();
				//��Ʈ������ ���ڸ� �����ϱ�
				ReservationMain.this.roomNum=Integer.parseInt(str.replaceAll("[^0-9]",""));
				p_center.removeAll();
				attachPanel();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		new ReservationMain(new Member());

	}

}
