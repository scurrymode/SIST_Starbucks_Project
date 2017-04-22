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
		//여기서 열어야 한다. 수정하고 뭐하고 다 여기서 켜라~~!
		System.out.println(reservationMain.year+"년"+(reservationMain.month+1)+"월"+date+"일"+time+"눌렀군?");
		
	}
}