package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import board.BoardMain;
import card.CardListMain;
import db.DBManager;
import dto.Member;
import dto.Product;
import member.MemberWindow;

public class ClientMain extends JPanel implements ActionListener{
	//메인 화면~!
	JPanel p_page = new JPanel(); //페이지들 담아둔거
	JPanel p_main = new JPanel(); //홈버튼 들어있는 거
	JButton bt_home= new JButton("홈으로");
	
	JPanel[] pageList = new JPanel[5];
	
	
	//클라이언트 화면
	JPanel p_center;
	JButton bt_orders, bt_myPage, bt_event, bt_card;

	//아예 처음 킬때 회원정보랑 싹 다 가져올거다!
	DBManager manager = DBManager.getInstance();
	Connection con;
	public String login_id;
	public Member member;
	Vector<Product> product_list = new Vector<Product>();
	ClientOrders orders; //주문창
	
	
	public ClientMain(MemberWindow memberWindow) {

		this.login_id=memberWindow.id;
		this.setLayout(new BorderLayout());
		
		p_center = new JPanel();
		bt_orders = new JButton("주문");
		bt_myPage = new JButton("마이페이지");
		bt_event = new JButton("이벤트");
		bt_card = new JButton("결제수단");
		
		//버튼 크기 키우기
		bt_orders.setPreferredSize(new Dimension(280, 350));
		bt_myPage.setPreferredSize(new Dimension(280, 350));
		bt_event.setPreferredSize(new Dimension(280, 350));
		bt_card.setPreferredSize(new Dimension(280, 350));
		
		p_center.setLayout(new GridLayout(2, 2));	
		p_center.add(bt_orders);
		p_center.add(bt_myPage);
		p_center.add(bt_event);
		p_center.add(bt_card);
		
		//리스너 연결
		bt_orders.addActionListener(this);
		bt_event.addActionListener(this);
		bt_myPage.addActionListener(this);
		bt_card.addActionListener(this);
		bt_home.addActionListener(this);
		
		
		p_main.setLayout(new BorderLayout());
		p_page.add(p_center);
		p_main.add(p_page);
		add(bt_home, BorderLayout.NORTH);
		
		add(p_main);
		
		//각종 데이터 다 가져오기(상품, 회원)
		getData();
		
		
		setPreferredSize(new Dimension(300*2, 400*2));
		
		//모든 페이지 일단 다 만들기~!
		init();
	}
	
		
	public void getData(){
		con = manager.getConnection();
		getMember();
		getProduct();
	}
	
	public void getMember(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql ="select * from member where member_login_id='"+login_id+"'";
		
		System.out.println(sql);
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			rs.next();
			
			member = new Member();
			member.setMember_id(rs.getInt(1));
			member.setMember_login_id(rs.getString(2));
			member.setMember_login_pw(rs.getString(3));
			member.setMember_name(rs.getString(4));
			member.setMember_nickname(rs.getString(5));
			member.setMember_gender(rs.getString(6));
			member.setMember_phone(rs.getString(7));
			member.setMember_birth(rs.getString(8));
			member.setMember_coupon(rs.getString(9));
			
			System.out.println(member.getMember_nickname());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void getProduct(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="select * from product";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Product dto = new Product();
				dto.setProduct_id(rs.getInt(1));
				dto.setProduct_category_id(rs.getInt(2));
				dto.setProduct_name(rs.getString(3));
				dto.setProduct_price(rs.getInt(4));
				
				product_list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
		
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_orders){
			setPage(1);
		}else if(obj==bt_event){
			setPage(2);
		}else if(obj==bt_myPage){
			setPage(3);
		} else if(obj==bt_card) {
			setPage(4);
		} else if(obj==bt_home) {
			setPage(0);
		}
	}
	
	public void init(){
		orders=new ClientOrders(this);
		BoardMain board=new BoardMain();	
		ClientEdit clientEdit =new ClientEdit(this);
		CardListMain card = new CardListMain(this);
		
		pageList[0]=p_center;
		pageList[1]=orders;
		pageList[2]=board;
		pageList[3]=clientEdit;
		pageList[4]=card;
		
		for(int i=1;i<5;i++){
			//넣기
			p_page.add(pageList[i]);
			pageList[i].setVisible(false);
		}
		
	}
	public void setPage(int num){
		for(int i=0;i<pageList.length;i++){
			pageList[i].setVisible(false);
		}
		pageList[num].setVisible(true);
		
	}

}
