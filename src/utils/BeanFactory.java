package utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 实体工厂类
 * @author wzw
 *
 */
public class BeanFactory {
	public static Object getBean(String id) {
		// 通过id获取一个指定的实现类
		try {
			// 1.获取document对象
			Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			// 2.获取指定的bean
			Element ele = (Element) doc.selectSingleNode("//bean[@id='" + id + "']");
			// 3.获取bean对象的class属性
			String value = ele.attributeValue("class");
			// 4.反射
			//return Class.forName(value).newInstance();
			//现在对service中add方法进行加强
			Object obj = Class.forName(value).newInstance();
			//是service的实现类
			if(id.endsWith("Service")){
				Object proxyObj = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//继续判断是否调用的是add或者regist
						if("add".equals(method.getName())||"regist".equals(method.getName())){
							System.out.println("添加操作");
							return method.invoke(obj, args);
						}
						return method.invoke(obj, args);
					}
				});
				
				//若是service方法 返回的是代理对象
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
