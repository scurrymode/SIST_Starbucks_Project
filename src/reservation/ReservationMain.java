package reservation;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.DBManager;
import dto.Member;

public class ReservationMain extends JFrame implements ActionListener{
	JPanel p_north, p_center;
	Choice choice;
	String room="1�� ���͵��";
	JButton bt_prev, bt_next;
	JLabel la;
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
		p_center = new JPanel();
		//�����г�
		choice = new Choice();
		bt_prev = new JButton("<");
		la = new JLabel("");
		la.setFont(new Font("���", Font.BOLD, 20));
		bt_next = new JButton(">");
			
		choice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object obj = e.getItem();
				String str = obj.toString();
				//��Ʈ������ ���ڸ� �����ϱ�
				ReservationMain.this.roomNum=Integer.parseInt(str.replaceAll("[^0-9]",""));
				p_center.removeAll();
				attachPanel();
			}
		});
		choice.add("1�� ���͵��");
		choice.add("2�� ���͵��");
		
		p_north.add(choice);
		p_north.add(bt_prev);
		p_north.add(la);
		p_north.add(bt_next);
		
		add(p_north, BorderLayout.NORTH);
		
		//���Ϳ� �޷� �г� ���̱�
		init();
		
		add(p_center);
		
		//��ư ������ ���̱�
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

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_prev){
			month--;
			if(month<0){
				month=11;
				year--;
			}
		}else if(obj == bt_next){
			month++;
			if(month>11){
				month=0;
				year++;
			}
		}
		la.setText(year+"�� "+(month+1)+"��");
		la.updateUI();
		p_center.removeAll();
		attachPanel();
	}
	
	
	public static void main(String[] args) {
		new ReservationMain(new Member(){
			
		});

	}

}
