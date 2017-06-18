package br.imd.filedata;

import java.util.Date;

/**
 * Classe responsável pelo tipo de arquivo Devide
 */

public class Device extends Data {

	private String id;
	private Date date;
	private String user;
	private String pc;
	private String activity;

	/**
	 * Construtor padrão
	 */
	public Device() {

	}

	/**
	 * Construtor parametizado
	 */
	public Device(String id, Date date, String user, String pc, String activity) {
		this.id = id;
		this.date = date;
		this.user = user;
		this.pc = pc;
		this.activity = activity;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return pc
	 */
	public String getPc() {
		return pc;
	}

	/**
	 * @param pc
	 */
	public void setPc(String pc) {
		this.pc = pc;
	}

	/**
	 * @return activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
}
