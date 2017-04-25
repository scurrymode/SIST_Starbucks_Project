package reservation;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dto.Reservation;

public class MyReservation extends JFrame implements ActionListener{
	JPanel p_center, p_south;
	JLabel label;
	Choice ch_time;
	JButton bt_back, bt_edit, bt_delete;
	
	int selectTime;
	Reservation reservation;
	
	ReservationMain reservationMain;
	int date;
	int time;
	int maxUnit;
	String resveStatus;
	
	public MyReservation(ReservationMain reservationMain, int date, int time, int maxUnit, String resveStatus) {
		this.reservationMain = reservationMain;
		this.date = date;
		this.time = time;
		this.resveStatus = resveStatus;
		this.maxUnit = maxUnit;
		
		p_center = new JPanel();
		p_south = new JPanel();
		label = new JLabel("������ �ð� ����");
		ch_time = new Choice();
		bt_back = new JButton("�ڷ�");
		bt_edit = new JButton("���� ����");
		bt_delete = new JButton("���� ����");
		
		reservation = new Reservation();
		
		ch_time.add("�� ����ð�");
		for(int i = 0; i < maxUnit; i++) {
			ch_time.add((i + 1) + "�ð�");
		}
		
		p_center.add(label);
		p_center.add(ch_time);
		
		p_south.add(bt_back);
		p_south.add(bt_edit);
		p_south.add(bt_delete);
		
		bt_back.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_delete.addActionListener(this);
		
		ch_time.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("���õ� �ð���? " + ch_time.getSelectedItem() + ", �ε��� : " + ch_time.getSelectedIndex());
				selectTime = ch_time.getSelectedIndex();
				System.out.println(selectTime);
			}
		});
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		setSize(200, 100);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/*public void updateReservation() {
		ReservationThread thread;

		if(resveStatus.equals("otherreserve")) {
			//����Ǵ°� ����
		} else if(resveStatus.equals("noreserve")) {
			//�� ������ �ѽð��ε� �νð����� �ٲٸ� insert�� ������
			if(maxUnit == 2) {
				reservation.setReservation_room_num(reservationMain.roomNum);
				reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
				reservation.setReservation_year(reservationMain.year);
				reservation.setReservation_month(reservationMain.month + 1);
				reservation.setReservation_date(date);	
				reservation.setReservation_time_unit(1);
				reservation.setReservation_start_time(time + 1);
				
				thread = new ReservationThread(reservation, "insert");
				thread.start();
			}
		} else if(resveStatus.equals("myreserve")) {
			//�� ������ �νð��ε� �ѽð����� �ٲٸ� delete�� ������
			if(maxUnit == 1) {
				reservation.setReservation_room_num(reservationMain.roomNum);
				reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
				reservation.setReservation_year(reservationMain.year);
				reservation.setReservation_month(reservationMain.month + 1);
				reservation.setReservation_date(date);	
				reservation.setReservation_time_unit(1);
				reservation.setReservation_start_time(time + 1);
				
				thread = new ReservationThread(reservation, "delete");
				thread.start();
			}
			
		}
		
	}
	
	public void deleteReservation() {
		ReservationThread thread;
		
		if(resveStatus.equals("otherreserve")) {
			//1�ð� ����
			reservation.setReservation_room_num(reservationMain.roomNum);
			reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
			reservation.setReservation_year(reservationMain.year);
			reservation.setReservation_month(reservationMain.month + 1);
			reservation.setReservation_date(date);
			reservation.setReservation_start_time(time);
			reservation.setReservation_time_unit(1);
			
			thread = new ReservationThread(reservation, "delete");
			thread.start();
		} else if(resveStatus.equals("noreserve")) {
			//1�ð� ����
			reservation.setReservation_room_num(reservationMain.roomNum);
			reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
			reservation.setReservation_year(reservationMain.year);
			reservation.setReservation_month(reservationMain.month + 1);
			reservation.setReservation_date(date);
			reservation.setReservation_start_time(time);
			reservation.setReservation_time_unit(1);
			
			thread = new ReservationThread(reservation, "delete");
			thread.start();
		} else if(resveStatus.equals("myreserve")) {
			//2�ð� ����
			for(int i = 0; i < 2; i++) {
				reservation.setReservation_room_num(reservationMain.roomNum);
				reservation.setReservation_member_login_id(reservationMain.dto.getMember_login_id());
				reservation.setReservation_year(reservationMain.year);
				reservation.setReservation_month(reservationMain.month + 1);
				reservation.setReservation_date(date);
				reservation.setReservation_start_time(time + i);
				reservation.setReservation_time_unit(1);
				
				thread = new ReservationThread(reservation, "delete");
				thread.start();
			}
			
		}
		
	}*/
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == bt_back) {
			this.dispose();
		} else if(obj == bt_edit) {
			//update�������ݺ�����
			if(selectTime != 0) {
				//updateReservation();
			} 
			else {
				JOptionPane.showMessageDialog(this, "�ð��� �������ּ���!");
			}
			
		} else if(obj == bt_delete) {
			//delete�������ݺ�����
			if(selectTime != 0) {
				//deleteReservation();
			} 
			else {
				JOptionPane.showMessageDialog(this, "�ð��� �������ּ���!");
			}
			
		}
	}
}
