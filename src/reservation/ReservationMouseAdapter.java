package reservation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		
	}
}