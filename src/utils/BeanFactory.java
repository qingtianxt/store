package utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * ʵ�幤����
 * @author wzw
 *
 */
public class BeanFactory {
	public static Object getBean(String id) {
		// ͨ��id��ȡһ��ָ����ʵ����
		try {
			// 1.��ȡdocument����
			Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			// 2.��ȡָ����bean
			Element ele = (Element) doc.selectSingleNode("//bean[@id='" + id + "']");
			// 3.��ȡbean�����class����
			String value = ele.attributeValue("class");
			// 4.����
			//return Class.forName(value).newInstance();
			//���ڶ�service��add�������м�ǿ
			Object obj = Class.forName(value).newInstance();
			//��service��ʵ����
			if(id.endsWith("Service")){
				Object proxyObj = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//�����ж��Ƿ���õ���add����regist
						if("add".equals(method.getName())||"regist".equals(method.getName())){
							System.out.println("��Ӳ���");
							return method.invoke(obj, args);
						}
						return method.invoke(obj, args);
					}
				});
				
				//����service���� ���ص��Ǵ������
				return proxyObj;
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	public static void main(String[] args) {
		System.out.println(getBean("ProductDao"));
	}
	
}
