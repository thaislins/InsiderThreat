package br.imd.profile;

import java.util.HashMap;

import br.imd.filedata.LDAP;

public class UserProfile extends LDAP {

	private HashMap<String, PC> devices;

	public UserProfile(String employee_name, String user_id, String email, String domain, String role) {
		super(employee_name, user_id, email, domain, role);
		devices = new HashMap<>();
	}

	public HashMap<String, PC> getDevices() {
		return devices;
	}

	public void setDevices(HashMap<String, PC> devices) {
		this.devices = devices;
	}
}
