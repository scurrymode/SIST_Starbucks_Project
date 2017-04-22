//�޷� �Ϸ��Ϸ�~!
//���� ���� ���� Ȯ���ϱ�

package reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResCalendar extends JPanel{
	ReservationMain reservationMain;
	
	JLabel la_date;
	JPanel p_grid; //��¥���̶� ���� ��Ȳ���� ���� ��,
	ArrayList<JLabel> label_list = new ArrayList<JLabel>();
	
	int date;//�г��� ���� �Ǵ� ���� ����
	
	
	public ResCalendar(ReservationMain reservationMain, int date) {
		this.reservationMain=reservationMain;
		this.date=date;

		this.setLayout(new BorderLayout());
		
		setBackground(Color.PINK);
		
		la_date = new JLabel(Integer.toString(date));
		p_grid = new JPanel();
		p_grid.setBackground(Color.WHITE);
			
		p_grid.setLayout(new GridLayout(4,	2));
		
		for(int i=0; i<8; i++){
			JLabel la = new JLabel((i+10)+"��");
			la.addMouseListener(new ReservationMouseAdapter(reservationMain, date, (i+10)));
			if(reservationMain.reservationStatus[date-1][i]==1){
				la.setForeground(Color.RED);
			}else if(reservationMain.reservationStatus[date-1][i]==2){
				la.setForeground(new Color(104, 234, 46));
			}else{
				la.setForeground(Color.BLUE);
			}
			label_list.add(la);
			p_grid.add(la);
		}
		
		
				
		add(la_date, BorderLayout.NORTH);
		add(p_grid);

		
		setPreferredSize(new Dimension(70, 70));
	}
}
