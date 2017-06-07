package br.imd.filedata;

import java.util.Date;

public class Device extends Data {

	private String id;
	private Date date;
	private String user;
	private String pc;
	private String activity;

	public Device() {

	}

	public Device(String id, Date date, String user, String pc, String activity) {
		this.id = id;
		this.date = date;
		this.user = user;
		this.pc = pc;
		this.activity = activity;
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

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
}
