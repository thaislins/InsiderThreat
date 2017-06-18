package br.imd.filedata;

/**
 * Classe responsável pelo tipo de arquivo LDAP
 */
public class LDAP extends Data {

	private String employee_name;
	private String user_id;
	private String email;
	private String domain;
	private String role;

	/**
	 * Construtor parametizado
	 */
	public LDAP(String employee_name, String user_id, String email, String domain, String role) {
		this.employee_name = employee_name;
		this.user_id = user_id;
		this.email = email;
		this.domain = domain;
		this.role = role;
	}

	/**
	 * @return employee_name
	 */
	public String getEmployee_name() {
		return employee_name;
	}

	/**
	 * @param employee_name
	 */
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	/**
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
