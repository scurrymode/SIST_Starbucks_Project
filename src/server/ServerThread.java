package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			
			String data=buffr.readLine();
			System.out.println(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//���⼭ ���� ������ ó������ �� �� �ۼ�������Ѵ�.
	public void run() {
		try {
			String data = buffr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
