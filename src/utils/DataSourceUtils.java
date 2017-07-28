package utils;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	//��ȡ����Դ
	private  static ComboPooledDataSource ds=new ComboPooledDataSource();
	private static ThreadLocal<Connection>tl = new ThreadLocal<>();
	/**
	 * ��ȡ����Դ
	 * @return
	 */
	public static DataSource getDataSource(){
		
		
		return ds;
	}
	/**
	 * �ӵ�ǰ�̻߳�ȡ����
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();
		if(conn == null){
			//��һ�λ�ȡ������һ�����ӣ��͵�ǰ���̰߳�
			conn = ds.getConnection();
			tl.set(conn);
		}
		
		return conn;
	}
	//�ͷ���Դ
	/**
	 * �ͷ�����
	 * @param conn ����
	 * */
	public static void closeConn(Connection conn)
	{
		if(conn!=null)
		{
			try{
				conn.close();
				//�͵�ǰ���߳̽��
				tl.remove();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			conn=null;
		}
	}
	/**
	 * �ͷ�����
	 * @param st ����
	 * */
	public static void closeStatement(Statement st)
	{
		if(st!=null)
		{
			try{
				st.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			st=null;
		}
	}
	/**
	 * �ͷ�����
	 * param rs����� ����
	 * */
	public static void closeResultSet(ResultSet rs)
	{
		if(rs!=null)
		{
			try{
				rs.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			rs=null;
		}
	}
	/**
	 * ��������
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException{
		//��ȡ���� ����������
		getConnection().setAutoCommit(false);
	}
	/**
	 * �����ύ
	 */
	public static void commitAndClose(){
		//��ȡ����
		try {
			Connection conn = getConnection();
			//�ύ����
			conn.commit();
			
			//�ͷ���Դ
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	/**
	 * ����ع�
	 */
	public static void rollbackAndClose(){
		//��ȡ����
		try {
			Connection conn = getConnection();
			//����ع�
			conn.rollback();
			
			//�ͷ���Դ
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void closeResourse(Connection conn,Statement st,ResultSet rs){
		closeResourse(st,rs);
		closeConn(conn);
	}
	public static void closeResourse(Statement st,ResultSet rs){
		closeResultSet(rs);
		closeStatement(st);
	}
	public static void main(String[] args) throws SQLException {
		getConnection();
		System.out.println(1);
	}
}
