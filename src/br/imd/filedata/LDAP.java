package br.imd.filedata;

public class LDAP extends Data {

	private String employee_name;
	private String user_id;
	private String email;
	private String domain;
	private String role;

	public LDAP(String employee_name, String user_id, String email, String domain, String role) {
		this.employee_name = employee_name;
		this.user_id = user_id;
		this.email = email;
		this.domain = domain;
		this.role = role;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
