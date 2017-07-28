package dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import dao.UserDao;
import domain.User;
import utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {
	/**
	 * �û�ע��
	 * 
	 * @throws SQLException
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode());
	}

	/**
	 * ' ͨ���������ȡһ���û�
	 */
	@Override
	public User getByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";

		return qr.query(sql, new BeanHandler<>(User.class), code);
	}

	/**
	 * �޸��û�
	 */
	@Override
	public void update(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "update user set username=?,password=?,name=?,email=?,birthday=?,state=?,code=? where uid=?";
		qr.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getBirthday(),
				user.getState(), null, user.getUid());

	}

	/**
	 * �û���¼
	 */
	@Override
	public User getByUsernameAndPwd(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new  QueryRunner(DataSourceUtils.getDataSource());
		String sql ="select * from user where username =? and password=? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class),username,password);
	}

}
