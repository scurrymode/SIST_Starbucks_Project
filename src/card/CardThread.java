package card;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import dto.Card;
import json.CardProtocol;

public class CardThread extends Thread{
	Card card;
	CardInputMain main;
	
	Socket socket;
	int port = 7777;
	//String host = "127.0.0.1";
	String host = "211.238.142.120";
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public CardThread(Card card, CardInputMain main) {
		this.card = card;
		this.main = main;
		
		try {
			socket = new Socket(host, port);
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		send();
	}
	
	public void run() {
		while(true) {
			listen();
		}
	}
	
	public void listen() {
		try {
			String data = buffr.readLine();
			
			if(data.equals("ī���ϿϷ�")){
				JOptionPane.showMessageDialog(main, "ī�� ��� ����!");
				main.dispose();
				main.main.dispose();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		CardProtocol protocol = new CardProtocol(card);
		String msg = protocol.getProtocol();
		
		try {
			buffw.write(msg+"\n");
			buffw.flush();
			System.out.println(msg + " ī�� ���");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
