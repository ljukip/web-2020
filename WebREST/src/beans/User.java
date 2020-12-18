package beans;

public class User {

	public enum Role {
		GUEST, HOST, ADMIN
	}

	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private Role role;
	private String jwt;
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, String name, String surname, String gender, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.role = role;
		//this.jwt = jwt;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
