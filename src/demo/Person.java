package demo;

public class Person {
	private String id;
	private String name;
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Person(String id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}
	private String password;
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
