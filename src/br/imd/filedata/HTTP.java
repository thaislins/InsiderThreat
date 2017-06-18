package br.imd.filedata;

import java.util.Date;

/**
 * Classe responsável pelo tipo de arquivo HTTP
 */

public class HTTP extends Data {

	private String id;
	private Date date;
	private String user;
	private String pc;
	private String url;
	private String activity;

	/**
	 * Construtor padrão
	 */
	public HTTP() {

	}

	/**
	 * Construtor parametizado
	 */
	public HTTP(String id, Date date, String user, String pc, String url) {
		this.id = id;
		this.date = date;
		this.user = user;
		this.pc = pc;
		this.url = url;
		activity = "Acessed";
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
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
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
