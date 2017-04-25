package pos;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import dto.Member;
import pos.login.PosWindow;

public class AdminPage extends JPanel{
	
	JPanel p_home,p_south;
	JTabbedPane tabbedPane;
	PosWindow posWindow;
	BufferedImage bufferedImage;
	JLabel la_south;
	Canvas can;
	public AdminPage(PosWindow posWindow) {
		getImage();
		this.posWindow = posWindow;
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(bufferedImage, 0, 0, this);
			}
		};
		la_south = new JLabel(posWindow.id+"�� �ȳ��ϼ���? �����������Դϴ�");
		la_south.setFont(new Font("����", Font.BOLD, 15));
		tabbedPane = new JTabbedPane();
		
		p_home = new JPanel();
		p_south = new JPanel();
		p_home.setLayout(new BorderLayout());
		p_home.setBackground(Color.white);
		p_home.add(can);
		p_south.add(la_south);
		p_south.setBackground(Color.black);
		la_south.setForeground(Color.white);
		p_home.add(p_south,BorderLayout.SOUTH);
		tabbedPane.addTab("Ȩ",p_home);
		tabbedPane.setBackground(new Color(0, 115, 67));
		tabbedPane.setForeground(new Color(255, 255, 255));
		tabbedPane.setBorder(null);
		UIManager.put("TabbedPane.selected", Color.white);
		this.setBackground(Color.white);
		tabbedPane.addTab("��������",new EmpPanel() );
		tabbedPane.addTab("ȸ������",new MemberPanel());
		tabbedPane.addTab("�������",new SalesPanel());
		tabbedPane.addTab("������",new GoodsPanel());
		tabbedPane.addTab("�����ǰ���",new RecipePanel());
		tabbedPane.addTab("�������װ���",new BoardPanel(posWindow));
		//tabbedPane.add("�������",new ReservationPanel(new Member()));
		add(tabbedPane);
		
		can.setPreferredSize(new Dimension(800, 600));
		setBackground(Color.black);
		setVisible(true);
		setSize(800,800);
	}
	public void getImage(){
		URL url;
		try {
			url = new URL("http://localhost:9090/data/home_img.jpg");
			bufferedImage = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
}