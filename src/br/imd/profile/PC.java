package br.imd.profile;

import java.util.ArrayList;

import br.imd.filedata.Device;
import br.imd.filedata.HTTP;
import br.imd.filedata.Logon;

public class PC {

	private String device;
	private ArrayList<Device> deviceActivity;
	private ArrayList<HTTP> httpActivity;
	private ArrayList<Logon> logonActivity;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public ArrayList<Device> getDeviceActivity() {
		return deviceActivity;
	}

	public void setDeviceActivity(ArrayList<Device> deviceActivity) {
		this.deviceActivity = deviceActivity;
	}

	public ArrayList<HTTP> getHttpActivity() {
		return httpActivity;
	}

	public void setHttpActivity(ArrayList<HTTP> httpActivity) {
		this.httpActivity = httpActivity;
	}

	public ArrayList<Logon> getLogonActivity() {
		return logonActivity;
	}

	public void setLogonActivity(ArrayList<Logon> logonActivity) {
		this.logonActivity = logonActivity;
	}
}
