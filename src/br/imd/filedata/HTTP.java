package br.imd.filedata;

import java.util.Date;

public class HTTP extends Data {

	private String id;
	private Date date;
	private String user;
	private String pc;
	private String url;

	public HTTP() {

	}

	public HTTP(String id, Date date, String user, String pc, String url) {
		this.id = id;
		this.date = date;
		this.user = user;
		this.pc = pc;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
