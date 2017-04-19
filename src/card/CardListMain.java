package card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.DBManager;
import dto.Card;

public class CardListMain extends JFrame implements ActionListener{
	DBManager manager = DBManager.getInstance();
	Connection con;
	Vector<Card> card_list = new Vector<Card>();
	Vector<JPanel> panel_list = new Vector<JPanel>();
	JPanel p_north, p_center;
	JButton bt;
	JLabel la_member;
	String login_id;

	public CardListMain(String login_id) {
		con = manager.getConnection();
		this.login_id = login_id;
		
		p_north = new JPanel();
		p_center = new JPanel();
		la_member = new JLabel(login_id + "님 안녕하세요.");
		bt = new JButton("카드 등록");
		
		la_member.setPreferredSize(new Dimension(300, 50));
		la_member.setFont(new Font("돋움", Font.ITALIC, 15));
		p_center.setBackground(Color.pink);
		
		p_north.add(la_member);
		p_north.add(bt);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		bt.addActionListener(this);
		
		setSize(500, 700);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		/*
		 * 패널안에 카드 회사, 유저네임 출력 
		 * --> 카드를 누르면 기존의 창에서 화면 전환 setVisible로?
		 * 	   카드 넘버, 유저네임, 유효기간, 회사이름다 나오는 거 패널 하나(.java로) 만들기 
		*/
		addPanel();
	}
	
	public void getList() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from card where member_id = 2";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Card dto = new Card();
				dto.setCard_id(rs.getInt("card_id"));
				dto.setMember_id(rs.getInt("member_id"));
				dto.setCard_number(rs.getString("card_number"));
				dto.setCard_username(rs.getString("card_username"));
				dto.setCard_valid(rs.getString("card_valid"));
				dto.setCard_companyname(rs.getString("card_companyname"));
				
				card_list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	public void addPanel() {
		try {
			URL url = new URL("http://211.238.142.120:9090/data/card.png");
			ImageIcon icon = new ImageIcon(url);
			for(int i = 0; i < card_list.size(); i++) {
				JPanel panel = new JPanel() {
					protected void paintComponent(Graphics g) {
						g.drawImage(icon.getImage(), 0, 0, 350, 200, this);
						setOpaque(false);
					}
				};
				
				JLabel la_num = new JLabel(card_list.get(i).getCard_number());
				JLabel la_user = new JLabel(card_list.get(i).getCard_username());
				JLabel la_company = new JLabel(card_list.get(i).getCard_companyname());
				la_num.setPreferredSize(new Dimension(350, 50));
				la_user.setPreferredSize(new Dimension(350, 50));
				la_company.setPreferredSize(new Dimension(350, 50));
				
				panel.add(la_company);
				panel.add(la_num);
				panel.add(la_user);
				
				p_center.add(panel);
				p_center.updateUI();
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		new CardCompanyMain();
	}

}
