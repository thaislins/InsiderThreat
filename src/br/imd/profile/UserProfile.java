package br.imd.profile;

import java.util.Date;
import java.util.HashMap;

import br.imd.filedata.LDAP;

public class UserProfile extends LDAP {

	/*
	 * HashMap que armazena os dispositivos de acordo com o Id do Usuário
	 */
	private HashMap<String, PC> devices;
	private boolean filtered;
	private String filteredDate;

	public UserProfile(String employee_name, String user_id, String email, String domain, String role) {
		super(employee_name, user_id, email, domain, role);
		devices = new HashMap<>();
		filtered = false;
		filteredDate = null;
	}

	public boolean isFiltered() {
		return filtered;
	}

	public void setFiltered(boolean filtered) {
		this.filtered = filtered;
	}

	public HashMap<String, PC> getDevices() {
		return devices;
	}

	public void setDevices(HashMap<String, PC> devices) {
		this.devices = devices;
	}

	public String getFilteredDate() {
		return filteredDate;
	}

	public void setFilteredDate(String filteredDate) {
		this.filteredDate = filteredDate;
	}

	public void dateString(Date date1, Date date2) {
		filteredDate = date1.toString() + " to " + date2.toString();
	}
}
