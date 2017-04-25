package reservation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class ReservationMouseAdapter extends MouseAdapter{
	ReservationMain reservationMain;
	int date;
	int time;
	
	public ReservationMouseAdapter(ReservationMain reservationMain, int date, int time) {
		this.reservationMain =reservationMain;
		this.date=date;
		this.time=time;
	}
	
	public void mouseClicked(MouseEvent e) {
		//���⼭ ����� �Ѵ�. �����ϰ� ���ϰ� �� ���⼭ �Ѷ�~~!
		System.out.println(reservationMain.year+"��"+(reservationMain.month+1)+"��"+date+"��"+time+"������?");
		
		int status = reservationMain.reservationStatus[date-1][time-10];
		int isPossibleNextTime = (time - 10) + 1;
		
		if(status == 0) {
			/*
			 * ������ �ȵǾ������Ƿ� Reserving.java�� ����
			 * ���� ������ �ð� ���� ��ȸ�ؼ� ������
			 * ??���?? ���� �ð��� status�� 2�̸� �ִ� 1�ð��� ���� �ɼ� �ְ� choice�� 1�� ��Ÿ���� �Ѵ�.
			 * ReservationProtocol�� insert���� ������
			 */
			if(reservationMain.reservationStatus[date-1][isPossibleNextTime] != 0) {
				new Reserving(reservationMain, date, time, 1);
			} 
			else {
				new Reserving(reservationMain, date, time, 2);
			}
			
		} else if(status == 2) {
			/*
			 * �� �����̹Ƿ� MyReservation.java�� ����
			 * ���� 2�ð��� ����������, �ִ� 2�ð��� ��Ÿ���� 
			 * ���� 1�ð��� �����ߴµ�, ���� �ð��� ������ �ȵǾ������� 2�ð��� ��Ÿ�� �� �ְ�
			 * �����ð��� ������ �Ǿ������� 1�ð��� ���õ� �� �ְ��Ѵ�. 
			 * ���� ��ư ������ ReservationProtocol�� update���� ������
			 * ���� ��ư ������ ReservationProtocol�� delete���� ������
			 */
			if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 1) {
				new MyReservation(reservationMain, date, time, 1, "otherreserve");
			} else if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 0) {//���� �ȵǾ�����
				new MyReservation(reservationMain, date, time, 2, "noreserve");
			} else if(reservationMain.reservationStatus[date-1][isPossibleNextTime] == 2) {//�� ������.
				new MyReservation(reservationMain, date, time, 2, "myreserve");
			}
		} else if(status == 1) {
			JOptionPane.showMessageDialog(reservationMain, "�̹� ���� ������ �ð��Դϴ�!");
		}
	}
}
