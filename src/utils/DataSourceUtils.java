package utils;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	//获取数据源
	private  static ComboPooledDataSource ds=new ComboPooledDataSource();
	private static ThreadLocal<Connection>tl = new ThreadLocal<>();
	/**
	 * 获取数据源
	 * @return
	 */
	public static DataSource getDataSource(){
		
		
		return ds;
	}
	/**
	 * 从当前线程获取连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();
		if(conn == null){
			//第一次获取，创建一个连接，和当前的线程绑定
			conn = ds.getConnection();
			tl.set(conn);
		}
		
		return conn;
	}
	//释放资源
	/**
	 * 释放链接
	 * @param conn 连接
	 * */
	public static void closeConn(Connection conn)
	{
		if(conn!=null)
		{
			try{
				conn.close();
				//和当前的线程解绑
				tl.remove();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			conn=null;
		}
	}
	/**
	 * 释放链接
	 * @param st 连接
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
	 * 释放链接
	 * param rs结果集 连接
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
	 * 开启事务
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException{
		//获取连接 ，开启事务
		getConnection().setAutoCommit(false);
	}
	/**
	 * 事务提交
	 */
	public static void commitAndClose(){
		//获取连接
		try {
			Connection conn = getConnection();
			//提交事务
			conn.commit();
			
			//释放资源
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	/**
	 * 事务回滚
	 */
	public static void rollbackAndClose(){
		//获取连接
		try {
			Connection conn = getConnection();
			//事务回滚
			conn.rollback();
			
			//释放资源
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
