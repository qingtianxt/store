package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import service.UserService;
import utils.MailUtils;

public class UserServiceImpl implements UserService{
	/**
	 * �û�ע��
	 * @throws Exception 
	 */
	@Override
	public void regist(User user) throws Exception {
		UserDao dao = new UserDaoImpl();
		dao.add(user);
		
		//�����ʼ�
		//email�ռ��˵�ַ
		//emailMsg���ʼ�������
		String emailMsg="��ӭ��ע���Ϊ���ǵ�һԱ��<a href='http://localhost:8080/store/user?method=active&code="+user.getCode()+"'>��˼���</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}
/**
 * �û�����
 * @throws Exception 
 */
	@Override
	public User active(String code) throws Exception {
		UserDao dao = new UserDaoImpl();
		
		//1.ͨ��code��ȡһ���û�
		User user =dao.getByCode(code);
		//2.�ж��û��Ƿ�Ϊ��
		if(user==null){
			//ͨ��������û���ҵ��û�
			return null;
		}
		//3.�޸��û�״̬
		//���û�״̬����Ϊ1
		user.setState(1);
		dao.update(user);
		return user;
	}
	/**
	 * �û���¼
	 */
@Override
public User login(String username, String password) throws Exception {
	// TODO �û���¼//Ҳ������ctrl+o
	UserDao dao = new UserDaoImpl();
	return dao.getByUsernameAndPwd(username,password);
	
}

}
