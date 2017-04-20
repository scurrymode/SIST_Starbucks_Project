package client;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.Product;

public class ClientMenuPanel extends JPanel implements ActionListener{
	ClientOrders clientOrders;
	BufferedImage image;
	Canvas can;
	JButton bt;
	JLabel la_name, la_price;
	Product product;

	
	public ClientMenuPanel(ClientOrders clientOrders, Product product) {
		this.clientOrders=clientOrders;
		this.product=product;
		
		setPreferredSize(new Dimension(110, 280));
		
		int id = product.getProduct_id();
		try {
			//��ǰ �̹��� ��������
			URL url = new URL("http://localhost:9090/data/"+id+".jpg");
			image = ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 100, 200, this);
			}
		};
		
		can.setPreferredSize(new Dimension(100, 200));
		
		la_name = new JLabel(product.getProduct_name());
		la_price = new JLabel(Integer.toString(product.getProduct_price()));
		bt = new JButton("�߰��ϱ�");
		
		
		
		bt.addActionListener(this);
		
		add(can);
		add(la_name);
		add(la_price);
		add(bt);		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		//�ֹ��� �޴� ���� �ƿ� �гη� ��� �ҵ�?
		JLabel la_order = new JLabel("    "+product.getProduct_name()+"               "+"1"+"               "+Integer.toString(product.getProduct_price()));
		Font font = new Font("����", Font.BOLD, 20);
		la_order.setFont(font);
		la_order.setPreferredSize(new Dimension(600, 20));
		clientOrders.p_center.add(la_order);
		clientOrders.orders_list.add(product);
		System.out.println("��ǰ �ֹ��� �߰���");
		
		
		//�հ�ݾ� üũ
		int sum=0;
		for(int i=0; i<clientOrders.orders_list.size();i++){
			sum+=clientOrders.orders_list.get(i).getProduct_price();
		}
		clientOrders.la_sum.setText("�հ�ݾ�"+sum);
		clientOrders.p_center.updateUI();
		
	}
	
	
	
}
