package utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
			return Class.forName(value).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	public static void main(String[] args) {
		System.out.println(getBean("ProductDao"));
	}
	
}
