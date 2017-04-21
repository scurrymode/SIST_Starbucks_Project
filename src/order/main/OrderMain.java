package order.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import db.DBManager;
import order.payment.Payment;
import pos.login.PosWindow;

public class OrderMain extends JPanel implements ActionListener,Runnable{
	Connection con;
	DBManager manager;
	Thread thread;
	//	p_east ��üȭ�� ����, p_west ��üȭ�� ���� p_product �ֹ��ѰŶߴ� ��,p_topMenu�޴� ���� ��ư�� �ִ°� p_subMenu�޴� ������ư�� p_pay �����ϱ��ư �ִ� �� 

	JPanel p_product,p_component,p_topMenu,p_subMenu, p_sum,p_pay , p_east, p_west ,p_date ;
	JButton bt_pay, bt_allDelete;
	JScrollPane scroll;
	
	JLabel la_sum_name,la_sum,la_info ,la_date;
	Vector<Product_category> bigMenu=new Vector<Product_category>();//���� �޴��� �������(Ŀ��,�꽺.��)
	Vector <Product> product_list=new Vector<Product>();	 //product ���� �޴����� ������ ������� 
	Vector<ProductPanel> menu_list=new Vector<ProductPanel>();
	Vector<Orders> orders_list=new Vector<Orders>();
	PosWindow posWindow;
	int total;
	int order_number=1;
	
	public OrderMain(PosWindow posWindow) {
		this.posWindow =posWindow;
		p_date=new JPanel();
		p_east=new JPanel();
		p_west=new JPanel();
		p_product=new JPanel();
		p_subMenu=new JPanel(); 
		p_topMenu=new JPanel();
		p_sum=new JPanel();
		p_pay=new JPanel();
		p_component=new JPanel();
		scroll=new JScrollPane(p_component);
		
		bt_allDelete=new JButton("��λ���");
		bt_pay=new JButton("�����ϱ�");
		
		la_date=new JLabel("�ð�");
		la_sum_name=new JLabel("�հ�:");
		la_sum=new JLabel("0");
		la_sum_name.setFont(new Font("����", Font.BOLD, 30));
		la_sum.setFont(new Font("����", Font.BOLD, 30));
		
		
		la_info=new JLabel("��ǰ��       ����         �ݾ�");
		la_info.setFont(new Font("����", Font.BOLD , 50 ));
		

		Product dto = new Product();
		
		//�г� ũ�� ����
		p_date.setPreferredSize(new Dimension(250, 100));
		p_east.setPreferredSize(new Dimension(250, 800));
		p_west.setPreferredSize(new Dimension(750, 800));
		p_product.setPreferredSize(new Dimension(750,100)); //�ֹ��Ѱ� �ߴ°� 
		p_sum.setPreferredSize(new Dimension(750, 100));
		p_component.setPreferredSize(new Dimension(750,600));
		p_topMenu.setPreferredSize(new Dimension(250,100));//(����)Ŀ��. ����. �� ������ ��ư
		p_subMenu.setPreferredSize(new Dimension(250,600)); //(����)�ֹ���ư��
		p_pay.setPreferredSize(new Dimension(250,100)); //�����ϱ� ��ư �ִ� �г�
		
		p_sum.setBackground(Color.LIGHT_GRAY);
		p_product.setBackground(Color.LIGHT_GRAY);
		p_component.setBackground(Color.LIGHT_GRAY);		
		p_date.setBackground(Color.WHITE);
		//���� ��ǰ��, ����, ���� �� ���̱� �� ������ư ���հ�
		p_west.setLayout(new FlowLayout());
		p_west.add(p_product);
		p_west.add(p_component);
		p_west.add(p_sum);
		p_sum.add(la_sum_name);
		p_sum.add(la_sum);
		//p_sum.add(bt_allDelete,BorderLayout.WEST);
		p_product.add(la_info);
		
		
		add(p_west);
		p_west.add(scroll);
		scroll.setPreferredSize(new Dimension(750,600));
		
		//���� �޴���ư�� �� �����ϱ� ��ư
		p_east.setLayout(new FlowLayout());
		p_east.add(p_topMenu);
		p_east.add(p_subMenu);
		
		p_east.add(p_date);
		p_date.add(la_date);
		p_east.add(p_pay);
		
		add(p_east,BorderLayout.EAST);
		
		p_pay.add(bt_pay);
		
		//bt_allDelete.addActionListener(this);
		bt_pay.addActionListener(this);

		setSize(1000,900);
		setVisible(true);
		

		thread=new Thread(this);
		thread.start();
		
		init();
		getMenu();
		getSubMenu();

		
		
	}
	//�����ͺ��̽� Ŀ�ؼ� ������
	public void init(){
		manager=DBManager.getInstance();
		con=manager.getConnection();
		System.out.println(con);
	}
	
	//���� ��ư�� ��������
	//DB���� ������ ��ư���� �� ����� ������  DB���̸�ŭ ��ư�� ��������� 
	public void getMenu(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from product_category order by product_category_id asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Product_category vo=new Product_category();
				vo.setProduct_category_id(rs.getInt("product_category_id"));
				vo.setProduct_category_name(rs.getString("product_category_name"));
				
				bigMenu.add(vo); 
				//prduct_category_name ��ŭ ��ư ����
				JButton bt = new JButton(vo.getProduct_category_name());
				bt.setBackground(Color.WHITE);
				bt.setPreferredSize(new Dimension(70, 50));
				bt.addActionListener(this);
				System.out.println(bt.getText());
				p_topMenu.add(bt);
				p_topMenu.updateUI();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
						e.printStackTrace();
				}
			}
		}
	}
	//�����޴��鿡 �ִ� ��ư�� ��������
	public void getSubMenu(){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql="select * from product order by product_id asc";
		
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Product dto=new Product();
				dto.setProduct_category_id(rs.getInt("product_category_id"));
				dto.setProduct_id(rs.getInt("product_id"));
				dto.setProduct_name(rs.getString("product_name"));
				dto.setProduct_price(rs.getInt("product_price"));
				
				product_list.add(dto);

				
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//�����޴��� ��ư�� ������  p_component �� �޴��� �߰��ǰ�
	//��ư ������ �迭�� �ö󰡱�!!!  �����ϱ� �����ϱ�
	public void actionPerformed(ActionEvent e) {
		//p_subMenu.removeAll();
		JButton obj=(JButton)e.getSource();
		//p_subMenu.g
		if(obj==bt_pay){
			pay( );
			
		}else if(obj.getText().equals("coffee") ||obj.getText().equals("drink")||obj.getText().equals("bread") ){
			ShowMenu(obj);
			
		}else{
			for(int i=0;i<product_list.size();i++){
				if(obj.getText().equals(product_list.get(i).getProduct_name())){
					InsertMenu(product_list.get(i));
					break;
					
				}
			}
		}
	}

	//�迭�� �Ƹ޸�ī�� �ø����!!!!!!!!!!!!!!!!!! 
	private void InsertMenu(Product product) {
		
		System.out.println(product.getProduct_name());
		
		//dto.setProduct_category_id(product_category_id);
		
		//product_list.add(product); ?
		
		ProductPanel productPanel = new ProductPanel(product,this);
		menu_list.add(productPanel);
		
		p_component.add(productPanel);
		p_component.updateUI();
		Sum();
	
	}
	
	public void ShowMenu(JButton obj){
		p_subMenu.removeAll();
		for(int i=0;i<bigMenu.size();i++){
			if(obj.getText().equals(bigMenu.get(i).getProduct_category_name())){
				int id=bigMenu.get(i).getProduct_category_id();
				for(int a=0;a<product_list.size();a++){
					if(id==product_list.get(a).getProduct_category_id()){
						JButton bt=new JButton(product_list.get(a).getProduct_name());
						System.out.println("�̰Ŵ����� �� ������");
						bt.addActionListener(this);
						bt.setBackground(Color.WHITE);
						bt.setPreferredSize(new Dimension(200, 50));
						
						p_subMenu.add(bt);
						p_subMenu.updateUI();
						
					}
				}
			}
		}
	}
	
	//�����ϱ� ��ư ������ ����DTO �� ����â�� �ѱ��;�.��......
	public void pay(){
		System.out.println("����â����");
		for(int i=0; i <menu_list.size(); i++){
			int id=menu_list.get(i).id;
			Orders orders=new Orders();
			
			orders.setProduct_id(id);
			orders.setOrders_emp_id(2);
			orders.setOrders_client_id(order_number);
			
			orders_list.add(orders);
			
		}
		new Payment(orders_list, total);
		order_number++;
		
		menu_list.removeAll(menu_list);
		p_component.removeAll();
		p_component.updateUI();
		la_sum.setText("0");
		p_sum.updateUI();
		
		System.out.println(orders_list.size());
		
	}
	
	public void Sum(){
		total=0;
		for(int i=0;i<menu_list.size();i++){
			String sum=menu_list.get(i).la_total.getText();
			total+=Integer.parseInt(sum);
			
		}
		la_sum.setText(Integer.toString(total));
		
	}
	
	public void date(){
			Calendar calendar=Calendar.getInstance();
			Date date=calendar.getTime();
			String today=(new SimpleDateFormat("yyyy-MM-dd HH�� mm�� ss��").format(date));
		
			la_date.setText(today);
			la_date.setFont(new Font("����", Font.BOLD, 15));
			p_date.updateUI();
		
	}
	
	//�ð�
	public void run() {
		while(true){
			date();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}