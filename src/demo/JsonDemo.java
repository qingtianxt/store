package demo;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import utils.JsonUtil;

public class JsonDemo {
	public static void main(String[] args) {
		Person p = new Person("qwe","tom","123");
		//不包含那些内容
		JsonConfig config = JsonUtil.configJson(new String[]{"password","class"});
		JSONObject json = JSONObject.fromObject(p, config);
//		String j =JsonUtil.object2json(p);
		System.out.println(json);
	}
}
