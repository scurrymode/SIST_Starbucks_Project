package pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import db.DBManager;

public class DataController{
	
	DBManager manager;
	Connection con;
	Vector<Vector> data = new Vector<Vector>();
	Vector<String> columnName = new Vector<String>();
	InsertFrame insertFrame;
	MyPanel myPanel;
	public DataController(MyPanel mypanel) {
		manager = DBManager.getInstance();
		this.con =manager.getConnection();
		this.myPanel =mypanel;
	}
	public AbstractTableModel getDataModel(){
		DataModel  model = new DataModel(data, columnName);
		return model;
	}
	public void getList(String table_Name){
		String sql = "select * from "+table_Name;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try {
			pstmt =  con.prepareStatement(sql);
			rs =pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			for(int i=1;i<=meta.getColumnCount();i++){
				columnName.add(meta.getColumnName(i));
			}
			while(rs.next()){
				Vector vec = new Vector();
				for(int i=0;i<meta.getColumnCount();i++){
					vec.add(rs.getString(i+1));
				}
				data.add(vec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void addEmp(InsertFrame insertFrame){
		this.insertFrame = insertFrame;
		data.removeAll(data);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into emp(emp_login_id,emp_login_pw,emp_name,emp_phone,emp_job,emp_sal)");
		sql.append("values(?,?,?,?,?,?)");
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		String phone=insertFrame.choice.getSelectedItem()+"-"+insertFrame.t_phone1.getText()+"-"+insertFrame.t_phone2.getText();
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			int row =data.size();
			System.out.println(row);
			//System.out.println(myPanel.table.getValueAt(1, 1));
			//for(int i=0;i<row;i++){
				//if(insertFrame.t_id.getText().equals(myPanel.table.getValueAt(i, 1))){
					//JOptionPane.showMessageDialog(myPanel, "�ߺ��� ���̵��Դϴ�.");
				//}
		//	}
			pstmt.setString(1,insertFrame.t_id.getText() );
			pstmt.setString(2,insertFrame.t_pw.getText() );
			pstmt.setString(3,insertFrame.t_name.getText() );
			pstmt.setString(4,phone);
			pstmt.setString(5,insertFrame.choice_job.getSelectedItem() );
			pstmt.setString(6,insertFrame.t_sal.getText() );
			rs = pstmt.executeQuery();
			System.out.println("�����������?");
			getList("emp");
			myPanel.table.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void SearchEmp(){
		String str ="";
		if(myPanel.choice.getSelectedItem().equals("�̸�")){
			str = "emp_name";
		}else{
			str = "emp_login_id";
		}
		String sql="select * from emp where "+str+" = ?";
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		System.out.println(sql);
		System.out.println(myPanel.t_search.getText());
		data.removeAll(data);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, myPanel.t_search.getText());
			rs =pstmt.executeQuery();
			while(rs.next()){
				Vector vec = new Vector();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));
				vec.add(rs.getString(7));
				
				data.add(vec);
			}
			myPanel.table.setModel(getDataModel());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void SearchMember(){
		String str ="";
		System.out.println(myPanel.choice.getSelectedItem());
		if(myPanel.choice.getSelectedItem().equals("�̸�")){
			str = "member_name";
		}else{
			str = "member_login_id";
		}
		String sql="select * from member where "+str+" = ?";
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		System.out.println(sql);
		System.out.println(myPanel.t_search.getText());
		data.removeAll(data);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, myPanel.t_search.getText());
			rs =pstmt.executeQuery();
			while(rs.next()){
				Vector vec = new Vector();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));
				vec.add(rs.getString(7));
				vec.add(rs.getString(8));
				vec.add(rs.getString(9));
				
				data.add(vec);
			}
			myPanel.table.setModel(getDataModel());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void editTable(DataModel myModel){
		
	}
}
