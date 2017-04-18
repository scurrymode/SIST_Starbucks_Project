package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*1.������ �Ѱ��� �α�
 * ������ ���̽� ���������� �ߺ��ؼ� �����ϱ� �ʱ� ����(db������ �ϴ� ������ Ŭ��������...)
 * 
 * 2. �ν��Ͻ��� ������ �Ѱ��� �ֺ���
 * ���ø����̼� ������ �����Ǵ� connection ��ü�� �ϳ��� �����ϱ� ����
 * 
*/
public class DBManager {

	private static DBManager instance;
	private String driver="org.mariadb.jdbc.Driver";
	private String url="jdbc:mariadb://211.238.142.120:3306/starbucks";
	//private String url="jdbc:mariadb://211.238.142.117:3306/starbucks";
	private String user="root";
	private String password="1234";
	private Connection con;
	
	private DBManager() {
		/*
		 * 1.����̹� �ε�
		 * 2.����
		 * 3.������ ����
		 * 4.�ݳ�,����
		 * */
		try {
			Class.forName(driver);
			con =DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	public static DBManager getInstance() {
		if(instance==null){
			instance =new DBManager();
		}
		return instance;
	}
	public Connection getConnection(){
		return con;
	}
	public void disConnection(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
}