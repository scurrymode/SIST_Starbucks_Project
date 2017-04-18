package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import db.DBManager;
import dto.Orders;
import goods.GoodsMain;

public class ServerThread extends Thread {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	DBManager manager = DBManager.getInstance();
	Connection con;
	JSONObject obj;

	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 주문 쿼리 날리기!
	public void sendQuery() {
		con = manager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"insert into orders (product_id, orders_date, orders_emp_id, orders_client_id, orders_status, orders_payment_type, orders_type)");
		sb.append(" values(?,current_timestamp(),?,?,?,?,?)");

		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sb.toString());
			System.out.println(obj.get("product_id"));
			pstmt.setInt(1, Long.valueOf((long) obj.get("product_id")).intValue());
			pstmt.setInt(2, Long.valueOf((long) obj.get("orders_emp_id")).intValue());
			pstmt.setInt(3, Long.valueOf((long) obj.get("orders_client_id")).intValue());
			pstmt.setString(4, (String) obj.get("orders_status"));
			pstmt.setString(5, (String) obj.get("orders_payment_type"));
			pstmt.setString(6, (String) obj.get("orders_type"));

			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("쿼리 성공");
				send();
				System.out.println("결과 전송");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void listen() {
		try {
			String data = buffr.readLine();
			// 제이슨파싱
			JSONParser parser = new JSONParser();
			obj = (JSONObject) parser.parse(data);
			String requestType = (String) obj.get("requestType");
			// 타입이 오더라면, 주문 쿼리 넣어줘야 한다.
			if (requestType.equals("order")) {
				sendQuery();
			}
			// 쿼리문 한번 날리고 한번 쉬자!
			Thread.sleep(100);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void send() {
		String str = "주문완료";
		try {
			buffw.write(str + "\n");
			buffw.flush();
			System.out.println("보냄");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 여기서 이제 서버가 처리해줄 껄 다 작성해줘야한다.
	public void run() {
		while(true){
			listen();
		}

	}

}
