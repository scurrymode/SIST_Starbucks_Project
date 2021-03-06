package order.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.media.jfxmedia.MediaError;

import db.DBManager;

import javafx.collections.FXCollections;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import order.payment.Payment;
import pos.login.PosWindow;

public class OrderMain extends JPanel implements ActionListener,Runnable{
	Connection con;
	DBManager manager;
	Thread thread;
	MusicThread music;
	
	//	p_east 전체화면 동쪽, p_west 전체화면 서쪽 p_product 주문한거뜨는 곳,p_topMenu메뉴 상위 버튼들 있는곳 p_subMenu메뉴 하위버튼들 p_pay 결제하기버튼 있는 곳 

	JPanel p_pos,p_product,p_component,p_topMenu,p_subMenu, p_sum,p_pay , p_east, p_west ,p_date,p_music ,p_etc ,p_con ,p_list;
	JButton bt_pay, bt_allDelete,bt_stop,bt_play, bt_list, bt_prev, bt_next,bt_reservation, bt_reservation_show ,bt_income , bt_stock ;
	JScrollPane scroll;
	
	JLabel la_sum_name,la_sum,la_info ,la_date;
	Vector<Product_category> bigMenu=new Vector<Product_category>();//상위 메뉴들 들어있음(커피,쥬스.빵)
	Vector <Product> product_list=new Vector<Product>();	 //product 하위 메뉴들의 정보가 들어있음 
	Vector<ProductPanel> menu_list=new Vector<ProductPanel>();
	Vector<Orders> orders_list=new Vector<Orders>();
	PosWindow posWindow;
	int total;
	int order_number=1;
<<<<<<< HEAD
=======

	JButton obj;
	private Iterator<String> songIterator;

>>>>>>> c4729145ac314e006448baee969974d467927758

	public OrderMain(PosWindow posWindow) {
		this.posWindow =posWindow;
		p_date=new JPanel();
		p_east=new JPanel();
		p_west=new JPanel();
		p_pos=new JPanel();
		p_product=new JPanel();
		p_component=new JPanel();
		p_sum=new JPanel();
		p_pay=new JPanel();

		p_topMenu=new JPanel();
		p_subMenu=new JPanel(); 
		p_etc=new JPanel();
		p_con=new JPanel();
		p_date=new JPanel();
		p_music=new JPanel();
	
		scroll=new JScrollPane(p_component);
		
		bt_allDelete=new JButton("전체삭제");
		bt_pay=new JButton("결제하기");
		bt_allDelete.setBackground(Color.WHITE);
		bt_pay.setBackground(Color.WHITE);
		bt_allDelete.setPreferredSize(new Dimension(150, 50));
		bt_pay.setPreferredSize(new Dimension(150, 50));
		
		bt_list=new JButton("목록");
		bt_next=new JButton("▶▶");
		bt_prev=new JButton("◀◀");
		bt_play=new JButton("▶");
		bt_stop=new JButton("||");
		
		bt_reservation=new JButton("예약하기");
		bt_reservation_show=new JButton("예약현황");
		bt_income=new JButton("매출현황");
		bt_stock=new JButton("재고현황");
		
		bt_reservation.setBackground(Color.WHITE);
		bt_reservation.setPreferredSize(new Dimension(150, 50));
		
		bt_reservation_show.setBackground(new Color(183, 30, 30));
		bt_reservation_show.setPreferredSize(new Dimension(150, 50));
		
		bt_income.setBackground(Color.WHITE);
		bt_income.setPreferredSize(new Dimension(150, 50));
		
		bt_stock.setBackground(Color.WHITE);
		bt_stock.setPreferredSize(new Dimension(150, 50));
		
		
		la_date=new JLabel("시간");
		la_sum_name=new JLabel("합계:");
		la_sum=new JLabel("0");
		la_sum_name.setFont(new Font("돋움", Font.BOLD, 30));
		la_sum.setFont(new Font("돋움", Font.BOLD, 30));
		
		
		la_info=new JLabel("제품명  수량   금액");
		la_info.setFont(new Font("돋움", Font.BOLD , 30 ));
		

		Product dto = new Product();
		
		//패널 크기 지정
		p_east.setPreferredSize(new Dimension(800, 800));
		p_west.setPreferredSize(new Dimension(400, 800));
		p_date.setPreferredSize(new Dimension(400, 50));
		p_pos.setPreferredSize(new Dimension(400, 150));
		p_product.setPreferredSize(new Dimension(400,50)); //주문한거 뜨는곳 
		p_sum.setPreferredSize(new Dimension(400, 100));
		p_component.setPreferredSize(new Dimension(400,350));
		p_topMenu.setPreferredSize(new Dimension(800,70));//(상위)커피. 음료. 빵 세개의 버튼
		p_subMenu.setPreferredSize(new Dimension(800,600)); //(하위)주문버튼들
		p_music.setPreferredSize(new Dimension(400, 50));
		p_pay.setPreferredSize(new Dimension(400,150)); //결제하기 버튼 있는 패널
		p_etc.setPreferredSize(new Dimension(800, 80));
		p_con.setPreferredSize(new Dimension(800, 50));
		
		//p_west 패널에 p_pos, p_product , p_component,p_sum, p_pay
		p_pos.setBackground(Color.LIGHT_GRAY);
		p_product.setBackground(Color.LIGHT_GRAY);
		p_component.setBackground(Color.LIGHT_GRAY);		
		p_sum.setBackground(Color.LIGHT_GRAY);
		p_pay.setBackground(Color.LIGHT_GRAY);
	
		
		//p_east 패널에 p_topMenu,p_subMenu,p_etc,p_date , p_music

		//서쪽 버튼 누르면 p_component 에 생성됨
		p_west.setLayout(new FlowLayout());
		p_west.add(p_pos);
		p_west.add(p_product);
		p_product.add(la_info);
		p_west.add(p_component);
		p_west.add(p_sum);
		p_sum.add(la_sum_name);
		p_sum.add(la_sum);
		//p_sum.add(bt_allDelete,BorderLayout.WEST);
	
		p_west.add(p_pay);
		p_pay.add(bt_allDelete);
		p_pay.add(bt_pay);
		
		add(p_west,BorderLayout.CENTER);
		//p_west.add(scroll);
		//scroll.setPreferredSize(new Dimension(750,600));
		
		//동쪽 메뉴버튼들  , 시간이랑 음악플레이버튼 
		p_east.setLayout(new FlowLayout());
		p_east.add(p_topMenu);
		p_east.add(p_subMenu);
		p_east.add(p_etc);
		p_etc.add(bt_reservation);
		p_etc.add(bt_reservation_show);
		p_etc.add(bt_income);
		p_etc.add(bt_stock);
		p_east.add(p_con);
		
		p_con.setLayout(new GridLayout(1,2));
		p_con.add(p_music);
		p_con.add(p_date);
		
		add(p_east,BorderLayout.EAST);
		p_date.add(la_date);
		
		p_music.add(bt_prev);
		
		p_music.add(bt_stop);
		p_music.add(bt_play);
		p_music.add(bt_next);
		p_music.add(bt_list);
		//add(p_east,BorderLayout.EAST);
		
		bt_allDelete.addActionListener(this);
		bt_pay.addActionListener(this);
		bt_play.addActionListener(this);
		bt_stop.addActionListener(this);
		bt_list.addActionListener(this);
		bt_next.addActionListener(this);
		
		setSize(1200,850);
		setVisible(true);
		

		thread=new Thread(this);
		thread.start();
	
		init();
		getMenu();
		getSubMenu();

	}
	//데이터베이스 커넥션 얻어오기
	public void init(){
		manager=DBManager.getInstance();
		con=manager.getConnection();
		System.out.println(con);
	}
	
	//상위 버튼들 가져오기
	//DB에서 가져온 버튼들을 다 만들고 누르면  DB길이만큼 버튼들 만들어지게 
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
				//prduct_category_name 만큼 버튼 생성
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
	//하위메뉴들에 있는 버튼들 가져오기
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
	//하위메뉴들 버튼들 누르면  p_component 에 메뉴가 추가되게
	//버튼 누르면 배열에 올라가기!!!  결제하기 삭제하기
	public void actionPerformed(ActionEvent e) {
		//p_subMenu.removeAll();
		obj=(JButton)e.getSource();
		//p_subMenu.g
		if(obj==bt_pay){ //결체버튼
			pay( );
			
		}else if(obj==bt_allDelete){//전체삭제
			allDelete();
			
		}else if(obj.getText().equals("coffee") ||obj.getText().equals("drink")||obj.getText().equals("bread") ){ 
			ShowMenu(obj);
			
		}else if(obj==bt_play){//음악재생
			musicStart();
		
		}else if(obj==bt_stop){//음악멈추기
			musicStop();
		
		}else if(obj==bt_next){
			nextMusic();
			
		}else if(obj==bt_list){//MP3 목록
			//musicList();
			
		}else{ //메뉴 넣기
			for(int i=0;i<product_list.size();i++){
				if(obj.getText().equals(product_list.get(i).getProduct_name())){
					InsertMenu(product_list.get(i));
					break;
					
				}
			}
		}
	}

	//배열에 아메리카노 올리라고!!!!!!!!!!!!!!!!!! 하나 추가되면서 합계실행
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
	
	//Topcategory 인 커피, 주스, 빵 버튼 생성하기 
	public void ShowMenu(JButton obj){
		p_subMenu.removeAll();
		for(int i=0;i<bigMenu.size();i++){
			if(obj.getText().equals(bigMenu.get(i).getProduct_category_name())){
				int id=bigMenu.get(i).getProduct_category_id();
				for(int a=0;a<product_list.size();a++){
					if(id==product_list.get(a).getProduct_category_id()){
						JButton bt=new JButton(product_list.get(a).getProduct_name());
						System.out.println("이거누르면 또 생성데");
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
	
	//전체삭제 버튼 누르면 메뉴 전체삭제
	public void allDelete(){
		int ans=JOptionPane.showConfirmDialog(this, "전체삭제?");
		if(ans==JOptionPane.OK_OPTION){
			total=0;
			menu_list.removeAll(menu_list);
			p_component.removeAll();
			p_component.updateUI();
			la_sum.setText("0");
			
		}
	}
	
	public void Sum(){
		
		int s=0;
		System.out.println(s);
		for(int i=0;i<menu_list.size();i++){
			String sum=menu_list.get(i).la_total.getText();
			s+=Integer.parseInt(sum);
			
		}
		la_sum.setText(Integer.toString(s));
		System.out.println(s);
		total=s;
	}
	
	//결제하기 버튼 누르면 정보DTO 를 결제창을 넘기고싶어.ㅇ......
	public void pay(){
		System.out.println("결제창으로");
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
	
	
	public void date(){
			Calendar calendar=Calendar.getInstance();
			Date date=calendar.getTime();
			String today=(new SimpleDateFormat("yyyy-MM-dd HH: mm: ss").format(date));
		
			la_date.setText(today);
			la_date.setFont(new Font("돋움", Font.BOLD, 15));
			p_date.updateUI();
		
			
	}
	
	public void musicStart(){
		System.out.println("재생");
		music=new MusicThread();
		music.run();
		
		music.start();
		
	}
	public void musicStop(){
		System.out.println("멈춰라");
		music.mediaPlayer.pause();
		
		
	}
	
	public void nextMusic(){
		
		
	}
	//public void musicList(){
//		System.out.println("목록가져와");
	//	File file=new File("C:/myserver/data");		

/*		//출력할 파일 경로
		try {
			BufferedWriter buffr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			ArrayList list=new ArrayList();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
	//}
	
	//시간
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