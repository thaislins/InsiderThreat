package br.imd.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import br.imd.filedata.LDAP;

/**
 * Classe responsável pelos usurios
 */

public class UserProfile extends LDAP {

	/**
	 * HashMap que armazena os dispositivos de acordo com o Id do Usuário
	 */
	private HashMap<String, PC> devices;
	private boolean filtered;
	private String filteredDate;
	private ArrayList<Integer> arrActCount;
	private double average;

	public UserProfile(String employee_name, String user_id, String email, String domain, String role) {
		super(employee_name, user_id, email, domain, role);
		devices = new HashMap<>();
		filtered = false;
		filteredDate = null;
		arrActCount = new ArrayList<>(Collections.nCopies(24, 0));
	}

	/**
	 * @return filtered
	 */
	public boolean isFiltered() {
		return filtered;
	}

	/**
	 * @param filtered
	 */
	public void setFiltered(boolean filtered) {
		this.filtered = filtered;
	}

	/**
	 * @return devices
	 */
	public HashMap<String, PC> getDevices() {
		return devices;
	}

	/**
	 * @param devices
	 */
	public void setDevices(HashMap<String, PC> devices) {
		this.devices = devices;
	}

	/**
	 * @return filteredDate
	 */
	public String getFilteredDate() {
		return filteredDate;
	}

	/**
	 * @param filteredDate
	 */
	public void setFilteredDate(String filteredDate) {
		this.filteredDate = filteredDate;
	}

	/**
	 * @return filteredDate
	 */
	public ArrayList<Integer> getArrActivities() {
		return arrActCount;
	}

	/**
	 * @param filteredDate
	 */
	public void setArrActivities(ArrayList<Integer> arrActCount) {
		this.arrActCount = arrActCount;
	}

	/**
	 * @return average
	 */
	public double getAverage() {
		return average;
	}

	/**
	 * @param average
	 */
	public void setAverage(double average) {
		this.average = average;
	}

	/**
	 * Cria um string com duas datas
	 * 
	 * @param date1
	 * @param date2
	 */
	public void dateString(Date date1, Date date2) {
		filteredDate = date1.toString() + " to " + date2.toString();
	}
}
