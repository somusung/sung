package min.member.dto;

public class MemberDTO {

	private String id;
	private String name;
	private String password;
	private String gender;
	private int age;
	private String birthday;
	private String email;
	private int num;
	private String joinday;

	public String getId( ) {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName( ) {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword( ) {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender( ) {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge( ) {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBirthday( ) {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail( ) {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNum( ) {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getJoinday( ) {
		return joinday;
	}

	public void setJoinday(String joinday) {
		this.joinday = joinday;
	}

	@Override
	public String toString( ) {
		return "MemberDTO [id=" + id + ", name=" + name + ", password=" + password + ", gender=" + gender + ", age=" + age
		  + ", birthday=" + birthday + ", email=" + email + ", num=" + num + ", joinday=" + joinday + "]";
	}

}